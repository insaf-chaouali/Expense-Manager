package com.example.expensemanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

public class CreateController {
    @FXML
    private Label confirmpasswordlabel;
    @FXML
    private Label registrationMessagelabel;

    @FXML
    private Button Register;

    @FXML
    private Button back;

    @FXML
    private TextField name;

    @FXML
    private PasswordField pass;

    @FXML
    private PasswordField confirm;

    @FXML
    private TextField firstnameText;

    @FXML
    private TextField lastnameText;


    public void Back(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();;
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
 public void registerbuttonAction(ActionEvent event){

     if(confirm.getText().equals(pass.getText())){
         registerUser();
         confirmpasswordlabel.setText(" ");

     } else{
         confirmpasswordlabel.setText("Password does not match");
     }
 }
 public void registerUser(){
    DatabaseConnection connectionNow =new DatabaseConnection();
    Connection connectDB = connectionNow.getConnection();


     String firstname = firstnameText.getText();
     String lastname=lastnameText.getText();
    String username =name.getText();
    String password=pass.getText();

    String insertFields="INSERT INTO USER_account(lastname,firstname,username,password)VALUES('";
    String insertValues=firstname+" ','"+lastname+" ','"+username+" ','"+password+")'";
    String insertToRegister = insertFields + insertValues;
 try{
     Statement statement= connectDB.createStatement();
     statement.executeUpdate(insertToRegister);
     registrationMessagelabel.setText("user has been registered successfully!");
 } catch(Exception e){
     e.printStackTrace();
     e.getCause();
 }
 }
}
