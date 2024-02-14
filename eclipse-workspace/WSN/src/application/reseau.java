package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
public class reseau {                       
    public ArrayList<noeud> listnoeuds ;
    private Sink sink;

    
    
    public reseau(int numberOfnoeuds, Pane layout) {
        // Initialize the network with the specified number of noeuds
        listnoeuds = new ArrayList<>();
        ArrayList<Coordinates> coordList=new ArrayList<Coordinates>();
        coordList=this.generateRandomCoordinates(numberOfnoeuds);
        for (int i = 0; i < numberOfnoeuds; i++) {
        	
            noeud newnoeud = new noeud("transmissionReceptionMode");
            listnoeuds.add(newnoeud);
          //set the coordinates of the nodes
        	listnoeuds.get(i).setX(coordList.get(i).X);
        	listnoeuds.get(i).setY(coordList.get(i).Y);
        	
            //View noeud on window
            	//view image
            Image noeudImage = new Image("node.png");
            ImageView noeudImageView = new ImageView(noeudImage);
            noeudImageView.setFitWidth(30);
            noeudImageView.setFitHeight(30);
            	//view ID
            String noeudid="Id :"+newnoeud.getId()+"";
            Label id=new Label(noeudid);
            	
            
            int randomX =coordList.get(i).X;
            int randomY =coordList.get(i).Y;
            
            	//view Coordinates
            String coordinates="("+randomX+","+randomY+")";
            // Add Tooltip with node information (when u put the mouse on the node it appears!)
            Tooltip tooltip = new Tooltip("Node ID: "+newnoeud.getId()+"\nCoordinates: "+coordinates); // Customize with your node information
            Tooltip.install(noeudImageView, tooltip);
            
            
            id.setLayoutX(randomX+2);
            id.setLayoutY(randomY-10);
            noeudImageView.setLayoutX(randomX);
            noeudImageView.setLayoutY(randomY);            
            layout.getChildren().addAll(noeudImageView,id);
        }
    }

    public void runLeachSimulation(double p,int duration,Pane layout) {
        // Run the LEACH setup phase 
    	
    	for (int r = 1; r < duration+1; r++) {
            
    		System.out.println(">>> Round :"+r );
        		for (noeud noeud : listnoeuds) {
                    // Check if the noeud is alive before executing LEACH setup
                    if (noeud.isAlive()) {             
                        noeud.leachSetup(p, r);                     
                    }
                }
        	
        

        // Run the LEACH steady-state phase 
            for (noeud noeud : listnoeuds) {
                // Check if the noeud is alive before transmitting data to its cluster head
                if (noeud.isAlive() && !noeud.isCH) {
                    noeud.transmitDataToClusterHead(noeud.findClosestClusterHead(this),layout,r);
                }
            }

            for (noeud clusterHead : getClusterHeads()) {
                // Check if the cluster head is alive before transmitting data to the sink
                if (clusterHead.isAlive()) {
                    clusterHead.transmitDataToSink(sink);
                }
            }}
    	}
    
    private ArrayList<noeud> getClusterHeads() {
        ArrayList<noeud> clusterHeads = new ArrayList<>();
        for (noeud noeud : listnoeuds) {
            if (noeud.isCH&& noeud.isAlive()) {
                clusterHeads.add(noeud);
            }
        }
        return clusterHeads;
    }
    public class Coordinates{
    	public Coordinates(int x2, int y2) {
			this.X=x2;
			this.Y=y2;
		}
		int X;
    	int Y;
    }
    
     public ArrayList<Coordinates> generateRandomCoordinates(int numberOfnoeuds) {
        Random random = new Random();
        ArrayList<Coordinates> coordinates = new ArrayList<>();

        for (int i = 0; i < numberOfnoeuds; i++) {
            int x = random.nextInt(851) + 100; // Random value between 100 and 950 (inclusive)
            int y = random.nextInt(551) + 100; // Random value between 100 and 650 (inclusive)

            coordinates.add(new Coordinates(x, y));
        }

        return coordinates;
    }
     
 }
