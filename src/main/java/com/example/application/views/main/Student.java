package com.example.application.views.main;
import java.util.ArrayList;

class Student extends User {
    private ArrayList<Assignment> assignments;
    private Class currentClass;

    public Student(int id, String name, School school) {
      super(id,name,school);
      assignments = new ArrayList<Assignment>();
    }

    public void addAssignment(Assignment a) {
      assignments.add(a);
    }

    public ArrayList<Assignment> getAssignments() {
      return assignments;
    }
    public double getGradeInClass(Class c) {
      double pointsPossible = 0.0;
      double pointsEarned = 0.0;
      for (int i = 0; i < assignments.size(); i++) {
        if (assignments.get(i).getAclass() == c) {
          pointsPossible += assignments.get(i).getPointsPoss();
          pointsEarned += assignments.get(i).getPointsEarn();
        }
      }
      if (pointsPossible > 0) {
        return pointsEarned / pointsPossible;
      }
      return 0;
    }
    public void setCurrentClass(Class c) {
      this.currentClass = c;
    }
    public double getGradeInCurrentClass() {
      return getGradeInClass(this.currentClass);
    }
    public void deleteAss(Assignment a) {
      assignments.remove(a);
    }
    public String getGradeInCurrentClassString() {
      double grade = getGradeInClass(this.currentClass);
      grade = grade * 100;
      String s = String.valueOf(grade);

      if (s.length() > 5) {
        s = s.substring(0,5);
      }
      return s + "%";
    }
    public ArrayList<Assignment> getAssForClass(Class c) {
      ArrayList<Assignment> ass = new ArrayList<Assignment>();
      for (int i = 0; i < assignments.size(); i++) {
        if (assignments.get(i).getAclass() == c) {
          ass.add(assignments.get(i));
        }
      }
      return ass;
    }
}
