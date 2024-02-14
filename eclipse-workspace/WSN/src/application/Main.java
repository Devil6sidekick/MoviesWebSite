package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // GridPane for centering
            GridPane inputGrid = new GridPane();
            inputGrid.setAlignment(Pos.CENTER);
            inputGrid.setStyle("-fx-background-color: #39A7FF;");
            inputGrid.setPadding(new Insets(10));
            inputGrid.setHgap(10);
            inputGrid.setVgap(10);

            // First row
            Label nodesLabel = new Label("Number of Nodes:");
            TextField nodesTextField = new TextField();
            Label durationLabel = new Label("Duration:");
            TextField durationTextField = new TextField();

            inputGrid.add(nodesLabel, 0, 0);
            inputGrid.add(nodesTextField, 1, 0);
            inputGrid.add(durationLabel, 2, 0);
            inputGrid.add(durationTextField, 3, 0);

            // Second row
            Label modeleLabel = new Label("Modele :");
            TextField modeleTextField = new TextField();
            Label percentageCHLabel = new Label("% of cluster heads:");
            TextField percentageCHTextField = new TextField();

            inputGrid.add(modeleLabel, 0, 1);
            inputGrid.add(modeleTextField, 1, 1);
            inputGrid.add(percentageCHLabel, 2, 1);
            inputGrid.add(percentageCHTextField, 3, 1);

            // Pane
            Pane layout = new Pane();
            layout.getChildren().add(inputGrid);

            //Submit button 
            Button submitButton = new Button("Submit");
            submitButton.setOnAction(e -> handleButtonClick(
                    nodesTextField.getText(),
                    durationTextField.getText(),
                    modeleTextField.getText(),
                    percentageCHTextField.getText()));
            submitButton.setStyle("-fx-background-color: #39A7FF; -fx-text-fill: white;");
            layout.getChildren().add(submitButton);
            
            submitButton.setLayoutX(230);
            submitButton.setLayoutY(160);
            
            // Load and display INPT logo
            Image image = new Image("inptlogo.jpeg"); 
            ImageView inpt = new ImageView(image);
            inpt.setFitWidth(200); // Adjust the width as needed
            inpt.setPreserveRatio(true);
            layout.getChildren().add(inpt);
            inpt.setLayoutY(390);
            inpt.setLayoutX(180);

            // Scene
            Scene scene = new Scene(layout, 600, 500);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            inputGrid.setMinWidth(layout.getWidth());

            // Stage
            primaryStage.setTitle("WSN Simulator");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        private void handleButtonClick(String nodes, String duration, String modele, String percentageCH) {
            try {
                // Check if the inputs are integers
                int nodesValue = Integer.parseInt(nodes);
                int durationValue = Integer.parseInt(duration);
                int percentageCHValue = Integer.parseInt(percentageCH);
             
                // Additional validation for percentageCH if needed
                if (percentageCHValue < 0 || percentageCHValue > 100) {
                    System.out.println("Invalid percentage value. Please enter a value between 0 and 100.");
                    return;
                }

                // Create a new Display window and pass the values
                Display displayWindow = new Display(nodesValue, durationValue, percentageCHValue);
                displayWindow.display();
            } catch (NumberFormatException ex) {
                // Handle the case where input is not a valid integer
                System.out.println("Invalid input. Please enter valid integers.");
            }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
