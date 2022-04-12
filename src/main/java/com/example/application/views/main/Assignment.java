package com.example.application.views.main;
class Assignment {
    private int id;
    private String name;
    private double pointsPossible;
    private double pointsEarned;
    private boolean graded;
    private Class Aclass;
    private Teacher teacher;
    private Student student;

    public Assignment(int id, String name, Class AClass, Teacher teacher, Student student) {
        this.id = id;
        this.name = name;
        pointsEarned = 0;
        pointsPossible = 0;
        graded = false;
        this.Aclass = Aclass;
        this.teacher = teacher;
        this.student = student;
    }
    //---getters and setters---\\
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setPointsPoss(double pts) {
      pointsPossible = pts;
    }
    public double getPointsPoss() {
      return pointsPossible;
    }
    public void setPointsEarn(double pts) {
      pointsEarned = pts;
    }
    public double getPointsEarn() {
      return pointsEarned;
    }
    public boolean getGraded() {
      return graded;
    }
    public void setGraded(boolean g) {
      graded = g;
    }
    //---end of getters and setters---\\

}
