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
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.BeforeEvent;

@Route("test")
public class MainView extends VerticalLayout implements HasUrlParameter<String> {

  public MainView() {
    VerticalLayout todosList = new VerticalLayout();
    TextField taskFieldStudent = new TextField();
    TextField taskFieldTeacher = new TextField();
    TextField taskFieldClass = new TextField();
    Button addButtonStudent = new Button("Add Student");
    Button addButtonTeacher = new Button("Add Teacher");
    Button addButtonClass = new Button("Add Class");

    Button removeButtonStudent = new Button("Remove Student");
    removeButtonStudent.addThemeVariants(ButtonVariant.LUMO_ERROR);
    Button removeButtonTeacher = new Button("Remove Teacher");
    removeButtonTeacher.addThemeVariants(ButtonVariant.LUMO_ERROR);
    Button removeButtonClass = new Button("Remove Class");
    removeButtonClass.addThemeVariants(ButtonVariant.LUMO_ERROR);
    //secondaryButton.addThemeVariants(ButtonVariant.LUMO_ERROR);


    School school = new School ("main");


    Grid<Student> gridStudent = new Grid<>(Student.class,false);
    gridStudent.addColumn(Student::getName).setHeader("Student Name");
    gridStudent.addColumn(Student::getId).setHeader("ID");
    gridStudent.setItems(school.getStudents());

    Grid<Teacher> gridTeacher = new Grid<>(Teacher.class,false);
    gridTeacher.addColumn(Teacher::getName).setHeader("Teacher Name");
    gridTeacher.addColumn(Teacher::getId).setHeader("ID");
    gridTeacher.setItems(school.getTeachers());

    Grid<Class> gridClass = new Grid<>(Class.class,false);
    gridClass.addColumn(Class::getName).setHeader("Class Name");
    gridClass.addColumn(Class::getId).setHeader("ID");
    gridClass.setItems(school.getClasses());

    addButtonStudent.addClickListener(click -> {
      school.addStudent(taskFieldStudent.getValue());
      gridStudent.getDataProvider().refreshAll();
    });
    addButtonStudent.addClickShortcut(Key.ENTER);

    addButtonTeacher.addClickListener(click -> {
      school.addTeacher(taskFieldTeacher.getValue());
      gridTeacher.getDataProvider().refreshAll();
    });
    addButtonTeacher.addClickShortcut(Key.ENTER);

    addButtonClass.addClickListener(click -> {
      school.addClass(taskFieldClass.getValue());
      gridClass.getDataProvider().refreshAll();
    });
    addButtonClass.addClickShortcut(Key.ENTER);

    //removals
    removeButtonStudent.addClickListener(click -> {
      school.removeStudent(taskFieldStudent.getValue());
      gridStudent.getDataProvider().refreshAll();
    });
    removeButtonStudent.addClickShortcut(Key.ENTER);

    removeButtonTeacher.addClickListener(click -> {
      school.removeTeacher(taskFieldTeacher.getValue());
      gridTeacher.getDataProvider().refreshAll();
    });
    removeButtonTeacher.addClickShortcut(Key.ENTER);

    removeButtonClass.addClickListener(click -> {
      school.removeClassWithName(taskFieldClass.getValue());
      gridClass.getDataProvider().refreshAll();
    });
    removeButtonClass.addClickShortcut(Key.ENTER);

    add(
      new H1("GradeBook Test"),
      todosList,
      new HorizontalLayout(
        taskFieldStudent,
        addButtonStudent,
        removeButtonStudent
      ),
      new VerticalLayout(
        gridStudent
      ),
      new HorizontalLayout(
        taskFieldTeacher,
        addButtonTeacher,
        removeButtonTeacher
      ),
      new VerticalLayout(
        gridTeacher
      ),
      new HorizontalLayout(
        taskFieldClass,
        addButtonClass,
        removeButtonClass
      ),
      new VerticalLayout(
        gridClass
      )
    );
  }

  @Override
  public void setParameter(BeforeEvent event, String parameter) {
    System.out.println("Hello " + parameter);
  }
}
