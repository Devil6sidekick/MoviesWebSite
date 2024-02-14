package application;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class noeud {
	
	private int Id;
	private static int dernierId = 0;
	/*cette variable pour incrementer l'id commençant de 1 apres chaque noeuds créé*/
	private double quantiteEnergie;
	private double energieActuelle;
	private boolean alive;
	public boolean isCH=false;
	private String modeleTransmissionReception;
	
	public double getEnergieActuelle() {
		return energieActuelle;
	}
	
	private int x;
	private int y;

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}

	
	
	ArrayList<noeud> voisins = new ArrayList<>();
	
	/*on genere les getters de tous les attributs pour pouvoir recuperer leurs valeurs et on genere le setters 
	 * pour le modele de communication puisqu il sera preciser par l utilisateur*/
	
	public double getQuantiteEnergie() {  //
		return quantiteEnergie;
	}
	public String getModeleTransmissionReception() {
		return modeleTransmissionReception;
	}
	public void setModeleTransmissionReception(String modeleTransmissionReception) {
		this.modeleTransmissionReception = modeleTransmissionReception;
	}

	public ArrayList<noeud> getVoisins() {
		return voisins;
	}
		
	public int getId() {
		return Id;
	}
	public noeud (String modeleTransmissionReception) {
		this.modeleTransmissionReception= modeleTransmissionReception;
		this.Id=++dernierId;
		this.quantiteEnergie=setQuantiteEnergie() ;
		this.energieActuelle=setEnergieActuelle();
		this.alive = true; // Initially, the noeud is alive
		
	}
    public double setQuantiteEnergie( ) {
    	/*cette fonction permet d attribuer la quantité d'énergie aux noeuds d'une façon aléatoire en attribuant 
    	 un nombre entre 10 et 100
    	Random random = new Random();
    	int a= random.nextInt(91) + 10;*/
    	this.quantiteEnergie=200;
    	return quantiteEnergie;
    }
    public double setEnergieActuelle() {
    	energieActuelle= quantiteEnergie;
    	       return energieActuelle;
    }
    	    
    
    /*cette fonction permet de definir les clusterheads selon leach et de changer le type de noeud pour la decharge de la batterie pendant la prochaine incrementation*/
	public void leachSetup(double p, int r) {
		Random random = new Random();
		double a = random.nextDouble();
		
		double z = 1 - p * (r % (1/p));
		double T = (p / z) * (energieActuelle / quantiteEnergie);

		if (a < T) {
			System.out.println("Noeud " + Id + " est un cluster head (Phase de Configuration).");
			isCH=true;

		} else {
			System.out.println("Noeud " + Id + " n'est pas un cluster head (Phase de Configuration).");
			isCH=false;

		}

		// Check if the noeud's energy level has reached 0 during the setup phase
		if (energieActuelle <= 0) {
			System.out.println("Noeud " + Id + " est mort.");
			alive = false;
		}
		}
    		


	

	
	public void transmitDataToClusterHead(noeud ch,Pane layout,int r) {
		if (ch==null) {
			System.out.println("Noeud"+Id+"n'a trouve aucun Cluster Head ;(");
			return;
		}
		// Simulate data transmission from normal noeuds to their cluster heads
		System.out.println("Noeud " + Id + " transmet des donnees à son cluster head "+ch.getId()+"(Phase Stable).");
		
		// Animation
		this.animatePacket(ch, layout,r);
		
		
		//Reduce the energie due to consumption
		energieActuelle-=20;
		// Check if the noeud's energy level has reached 0 during the steady-state phase
		if (energieActuelle <= 0) {
			System.out.println("Noeud " + Id + " est mort.");
			alive = false;
		}
	}

	public void transmitDataToSink(Sink sink) {
		// Simulate data transmission from cluster heads to the sink (base station)
		System.out.println("Cluster Head " + Id + " transmet des donnees au Sink (Phase Stable).");
		//Reduce the energie due to consumption
		energieActuelle-=50;
		
	}
	public boolean isAlive() {
		return alive;
	}
	
	public noeud findClosestClusterHead(reseau reseau) {
	    if (!this.isCH) {
	        noeud closestClusterHead = null;
	        double minDistance = Double.MAX_VALUE;

	        for (noeud othernoeud : reseau.listnoeuds) {
	            if (othernoeud.isCH && othernoeud != this) {
	                double distance = calculateDistance(this, othernoeud);
	                // Update minDistance only if distance is less than the current minDistance
	                if (distance < minDistance) {
	                    minDistance = distance;
	                    closestClusterHead = othernoeud;
	                }
	            }
	        }
	        return closestClusterHead;
	    }

	    return null;
	}



    // Method to calculate the Euclidean distance between two noeuds
    public  double calculateDistance(noeud noeud1, noeud noeud2) {
        double dx = noeud1.getX() - noeud2.getX();
        double dy = noeud1.getY() - noeud2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    public void animatePacket(noeud receiverNoeud, Pane layout, int r) {
        Rectangle packet = new Rectangle(this.getX(), this.getY(), 20, 10);
        packet.setFill(Color.BLUE);

        // Create a Text element with the specified value 'r' in white color
        Text text = new Text(String.valueOf(r));
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 8));
        text.setX(this.getX()+5 ); // Adjust the position based on your requirements
        text.setY(this.getY()+5); // Adjust the position based on your requirements

        layout.getChildren().addAll(packet, text);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    // Set the initial position of the packet and text
                    packet.setTranslateX(0);
                    packet.setTranslateY(0);
                    text.setTranslateX(0);
                    text.setTranslateY(0);
                }),
                new KeyFrame(Duration.seconds(10), e -> {
                    // Translate the packet during the animation
                    TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(10), packet);
                    translateTransition.setToX(receiverNoeud.getX() - this.getX());
                    translateTransition.setToY(receiverNoeud.getY() - this.getY());
                    translateTransition.play();

                    // Translate the text during the animation
                    TranslateTransition textTranslateTransition = new TranslateTransition(Duration.seconds(10), text);
                    textTranslateTransition.setToX(receiverNoeud.getX() - this.getX());
                    textTranslateTransition.setToY(receiverNoeud.getY() - this.getY());
                    textTranslateTransition.play();

                    // Make the packet and text disappear
                    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(11), packet);
                    fadeTransition.setToValue(0);
                    fadeTransition.play();

                    FadeTransition textFadeTransition = new FadeTransition(Duration.seconds(11), text);
                    textFadeTransition.setToValue(0);
                    textFadeTransition.play();
                })
        );

        timeline.setCycleCount(1);
        timeline.play();
    }

}

    


