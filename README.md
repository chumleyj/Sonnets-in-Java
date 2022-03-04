# Overview

This is a demonstration project I used to learn the basics of coding in Java. My goal was to become familiar with the basics of the language, with stretch goals of reading from a file and connecting to a database.

The program I wrote displays Shakespeare's sonnets. The program begins by asking the user whether to read sonnets from a local file or from a cloud database (Cloud Firestore). After this, the user inputs a sonnet number and the program displays that sonnet.

The program uses a Sonnet class to store, format, and display individual sonnets. If reading from a local file, the program creates a HashMap with instances of each sonnet as the values. If reading from Firestore, the program queries Firestore for the specific sonnet document then creates an instance of a Sonnet object from that information.

[Software Demo Video](http://youtube.link.goes.here)

# Development Environment

* IntelliJ IDEA 2021.3.2 (Community Edition)

* Maven

* Java
  * firebase-admin v8.1.0


# Useful Websites

{Make a list of websites that you found helpful in this project}
* [Web Site Name](http://url.link.goes.here)
* [Web Site Name](http://url.link.goes.here)

# Future Work

{Make a list of things that you need to fix, improve, and add in the future.}
* Item 1
* Item 2
* Item 3
