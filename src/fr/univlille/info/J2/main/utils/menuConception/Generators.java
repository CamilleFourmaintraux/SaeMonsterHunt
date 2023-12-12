package fr.univlille.info.J2.main.utils.menuConception;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import fr.univlille.info.J2.main.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * Classe de génération des différentes fenêtres, boites de dialogues ou composants.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 */
public class Generators {
	
	/**
	 * Looger qui permet d'éviter les system.out pour à la place faire de vra ifichiers de log.
	 */
	private static final Logger logger = Logger.getLogger(Generators.class.getName());

	private Generators() {}
	/**
	 * Génére un bouton avec un texte donné et le positionne aux coordonnées spécifiés.
	 *
	 * @param msg 		Le texte affiché sur le bouton.
	 * @param x 		La position horizontale du bouton.
	 * @param y 		La position verticale du bouton.
	 * @param inactive 	La couleur en hexadécimal quand le bouton n'est pas en interaction
	 * @param active 	La couleur en hexadécimal quand le bouton est en interaction
	 * @return Le bouton généré.
	 */
	public static Button generateButton(String msg, Color active, Color inactive) {
		Button button = new Button(msg);
		Generators.applyStyleToButton(button, active, inactive);
		return button;
	}
	public static Button generateButton(String msg, double x, double y, Color active, Color inactive) {
		Button button = new Button(msg);
		Generators.setLayout(button, x-(button.getWidth()/2) ,y);
		Generators.applyStyleToButton(button, active, inactive);
		return button;
	}
	
	/**
	 * Génére un Label avec le texte donné et la positionne aux coordonnées spécifiés.
	 *
	 * @param msg 		Le texte affiché sur le label.
	 * @param x 		La position horizontale du label.
	 * @param y 		La position verticale du label.
	 * @return Le label généré.
	 */
	public static Label generateLabel(String msg, double x, double y) {
		Label label = new Label(msg);
		label.setMinWidth(label.getPrefWidth());
		Generators.setLayout(label, x, y);
		return label;
	}
	public static Label generateLabel(String msg) {
		Label label = new Label(msg);
		label.setMinWidth(label.getPrefWidth());
		return label;
	}

	/**
	 * Génére un slider avec une valeur minimale, maximale et par défault et le positionne aux coordonnées spécifiés.
	 *
	 * @param min 			La valeur minimale que peut prendre le slider
	 * @param default_value La valeur par défault que prend le slider
	 * @param max 			La valeur maximale que peut prendre le slider
	 * @return Le slider généré.
	 */
	public static Slider generateSlider(double min, double max, double default_value) {
		Slider slider = new Slider(min, max, default_value);
		slider.setMinWidth(180);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(25f);
		slider.setBlockIncrement(1f);
		return slider;
	}

	/**
	 * Génére une liste déroulante (ComboBox) avec les valeurs spécifies et la positionne aux coordonnées spécifiés.
	 *
	 * @param values 	Les valeurs affiché dans la liste deroulante.
	 * @return La liste déroulante générée.
	 */
	public static <T> ComboBox<T> generateComboBox(T[] values) {
		ComboBox<T> box = new ComboBox<>();
		box.getItems().addAll(values);
		box.setValue(values[0]);
		return box;
	}

	/**
	 * Génére un TextField avec une valeur par défaut et le positionne aux coordonnées spécifiés.
	 *
	 * @param defaultValue 	La valeur par défaut du champ de texte.
	 * @param maxLength 	La longueur maximale du texte autorisé dans le champ.
	 * @param limit1 		Le premier caractère définissant le début de l'ensemble des caractères autorisés
	 * @param limit2 		Le deuxime caractère définissant la fin de l'ensemble des caractères autorisés
	 * @return Le TextField généré.
	 */
	public static TextField generateTextField(String defaultValue,  int maxLength, char limit1, char limit2) {
		TextField tf = new TextField(defaultValue);
		tf.setMaxWidth((double)8*maxLength+30);
		tf.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
				if(!tf.getText().isEmpty()) {
					if (tf.getText().length() > maxLength) {
						String s = tf.getText().substring(0, maxLength);
						tf.setText(s);
					}
					if(tf.getText().charAt(tf.getText().length()-1)<limit1 || tf.getText().charAt(tf.getText().length()-1)>limit2) {
						tf.setText(oldValue);
					}
				}
			}
		});
		return tf;
	}

	/**
	 * Génére un TextField avec une valeur par défaut et le positionne aux coordonnées spécifiés.
	 *
	 * @param defaultValue 	La valeur par défaut du champ de texte.
	 * @return Le TextField généré.
	 */
	public static TextField generateTextField(String defaultValue) { //maxLength devrait être <=16 pour des raisons d'affichage (sinon affichage moins beau)
		TextField tf = new TextField(defaultValue);
		tf.setMaxWidth((double)8*5+30);
		return tf;
	}

	/**
     * Ajoute une vérification pour des valeurs numériques à un champ de texte.
     *
	 * @param tf 	Le champ de texte à vérifier.
	 * @param min 	La valeur minimale autorisée.
	 * @param max 	La valeur maximale autorisée.
	 */
	public static void addCheckNumericalValueToTextField(TextField tf, int min, int max) {
		tf.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(!tf.getText().isEmpty()) {
					try {
						if(Integer.parseInt(tf.getText())<min) {
							tf.setText(""+min);
						}else if(Integer.parseInt(tf.getText())>max) {
							tf.setText(""+max);
						}
					}catch(Exception e) {
						logger.info("Error in the listener of addCheckNumericalValueToTextField");
					}
				}

			}
		});
	}

	/**
	 * Génére un Label utilisé comme titre de menu/fenêtre.
	 *
	 * @param title le titre du menu/fenêtre.
	 * @return Le label généré.
	 */
	public static Label generateTitle(String title) {
		Label label = new Label(" "+title+" ");
		Generators.setLayout(label, 0, 0);
		Generators.applyStyleToTitle(label,Color.BLACK, Color.WHITE);
		label.setTextAlignment(TextAlignment.CENTER);
		return label;
	}

	/**
<<<<<<< HEAD
	 * Génère une fenêtre d'avertissement pour notifier le changement de tour.
	 * 
	 * @param title 		Titre de la fenêtre.
	 * @param text 			Texte d'avertissement de changement de tour.
	 * @param boutonJouer 	Bouton permettant de confirmer le changement de tour.
	 * @return Une fenêtre d'alerte.
	 */
	public static Alert generateAlert(String title, String text, Collection<ButtonType> boutonJouer) {
=======
	 * Génére une alerte (notification)
	 *
	 * @param title le titre de l'alerte.
	 * @param text le texte de l'alerte
	 * @param buttons les boutons de l'alerte
	 * 
	 * @return l'alerte générée.
	 */
	public static Alert generateAlert(String title, String text, Collection<ButtonType> buttons) {
>>>>>>> master
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(text);

		// Personnaliser l'apparence de la boîte de dialogue
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.setMinHeight(Region.USE_PREF_SIZE);

		// Boutons de confirmation et d'annulation
		alert.getButtonTypes().setAll(buttons);

		return alert;
	}
	
	/**
	 * Génére un Dialog (notification avancée)
	 *
	 * @param title le titre du Dialog
	 * @param text le texte du Dialog
	 * @param buttons les boutons du Dialog
	 * @param nodes liste de liste (rows & cols) des nodes qui seront ajoutés à la Grid du Dialog
	 * 
	 * @return l'alerte générée.
	 */
	public static Dialog<ButtonType> generateDialog(String title, String text, Collection<ButtonType> buttons, ArrayList<ArrayList<Node>> nodes) {
		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.setTitle(title);
		dialog.setHeaderText(text);

		GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        for(int index = 0; index<nodes.size(); index++) {
        	for(int i = 0; i<nodes.get(index).size(); i++) {
            	grid.add(nodes.get(index).get(i), i, index);
            }
        }
		dialog.getDialogPane().setContent(grid);
		// Personnaliser l'apparence de la boîte de dialogue
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.setMinHeight(Region.USE_PREF_SIZE);

		// Boutons de confirmation et d'annulation
		dialogPane.getButtonTypes().setAll(buttons);

		return dialog;
	}
	
	 // Méthode pour créer un espace vide avec une taille spécifique
    public static Region createEmptySpace(double size) {
        Region spacer = new Region();
        spacer.setPrefHeight(size);
        spacer.setPrefWidth(size);
        return spacer;
    }


	/**
	 * Positionne un élément aux coordonnées spécifiés.
	 *
	 * @param node 	L'élément à positionner.
	 */
	public static void setLayout(Node node, double x, double y) {
		node.setLayoutX(x);
		node.setLayoutY(y);
	}

	/**
	 * Applique un style particulier à un Label de titre.
	 *
	 * @param label 	un Label titre.
	 * @param bgColor 	Une couleur de fond.
	 * @param textColor Une couleur de police.
	 */
	public static void applyStyleToTitle(Label label, Color bgColor, Color textColor) {
		label.setBackground(Utils.setBackGroungFill(bgColor));
		label.setTextFill(textColor);
		label.setStyle("-fx-font-size: 25px;");
	}

	/**
	 * Applique un style de base à un bouton, y compris le style lors du survol de la souris.
	 *
	 * @param b 		Le bouton auquel on applique le style.
	 * @param inactive 	La couleur en hexadécimal quand le bouton n'est pas en interaction
	 * @param active 	La couleur en hexadécimal quand le bouton est en interaction
	 */
	public static void applyStyleToButton(Button b, Color inactive, Color active) { //inactive = #ffffff & active = #000000
		//Style de base
		b.setStyle("-fx-background-color: "+Utils.convertToHex(active)+";\n"
				+ "-fx-text-fill: "+Utils.convertToHex(inactive)+";\n"
				+ "-fx-font-size: 14px;\n"
				+ "-fx-background-radius: 20px;\n");
		//Style lorsque l'utilisateur passe la souris sur le button
		b.setOnMouseEntered(e ->
			b.setStyle("-fx-background-color: "+Utils.convertToHex(inactive)+";\n"
					+ "-fx-text-fill: "+Utils.convertToHex(active)+";\n"
					+ "-fx-font-size: 14px;\n"
					+ "-fx-background-radius: 20px;\n")
		);
		// Rétablir le style de base lorsque la souris quitte le bouton
		b.setOnMouseExited(e ->
			b.setStyle("-fx-background-color: "+Utils.convertToHex(active)+";\n"
					+ "-fx-text-fill: "+Utils.convertToHex(inactive)+";\n"
					+ "-fx-font-size: 14px;\n"
					+ "-fx-background-radius: 20px;\n")
		);
	}
<<<<<<< HEAD

	/**
	 * Méthode pour générer le fenêtre de sauvegarde de labyrinthe.
	 * 
	 * @param walls 			Le labyrinthe et ses murs.
	 * @param textColor 		Couleur du texte.
	 * @param textLabel 		Couleur des labels.
	 * @param textButton 		Couleur des boutons.
	 * @param textNotification 	Texte de notification de sauvegarde.
	 * @return Hbox de sauvegarde de labyrinthe.
	 */
	public static HBox generateHBoxSaveMap(boolean[][] walls, Color textColor, String textLabel, String textButton, String textNotification) {
    	Label l_saveMap = Generators.generateLabel(textLabel, 0, 0);
		l_saveMap.setTextFill(textColor);
		TextField tf_saveMap = Generators.generateTextField(SaveLoadSystemMaps.DEFAULT_NAME_FOR_MAP_SAVE, 0, 0, 9, 'A', 'z');
		Label notification = Generators.generateLabel(textNotification, 0, 0);
		notification.setTextFill(textColor);
		notification.setVisible(false);
		Button b_saveMap = Generators.generateButton(textButton, 0, 0,Color.WHITE,textColor);
		b_saveMap.setMinWidth(b_saveMap.getPrefWidth());
		b_saveMap.setOnAction(e->{
			try {
				String fileName = tf_saveMap.getText();
				if(fileName.isEmpty()) {
					SaveLoadSystemMaps.saveMap(walls, SaveLoadSystemMaps.DEFAULT_NAME_FOR_MAP_SAVE);
				}else {
					SaveLoadSystemMaps.saveMap(walls, fileName);
				}
				notification.setVisible(true);
			}catch(IOException ioe) {
				System.out.println("ERROR - An error occurred while saving the map.");
				notification.setVisible(false);
			}
		});
		HBox hbox_saveMap = new HBox(10);
		hbox_saveMap.getChildren().addAll(l_saveMap,tf_saveMap,b_saveMap,notification);
		return hbox_saveMap;
    }


=======
>>>>>>> master
}