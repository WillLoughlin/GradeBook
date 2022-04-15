package com.example.application.views.main;
import java.util.ArrayList;

abstract class User {
    private int id;
    private String name;
    private ArrayList<Class> classes;
    private School school;
    private String username;
    private String password;

    public User(int id, String name, School school) {
        this.id = id;
        this.name = name;
        classes = new ArrayList<Class>();
        this.school = school;
        this.username = "";
        this.password = "";
    }
    //---getters and setters---\\
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public ArrayList<Class> getClasses() {
      return classes;
    }
    public void addClass(Class c) {
      classes.add(c);
    }
    public void removeClass(Class c) {
      classes.remove(c);
    }
    public void setUsername(String u) {
      username = u;
    }
    public String getUsername() {
      return username;
    }
    public void setPassword(String p) {
      password = p;
    }
    public String getPassword() {
      return password;
    }
    //---end of getters and setters---\\
}
