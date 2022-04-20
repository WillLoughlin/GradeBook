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
    add(new H1("Class view: " + this.class_name));
  }
}
