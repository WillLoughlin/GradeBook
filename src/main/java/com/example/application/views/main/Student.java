package com.example.application.views.main;
import java.util.ArrayList;

class Student extends User {
    private ArrayList<Assignment> assignments;

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
}
