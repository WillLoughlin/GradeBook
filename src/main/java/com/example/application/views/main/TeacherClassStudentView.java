package com.example.application.views.main;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
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
import com.vaadin.flow.component.dialog.Dialog;
import java.util.Optional;
import com.vaadin.flow.component.orderedlayout.FlexComponent;





@Route("teacher-class-student")
@PageTitle("Class Student Portal")

public class TeacherClassStudentView extends VerticalLayout implements HasUrlParameter<String> {

  String teacher_username;
  String class_name;
  String student_username;
  Assignment curr_ass;

  public TeacherClassStudentView() {
    //setParameter needs to run
  }

  @Override
  public void setParameter(BeforeEvent event, String parameter) {
    System.out.println("teacher-class-student: " + parameter);
    String[] split = parameter.split("-");
    this.teacher_username = split[0];
    this.class_name = split[1];
    this.student_username = split[2];
    buildView();
  }

  public void buildView() {

    setAlignItems(Alignment.CENTER);

    School school = new School("school");
    Teacher t = school.getTeacherWithUsername(teacher_username);
    Class c = school.getClassWithName(class_name);
    Student s = school.getStudentWithUsername(student_username);


    Span grid_title = new Span("Grades for " + s.getName() + " in " + class_name);
    grid_title.getStyle().set("font-weight", "bold");

    ArrayList<Assignment> ass = s.getAssForClass(c);
    Grid<Assignment> ass_grid = new Grid<>(Assignment.class,false);
    ass_grid.addColumn(Assignment::getName).setHeader("Assignment");
    ass_grid.addColumn(Assignment::getPointsPoss).setHeader("Points Possible");
    ass_grid.addColumn(Assignment::getPointsEarn).setHeader("Points Earned");
    ass_grid.addColumn(Assignment::getGradeStr).setHeader("Grade");

    ass_grid.setItems(ass);
    ass_grid.setWidth("700px");




    //from: https://vaadin.com/docs/latest/ds/components/dialog
    Dialog dialog = new Dialog();

    TextField pointsPossField = new TextField("Points Possible");
    TextField pointsEarnField = new TextField("Points Earned");

    VerticalLayout dialogLayout = new VerticalLayout(pointsPossField,
            pointsEarnField);
    dialogLayout.setPadding(false);
    dialogLayout.setSpacing(false);
    dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
    dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

    //dialog.setHeaderTitle("Edit Assignment");

    // VerticalLayout dialogLayout = createDialogLayout();
    dialog.add(new H3("Edit Grade"));
    dialog.add(dialogLayout);

    Button cancelButton = new Button("Cancel");
    Button saveButton = new Button("Save");
    saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    Button deleteButton = new Button("Delete");
    deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

    // dialog.getFooter().add(cancelButton);
    // dialog.getFooter().add(saveButton);
    dialog.add(new HorizontalLayout(cancelButton,saveButton,deleteButton));

    saveButton.addClickListener(click -> {
      // System.out.println(pointsPossField.getValue());
      // System.out.println(pointsEarnField.getValue());
      pointsPossField.setValue(String.valueOf(curr_ass.getPointsPoss()));
      pointsEarnField.setValue(String.valueOf(curr_ass.getPointsEarn()));
      double ptsPoss = Double.parseDouble(pointsPossField.getValue());
      double ptsEarn = Double.parseDouble(pointsEarnField.getValue());
      school.updateAss(curr_ass,ptsPoss,ptsEarn);
      dialog.close();
      ass_grid.setItems(s.getAssForClass(c));
    });

    deleteButton.addClickListener(click -> {
      school.deleteAss(curr_ass);
      dialog.close();
      ass_grid.setItems(s.getAssForClass(c));
    });

    cancelButton.addClickListener(click -> {
      dialog.close();
    });

    ass_grid.addSelectionListener(selection -> {
      Optional<Assignment> optionalAss = selection.getFirstSelectedItem();
      if(optionalAss.isPresent()) {
        dialog.open();
        curr_ass = optionalAss.get();
        //System.out.println(optionalAss.get().getUsername());
        //UI.getCurrent().navigate("teacher-class-student/" + this.teacher_username+"-"+this.class_name+"-"+optionalStu.get().getUsername());
      }
    });

    Dialog dialogAdd = new Dialog();

    TextField aname = new TextField("Assignment Name");
    TextField pointsPossField2 = new TextField("Points Possible");
    TextField pointsEarnField2 = new TextField("Points Earned");

    VerticalLayout dialogLayoutAdd = new VerticalLayout(aname,pointsPossField2,
            pointsEarnField2);
    dialogLayoutAdd.setPadding(false);
    dialogLayoutAdd.setSpacing(false);
    dialogLayoutAdd.setAlignItems(FlexComponent.Alignment.STRETCH);
    dialogLayoutAdd.getStyle().set("width", "18rem").set("max-width", "100%");

    dialogAdd.add(new H3("Add Assignment"));
    dialogAdd.add(dialogLayoutAdd);

    Button cButton = new Button("Cancel");
    Button aButton = new Button("Add");
    aButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

    dialogAdd.add(new HorizontalLayout(cButton,aButton));

    Button add_ass = new Button("Add Assignment");
    add_ass.addClickListener(click -> {
      dialogAdd.open();
    });

    cButton.addClickListener(click -> {
      dialogAdd.close();
    });

    aButton.addClickListener(click -> {
      String n = aname.getValue();
      double pPoss = Double.parseDouble(pointsPossField2.getValue());
      double pEarn = Double.parseDouble(pointsEarnField2.getValue());
      System.out.println("Adding assingment " + n);
      school.addAssignmentPts(n, c,t,s, pPoss, pEarn);
      dialogAdd.close();
      ass_grid.setItems(s.getAssForClass(c));
    });



    Button back = new Button("Back");
    back.addClickListener(click -> {
      UI.getCurrent().navigate("teacher-class/" + teacher_username + "-" + class_name);
    });


    add(
      new H1("Teacher Portal: " + this.class_name + ", " + this.student_username),
      grid_title,
      new HorizontalLayout(ass_grid),
      add_ass,
      back
    );

  }
  public void notify(String n) {
    Notification notif = new Notification("notify");
    notif.show(n,5000,Notification.Position.MIDDLE);
  }
}
