/**
 * Le package main.maze contient les classes nécessaires pour la mise en œuvre
 * du jeu Monster Hunt. Il gère la logique du jeu, y compris la gestion du labyrinthe,
 * les déplacements du monstre, le tir du chasseur, et les vues associées.
 */
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
 * La classe Management représente une fenêtre de gestion de jeu pour le jeu "MONSTER-HUNTER".
 * Elle génère les paramétres du jeu, la création de labyrinthes, et la transition entre les diffèrentes vues du jeu.
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
	 * Constante ID du menu de GameOver.
	 */
	final int ID_GAMEOVER = 3;
	
	/**
	 * Constante utilisée dans les comboBox pour le choix des joueurs.
	 */
	final String[] IA_LEVELS = new String[] {"Player","IA-Easy","IA-Moderate","IA-Hardcore"};
	

	/**
	 * Constante utilisée dans les comboBox pour le choix des themes.
	 */
	final String[] THEMES = new String[] {"Cave","Forest","Ocean"};
	
	/**
	 * Constante pour le décalage lors de la génération des labels
	 */
	final int SPACING = 5;
	
	/**
	 * Constante de largeur minimum des labels.
	 */
	final int LABEL_MIN_WIDTH = 120;
	
	/**
	 * Constante de taille minimal du labyrinthe.
	 */
	final int MIN_MAZE_SIZE = 3;
	
	/**
	 * Constante de taille maximal du labyrinthe.
	 */
	final int MAX_MAZE_SIZE = 75;


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
	/**
	 * Hauteur de la fenêtre (500 oar défaut).
	 */
	public int window_height;
	/**
	 * Largeur de la fenêtre (500 oar défaut).
	 */
	public int window_width;
	/**
	 * Couleur des murs.
	 */
	public Color colorOfWalls;
	/**
	 * Couleur du sol.
	 */
	public Color colorOfFloors;
	/**
	 * Couleur du brouillard pour la vue du chasseur.
	 */
	public Color colorOfFog;
	/**
	 * stocke la hauteur du labyrinthe
	 */
	public int maze_height;
	
	/**
	 * stocke la largeur du labyrinthe
	 */
	public int maze_width;
	
	/**
	 * taux d'apparrition des murs
	 */
	public int probability;
	

	/**
	 * niveau de zoom sur le labyrinthe
	 */
	public int zoom;
	
	/**
	 * stocke le nom du joueur Monster
	 */
	public String monster_name;
	
	/**
	 * stocke le nom du joueur Hunter
	 */
	public String hunter_name;
	
	/**
	 * indique le type de joueur (humain, niveaux de l'ia)
	 */
	public String monster_IA;
	
	/**
	 * indique le type de joueur (humain, niveaux de l'ia)
	 */
	public String hunter_IA;
	
	/**
	 * indique les couleurs avec lesquels le jeu doit s'afficher
	 */
	public String theme;
	
	/**
	 * indique si le jeu se deroule sur la meme fenetre (true) ou sur des fenetres separees (false).
	 */
	public boolean sameScreen;
	
	/**
	 * Map contenant les différents menus du jeu.
	 */
	public Map<Integer, Scene> menus;
	
	/**
	 * Constructeur de la classe Management.
	 *
	 * @param window_height 	La hauteur de la fenetre.
	 * @param window_width 		La largeur de la fenetre.
	 * @param gap_X 			L'écart horizontal dans la vue du labyrinthe. Permet de décaler l'entièreté du labyrinthe sur un axe horizontal.
	 * @param gap_Y 			L'écart vertical dans la vue du labyrinthe.Permet de décaler l'entièreté du labyrinthe sur un axe vertical.
	 */
	public Management(int window_height, int window_width, int gap_X, int gap_Y) {
		this.menus=new HashMap<Integer,Scene>();
		this.window_height = window_height;
		this.window_width = window_width;
		this.colorOfFloors=Color.LIGHTGRAY;
		this.colorOfWalls=Color.DARKGRAY;
		this.colorOfFog=Color.BLACK;
		this.zoom=50;
		
		this.sameScreen=true;
		this.maze_height=10;
		this.maze_width=10;
		this.probability=20;
		this.theme="Cave";

		this.generateSettingsMenu();
		this.generatePlayMenu(gap_X,gap_Y);
		this.generateGameOverScreen();

		this.setScene(this.getScene(this.ID_PLAY));
		this.setTitle("MONSTER-HUNTER");
		
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
			this.setScene(this.getScene(this.ID_GAMEOVER));
			maze.isGameOver=false;
			return true;
		}
		return false;
	}

	/**
	 * Affiche une boite de dialogue indiquant à qui est le tour.
	 * @param joueur Le nom du joueur.
	 */
	public void turnView(String joueur) {
		this.hide();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Au tour du " + joueur);
		alert.setHeaderText("Voulez-vous commencer votre tour ?");

		// Personnaliser l'apparence de la boîte de dialogue
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.setMinHeight(Region.USE_PREF_SIZE);

		// Boutons de confirmation et d'annulation
		ButtonType boutonJouer = new ButtonType("Jouer");
		alert.getButtonTypes().setAll(boutonJouer);

		// Attendre la réponse de l'utilisateur
		alert.showAndWait().ifPresent(response -> {
			if(response == boutonJouer){
				if(joueur.equals("Monstre")){
					this.setScene(mv.scene);
				} else {
					this.setScene(hv.scene);
				}
				this.show();
			}
		});
	}
	
	/**
     * Met en pause l'exécution du jeu pendant un certain nombre de secondes.
	 * @param secondes Le nombre de secondes à attendre.
	 */
	public void wait(int secondes) {
		try {
			Thread.sleep(secondes*1000);
		} catch (InterruptedException e) {
			System.out.println("InterruptedException");
		}
	}
	
	/**
     * Gère le déplacement du monstre par l'IA.
	 */
	public void monsterIAplay() {
			this.maze.move(this.maze.monster.play());
	}
	
	/**
     * Gère le tir du chasseur par l'IA.
	 */
	public void hunterIAplay() {
			this.maze.shoot(this.maze.hunter.play());
	}

	/**
	 * Bascule entre la vue du monstre et la vue du chasseur en fonction du tour actuel du jeu.
	 */
	public void switchInGameView() {
		if(this.hunter_IA.equals("Player")&&this.monster_IA.equals("Player")) {
			if(this.maze.isMonsterTurn) {
				this.turnView("Monstre");
			}else {
				this.turnView("Chasseur");
			}
		}else if(this.hunter_IA.equals("Player")||this.monster_IA.equals("Player")) {
			if(this.maze.isMonsterTurn) {
				if(this.monster_IA.equals("Player")) {
					this.setScene(mv.scene);
				}else {
					this.monsterIAplay();
				}
				
			}else {
				if(this.hunter_IA.equals("Player")) {
					this.setScene(hv.scene);
				}else {
					this.hunterIAplay();
				}
			}
		}else if((!this.hunter_IA.equals("Player")) && (!this.monster_IA.equals("Player"))) {
			if(this.maze.isMonsterTurn) {
				this.monsterIAplay();
			}else {
				this.hunterIAplay();
			}
		}
	}

	

	/**
	 * Génére le menu principal du jeu, permettant  l'utilisateur de définir des paramètres pour le jeu
	 * (noms des personnages, niveaux d'IA, etc.) et de lancer une partie.
	 *
	 * @param gap_X 		L'espacement horizontal entre les cellules du labyrinthe.
	 * @param gap_Y 		L'espacement vertical entre les cellules du labyrinthe.
	 */
	public void generatePlayMenu(int gap_X, int gap_Y) {
		Label title = this.generateTitle("Main Menu");

		TextField tf_name_monster = this.generateTextField("Monster", this.calculPercentage(this.window_width, 10), this.calculPercentage(this.window_height, 40), 16, 'A', 'z');
		TextField tf_name_hunter = this.generateTextField("Hunter", this.calculPercentage(this.window_width, 60), this.calculPercentage(this.window_height, 40), 16, 'A', 'z');

		Label l_nameM = this.generateLabel("Monster Name", tf_name_monster.getLayoutX(),tf_name_monster.getLayoutY()-15, this.LABEL_MIN_WIDTH);
		Label l_nameH = this.generateLabel("Hunter Name", tf_name_hunter.getLayoutX(),tf_name_hunter.getLayoutY()-15, this.LABEL_MIN_WIDTH);
		
		//Label l_invalidSettings = this.generateLabel("Invalid Settings", tf_name_hunter.getLayoutX(),tf_name_hunter.getLayoutY()-15, this.LABEL_MIN_WIDTH);

		ComboBox<String> choixIA_Monster = this.generateComboBox(this.IA_LEVELS, this.calculPercentage(this.window_width, 10), this.calculPercentage(this.window_height, 50));
		ComboBox<String> choixIA_Hunter = this.generateComboBox(this.IA_LEVELS, this.calculPercentage(this.window_width, 60), this.calculPercentage(this.window_height, 50));

		Button bSettings = this.generateButton("Modify Settings", this.calculPercentage(this.window_width, 38), this.calculPercentage(this.window_height,80));
		bSettings.setOnAction(e->{
			this.setScene(this.getScene(this.ID_SETTINGS));
		});

		Button bPlay = this.generateButton("PLAY", this.calculPercentage(this.window_width, 45), this.calculPercentage(this.window_height,90));
		bPlay.setOnAction(e->{
			//intantiation of the settings
			this.monster_name=l_nameM.getText();
			this.monster_IA=choixIA_Monster.getValue();
			this.hunter_name=l_nameH.getText();
			this.hunter_IA=choixIA_Hunter.getValue();
			System.out.println("TEST+"+this.hunter_IA);
			
			this.zoom=(this.window_height/this.maze_height);
			
			//Creation of the maze
			this.maze=new Maze(this.probability, this.maze_height, this.maze_width, monster_IA, hunter_IA);
			this.maze.attach(this);
			this.mv=new MonsterView(window_height,window_width,gap_X,gap_Y,this.zoom,colorOfWalls,colorOfFloors,this.maze);
			this.hv=new HunterView(window_height,window_width,gap_X,gap_Y,this.zoom,colorOfWalls,colorOfFloors,colorOfFog,this.maze);
			this.setScene(hv.scene);
		});



		

		// Placez le titre en haut à gauche en définissant l'alignement
		StackPane.setAlignment(title, Pos.TOP_CENTER);
		
		VBox buttonsLayout = new VBox(20);
		buttonsLayout.getChildren().addAll(l_nameM, tf_name_monster, choixIA_Monster,l_nameH, tf_name_hunter, choixIA_Hunter, bSettings,bPlay);
		StackPane.setAlignment(buttonsLayout, Pos.CENTER);
		
		StackPane root = new StackPane();
		root.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));
		
		root.getChildren().addAll(title,  buttonsLayout);
		
		StackPane.setAlignment(buttonsLayout, Pos.CENTER);
		StackPane.setAlignment(root, Pos.CENTER);

		Scene scene =  new Scene(root, this.window_height, this.window_width, this.colorOfFloors);

		this.menus.put(Integer.valueOf(this.ID_PLAY),scene);
	}
	
	
	/**
	 * Génére le menu des paramètres du jeu, permettant  l'utilisateur de personnaliser diverses options telles que
	 * la taille du labyrinthe, le thème, etc.
	 */
	public void generateSettingsMenu() {
		Label title = this.generateTitle("Settings");

		TextField tf_maze_height = this.generateTextField("10",this.calculPercentage(this.window_width,30), this.calculPercentage(this.window_height,50), 2, '0', '9');
		TextField tf_maze_width = this.generateTextField("10", this.calculPercentage(this.window_width,30), this.calculPercentage(this.window_height,60), 2, '0', '9');
		this.addCheckNumericalValueToTextField(tf_maze_height, this.MIN_MAZE_SIZE, this.MAX_MAZE_SIZE);
		this.addCheckNumericalValueToTextField(tf_maze_width, this.MIN_MAZE_SIZE, this.MAX_MAZE_SIZE);
		
		TextField tf_probability= this.generateTextField("20", this.calculPercentage(this.window_width,77), this.calculPercentage(this.window_height,50), 3, '0', '9');
		this.addCheckNumericalValueToTextField(tf_probability, 0, 100);
		
		Label l_height = this.generateLabel("Maze Height ("+this.MIN_MAZE_SIZE+"-"+this.MAX_MAZE_SIZE+")", tf_maze_height.getLayoutX()-this.LABEL_MIN_WIDTH-this.SPACING, tf_maze_height.getLayoutY(), this.LABEL_MIN_WIDTH);
		Label l_width= this.generateLabel("Maze Width ("+this.MIN_MAZE_SIZE+"-"+this.MAX_MAZE_SIZE+")", tf_maze_width.getLayoutX()-this.LABEL_MIN_WIDTH-this.SPACING, tf_maze_width.getLayoutY(), this.LABEL_MIN_WIDTH);
		
		Label l_probability= this.generateLabel("Spawn Rate of walls (%)", tf_probability.getLayoutX()-this.LABEL_MIN_WIDTH-(this.SPACING*8), tf_probability.getLayoutY(), this.LABEL_MIN_WIDTH);

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

		ComboBox<String> theme = this.generateComboBox(this.THEMES, this.calculPercentage(this.window_width, 70), this.calculPercentage(this.window_height,60));
		theme.setOnAction(e->{
			this.theme=theme.getValue();
			this.applyTheme(this.theme);
		});
		Label l_theme = this.generateLabel("Choose a theme", theme.getLayoutX()-this.LABEL_MIN_WIDTH,theme.getLayoutY(), this.LABEL_MIN_WIDTH);

		Button bBack = this.generateButton("Back", this.calculPercentage(this.window_width, 5), this.calculPercentage(this.window_height,90));
		bBack.setOnAction(e->{
			this.setScene(this.getScene(this.ID_PLAY));
			this.maze_height=Integer.parseInt(tf_maze_height.getText());
			this.maze_width=Integer.parseInt(tf_maze_width.getText());
		});

		Group group = new Group();
		group.getChildren().addAll(title, bScreenType, l_height, tf_maze_height, l_width, tf_maze_width, l_probability, tf_probability, l_theme, theme, bBack);
		this.menus.put(Integer.valueOf(this.ID_SETTINGS), new Scene(group, this.window_height, this.window_width, this.colorOfFloors));
	}
	
	/**
	 * Génére le menu de GameOver.
	 */
	public void generateGameOverScreen() {
		Label title = this.generateTitle("Game Over Menu");

		Button restartButton = this.generateButton("Rejouer", 0, 0);
		Button quitButton = this.generateButton("Quitter", 0, 0);

		restartButton.setOnAction(e -> {
			this.setScene(this.getScene(this.ID_PLAY));
		});

		quitButton.setOnAction(e -> {
			System.exit(0);
		});

		StackPane layout = new StackPane();
		layout.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));

		// Placez le titre en haut à gauche en définissant l'alignement
		StackPane.setAlignment(title, Pos.TOP_LEFT);

		VBox buttonLayout = new VBox(20);
		buttonLayout.getChildren().addAll(restartButton, quitButton);
		buttonLayout.setAlignment(Pos.CENTER);

		// Superposez le titre et les boutons
		layout.getChildren().addAll(title, buttonLayout);
		this.menus.put(Integer.valueOf(this.ID_GAMEOVER), new Scene(layout, this.window_height, this.window_width, this.colorOfFloors));
	}
	
	/**
     * Applique un thème spécifique au jeu en modifiant les couleurs d'affichage.
     * 
	 * @param theme Le nom du thème à appliquer (parmi "Cave", "Forest", "Ocean").
	 */
	public void applyTheme(String theme) {
		if(theme.equals("Cave")) {
			this.colorOfFloors=Color.LIGHTGRAY;
			this.colorOfWalls=Color.DARKGRAY;
			this.colorOfFog=Color.BLACK;
		}else if(theme.equals("Forest")) {
			this.colorOfFloors=Color.LIGHTGREEN;
			this.colorOfWalls=Color.FORESTGREEN;
			this.colorOfFog=Color.DARKGREEN;
		}else if(theme.equals("Ocean")) {
			this.colorOfFloors=Color.AQUAMARINE;
			this.colorOfWalls=Color.SEAGREEN;
			this.colorOfFog=Color.DARKBLUE;
		}
		for(Scene s:this.menus.values()) {
			s.setFill(colorOfFloors);
		}
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
		// Rétablir le style de base lorsque la souris quitte le bouton
		b.setOnMouseExited(e -> {
			b.setStyle("-fx-background-color: #3498db;\n"
					+ "    -fx-text-fill: #ffffff;\n"
					+ "    -fx-font-size: 14px;");
		});
	}

	/**
	 * Génére un bouton avec un texte donné et le positionne aux coordonnées spécifiés.
	 *
	 * @param msg 	Le texte affiché sur le bouton.
	 * @param x 	La position horizontale du bouton.
	 * @param y 	La position verticale du bouton.
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
	 * Génére une liste déroulante (ComboBox) avec les valeurs spécifies et la positionne aux coordonnées spécifiés.
	 *
	 * @param values 	Les valeurs affiché dans la liste deroulante.
	 * @param x 		La position horizontale de la liste deroulante.
	 * @param y 		La position verticale de la liste deroulante.
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
	 * Génére un Label avec le texte donné et la positionne aux coordonnées spécifiés.
	 *
	 * @param msg 		Le texte affiché sur le label.
	 * @param x 		La position horizontale du label.
	 * @param y 		La position verticale du label.
	 * @param minWidth 	La largeur minimale du label.
	 * @return Le label généré.
	 */
	public Label generateLabel(String msg, double x, double y, double minWidth) {
		Label label = new Label(msg);
		this.setMinWidth(minWidth);
		//this.download(label);
		this.setLayout(label, x,y);
		return label;
	}

	/**
	 * Génére un TextField avec une valeur par défaut et le positionne aux coordonnées spécifiés.
	 *
	 * @param defaultValue 	La valeur par défaut du champ de texte.
	 * @param x 			La position horizontale du champ de texte.
	 * @param y 			La position verticale du champ de texte.
	 * @param maxLength 	La longueur maximale du texte autorisé dans le champ.
	 * @param limit1 		Le premier caractère définissant le début de l'ensemble des caractères autorisés
	 * @param limit2 		Le deuxime caractère définissant la fin de l'ensemble des caractères autorisés
	 * @return Le TextField généré.
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
     * Ajoute une vérification pour des valeurs numériques à un champ de texte.
     * 
	 * @param tf 	Le champ de texte à vérifier.
	 * @param min 	La valeur minimale autorisée.
	 * @param max 	La valeur maximale autorisée.
	 */
	public void addCheckNumericalValueToTextField(TextField tf, int min, int max) {
		tf.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(!tf.getText().isEmpty()) {
					if(Integer.parseInt(tf.getText())<min) {
						tf.setText(""+min);
					}else if(Integer.parseInt(tf.getText())>max) {
						tf.setText(""+max);
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
	 * Permet de définir la couleur de fond d'un élément identifié par son ID.
	 *
	 * @param id 	L'identifiant de l'élément a modifié.
	 * @param fill	La couleur de fond appliquée.
	 */
	public void setBackGround(int id, Color fill) {
		menus.get(id).setFill(fill);
	}

	/**
	 * Récupère une scène avec un identifiant donné.
	 *
	 * @param id L'identifiant de la scène que l'on cherche.
	 * @return La scène correspondant à l'identifiant specifié.
	 */
	public Scene getScene(int id) {
		return this.menus.get(Integer.valueOf(id));
	}

	/**
	 * Positionne un élément aux coordonnées spécifiés.
	 *
	 * @param node 	L'élément à positionner.
	 * @param x 	La position horizontale de l'élément.
	 * @param y 	La position verticale de l'élément.
	 */
	public void setLayout(Node node, double x, double y) {
		node.setLayoutX(x);
		node.setLayoutY(y);
	}

	/**
	 * Calcule un pourcentage de la valeur totale donné.
	 *
	 * @param total 		La valeur totale.
	 * @param percentage 	Le pourcentage a calculé (doit être entre 0 et 100).
	 * @return La valeur résultante du pourcentage calculé.
	 */
	public double calculPercentage(double total, double percentage) {//percentage must be between 0 and 100
		return (percentage/100)*total;
	}

}
