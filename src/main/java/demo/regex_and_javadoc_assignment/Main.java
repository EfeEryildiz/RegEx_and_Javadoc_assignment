package demo.regex_and_javadoc_assignment;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/**
 * A JavaFX application that implements a registration form with input validation.
 * This form validates user input for first name, last name, email, date of birth,
 * and zip code using regular expressions and custom validation logic.
 *
 * @author Your Name
 * @version 1.0
 * @since 2024-03-11
 */
public class RegistrationForm extends Application {

    // Regular expressions for validation
    private static final String NAME_REGEX = "^[A-Za-z]{2,25}$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@farmingdale\\.edu$";
    private static final String ZIP_REGEX = "^\\d{5}$";
    private static final String DATE_REGEX = "^(0[1-9]|1[0-2])/(0[1-9]|[12]\\d|3[01])/\\d{4}$";

    // Form fields
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private TextField dobField;
    private TextField zipField;
    private Button addButton;

    // Labels for validation feedback
    private Label firstNameValidation;
    private Label lastNameValidation;
    private Label emailValidation;
    private Label dobValidation;
    private Label zipValidation;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Registration Form");

        // Create the form layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Initialize form fields
        initializeFormFields();

        // Add fields to grid
        addFieldsToGrid(grid);

        // Create the scene
        Scene scene = new Scene(grid, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeFormFields() {
        // Initialize text fields
        firstNameField = new TextField();
        lastNameField = new TextField();
        emailField = new TextField();
        dobField = new TextField();
        zipField = new TextField();

        // Initialize validation labels
        firstNameValidation = new Label();
        lastNameValidation = new Label();
        emailValidation = new Label();
        dobValidation = new Label();
        zipValidation = new Label();

        // Initialize add button
        addButton = new Button("Add");
        addButton.setDisable(true);

        // Add validation listeners
        setupValidationListeners();
    }

    private void addFieldsToGrid(GridPane grid) {
        int row = 0;

        // First Name
        grid.add(new Label("First Name:"), 0, row);
        grid.add(firstNameField, 1, row);
        grid.add(firstNameValidation, 2, row++);

        // Last Name
        grid.add(new Label("Last Name:"), 0, row);
        grid.add(lastNameField, 1, row);
        grid.add(lastNameValidation, 2, row++);

        // Email
        grid.add(new Label("Email:"), 0, row);
        grid.add(emailField, 1, row);
        grid.add(emailValidation, 2, row++);

        // Date of Birth
        grid.add(new Label("Date of Birth (MM/DD/YYYY):"), 0, row);
        grid.add(dobField, 1, row);
        grid.add(dobValidation, 2, row++);

        // Zip Code
        grid.add(new Label("Zip Code:"), 0, row);
        grid.add(zipField, 1, row);
        grid.add(zipValidation, 2, row++);

        // Add Button
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.getChildren().add(addButton);
        grid.add(buttonBox, 1, row);

        // Add button click handler
        addButton.setOnAction(e -> handleSubmission());
    }

    /**
     * Sets up validation listeners for all input fields.
     * Each field is validated when it loses focus.
     * The Add button is enabled only when all fields contain valid data.
     */
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

    /**
     * Validates a text field against a regular expression pattern.
     *
     * @param field The TextField to validate
     * @param regex The regular expression pattern to validate against
     * @param validationLabel The Label to show validation feedback
     * @param errorMessage The message to display if validation fails
     * @return true if the field is valid, false otherwise
     */
    private boolean validateField(TextField field, String regex, Label validationLabel, String errorMessage) {
        boolean isValid = Pattern.matches(regex, field.getText());
        updateValidationLabel(validationLabel, isValid, errorMessage);
        updateAddButton();
        return isValid;
    }

    /**
     * Validates the date of birth field.
     * Checks if the date is in the correct format and represents a reasonable age.
     *
     * @return true if the date is valid, false otherwise
     * @throws DateTimeParseException if the date cannot be parsed
     */
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
                isValid = false;
            }
        }

        updateValidationLabel(dobValidation, isValid, "Enter a valid date (MM/DD/YYYY)");
        updateAddButton();
        return isValid;
    }

    private void updateValidationLabel(Label label, boolean isValid, String errorMessage) {
        label.setText(isValid ? "✓" : "✗ " + errorMessage);
        label.setTextFill(isValid ? Color.GREEN : Color.RED);
    }

    private void updateAddButton() {
        boolean allValid = Pattern.matches(NAME_REGEX, firstNameField.getText()) &&
                Pattern.matches(NAME_REGEX, lastNameField.getText()) &&
                Pattern.matches(EMAIL_REGEX, emailField.getText()) &&
                validateDateOfBirth() &&
                Pattern.matches(ZIP_REGEX, zipField.getText());

        addButton.setDisable(!allValid);
    }

    private void handleSubmission() {
        // Create new window/scene for successful registration
        Stage successStage = new Stage();
        successStage.setTitle("Registration Successful");

        Label successLabel = new Label("Registration completed successfully!");
        successLabel.setStyle("-fx-font-size: 16px; -fx-padding: 20px;");

        Scene successScene = new Scene(new HBox(successLabel), 300, 100);
        successStage.setScene(successScene);
        successStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}