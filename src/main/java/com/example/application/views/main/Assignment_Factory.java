package com.example.application.views.main;
public class Assignment_Factory extends Abstract_Factory {

    @Override
    Assignment create(int id, String name, Class AClass, Teacher teacher, Student student) {
        Assignment assignment = new Assignment(id, name, AClass, teacher, student);
        return assignment;
    }
}
