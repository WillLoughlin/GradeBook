package com.example.application.views.main;
import java.util.ArrayList;

class Teacher extends User {
    private ArrayList<Assignment> createdAssignments;

    public Teacher(int id, String name, School school) {
        super(id,name,school);
        createdAssignments = new ArrayList<Assignment>();
    }

    public void addAssignment(Assignment a) {
      createdAssignments.add(a);
    }

    public ArrayList<Assignment> getAssignments() {
      return createdAssignments;
    }
}
