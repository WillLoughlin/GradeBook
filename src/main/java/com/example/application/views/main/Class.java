package com.example.application.views.main;
import java.util.ArrayList;

class Class implements Class_Interface{
    private int id;
    private String name;
    private Teacher teacher;
    private ArrayList<Student> students;
    private School school;
    private ArrayList<Assignment> assignments;
    private Student cur_student;

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
      // if (t != null) {
      //   ArrayList<Class> cs = t.getClasses();
      //   if (!cs.contains(this)) {
      //     t.addClass(this);
      //   }
      // }
      teacher = t;
    }
    public Teacher getTeacher() {
      return teacher;
    }
    public ArrayList<Student> getStudents(){
      return students;
    }
    public int getNumStudents() {
      return students.size();
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
    public void deleteAss(Assignment a) {
      assignments.remove(a);
    }
    public ArrayList<Assignment> getAssignments() {
      return assignments;
    }
    public void setCurrentStudent(Student s) {
      this.cur_student = s;
    }
    public void clearAss() {
      assignments = new ArrayList<Assignment>();
    }
    public String getCurrentStudentGrade() {
      String g = String.valueOf(this.cur_student.getGradeInClass(this) * 100);
      if (g.length() > 5) {
        g = g.substring(0,5);
      }
      g = g + "%";
      return g;
    }
    public ArrayList<Assignment> getAssWithName(String n) {
      ArrayList<Assignment> toRet = new ArrayList<Assignment>();
      for (int i = 0; i < assignments.size(); i++) {
        if (assignments.get(i).getName().equals(n)) {
          toRet.add(assignments.get(i));
        }
      }
      return toRet;
    }
    public double getClassAvgAss(String n) {
      ArrayList<Assignment> ass = getAssWithName(n);
      double totalPoss = 0.0;
      double totalEarn = 0.0;
      for (int i = 0; i < ass.size();i++) {
        totalPoss += ass.get(i).getPointsPoss();
        totalEarn += ass.get(i).getPointsEarn();
      }
      return totalEarn / totalPoss;
    }
}
