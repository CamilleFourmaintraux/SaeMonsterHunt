package main.application;

//--module-path /home/iutinfo/eclipse-workspace/Jars/javafx-sdk-21/lib --add-modules=javafx.controls

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import main.maze.Management;
/**
 * La classe `MonsterHunt` est la classe principale de l'application de notre jeu "Monster Hunt". 
 * Elle extends la classe `Application` de JavaFX pour permettre la cr�ation de l'interface graphique du jeu. 
 * Lorsque l'application est lanc�e, elle instancie un objet de la classe `Management` pour g�rer et afficher le jeu.
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
	 * M�thode de d�marrage de l'application. Cette m�thode est appel�e lorsque l'application est lanc�e.
     *
     * @param stage La fen�tre principale de l'application.
     * @throws Exception Si une exception se produit lors de la cr�ation de l'objet `Management`.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// Cr�e un objet `Management` pour g�rer le jeu avec les param�tres sp�cifi�s.
		Management game = new Management(50,10,10,500,500,0,0,50,Color.DARKGRAY,Color.LIGHTGRAY);
	}
	
	

}
