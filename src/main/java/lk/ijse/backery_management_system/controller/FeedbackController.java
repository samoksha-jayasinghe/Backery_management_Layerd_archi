package lk.ijse.backery_management_system.controller;

import com.google.protobuf.StringValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.bakerymanagment.dto.CustomerDto;
import lk.ijse.bakerymanagment.dto.FeedbackDto;
import lk.ijse.bakerymanagment.dto.tm.EmployeeTM;
import lk.ijse.bakerymanagment.dto.tm.FeedbackTM;
import lk.ijse.bakerymanagment.model.FeedbackModel;

//import javax.swing.text.TabableView;
//import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class FeedbackController implements Initializable {

    public AnchorPane ancFeedback;
    public Text lblFeedBackId;
    public TextField txtcustomerId;
    public TextField txtOrdered;
    public TextField txtrating;
    public TextField txtcomment;

    public Button btnSave;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnReset;

    public TableView<FeedbackTM> tblFeedback;
    public TableColumn<FeedbackDto , String > colFeedbackId;
    public TableColumn<FeedbackDto , String > colCustomerId;
    public TableColumn<FeedbackDto , String > colOrderId;
    public TableColumn<FeedbackDto , String > colRating;
    public TableColumn<FeedbackDto , String > colComment;

    public Button txtBack;
    public TextField txtSearch;

    private final FeedbackModel feedbackModel = new FeedbackModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colFeedbackId.setCellValueFactory(new PropertyValueFactory<>("FeedbackId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        colRating.setCellValueFactory(new PropertyValueFactory<>("Rating"));
        colComment.setCellValueFactory(new PropertyValueFactory<>("Comment"));

        try {
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong!",ButtonType.OK).show();
        }
    }
    public void loadTableData() throws SQLException, ClassNotFoundException {
        tblFeedback.setItems(FXCollections.observableArrayList(
                feedbackModel.getAllFeedback().stream()
                        .map(feedbackDto -> new FeedbackTM(
                                feedbackDto.getFeedbackId(),
                                feedbackDto.getCustomerId(),
                                feedbackDto.getOrderId(),
                                feedbackDto.getRating(),
                                feedbackDto.getComment()
                        )).toList()
        ));
    }

    private void resetPage() throws SQLException, ClassNotFoundException {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            txtcustomerId.setText(null);
            txtOrdered.setText(null);
            txtrating.setText(null);
            txtcomment.setText(null);


        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong!",ButtonType.OK).show();
        }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = feedbackModel.getNextFeedbackId();
        lblFeedBackId.setText(nextId);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String feedbackId = lblFeedBackId.getText();
        String customerId = txtcustomerId.getText();
        String orderId = txtOrdered.getText();
        String ratng = txtrating.getText();
        String comment = txtcomment.getText();

       // boolean ratingValid = ratng.equals("");

        int preseRating = Integer.parseInt(ratng);

        FeedbackDto feedbackDto = new FeedbackDto(
                feedbackId,
                customerId,
                orderId,
                preseRating,
                comment
        );
        //if (ratingValid) {
            try {
                boolean isSaved = feedbackModel.saveFeedback(feedbackDto);

                if (isSaved) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Saved successfully").show();

                }else {
                    new Alert(Alert.AlertType.ERROR, "Saving failed").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "something error", ButtonType.OK).show();
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
            String feedbackId = lblFeedBackId.getText();

            try {
                boolean isDeleted = feedbackModel.deleteFeedback(feedbackId);
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

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String feedbackId = lblFeedBackId.getText();
        String customerId = txtcustomerId.getText();
        String orderId = txtOrdered.getText();
        String rating = txtrating.getText();
        String comment = txtcomment.getText();

       // boolean ratingValid = Objects.equals(rating, "5");

        int preseRating = Integer.parseInt(rating);

        FeedbackDto feedbackDto = new FeedbackDto(
                feedbackId,
                customerId,
                orderId,
                preseRating,
                comment
        );
            try {
                boolean isUpdate = feedbackModel.updateFeedback(feedbackDto);

                if (isUpdate) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Update successfully", ButtonType.OK).show();

                }else {
                    new Alert(Alert.AlertType.ERROR, "Update failed", ButtonType.OK).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "something error", ButtonType.OK).show();
            }

        }

    public void btnResetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        resetPage();
    }

    public void onClickedTable(MouseEvent mouseEvent) {

       FeedbackTM selectedFeedback = (FeedbackTM) tblFeedback.getSelectionModel().getSelectedItem();

        if (selectedFeedback != null) {
            lblFeedBackId.setText(selectedFeedback.getFeedbackId());
            txtcustomerId.setText(selectedFeedback.getCustomerId());
            txtOrdered.setText(selectedFeedback.getOrderId());
            txtrating.setText(String.valueOf(selectedFeedback.getRating()));
            txtcomment.setText(selectedFeedback.getComment());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);

        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        navigateTo("/view/DashBordPage.fxml");

    }

    private void navigateTo(String path) {
        try{
            ancFeedback.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancFeedback.widthProperty());
            anchorPane.prefHeightProperty().bind(ancFeedback.heightProperty());

            ancFeedback.getChildren().setAll(anchorPane);

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "something error").show();
            e.printStackTrace();

        }
    }
}

