package application;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Display {
	static int numberofNodes;
	static double percentageCH;
	static int duration;
	int position;
	
	public Display(int nodesValue,int durationValue,int percentageCHValue) {
		this.duration=durationValue;
		this.numberofNodes=nodesValue;
		this.percentageCH=percentageCHValue/100.0;
		
	}
	public static void display() {
		//Window
		Stage window=new Stage();
		window.initModality(Modality.APPLICATION_MODAL);

		
		//Layout
		Pane layout=new Pane();
		
		//Display the nodes
		reseau reseau =new reseau(numberofNodes,layout);
		
		//Display Sink
		Image image = new Image("sink..jpg");
        ImageView sink = new ImageView(image);

        // Calculate midpoints of the intervals
        int midX = (100 + 950) / 2; // Middle of 100 and 950
        int midY = (100 + 650) / 2; // Middle of 100 and 650

        // Set dimensions
        sink.setFitWidth(50); // Adjust the width as needed
        sink.setPreserveRatio(true);

        // Set fixed coordinates
        sink.setLayoutX(midX);
        sink.setLayoutY(midY);

        layout.getChildren().add(sink);
		
      
		//runLeach 
		reseau.runLeachSimulation(percentageCH, duration,layout);
		
		//Scene
  		Scene scene=new Scene(layout,1000,700);
  		window.setScene(scene);
  		window.show();
  		window.setTitle("Display");


	}
}
