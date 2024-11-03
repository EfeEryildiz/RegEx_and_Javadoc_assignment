package demo.regex_and_javadoc_assignment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * The type Registration form controller.
 */
public class RegistrationFormController implements Initializable {

    // Regular expressions for validation
    private static final String NAME_REGEX = "^[A-Za-z]{2,25}$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@farmingdale\\.edu$";
    private static final String ZIP_REGEX = "^\\d{5}$";
    private static final String DATE_REGEX = "^(0[1-9]|1[0-2])/(0[1-9]|[12]\\d|3[01])/\\d{4}$";

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField dobField;
    @FXML
    private TextField zipField;
    @FXML
    private Button addButton;
    @FXML
    private Label firstNameValidation;
    @FXML
    private Label lastNameValidation;
    @FXML
    private Label emailValidation;
    @FXML
    private Label dobValidation;
    @FXML
    private Label zipValidation;
    @FXML
    private Label statusMessage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupValidationListeners();
        clearValidationMessages();
    }

    private void clearValidationMessages() {
        firstNameValidation.setText("");
        lastNameValidation.setText("");
        emailValidation.setText("");
        dobValidation.setText("");
        zipValidation.setText("");
        statusMessage.setText("");
    }

    private void setupValidationListeners() {
        firstNameField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) { // Focus lost
                validateField(firstNameField, NAME_REGEX, firstNameValidation, "First name must be 2-25 letters");
            }
        });

        lastNameField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validateField(lastNameField, NAME_REGEX, lastNameValidation, "Last name must be 2-25 letters");
            }
        });

        emailField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validateField(emailField, EMAIL_REGEX, emailValidation, "Must be a valid Farmingdale email");
            }
        });

        dobField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validateDateOfBirth();
            }
        });

        zipField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validateField(zipField, ZIP_REGEX, zipValidation, "Must be a 5-digit number");
            }
        });
    }

    private boolean validateField(TextField field, String regex, Label validationLabel, String errorMessage) {
        boolean isValid = Pattern.matches(regex, field.getText());
        updateValidationLabel(validationLabel, isValid, errorMessage);
        return isValid;
    }

    private boolean validateDateOfBirth() {
        String dob = dobField.getText();
        boolean isValid = false;

        if (Pattern.matches(DATE_REGEX, dob)) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate birthDate = LocalDate.parse(dob, formatter);
                LocalDate now = LocalDate.now();

                // Check if date is in the past and person is not too old
                isValid = birthDate.isBefore(now) && birthDate.isAfter(now.minusYears(120));
            } catch (DateTimeParseException e) {
                // Ignore
            }
        }

        updateValidationLabel(dobValidation, isValid, "Enter a valid date (MM/DD/YYYY)");
        return isValid;
    }

    private void updateValidationLabel(Label label, boolean isValid, String errorMessage) {
        label.setText(isValid ? "✓" : "✗ " + errorMessage);
        label.setTextFill(isValid ? Color.GREEN : Color.RED);
    }

    private boolean validateAllFields() {
        boolean isFirstNameValid = validateField(firstNameField, NAME_REGEX, firstNameValidation, "First name must be 2-25 letters");
        boolean isLastNameValid = validateField(lastNameField, NAME_REGEX, lastNameValidation, "Last name must be 2-25 letters");
        boolean isEmailValid = validateField(emailField, EMAIL_REGEX, emailValidation, "Must be a valid Farmingdale email");
        boolean isDobValid = validateDateOfBirth();
        boolean isZipValid = validateField(zipField, ZIP_REGEX, zipValidation, "Must be a 5-digit number");

        return isFirstNameValid && isLastNameValid && isEmailValid && isDobValid && isZipValid;
    }

    @FXML
    private void handleSubmission() {
        if (validateAllFields()) {
            try {
                // Load the success view
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/demo/regex_and_javadoc_assignment/success_view.fxml"));
                Parent root = loader.load();

                // Get the current stage
                Stage stage = (Stage) addButton.getScene().getWindow();

                // Create new scene and set it on the stage
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/demo/regex_and_javadoc_assignment/styles.css").toExternalForm());
                // Set the new scene
                stage.setScene(scene);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}