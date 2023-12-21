/**
 * Le package main.utils contient des classes et des méthodes utilitaires pour diverses opérations
 * au sein de l'application. Il comprend des fonctionnalités pour la gestion de l'observateur (Observer)
 * et du sujet (Subject) pour le modèle de conception Observer, ainsi que des méthodes utilitaires
 * pour la génération de nombres aléatoires et la création d'arrière-plans graphiques.
 */
package fr.univlille.info.J2.main.utils;

import java.util.Random;
import java.util.logging.Logger;

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
 */
public class Utils {
	
	
	private Utils() {}
	
	/**
	 * Looger qui permet d'éviter les system.out pour à la place faire de vra ifichiers de log.
	 */
	private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());
	
	/**
	 * Générateur de nombres aléatoires utilisé pour diverses opérations.
	 */
	public static final Random random = new Random();
	
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
	 * Méthode permettant de convertir un objet color (donc une couleur) en valeur hexadécimal dans une chaine de caractère.
	 * 
	 * @param color Couleur à convertir en valeur hexadécimal.
	 * @return Chaine de caractère contenant le code hexadécimal.
	 */
	public static String convertToHex(Color color) {
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);

        return String.format("#%02x%02x%02x", r, g, b);
    }

	/**
     * Met en pause l'exécution du jeu pendant un certain nombre de secondes.
	 * @param secondes Le nombre de secondes à attendre.
	 */
	public static void wait(int secondes) {
		try {
			Thread.sleep(secondes*(long)1000);
		} catch (InterruptedException e) {
			LOGGER.info("InterruptedException in Utils -> method wait(int)");
		}
	}
}
