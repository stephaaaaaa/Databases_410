# Databases_410
* Stephanie Labastida - stephanielabastida@u.boisestate.edu
* Sadie Shirts - sadieshirts@u.boisestate.edu
* Spring 2018

## Overview

This Java project is a  command line to-do list manager that uses an SQL database as the backend for storing tasks in a
to-do list.

## Compiling and Using

First, navigate to the correct location in the project directory:
    Databases > out > artifacts > Databases_jar

Since this project has many external libraries and dependencies, we can only run it through a .jar file. This way, all the
dependencies come packaged, and we run into no errors when we run it from the command line.
We then type the following:

    java -jar Databases.jar

We can then use the program as intended!

## Classes

* example-data.sql
    This file we used in MySQL workbench in order to populate the task table created in schema.sql with example data.
* schema.sql
    This file generates the task table in MySql, which we populate with example data in example-data.sql.
* Queries.java
    This file was to help keep our backend class and main class clean, and contains the queries for displaying information from
    our database, as well as updating it.
* SSH_Manager.java
    This file was also another helper to keep the SSH methods in one place. It was easiest to keep it in a separate class,
    so we would not have our main class over-saturated with many classes that existed for the program's connectivity functionality.
* TaskTracker_Backend.java
    This is our back-end file, that contains all the methods that run database queries, so that when the respective command is
    called in TaskTracker.java, we only need to call the simple one-line method that is defined in this class.
* TaskTracker.java
    This is the class in which our cliche shell is declared, and where our main method exists. Each command reflects a method
    call, which will in turn call the proper helper method in our TaskTracker_Backend.java class. The way that the classes are laid out
    made sense from an organization standpoint, and having separate classes for helper methods allowed us to keep our main and
    back-end classes from being too overwhelming.


## Discussion

Our biggest issue in creating this project was getting our Java program to connect to the database via ssh. We realized that
we did not have the complete .jar file downloaded in order to get the provided workaround for the broken code in Java
(Class.forName("com.mysql.jdbc.Driver").newInstance()) to actually work.
We also ran into some road blocks because we were both relatively new to the IntelliJ IDE. Adding the external libraries
(Cliche, Jsch, MySQL) proved to be difficult. Many classmates had different approaches to it, using Gradle or Maven, but
soon we realized that you have to manipulate the project structure, and add the libraries as .jar files in IntelliJ.
After fixing that issue, we ran into another of trying to figure out what ports to use when connecting via ssh,
because we were not working on the same machine. We spent a long time of trial and error before successfully connecting from
Stephanie's machine to Sadie's database that was already set up.

We thankfully kept our classes very organized, and were able to break our classes up into helper classes (like by creating
SSH_Manager as a separate class from our main managers). Keeping the schema simple also helped to bring order to queries that
were called to the database.

## Testing

We tested the program from command line via the ssh connection and port that we had set up on one of the machines. We also ran
our on MySQLWorkbench using solely the database in order to validate our queries and see the table in real time.
