package com.example.expensemanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class HelloController {

    @FXML
    private PasswordField PasswordUser;

    @FXML
    private Label Sign;

    @FXML
    private TextField UserName;

    @FXML
    private Hyperlink forget;

    @FXML
    private Button login;

    @FXML
    private CheckBox showPass;

    @FXML
    private Label title;

    @FXML
    private TextField textfiledPassword;

    @FXML
    private Button cancelbutton;

    @FXML
    private Label loginMessagelabel;

    @FXML
    private void checkboxAction(ActionEvent event) {
        if (showPass.isSelected()) {
            // Show password
            textfiledPassword.setText(PasswordUser.getText());
            textfiledPassword.setVisible(true);
            PasswordUser.setVisible(false);
            return;// Disable cache
        }
        PasswordUser.setText(textfiledPassword.getText()); // Enable cache
        PasswordUser.setVisible(true);
        textfiledPassword.setVisible(false);// Force refresh to update cache
    }

    public void create(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("test.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    public void cancelbuttonOnAction(ActionEvent event){
        Stage stage=(Stage) cancelbutton.getScene().getWindow();
        stage.close();
    }

    public void Forget(ActionEvent event) throws  IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("forget.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Forget!");
        stage.setScene(scene);
        stage.show();
    }
    public void loginButtonOnAction(ActionEvent event){
        if(UserName.getText().isBlank() == false && PasswordUser.getText().isBlank() == false){
        validatelogin(event);
        } else
        {
    loginMessagelabel.setText("Please enter username and Password");
        }
    }
    public void validatelogin(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifylogin = "SELECT count(1) FROM user where name='" + UserName.getText() + "' AND password='" + PasswordUser.getText() + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifylogin);
            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    loginMessagelabel.setText("Congratulations!");
                    FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("hello-view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                } else {
                    loginMessagelabel.setText("Invalid login.Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }}
