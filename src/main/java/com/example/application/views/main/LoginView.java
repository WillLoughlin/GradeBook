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


@Route("")
@PageTitle("GradeBook Login")

public class LoginView extends VerticalLayout {
  //LoginForm login = new LoginForm();

  public LoginView() {
    //addClassName("login-view");
    setSizeFull();

    setAlignItems(Alignment.CENTER);


    //login.setAction("login");

    //login.setForgotPassword("Register");
    TextField username = new TextField();
    username.setPlaceholder("Username");
    TextField password = new TextField();
    password.setPlaceholder("Password");
    Button login = new Button("Login");


    login.addClickListener(click -> {
      boolean error_notification = true;
      School school = new School("school");
      String u = username.getValue();
      String p = password.getValue();
      if (!p.equals("") && !u.equals("")) {
        String response = school.checkUser(u,p);
        if (response.equals("Student")) {
          error_notification = false;
          //navigate to student page
          UI.getCurrent().navigate("student/" + username.getValue());
        } else if (response.equals("Teacher")) {
          error_notification = false;
          //navigate to teacher page
          UI.getCurrent().navigate("teacher/" + username.getValue());
        }
      }
      if (error_notification){
        Notification login_error = new Notification("warning");
        login_error.addThemeVariants(NotificationVariant.LUMO_ERROR);
        login_error.show("Invalid Username and Password",5000,Notification.Position.MIDDLE);
      }

    });
    Button registerTeacher = new Button("Register Teacher");
    registerTeacher.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    registerTeacher.addThemeVariants(ButtonVariant.LUMO_SMALL);
    Button registerStudent = new Button("Register Student");
    registerStudent.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    registerStudent.addThemeVariants(ButtonVariant.LUMO_SMALL);


    registerTeacher.addClickListener(click -> {
      //no ""
      String u = username.getValue();
      String p = password.getValue();
      School school = new School("school");
      if (!p.equals("") && !u.equals("")) {
        //valid
        if (school.checkValidRegister(u)) {
          school.addTeacher(u);
          Teacher t = school.getTeacherWithName(u);
          t.setUsername(u);
          t.setPassword(p);
          school.save();
          Notification login_error = new Notification("warning");
          login_error.addThemeVariants(NotificationVariant.LUMO_ERROR);
          login_error.show(u + " registered successfully as Teacher",5000,Notification.Position.MIDDLE);
        } else {
          Notification login_error = new Notification("warning");
          login_error.addThemeVariants(NotificationVariant.LUMO_ERROR);
          login_error.show("Username already taken",5000,Notification.Position.MIDDLE);
        }
      } else {
        Notification login_error = new Notification("warning");
        login_error.addThemeVariants(NotificationVariant.LUMO_ERROR);
        login_error.show("Must have values for Username and Password to register",5000,Notification.Position.MIDDLE);
      }
    });

      registerStudent.addClickListener(click -> {
        //no ""
        String u = username.getValue();
        String p = password.getValue();
        School school = new School("school");
        if (!p.equals("") && !u.equals("")) {
          //valid
          if (school.checkValidRegister(u)) {
            school.addStudent(u);
            Student t = school.getStudentWithName(u);
            t.setUsername(u);
            t.setPassword(p);
            school.save();
            Notification login_error = new Notification("warning");
            login_error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            login_error.show(u + " registered successfully as Student",5000,Notification.Position.MIDDLE);
          } else {
            Notification login_error = new Notification("warning");
            login_error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            login_error.show("Username already taken",5000,Notification.Position.MIDDLE);
          }
        } else {
          Notification login_error = new Notification("warning");
          login_error.addThemeVariants(NotificationVariant.LUMO_ERROR);
          login_error.show("Must have values for Username and Password to register",5000,Notification.Position.MIDDLE);
        }

    });


    add(
      new H1("GradeBook"),
      username,
      password,
      login,
      new HorizontalLayout(
        registerTeacher,
        registerStudent
      )
    );
  }
}
