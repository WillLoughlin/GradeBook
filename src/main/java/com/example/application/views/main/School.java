package com.example.application.views.main;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

class School {
    private String name;

    private ArrayList<Student> students;
    private int num_students;
    private ArrayList<Teacher> teachers;
    private int num_teachers;
    private ArrayList<Class> classes;
    private int num_classes;
    private ArrayList<Assignment> assignments;
    private int num_ass;
    private Logger logger;
    private ArrayList<String> logger_data;

    public School(String name) {
        this.name = name;
        students = new ArrayList<Student>();
        teachers = new ArrayList<Teacher>();
        classes = new ArrayList<Class>();
        assignments = new ArrayList<Assignment>();
        num_students = 0;
        num_teachers = 0;
        num_classes = 0;
        num_ass = 0;
        logger = Logger.getInstance();
        logger_data = new ArrayList<String>();
        load();

    }
    //---getters and setters---\\
    public String getName() {
        return name;
    }
    public ArrayList<Student> getStudents() {
      return students;
    }
    public ArrayList<Teacher> getTeachers() {
      return teachers;
    }
    public ArrayList<Class> getClasses() {
      return classes;
    }
    public ArrayList<Assignment> getAssignments() {
      return assignments;
    }
    //--- end of getters and setters---\\
    public void addClass(String name) {
      Class newClass = new Class(num_classes,name, this);
      num_classes +=1;
      classes.add(newClass);

      logger_data.clear();
      logger_data.add(name);
      logger.update("addClass",logger_data);
      logger_data.clear();

      save();
    }
    public void addStudent(String name) {
      Student newStu = new Student(num_students, name, this);
      num_students +=1;
      students.add(newStu);

      logger_data.clear();
      logger_data.add(name);
      logger.update("addStudent",logger_data);
      logger_data.clear();

      save();
    }
    public void removeStudent(String n) {
      Student toRemove = getStudentWithName(n);
      //System.out.println("Student name to remove in School: " + toRemove.getName());
      students.remove(toRemove);
      num_students-=1;
      // for (int i = 0; i < students.size(); i++) {
      //   System.out.println(students.get(i).getName());
      // }
      save();
    }
    public void removeTeacher(String n) {
      Teacher toRemove = getTeacherWithName(n);
      teachers.remove(toRemove);
      num_teachers-=1;
      save();
    }
    public void removeClass(String n) {
      Class c = getClassWithName(n);
      classes.remove(c);
      num_classes-=1;
      save();
    }
    public void addTeacher(String name) {
      Teacher newTea = new Teacher(num_teachers,name, this);
      num_teachers +=1;
      teachers.add(newTea);

      logger_data.clear();
      logger_data.add(name);
      logger.update("addTeacher",logger_data);
      logger_data.clear();

      save();
    }
    public void addStudentToClass(Class c, Student s) {
      c.addStudent(s);
      s.addClass(c);

      logger_data.clear();
      logger_data.add(c.getName());
      logger_data.add(s.getName());
      logger.update("addStudentToClass",logger_data);
      logger_data.clear();

      save();
    }
    public void removeStudentFromClass(Class c, Student s) {
      c.removeStudent(s);
      s.removeClass(c);

      logger_data.clear();
      logger_data.add(c.getName());
      logger_data.add(s.getName());
      logger.update("removeStudentFromClass",logger_data);
      logger_data.clear();

      save();
    }
    public Class getClassWithName(String n) {
      for (int i = 0; i < classes.size(); i++) {
        if (classes.get(i).getName().equals(n)){
          return classes.get(i);
        }
      }
      return null;
    }
    public ArrayList<Student> getStudentsInClass(Class c) {
      return c.getStudents();
    }
    public void setTeacherToClass(Class c, Teacher t) {
      c.setTeacher(t);

      logger_data.clear();
      logger_data.add(c.getName());
      logger_data.add(t.getName());
      logger.update("setTeacherToClass",logger_data);
      logger_data.clear();

      save();
    }
    public Teacher getTeacherWithName(String n) {
      for (int i = 0; i < teachers.size(); i++) {
        if (teachers.get(i).getName().equals(n)){
          return teachers.get(i);
        }
      }
      return null;
    }
    public Student getStudentWithName(String n) {
      for (int i = 0; i < students.size(); i++) {
        if (students.get(i).getName().equals(n)){
          return students.get(i);
        }
      }
      return null;
    }
    public void save(){
      updateIds();
      String users_file_name = "src/main/java/com/example/application/views/main/Users.csv";
      try {
        FileWriter writer = new FileWriter(users_file_name);
        writer.write("name,user_id,username,password,Teacher or Student\n");
        for (int i = 0; i < teachers.size(); i++) {
          writer.write(teachers.get(i).getName() + "," + teachers.get(i).getId() + "," + teachers.get(i).getUsername() + "," + teachers.get(i).getPassword() + ",Teacher\n");
        }
        for (int i = 0; i < students.size(); i++) {
          writer.write(students.get(i).getName() + "," + students.get(i).getId() + "," + students.get(i).getUsername() + "," + students.get(i).getPassword() + ",Student\n");
        }
        writer.close();
      }
      catch(IOException e) {
       e.printStackTrace();
      }

      String classes_file_name = "src/main/java/com/example/application/views/main/Classes.csv";
      try {
        FileWriter writer = new FileWriter(classes_file_name);
        writer.write("name,class_id,Teacher,Student,Student,...\n");
        for (int i = 0; i < classes.size(); i++) {
          if (classes.get(i).getTeacher() != null) {
            writer.write(classes.get(i).getName() + "," + classes.get(i).getId() + "," + classes.get(i).getTeacher().getName());
          } else {
            writer.write(classes.get(i).getName() + "," + classes.get(i).getId() + ",");
          }
          ArrayList<Student> class_students = classes.get(i).getStudents();

          for (int j = 0; j < class_students.size(); j++) {
            writer.write("," + class_students.get(j).getName());
          }

          writer.write("\n");
        }
        writer.close();
      }
      catch(IOException e) {
       e.printStackTrace();
      }
    }
    //https://www.w3schools.com/java/java_files_read.asp
    public void load(){
      String data;
      String [] split_data;
      try {
            File users_file = new File("src/main/java/com/example/application/views/main/Users.csv");
            Scanner reader = new Scanner(users_file);
            while (reader.hasNextLine()) {
              data = reader.nextLine();
              split_data = data.split(",");
              addUserFromLoad(split_data[0],split_data[1],split_data[2],split_data[3],split_data[4]);
              //System.out.println(split_data[0] + split_data[1] + split_data[2]+split_data[3]+split_data[4]);
            }
            reader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
      try {
            File users_file = new File("src/main/java/com/example/application/views/main/Classes.csv");
            Scanner reader = new Scanner(users_file);
            int lineCounter = 0;
            while (reader.hasNextLine()) {
              lineCounter++;
              data = reader.nextLine();
              if (lineCounter != 1) {
                addClassFromLoad(data.split(","));
              }
              //addUserFromLoad(split_data[0],split_data[1],split_data[2],split_data[3],split_data[4]);
              //System.out.println(split_data[0] + split_data[1] + split_data[2]+split_data[3]+split_data[4]);
              //System.out.println(data);
            }
            reader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }

    }
    public void addUserFromLoad(String name, String user_id_string,String username,String password,String T_or_S) {
      //int user_id = Integer.parseInt(user_id_string);
      //System.out.println(user_id_string);
      int user_id = 0;
      if (T_or_S.equals("Teacher")) {
        Teacher t = new Teacher(num_teachers,name, this);
        t.setUsername(username);
        t.setPassword(password);
        teachers.add(t);
        num_teachers +=1;
      } else if (T_or_S.equals("Student")) {
        Student s = new Student(num_students,name, this);
        s.setUsername(username);
        s.setPassword(password);
        students.add(s);
        num_students +=1;
      }
    }
    public void addClassFromLoad(String [] split_data) {
      //String split_data = data.split(",");
      String n = split_data[0];
      String id_str = split_data[1];
      String teacherName = "";
      if (split_data.length > 2) {
        teacherName = split_data[2];
      }
      Class new_class = new Class(num_classes,n,this);
      num_classes+=1;
      Teacher setT = getTeacherWithName(teacherName);
      new_class.setTeacher(setT);
      classes.add(new_class);
      if (split_data.length > 3) {
        for (int i = 3; i < split_data.length; i++){
          Student addS = getStudentWithName(split_data[i]);
          //System.out.println("Student: " + split_data[i]);
          new_class.addStudent(addS);
          addS.addClass(new_class);

        }
      }

    }

    public void printInfo() {
      System.out.println("Teachers: ");
      for (int i = 0; i < teachers.size();i++) {
        System.out.println(teachers.get(i).getId() + ": " + teachers.get(i).getName());
      }
      System.out.println("\nStudents: ");
      for (int i = 0; i < students.size();i++) {
        System.out.println(students.get(i).getId() + ": " + students.get(i).getName());
      }
      System.out.println("\nClasses: ");
      for (int i = 0; i < classes.size();i++) {
        System.out.println(classes.get(i).getId() + ": " + classes.get(i).getName());
      }
    }

    public void updateIds() {
      for (int i = 0; i < students.size(); i++) {
        students.get(i).setId(i);
      }
      for (int j = 0; j < teachers.size(); j++) {
        teachers.get(j).setId(j);
      }
      for (int c = 0; c < classes.size(); c++) {
        classes.get(c).setId(c);
      }
    }
}
