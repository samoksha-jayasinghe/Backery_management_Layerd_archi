package lk.ijse.backery_management_system.controller;

// btnupdate reset save error

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
import lk.ijse.bakerymanagment.dto.PaymentDto;
import lk.ijse.bakerymanagment.dto.tm.PaymentTM;
import lk.ijse.bakerymanagment.model.PaymentModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    public AnchorPane ancPayment;
    public Text lblPaymentId;
    public TextField txtOrderId;
    public TextField txtMethod;
    public TextField txtPaymentDate;
    public TextField txtAmmount;

    public TableView<PaymentTM> tblPayment;
    public TableColumn<PaymentTM , String> colPaymentId;
    public TableColumn<PaymentTM , String> colOrderId;
    public TableColumn<PaymentTM , String> colMethod;
    public TableColumn<PaymentTM , String> colPaymentDate;
    public TableColumn<PaymentTM , String> colAmmount;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public Button txtBack;

    private final PaymentModel paymentModel = new PaymentModel();

    public TextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colMethod.setCellValueFactory(new PropertyValueFactory<>("method"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colAmmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        try {
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong!", ButtonType.OK).show();
        }

    }
    public void loadTable() throws SQLException, ClassNotFoundException {
        tblPayment.setItems(FXCollections.observableArrayList(
                paymentModel.getAllPayment().stream()
                        .map(paymentDto -> new PaymentTM(
                                paymentDto.getPaymentId(),
                                paymentDto.getOrderId(),
                                paymentDto.getMethod(),
                                paymentDto.getPaymentDate(),
                                paymentDto.getAmount()
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

            txtOrderId.setText(null);
            txtMethod.setText(null);
            txtPaymentDate.setText(null);
            txtAmmount.setText(null);
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong!", ButtonType.OK).show();
        }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = paymentModel.getNextPaymentId();
        lblPaymentId.setText(nextId);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String paymentId = lblPaymentId.getText();
        String orderId = txtOrderId.getText();
        String method = txtMethod.getText();
        String paymentDate = txtPaymentDate.getText();
        String ammount = txtAmmount.getText();

        int preseAmmount = Integer.parseInt(ammount);

        PaymentDto paymentDto = new PaymentDto(
                paymentId,
                orderId,
                method,
                paymentDate,
                preseAmmount
        );

        try {
            boolean isSaved = paymentModel.savePayment(paymentDto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION,"Payment saved successfully!", ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Something went wrong!", ButtonType.OK).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong!", ButtonType.OK).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String paymentId = lblPaymentId.getText();
        String orderId = txtOrderId.getText();
        String method = txtMethod.getText();
        String paymentDate = txtPaymentDate.getText();
        String ammount = txtAmmount.getText();

        int preseAmmount = Integer.parseInt(ammount);

        PaymentDto paymentDto = new PaymentDto(
                paymentId,
                orderId,
                method,
                paymentDate,
                preseAmmount
        );

        try {
            boolean isUpdated = paymentModel.updatePayment(paymentDto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION,"Payment saved successfully!", ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Something went wrong!", ButtonType.OK).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong!", ButtonType.OK).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are u sure ? ",
                ButtonType.YES,
                ButtonType.NO
        );
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            String paymentId = lblPaymentId.getText();

            try {
                boolean isDeleted = paymentModel.deletePayment(paymentId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION,"Payment deleted successfully!", ButtonType.OK).show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Something went wrong!", ButtonType.OK).show();
                }
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Something went wrong!", ButtonType.OK).show();
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
            ancPayment.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancPayment.widthProperty());
            anchorPane.prefHeightProperty().bind(ancPayment.heightProperty());

            ancPayment.getChildren().setAll(anchorPane);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            e.printStackTrace();
        }
    }

    public void onClickedTable(MouseEvent mouseEvent) {
        PaymentTM selectedpayment = tblPayment.getSelectionModel().getSelectedItem();

        if (selectedpayment != null) {
            lblPaymentId.setText(selectedpayment.getPaymentId());
            txtOrderId.setText(selectedpayment.getOrderId());
            txtMethod.setText(selectedpayment.getMethod());
            txtPaymentDate.setText(selectedpayment.getPaymentDate());
            txtAmmount.setText(String.valueOf(selectedpayment.getAmount()));

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
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to load payment data").show();
            }
        } else {
            try {
                tblPayment.setItems(FXCollections.observableArrayList(
                        paymentModel.searchPayments(searchText)
                                .stream()
                                .map(payment -> new PaymentTM(
                                        payment.getPaymentId(),
                                        payment.getOrderId(),
                                        payment.getMethod(),
                                        payment.getPaymentDate(),
                                        payment.getAmount()
                                )).toList()
                ));
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to search payments").show();
            }
        }
    }


}
