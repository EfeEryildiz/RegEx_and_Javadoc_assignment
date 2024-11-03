package demo.regex_and_javadoc_assignment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.util.EventObject;

/**
 * The type Success view controller.
 */
public class SuccessViewController {

    private EventObject event;

    @FXML
    private void handleBackButton() {
        try {
            // Load the registration form again
            FXMLLoader loader = new FXMLLoader(getClass().getResource("registration_form.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

            // Create new scene and set it on the stage
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/demo/regex_and_javadoc_assignment/styles.css").toExternalForm());
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
