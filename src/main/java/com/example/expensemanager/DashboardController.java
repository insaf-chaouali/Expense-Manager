package com.example.expensemanager;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

public class DashboardController implements Initializable {
    private int i;
    @FXML
    private PieChart expense_pie;
    @FXML
    private ProgressBar progress;
    @FXML
    private ProgressBar progress2;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    private Label totalBalance;
    @FXML
    private Label totalExpense;
    @FXML
    private Label totalIncome;
    @FXML
    private HBox firstTransaction;
    @FXML
    private HBox secondTransaction;
    @FXML
    private HBox thirdTransaction;
    @FXML
    private HBox fourthTransaction;
    @FXML
    private Label cause1;
    @FXML
    private Label cause2;
    @FXML
    private Label cause3;
    @FXML
    private Label cause4;
    @FXML
    private Label description1;
    @FXML
    private Label description2;
    @FXML
    private Label description3;
    @FXML
    private Label description4;
    @FXML
    private Label trandate1;
    @FXML
    private Label trandate2;
    @FXML
    private Label trandate3;
    @FXML
    private Label trandate4;
    @FXML
    private Label tranamount1;
    @FXML
    private Label tranamount2;
    @FXML
    private Label tranamount3;
    @FXML
    private Label tranamount4;
    @FXML
    private Label expenseRank1;
    @FXML
    private Label expenseRank2;
    @FXML
    private Label expenseRank3;
    @FXML
    private Label expenseRank4;
    @FXML
    private Label totalPie;
    @FXML
    private ChoiceBox<String> incomesource;
    List<String> lastNineMonths;
    private CategoryAxis xAxis;
    @FXML
    private ChoiceBox<String> pieChoice;
    @FXML
    private ImageView tranSwitch;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            tranSwitch.setCursor(Cursor.HAND);

        firstTransaction.setVisible(false);
        secondTransaction.setVisible(false);
        thirdTransaction.setVisible(false);
        fourthTransaction.setVisible(false);


        TransactionDAO transactionDAO = new TransactionDAO();
        List<Transaction> transactions = transactionDAO.getAllTransactions();
        List<String> expenses = List.of("Food","Groceries","Investment","Transport","Entertainment","Healthcare","Other","Housing","Bills");
        List<String> income = List.of("Salary","Investment Income","Other");


        totalBalance.setText(String.valueOf(TotalBalace(1))+ "DT");
        totalExpense.setText(String.valueOf(TotalExpense(1))+ "DT");
        totalIncome.setText(String.valueOf(TotalIncome(1))+ "DT");


        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Create a list to store the dates of the last 9 months
         lastNineMonths = new ArrayList<>();

        // Start from the current date and go back 9 months
        for (int i = 0; i < 12; i++) {
            LocalDate previousMonth = currentDate.minusMonths(i);
            String monthName = String.valueOf(previousMonth.getMonth());
            String month = Character.toUpperCase(monthName.charAt(0)) + monthName.substring(1,3).toLowerCase();

            lastNineMonths.add(0,month);
        }


        XYChart.Series<String, Number> lineSeries = new XYChart.Series<>();
        lineSeries.setName("Income");
        for(String month : lastNineMonths) {
            lineSeries.getData().add(new XYChart.Data<>(month, TotalIncome(1,month)));
        }

        XYChart.Series<String, Number> lineSeries2 = new XYChart.Series<>();
        lineSeries2.setName("Outcome");
        for(String month : lastNineMonths) {
            lineSeries2.getData().add(new XYChart.Data<>(month, TotalOutcome(1,month)));
        }


        lineChart.getData().addAll(lineSeries, lineSeries2);
        lineChart.setLegendVisible(false);
        lineSeries.getNode().setStyle("-fx-stroke: #2da361;");
        lineSeries2.getNode().setStyle("-fx-stroke: #eb5959;");

        //transaction init
        Iterator<Transaction> iterator = transactionDAO.getAllTransactions().iterator();
        Transaction t1;
        String inStyle="-fx-background-color:#D3EDDE ;-fx-background-radius: 5px;-fx-text-fill: #1a9c52;";
        String outStyle="-fx-background-color:#FBDDDD ;-fx-background-radius: 5px;-fx-text-fill: #ea4f4f;";

        if (iterator.hasNext()) {
            t1 = iterator.next();
            firstTransaction.setVisible(true);
            cause1.setText(t1.getCause());
            description1.setText(t1.getDescription());
            trandate1.setText(t1.getDate().format(DateTimeFormatter.ofPattern("E, dd MMM yyyy")));

            if(t1.getInout().equals("IN")){
                tranamount1.setText("+ "+t1.getAmount().toString()+ " DT");
                tranamount1.setStyle(inStyle);
            }else {
                tranamount1.setText("- "+t1.getAmount().toString()+ " DT");
                tranamount1.setStyle(outStyle);
            }
        }

        if (iterator.hasNext()) {
            t1 = iterator.next();
            secondTransaction.setVisible(true);
            cause2.setText(t1.getCause());
            description2.setText(t1.getDescription());
            trandate2.setText(t1.getDate().format(DateTimeFormatter.ofPattern("E, dd MMM yyyy")));

            if(t1.getInout().equals("IN")){
                tranamount2.setText("+ "+t1.getAmount().toString()+ " DT");
                tranamount2.setStyle(inStyle);
            }else {
                tranamount2.setText("- "+t1.getAmount().toString()+ " DT");
                tranamount2.setStyle(outStyle);
            }
        }
        if (iterator.hasNext()) {
            t1 = iterator.next();
                thirdTransaction.setVisible(true);
            cause3.setText(t1.getCause());
            description3.setText(t1.getDescription());
            trandate3.setText(t1.getDate().format(DateTimeFormatter.ofPattern("E, dd MMM yyyy")));

            if(t1.getInout().equals("IN")){
                tranamount3.setStyle(inStyle);
                tranamount3.setText("+ "+ t1.getAmount().toString()+ " DT");
            }else {
                tranamount3.setText("- "+t1.getAmount().toString()+ " DT");
                tranamount3.setStyle(outStyle);
            }
        }if (iterator.hasNext()) {
            t1 = iterator.next();
            fourthTransaction.setVisible(true);
            cause4.setText(t1.getCause());
            description4.setText(t1.getDescription());
            trandate4.setText(t1.getDate().format(DateTimeFormatter.ofPattern("E, dd MMM yyyy")));

            if(t1.getInout().equals("IN")){
                tranamount4.setStyle(inStyle);
                tranamount4.setText("+ "+t1.getAmount().toString()+ " DT");
            }else {
                tranamount4.setText("- "+t1.getAmount().toString()+ " DT");
                tranamount4.setStyle(outStyle);
            }
        }
        iterator.remove();

        //INCOME SOURCES BARCHART
            // Sample data - replace this with your actual data
            incomesource.setItems(FXCollections.observableArrayList("3 Months","6 Months","9 Months","1 Year"));
            incomesource.getSelectionModel().selectFirst();
            incomesource.setCursor(Cursor.HAND);
            incomesource.setOnAction(this::BarsChoice);

            List<String> lastNmonths = getLastNElements(lastNineMonths, 4);

            ArrayList<Double> salaries = new ArrayList<>();
            ArrayList<Double> other = new ArrayList<>();
            ArrayList<Double> investincome = new ArrayList<>();

            for(String month : lastNmonths) {
                salaries.add(TotalIncome(1,month,"Salary"));
                other.add(TotalIncome(1,month,"Other"));
                investincome.add(TotalIncome(1,month,"Investment"));


            }
            xAxis = (CategoryAxis) barChart.getXAxis();
            NumberAxis yAxis = (NumberAxis) barChart.getYAxis();

            XYChart.Series<String, Number> series1 = new XYChart.Series<>();
            XYChart.Series<String, Number> series2 = new XYChart.Series<>();
            XYChart.Series<String, Number> series3 = new XYChart.Series<>();

            for (int i = 0; i < lastNmonths.size(); i++) {
                series1.getData().add(new XYChart.Data<>(lastNmonths.get(i),investincome.get(i) ));
                series2.getData().add(new XYChart.Data<>(lastNmonths.get(i),salaries.get(i) ));
                series3.getData().add(new XYChart.Data<>(lastNmonths.get(i), other.get(i)));
            }

            series1.setName("Bar 1");
            series2.setName("Bar 2");
            series3.setName("Bar 3");

            barChart.getData().addAll(series1, series2, series3);



            // Customize series colors using CSS
            String series1Style = "-fx-bar-fill: #3c7eff;";
            String series2Style = "-fx-bar-fill: #094cd1; ";
            String series3Style = "-fx-bar-fill: #83acfd;";

            // Apply styles to each series node
            applyStyleToSeries(series1, "-fx-bar-fill: #3c7eff;");
            applyStyleToSeries(series2, "-fx-bar-fill: #094cd1;");
            applyStyleToSeries(series3, "-fx-bar-fill: #83acfd;");


            //PIE CHART
            List<ExpenseAnalyzer> expenseAnalyzers = TransactionDAO.analyzeExpenses(9);
            pieChoice.setCursor(Cursor.HAND);
            pieChoice.setItems(FXCollections.observableArrayList("Last month","3 Months","9 Months","1 Year"));
            pieChoice.getSelectionModel().select(2);
            pieChoice.setOnAction(this::PieAct);

            PieChart.Data slice1 = new PieChart.Data(expenseAnalyzers.get(0).getCause(),expenseAnalyzers.get(0).getSum() );
            PieChart.Data slice2 = new PieChart.Data(expenseAnalyzers.get(1).getCause(), expenseAnalyzers.get(1).getSum());
            PieChart.Data slice3 = new PieChart.Data(expenseAnalyzers.get(2).getCause(), expenseAnalyzers.get(2).getSum());
            PieChart.Data slice4 = new PieChart.Data(expenseAnalyzers.get(3).getCause(), expenseAnalyzers.get(3).getSum());

            expenseRank1.setText(expenseAnalyzers.get(0).getCause());
            expenseRank2.setText(expenseAnalyzers.get(1).getCause());
            expenseRank3.setText(expenseAnalyzers.get(2).getCause());
            expenseRank4.setText(expenseAnalyzers.get(3).getCause());

            Double expSumPie = 0.0;
            for (int i=0; i<4;i++)expSumPie+=expenseAnalyzers.get(i).getSum();
            totalPie.setText(expSumPie.toString()+" DT");

            expense_pie.getData().addAll(slice1, slice2, slice3,slice4);
            expense_pie.setLegendVisible(false);
            expense_pie.setLabelsVisible(false);
            slice1.getNode().setStyle("-fx-pie-color: #094cd1;");
            slice2.getNode().setStyle("-fx-pie-color: #3c7eff;");
            slice3.getNode().setStyle("-fx-pie-color: #83acfd;");
            slice4.getNode().setStyle("-fx-pie-color: #cfdefd;");
            expense_pie.setStyle("-fx-chart-pie-stroke: red;");


            //PROGRESS BAR
            progress.setProgress(TotalBalace(1)/10000);
            progress2.setProgress(TotalBalace(1)/28000);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void PieAct(ActionEvent actionEvent) {

        String selectedOption = pieChoice.getValue();
        expense_pie.getData().clear();
        int n = 3;
        switch (selectedOption) {
            case "Last months":
                n = 1;
                break;
            case "3 Months":
                n = 3;
                break;
            case "6 Months":
                n = 6;
                break;
            case "9 Months":
                n = 9;
                break;
            case "1 Year":
                n = 12;
                break;
        }
        List<ExpenseAnalyzer> expenseAnalyzers = TransactionDAO.analyzeExpenses(n);
        PieChart.Data slice1 = new PieChart.Data(expenseAnalyzers.get(0).getCause(),expenseAnalyzers.get(0).getSum() );
        PieChart.Data slice2 = new PieChart.Data(expenseAnalyzers.get(1).getCause(), expenseAnalyzers.get(1).getSum());
        PieChart.Data slice3 = new PieChart.Data(expenseAnalyzers.get(2).getCause(), expenseAnalyzers.get(2).getSum());
        PieChart.Data slice4 = new PieChart.Data(expenseAnalyzers.get(3).getCause(), expenseAnalyzers.get(3).getSum());

        expenseRank1.setText(expenseAnalyzers.get(0).getCause());
        expenseRank2.setText(expenseAnalyzers.get(1).getCause());
        expenseRank3.setText(expenseAnalyzers.get(2).getCause());
        expenseRank4.setText(expenseAnalyzers.get(3).getCause());

        Double expSumPie = 0.0;
        for (int i=0; i<4;i++)expSumPie+=expenseAnalyzers.get(i).getSum();
        totalPie.setText(expSumPie.toString()+" DT");

        expense_pie.getData().addAll(slice1, slice2, slice3,slice4);
        expense_pie.setLegendVisible(false);
        expense_pie.setLabelsVisible(false);
        slice1.getNode().setStyle("-fx-pie-color: #094cd1;");
        slice2.getNode().setStyle("-fx-pie-color: #3c7eff;");
        slice3.getNode().setStyle("-fx-pie-color: #83acfd;");
        slice4.getNode().setStyle("-fx-pie-color: #cfdefd;");
        expense_pie.setStyle("-fx-chart-pie-stroke: red;");

    }

    private void applyStyleToSeries(XYChart.Series<String, Number> series, String style) {
        for (XYChart.Data<String, Number> data : series.getData()) {
            data.getNode().setStyle(style);
        }
    }
    private static Double TotalBalace(int id) throws SQLException {
        Double sum = 0.0;
        TransactionDAO transactionDAO = new TransactionDAO();
        List<Transaction> transactions = transactionDAO.getAllTransactions();
        for (Transaction transaction1 : transactions) {
            if (transaction1.getUserId() == id) {
                if(transaction1.getInout().equals("IN")){
                sum += transaction1.getAmount();}
                else if(transaction1.getInout().equals("OUT")){
                    sum -= transaction1.getAmount();
                }
            }
        }
        return sum;

    }
    private static Double TotalIncome(int id) throws SQLException {
        Double sum = 0.0;
        TransactionDAO transactionDAO = new TransactionDAO();

        List<Transaction> transactions = transactionDAO.getAllTransactions();
        for (Transaction transaction1 : transactions) {
            if (transaction1.getUserId() == id && transaction1.getInout().equals("IN") ) {
                sum += transaction1.getAmount();
            }
        }
        return sum;

    }
    private static Double TotalIncome(int id,String month) throws SQLException {
        Double sum = 0.0;
        String month1;
        TransactionDAO transactionDAO = new TransactionDAO();
        List<Transaction> transactions = transactionDAO.getAllTransactions();
        for (Transaction transaction1 : transactions) {
            month1 = transaction1.getDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.US);
            if (transaction1.getUserId() == id && transaction1.getInout().equals("IN") && month1.equals(month)) {
                sum += transaction1.getAmount();
            }
        }
        return sum;

    }
    private static Double TotalIncome(int id,String month,String cause) throws SQLException {
        Double sum = 0.0;
        String month1;
        TransactionDAO transactionDAO = new TransactionDAO();
        List<Transaction> transactions = transactionDAO.getAllTransactions();
        for (Transaction transaction1 : transactions) {
            month1 = transaction1.getDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.US);
            if (transaction1.getUserId() == id && transaction1.getInout().equals("IN") && month1.equals(month) && transaction1.getCause().equals(cause)) {
                sum += transaction1.getAmount();
            }
        }
        return sum;

    }

    private static Double TotalExpense(int id) throws SQLException {
        Double sum = 0.0;
        TransactionDAO transactionDAO = new TransactionDAO();
        List<Transaction> transactions = transactionDAO.getAllTransactions();
        for (Transaction transaction1 : transactions) {
            if (transaction1.getUserId() == id && transaction1.getInout().equals("OUT") ) {
                sum += transaction1.getAmount();
            }
        }
        return sum;

    }
    private static Double TotalOutcome(int id,String month) throws SQLException {
        Double sum = 0.0;
        String month1;
        TransactionDAO transactionDAO = new TransactionDAO();
        List<Transaction> transactions = transactionDAO.getAllTransactions();
        for (Transaction transaction1 : transactions) {
            month1 = transaction1.getDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.US);
            if (transaction1.getUserId() == id && transaction1.getInout().equals("OUT") && month1.equals(month)) {
                sum += transaction1.getAmount();
            }
        }
        return sum;

    }
    public static List<String> getLastNElements(List<String> originalList, int n) {
        int startIndex = Math.max(originalList.size() - n, 0);
        return new ArrayList<>(originalList.subList(startIndex, originalList.size()));
    }
    public void BarsChoice(ActionEvent actionEvent) {
        String selectedOption = incomesource.getValue();
        barChart.getData().clear();
        int n = 3;
        switch (selectedOption) {
            case "Last month":
                n = 3;
                break;
            case "3 Months":
                n = 3;
                break;
            case "6 Months":
                n = 6;
                break;
            case "9 Months":
                n = 9;
                break;
            case "1 Year":
                n = 11;
                break;
        }
        ArrayList<Double> salaries = new ArrayList<>();
        ArrayList<Double> other = new ArrayList<>();
        ArrayList<Double> investincome = new ArrayList<>();

        List<String> lastNmonths = getLastNElements(lastNineMonths, n);
        try {
            for (String month : lastNmonths) {
                salaries.add(TotalIncome(1, month, "Salary"));
                other.add(TotalIncome(1, month, "Other"));
                investincome.add(TotalIncome(1, month, "Investment"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error handling choice", e);
        }

        // Clear existing data
        barChart.getData().clear();

        // Create series
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        XYChart.Series<String, Number> series3 = new XYChart.Series<>();

        // Add data to series
        for (int i = 0; i < lastNmonths.size(); i++) {
            series1.getData().add(new XYChart.Data<>(lastNmonths.get(i), investincome.get(i)));
            series2.getData().add(new XYChart.Data<>(lastNmonths.get(i), salaries.get(i)));
            series3.getData().add(new XYChart.Data<>(lastNmonths.get(i), other.get(i)));
        }

        // Set series names
        series1.setName("Investment");
        series2.setName("Salary");
        series3.setName("Other");

        // Add series to chart
        barChart.getData().addAll(series1, series2, series3);

        // Customize series colors using CSS
        String series1Style = "-fx-bar-fill: #3c7eff;";
        String series2Style = "-fx-bar-fill: #094cd1; ";
        String series3Style = "-fx-bar-fill: #83acfd;";

        // Apply styles to each series node
        applyStyleToSeries(series1, "-fx-bar-fill: #3c7eff;");
        applyStyleToSeries(series2, "-fx-bar-fill: #094cd1;");
        applyStyleToSeries(series3, "-fx-bar-fill: #83acfd;");

        // Adjust the margins around the plot area
        barChart.setAnimated(false); // Disable animation to prevent chart layout issues
        double margin = 20; // Adjust the margin value as needed
        barChart.setPadding(new Insets(margin, margin, margin, margin));
    }

    public void actSwitch(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("CRUD.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

}