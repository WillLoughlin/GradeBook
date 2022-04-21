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
    Abstract_Factory assignment_factory;

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
        assignment_factory = Factory_Provider.getFactory();
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
      students.remove(n);
    }
    public void removeTeacher(String n) {
      Teacher toRemove = getTeacherWithName(n);
      teachers.remove(n);
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
    public void updateAss(Assignment a,double poss, double earn) {
      a.setPointsPoss(poss);
      a.setPointsEarn(earn);
      //System.out.println("A: " + a.getName() + ", Poss: " + a.getPointsPoss() + ", Earn: " + a.getPointsEarn());
      save();
    }
    public void addAssToClass(String ass_name, double ptsPoss, Class c) {
      ArrayList<Student> stu = c.getStudents();
      for (int i = 0; i < stu.size(); i++) {
        Assignment a = assignment_factory.create(num_ass, ass_name, c, c.getTeacher(), stu.get(i));
        a.setPointsPoss(ptsPoss);
        assignments.add(a);
        stu.get(i).addAssignment(a);
        c.addAssignment(a);
        num_ass+=1;
      }
      save();
    }
    public ArrayList<Assignment> getTotalAssFromClass(Class c) {
      ArrayList<Assignment> toRet = new ArrayList<Assignment>();
      ArrayList<Assignment> ass = c.getAssignments();
      for (int i = 0; i < ass.size(); i++) {
        String n = ass.get(i).getName();
        boolean add = true;
        for (int j = 0; j < toRet.size(); j++) {
          if (toRet.get(j).getName().equals(n)) {
            add = false;
          }
        }
        if (add){
          toRet.add(ass.get(i));
        }
      }
      return toRet;
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
    public String checkUser(String u, String p) {
      for (int i = 0; i < students.size(); i++) {
        if (u.equals(students.get(i).getUsername()) && p.equals(students.get(i).getPassword())) {
          return "Student";
        }
      }
      for (int i = 0; i < teachers.size(); i++) {
        if (u.equals(teachers.get(i).getUsername()) && p.equals(teachers.get(i).getPassword())) {
          return "Teacher";
        }
      }
      return "Invalid";
    }
    public Class getClassWithName(String n) {
      for (int i = 0; i < classes.size(); i++) {
        if (classes.get(i).getName().equals(n)){
          return classes.get(i);
        }
      }
      return null;
    }
    public Teacher getTeacherWithUsername(String u) {
      for (int i = 0; i < teachers.size(); i++) {
        if (teachers.get(i).getUsername().equals(u)){
          return teachers.get(i);
        }
      }
      return null;
    }
    public void removeClass(Class c) {
      classes.remove(c);
    }
    public void removeClassWithName(String name) {
      removeClass(getClassWithName(name));
    }
    public ArrayList<Student> getStudentsInClass(Class c) {
      return c.getStudents();
    }
    public void setTeacherToClass(Class c, Teacher t) {
      c.setTeacher(t);
      t.addClass(c);

      logger_data.clear();
      logger_data.add(c.getName());
      logger_data.add(t.getName());
      logger.update("setTeacherToClass",logger_data);
      logger_data.clear();

      save();
    }
    public boolean validClassName(String n) {
      for (int i = 0; i < classes.size(); i++) {
        if (classes.get(i).getName().equals(n)) {
          return false;
        }
      }
      return true;
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
    public Student getStudentWithUsername(String n) {
      //System.out.println("Looking for student with username " + n);
      for (int i = 0; i < students.size(); i++) {
        if (students.get(i).getUsername().equals(n)){
          return students.get(i);
        }
      }
      return null;
    }
    public ArrayList<Assignment> getAssignmentsStudentClass(String sname, String cname) {
      // System.out.println("Checking assignments");
      // System.out.println("Comparing against: " + sname + ", " + cname);
      ArrayList<Assignment> ass = new ArrayList<Assignment>();
      for (int i = 0; i < assignments.size(); i++) {
        // System.out.println("Name: " + assignments.get(i).getAclass().getName());
        // System.out.println("Student: " + assignments.get(i).getStudent().getName());
        // System.out.println("");

        if (assignments.get(i).getAclass().getName().equals(cname) && assignments.get(i).getStudent().getUsername().equals(sname)) {
          ass.add(assignments.get(i));
        }
      }
      return ass;
    }
    public boolean checkValidRegister(String n) {
      for (int i = 0; i < students.size(); i++) {
        if (students.get(i).getUsername().equals(n)){
          return false;
        }
      }
      for (int i = 0; i < teachers.size(); i++) {
        if (teachers.get(i).getName().equals(n)){
          return false;
        }
      }
      return true;
    }
    public void deleteAss(Assignment a) {
      Student s = a.getStudent();
      Class c = a.getAclass();
      s.deleteAss(a);
      c.deleteAss(a);
      assignments.remove(a);
      num_ass -=1;
      save();
    }
    public void addAssignment(String name,Class c,Teacher teacher,Student student) {
      Assignment a = assignment_factory.create(num_ass, name, c, teacher, student);
      num_ass +=1;
      assignments.add(a);
      c.addAssignment(a);
      teacher.addAssignment(a);
      student.addAssignment(a);
      save();
    }
    public void addAssignmentPts(String name,Class c,Teacher teacher,Student student, double ptsPoss, double ptsEarn) {
      Assignment a = assignment_factory.create(num_ass, name, c, teacher, student);
      a.setPointsEarn(ptsEarn);
      a.setPointsPoss(ptsPoss);
      num_ass +=1;
      assignments.add(a);
      c.addAssignment(a);
      teacher.addAssignment(a);
      student.addAssignment(a);
      save();
    }
    public void save(){
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

      String ass_file_name = "src/main/java/com/example/application/views/main/Assignments.csv";
      try {
        FileWriter writer = new FileWriter(ass_file_name);
        writer.write("name,ass_id,pointsPossible,pointsEarned,graded,class name,teacher name,student name\n");
        for (int i = 0; i < assignments.size(); i++) {
          writer.write(assignments.get(i).getName() + "," + assignments.get(i).getId() + "," + assignments.get(i).getPointsPoss() + "," + assignments.get(i).getPointsEarn() + "," + assignments.get(i).getGraded() + "," +  assignments.get(i).getAclass().getName() + "," + assignments.get(i).getTeacher().getName() + "," +  assignments.get(i).getStudent().getName() + "\n");
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
      try {
            File ass_file = new File("src/main/java/com/example/application/views/main/Assignments.csv");
            Scanner reader = new Scanner(ass_file);
            int lineCounter = 0;
            while (reader.hasNextLine()) {
              lineCounter+=1;
              data = reader.nextLine();
              split_data = data.split(",");
              if (lineCounter != 1) {
                addAssFromLoad(split_data[0],split_data[1],split_data[2],split_data[3],split_data[4],split_data[5],split_data[6],split_data[7]);
              }
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
      if (setT != null) {
        setT.addClass(new_class);
      }
      if (split_data.length > 3) {
        for (int i = 3; i < split_data.length; i++){
          Student addS = getStudentWithName(split_data[i]);
          //System.out.println("Student: " + split_data[i]);
          new_class.addStudent(addS);
          addS.addClass(new_class);

        }
      }

    }

    public void addAssFromLoad (String name, String id_str, String pointsPossible_str, String pointsEarned_str, String graded_str, String className, String teacherName, String studentName) {
      //System.out.println(pointsPossible_str);
      double pointsPossible = Double.parseDouble(pointsPossible_str);
      double pointsEarned = Double.parseDouble(pointsEarned_str);
      boolean graded = Boolean.parseBoolean(graded_str);
      Teacher t = getTeacherWithName(teacherName);
      Class c = getClassWithName(className);
      Student s = getStudentWithName(studentName);

      Assignment a = assignment_factory.create(num_ass, name, c, t, s);
      num_ass +=1;

      a.setPointsPoss(pointsPossible);
      a.setPointsEarn(pointsEarned);
      a.setGraded(graded);

      assignments.add(a);
      c.addAssignment(a);
      t.addAssignment(a);
      s.addAssignment(a);
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
}
