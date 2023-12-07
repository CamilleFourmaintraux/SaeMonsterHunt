/**

 * Le package main.maze contient les classes nécessaires pour la mise en œuvre
 * du jeu Monster Hunt. Il gère la logique du jeu, y compris la gestion du labyrinthe,
 * les déplacements du monstre, le tir du chasseur, et les vues associées.
 */
package fr.univlille.info.J2.main.management;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.univlille.info.J2.main.utils.Generators;
import fr.univlille.info.J2.main.utils.Observer;
import fr.univlille.info.J2.main.utils.SaveLoadSystemMaps;
import fr.univlille.info.J2.main.utils.Subject;
import fr.univlille.info.J2.main.utils.Utils;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
	 * Constante ID de la scene d'attente entre deux joueurs.
	 */
	final int ID_WAIT = 0;

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
	 * Constante ID du menu de settings-Miscellaneous.
	 */
	final int ID_MISCELLANEOUS_SETTINGS = 4;

	/**
	 * Constante ID du menu de settings-Hunter.
	 */
	final int ID_MAZE_SETTINGS = 5;

	/**
	 * Constante ID du menu de settings-Monster.
	 */
	final int ID_MONSTER_SETTINGS = 6;

	/**
	 * Constante ID du menu de settings-Hunter.
	 */
	final int ID_HUNTER_SETTINGS = 7;

	/**
	 * Constante ID de l'éditeur de labyrinthe.
	 */
	final int ID_MAZE_EDITOR = 8;

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
	 * Constante pour la taille minimale des fenêtres
	 */
	final int WINDOWS_MIN_SIZE = 500;

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
	final int MAX_MAZE_SIZE = 50;

	/**
	 * Constante de probabilité par défaut d'apparition de murs
	 */
	final int DEFAULT_PROBABILITY = 20;

	/**
	 * Constante de la portée de déplacement du monstre
	 */
	final int DEFAULT_MOVING_RANGE = 1;


	/**
	 * Constante de la portée minimale de la vision du monstre
	 */
	final int MIN_VISION_RANGE = 1;

	/**
	 * Constante de la portéepar défault de la vision du monstre
	 */
	final int DEFAULT_VISION_RANGE = 3;

	/**
	 * Constante de la portée maximale de la vision du monstre
	 */
	final int MAX_VISION_RANGE = 9;


	/**
	 * Constante de la portée par défault de la vision bonus du chasseur
	 * Les constantes MIN_BONUS_RANGE ni MAX_BONUS_RANGEont étés retirés car elles n'étaient pas utiles.
	 */
	final int DEFAULT_BONUS_RANGE = 0;

	/**
	 * Hauteur de la fenêtre (500 oar défaut).
	 */
	public double window_height;
	/**
	 * Largeur de la fenêtre (500 oar défaut).
	 */
	public double window_width;

	/**
	 * Position x de la fenetre
	 */
	public double window_x;
	/**
	 * Position y de la fenetre
	 */
	public double window_y;


	/**
	 * Le labyrinthe.
	 */
	protected Maze maze;

	/**
	 * La vue sur
	 */
	public MazeEditor mEdit;

	/**
	 * La vue du Monstre.
	 */
	public MonsterView mv;

	/**
	 * La vue du Chasseur.
	 */
	public HunterView hv;

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
	 * portée de déplacement du monstre
	 */
	public int moving_range;

	/**
	 * portée de vision du monstre
	 */
	public int vision_range;

	/**
	 * indique si le monstre aura une vision limité ou totale du terrain
	 */
	public boolean limitedVision;

	/**
	 * portée de vision bonus du chasseur
	 */
	public int bonus_range;


	/**
	 * niveau de zoom sur le labyrinthe
	 */
	public int zoom;

	/**
	 * décalement horizontal du labyrinthe
	 */
	public int gap_X;

	/**
	 * décalement vertical du labyrinthe
	 */
	public int gap_Y;

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
	 * Vue du Monstre.
	 */
	public Stage viewM;
	/**
	 * Vue du Chasseur.
	 */
	public Stage viewH;
	/**
	 * Vue commune (ex: Menu principal)
	 */
	public Stage viewCommon;

	/**
	 * 
	 */
	public boolean isGenerationRandom;

	/**
	 * Objet File représentant un labyrinthe personnnalisé et importé par le joueur.
	 */
	public File importedmap;

	/**
	 * Constructeur de la classe Management.
	 *
	 * @param window_height 	La hauteur de la fenetre.
	 * @param window_width 		La largeur de la fenetre.
	 * @param gap_X 			L'écart horizontal dans la vue du labyrinthe. Permet de décaler l'entièreté du labyrinthe sur un axe horizontal.
	 * @param gap_Y 			L'écart vertical dans la vue du labyrinthe.Permet de décaler l'entièreté du labyrinthe sur un axe vertical.
	 */
	public Management(double window_height, double window_width, int gap_X, int gap_Y) {
		this.menus=new HashMap<>();
		this.window_height = window_height;
		this.window_width = window_width;
		this.colorOfFloors=Color.LIGHTGRAY;
		this.colorOfWalls=Color.DARKGRAY;
		this.colorOfFog=Color.BLACK;

		this.zoom=30;
		this.gap_X=gap_X;
		this.gap_Y=gap_Y;

		this.sameScreen=true;
		this.limitedVision=false;
		this.maze_height=this.DEFAULT_MAZE_SIZE;
		this.maze_width=this.DEFAULT_MAZE_SIZE;
		this.probability=this.DEFAULT_PROBABILITY;
		this.vision_range=this.DEFAULT_VISION_RANGE;
		this.moving_range=this.DEFAULT_MOVING_RANGE;
		this.bonus_range=this.DEFAULT_BONUS_RANGE;
		this.theme="Cave";
		this.isGenerationRandom=true;

		this.generateWaitingNextPlayer();
		this.generateSettingsMiscellaneous();
		this.generateSettingsMaze();
		this.generateSettingsMonster();
		this.generateSettingsHunter();
		this.generateSettingsMainMenu();
		this.generateMazeEditor();
		this.generatePlayMenu();
		this.generateGameOverScreen();

		this.viewM = new Stage();
		this.viewH = new Stage();
		this.viewCommon = new Stage();
		viewM.setFullScreenExitHint("");
		viewH.setFullScreenExitHint("");
		viewM.setTitle("MONSTERHUNT - MonsterView");
		viewH.setTitle("MONSTERHUNT - HunterView");
		viewCommon.setFullScreenExitHint("");
		viewCommon.setTitle("MONTERHUNT");

		this.setScene(this.getScene(this.ID_PLAY));
		this.setTitle("MONSTERHUNT");

		this.setMinHeight(this.WINDOWS_MIN_SIZE);
		this.setMinWidth(this.WINDOWS_MIN_SIZE);
		this.viewCommon.setMinHeight(this.WINDOWS_MIN_SIZE);
		this.viewCommon.setMinWidth(this.WINDOWS_MIN_SIZE);
		this.viewH.setMinHeight(this.WINDOWS_MIN_SIZE);
		this.viewH.setMinWidth(this.WINDOWS_MIN_SIZE);
		this.viewM.setMinHeight(this.WINDOWS_MIN_SIZE);
		this.viewM.setMinWidth(this.WINDOWS_MIN_SIZE);

		this.heightProperty().addListener((obs, oldVal, newVal) -> {
				this.window_height = newVal.doubleValue();
		});

		this.widthProperty().addListener((obs, oldVal, newVal) -> {
				this.window_width = newVal.doubleValue();

		});

		this.xProperty().addListener((obs, oldVal, newVal)->{
				this.window_x=newVal.doubleValue();

		});

		this.yProperty().addListener((obs, oldVal, newVal)->{
				this.window_y=newVal.doubleValue();
		});

		this.viewCommon.heightProperty().addListener((obs, oldVal, newVal) -> {
				this.window_height = newVal.doubleValue();
		});

		this.viewCommon.widthProperty().addListener((obs, oldVal, newVal) -> {
				this.window_width = newVal.doubleValue();

		});

		this.viewCommon.xProperty().addListener((obs, oldVal, newVal)->{
				this.window_x=newVal.doubleValue();
		});

		this.viewCommon.yProperty().addListener((obs, oldVal, newVal)->{
				this.window_y=newVal.doubleValue();
		});

		this.sceneProperty().addListener(e->{
			this.setX(this.window_x);
			this.setY(this.window_y);
			this.setWidth(this.window_width);
			this.setHeight(this.window_height);
		});

		this.viewCommon.sceneProperty().addListener(e->{
			this.viewCommon.setX(this.window_x);
			this.viewCommon.setY(this.window_y);
			this.viewCommon.setWidth(this.window_width);
			this.viewCommon.setHeight(this.window_height);
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
			this.setHeight(this.window_height);
			this.setWidth(this.window_width);
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

	/**
     * Gère le déplacement du monstre par l'IA.
     * 
     * @param c Coordonnée à laquelle le monstre veut se déplacer.
	 */
	public void monsterPlayAt(ICoordinate c) {
		this.maze.move(c);//this.maze.monster.play());
		this.mv.actualize();
		this.setTitle("MONSTERHUNT");

	}

	/**
     * Gère le tir du chasseur par l'IA.
     * 
     * @param c Coordonnée à laquelle le chasseur veut tirer.
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
			}
		}
	}

	/**
	 * Affiche une boite d'avertissement indiquant que c'est le tour du chasseur.
	 */
	public void toHunterView() {
		if(this.sameScreen) {
			if(this.monster_IA.equals("Player")&&this.hunter_IA.equals("Player")) {
				//this.viewCommon.hide();
				this.viewCommon.setScene(this.getScene(ID_WAIT));
				ArrayList<ButtonType> alb = new ArrayList<>();
				ButtonType boutonJouer = new ButtonType("Play");
				alb.add(boutonJouer);
				Alert alert = Generators.generateAlert("It’s the Hunter’s turn", "Do you want to start your turn?", alb);// Attendre la réponse de l'utilisateur
				/*alert.setOnCloseRequest(e->{
					maze.triggersGameOver();
				});*///BUG A REVOIR //TODO
				alert.showAndWait().ifPresent(response -> {
					if(response == boutonJouer){
						this.viewCommon.setScene(hv.scene);
					}
				});
				//this.viewCommon.show();
			}else {
				this.viewCommon.setScene(hv.scene);
			}
			viewCommon.setTitle("MONTERHUNT - HunterView");
		}

	}
	
	/**
	 * Affiche une boite d'avertissement indiquant que c'est le tour du monstre.
	 */
	public void toMonsterView() {
		if(this.sameScreen) {
			if(this.monster_IA.equals("Player")&&this.hunter_IA.equals("Player")){
				//this.viewCommon.hide();
				this.viewCommon.setScene(this.getScene(this.ID_WAIT));
				ArrayList<ButtonType> alb = new ArrayList<>();
				ButtonType boutonJouer = new ButtonType("Play");
				alb.add(boutonJouer);
				Alert alert = Generators.generateAlert("It’s the Monster’s turn", "Do you want to start your turn?", alb);// Attendre la réponse de l'utilisateur
				/*alert.setOnCloseRequest(e->{
					maze.triggersGameOver();
				});*///BUG A REVOIR //TODO;
				alert.showAndWait().ifPresent(response -> {
					if(response == boutonJouer){
						this.viewCommon.setScene(mv.scene);
					}
				});
				//this.viewCommon.show();
			}else {
				this.viewCommon.setScene(mv.scene);
			}
			viewCommon.setTitle("MONTERHUNT - MonsterView");
		}
	}


	/**
	 * Génére le menu principal du jeu, permettant  l'utilisateur de définir des paramètres pour le jeu
	 * (noms des personnages, niveaux d'IA, etc.) et de lancer une partie.
	 */
	public void generatePlayMenu() {

		Label title = Generators.generateTitle("Main Menu");

		TextField tf_name_monster = Generators.generateTextField("Monster", this.calculPercentage(this.window_width, 10), this.calculPercentage(this.window_height, 40), 16, 'A', 'z');
		TextField tf_name_hunter = Generators.generateTextField("Hunter", this.calculPercentage(this.window_width, 60), this.calculPercentage(this.window_height, 40), 16, 'A', 'z');

		Label l_nameM = Generators.generateLabel("Monster Name", tf_name_monster.getLayoutX(),tf_name_monster.getLayoutY()-15);
		Label l_nameH = Generators.generateLabel("Hunter Name", tf_name_hunter.getLayoutX(),tf_name_hunter.getLayoutY()-15);

		//Label l_invalidSettings = Generators.generateLabel("Invalid Settings", tf_name_hunter.getLayoutX(),tf_name_hunter.getLayoutY()-15, this.LABEL_MIN_WIDTH);

		ComboBox<String> choixIA_Monster = Generators.generateComboBox(this.IA_LEVELS, this.calculPercentage(this.window_width, 10), this.calculPercentage(this.window_height, 50));
		ComboBox<String> choixIA_Hunter = Generators.generateComboBox(this.IA_LEVELS, this.calculPercentage(this.window_width, 60), this.calculPercentage(this.window_height, 50));

		Button bPlay = Generators.generateButton("PLAY", this.calculPercentage(this.window_width, 45), this.calculPercentage(this.window_height,90),Color.WHITE, Color.BLACK);
		bPlay.setOnAction(e->{
			//intantiation of the settings
			this.monster_name=tf_name_monster.getText();
			this.monster_IA=choixIA_Monster.getValue();
			this.hunter_name=tf_name_hunter.getText();
			this.hunter_IA=choixIA_Hunter.getValue();

			//Adaptation du zoom
			if(this.window_height>this.window_width) {
				this.zoom=(int)((this.window_width/(this.maze_height+this.maze_width)));
			}else {
				this.zoom=(int)((this.window_height/(this.maze_height+this.maze_width)));
			}

			//Creation of the maze
			if(this.isGenerationRandom) {
				this.maze = new Maze(this.probability, this.maze_height, this.maze_width, monster_IA, hunter_IA, this.limitedVision, this.vision_range, this.moving_range, this.bonus_range);
			}else {
				try {
					this.maze = new Maze(SaveLoadSystemMaps.loadMap(this.importedmap), monster_IA, hunter_IA, this.limitedVision, this.vision_range, this.moving_range, this.bonus_range);
				} catch (Exception exception) {
					this.maze = new Maze(this.probability, this.maze_height, this.maze_width, monster_IA, hunter_IA, this.limitedVision, this.vision_range, this.moving_range, this.bonus_range);
				}
			}
			this.maze.attach(this);

			this.mv=new MonsterView(this.window_height,this.window_width+100,this.gap_X,this.gap_Y,this.zoom,this.colorOfWalls,this.colorOfFloors,this.colorOfFog,this.maze,this.monster_name);
			this.hv=new HunterView(this.window_height,this.window_width+100,this.gap_X,this.gap_Y,this.zoom,this.colorOfWalls,this.colorOfFloors,this.colorOfFog,this.maze,this.hunter_name);
			if(this.sameScreen) {
				this.viewCommon.setScene(hv.scene);
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

		Button bSettings = Generators.generateButton("Modify Settings", this.calculPercentage(this.window_width, 38), this.calculPercentage(this.window_height,80),Color.WHITE, Color.BLACK);
		bSettings.setOnAction(e->{
			this.setScene(this.getScene(this.ID_SETTINGS));
		});

		Button bQuit = Generators.generateButton("Quitter", 0, 0,Color.WHITE, Color.BLACK);
		bQuit.setOnAction(e -> {
			System.exit(0);
		});


		  VBox TitleVbox = new VBox(20);
		  TitleVbox.getChildren().addAll(title);
		  TitleVbox.setAlignment(Pos.TOP_CENTER);

		 // Créez un layout vertical pour les boutons
		  VBox buttonsLayout = new VBox(15);

		  // Ajoutez les éléments du menu aux boutons
		  buttonsLayout.getChildren().addAll(l_nameM, tf_name_monster, choixIA_Monster, l_nameH, tf_name_hunter, choixIA_Hunter, bPlay, bSettings, bQuit);

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
	 * Génére la scene s'affichant
	 */
	public void generateWaitingNextPlayer() {

		Label label = new Label("Waiting for the next player.");
		label.setTextFill(Color.WHITE);

		BorderPane root = new BorderPane(label);
		root.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));

		this.menus.put(Integer.valueOf(this.ID_WAIT), new Scene(root, this.window_height, this.window_width, Color.BLACK));
	}


	/**
	 * Génére le menu des paramètres du jeu, permettant  l'utilisateur de personnaliser diverses options telles que
	 * la taille du labyrinthe, le thème, etc.
	 */
	public void generateSettingsMainMenu() {
		Label title = Generators.generateTitle("Settings");

		Button toMisc = Generators.generateButton("Screen", this.calculPercentage(this.window_width, 5), this.calculPercentage(this.window_height,30),Color.WHITE, Color.BLACK);
		toMisc.setOnAction(e->{
			this.setScene(this.getScene(this.ID_MISCELLANEOUS_SETTINGS));
		});
		toMisc.setMinWidth(150);

		Button toMaze = Generators.generateButton("Maze", this.calculPercentage(this.window_width, 5), this.calculPercentage(this.window_height,40),Color.WHITE, Color.BLACK);
		toMaze.setOnAction(e->{
			this.setScene(this.getScene(this.ID_MAZE_SETTINGS));
		});
		toMaze.setMinWidth(150);

		Button toMons = Generators.generateButton("Monster", this.calculPercentage(this.window_width, 5), this.calculPercentage(this.window_height,50),Color.WHITE, Color.BLACK);
		toMons.setOnAction(e->{
			this.setScene(this.getScene(this.ID_MONSTER_SETTINGS));
		});
		toMons.setMinWidth(150);

		Button toHunt = Generators.generateButton("Hunter", this.calculPercentage(this.window_width, 5), this.calculPercentage(this.window_height,60),Color.WHITE, Color.BLACK);
		toHunt.setOnAction(e->{
			this.setScene(this.getScene(this.ID_HUNTER_SETTINGS));
		});
		toHunt.setMinWidth(150);

		Button bBack = Generators.generateButton("Back", this.calculPercentage(this.window_width, 5), this.calculPercentage(this.window_height,90),Color.WHITE, Color.BLACK);
		bBack.setOnAction(e->{
			this.setScene(this.getScene(this.ID_PLAY));
		});

		VBox vbox = new VBox(30);
		vbox.getChildren().addAll(toMisc,toMaze,toMons,toHunt);
		Group group = new Group(vbox);

		BorderPane bp = new BorderPane(group);
		bp.setPadding(new Insets(30, 30, 30, 30));
		bp.setTop(title);
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		bp.setBottom(bBack);
		BorderPane.setAlignment(bBack, Pos.BOTTOM_CENTER);
		bp.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));
		this.menus.put(Integer.valueOf(this.ID_SETTINGS), new Scene(bp, this.window_height, this.window_width, this.colorOfFloors));
	}


	/**
	 * Génére le menu des paramètres gérant des paramètres généreaux
	 */
	public void generateSettingsMiscellaneous() {
		Label title = Generators.generateTitle("Settings - Miscellaneous");
		Button bScreenType = Generators.generateButton("Same Screen", 0,-50,Color.WHITE, Color.BLACK);
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
		Label l_screenType = Generators.generateLabel("Choose a display mode", 0, -70);


		ComboBox<String> theme = Generators.generateComboBox(this.THEMES, this.calculPercentage(this.window_width, 70), this.calculPercentage(this.window_height,70));
		theme.setOnAction(e->{
			this.theme=theme.getValue();
			this.applyTheme(this.theme);
		});
		Label l_theme = Generators.generateLabel("Choose a theme", theme.getLayoutX()-this.LABEL_MIN_WIDTH,theme.getLayoutY());

		Button bBack = Generators.generateButton("Back", this.calculPercentage(this.window_width, 5), this.calculPercentage(this.window_height,90),Color.WHITE, Color.BLACK);
		bBack.setOnAction(e->{
			this.setScene(this.getScene(this.ID_SETTINGS));
		});

		VBox vbox = new VBox(10);
		vbox.getChildren().addAll(l_theme, theme);
		Group group = new Group();
		group.getChildren().addAll(l_screenType,bScreenType,vbox);

		BorderPane bp = new BorderPane(group);
		bp.setPadding(new Insets(30, 30, 30, 30));
		bp.setTop(title);
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		bp.setBottom(bBack);
		BorderPane.setAlignment(bBack, Pos.BOTTOM_CENTER);
		bp.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));
		this.menus.put(Integer.valueOf(this.ID_MISCELLANEOUS_SETTINGS), new Scene(bp, this.window_height, this.window_width, this.colorOfFloors));
	}

	/**
	 * Génére le menu des paramètres gérant le labyrinthe
	 */
	public void generateSettingsMaze() {

		Label title = Generators.generateTitle("Settings - Maze");

		Label lGen = Generators.generateLabel("Set the generation mode :", 0,0);

		Button bGen = Generators.generateButton("Random", this.calculPercentage(this.window_width, 10), this.calculPercentage(this.window_height,20),Color.WHITE, Color.BLACK);
		bGen.setMinWidth(100);

		TextField tf_height = Generators.generateTextField("10",this.calculPercentage(this.window_width,30), this.calculPercentage(this.window_height,30));//, 2, '0', '9');
		TextField tf_width = Generators.generateTextField("10", this.calculPercentage(this.window_width,30), this.calculPercentage(this.window_height,50));//, 2, '0', '9');
		Generators.addCheckNumericalValueToTextField(tf_height, this.MIN_MAZE_SIZE, this.MAX_MAZE_SIZE);
		Generators.addCheckNumericalValueToTextField(tf_width, this.MIN_MAZE_SIZE, this.MAX_MAZE_SIZE);

		TextField tf_probability = Generators.generateTextField("20", this.calculPercentage(this.window_width,30), this.calculPercentage(this.window_height,70), 3, '0', '9');
		Generators.addCheckNumericalValueToTextField(tf_probability, 0, 100);
		Label l_probability= Generators.generateLabel("Spawn Rate of walls (%)", tf_probability.getLayoutX()-this.LABEL_MIN_WIDTH-(this.SPACING*8), tf_probability.getLayoutY());

		Label l_height = Generators.generateLabel("Maze Height ("+this.MIN_MAZE_SIZE+"-"+this.MAX_MAZE_SIZE+")", tf_height.getLayoutX()-this.LABEL_MIN_WIDTH-this.SPACING, tf_height.getLayoutY());
		Label l_width= Generators.generateLabel("Maze Width ("+this.MIN_MAZE_SIZE+"-"+this.MAX_MAZE_SIZE+")", tf_width.getLayoutX()-this.LABEL_MIN_WIDTH-this.SPACING, tf_width.getLayoutY());

		Slider slider_height = Generators.generateSlider(this.MIN_MAZE_SIZE,this.MAX_MAZE_SIZE,this.DEFAULT_MAZE_SIZE,l_height.getLayoutX(),tf_height.getLayoutY()+this.SPACING*5);
		slider_height.valueProperty().addListener(e->{
			tf_height.setText(""+(int)slider_height.getValue());
			this.maze_height=(int)slider_height.getValue();
		});
		tf_height.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {

					if(!tf_height.getText().isEmpty()) {
						if (tf_height.getText().length() > 2) {
							String s = tf_height.getText().substring(0, 2);
							tf_height.setText(s);
						}
						if(tf_height.getText().charAt(tf_height.getText().length()-1)<'0' || tf_height.getText().charAt(tf_height.getText().length()-1)>'9') {
							tf_height.setText(oldValue);
						}
						slider_height.setValue(Integer.parseInt(tf_height.getText()));
						maze_height=Integer.parseInt(tf_height.getText());
					}else {
						slider_height.setValue(DEFAULT_MAZE_SIZE);
						maze_height=DEFAULT_MAZE_SIZE;
						tf_height.setText(""+DEFAULT_MAZE_SIZE);
					}
				}
			});

		Slider slider_width = Generators.generateSlider(this.MIN_MAZE_SIZE,this.MAX_MAZE_SIZE,this.DEFAULT_MAZE_SIZE,l_width.getLayoutX(),tf_width.getLayoutY()+this.SPACING*5);
		slider_width.valueProperty().addListener(e->{
			tf_width.setText(""+(int)slider_width.getValue());
			this.maze_width=(int)slider_width.getValue();
		});

		tf_width.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
				if(!tf_width.getText().isEmpty()) {
					if (tf_width.getText().length() > 2) {
						String s = tf_width.getText().substring(0, 2);
						tf_width.setText(s);
					}
					if(tf_width.getText().charAt(tf_width.getText().length()-1)<'0' || tf_width.getText().charAt(tf_width.getText().length()-1)>'9') {
						tf_width.setText(oldValue);
					}
					slider_width.setValue(Integer.parseInt(tf_width.getText()));
					maze_width=Integer.parseInt(tf_width.getText());
				}else {
					slider_width.setValue(DEFAULT_MAZE_SIZE);
					maze_width=DEFAULT_MAZE_SIZE;
					tf_width.setText(""+DEFAULT_MAZE_SIZE);
				}
			}
		});

		Button bEditor = Generators.generateButton("Maze Editor", this.calculPercentage(this.window_width, 5), this.calculPercentage(this.window_height,90),Color.WHITE, Color.BLACK);
		bEditor.setOnAction(e->{
			this.setScene(this.getScene(this.ID_MAZE_EDITOR));
		});

		Text message = new Text("No file selected");

		//Création du gestionnaire de fcihier
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose a map to import");
		// Créer un filtre pour les fichiers .dat
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers DAT (*.dat)", "*.dat");
        fileChooser.getExtensionFilters().add(extFilter);
        // Définir le répertoire initial du FileChooser
        File initialDirectory = new File(SaveLoadSystemMaps.MAZES_DIRECTORY);
        fileChooser.setInitialDirectory(initialDirectory);

		Button bImport = Generators.generateButton("Import", this.calculPercentage(this.window_width, 5), this.calculPercentage(this.window_height,90),Color.WHITE,Color.BLACK);
		bImport.setOnAction(e->{
			this.importedmap = fileChooser.showOpenDialog(this);
			if(this.importedmap==null) {
				message.setText("Operation canceled : no file selected");
			}else {
				message.setText("File selected : "+this.importedmap.getName());
			}

		});

		Button bBack = Generators.generateButton("Back", this.calculPercentage(this.window_width, 5), this.calculPercentage(this.window_height,90),Color.WHITE, Color.BLACK);
		bBack.setOnAction(e->{
			this.setScene(this.getScene(this.ID_SETTINGS));
			if(tf_probability.getText().isEmpty()) {
				this.probability=this.DEFAULT_PROBABILITY;
				tf_probability.setText(""+this.DEFAULT_PROBABILITY);
			}else {
				this.probability=Integer.parseInt(tf_probability.getText());
			}
		});



		Group g_height = new Group();
		g_height.getChildren().addAll(l_height, tf_height,slider_height);
		Group g_width = new Group();
		g_width.getChildren().addAll(l_width, tf_width, slider_width);

		VBox settingsForRandom = new VBox(10);
		settingsForRandom.getChildren().addAll(g_height,g_width,l_probability, tf_probability);
		settingsForRandom.setAlignment(Pos.CENTER);
		settingsForRandom.setPadding(new Insets(30));

		VBox settingsForImport = new VBox(10);
		settingsForImport.getChildren().addAll(message,bImport,bEditor);
		settingsForImport.setAlignment(Pos.CENTER);
		settingsForImport.setPadding(new Insets(30));

		//Valeurs par défaults
		settingsForRandom.setVisible(true);
		settingsForImport.setVisible(false);

		bGen.setOnAction(e->{
			if(this.isGenerationRandom) {
				this.isGenerationRandom=false;
				bGen.setText("Imported");
				settingsForRandom.setVisible(false);
				settingsForImport.setVisible(true);
			}else {
				this.isGenerationRandom=true;
				bGen.setText("Random");
				settingsForRandom.setVisible(true);
				settingsForImport.setVisible(false);
			}
		});




		StackPane sp_ran = new StackPane();
		sp_ran.getChildren().addAll(settingsForRandom,settingsForImport);

		StackPane sp_imp = new StackPane();
		sp_imp.getChildren().addAll(bGen);

		VBox settings = new VBox(10);
		settings.getChildren().addAll(sp_ran,sp_imp);

		VBox topPanel = new VBox(10);
		topPanel.getChildren().addAll(title,lGen,bGen);
		topPanel.setAlignment(Pos.CENTER);

		VBox bottomPanel = new VBox(10);
		bottomPanel.getChildren().addAll(bBack);
		bottomPanel.setAlignment(Pos.CENTER);

		BorderPane bp = new BorderPane(settings);
		bp.setPadding(new Insets(30, 30, 30, 30));
		bp.setTop(topPanel) ;
		BorderPane.setAlignment(topPanel, Pos.TOP_CENTER);
		bp.setBottom(bottomPanel);
		BorderPane.setAlignment(bottomPanel, Pos.BOTTOM_CENTER);
		bp.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));
		this.menus.put(Integer.valueOf(this.ID_MAZE_SETTINGS), new Scene(bp, this.window_height, this.window_width, this.colorOfFloors));
	}

	/**
	 * Génére le menu des paramètres gérant le monstre
	 */
	public void generateSettingsMonster() {
		Label title = Generators.generateTitle("Settings - Monster");
		TextField tf_vision = Generators.generateTextField(""+this.DEFAULT_VISION_RANGE, this.calculPercentage(this.window_width,70), this.calculPercentage(this.window_height,39), 1, '0', '9');
		Generators.addCheckNumericalValueToTextField(tf_vision, this.MIN_VISION_RANGE, this.MAX_VISION_RANGE);
		tf_vision.setDisable(true);
		Button b_vision = Generators.generateButton("NO", this.calculPercentage(this.window_width,70),this.calculPercentage(this.window_height,30),Color.WHITE, Color.BLACK);
		b_vision.setMinWidth(50);
		b_vision.setOnAction(e->{
			if(limitedVision) {
				this.limitedVision=false;
				b_vision.setText("NO");
				tf_vision.setDisable(true);
			}else {
				this.limitedVision=true;
				b_vision.setText("YES");
				tf_vision.setDisable(false);
			}
		});
		Label l_b_vision= Generators.generateLabel("Activate limited Vision", b_vision.getLayoutX()-this.LABEL_MIN_WIDTH-(this.SPACING*8), b_vision.getLayoutY());
		Label l_tf_vision= Generators.generateLabel("Vision Range", tf_vision.getLayoutX()-this.LABEL_MIN_WIDTH-(this.SPACING*8), tf_vision.getLayoutY());

		TextField tf_range = Generators.generateTextField("1", this.calculPercentage(this.window_width,70), this.calculPercentage(this.window_height,50), 1, '0', '9');
		Generators.addCheckNumericalValueToTextField(tf_range, 1, 9);
		Label l_range = Generators.generateLabel("Moving Range", tf_range.getLayoutX()-this.LABEL_MIN_WIDTH-(this.SPACING*8), tf_range.getLayoutY());
		Button bBack = Generators.generateButton("Back", this.calculPercentage(this.window_width, 5), this.calculPercentage(this.window_height,90),Color.WHITE, Color.BLACK);
		bBack.setOnAction(e->{
			this.setScene(this.getScene(this.ID_SETTINGS));
			if(tf_vision.getText().isEmpty()) {
				this.vision_range=this.DEFAULT_VISION_RANGE;
				tf_vision.setText(""+this.DEFAULT_VISION_RANGE);
			}else {
				this.vision_range=Integer.parseInt(tf_vision.getText());
			}
			if(tf_range.getText().isEmpty()) {
				this.moving_range=this.DEFAULT_MOVING_RANGE;
				tf_range.setText(""+this.DEFAULT_MOVING_RANGE);
			}else {
				this.moving_range=Integer.parseInt(tf_range.getText());
			}
		});

		Group group = new Group();
		group.getChildren().addAll(tf_vision,b_vision,l_b_vision,l_tf_vision,tf_range,l_range);

		BorderPane bp = new BorderPane(group);
		bp.setPadding(new Insets(30, 30, 30, 30));
		bp.setTop(title);
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		bp.setBottom(bBack);
		BorderPane.setAlignment(bBack, Pos.BOTTOM_CENTER);
		bp.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));
		this.menus.put(Integer.valueOf(this.ID_MONSTER_SETTINGS), new Scene(bp, this.window_height, this.window_width, this.colorOfFloors));
	}

	/**
	 * Génére le menu des paramètres gérant le chasseur
	 */
	public void generateSettingsHunter() {
		Label title = Generators.generateTitle("Settings - Hunter");

		TextField tf_bonusRange = Generators.generateTextField(""+this.DEFAULT_BONUS_RANGE, this.calculPercentage(this.window_width,70), this.calculPercentage(this.window_height,50), 1, '0', '9');
		Label l_bonusRange = Generators.generateLabel("Bonus Vision Range", tf_bonusRange.getLayoutX()-this.LABEL_MIN_WIDTH-(this.SPACING*8), tf_bonusRange.getLayoutY());

		Button bBack = Generators.generateButton("Back", this.calculPercentage(this.window_width, 5), this.calculPercentage(this.window_height,90),Color.WHITE, Color.BLACK);
		bBack.setOnAction(e->{
			this.setScene(this.getScene(this.ID_SETTINGS));
			if(tf_bonusRange.getText().isEmpty()) {
				this.bonus_range=this.DEFAULT_BONUS_RANGE;
				tf_bonusRange.setText(""+this.DEFAULT_BONUS_RANGE);
			}else {
				this.bonus_range=Integer.parseInt(tf_bonusRange.getText());
			}
		});

		Group group = new Group();
		group.getChildren().addAll(l_bonusRange,tf_bonusRange);

		BorderPane bp = new BorderPane(group);
		bp.setPadding(new Insets(30, 30, 30, 30));
		bp.setTop(title);
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		bp.setBottom(bBack);
		BorderPane.setAlignment(bBack, Pos.BOTTOM_CENTER);
		bp.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));
		this.menus.put(Integer.valueOf(this.ID_HUNTER_SETTINGS), new Scene(bp, this.window_height, this.window_width, this.colorOfFloors));
	}
	
	/**
	 * Génére le menu de l'éditeur de labyrinthe.
	 */
	public void generateMazeEditor() {
		Label title = Generators.generateTitle("Maze Editor");

		this.mEdit = new MazeEditor(this.DEFAULT_MAZE_SIZE,this.DEFAULT_MAZE_SIZE,this.window_height,this.window_width,this.gap_X,this.gap_Y);

		Label l_height = Generators.generateLabel("Maze Height ("+this.MIN_MAZE_SIZE+"-"+this.MAX_MAZE_SIZE+")", 0, 0);
		Label l_width= Generators.generateLabel("Maze Width ("+this.MIN_MAZE_SIZE+"-"+this.MAX_MAZE_SIZE+")", 0, 0);

		Slider slider_editor_height = Generators.generateSlider(this.MIN_MAZE_SIZE,this.MAX_MAZE_SIZE,this.DEFAULT_MAZE_SIZE,10,10);
		slider_editor_height.valueProperty().addListener(e->{
			mEdit.editor_height=(int)slider_editor_height.getValue();
			l_height.setText("Maze Width ("+this.MIN_MAZE_SIZE+"-"+this.MAX_MAZE_SIZE+") : "+mEdit.editor_height);
		});
		Slider slider_editor_width = Generators.generateSlider(this.MIN_MAZE_SIZE,this.MAX_MAZE_SIZE,this.DEFAULT_MAZE_SIZE,20,20);
		slider_editor_width.valueProperty().addListener(e->{
			mEdit.editor_width=(int)slider_editor_width.getValue();
			l_width.setText("Maze Width ("+this.MIN_MAZE_SIZE+"-"+this.MAX_MAZE_SIZE+") : "+mEdit.editor_width);
		});

		Button bReset = Generators.generateButton("Reset map", this.calculPercentage(this.window_width, 5), this.calculPercentage(this.window_height,70),Color.WHITE, Color.BLACK);
		bReset.setOnAction(e->{
			mEdit.resetDrawing(mEdit.editor_height,mEdit.editor_width,this.window_height, this.window_width,this.gap_X,this.gap_Y);
		});

		Text message = new Text();
		message.setVisible(false);

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose a map to import");
		// Créer un filtre pour les fichiers .dat
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers DAT (*.dat)", "*.dat");
        fileChooser.getExtensionFilters().add(extFilter);
        // Définir le répertoire initial du FileChooser
        File initialDirectory = new File(SaveLoadSystemMaps.MAZES_DIRECTORY);
        fileChooser.setInitialDirectory(initialDirectory);

		Button bImport = Generators.generateButton("Import", this.calculPercentage(this.window_width, 5), this.calculPercentage(this.window_height,90),Color.WHITE,Color.BLACK);
		bImport.setOnAction(event->{
			mEdit.map_import = fileChooser.showOpenDialog(this);
			if(mEdit.map_import!=null) {
				try {
					mEdit.walls = SaveLoadSystemMaps.loadMap(mEdit.map_import);
					message.setText("Success - Import done");
					message.setVisible(true);
				} catch (ClassNotFoundException | IOException exception) {
					message.setText("Error - Import impossible");
					message.setVisible(true);
				}
				mEdit.draw(mEdit.walls.length, mEdit.walls[0].length, window_height, window_width, gap_X, gap_Y);
			}else {
				message.setText("Cancelled");
				message.setVisible(true);
			}
		});

		Button bBack = Generators.generateButton("Back", this.calculPercentage(this.window_width, 5), this.calculPercentage(this.window_height,90),Color.WHITE,Color.BLACK);
		bBack.setOnAction(e->{
			this.setScene(getScene(this.ID_MAZE_SETTINGS));
			message.setVisible(false);
		});

		//HBox saveMapPanel = Generators.generateHBoxSaveMap(mEdit.walls, Color.BLACK, "Save your map : ", "Save map","Map successfully saved","ERROR - Impossible to save Map");


		Label l_saveMap = Generators.generateLabel("Save your map : ", 0, 0);
		l_saveMap.setTextFill(Color.BLACK);
		TextField tf_saveMap = Generators.generateTextField(SaveLoadSystemMaps.DEFAULT_NAME_FOR_MAP_SAVE, 0, 0, 9, 'A', 'z');
		Button bSave = Generators.generateButton("Save map", 0, 0,Color.WHITE,Color.BLACK);
		bSave.setMinWidth(bSave.getPrefWidth());
		bSave.setOnAction(e->{
			try {
				String fileName = tf_saveMap.getText();
				if(fileName.isEmpty()) {
					SaveLoadSystemMaps.saveMap(mEdit.walls, SaveLoadSystemMaps.DEFAULT_NAME_FOR_MAP_SAVE);
				}else {
					SaveLoadSystemMaps.saveMap(mEdit.walls, fileName);
				}
				message.setText("Map successfully saved!");
				message.setVisible(true);
			}catch(IOException ioe) {
				message.setText("Error when saving the map.");
				message.setVisible(true);
			}
		});
		HBox saveMapPanel = new HBox(10);
		saveMapPanel.getChildren().addAll(l_saveMap,tf_saveMap,message);

		HBox bottomPanel = new HBox(10);
		bottomPanel.getChildren().add(bBack);
		bottomPanel.getChildren().add(bReset);
		bottomPanel.getChildren().add(bImport);
		bottomPanel.getChildren().add(bSave);


		VBox controlPanel = new VBox(10);
		controlPanel.getChildren().add(l_height);
		controlPanel.getChildren().add(slider_editor_height);
		controlPanel.getChildren().add(l_width);
		controlPanel.getChildren().add(slider_editor_width);
		controlPanel.getChildren().add(saveMapPanel);
		controlPanel.getChildren().add(bottomPanel);

		BorderPane bp = new BorderPane(mEdit.group);
		bp.setPadding(new Insets(30, 30, 30, 30));
		bp.setTop(title);
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		bp.setBottom(controlPanel);
		BorderPane.setAlignment(controlPanel, Pos.BOTTOM_CENTER);
		bp.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));
		this.menus.put(Integer.valueOf(this.ID_MAZE_EDITOR), new Scene(bp, this.window_height, this.window_width, this.colorOfFloors));
	}


	/**
	 * Génére le menu de GameOver.
	 */
	public void generateGameOverScreen() {
		Label title = Generators.generateTitle("Game Over Menu");

		Button restartButton = Generators.generateButton("Rejouer", 0, 0,Color.WHITE, Color.BLACK);
		restartButton.setOnAction(e -> {
			this.hide();
			this.setScene(this.getScene(this.ID_PLAY));
			this.show();
		});

		Button quitButton = Generators.generateButton("Quitter", 0, 0,Color.WHITE, Color.BLACK);
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