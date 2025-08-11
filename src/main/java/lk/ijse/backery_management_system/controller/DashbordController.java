package lk.ijse.backery_management_system.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashbordController implements Initializable {
    @FXML
    public AnchorPane ancDashbordloadPage;
    public ComboBox cmbOverView;
    public Button btnOverView;
    public AnchorPane ancOverView;
    public Label lblOrderPlace;
    public Label lblProduct;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadPageNames();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Page not found..!").show();
        }
    }

    public void navigateTo2(String path){
        try {
            ancOverView.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancDashbordloadPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancDashbordloadPage.heightProperty());

            ancOverView.getChildren().setAll(anchorPane);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Page not found..!").show();
            e.printStackTrace();
        }
    }

    private void navigateTo(String path) {
        try {
            ancDashbordloadPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancDashbordloadPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancDashbordloadPage.heightProperty());

            ancDashbordloadPage.getChildren().add(anchorPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Page not found..!").show();
            e.printStackTrace();
        }
    }




    private void loadPageNames(){
        try {
            cmbOverView.setItems(FXCollections.observableArrayList("Customer Page","Employee Page","Feedback Page","Ingredients Page", "Inventory Page", "Invoice Page", "Item Page", "Order Page", "Orderdetails Page", "Payment Page", "Product Page", "Supplier Page", "Users Page"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbOverViewPagesOnAction(ActionEvent actionEvent) {
        try {

        String selectedPage = (String) cmbOverView.getSelectionModel().getSelectedItem();
        if (selectedPage != null) {
            switch (selectedPage) {
                case "Customer Page" -> navigateTo("/view/CustomerPage.fxml");
                case "Employee Page" -> navigateTo("/view/EmployeeAttendnce.fxml");
                case "Feedback Page" -> navigateTo("/view/FeedbackPage.fxml");
                case "Ingredients Page" -> navigateTo("/view/IngredientPage.fxml");
                case "Inventory Page" -> navigateTo("/view/InventoryPage.fxml");
                case "Invoice Page" -> navigateTo("/view/InvoicePage .fxml");
                case "Item Page" -> navigateTo("/view/Itempage.fxml");
                case "Order Page" -> navigateTo("/view/OrderPage.fxml");
                case "Orderdetails Page" -> navigateTo("/view/OrderDetailsPage.fxml");
                case "Payment Page" -> navigateTo("/view/PaymentPage.fxml");
                case "Product Page" -> navigateTo("/view/ProductPage.fxml");
                case "Supplier Page" -> navigateTo("/view/SupplierPage.fxml");
                case "Users Page" -> navigateTo("/view/UserPage.fxml");

            }
        }
        }catch (Exception e){
        e.printStackTrace();
        new Alert(Alert.AlertType.ERROR, "Page not found..!").show();
        }
    }

    public void btnOverViewOnAction(ActionEvent actionEvent) {
        navigateTo("/view/ButtonSet.fxml");
    }

    public void onClickedLbl(MouseEvent mouseEvent) {
        navigateTo("/view/OrderPlacement.fxml");
    }

    public void onClickedProduct(MouseEvent mouseEvent) {
    }
}





