package lk.ijse.backery_management_system.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.bakerymanagment.dto.OrderDto;
import lk.ijse.bakerymanagment.dto.tm.OrderDetailsTM;
import lk.ijse.bakerymanagment.dto.tm.OrderTM;
import lk.ijse.bakerymanagment.model.OrderModel;


import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrderController implements Initializable {

    public AnchorPane ancOrder;
    public Text lblOrderId;
    public TextField txtCustomerId;
    public TextField txtOrderDate;
    public TextField txtStatus;
    public TextField txtTotalAmmount;

    public TableView<OrderTM> tblOrder;
    public TableColumn<OrderTM , String> colOrderId;
    public TableColumn<OrderTM , String> colCustomerId;
    public TableColumn<OrderTM , String> colOrderDate;
    public TableColumn<OrderTM , String> colStatus;
    public TableColumn<OrderTM , String> colTotalAmmount;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public Button txtBack;

    private final OrderModel orderModel = new OrderModel();

    public TextField txtSearch;
    public ComboBox cmbStatus;
    public DatePicker ordersDatePicker;
    public ComboBox cmbCustomerId;

    private final String datePattern = "^\\d{4}/\\d{2}/\\d{2}$";
    //public DatePicker ordersDatePicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("Orderdate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        colTotalAmmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        cmbStatus.setItems(FXCollections.observableArrayList("Pending","Shipped","Deliverd","Canceled"));

        try {
            cmbCustomerId.setItems(FXCollections.observableArrayList(orderModel.getAllCustomerIds()));
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
        }

        try {
            setOrdersDateTimePicker();
            loadCustomerIds();
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong!",ButtonType.OK).show();
        }


    }
    public void loadTable() throws SQLException, ClassNotFoundException {
        tblOrder.setItems(FXCollections.observableArrayList(
                orderModel.getAllOrder().stream()
                        .map(orderDto -> new OrderTM(
                                orderDto.getOrderId(),
                                orderDto.getCustomerId(),
                                orderDto.getOrderdate(),
                                orderDto.getStatus(),
                                orderDto.getTotalAmount()

                        )).toList()
        ));
    }

    private void resetPage() {
        try {
            loadTable();
            loadNextId();

            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

            //txtCustomerId.setText(null);
            //txtOrderDate.setText(null);
            //txtStatus.setText(null);
            //txtTotalAmmount.setText(null);
            ordersDatePicker.setValue(null);
            cmbCustomerId.getSelectionModel().clearSelection();
            cmbStatus.getSelectionModel().clearSelection();


        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong!",ButtonType.OK).show();
        }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = orderModel.getNextOrderId();
        lblOrderId.setText(nextId);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String orderId = lblOrderId.getText();
        LocalDate orderDate = ordersDatePicker.getValue();
        String customerId = (String) cmbCustomerId.getSelectionModel().getSelectedItem();
        //String orderDate = txtOrderDate.getText();
        String status = (String) cmbStatus.getSelectionModel().getSelectedItem();
        String totalAmmount = txtTotalAmmount.getText();

        int preseTotal = Integer.parseInt(totalAmmount);

        if (orderId.isEmpty() || orderDate == null || customerId.isEmpty() || status == null) {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
            return;
        }

        OrderDto orderDto = new OrderDto (
                orderId,
                customerId,
                String.valueOf(orderDate),
                status,
                preseTotal
        );

        try {
            boolean isUpdated = orderModel.saveOrder(orderDto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION,"Order Updated!",ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Something went wrong!",ButtonType.OK).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong!",ButtonType.OK).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String orderId = lblOrderId.getText();
        LocalDate orderDate = ordersDatePicker.getValue();
        String customerId = (String) cmbCustomerId.getSelectionModel().getSelectedItem();
        //String orderDate = txtOrderDate.getText();
        String status = (String) cmbStatus.getSelectionModel().getSelectedItem();
        String totalAmmount = txtTotalAmmount.getText();

        int preseTotal = Integer.parseInt(totalAmmount);

        if (orderId.isEmpty() || orderDate == null || customerId.isEmpty() || status == null) {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
            return;
        }

        OrderDto orderDto = new OrderDto (
                orderId,
                customerId,
                String.valueOf(orderDate),
                status,
                preseTotal
        );

        try {
            boolean isUpdated = orderModel.updateOrder(orderDto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION,"Order Updated!",ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Something went wrong!",ButtonType.OK).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong!",ButtonType.OK).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are u sure ?",
                ButtonType.YES,
                ButtonType.NO
        );
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            String orderId = lblOrderId.getText();

            try {
                boolean isDeleted = orderModel.deleteOrder(orderId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION,"Order Deleted!",ButtonType.OK).show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Something went wrong!",ButtonType.OK).show();
                }
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Something went wrong!",ButtonType.OK).show();
            }
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        navigateTo("/view/DashBordPage.fxml");
    }

    private void navigateTo(String path) {
        try {
            ancOrder.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancOrder.widthProperty());
            anchorPane.prefHeightProperty().bind(ancOrder.heightProperty());

            ancOrder.getChildren().setAll(anchorPane);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Something went wrong",ButtonType.OK).show();
            e.printStackTrace();
        }
    }

    public void onClickedTable(MouseEvent mouseEvent) {
        OrderTM selectedorder = tblOrder.getSelectionModel().getSelectedItem();

        if (selectedorder != null) {
            lblOrderId.setText(selectedorder.getOrderId());
            ordersDatePicker.setValue(LocalDate.parse(selectedorder.getOrderdate()));
            cmbCustomerId.setValue(selectedorder.getCustomerId());
            //txtOrderDate.setText(selectedorder.getOrderdate());
            cmbStatus.setValue(selectedorder.getStatus());
            txtTotalAmmount.setText(String.valueOf(selectedorder.getTotalAmount()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
    public void search(KeyEvent keyEvent) {
        String searchText = txtSearch.getText();
        if (searchText.isEmpty()) {
            try {
                loadTable();
            }catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to load orders").show();
            }
        }else {
            try {
                ArrayList<OrderDto> ordersList = orderModel.searchOrders(searchText);
                tblOrder.setItems(FXCollections.observableArrayList(
                        ordersList.stream()
                                .map(orderDto -> new OrderTM(
                                        orderDto.getOrderId(),
                                        orderDto.getCustomerId(),
                                        orderDto.getOrderdate(),
                                        orderDto.getStatus(),
                                        orderDto.getTotalAmount()
                                ))
                                .toList()
                ));
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to load orders").show();
            }
        }
    }
    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        cmbCustomerId.setItems(FXCollections.observableArrayList(orderModel.getAllCustomerIds()));
    }
    /*public void goToAddCustomersLabel(MouseEvent mouseEvent) {
        navigateTo("/view/CustomerView.fxml");
    }*/
    private void setOrdersDateTimePicker() {
        // Set prompt text to today's date
        ordersDatePicker.setPromptText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        // Set up row click to load date into DatePicker
        tblOrder.setRowFactory(tv -> {
            TableRow<OrderTM> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    OrderTM rowData = row.getItem();
                    // Assuming OrdersTM has a getDate() method returning String in yyyy-MM-dd or similar format
                    try {
                        LocalDate date = LocalDate.parse(rowData.getOrderdate());
                        ordersDatePicker.setValue(date);
                    } catch (Exception e) {
                        e.printStackTrace();
                        new Alert(Alert.AlertType.ERROR, "Failed to set order date").show();
                        ordersDatePicker.setValue(null);
                    }
                }
            });
            return row;
        });
    }


}
