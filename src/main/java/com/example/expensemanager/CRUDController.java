package com.example.expensemanager;

import com.example.expensemanager.Transaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CRUDController implements Initializable {
    @FXML
    private ImageView Homebutt;
    @FXML
    private TableView<Transaction> table;
    @FXML
    private TableColumn<Transaction, String> inoutCol;
    @FXML
    private TableColumn<Transaction, String> causeCol;
    @FXML
    private TableColumn<Transaction, String> descCol;
    @FXML
    private TableColumn<Transaction, LocalDate> dateCol;
    @FXML
    private TableColumn<Transaction, Double> amountCol;
    @FXML
    private TableColumn<Transaction, Double> idCol;
    @FXML
    private RadioButton inRadio;
    @FXML
    private RadioButton outRadio;
    private ToggleGroup group;
    @FXML
    private TextField descField;
    @FXML
    private ChoiceBox<String> causeChoice;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField amountField;
    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    private int idTrans = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Homebutt.setCursor(Cursor.HAND);

        addButton.setCursor(Cursor.HAND);
        editButton.setCursor(Cursor.HAND);
        deleteButton.setCursor(Cursor.HAND);

        // Create a ToggleGroup and add the radio buttons to it
        group = new ToggleGroup();
        inRadio.setToggleGroup(group);
        outRadio.setToggleGroup(group);

        // Add listener to update the label when the selection changes
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RadioButton selectedRadioButton = (RadioButton) newValue;
                if(selectedRadioButton.getText().equals("IN")){
                    causeChoice.getItems().clear();
                    causeChoice.setItems(FXCollections.observableArrayList("Salary","Investment Income","Other"));
                    causeChoice.getSelectionModel().selectFirst();
                }else {
                    causeChoice.getItems().clear();
                    causeChoice.setItems(FXCollections.observableArrayList("Food","Groceries","Investment","Transport","Entertainment","Healthcare","Other","Housing","Bills"));
                    causeChoice.getSelectionModel().selectFirst();
                }

                }

        });


        // Set cell value factories using PropertyValueFactory
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        inoutCol.setCellValueFactory(new PropertyValueFactory<>("inout"));
        causeCol.setCellValueFactory(new PropertyValueFactory<>("cause"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        // Populate table
        List<Transaction> transactions;
        try {
            transactions = TransactionDAO.getAllTransactions();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ObservableList<Transaction> trans = FXCollections.observableArrayList(transactions);
        table.setItems(trans);
        // Add event listener for row selection
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Update input fields with the data from the selected row
                Transaction selectedTransaction = table.getSelectionModel().getSelectedItem();
                if (selectedTransaction != null) {
                    descField.setText(selectedTransaction.getDescription());
                    causeChoice.setValue(selectedTransaction.getCause());
                    datePicker.setValue(selectedTransaction.getDate());
                    amountField.setText(String.valueOf(selectedTransaction.getAmount()));
                    idTrans = selectedTransaction.getId();
                    // Check the inRadio or outRadio based on the inout value
                    if (selectedTransaction.getInout().equals("IN")) {
                        inRadio.setSelected(true);
                    } else if (selectedTransaction.getInout().equals("OUT")) {
                        outRadio.setSelected(true);
                    }
                }
            }
        });
    }
    public void deleteButton(ActionEvent actionEvent) {
        try{
            TransactionDAO.deleteTransaction(idTrans);
        }catch(SQLException e){
            e.printStackTrace();
        }
        table.refresh();
    }
    public void updateButton(ActionEvent actionEvent) {
        Transaction transaction = new Transaction();
        transaction.setId(idTrans);
        transaction.setCause(causeChoice.getValue());
        transaction.setDate(datePicker.getValue());
        transaction.setAmount(Double.parseDouble(amountField.getText()));
        if(inRadio.isSelected()){
            transaction.setInout("IN");
        }else if(outRadio.isSelected()){
            transaction.setInout("OUT");
        }
        table.refresh();
        try {

            TransactionDAO.updateTransaction(transaction);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public void addButton(ActionEvent event) {
        Transaction transaction = new Transaction();
        if(inRadio.isSelected()){
            transaction.setInout("IN");
        }else if(outRadio.isSelected()){
            transaction.setInout("OUT");
        }
        transaction.setCause(causeChoice.getSelectionModel().getSelectedItem());
        transaction.setDescription(descField.getText());
        transaction.setDate(LocalDate.parse(datePicker.getValue().toString()));
        transaction.setUserId(1);
        transaction.setAmount(Double.parseDouble(amountField.getText()));

        System.out.println(transaction.getCause()+" "+transaction.getDescription()+" "+transaction.getDate()+" "+transaction.getAmount());

        TransactionDAO transactionDAO = new TransactionDAO();
        try {
            transactionDAO.createTransaction(transaction);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        table.refresh();

        List<Transaction> transactions;
        try {
            transactions = TransactionDAO.getAllTransactions();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void transSwitch(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
