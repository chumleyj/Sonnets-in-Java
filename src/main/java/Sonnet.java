import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/***********************************************
 * Class: Sonnet
 * Purpose: Stores all information about a
 * single sonnet and methods for getting info
 * about or displaying the sonnet.
 **********************************************/
public class Sonnet {
    // Instance variables
    private int idNumber;
    private int numLines;
    private String name;
    private ArrayList<String> text;

    /***********************************************
     * Constructor: Sonnet
     * Purpose: creates a new sonnet object,
     * assigning the idNumber and parsing the text
     * into lines.
     **********************************************/
    public Sonnet(int idNumber, String text) {
        this.idNumber = idNumber;
        this.name = String.format("Sonnet %d", idNumber);
        parseText(text);
        this.numLines = this.text.size();
    }

    /***********************************************
     * Constructor: Sonnet
     * Purpose: creates a new sonnet object from
     * a Firebase document
     **********************************************/
    public Sonnet(Map<String, Object> sonnetData) {
        this.idNumber = Math.toIntExact((long) sonnetData.get("sonnetNumber"));
        this.numLines = Math.toIntExact((long) sonnetData.get("numLines"));
        this.name = (String) sonnetData.get("name");
        this.text = (ArrayList<String>) sonnetData.get("text");
    }

    /***********************************************
     * Function: parseText
     * Purpose: Splits text parameter into lines
     * and converts to an ArrayList.
     **********************************************/
    private void parseText(String text) {
        // get the line separator and split text by that separator
        String separator = System.getProperty("line.separator");
        String[] splitText = text.split(separator);

        // convert to ArrayList and store in this.text
        this.text = new ArrayList<>(Arrays.asList(splitText));
    }

    // Getters for private instance variables
    public int getNumber() { return this.idNumber; }
    public int getNumLines() { return this.numLines; }
    public String getName() { return this.name; }
    public ArrayList<String> getText() { return this.text; }

    /***********************************************
     * Function: displayText
     * Purpose: displays the sonnet with each line
     * of the sonnet displayed on its own line.
     **********************************************/
    public void displayText() {
        int startIndent = this.text.size() - 2;
        System.out.println();
        System.out.println(this.name.toUpperCase());
        for (int i = 0; i < this.text.size(); i++) {
            if (i >= startIndent) {
                System.out.print("\t");
            }
            System.out.println(this.text.get(i));
        }
        System.out.println();
    }

    /***********************************************
     * Function: exportToFirestore
     * Purpose: creates a HashMap with all sonnet
     * data for uploading to a Firestore database.     *
     **********************************************/
    public Map<String, Object> exportToFirestore() {

        Map<String, Object> export = new HashMap<>();
        export.put("sonnetNumber", this.idNumber);
        export.put("name", this.name);
        export.put("numLines", this.numLines);
        export.put("text", this.text);

        return export;
    }

    public static void main(String[] args) { }
}

