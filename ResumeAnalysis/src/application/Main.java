package application;
	
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class Main extends Application {

    private final TextField resumePathField = new TextField();
    private final TextArea outputArea = new TextArea();
    private final ComboBox<String> companySelector = new ComboBox<>();
    private final Label statusLabel = new Label();
    private final ProgressIndicator progressIndicator = new ProgressIndicator();
    private String resumeText = "";

    // CSS Styling Constants
    private static final String MAIN_COLOR = "#2c3e50";
    private static final String SECONDARY_COLOR = "#3498db";
    private static final String FONT_FAMILY = "Segoe UI";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png"))));
        } catch (Exception e) {
            // Ignore if icon not found
        }

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f6fa;");

        VBox header = createHeaderSection(stage);
        root.setTop(header);

        VBox content = createContentSection();
        root.setCenter(content);

        HBox statusBar = createStatusBar();
        root.setBottom(statusBar);

        Scene scene = new Scene(root, 900, 700);
        try {
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
        } catch (Exception e) {
            // Ignore if CSS not found
        }
        stage.setScene(scene);
        stage.setTitle("Smart Resume Analyzer Pro");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }

    private VBox createHeaderSection(Stage stage) {
        Label title = new Label("Smart Resume Analyzer");
        title.setFont(Font.font(FONT_FAMILY, 24));
        title.setStyle("-fx-text-fill: " + MAIN_COLOR + ";");

        HBox fileSection = new HBox(10);
        fileSection.setAlignment(Pos.CENTER_LEFT);

        ImageView uploadIcon = null;
        try {
            uploadIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/upload.png"))));
            uploadIcon.setFitHeight(20);
            uploadIcon.setFitWidth(20);
        } catch (Exception e) {
            // Ignore if icon not found
        }

        Button browseBtn = createStyledButton(uploadIcon);
        browseBtn.setOnAction(e -> selectResume(stage));

        resumePathField.setPromptText("Select a resume file...");
        resumePathField.setStyle("-fx-font-size: 14; -fx-padding: 8;");
        resumePathField.setEditable(false);

        fileSection.getChildren().addAll(browseBtn, resumePathField);

        VBox header = new VBox(20);
        header.getChildren().addAll(title, fileSection);
        return header;
    }

    private VBox createContentSection() {
        VBox content = new VBox(20);

        Label analysisTitle = new Label("Analysis Results");
        analysisTitle.setStyle("-fx-font-size: 18; -fx-text-fill: " + MAIN_COLOR + ";");

        outputArea.setWrapText(true);
        outputArea.setEditable(false);
        outputArea.setStyle("-fx-font-family: Consolas; -fx-font-size: 13;");
        outputArea.setPrefHeight(400);

        HBox companySection = new HBox(10);
        companySection.setAlignment(Pos.CENTER_LEFT);

        Label companyLabel = new Label("Select Target Company:");
        companyLabel.setStyle("-fx-font-size: 14;");

        companySelector.setPromptText("Choose a company...");
        companySelector.setStyle("-fx-font-size: 14; -fx-pref-width: 300;");
        companySelector.setOnAction(e -> analyzeCompanySkills());

        companySection.getChildren().addAll(companyLabel, companySelector);

        content.getChildren().addAll(analysisTitle, outputArea, companySection);
        return content;
    }

    private HBox createStatusBar() {
        HBox statusBar = new HBox(10);
        statusBar.setAlignment(Pos.CENTER_LEFT);
        statusBar.setStyle("-fx-background-color: " + MAIN_COLOR + "; -fx-padding: 10;");

        statusLabel.setStyle("-fx-text-fill: white; -fx-font-size: 13;");
        statusLabel.setMaxWidth(Double.MAX_VALUE);

        progressIndicator.setVisible(false);
        progressIndicator.setPrefSize(20, 20);

        statusBar.getChildren().addAll(progressIndicator, statusLabel);
        HBox.setHgrow(statusLabel, Priority.ALWAYS);
        return statusBar;
    }

    private Button createStyledButton(ImageView icon) {
        Button btn = new Button("Browse Resume", icon);
        String baseStyle = "-fx-font-size: 14; -fx-text-fill: white; -fx-background-color: " + SECONDARY_COLOR + "; " +
                "-fx-padding: 10 20; -fx-background-radius: 5;";
        btn.setStyle(baseStyle);
        btn.setOnMouseEntered(e -> btn.setStyle(baseStyle ));
        btn.setOnMouseExited(e -> btn.setStyle(baseStyle));
        return btn;
    }

    private void selectResume(Stage stage) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Resume Files", "*.pdf", "*.docx")
        );
        File file = chooser.showOpenDialog(stage);
        if (file != null) {
            resumePathField.setText(file.getAbsolutePath());
            analyze();
        }
    }

    private void analyze() {
        String path = resumePathField.getText();
        if (path == null || path.isEmpty()) {
            showError("Please select a resume file first!");
            return;
        }
        startAnalysisTask(path);
    }

    private void startAnalysisTask(String path) {
        Task<Void> analysisTask = new Task<Void>() {
            @Override
            protected Void call() {
                updateStatus("Analyzing resume...", true);
                resumeText = ResumeParser.parse(path);

                List<String> resumeSkills = extractSkillsFromText(resumeText);
                List<String> roles = SkillMatcher.suggestRoles(resumeText);

                updateUI(() -> displayResults(resumeSkills, roles));
                return null;
            }

            @Override
            protected void failed() {
                showError("Analysis failed: " + getException().getMessage());
            }

            @Override
            protected void succeeded() {
                updateStatus("Analysis complete!", false);
            }
        };

        Thread t = new Thread(analysisTask);
        t.setDaemon(true);
        t.start();
    }

    private void displayResults(List<String> resumeSkills, List<String> roles) {
        StringBuilder output = new StringBuilder();
        output.append("=== Resume Analysis Report ===\n\n");
        output.append("Extracted Skills:\n");
        if (resumeSkills.isEmpty()) {
            output.append("(No known skills found)\n");
        } else {
            for (String skill : resumeSkills) {
                output.append("- ").append(skill).append("\n");
            }
        }
        output.append("\nSuggested Career Paths:\n");
        List<String> uniqueCompanies = new ArrayList<>();
        for (String role : roles) {
            output.append("• ").append(role).append("\n");
            List<String> companies = JobCompanyMap.getCompaniesForRole(role);
            if (!companies.isEmpty()) {
                output.append("   Potential Employers: ")
                        .append(String.join(", ", companies))
                        .append("\n\n");
                for (String c : companies) {
                    if (!uniqueCompanies.contains(c)) uniqueCompanies.add(c);
                }
            }
        }
        companySelector.getItems().setAll(uniqueCompanies);
        outputArea.setText(output.toString());
    }

    private void analyzeCompanySkills() {
        String selected = companySelector.getValue();
        if (selected == null || selected.isEmpty()) return;

        List<String> requiredSkills = JobCompanyMap.getSkillsForCompany(selected);
        List<String> resumeSkills = extractSkillsFromText(resumeText);

        List<String> missing = requiredSkills.stream()
                .filter(skill -> !resumeSkills.contains(skill.toLowerCase()))
                .collect(Collectors.toList());

        String message = missing.isEmpty() ?
                "🎉 Perfect match! All required skills are present!" :
                "⚠️ Missing skills: " + String.join(", ", missing);

        showMessage(message);
    }

    private List<String> extractSkillsFromText(String text) {
        if (text == null) return new ArrayList<>();
        text = text.toLowerCase();
        String[] knownSkills = {
                "java", "spring", "sql", "aws", "docker", "kubernetes", "microservices",
                "rest", "oop", "excel", "python", "linux", "hibernate", "tableau", "mysql"
        };
        List<String> found = new ArrayList<>();
        for (String skill : knownSkills) {
            if (text.contains(skill.toLowerCase())) {
                found.add(skill.toLowerCase());
            }
        }
        return found;
    }

    private void updateStatus(String message, boolean showProgress) {
        Platform.runLater(() -> {
            statusLabel.setStyle("-fx-text-fill: white; -fx-font-size: 13;");
            statusLabel.setText(message);
            progressIndicator.setVisible(showProgress);
        });
    }

    private void showError(String message) {
        Platform.runLater(() -> {
            statusLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 13;");
            statusLabel.setText(message);
            progressIndicator.setVisible(false);
        });
    }

    private void showMessage(String message) {
        Platform.runLater(() -> {
            statusLabel.setStyle("-fx-text-fill: #2ecc71; -fx-font-size: 13;");
            statusLabel.setText(message);
        });
    }

    private void updateUI(Runnable update) {
        Platform.runLater(update);
    }
}

