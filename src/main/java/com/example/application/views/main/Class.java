package com.example.application.views.main;
import java.util.ArrayList;

class Class implements Class_Interface{
    private int id;
    private String name;
    private Teacher teacher;
    private ArrayList<Student> students;
    private School school;
    private ArrayList<Assignment> assignments;

    public Class(int id, String name, School school) {
        this.id = id;
        this.name = name;
        students = new ArrayList<Student>();
        this.school = school;
        assignments = new ArrayList<Assignment>();

    }
    //---getters and setters---\\
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setTeacher(Teacher t) {
      teacher = t;
    }
    public Teacher getTeacher() {
      return teacher;
    }
    public ArrayList<Student> getStudents(){
      return students;
    }
    //---end of getters and setters---\\
    public void addStudent(Student s) {
      students.add(s);
    }
    public void removeStudent(Student s) {
      students.remove(s);
    }
    public void addAssignment(Assignment a) {
      assignments.add(a);
    }
    public ArrayList<Assignment> getAssignments() {
      return assignments;
    }
}
