/**
 * Le package main.application contient la classe pour le lancement l'application Monster Hunt.
 */
package fr.univlille.info.J2.main.application;

//Runtime components
//--module-path /home/iutinfo/ExternalJars/Jars/javafx-sdk-21/lib --add-modules=javafx.controls,javafx.media

import fr.univlille.info.J2.main.management.Management;
import fr.univlille.info.J2.main.utils.resources.DisplayValues;

//--module-path /home/iutinfo/eclipse-workspace/Jars/javafx-sdk-21/lib --add-modules=javafx.controls

import javafx.application.Application;
import javafx.stage.Stage;
/**
 *
 * MonsterHunt est la classe de lancement de l'application Monster Hunt.
 * Elle étend la classe Application de JavaFX.
 *
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public class MonsterHunt extends Application{
	static boolean finished = false;

	public static void main(String[] args) {
		launch();
	}
	
	/**
     * Méthode de démarrage de l'application.
     * @param stage La scène principale de l'application.
     * @throws Exception Si une exception survient lors du démarrage de l'application.
     */
	@Override
	public void start(Stage stage) throws Exception {
		Management game = new Management(new DisplayValues(DisplayValues.WINDOWS_MIN_SIZE,DisplayValues.WINDOWS_MIN_SIZE,0,0,10,0,0));
		game.show();
	}



}