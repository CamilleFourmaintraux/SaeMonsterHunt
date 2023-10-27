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
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;

/**
 * La classe Management reprsente une fentre de gestion de jeu pour le jeu "MONSTER-HUNTER".
 * Elle gre les paramtres du jeu, la cration de labyrinthes, et la transition entre les diffrentes vues du jeu.
 * Elle implmente l'interface Observer pour ragir aux vnements du jeu.
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
	 * Constante utilis dans les comboBox pour le choix des joueurs.
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
	public int window_height; //500 par défault
	public int window_width;//500 par défault
	public Color colorOfWalls;
	public Color colorOfFloors;
	/**
	 * Map contenant les diffrents menus du jeu.
	 */
	public Map<Integer, Scene> menus;
	/**
	 * paramtre indiquant si le jeu se droule sur la mme fentre (true) ou fentre spar (false).
	 */
	public boolean sameScreen;

	/**
	 * Constructeur de la classe Management.
	 *
	 * @param probability le taux de chances que la case du labyrinthe soit un mur plein.
	 * @param maze_height La hauteur du labyrinthe.
	 * @param maze_width La largeur du labyrinthe.
	 * @param window_height La hauteur de la fentre.
	 * @param window_width La largeur de la fentre.
	 * @param gap_X L'cart horizontal dans la vue du labyrinthe.
	 * @param gap_Y L'cart vertical dans la vue du labyrinthe.
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
	 * Met  jour l'observateur en ragissant  un changement dans le sujet observ.
	 *
	 * @param s Le sujet observ dont l'tat a t modifi.
	 */
	@Override
	public void update(Subject s) {
		if(!this.gameOver()) {
			this.switchInGameView();
		}
	}

	/**
	 * Met  jour l'observateur en ragissant  un changement dans le sujet observ avec des donnes spcifiques.
	 *
	 * @param s Le sujet observ dont l'tat a t modifi.
	 * @param o Un objet contenant des informations spcifiques sur la mise  jour.
	 */
	@Override
	public void update(Subject s, Object o) {
		if(!this.gameOver()) {
			this.switchInGameView();
		}

	}

	/**
	 * Vrifie si le jeu est termin.
	 *
	 * @return true si le jeu est termin, sinon faux.
	 */
	public boolean gameOver() {
		if(this.maze.isGameOver) {
			this.hide();
			gameOverScreen();
			maze.isGameOver=false;
			return true;
		}
		return false;
	}

	/**
	 * Affiche une bote de dialogue indiquant  qui est le tour.
	 * @param joueur Le nom du joueur.
	 */
	public void TurnView(String joueur) {
		this.hide();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Au tour du " + joueur);
		alert.setHeaderText("Voulez vous commencer votre Tour ?");

		// Personnaliser l'apparence de la boîte de dialogue
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.setMinHeight(Region.USE_PREF_SIZE);

		// Boutons de confirmation et d'annulation
		ButtonType bouttonJouer = new ButtonType("Jouer");
		alert.getButtonTypes().setAll(bouttonJouer);

		// Attendre la réponse de l'utilisateur
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
	 * Gnre le menu des paramtres du jeu, permettant  l'utilisateur de personnaliser diverses options telles que
	 * la taille du labyrinthe, le thme, etc.
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
	 * Gnre le menu principal du jeu, permettant  l'utilisateur de dfinir des paramtres pour le jeu
	 * (noms des personnages, niveaux d'IA, etc.) et de lancer le jeu lui-mme.
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

		Scene scene =  new Scene(group, this.window_height, this.window_width, this.colorOfFloors);

		this.menus.put(Integer.valueOf(this.ID_PLAY),scene);
	}

	/**
	 * Applique un style de base  un bouton, y compris le style lors du survol de la souris.
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
		// Rétablir le style de base lorsque la souris quitte le bouton
		b.setOnMouseExited(e -> {
			b.setStyle("-fx-background-color: #3498db;\n"
					+ "    -fx-text-fill: #ffffff;\n"
					+ "    -fx-font-size: 14px;");
		});
	}

	/**
	 * Gnre un bouton avec un texte donn et le positionne aux coordonnes spcifies.
	 *
	 * @param msg Le texte affich sur le bouton.
	 * @param x La position horizontale du bouton.
	 * @param y La position verticale du bouton.
	 * @return Le bouton gnr.
	 */
	public Button generateButton(String msg, double x, double y) {
		Button button = new Button(msg);
		//this.download(button);
		this.setLayout(button, x-(button.getWidth()/2) ,y);
		this.applyStyleToButton(button);
		return button;
	}

	/**
	 * Gnre une liste droulante (ComboBox) avec les valeurs spcifies et la positionne aux coordonnes spcifies.
	 *
	 * @param values Les valeurs  afficher dans la liste droulante.
	 * @param x La position horizontale de la liste droulante.
	 * @param y La position verticale de la liste droulante.
	 * @return La liste droulante gnre.
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
	 * Gnre un Label avec le texte donn et la positionne aux coordonnes spcifies.
	 *
	 * @param msg Le texte affich sur le label.
	 * @param x La position horizontale du label.
	 * @param y La position verticale du label.
	 * @param minWidth La largeur minimale du label.
	 * @return Le label gnre.
	 */
	public Label generateLabel(String msg, double x, double y, double minWidth) {
		Label label = new Label(msg);
		this.setMinWidth(minWidth);
		//this.download(label);
		this.setLayout(label, x,y);
		return label;
	}

	/**
	 * Gnre un TextField avec une valeur par dfaut et le positionne aux coordonnes spcifies.
	 *
	 * @param defaultValue La valeur par dfaut du champ de texte.
	 * @param x La position horizontale du champ de texte.
	 * @param y La position verticale du champ de texte.
	 * @param maxLength La longueur maximale du texte autorise dans le champ.
	 * @param limit1 Le premier caractre limite autoris.
	 * @param limit2 Le deuxime caractre limite autoris.
	 * @return Le TextField gnr.
	 */
	public TextField generateTextField(String defaultValue, double x, double y, int maxLength, char limit1, char limit2) { //maxLength devrait être <=16 pour des raisons d'affichage (sinon affichage moins beau)
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
	 * Gnre un Label utilis comme titre de menu/fentre.
	 *
	 * @param title le titre du menu/fentre.
	 * @return Le label gnr.
	 */
	public Label generateTitle(String title) {
		Label label = new Label(" "+title+" ");
		this.setLayout(label, 0, 0);
		this.applyStyleToTitle(label);
		label.setTextAlignment(TextAlignment.CENTER);
		return label;
	}

	/**
	 * Applique un style particulier  un Label de titre.
	 *
	 * @param label un Label titre.
	 */
	public void applyStyleToTitle(Label label) {
		label.setBackground(Utils.setBackGroungFill(this.colorOfWalls));
		label.setStyle("-fx-font-size: 25px;-fx-text-fill: #000000;");
	}

	/*
	//Cette fonction semble très étange, mais elle permet de corriger un bug de javaFX
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
	 * Permet de dfinir la couleur de fond d'un lment identifi par son ID.
	 *
	 * @param id L'identifiant de l'lment  modifier.
	 * @param fill La couleur de fond  appliquer.
	 */
	public void setBackGround(int id, Color fill) {
		menus.get(id).setFill(fill);
	}

	/**
	 * Rcupre une scne avec un identifiant donn.
	 *
	 * @param id L'identifiant de la scne  rcuprer.
	 * @return La scne correspondant  l'identifiant spcifi.
	 */
	public Scene getScene(int id) {
		return this.menus.get(Integer.valueOf(id));
	}

	/**
	 * Positionne un lment aux coordonnes spcifies.
	 *
	 * @param node L'lment  positionner.
	 * @param x La position horizontale de l'lment.
	 * @param y La position verticale de l'lment.
	 */
	public void setLayout(Node node, double x, double y) {
		node.setLayoutX(x);
		node.setLayoutY(y);
	}

	/**
	 * Calcule un pourcentage de la valeur totale donne.
	 *
	 * @param total La valeur totale.
	 * @param percentage Le pourcentage  calculer (doit tre entre 0 et 100).
	 * @return La valeur rsultante du pourcentage calcul.
	 */
	public double calculPercentage(double total, double percentage) {//percentage must be between 0 and 100
		return (percentage/100)*total;
	}

	public void gameOverScreen() {

		Stage gameOverStage = new Stage();

		gameOverStage.setTitle("Game Over Menu");
		gameOverStage.setWidth(this.window_width);
		gameOverStage.setHeight(this.window_height);

		Label title = this.generateTitle("Game Over Menu");

		Button restartButton = this.generateButton("Rejouer", this.calculPercentage(this.window_width, 38), this.calculPercentage(this.window_height, 80));
		Button quitButton = this.generateButton("Quitter", this.calculPercentage(this.window_width, 38), this.calculPercentage(this.window_height, 80));

		restartButton.setOnAction(e -> {
			this.show();
			this.setScene(this.getScene(this.ID_PLAY));
			gameOverStage.close();
		});

		quitButton.setOnAction(e -> {
			System.exit(0);
		});

		StackPane layout = new StackPane();

		// Placez le titre en haut à gauche en définissant l'alignement
		StackPane.setAlignment(title, Pos.TOP_LEFT);

		VBox buttonLayout = new VBox(20);
		buttonLayout.getChildren().addAll(restartButton, quitButton);
		buttonLayout.setAlignment(Pos.CENTER);

		// Superposez le titre et les boutons
		layout.getChildren().addAll(title, buttonLayout);

		Scene sceneButton = new Scene(layout);
		gameOverStage.setScene(sceneButton);

		gameOverStage.show();
	}

}
