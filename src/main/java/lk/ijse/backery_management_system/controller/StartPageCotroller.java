package lk.ijse.backery_management_system.controller;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class StartPageCotroller {
    public Label lblBakers;
    public Label lblsj;
    public AnchorPane ancStart;

    public void initialize() {
        animateLabelSlideIn();
        animateLabelSlideIn2();
    }

    public void btnStartOnAction(ActionEvent actionEvent) {
            navigateTo("/view/LoginPage.fxml");
    }

    private void animateLabelSlideIn() {
        String loginText = lblsj.getText();
        lblsj.setText(loginText); // Make sure text is visible
        lblsj.setOpacity(0);      // Start invisible
        lblsj.setTranslateX(-50); // Start off-screen to the left

        // Slide-in animation
        TranslateTransition slide = new TranslateTransition(Duration.millis(2000), lblsj);
        slide.setFromX(-100);
        slide.setToX(0);

        // Fade-in animation
        FadeTransition fade = new FadeTransition(Duration.millis(2000), lblsj);
        fade.setFromValue(0);
        fade.setToValue(1);

        // Play both at once
        ParallelTransition parallel = new ParallelTransition(slide, fade);
        parallel.play();
    }

    private void animateLabelSlideIn2() {
        String loginText = lblBakers.getText();
        lblBakers.setText(loginText); // Make sure text is visible
        lblBakers.setOpacity(0);      // Start invisible
        lblBakers.setTranslateX(-50); // Start off-screen to the left

        // Slide-in animation
        TranslateTransition slide = new TranslateTransition(Duration.millis(2000), lblBakers);
        slide.setFromX(100);
        slide.setToX(0);

        // Fade-in animation
        FadeTransition fade = new FadeTransition(Duration.millis(2000), lblBakers);
        fade.setFromValue(0);
        fade.setToValue(1);

        // Play both at once
        ParallelTransition parallel = new ParallelTransition(slide, fade);
        parallel.play();
    }

    private void navigateTo(String path) {
        try {
            ancStart.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancStart.widthProperty());
            anchorPane.prefHeightProperty().bind(ancStart.heightProperty());

            ancStart.getChildren().setAll(anchorPane);


        }catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something error").show();
            e.printStackTrace();

        }
    }
}
