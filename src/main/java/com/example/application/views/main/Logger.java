package com.example.application.views.main;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
//File creation / writing code from: https://www.w3schools.com/java/java_files_create.asp

/*
//$$$ Tracker $$$\\
We use the tracker pattern here in the form of a logger that saves event information from the School object.
This information is stored in logger.txt and is useful to understand program history.
//$$$$$$$$$$$$$$$$$\\
*/
/*
//$$$ Singleton $$$\\
The logger is used as a singleton to ensure that there is only one active logger making changes to the logger.txt file.
This can be seen by the private constructor and the getInstance method in the Logger class.
//$$$$$$$$$$$$$$$$$\\
*/

//-----------------Logger Class-----------------\\
//This class implemented as part of the observer pattern
class Logger implements Logger_Interface {
  private static Logger logger;

  private Logger() {

  }

  public static synchronized Logger getInstance() {
    if (logger == null) {
      logger = new Logger();
      return logger;
    } else {
      return logger;
    }
  }

  //gets writer to append to file for given day
  private FileWriter getFile() {
    String logger_file_name = "src/main/java/com/example/application/views/main/logger.txt";
    FileWriter writer;
    try {
      writer = new FileWriter(logger_file_name, true);
      return writer;
    }
    catch(IOException e) {
     e.printStackTrace();
    }
    return null;
  }

  //clears logger file for given day, called from proj2.java
  public void clearFile() {
    String logger_file_name = "src/main/java/com/example/application/views/main/logger.txt";
    try {
      FileWriter writer = new FileWriter(logger_file_name);
    }
    catch(IOException e) {
     e.printStackTrace();
    }
  }

  //update takes an event and a list of data and prints to the logger file
  public void update(String event, ArrayList<String> data) {
    FileWriter writer = getFile();
    try {
    switch (event) {
        case "addClass":
          //index 0 is name of class added
          writer.write("Create Class: " + data.get(0) + " added to class list. \n");
          writer.close();
          break;
        case "addStudent":
          //index 0 is name of student added
          writer.write("Create Student: " + data.get(0) + " added to student list. \n");
          writer.close();
          break;

        case "addTeacher":
          //index 0 is name of teacher added
          writer.write("Create Teacher: " + data.get(0) + " added to teacher list. \n");
          writer.close();
          break;
        case "addStudentToClass":
          //index 0 is class name, 1 is student Name
          writer.write("Add Student to Class: " + data.get(1) + " added to Class: " + data.get(0) + "\n");
          writer.close();
          break;
        case "removeStudentFromClass":
          //index 0 is class name, 1 is student Name
          writer.write("Remove Student from Class: " + data.get(1) + " removed from Class: " + data.get(0)+ "\n");
          writer.close();
          break;
        case "setTeacherToClass":
          //index 0 is class name, 1 is teacher Name
          writer.write("Set Teacher to Class: " + data.get(1) + " set as teacher to Class: " + data.get(0)+ "\n");
          writer.close();
          break;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
