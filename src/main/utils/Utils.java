/**
 * Le package main.utils contient des classes et des méthodes utilitaires pour diverses opérations
 * au sein de l'application. Il comprend des fonctionnalités pour la gestion de l'observateur (Observer)
 * et du sujet (Subject) pour le modèle de conception Observer, ainsi que des méthodes utilitaires
 * pour la génération de nombres aléatoires et la création d'arrière-plans graphiques.
 */
package main.utils;

import java.util.Random;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
/**
 * 
 * La classe Utils fournit des méthodes utilitaires pour diverses opérations
 * telles que la génération de nombres aléatoires et la création d'arrière-plans
 * pour les éléments graphiques.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public class Utils {
	/**
	 * Générateur de nombres aléatoires utilisé pour diverses opérations.
	 */
	public static Random random = new Random();
	/**
	 * Crée un arrière-plan (Background) avec une couleur de remplissage spécifiée.
	 * 
	 * @param fill La couleur de remplissage de l'arrière-plan.
	 * @return L'arrière-plan créé avec la couleur de remplissage spécifiée.
	 */
	public static Background setBackGroungFill(Color fill) {
		return new Background(new BackgroundFill(fill, new CornerRadii(0), Insets.EMPTY));
	}
	
	/**
     * Met en pause l'exécution du jeu pendant un certain nombre de secondes.
	 * @param secondes Le nombre de secondes à attendre.
	 */
	public static void wait(int secondes) {
		try {
			Thread.sleep(secondes*1000);
		} catch (InterruptedException e) {
			System.out.println("InterruptedException");
		}
	}
}
