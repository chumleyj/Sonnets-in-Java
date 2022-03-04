import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;


/***********************************************
 * Class: Shakespeare
 * Purpose: Holds a collection of sonnets that
 * can be displayed based on user input.
 **********************************************/
public class Shakespeare {
    // name of file containing the sonnets
    private String sonnets = "sonnets.txt";
    // get the line separator character for current OS
    final String lineSeparator = System.getProperty("line.separator");
    // HashMap to hold imported sonnets
    public HashMap<Integer, Sonnet> sonnetMap = new HashMap<>();

    /***********************************************
     * Function: extractNumber
     * Purpose: Extracts the integer at the
     * beginning of the text parameter and returns
     * it.
     **********************************************/
    private int extractNumber(String text) {
        String firstLine = text.substring(0, text.indexOf(lineSeparator));
        return Integer.parseInt((firstLine));
    }

    /***********************************************
     * Function: importSonnets
     * Purpose: Imports the contents of this.sonnets
     * file and creates individual sonnets for each
     * entry. Adds each to this.sonnetMap with the
     * sonnet number as the key.
     **********************************************/
    public void importSonnets() {

        try {
            File sonnetFile = new File(sonnets);
            Scanner sonnetReader = new Scanner(sonnetFile);
            // delimiter for sonnets file
            sonnetReader.useDelimiter("/BR");

            // loop through file so long as there are more lines
            while (sonnetReader.hasNextLine()) {
                // read next sonnet in file
                String data = sonnetReader.next();

                // get sonnet number and remove number from start current read
                int currentNum = extractNumber(data);
                data = data.substring(data.indexOf(lineSeparator)).trim();

                // create new sonnet with the current info and add to sonnetMap
                Sonnet currentSonnet = new Sonnet(currentNum, data);
                sonnetMap.put(currentNum, currentSonnet);
            }

            sonnetReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("\"sonnet.txt file could not be opened.\"");
        }
    }

    /***********************************************
     * Function: getSourceSelection
     * Purpose: Prompts the user to select whether
     * to read sonnets from a local file or from
     * a Firestore database.
     **********************************************/
    public int getSourceSelection() {
        // scanner for user input
        Scanner userInput = new Scanner(System.in);

        String response;
        int userSelection = -1;

        // continue prompting until valid selection provided
        while (userSelection <0 || userSelection > 1) {
            System.out.print("Read from local file (0) or from Cloud Database (1)? ");
            response = userInput.nextLine().trim();

            try {
                // try parsing input to an int
                userSelection = Integer.parseInt(response);
            } catch (NumberFormatException nfe) {
                // if non-integer input, set to -1
                userSelection = -1;
            }

            // check if solution is between 0 and 1
            if (userSelection < 0 || userSelection > 1) {
                System.out.println("Invalid response. Enter 0 for local file or 1 for Cloud Database.");
            }
        }

        return userSelection;
    }

    /***********************************************
     * Function: getUserSelection
     * Purpose: Prompts the user to select a sonnet
     * to display. Checks for valid input
     **********************************************/
    public int getSonnetSelection(int highestSonnet) {
        // scanner for user input
        Scanner userInput = new Scanner(System.in);

        // display range of sonnets to pick from
        String prompt = String.format("Enter a sonnet number to display (1-%d) or 0 to quit: ", highestSonnet);

        // variables for user selection
        String response;
        int userSelection = -1;

        // continue prompting until the user provides a valid selection
        while (userSelection < 0 || userSelection > highestSonnet) {
            // get input
            System.out.print(prompt);
            response = userInput.nextLine().trim();

            try {
                // try parsing input to an int
                userSelection = Integer.parseInt(response);
            } catch (NumberFormatException nfe) {
                // if non-integer input, set to -1
                userSelection = -1;
            }

            // check if solution is between 0 and highest sonnet number (inclusive)
            if (userSelection < 0 || userSelection > highestSonnet) {
                System.out.println("Invalid selection");
            }
        }

        return userSelection;
    }

    public static void main(String[] args) {
        Shakespeare sonnets = new Shakespeare();

        // prompt user whether to read from local file or Firestore
        int source = sonnets.getSourceSelection();

        // read from local file
        if (source == 0) {
            // import sonnets from local file
            sonnets.importSonnets();
            // get the highest number sonnet
            int highestSonnet = Collections.max(sonnets.sonnetMap.keySet());

            // prompt for sonnet to display
            int sonnetNumber = sonnets.getSonnetSelection(highestSonnet);

            // display and continue prompting until user selects 0 to exit
            while (sonnetNumber > 0) {
                sonnets.sonnetMap.get(sonnetNumber).displayText();
                sonnetNumber = sonnets.getSonnetSelection(highestSonnet);
            }

        } else { // read from Firestore
            FirebaseApp.initializeApp();
            Firestore db = FirestoreClient.getFirestore();

            // variable to hold the highest sonnet number in the database
            int highestSonnet;

            // get the highest sonnet number from the Data document in sonnets collection
            try {
                Map<String, Object> data = db.collection("sonnets").document("Data").get().get().getData();
                highestSonnet = Math.toIntExact((long) data.get("numSonnets"));
            } catch (InterruptedException | ExecutionException e) {
                // set to 154 if exception is thrown
                highestSonnet = 154;
            }

            // prompt for sonnet to display
            int sonnetNumber = sonnets.getSonnetSelection(highestSonnet);

            // display and continue prompting until user selects 0 to exit
            while (sonnetNumber > 0) {

                String sonnetName = "Sonnet " + sonnetNumber;

                // pull data from the appropriate sonnet document in Firestore
                try {
                    // get document reference from Firestore
                    ApiFuture<DocumentSnapshot> future = db.collection("sonnets").document(sonnetName).get();
                    DocumentSnapshot doc = future.get();

                    // if the document exists, create a new sonnet with its data and display it
                    if (doc.exists()) {
                        Sonnet sonnetSelection = new Sonnet(doc.getData());
                        sonnetSelection.displayText();
                    } else {
                        // display error message if the sonnet document requested does not exist
                        System.out.println("Could not access sonnet.");
                    }

                } catch (ExecutionException | InterruptedException e) {
                    // display error message if sonnet could not be accessed
                    System.out.println("Could not access sonnet.");
                }

                sonnetNumber = sonnets.getSonnetSelection(highestSonnet);
            }
        }
    } // main method
} // Shakespeare Class
