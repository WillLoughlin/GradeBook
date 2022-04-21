package com.example.application.views.main;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.grid.Grid;
import java.util.ArrayList;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.component.html.Span;
import java.util.Optional;




@Route("teacher-class")
@PageTitle("Class Portal")

public class TeacherClassView extends VerticalLayout implements HasUrlParameter<String> {

  String teacher_username;
  String class_name;

  public TeacherClassView() {
    //setParameter needs to run
  }

  @Override
  public void setParameter(BeforeEvent event, String parameter) {
    System.out.println("teacher-class: " + parameter);
    String[] split = parameter.split("-");
    this.teacher_username = split[0];
    this.class_name = split[1];
    buildView();
  }

  public void buildView() {

    setAlignItems(Alignment.CENTER);

    School school = new School("school");
    Teacher t = school.getTeacherWithUsername(teacher_username);
    Class c = school.getClassWithName(class_name);

    Span student_grid_title = new Span("Students in " + class_name);
    student_grid_title.getStyle().set("font-weight", "bold");

    ArrayList<Student> student_list = c.getStudents();
    for (int i = 0; i < student_list.size(); i++) {
      student_list.get(i).setCurrentClass(c);
    }
    Grid<Student> student_grid = new Grid<>(Student.class, false);
    student_grid.addColumn(Student::getName).setHeader("Student Name");
    student_grid.addColumn(Student::getGradeInCurrentClassString).setHeader("Grade");

    student_grid.setItems(c.getStudents());
    student_grid.setWidth("400px");

    student_grid.addSelectionListener(selection -> {
      Optional<Student> optionalStu = selection.getFirstSelectedItem();
      if(optionalStu.isPresent()) {
        System.out.println(optionalStu.get().getUsername());
        UI.getCurrent().navigate("teacher-class-student/" + this.teacher_username+"-"+this.class_name+"-"+optionalStu.get().getUsername());
      }
    });

    TextField textFieldStudent = new TextField("Student Username");
    Button ASButton = new Button("Add Student");
    Button RSButton = new Button("Remove Student");
    RSButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

    ASButton.addClickListener(click -> {
      String u = textFieldStudent.getValue();
      if (!u.equals("")) {
        Student s = school.getStudentWithUsername(u);
        if (s != null) {
          school.addStudentToClass(c,s);
          student_grid.getDataProvider().refreshAll();
          notify("Student " + u + " added to " + class_name);
        } else {
          notify("Username " + u + " not found in student database.");
        }
      }
    });


    ASButton.addClickShortcut(Key.ENTER);

    VerticalLayout left = new VerticalLayout(student_grid_title,student_grid,textFieldStudent,new HorizontalLayout(ASButton,RSButton));


    Span ass_grid_title = new Span("Assignments in " + class_name);
    ass_grid_title.getStyle().set("font-weight", "bold");

    Grid<Assignment> ass_grid = new Grid<>(Assignment.class,false);
    ass_grid.addColumn(Assignment::getName).setHeader("Assignment Name");
    ass_grid.addColumn(Assignment::getClassAvg).setHeader("Average");
    ass_grid.setItems(school.getTotalAssFromClass(c));
    ass_grid.setWidth("400px");

    ass_grid.addSelectionListener(selection -> {
      Optional<Assignment> optionalAss = selection.getFirstSelectedItem();
      if(optionalAss.isPresent()) {
        //System.out.println(optionalStu.get().getUsername());
        UI.getCurrent().navigate("teacher-class-assignment/" + this.teacher_username+"-"+this.class_name+"-"+optionalAss.get().getName());
      }
    });

    RSButton.addClickListener(click -> {
      String u = textFieldStudent.getValue();
      if (!u.equals("")) {
        Student s = school.getStudentWithUsername(u);
        if (s != null) {
          // school.addStudentToClass(c,s);
          school.removeStudentFromClass(s,c);
          student_grid.setItems(c.getStudents());
          ass_grid.setItems(school.getTotalAssFromClass(c));
          notify("Student " + u + " removed from " + class_name);
        } else {
          notify("Username " + u + " not found in student database.");
        }
      }
    });

    TextField textFieldAssignment = new TextField("Assignment Name");
    TextField textFieldAssignmentPts = new TextField("Points Possible");
    Button AAButton = new Button("Add Assignment");

    VerticalLayout right = new VerticalLayout(ass_grid_title,ass_grid,new HorizontalLayout(textFieldAssignment,textFieldAssignmentPts),AAButton);

    AAButton.addClickListener(click -> {
      String aname = textFieldAssignment.getValue();
      double ptsPoss = Double.parseDouble(textFieldAssignmentPts.getValue());
      school.addAssToClass(aname,ptsPoss,c);
      ass_grid.setItems(school.getTotalAssFromClass(c));
      student_grid.setItems(c.getStudents());
    });

    Button back = new Button("Back");
    back.addClickListener(click -> {
      UI.getCurrent().navigate("teacher/" + teacher_username);
    });


    add(
      new H1("Teacher Portal: " + this.class_name),
      new HorizontalLayout(
        left,
        right
      ),
      back
    );

  }
  public void notify(String n) {
    Notification notif = new Notification("notify");
    notif.show(n,5000,Notification.Position.MIDDLE);
  }
}
