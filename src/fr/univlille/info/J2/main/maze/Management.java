/**
 * Le package main.maze contient les classes nécessaires pour la mise en œuvre
 * du jeu Monster Hunt. Il gère la logique du jeu, y compris la gestion du labyrinthe,
 * les déplacements du monstre, le tir du chasseur, et les vues associées.
 */
package fr.univlille.info.J2.main.maze;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.HashMap;
import java.util.Map;

import fr.univlille.info.J2.main.utils.Observer;
import fr.univlille.info.J2.main.utils.Subject;
import fr.univlille.info.J2.main.utils.Utils;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
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
	final int MIN_MAZE_SIZE = 1;
	
	/**
	 * Constante de taille par défault du labyrinthe.
	 */
	final int DEFAULT_MAZE_SIZE = 10;
	
	/**
	 * Constante de taille maximal du labyrinthe.
	 */
	final int MAX_MAZE_SIZE = 75;
	
	/**
	 * Constante de probabilité par défaut d'apparition de murs
	 */
	final int DEFAULT_PROBABILITY = 20;

	
	/**
	 * Constante de taille minimal du labyrinthe.
	 */
	final int MIN_VISION_RANGE = 1;
	
	/**
	 * Constante de taille par défault du labyrinthe.
	 */
	final int DEFAULT_VISION_RANGE = 3;
	
	/**
	 * Constante de taille maximal du labyrinthe.
	 */
	final int MAX_VISION_RANGE = 9;

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
	public double window_height;
	/**
	 * Largeur de la fenêtre (500 oar défaut).
	 */
	public double window_width;
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
	 * taux d'apparition des murs
	 */
	public int vision_range;
	
	/**
	 * indique si le monstre aura une vision limité ou totale du terrain
	 */
	public boolean limitedVision;

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
	
	public Stage viewM;
	public Stage viewH;
	public Stage viewCommon;
	
	/**
	 * Constructeur de la classe Management.
	 *
	 * @param window_height 	La hauteur de la fenetre.
	 * @param window_width 		La largeur de la fenetre.
	 * @param gap_X 			L'écart horizontal dans la vue du labyrinthe. Permet de décaler l'entièreté du labyrinthe sur un axe horizontal.
	 * @param gap_Y 			L'écart vertical dans la vue du labyrinthe.Permet de décaler l'entièreté du labyrinthe sur un axe vertical.
	 */
	public Management(double window_height, double window_width, int gap_X, int gap_Y) {
		this.menus=new HashMap<Integer,Scene>();
		this.window_height = window_height;
		this.window_width = window_width;
		this.colorOfFloors=Color.LIGHTGRAY;
		this.colorOfWalls=Color.DARKGRAY;
		this.colorOfFog=Color.BLACK;
		this.zoom=50;
		
		this.sameScreen=true;
		this.limitedVision=false;
		this.maze_height=this.DEFAULT_MAZE_SIZE;
		this.maze_width=this.DEFAULT_MAZE_SIZE;
		this.probability=this.DEFAULT_PROBABILITY;
		this.vision_range=this.DEFAULT_VISION_RANGE;
		this.theme="Cave";

		this.generateSettingsMenu();
		this.generatePlayMenu(gap_X,gap_Y);
		this.generateGameOverScreen();
		
		this.viewM = new Stage();
		this.viewH = new Stage();
		this.viewCommon = new Stage();
		this.viewCommon.setX(500);
		this.viewCommon.setY(150);
		viewM.setFullScreenExitHint("");
		viewH.setFullScreenExitHint("");
		viewM.setTitle("MONSTERHUNT - MonsterView");
		viewH.setTitle("MONSTERHUNT - HunterView");
		viewCommon.setFullScreenExitHint("");
		viewCommon.setTitle("MONTERHUNT");

		this.setScene(this.getScene(this.ID_PLAY));
		this.setTitle("MONSTERHUNT");
		
		this.setMinHeight(500);
		this.setMinWidth(500);
		
		this.heightProperty().addListener((obs, oldVal, newVal) -> {
			this.window_height = newVal.doubleValue();
		});
		
		this.widthProperty().addListener((obs, oldVal, newVal) -> {
			this.window_width = newVal.doubleValue();
		});
		
	}

	/**
	 * Met à jour l'observateur en réagissant à un changement dans le sujet observé.
	 *
	 * @param s Le sujet observé dont l'état a été modifié.
	 */
	@Override
	public void update(Subject s) {
		if((!this.gameOver())) {
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
		if((!this.gameOver())) {
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
			this.show();
			if(this.sameScreen) {
				this.viewCommon.hide();
			}else {
				this.viewM.hide();
				this.viewH.hide();
			}
			
			maze.isGameOver=false;
			return true;
		}
		return false;
	}

	
	
	public Alert generateAlert(String title, String text, ButtonType boutonJouer) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(text);

		// Personnaliser l'apparence de la boîte de dialogue
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.setMinHeight(Region.USE_PREF_SIZE);

		// Boutons de confirmation et d'annulation
		alert.getButtonTypes().setAll(boutonJouer);
		
		return alert;
	}
	
	
	
	/**
     * Gère le déplacement du monstre par l'IA.
	 */
	public void monsterPlayAt(ICoordinate c) {
		this.maze.move(c);//this.maze.monster.play());
		this.mv.actualize();
		this.setTitle("MONSTERHUNT");

	}
	
	/**
     * Gère le tir du chasseur par l'IA.
	 */
	public void hunterPlayAt(ICoordinate c) {
		this.maze.shoot(c);//this.maze.hunter.play());
		this.hv.actualize();
	}

	/**
	 * Bascule entre la vue du monstre et la vue du chasseur en fonction du tour actuel du jeu.
	 */
	public void switchInGameView() {
		ICoordinate c;
		if(this.maze.isMonsterTurn) {
			this.toMonsterView();
			c = this.maze.monster.play();
			if(c!=null) {
				this.monsterPlayAt(c);
			}
		}else {
			this.toHunterView();
			c = this.maze.hunter.play();
			if(c!=null) {
				this.hunterPlayAt(c);
				this.hv.actualizeCell(c);
			}
		}
	}
	
	/**
	 * Affiche une boite de dialogue indiquant à qui est le tour.
	 * @param joueur Le nom du joueur.
	 */
	public void toHunterView() {
		if(this.sameScreen) {
			this.viewCommon.hide();
			if(this.monster_IA.equals("Player")&&this.hunter_IA.equals("Player")) {
				ButtonType boutonJouer = new ButtonType("Jouer");
				Alert alert = this.generateAlert("Au tour du Chasseur", "Voulez-vous commencer votre tour ?", boutonJouer);// Attendre la réponse de l'utilisateur
				alert.showAndWait().ifPresent(response -> {
					if(response == boutonJouer){
						this.viewCommon.setScene(hv.scene);
						//this.viewCommon.setFullScreen(false);
					}
				});
			}else {
				this.viewCommon.setScene(hv.scene);
			}
			viewCommon.setTitle("MONTERHUNT - HunterView");
			this.viewCommon.show();
		}
		
	}
	
	public void toMonsterView() {
		if(this.sameScreen) {
			this.viewCommon.hide();
			if(this.monster_IA.equals("Player")&&this.hunter_IA.equals("Player")){
				ButtonType boutonJouer = new ButtonType("Jouer");
				Alert alert = this.generateAlert("Au tour du Monstre", "Voulez-vous commencer votre tour ?", boutonJouer);// Attendre la réponse de l'utilisateur
				alert.showAndWait().ifPresent(response -> {
					if(response == boutonJouer){
						this.viewCommon.setScene(mv.scene);
						//this.viewCommon.setFullScreen(false);
					} 
				});
			}else {
				this.viewCommon.setScene(mv.scene);
			}
			viewCommon.setTitle("MONTERHUNT - MonsterView");
			this.viewCommon.show();
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
			this.monster_name=tf_name_monster.getText();
			this.monster_IA=choixIA_Monster.getValue();
			this.hunter_name=tf_name_hunter.getText();
			this.hunter_IA=choixIA_Hunter.getValue();
			
			if(this.window_height>this.window_width) {
				this.zoom=(int)((this.window_width/this.maze_width)/1.2);
			}else {
				this.zoom=(int)((this.window_height/this.maze_height)/1.2);
			}
			
			
			//Creation of the maze
			this.maze=new Maze(this.probability, this.maze_height, this.maze_width, monster_IA, hunter_IA, this.limitedVision, this.vision_range);
			this.maze.attach(this);

			this.mv=new MonsterView(this.window_height,this.window_width+100,gap_X,gap_Y,this.zoom,colorOfWalls,this.colorOfFloors,this.maze,this.monster_name);
			this.hv=new HunterView(this.window_height,this.window_width+100,gap_X,gap_Y,this.zoom,colorOfWalls,this.colorOfFloors,this.colorOfFog,this.maze,this.hunter_name);
			if(this.sameScreen) {
				this.viewCommon.show();
				this.setScene(hv.scene);
			}else {
				this.viewM.setScene(mv.scene);
				this.viewH.setScene(hv.scene);
				this.viewM.show();
				this.viewH.show();
			}
			this.hide();
			this.switchInGameView(); //Ici Vérifie qui joue (IA ou joueur) pour pouvoir démarrer le jeu.
		});

		  VBox TitleVbox = new VBox(20);
		  TitleVbox.getChildren().addAll(title);
		  TitleVbox.setAlignment(Pos.TOP_CENTER);
		  
		 // Créez un layout vertical pour les boutons
		  VBox buttonsLayout = new VBox(20);

		  // Ajoutez les éléments du menu aux boutons
		  buttonsLayout.getChildren().addAll(l_nameM, tf_name_monster, choixIA_Monster, l_nameH, tf_name_hunter, choixIA_Hunter, bSettings, bPlay);

		  // Centrez les boutons horizontalement et verticalement
		  buttonsLayout.setAlignment(Pos.CENTER);

		  // Créez un layout pour le titre et les boutons
		  StackPane root = new StackPane();
		  root.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));

		  // Ajoutez le titre et les boutons au layout
		  root.getChildren().addAll(TitleVbox, buttonsLayout);

		  // Centrez le layout sur l'écran
		  StackPane.setAlignment(root, Pos.CENTER);

		  // Laissez un espace en haut de la page
		  root.setPadding(new Insets(30));
		  
		  // Créez une scène avec le layout
		  Scene scene = new Scene(root, this.window_height, this.window_width, this.colorOfFloors);

		  // Ajoutez la scène aux menus
		  this.menus.put(Integer.valueOf(this.ID_PLAY), scene);

		}
	
	
	/**
	 * Génére le menu des paramètres du jeu, permettant  l'utilisateur de personnaliser diverses options telles que
	 * la taille du labyrinthe, le thème, etc.
	 */
	public void generateSettingsMenu() {
		Label title = this.generateTitle("Settings");

		TextField tf_maze_height = this.generateTextField("10",this.calculPercentage(this.window_width,30), this.calculPercentage(this.window_height,30), 2, '0', '9');
		TextField tf_maze_width = this.generateTextField("10", this.calculPercentage(this.window_width,30), this.calculPercentage(this.window_height,50), 2, '0', '9');
		this.addCheckNumericalValueToTextField(tf_maze_height, this.MIN_MAZE_SIZE, this.MAX_MAZE_SIZE);
		this.addCheckNumericalValueToTextField(tf_maze_width, this.MIN_MAZE_SIZE, this.MAX_MAZE_SIZE);
		
		TextField tf_probability = this.generateTextField("20", this.calculPercentage(this.window_width,30), this.calculPercentage(this.window_height,70), 3, '0', '9');
		this.addCheckNumericalValueToTextField(tf_probability, 0, 100);
		Label l_probability= this.generateLabel("Spawn Rate of walls (%)", tf_probability.getLayoutX()-this.LABEL_MIN_WIDTH-(this.SPACING*8), tf_probability.getLayoutY(), this.LABEL_MIN_WIDTH);
		
		TextField tf_vision = this.generateTextField(""+this.DEFAULT_VISION_RANGE, this.calculPercentage(this.window_width,77), this.calculPercentage(this.window_height,37), 1, '0', '9');
		this.addCheckNumericalValueToTextField(tf_vision, this.MIN_VISION_RANGE, this.MAX_VISION_RANGE);
		Button b_vision = this.generateButton("NO", this.calculPercentage(this.window_width,77),this.calculPercentage(this.window_height,30));
		b_vision.setOnAction(e->{
			if(limitedVision) {
				this.limitedVision=false;
				b_vision.setText("NO");
			}else {
				this.limitedVision=true;
				b_vision.setText("YES");
			}
		});Label l_b_vision= this.generateLabel("Activate limited Vision", b_vision.getLayoutX()-this.LABEL_MIN_WIDTH-(this.SPACING*8), b_vision.getLayoutY(), this.LABEL_MIN_WIDTH);
		Label l_tf_vision= this.generateLabel("Vision Range", tf_vision.getLayoutX()-this.LABEL_MIN_WIDTH-(this.SPACING*8), tf_vision.getLayoutY(), this.LABEL_MIN_WIDTH);
		
		Label l_height = this.generateLabel("Maze Height ("+this.MIN_MAZE_SIZE+"-"+this.MAX_MAZE_SIZE+")", tf_maze_height.getLayoutX()-this.LABEL_MIN_WIDTH-this.SPACING, tf_maze_height.getLayoutY(), this.LABEL_MIN_WIDTH);
		Label l_width= this.generateLabel("Maze Width ("+this.MIN_MAZE_SIZE+"-"+this.MAX_MAZE_SIZE+")", tf_maze_width.getLayoutX()-this.LABEL_MIN_WIDTH-this.SPACING, tf_maze_width.getLayoutY(), this.LABEL_MIN_WIDTH);
		
		Slider slider_height = this.generateSlider(1,75,10,l_height.getLayoutX(),tf_maze_height.getLayoutY()+this.SPACING*5);
		slider_height.valueProperty().addListener(e->{
			tf_maze_height.setText(""+(int)slider_height.getValue());
		});
		
		Slider slider_width = this.generateSlider(1,75,10,l_width.getLayoutX(),tf_maze_width.getLayoutY()+this.SPACING*5);
		slider_width.valueProperty().addListener(e->{
			tf_maze_width.setText(""+(int)slider_width.getValue());
		});
		
		/*setOnMouseReleased(e->{
			tf_maze_height.setText(""+(int)slider_height.getValue());
		});*/
		
		

		Button bScreenType = this.generateButton("Same Screen", -15,50);
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

		ComboBox<String> theme = this.generateComboBox(this.THEMES, this.calculPercentage(this.window_width, 70), this.calculPercentage(this.window_height,70));
		theme.setOnAction(e->{
			this.theme=theme.getValue();
			this.applyTheme(this.theme);
		});
		Label l_theme = this.generateLabel("Choose a theme", theme.getLayoutX()-this.LABEL_MIN_WIDTH,theme.getLayoutY(), this.LABEL_MIN_WIDTH);

		Button bBack = this.generateButton("Back", this.calculPercentage(this.window_width, 5), this.calculPercentage(this.window_height,90));
		bBack.setOnAction(e->{
			this.setScene(this.getScene(this.ID_PLAY));
			if(tf_maze_height.getText().isEmpty()) {
				this.maze_height=this.DEFAULT_MAZE_SIZE;
			}else {
				this.maze_height=Integer.parseInt(tf_maze_height.getText());
			}
			
			if(tf_maze_width.getText().isEmpty()) {
				this.maze_width=this.DEFAULT_MAZE_SIZE;
			}else {
				this.maze_width=Integer.parseInt(tf_maze_width.getText());
			}
			
			if(tf_probability.getText().isEmpty()) {
				this.probability=this.DEFAULT_PROBABILITY;
			}else {
				this.probability=Integer.parseInt(tf_probability.getText());
			}
			if(tf_vision.getText().isEmpty()) {
				this.vision_range=this.DEFAULT_VISION_RANGE;
			}else {
				this.vision_range=Integer.parseInt(tf_vision.getText());
			}
		});

		Group group = new Group();
		group.getChildren().addAll(tf_vision,b_vision,l_b_vision,l_tf_vision,slider_height, slider_width, l_height, tf_maze_height, l_width, tf_maze_width, l_probability, tf_probability, l_theme, theme);
		
		BorderPane bp = new BorderPane(group);
		bp.setPadding(new Insets(30, 30, 30, 30));
		Group top_g = new Group();
		top_g.getChildren().addAll(title,bScreenType);
		bp.setTop(top_g);
		BorderPane.setAlignment(top_g, Pos.TOP_CENTER);
		bp.setBottom(bBack);
		BorderPane.setAlignment(bBack, Pos.BOTTOM_CENTER);
		bp.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));
		this.menus.put(Integer.valueOf(this.ID_SETTINGS), new Scene(bp, this.window_height, this.window_width, this.colorOfFloors));
	}
	
	public Slider generateSlider(double min, double max, double default_value, double x, double y) {
		Slider slider = new Slider(min, max, default_value);
		slider.setLayoutX(x);
		slider.setLayoutY(y);
		slider.setMinWidth(180);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(25f);
		slider.setBlockIncrement(1f);
		return slider;
	}
	
	/**
	 * Génére le menu de GameOver.
	 */
	public void generateGameOverScreen() {
		Label title = this.generateTitle("Game Over Menu");

		Button restartButton = this.generateButton("Rejouer", 0, 0);
		Button quitButton = this.generateButton("Quitter", 0, 0);

		restartButton.setOnAction(e -> {
			this.hide();
			this.setScene(this.getScene(this.ID_PLAY));
			this.show();
		});

		quitButton.setOnAction(e -> {
			System.exit(0);
		});

		StackPane layout = new StackPane();
		layout.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));

		// Placez le titre en haut et au centre de la page 
		StackPane.setAlignment(title, Pos.TOP_CENTER);
		
		// Laissez un espace en haut de la page
		
		VBox vBoxTitle = new VBox(10);
		vBoxTitle.getChildren().addAll(title);
		vBoxTitle.setAlignment(Pos.TOP_CENTER);
		
		VBox buttonLayout = new VBox(20);
		buttonLayout.getChildren().addAll(restartButton, quitButton);
		buttonLayout.setAlignment(Pos.CENTER);
		
		layout.setPadding(new Insets(30));

		// Superposez le titre et les boutons
		layout.getChildren().addAll(vBoxTitle, buttonLayout);
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
		b.setStyle("	-fx-background-color: #000000;\n"
				+ "    	-fx-text-fill: #ffffff;\n"
				+ "    	-fx-font-size: 14px;\n"
				+ "		-fx-background-radius: 20px;\n");
		//Style lorsque l'utilisateur passe la souris sur le button
		b.setOnMouseEntered(e -> {
			b.setStyle("-fx-background-color: #ffffff;\n"
					+ "    -fx-text-fill: #000000;\n"
					+ "    -fx-font-size: 14px;\n"
					+ "		-fx-background-radius: 20px;\n");
		});
		// Rétablir le style de base lorsque la souris quitte le bouton
		b.setOnMouseExited(e -> {
			b.setStyle("	-fx-background-color: #000000;\n"
					+ "    	-fx-text-fill: #ffffff;\n"
					+ "    	-fx-font-size: 14px;\n"
					+ "		-fx-background-radius: 20px;\n");
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
		this.applyStyleToTitle(label,Color.BLACK, Color.WHITE);
		label.setTextAlignment(TextAlignment.CENTER);
		return label;
	}

	/**
	 * Applique un style particulier à un Label de titre.
	 *
	 * @param label un Label titre.
	 */
	public void applyStyleToTitle(Label label, Color bgColor, Color textColor) {
		label.setBackground(Utils.setBackGroungFill(bgColor));
		label.setTextFill(textColor);
		label.setStyle("-fx-font-size: 25px;");
	}

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
