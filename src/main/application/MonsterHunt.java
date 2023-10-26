package main.application;

//--module-path /home/iutinfo/eclipse-workspace/Jars/javafx-sdk-21/lib --add-modules=javafx.controls

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import main.maze.Management;
/**
 * La classe `MonsterHunt` est la classe principale de l'application de notre jeu "Monster Hunt". 
 * Elle extends la classe `Application` de JavaFX pour permettre la création de l'interface graphique du jeu. 
 * Lorsque l'application est lancée, elle instancie un objet de la classe `Management` pour gérer et afficher le jeu.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public class MonsterHunt extends Application{

	public static void main(String[] args) {
		launch();
	}
	
	/**
	 * Méthode de démarrage de l'application. Cette méthode est appelée lorsque l'application est lancée.
     *
     * @param stage La fenêtre principale de l'application.
     * @throws Exception Si une exception se produit lors de la création de l'objet `Management`.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// Crée un objet `Management` pour gérer le jeu avec les paramètres spécifiés.
		Management game = new Management(50,10,10,500,500,0,0,50,Color.DARKGRAY,Color.LIGHTGRAY);
	}
	
	

}
