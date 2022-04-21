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
import java.util.Optional;
import com.vaadin.flow.component.html.Span;


@Route("student")
@PageTitle("Student Portal")

public class StudentView extends VerticalLayout implements HasUrlParameter<String>{
  String username;

  public StudentView() {
    //let set parameter happen, it calls buildView
  }

  @Override
  public void setParameter(BeforeEvent event, String parameter) {
    //System.out.println("Hello " + parameter);
    this.username = parameter;
    //System.out.println("Username: " + this.username);
    buildView();
  }

  public void buildView() {
    setAlignItems(Alignment.CENTER);

    School school = new School("school");
    //System.out.println("Looking for self with username: " + this.username);
    Student self = school.getStudentWithUsername(this.username);
    //setSizeFull();
    //setAlignItems(Alignment.CENTER);

     Span gridTitle = new Span("Select a Class");
     gridTitle.getStyle().set("font-weight", "bold");

    for (int i = 0; i < self.getClasses().size(); i++) {
      self.getClasses().get(i).setCurrentStudent(self);
    }
    Grid<Class> grid = new Grid<>(Class.class, false);
    grid.addColumn(Class::getName).setHeader("Class Name");
    grid.addColumn(Class::getCurrentStudentGrade).setHeader("ID");

    grid.setItems(self.getClasses());
    grid.setWidth("400px");
    //grid.setWidth("40%");
    //grid.setHeight("40%");

    grid.addSelectionListener(selection -> {
      Optional<Class> optionalClass = selection.getFirstSelectedItem();
      if(optionalClass.isPresent()) {
        //System.out.println(optionalClass.get().getName());
        UI.getCurrent().navigate("student-class/" + this.username+"-"+optionalClass.get().getName());
      }
    });

    Button back = new Button("Log Out");
    back.addClickListener(click -> {
      UI.getCurrent().navigate("");
    });

    add(
      new H1("Student Portal"),
      gridTitle,
      new HorizontalLayout(grid),
      back
    );
  }
}
