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
        this.Aclass = AClass;
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
    public Class getAclass() {
        return Aclass;
    }
    public void setClass(Class c) {
        this.Aclass = c;
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    public Teacher getTeacher() {
      return this.teacher;
    }
    public void setStudent(Student student) {
      this.student = student;
    }
    public Student getStudent() {
      return this.student;
    }
    public String getGradeStr() {
      if (pointsPossible != 0) {
        String toRet = ((pointsEarned / pointsPossible) *100) + "%";
        if (toRet.length() > 6) {
          toRet = toRet.substring(0,5);
          toRet = toRet + "%";
        }
        return toRet;
      }
      return "0.0%";
    }
    public String getStudentName() {
      return student.getName();
    }
    public String getClassAvg() {
      String s = String.valueOf(Aclass.getClassAvgAss(name) * 100);
      if (s.length() > 5) {
        s = s.substring(0,5);
      }
      s = s + "%";
      return s;
    }
    //---end of getters and setters---\\

}
