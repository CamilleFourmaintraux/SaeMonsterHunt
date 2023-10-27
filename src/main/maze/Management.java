package main.maze;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import main.strategy.monster.Monster;
import main.utils.Observer;
import main.utils.Subject;
import main.utils.Utils;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Region;

/**
 * La classe Management représente une fenêtre de gestion de jeu pour le jeu "MONSTER-HUNTER".
 * Elle gère les paramètres du jeu, la création de labyrinthes, et la transition entre les différentes vues du jeu.
 * Elle implémente l'interface Observer pour réagir aux événements du jeu.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 *
 */
public class Management extends Stage implements Observer{
	
	/**
	 * Constante ID du menu settings.
	 */
	final int ID_SETTINGS = 1;
	/**
	 * Constante ID du menu play.
	 */
	final int ID_PLAY = 2;
	/**
	 * Constante utilisé dans les comboBox pour le choix des joueurs.
	 */
	final String[] IA_LEVELS = new String[] {"Player","IA-Easy","IA-Moderate","IA-Hardcore"};
	
	final int SPACING = 5;
	/**
	 * Constante de largeur minimum des labels.
	 */
	final int LABEL_MIN_WIDTH = 120;
	
	/**
	 * Le labyrinthe.
	 */
	protected Maze maze;
	/**
	 * La vue du Monstre.
	 */
	public MonsterView mv;
	/**
	 * La vue du Chasseur.
	 */
	public HunterView hv;
	
	//Affichage
	public int window_height; //500 par dÃ©fault
	public int window_width;//500 par dÃ©fault
	public Color colorOfWalls;
	public Color colorOfFloors;
	/**
	 * Map contenant les différents menus du jeu.
	 */
	public Map<Integer, Scene> menus;
	/**
	 * paramètre indiquant si le jeu se déroule sur la même fenètre (true) ou fenètre séparé (false).
	 */
	public boolean sameScreen;
	
	/**
	 * Constructeur de la classe Management.
	 * 
	 * @param probability le taux de chances que la case du labyrinthe soit un mur plein.
	 * @param maze_height La hauteur du labyrinthe.
	 * @param maze_width La largeur du labyrinthe.
	 * @param window_height La hauteur de la fenêtre.
	 * @param window_width La largeur de la fenêtre.
	 * @param gap_X L'écart horizontal dans la vue du labyrinthe.
	 * @param gap_Y L'écart vertical dans la vue du labyrinthe.
	 * @param zoom Le niveau de zoom de la vue du labyrinthe.
	 * @param colorOfWalls La couleur des murs.
	 * @param colorOfFloors La couleur du sol.
	 */
	public Management(int probability, int maze_height, int maze_width, int window_height, int window_width, int gap_X, int gap_Y, int zoom, Color colorOfWalls, Color colorOfFloors) {
		this.menus=new HashMap<Integer,Scene>();
		this.window_height = window_height;
		this.window_width = window_width;
		this.colorOfFloors=colorOfFloors;
		this.colorOfWalls=colorOfWalls;
		this.sameScreen=true;
		this.generateSettingsMenu();
		this.generatePlayMenu(probability,maze_height,maze_width,gap_X,gap_Y,zoom);
		this.setScene(this.getScene(this.ID_PLAY));
		this.setTitle("MONSTER-HUNTER");
		this.show();
		
	}

	/**
     * Met à jour l'observateur en réagissant à un changement dans le sujet observé.
	 * 
	 * @param s Le sujet observé dont l'état a été modifié.
	 */
	@Override
	public void update(Subject s) {
		if(!this.gameOver()) {
			this.switchInGameView();
		}
	}

	/**
     * Met à jour l'observateur en réagissant à un changement dans le sujet observé avec des données spécifiques.
	 * 
	 * @param s Le sujet observé dont l'état a été modifié.
	 * @param o Un objet contenant des informations spécifiques sur la mise à jour.
	 */
	@Override
	public void update(Subject s, Object o) {
		if(!this.gameOver()) {
			this.switchInGameView();
		}
		
	}
	
	/**
	 * Vérifie si le jeu est terminé.
	 * 
	 * @return true si le jeu est terminé, sinon faux.
	 */
	public boolean gameOver() {
		if(this.maze.isGameOver) {
			this.setScene(this.getScene(this.ID_PLAY));
			maze.isGameOver=false;
			return true;
		}
		return false;
	}

	/**
	 * Affiche une boîte de dialogue indiquant à qui est le tour.
	 * @param joueur Le nom du joueur.
	 */
	public void TurnView(String joueur) {
		this.hide();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Au tour du " + joueur);
		alert.setHeaderText("Voulez vous commencer votre Tour ?");

		// Personnaliser l'apparence de la boÃ®te de dialogue
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.setMinHeight(Region.USE_PREF_SIZE);

		// Boutons de confirmation et d'annulation
		ButtonType bouttonJouer = new ButtonType("Jouer");
		alert.getButtonTypes().setAll(bouttonJouer);

		// Attendre la rÃ©ponse de l'utilisateur
		alert.showAndWait().ifPresent(response -> {
			if(response == bouttonJouer){
				if(joueur.equals("Monstre")){
					this.setScene(mv.scene);
					this.show();
				} else {
					this.setScene(hv.scene);
					this.show();
				}
			}
		});
	}

	/**
 	 * Bascule entre la vue du monstre et la vue du chasseur en fonction du tour actuel du jeu.
 	 */
	public void switchInGameView() {
		if(this.maze.isMonsterTurn) {
			TurnView("Monstre");
		}else {
			TurnView("Chasseur");
		}
	}

	/**
 	 * Génère le menu des paramètres du jeu, permettant à l'utilisateur de personnaliser diverses options telles que
 	 * la taille du labyrinthe, le thème, etc.
 	 */
	public void generateSettingsMenu() {
		Label title = this.generateTitle("Settings");
		
		TextField tf_maze_height = this.generateTextField("10",this.calculPercentage(this.window_width,30), this.calculPercentage(this.window_height,50), 2, '0', '9');
		TextField tf_maze_width = this.generateTextField("10", this.calculPercentage(this.window_width,30), this.calculPercentage(this.window_height,60), 2, '0', '9');
		Label l_height = this.generateLabel("Maze Height", tf_maze_height.getLayoutX()-this.LABEL_MIN_WIDTH, tf_maze_height.getLayoutY(), this.LABEL_MIN_WIDTH);
		Label l_width= this.generateLabel("Maze Width", tf_maze_width.getLayoutX()-this.LABEL_MIN_WIDTH, tf_maze_width.getLayoutY(), this.LABEL_MIN_WIDTH);
		
		Button bScreenType = this.generateButton("Same Screen", this.calculPercentage(this.window_width,35),this.calculPercentage(this.window_height,15));
		bScreenType.setOnAction(e->{
			if(sameScreen) {
				this.sameScreen=false;
				bScreenType.setText("Separate Screen");
			}else {
				this.sameScreen=true;
				bScreenType.setText("Same Screen");
			}
		});
		bScreenType.setMinWidth(150);
		
		ComboBox<String> theme = this.generateComboBox(new String[]{"Cave", "Forest", "Ocean"}, this.calculPercentage(this.window_width, 70), this.calculPercentage(this.window_height,50));
		Label l_theme = this.generateLabel("Choose a theme", theme.getLayoutX()-this.LABEL_MIN_WIDTH,theme.getLayoutY(), this.LABEL_MIN_WIDTH);
		
		Button bBack = this.generateButton("Back", this.calculPercentage(this.window_width, 5), this.calculPercentage(this.window_height,90));
		bBack.setOnAction(e->{
			this.setScene(this.getScene(this.ID_PLAY));
		});
		
		Group group = new Group();
		group.getChildren().addAll(title, l_height, tf_maze_height, l_width, tf_maze_width, bScreenType, l_theme, theme, bBack);
		this.menus.put(Integer.valueOf(this.ID_SETTINGS), new Scene(group, this.window_height, this.window_width, this.colorOfFloors));
	}
	
	
	/**
     * Génère le menu principal du jeu, permettant à l'utilisateur de définir des paramètres pour le jeu
     * (noms des personnages, niveaux d'IA, etc.) et de lancer le jeu lui-même.
	 * 
	 * @param probability le taux de chances que la case du labyrinthe soit un mur plein.
	 * @param maze_height La hauteur du labyrinthe.
	 * @param maze_width La largeur du labyrinthe.
	 * @param gap_X L'espacement horizontal entre les cellules du labyrinthe.
	 * @param gap_Y L'espacement vertical entre les cellules du labyrinthe.
	 * @param zoom Le niveau de zoom pour l'affichage du labyrinthe.
	 */
	public void generatePlayMenu(int probability, int maze_height, int maze_width, int gap_X, int gap_Y, int zoom) {
		Label title = this.generateTitle("Main Menu");
		
		TextField tf_name_monster = this.generateTextField("Monster", this.calculPercentage(this.window_width, 10), this.calculPercentage(this.window_height, 40), 16, 'A', 'z');
		TextField tf_name_hunter = this.generateTextField("Hunter", this.calculPercentage(this.window_width, 60), this.calculPercentage(this.window_height, 40), 16, 'A', 'z');
		
		Label l_nameM = this.generateLabel("Monster Name", tf_name_monster.getLayoutX(),tf_name_monster.getLayoutY()-15, this.LABEL_MIN_WIDTH);
		Label l_nameH = this.generateLabel("Hunter Name", tf_name_hunter.getLayoutX(),tf_name_hunter.getLayoutY()-15, this.LABEL_MIN_WIDTH);
		
		ComboBox<String> choixIA_Monster = this.generateComboBox(this.IA_LEVELS, this.calculPercentage(this.window_width, 10), this.calculPercentage(this.window_height, 50));
		ComboBox<String> choixIA_Hunter = this.generateComboBox(this.IA_LEVELS, this.calculPercentage(this.window_width, 60), this.calculPercentage(this.window_height, 50));
		
		Button bSettings = this.generateButton("Modify Settings", this.calculPercentage(this.window_width, 38), this.calculPercentage(this.window_height,80));
		bSettings.setOnAction(e->{
			this.setScene(this.getScene(this.ID_SETTINGS));
		});
		
		Button bPlay = this.generateButton("PLAY", this.calculPercentage(this.window_width, 45), this.calculPercentage(this.window_height,90));
		bPlay.setOnAction(e->{
			this.maze=new Maze(probability, maze_height, maze_width);
			this.maze=new Maze();
			this.maze.attach(this);
			this.mv=new MonsterView(window_height,window_width,gap_X,gap_Y,zoom,colorOfWalls,colorOfFloors,this.maze);
			this.hv=new HunterView(window_height,window_width,gap_X,gap_Y,zoom,colorOfWalls,colorOfFloors,this.maze);
			this.setScene(hv.scene);
		});
		
		
		
		Group group = new Group();
		group.getChildren().addAll(title, l_nameM, tf_name_monster, choixIA_Monster, l_nameH, tf_name_hunter, choixIA_Hunter, bSettings, bPlay);
		
		this.menus.put(Integer.valueOf(this.ID_PLAY), new Scene(group, this.window_height, this.window_width, this.colorOfFloors));
	}
	
	/**
     * Applique un style de base à un bouton, y compris le style lors du survol de la souris.
	 * 
	 * @param b Le bouton auquel on applique le style.
	 */
	public void applyStyleToButton(Button b) {
		//Style de base
		b.setStyle("-fx-background-color: #3498db;\n"
				+ "    -fx-text-fill: #ffffff;\n"
				+ "    -fx-font-size: 14px;");
		//Style lorsque l'utilisateur passe la souris sur le button
		b.setOnMouseEntered(e -> {
		    b.setStyle("-fx-background-color: #e74c3c;\n"
					+ "    -fx-text-fill: #ffffff;\n"
					+ "    -fx-font-size: 14px;");
		});
		// RÃ©tablir le style de base lorsque la souris quitte le bouton
		b.setOnMouseExited(e -> {
			b.setStyle("-fx-background-color: #3498db;\n"
					+ "    -fx-text-fill: #ffffff;\n"
					+ "    -fx-font-size: 14px;");
		});
	}
	
	/**
     * Génère un bouton avec un texte donné et le positionne aux coordonnées spécifiées.
	 * 
	 * @param msg Le texte affiché sur le bouton.
	 * @param x La position horizontale du bouton.
	 * @param y La position verticale du bouton.
	 * @return Le bouton généré.
	 */
	public Button generateButton(String msg, double x, double y) {
		Button button = new Button(msg);
		//this.download(button);
		this.setLayout(button, x-(button.getWidth()/2) ,y);
		this.applyStyleToButton(button);
		return button;
	}
	
	/**
     * Génère une liste déroulante (ComboBox) avec les valeurs spécifiées et la positionne aux coordonnées spécifiées.
	 * 
	 * @param values Les valeurs à afficher dans la liste déroulante.
	 * @param x La position horizontale de la liste déroulante.
	 * @param y La position verticale de la liste déroulante.
	 * @return La liste déroulante générée.
	 */
	public ComboBox<String> generateComboBox(String[] values, double x, double y) {
		ComboBox<String> theme = new ComboBox<String>();
		theme.getItems().addAll(values);
		theme.setValue(values[0]);
		//this.download(theme);
		this.setLayout(theme, x ,y);
		return theme;
	}
	
	/**
     * Génère un Label avec le texte donné et la positionne aux coordonnées spécifiées.
	 * 
	 * @param msg Le texte affiché sur le label.
	 * @param x La position horizontale du label.
	 * @param y La position verticale du label.
	 * @param minWidth La largeur minimale du label.
	 * @return Le label générée.
	 */
	public Label generateLabel(String msg, double x, double y, double minWidth) {
		Label label = new Label(msg);
		this.setMinWidth(minWidth);
		//this.download(label);
		this.setLayout(label, x,y);
		return label;
	}
	
	/**
     * Génère un TextField avec une valeur par défaut et le positionne aux coordonnées spécifiées.
	 *  
	 * @param defaultValue La valeur par défaut du champ de texte.
	 * @param x La position horizontale du champ de texte.
	 * @param y La position verticale du champ de texte.
	 * @param maxLength La longueur maximale du texte autorisée dans le champ.
	 * @param limit1 Le premier caractère limite autorisé.
	 * @param limit2 Le deuxième caractère limite autorisé.
	 * @return Le TextField généré.
	 */
	public TextField generateTextField(String defaultValue, double x, double y, int maxLength, char limit1, char limit2) { //maxLength devrait Ãªtre <=16 pour des raisons d'affichage (sinon affichage moins beau)
		TextField tf = new TextField(defaultValue);
		tf.setMaxWidth(8*maxLength+30);
		this.setLayout(tf, x,y);
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
	 * Gènère un Label utilisé comme titre de menu/fenêtre.
	 * 
	 * @param title le titre du menu/fenêtre.
	 * @return Le label généré.
	 */
	public Label generateTitle(String title) {
		Label label = new Label(" "+title+" ");
		this.setLayout(label, 0, 0);
		this.applyStyleToTitle(label);
		label.setTextAlignment(TextAlignment.CENTER);
		return label;
	}
	
	/**
     * Applique un style particulier à un Label de titre.
	 * 
	 * @param label un Label titre.
	 */
	public void applyStyleToTitle(Label label) {
		label.setBackground(Utils.setBackGroungFill(this.colorOfWalls));
		label.setStyle("-fx-font-size: 25px;-fx-text-fill: #000000;");
	}
	
	/*
	//Cette fonction semble trÃ¨s Ã©tange, mais elle permet de corriger un bug de javaFX
	public void download(Node node) {
		Group test=new Group();
		test.getChildren().addAll(node);
		Stage stage = new Stage();
		stage.setScene(new Scene(test,10,10));
		stage.show();
		stage.close();
	}
	*/
	
	/**
     * Permet de définir la couleur de fond d'un élément identifié par son ID.
	 * 
	 * @param id L'identifiant de l'élément à modifier.
	 * @param fill La couleur de fond à appliquer.
	 */
	public void setBackGround(int id, Color fill) {
		menus.get(id).setFill(fill);
	}
	
	/**
     * Récupère une scène avec un identifiant donné.
	 * 
	 * @param id L'identifiant de la scène à récupérer.
	 * @return La scène correspondant à l'identifiant spécifié.
	 */
	public Scene getScene(int id) {
		return this.menus.get(Integer.valueOf(id));
	}
	
	/**
     * Positionne un élément aux coordonnées spécifiées.
	 * 
	 * @param node L'élément à positionner.
	 * @param x La position horizontale de l'élément.
	 * @param y La position verticale de l'élément.
	 */
	public void setLayout(Node node, double x, double y) {
		node.setLayoutX(x);
		node.setLayoutY(y);
	}
	
	/**
     * Calcule un pourcentage de la valeur totale donnée.
	 * 
	 * @param total La valeur totale.
	 * @param percentage Le pourcentage à calculer (doit être entre 0 et 100).
	 * @return La valeur résultante du pourcentage calculé.
	 */
	public double calculPercentage(double total, double percentage) {//percentage must be between 0 and 100
		return (percentage/100)*total;
	}
	
	
	
}
