# Overview

This is a demonstration project I used to learn the basics of coding in Java. My goal was to become familiar with the basics of the language, with stretch goals of reading from a file and connecting to a database.

The program I wrote displays Shakespeare's sonnets. The program begins by asking the user whether to read sonnets from a local file or from a cloud database (Cloud Firestore). After this, the user inputs a sonnet number and the program displays that sonnet.

The program uses a Sonnet class to store, format, and display individual sonnets. If reading from a local file, the program creates a HashMap with instances of each sonnet as the values. If reading from Firestore, the program queries Firestore for the specific sonnet document then creates an instance of a Sonnet object from that information.

The Firestore NoSQL database has a collection called "sonnets" that contains a unique document for each sonnet. Each document contains the name of the sonnet, its number, the number of lines, and the text of the sonnet. 

[Software Demo Video](http://youtube.link.goes.here)

# Development Environment

* IntelliJ IDEA 2021.3.2 (Community Edition)
* Maven v3.8.1
* Java v17.0.2
    * firebase-admin v8.1.0
    * slf4j-api v1.7.32
    * slf4j-simple v.1.7.32

# Useful Websites

* [Google Cloud Firestore Documentation](https://cloud.google.com/firestore/docs)
* [Google Cloud Firestore Java API](https://googleapis.dev/java/google-cloud-firestore/latest/overview-summary.html)
* [Apache Maven Project](https://maven.apache.org/what-is-maven.html)
* [Codecademy Java for Programmers Course](https://www.codecademy.com/learn/java-for-programmers)

# Future Work

* Add more content, such as some of Shakespeare's plays or poems by other authors.
* Update the Sonnet class to get more detail about the sonnet, such as word count, character counts, etc. to allow analysis of the sonnets.
* Add audio files of each sonnet being read that can be played instead of just displaying text.
