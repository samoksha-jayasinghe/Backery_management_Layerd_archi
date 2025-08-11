package lk.ijse.backery_management_system.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.bakerymanagment.dto.InvoiceDto;
import lk.ijse.bakerymanagment.dto.tm.InvoiceTM;
import lk.ijse.bakerymanagment.model.InvoiceModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class InvoiceController implements Initializable {

    public AnchorPane ancInvoice;

    public Text lblInvoiceId;
    public TextField txtOrderId;
    public TextField txtDataIssued;
    public TextField txtTotalAmount;

    public TableView<InvoiceTM> tblInvoice;
    public TableColumn<InvoiceTM , String> colInvoiceId;
    public TableColumn<InvoiceTM , String> colOrderId;
    public TableColumn<InvoiceTM , String> colDataIssued;
    public TableColumn<InvoiceTM , String> colTotalAmmount;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    private final InvoiceModel invoiceModel = new InvoiceModel();

    public Button txtBack;

    public TextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colInvoiceId.setCellValueFactory(new PropertyValueFactory<>("invoiceid"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderid"));
        colDataIssued.setCellValueFactory(new PropertyValueFactory<>("dataIssue"));
        colTotalAmmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error", ButtonType.OK);
        }
    }
    public void loadTable() throws SQLException, ClassNotFoundException {
        tblInvoice.setItems(FXCollections.observableArrayList(
                invoiceModel.getAllInvoice().stream()
                        .map(invoiceDto -> new InvoiceTM(
                                invoiceDto.getInvoiceid(),
                                invoiceDto.getOrderid(),
                                invoiceDto.getDataIssue(),
                                invoiceDto.getTotalAmount()
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
            txtDataIssued.setText(null);
            txtTotalAmount.setText(null);

        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error", ButtonType.OK);
        }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = invoiceModel.getNextInvoiceId();
        lblInvoiceId.setText(nextId);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String invoiceId = lblInvoiceId.getText();
        String orderId = txtOrderId.getText();
        String dataIssued = txtDataIssued.getText();
        String totalAmount = txtTotalAmount.getText();

        int preseTotal = Integer.parseInt(totalAmount);

        InvoiceDto invoiceDto = new InvoiceDto(
                invoiceId,
                orderId,
                dataIssued,
                preseTotal
        );

        try {
            boolean isSaved = invoiceModel.saveInvoice(invoiceDto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Invoice Saved", ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Error", ButtonType.OK).show();
            }

        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error", ButtonType.OK).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String invoiceId = lblInvoiceId.getText();
        String orderId = txtOrderId.getText();
        String dataIssued = txtDataIssued.getText();
        String totalAmount = txtTotalAmount.getText();

        int preseTotal = Integer.parseInt(totalAmount);

        InvoiceDto invoiceDto = new InvoiceDto(
                invoiceId,
                orderId,
                dataIssued,
                preseTotal
        );

        try {
            boolean isUpdated = invoiceModel.updateInvoice(invoiceDto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Invoice Saved", ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Error", ButtonType.OK).show();
            }

        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error", ButtonType.OK).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are u Sure ?",
                ButtonType.YES,
                ButtonType.NO
        );
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            String invoiceId = lblInvoiceId.getText();

            try {
                boolean isDeleted = invoiceModel.deleteInvoice(invoiceId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Deleted successfully", ButtonType.OK).show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Deleting failed", ButtonType.OK).show();
                }
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "something error", ButtonType.OK).show();
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
        try{
            ancInvoice.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancInvoice.widthProperty());
            anchorPane.prefHeightProperty().bind(ancInvoice.heightProperty());

            ancInvoice.getChildren().setAll(anchorPane);

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "something error").show();
            e.printStackTrace();

        }
    }


    public void onClickedTable(MouseEvent mouseEvent) {
        InvoiceTM selectedinvoice = tblInvoice.getSelectionModel().getSelectedItem();

        if(selectedinvoice != null){
            lblInvoiceId.setText(selectedinvoice.getInvoiceid());
            txtOrderId.setText(selectedinvoice.getOrderid());
            txtDataIssued.setText(selectedinvoice.getDataIssue());
            txtTotalAmount.setText(String.valueOf(selectedinvoice.getTotalAmount()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }


}
