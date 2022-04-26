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

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.page.Page;


@Route("teacher")
@PageTitle("Teacher Portal")

public class TeacherView extends VerticalLayout implements HasUrlParameter<String> {
  String username;

  public TeacherView() {
    //add(new H1("Teacher Portal"));
  }

  @Override
  public void setParameter(BeforeEvent event, String parameter) {
    this.username = parameter;
    //System.out.println("Username: " + this.username);
    buildView();
  }

  public void buildView() {
    setAlignItems(Alignment.CENTER);

    School school = new School("school");
    //System.out.println("Looking for self with username: " + this.username);
    Teacher self = school.getTeacherWithUsername(this.username);
    //System.out.println("name: " + self.getName());



    Span gridTitle = new Span("Select a Class");
    gridTitle.getStyle().set("font-weight", "bold");

   Grid<Class> grid = new Grid<>(Class.class, false);
   grid.addColumn(Class::getName).setHeader("Class Name");
   // grid.addColumn(Class::getId).setHeader("ID");
   grid.addColumn(Class::getNumStudents).setHeader("# of Students");


   grid.setItems(self.getClasses());
   //grid.setWidth("40%");
   //grid.setHeight("40%");
   grid.setWidth("400px");

   grid.addSelectionListener(selection -> {
     Optional<Class> optionalClass = selection.getFirstSelectedItem();
     if(optionalClass.isPresent()) {
       //System.out.println(optionalClass.get().getName());
       UI.getCurrent().navigate("teacher-class/" + this.username+"-"+optionalClass.get().getName());
     }
   });

   TextField taskFieldClass = new TextField("Class Name");
   Button addClassButton = new Button("Add Class");
   Button removeButtonClass = new Button("Remove Class");
   removeButtonClass.addThemeVariants(ButtonVariant.LUMO_ERROR);

   addClassButton.addClickListener(click -> {
     String className = taskFieldClass.getValue();
     if (!className.equals("")) {
       if (school.validClassName(className)) {
         school.addClass(className);
         Class added = school.getClassWithName(className);
         //added.setTeacher(self);
         school.setTeacherToClass(added,self);
         school.save();
         //grid.setItems(self.getClasses());
         grid.getDataProvider().refreshAll();
         notify(className + " added to classes for " + self.getName());
       } else {
         notify(className + " already taken");
       }
     }
   });

   removeButtonClass.addClickListener(click -> {
     String className = taskFieldClass.getValue();
     if (!className.equals("")) {
       if (school.isClassName(className,self)) {
         // school.addClass(className);
         // Class added = school.getClassWithName(className);
         // school.setTeacherToClass(added,self);
         // school.save();
         // grid.getDataProvider().refreshAll();
         school.removeClassWithName(className);
         grid.setItems(self.getClasses());
         notify(className + " removed from classes for " + self.getName());
       } else {
         notify(className + " not found in database");
       }
     }
   });

   Button back = new Button("Log Out");
   back.addClickListener(click -> {
     UI.getCurrent().navigate("");
   });

   //TO DO: Add remove class functionality
   //-delete assignments with class
   //remove class from student's lists



   add(
     new H1("Teacher Portal"),
     gridTitle,
     new HorizontalLayout(grid),
     taskFieldClass,
     new HorizontalLayout(addClassButton,removeButtonClass),
     back
   );
  }

  public void notify(String n) {
    Notification notif = new Notification("notify");
    notif.show(n,5000,Notification.Position.MIDDLE);
  }
}
