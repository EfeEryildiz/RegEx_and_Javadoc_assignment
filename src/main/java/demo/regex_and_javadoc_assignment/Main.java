package demo.regex_and_javadoc_assignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * The type Main.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/demo/regex_and_javadoc_assignment/registration_form.fxml"));
        Parent root = loader.load();

        // Load CSS
        Scene scene = new Scene(root, 700, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/demo/regex_and_javadoc_assignment/styles.css")).toExternalForm());

        primaryStage.setTitle("Student Registration");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}