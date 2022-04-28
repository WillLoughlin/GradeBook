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



@Route("student-class")
@PageTitle("Class Portal")

public class StudentClassView extends VerticalLayout implements HasUrlParameter<String> {
  String student_name;
  String class_name;

  public StudentClassView() {
    //setParameter needs to run
  }

  @Override
  public void setParameter(BeforeEvent event, String parameter) {
    System.out.println("student-class: " + parameter);
    String[] split = parameter.split("-");
    this.student_name = split[0];
    this.class_name = split[1];
    buildView();
  }

  public void buildView() {
    setAlignItems(Alignment.CENTER);
    School school = new School("school");
    Student self = school.getStudentWithUsername(student_name);
    Class c = school.getClassWithName(class_name);
    ArrayList<Assignment> assignments = school.getAssignmentsStudentClass(this.student_name, this.class_name);

    Grid<Assignment> grid = new Grid<>(Assignment.class, false);
    grid.addColumn(Assignment::getName).setHeader("Assignment");
    grid.addColumn(Assignment::getPointsPoss).setHeader("Points Possible");
    grid.addColumn(Assignment::getPointsEarn).setHeader("Points Earned");


    grid.setItems(assignments);

    grid.setWidth("400px");

    Button back = new Button("Back");
    back.addClickListener(click -> {
      UI.getCurrent().navigate("student/" + student_name);
    });

    Span gridTitle = new Span("Grades for " + self.getName() + " in " + class_name);
    gridTitle.getStyle().set("font-weight", "bold");

    c.setCurrentStudent(self);
    String grade = c.getCurrentStudentGrade();
    Span grade_title = new Span("Total: " + grade);
    grade_title.getStyle().set("font-weight", "bold");


    add(
      new H1("Student Portal: " + this.class_name),
      gridTitle,
      grade_title,
      new HorizontalLayout(grid),
      back
    );

  }
}
