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
import java.util.logging.Logger;

import fr.univlille.info.J2.main.application.system.SaveLoadSystemGames;
import fr.univlille.info.J2.main.application.system.SaveLoadSystemMaps;
import fr.univlille.info.J2.main.utils.Utils;
import fr.univlille.info.J2.main.utils.menuConception.DisplayValues;
import fr.univlille.info.J2.main.utils.menuConception.Generators;
import fr.univlille.info.J2.main.utils.menuConception.Theme;
import fr.univlille.info.J2.main.utils.patrons.Observer;
import fr.univlille.info.J2.main.utils.patrons.Subject;
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
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
	 * Looger qui permet d'éviter les system.out pour à la place faire de vra ifichiers de log.
	 */
	private static final Logger LOGGER = Logger.getLogger(Management.class.getName());

	/**
	 * Constante ID de la scene d'attente entre deux joueurs.
	 */
	private static final int ID_WAIT = 0;

	/**
	 * Constante ID du menu settings.
	 */
	private static final int ID_SETTINGS = 1;

	/**
	 * Constante ID du menu play.
	 */
	private static final int ID_PLAY = 2;

	/**
	 * Constante ID du menu de GameOver.
	 */
	private static final int ID_GAMEOVER = 3;

	/**
	 * Constante ID du menu de settings-Miscellaneous.
	 */
	private static final int ID_MISCELLANEOUS_SETTINGS = 4;

	/**
	 * Constante ID du menu de settings-Hunter.
	 */
	private static final int ID_MAZE_SETTINGS = 5;

	/**
	 * Constante ID du menu de settings-Monster.
	 */
	private static final int ID_MONSTER_SETTINGS = 6;

	/**
	 * Constante ID du menu de settings-Hunter.
	 */
	private static final int ID_HUNTER_SETTINGS = 7;

	/**
	 * Constante ID de l'éditeur de labyrinthe.
	 */
	private static final int ID_MAZE_EDITOR = 8;

	/**
	 * Constante utilisée dans les comboBox pour le choix des joueurs.
	 */
	protected static final String[] IA_LEVELS = new String[] {"Player","IA-Easy","IA-Moderate","IA-Hardcore"};

	/**
	 * Constante utilisée dans les comboBox pour le choix des themes.
	 */
	private static final String[] THEMES = new String[] {Theme.THEME_DUNGEON, Theme.THEME_CAVE, Theme.THEME_MEADOW, Theme.THEME_FOREST, Theme.THEME_OCEAN};
	/**
	 * Theme actuellement utilisé par le jeu
	 */
	private Theme current_theme;
	
	/**
	 * Constante pour le décalage lors de la génération des labels
	 */
	private static final int SPACING = 5;
	
	/**
	 * label qui indique le gagnant
	 */
	private Label winner ;
	
	private DisplayValues display;
	

	/**
	 * Constante de largeur minimum des labels.
	 */
	private static final int LABEL_MIN_WIDTH = 120;

	/**
	 * Constante de taille minimal du labyrinthe.
	 */
	private static final int MIN_MAZE_SIZE = 1;

	/**
	 * Constante de taille par défault du labyrinthe.
	 */
	private static final int DEFAULT_MAZE_SIZE = 10;

	/**
	 * Constante de taille maximal du labyrinthe.
	 */
	private static final int MAX_MAZE_SIZE = 50;

	/**
	 * Constante de probabilité par défaut d'apparition de murs
	 */
	private static final int DEFAULT_PROBABILITY = 20;

	/**
	 * Constante de la portée de déplacement du monstre
	 */
	private static final int DEFAULT_MOVING_RANGE = 1;


	/**
	 * Constante de la portée minimale de la vision du monstre
	 */
	private static final int MIN_VISION_RANGE = 1;

	/**
	 * Constante de la portéepar défault de la vision du monstre
	 */
	private static final int DEFAULT_VISION_RANGE = 3;

	/**
	 * Constante de la portée maximale de la vision du monstre
	 */
	private static final int MAX_VISION_RANGE = 9;


	/**
	 * Constante de la portée par défault de la vision bonus du chasseur
	 * Les constantes MIN_BONUS_RANGE ni MAX_BONUS_RANGEont étés retirés car elles n'étaient pas utiles.
	 */
	private static final int DEFAULT_BONUS_RANGE = 0;
	
	/**
	 * Le labyrinthe.
	 */
	private Maze maze;

	/**
	 * La vue sur
	 */
	private MazeEditor mEdit;

	/**
	 * La vue du Monstre.
	 */
	private MonsterView mv;

	/**
	 * La vue du Chasseur.
	 */
	private HunterView hv;
	
	
	/**
	 * stocke la hauteur du labyrinthe
	 */
	private int maze_height;
	/**
	 * stocke la largeur du labyrinthe
	 */
	private int maze_width;
	/**
	 * taux d'apparrition des murs
	 */
	private int probability;
	/**
	 * portée de déplacement du monstre
	 */
	private int moving_range;
	/**
	 * portée de vision du monstre
	 */
	private int vision_range;
	/**
	 * indique si le monstre aura une vision limité ou totale du terrain
	 */
	private boolean limitedVision;
	/**
	 * portée de vision bonus du chasseur
	 */
	private int bonus_range;
	
	/**
	 * stocke le nom du joueur Monster
	 */
	private String monster_name;

	/**
	 * stocke le nom du joueur Hunter
	 */
	private String hunter_name;

	/**
	 * indique le type de joueur (humain, niveaux de l'ia)
	 */
	private String monster_IA;

	/**
	 * indique le type de joueur (humain, niveaux de l'ia)
	 */
	private String hunter_IA;
	
	/**
	 * indique si le jeu se deroule sur la meme fenetre (true) ou sur des fenetres separees (false).
	 */
	private boolean isSameScreen;

	/**
	 * Map contenant les différents menus du jeu.
	 */
	private Map<Integer, Scene> menus;

	//Les différentes fenêtres
	private Stage viewM;
	private Stage viewH;
	private Stage viewCommon;

	private boolean isGenerationRandom;

	//Le fichier importé par
	private File importedmap;

	/**
	 * Constructeur de la classe Management.
	 *
	 * @param window_height 	La hauteur de la fenetre.
	 * @param window_width 		La largeur de la fenetre.
	 * @param gap_X 			L'écart horizontal dans la vue du labyrinthe. Permet de décaler l'entièreté du labyrinthe sur un axe horizontal.
	 * @param gap_Y 			L'écart vertical dans la vue du labyrinthe.Permet de décaler l'entièreté du labyrinthe sur un axe vertical.
	 */
	public Management(DisplayValues display) {
		Theme.initThemes();
		this.current_theme=Theme.themesMap.get(Theme.THEME_DUNGEON);
		this.display=display;
		
		this.menus=new HashMap<>();
		this.isSameScreen=true;
		this.limitedVision=false;
		this.maze_height=DEFAULT_MAZE_SIZE;
		this.maze_width=DEFAULT_MAZE_SIZE;
		this.probability=DEFAULT_PROBABILITY;
		this.vision_range=DEFAULT_VISION_RANGE;
		this.moving_range=DEFAULT_MOVING_RANGE;
		this.bonus_range=DEFAULT_BONUS_RANGE;
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

		this.setScene(this.getScene(ID_PLAY));
		this.setTitle("MONSTERHUNT");

		this.setMinHeight(DisplayValues.WINDOWS_MIN_SIZE);
		this.setMinWidth(DisplayValues.WINDOWS_MIN_SIZE);
		this.viewCommon.setMinHeight(DisplayValues.WINDOWS_MIN_SIZE);
		this.viewCommon.setMinWidth(DisplayValues.WINDOWS_MIN_SIZE);
		this.viewH.setMinHeight(DisplayValues.WINDOWS_MIN_SIZE);
		this.viewH.setMinWidth(DisplayValues.WINDOWS_MIN_SIZE);
		this.viewM.setMinHeight(DisplayValues.WINDOWS_MIN_SIZE);
		this.viewM.setMinWidth(DisplayValues.WINDOWS_MIN_SIZE);

		this.heightProperty().addListener((obs, oldVal, newVal) ->
			this.display.setWindowHeight(newVal.doubleValue())
		);

		this.widthProperty().addListener((obs, oldVal, newVal) ->
			this.display.setWindowWidth(newVal.doubleValue())

		);

		this.xProperty().addListener((obs, oldVal, newVal)->
			this.display.setWindowX(newVal.doubleValue())

		);

		this.yProperty().addListener((obs, oldVal, newVal)->
			this.display.setWindowY(newVal.doubleValue())
		);

		this.viewCommon.heightProperty().addListener((obs, oldVal, newVal) ->
			this.display.setWindowHeight(newVal.doubleValue())
		);

		this.viewCommon.widthProperty().addListener((obs, oldVal, newVal) ->
			this.display.setWindowWidth(newVal.doubleValue())

		);

		this.viewCommon.xProperty().addListener((obs, oldVal, newVal)->
			this.display.setWindowX(newVal.doubleValue())
		);

		this.viewCommon.yProperty().addListener((obs, oldVal, newVal)->
			this.display.setWindowY(newVal.doubleValue())
		);

		this.sceneProperty().addListener(e->{
			this.setX(this.display.getWindowX());
			this.setY(this.display.getWindowY());
			this.setHeight(this.display.getWindowHeight());
			this.setWidth(this.display.getWindowWidth());
		});

		this.viewCommon.sceneProperty().addListener(e->{
			this.viewCommon.setX(this.display.getWindowX());
			this.viewCommon.setY(this.display.getWindowY());
			this.viewCommon.setHeight(this.display.getWindowHeight());
			this.viewCommon.setWidth(this.display.getWindowWidth());
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
		if(this.maze.isGameOver()) {
			if (this.maze.winner == 1) {
				this.winner.setText("Monster a gagné !");}
			else if (this.maze.winner == 2) {
				this.winner.setText("Hunter a gagné !");
			}else {
				this.winner.setText("Jeu arrêté");
			}
			
			this.setScene(this.getScene(ID_GAMEOVER));
			this.setHeight(this.display.getWindowHeight());
			this.setWidth(this.display.getWindowWidth());
			this.show();
			if(this.isSameScreen) {
				this.viewCommon.hide();
			}else {
				this.viewM.hide();
				this.viewH.hide();
			}
			maze.setGameOver(false);
			return true;
		}
		return false;
	}

	/**
     * Gère le déplacement du monstre par l'IA.
	 */
	public void monsterPlayAt(ICoordinate c) {
		this.maze.move(c);
		this.mv.actualize();
		this.setTitle("MONSTERHUNT");

	}

	/**
     * Gère le tir du chasseur par l'IA.
	 */
	public void hunterPlayAt(ICoordinate c) {
		this.maze.shoot(c);
		this.hv.actualize();
	}

	/**
	 * Bascule entre la vue du monstre et la vue du chasseur en fonction du tour actuel du jeu.
	 */
	public void switchInGameView() {
		ICoordinate c;
		if(this.maze.isMonsterTurn()) {
			this.toMonsterView();
			c = this.maze.getMonster().play();
			if(c!=null) {
				this.monsterPlayAt(c);
			}
		}else {
			this.toHunterView();
			c = this.maze.getHunter().play();
			if(c!=null) {
				this.hunterPlayAt(c);
			}
		}
	}

	/**
	 * Affiche une boite de dialogue indiquant à qui est le tour.
	 * @param joueur Le nom du joueur.
	 */
	public void toHunterView() {
		if(this.isSameScreen) {
			if(this.monster_IA.equals(Management.IA_LEVELS[0])&&this.hunter_IA.equals(Management.IA_LEVELS[0])) {
				this.viewCommon.setScene(this.getScene(ID_WAIT));
				ArrayList<ButtonType> alb = new ArrayList<>();
				ButtonType boutonJouer = new ButtonType("Play");
				alb.add(boutonJouer);
				Alert alert = Generators.generateAlert("It’s the Hunter’s turn", "Do you want to start your turn?", alb);// Attendre la réponse de l'utilisateur
				alert.setOnCloseRequest(e-> this.viewCommon.setScene(hv.scene) );
				alert.showAndWait().ifPresent(response -> {
					if(response == boutonJouer){
						this.viewCommon.setScene(hv.scene);
					}
				});
			}else {
				this.viewCommon.setScene(hv.scene);
			}
			viewCommon.setTitle("MONTERHUNT - HunterView");
		}

	}

	public void toMonsterView() {
		if(this.isSameScreen) {
			if(this.monster_IA.equals(Management.IA_LEVELS[0])&&this.hunter_IA.equals(Management.IA_LEVELS[0])){
				this.viewCommon.setScene(this.getScene(ID_WAIT));
				ArrayList<ButtonType> alb = new ArrayList<>();
				ButtonType boutonJouer = new ButtonType("Play");
				alb.add(boutonJouer);
				Alert alert = Generators.generateAlert("It’s the Monster’s turn", "Do you want to start your turn?", alb);// Attendre la réponse de l'utilisateur
				alert.setOnCloseRequest(e-> this.viewCommon.setScene(mv.scene) );
				alert.showAndWait().ifPresent(response -> {
					if(response == boutonJouer){
						this.viewCommon.setScene(mv.scene);
					}
				});
			}else {
				this.viewCommon.setScene(mv.scene);
			}
			viewCommon.setTitle("MONTERHUNT - MonsterView");
		}
	}


	/**
	 * Génére le menu principal du jeu, permettant  l'utilisateur de définir des paramètres pour le jeu
	 * (noms des personnages, niveaux d'IA, etc.) et de lancer une partie.
	 *
	 * @param gap_X 		L'espacement horizontal entre les cellules du labyrinthe.
	 * @param gap_Y 		L'espacement vertical entre les cellules du labyrinthe.
	 */
	public void generatePlayMenu() {

		Label title = Generators.generateTitle("Main Menu");

		TextField tf_name_monster = Generators.generateTextField("Monster", this.calculPercentage(this.display.getWindowWidth(), 10), this.calculPercentage(this.display.getWindowHeight(), 40), 16, 'A', 'z');
		TextField tf_name_hunter = Generators.generateTextField("Hunter", this.calculPercentage(this.display.getWindowWidth(), 60), this.calculPercentage(this.display.getWindowHeight(), 40), 16, 'A', 'z');

		Label l_nameM = Generators.generateLabel("Monster Name", tf_name_monster.getLayoutX(),tf_name_monster.getLayoutY()-15);
		Label l_nameH = Generators.generateLabel("Hunter Name", tf_name_hunter.getLayoutX(),tf_name_hunter.getLayoutY()-15);

		ComboBox<String> choixIA_Monster = Generators.generateComboBox(Management.IA_LEVELS, this.calculPercentage(this.display.getWindowWidth(), 10), this.calculPercentage(this.display.getWindowHeight(), 50));
		ComboBox<String> choixIA_Hunter = Generators.generateComboBox(Management.IA_LEVELS, this.calculPercentage(this.display.getWindowWidth(), 60), this.calculPercentage(this.display.getWindowHeight(), 50));

		Button bPlay = Generators.generateButton("PLAY", this.calculPercentage(this.display.getWindowWidth(), 45), this.calculPercentage(this.display.getWindowHeight(),90),Color.WHITE, Color.BLACK);
		bPlay.setOnAction(e->{
			//intantiation of the settings
			this.monster_name=tf_name_monster.getText();
			this.monster_IA=choixIA_Monster.getValue();
			this.hunter_name=tf_name_hunter.getText();
			this.hunter_IA=choixIA_Hunter.getValue();
			
			
			//Adaptation du zoom
			if(this.display.getWindowHeight()>this.display.getWindowWidth()) {
				this.display.setZoom(this.display.getWindowWidth()/(this.maze_height+this.maze_width));
			}else {
				this.display.setZoom(this.display.getWindowHeight()/(this.maze_height+this.maze_width));
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

			this.mv=new MonsterView(this.display,this.maze,this.monster_name,this.current_theme);
			this.hv=new HunterView(this.display,this.maze,this.hunter_name,this.current_theme);
			if(this.isSameScreen) {
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

		Button bSettings = Generators.generateButton("Modify Settings", this.calculPercentage(this.display.getWindowWidth(), 38), this.calculPercentage(this.display.getWindowHeight(),80),Color.WHITE, Color.BLACK);
		bSettings.setOnAction(e->
			this.setScene(this.getScene(ID_SETTINGS))
		);

		Button bQuit = Generators.generateButton("Quitter", 0, 0,Color.WHITE, Color.BLACK);
		bQuit.setOnAction(e ->
			System.exit(0)
		);


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
		  Scene scene = new Scene(root, this.display.getWindowHeight(), this.display.getWindowWidth(), this.current_theme.getFloorColor());

		  // Ajoutez la scène aux menus
		  this.menus.put(Integer.valueOf(ID_PLAY), scene);

		}

	/**
	 * Génére la scene s'affichant
	 */
	public void generateWaitingNextPlayer() {

		Label label = new Label("Waiting for the next player.");
		label.setTextFill(Color.WHITE);

		BorderPane root = new BorderPane(label);
		root.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));

		this.menus.put(Integer.valueOf(ID_WAIT), new Scene(root, this.display.getWindowHeight(), this.display.getWindowWidth(), Color.BLACK));
	}


	/**
	 * Génére le menu des paramètres du jeu, permettant  l'utilisateur de personnaliser diverses options telles que
	 * la taille du labyrinthe, le thème, etc.
	 */
	public void generateSettingsMainMenu() {
		Label title = Generators.generateTitle("Settings");

		Button toMisc = Generators.generateButton("Screen", this.calculPercentage(this.display.getWindowWidth(), 5), this.calculPercentage(this.display.getWindowHeight(),30),Color.WHITE, Color.BLACK);
		toMisc.setOnAction(e->
			this.setScene(this.getScene(ID_MISCELLANEOUS_SETTINGS))
		);
		toMisc.setMinWidth(150);

		Button toMaze = Generators.generateButton("Maze", this.calculPercentage(this.display.getWindowWidth(), 5), this.calculPercentage(this.display.getWindowHeight(),40),Color.WHITE, Color.BLACK);
		toMaze.setOnAction(e->
			this.setScene(this.getScene(ID_MAZE_SETTINGS))
		);
		toMaze.setMinWidth(150);

		Button toMons = Generators.generateButton("Monster", this.calculPercentage(this.display.getWindowWidth(), 5), this.calculPercentage(this.display.getWindowHeight(),50),Color.WHITE, Color.BLACK);
		toMons.setOnAction(e->
			this.setScene(this.getScene(ID_MONSTER_SETTINGS))
		);
		toMons.setMinWidth(150);

		Button toHunt = Generators.generateButton("Hunter", this.calculPercentage(this.display.getWindowWidth(), 5), this.calculPercentage(this.display.getWindowHeight(),60),Color.WHITE, Color.BLACK);
		toHunt.setOnAction(e->
			this.setScene(this.getScene(ID_HUNTER_SETTINGS))
		);
		toHunt.setMinWidth(150);

		Button bBack = Generators.generateButton("Back", this.calculPercentage(this.display.getWindowWidth(), 5), this.calculPercentage(this.display.getWindowHeight(),90),Color.WHITE, Color.BLACK);
		bBack.setOnAction(e->
			this.setScene(this.getScene(ID_PLAY))
		);

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
		this.menus.put(Integer.valueOf(ID_SETTINGS), new Scene(bp, this.display.getWindowHeight(), this.display.getWindowWidth(), this.current_theme.getFloorColor()));
	}


	/**
	 * Génére le menu des paramètres gérant des paramètres généreaux
	 */
	public void generateSettingsMiscellaneous() {
		Label title = Generators.generateTitle("Settings - Miscellaneous");
		
		Button bScreenType = Generators.generateButton("Same Screen", 0,30,Color.WHITE, Color.BLACK);
		bScreenType.setOnAction(e->{
			if(isSameScreen) {
				this.isSameScreen=false;
				bScreenType.setText("Separate Screen");
			}else {
				this.isSameScreen=true;
				bScreenType.setText("Same Screen");
			}
		});
		bScreenType.setMinWidth(150);
		Label l_screenType = Generators.generateLabel("Choose display settings", 0, 0);
		
		
		Button bBasicMode = Generators.generateButton("Image Mode", 0,60,Color.WHITE, Color.BLACK);
		bBasicMode.setOnAction(e->{
			if(this.current_theme.isWithImages()) {
				this.current_theme.setWithImages(false);
				bBasicMode.setText("Color Mode");
			}else {
				this.current_theme.setWithImages(true);
				bBasicMode.setText("Image Mode");
			}
		});
		bBasicMode.setMinWidth(150);


		ComboBox<String> theme = Generators.generateComboBox(THEMES, 0, 130);
		theme.setOnAction(e-> {
			this.current_theme = Theme.themesMap.get(theme.getValue());
			if(this.current_theme.isWithImages()) {
				bBasicMode.setText("Color Mode");
			}else {
				bBasicMode.setText("Image Mode");
			}
			this.applyTheme();
		});
		Label l_theme = Generators.generateLabel("Choose a theme", 0, 110);
		
		Button bBack = Generators.generateButton("Back", 0, 0,Color.WHITE, Color.BLACK);
		bBack.setOnAction(e->
			this.setScene(this.getScene(ID_SETTINGS))
		);

		Group group = new Group();
		group.getChildren().addAll(l_screenType,bScreenType,bBasicMode,l_theme,theme);

		BorderPane bp = new BorderPane(group);
		bp.setPadding(new Insets(30, 30, 30, 30));
		bp.setTop(title);
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		bp.setBottom(bBack);
		BorderPane.setAlignment(bBack, Pos.BOTTOM_CENTER);
		bp.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));
		this.menus.put(Integer.valueOf(ID_MISCELLANEOUS_SETTINGS), new Scene(bp, this.display.getWindowHeight(), this.display.getWindowWidth(), this.current_theme.getFloorColor()));
	}

	/**
	 * Génére le menu des paramètres gérant le labyrinthe
	 */
	public void generateSettingsMaze() {

		Label title = Generators.generateTitle("Settings - Maze");

		Label lGen = Generators.generateLabel("Set the generation mode :", 0,0);

		Button bGen = Generators.generateButton("Random", this.calculPercentage(this.display.getWindowWidth(), 10), this.calculPercentage(this.display.getWindowHeight(),20),Color.WHITE, Color.BLACK);
		bGen.setMinWidth(100);

		TextField tf_height = Generators.generateTextField("10",this.calculPercentage(this.display.getWindowWidth(),30), this.calculPercentage(this.display.getWindowHeight(),30));
		TextField tf_width = Generators.generateTextField("10", this.calculPercentage(this.display.getWindowWidth(),30), this.calculPercentage(this.display.getWindowHeight(),50));
		Generators.addCheckNumericalValueToTextField(tf_height, MIN_MAZE_SIZE, MAX_MAZE_SIZE);
		Generators.addCheckNumericalValueToTextField(tf_width, MIN_MAZE_SIZE, MAX_MAZE_SIZE);

		TextField tf_probability = Generators.generateTextField("20", this.calculPercentage(this.display.getWindowWidth(),30), this.calculPercentage(this.display.getWindowHeight(),70), 3, '0', '9');
		Generators.addCheckNumericalValueToTextField(tf_probability, 0, 100);
		Label l_probability= Generators.generateLabel("Spawn Rate of walls (%)", tf_probability.getLayoutX()-LABEL_MIN_WIDTH-(SPACING*8), tf_probability.getLayoutY());

		Label l_height = Generators.generateLabel("Maze Height ("+MIN_MAZE_SIZE+"-"+MAX_MAZE_SIZE+")", tf_height.getLayoutX()-LABEL_MIN_WIDTH-SPACING, tf_height.getLayoutY());
		Label l_width= Generators.generateLabel("Maze Width ("+MIN_MAZE_SIZE+"-"+MAX_MAZE_SIZE+")", tf_width.getLayoutX()-LABEL_MIN_WIDTH-SPACING, tf_width.getLayoutY());

		Slider slider_height = Generators.generateSlider(MIN_MAZE_SIZE,MAX_MAZE_SIZE,DEFAULT_MAZE_SIZE,l_height.getLayoutX(),tf_height.getLayoutY()+SPACING*5);
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

		Slider slider_width = Generators.generateSlider(MIN_MAZE_SIZE,MAX_MAZE_SIZE,DEFAULT_MAZE_SIZE,l_width.getLayoutX(),tf_width.getLayoutY()+SPACING*5);
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

		Button bEditor = Generators.generateButton("Maze Editor", this.calculPercentage(this.display.getWindowWidth(), 5), this.calculPercentage(this.display.getWindowHeight(),90),Color.WHITE, Color.BLACK);
		bEditor.setOnAction(e->
			this.setScene(this.getScene(ID_MAZE_EDITOR))
		);

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

		Button bImport = Generators.generateButton("Import", this.calculPercentage(this.display.getWindowWidth(), 5), this.calculPercentage(this.display.getWindowHeight(),90),Color.WHITE,Color.BLACK);
		bImport.setOnAction(e->{
			this.importedmap = fileChooser.showOpenDialog(this);
			if(this.importedmap==null) {
				message.setText("Operation canceled : no file selected");
			}else {
				message.setText("File selected : "+this.importedmap.getName());
			}

		});

		Button bBack = Generators.generateButton("Back", this.calculPercentage(this.display.getWindowWidth(), 5), this.calculPercentage(this.display.getWindowHeight(),90),Color.WHITE, Color.BLACK);
		bBack.setOnAction(e->{
			this.setScene(this.getScene(ID_SETTINGS));
			if(tf_probability.getText().isEmpty()) {
				probability=DEFAULT_PROBABILITY;
				tf_probability.setText(""+DEFAULT_PROBABILITY);
			}else {
				probability=Integer.parseInt(tf_probability.getText());
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
		this.menus.put(Integer.valueOf(ID_MAZE_SETTINGS), new Scene(bp, this.display.getWindowHeight(), this.display.getWindowWidth(), this.current_theme.getFloorColor()));
	}

	/**
	 * Génére le menu des paramètres gérant le monstre
	 */
	public void generateSettingsMonster() {
		Label title = Generators.generateTitle("Settings - Monster");
		TextField tf_vision = Generators.generateTextField(""+DEFAULT_VISION_RANGE, calculPercentage(this.display.getWindowWidth(),70), this.calculPercentage(this.display.getWindowHeight(),39), 1, '0', '9');
		Generators.addCheckNumericalValueToTextField(tf_vision, MIN_VISION_RANGE, MAX_VISION_RANGE);
		tf_vision.setDisable(true);
		Button b_vision = Generators.generateButton("NO", calculPercentage(this.display.getWindowWidth(),70),calculPercentage(this.display.getWindowHeight(),30),Color.WHITE, Color.BLACK);
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
		Label l_b_vision= Generators.generateLabel("Activate limited Vision", b_vision.getLayoutX()-LABEL_MIN_WIDTH-(SPACING*8), b_vision.getLayoutY());
		Label l_tf_vision= Generators.generateLabel("Vision Range", tf_vision.getLayoutX()-LABEL_MIN_WIDTH-(SPACING*8), tf_vision.getLayoutY());

		TextField tf_range = Generators.generateTextField("1", this.calculPercentage(this.display.getWindowWidth(),70), this.calculPercentage(this.display.getWindowHeight(),50), 1, '0', '9');
		Generators.addCheckNumericalValueToTextField(tf_range, 1, 9);
		Label l_range = Generators.generateLabel("Moving Range", tf_range.getLayoutX()-LABEL_MIN_WIDTH-(SPACING*8), tf_range.getLayoutY());
		Button bBack = Generators.generateButton("Back", this.calculPercentage(this.display.getWindowWidth(), 5), this.calculPercentage(this.display.getWindowHeight(),90),Color.WHITE, Color.BLACK);
		bBack.setOnAction(e->{
			this.setScene(this.getScene(ID_SETTINGS));
			if(tf_vision.getText().isEmpty()) {
				this.vision_range=DEFAULT_VISION_RANGE;
				tf_vision.setText(""+DEFAULT_VISION_RANGE);
			}else {
				this.vision_range=Integer.parseInt(tf_vision.getText());
			}
			if(tf_range.getText().isEmpty()) {
				this.moving_range=DEFAULT_MOVING_RANGE;
				tf_range.setText(""+DEFAULT_MOVING_RANGE);
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
		this.menus.put(Integer.valueOf(ID_MONSTER_SETTINGS), new Scene(bp, this.display.getWindowHeight(), this.display.getWindowWidth(), this.current_theme.getFloorColor()));
	}

	/**
	 * Génére le menu des paramètres gérant le chasseur
	 */
	public void generateSettingsHunter() {
		Label title = Generators.generateTitle("Settings - Hunter");

		TextField tf_bonusRange = Generators.generateTextField(""+DEFAULT_BONUS_RANGE, this.calculPercentage(this.display.getWindowWidth(),70), this.calculPercentage(this.display.getWindowHeight(),50), 1, '0', '9');
		Label l_bonusRange = Generators.generateLabel("Bonus Vision Range", tf_bonusRange.getLayoutX()-LABEL_MIN_WIDTH-(SPACING*8), tf_bonusRange.getLayoutY());

		Button bBack = Generators.generateButton("Back", this.calculPercentage(this.display.getWindowWidth(), 5), this.calculPercentage(this.display.getWindowHeight(),90),Color.WHITE, Color.BLACK);
		bBack.setOnAction(e->{
			this.setScene(this.getScene(ID_SETTINGS));
			if(tf_bonusRange.getText().isEmpty()) {
				this.bonus_range=DEFAULT_BONUS_RANGE;
				tf_bonusRange.setText(""+DEFAULT_BONUS_RANGE);
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
		this.menus.put(Integer.valueOf(ID_HUNTER_SETTINGS), new Scene(bp, this.display.getWindowHeight(), this.display.getWindowWidth(), this.current_theme.getFloorColor()));
	}

	public void generateMazeEditor() {
		Label title = Generators.generateTitle("Maze Editor");

		this.mEdit = new MazeEditor(DEFAULT_MAZE_SIZE,DEFAULT_MAZE_SIZE,this.display,this.current_theme);

		Label l_height = Generators.generateLabel("Maze Height ("+MIN_MAZE_SIZE+"-"+MAX_MAZE_SIZE+")", 0, 0);
		Label l_width= Generators.generateLabel("Maze Width ("+MIN_MAZE_SIZE+"-"+MAX_MAZE_SIZE+")", 0, 0);

		Slider slider_editor_height = Generators.generateSlider(MIN_MAZE_SIZE,MAX_MAZE_SIZE,DEFAULT_MAZE_SIZE,10,10);
		slider_editor_height.valueProperty().addListener(e->{
			mEdit.editor_height=(int)slider_editor_height.getValue();
			l_height.setText("Height of the maze ("+MIN_MAZE_SIZE+"-"+MAX_MAZE_SIZE+") : "+mEdit.editor_height);
		});
		Slider slider_editor_width = Generators.generateSlider(MIN_MAZE_SIZE,MAX_MAZE_SIZE,DEFAULT_MAZE_SIZE,20,20);
		slider_editor_width.valueProperty().addListener(e->{
			mEdit.editor_width=(int)slider_editor_width.getValue();
			l_width.setText("Width of the maze ("+MIN_MAZE_SIZE+"-"+MAX_MAZE_SIZE+") : "+mEdit.editor_width);
		});

		Button bReset = Generators.generateButton("Reset map", this.calculPercentage(this.display.getWindowWidth(), 5), this.calculPercentage(this.display.getWindowHeight(),70),Color.WHITE, Color.BLACK);
		bReset.setOnAction(e->
			mEdit.resetDrawing(mEdit.editor_height,mEdit.editor_width,this.display)
		);

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

		Button bImport = Generators.generateButton("Import", this.calculPercentage(this.display.getWindowWidth(), 5), this.calculPercentage(this.display.getWindowHeight(),90),Color.WHITE,Color.BLACK);
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
				mEdit.draw(mEdit.walls.length, mEdit.walls[0].length,this.display);
			}else {
				message.setText("Cancelled");
				message.setVisible(true);
			}
		});

		Button bBack = Generators.generateButton("Back", this.calculPercentage(this.display.getWindowWidth(), 5), this.calculPercentage(this.display.getWindowHeight(),90),Color.WHITE,Color.BLACK);
		bBack.setOnAction(e->{
			this.setScene(getScene(ID_MAZE_SETTINGS));
			message.setVisible(false);
		});

		Label l_saveMap = Generators.generateLabel("Save your map : ", 0, 0);
		l_saveMap.setTextFill(Color.BLACK);
		TextField tf_saveMap = Generators.generateTextField(SaveLoadSystemMaps.DEFAULT_NAME_FOR_MAP_SAVE, 0, 0, 24, 'A', 'z');
		tf_saveMap.setMinWidth(210);
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
		this.menus.put(Integer.valueOf(ID_MAZE_EDITOR), new Scene(bp, this.display.getWindowHeight(), this.display.getWindowWidth(), this.current_theme.getFloorColor()));
	}


	/**
	 * Génére le menu de GameOver.
	 */
	public void generateGameOverScreen() {
		Label title = Generators.generateTitle("Game Over Menu");
		Label Credit = Generators.generateLabel("Jeu réalisé par Fourmaintraux Camille | Top Jessy | Debacq Arthur | Franos Théo ", 0, 0);
		
		
		this.winner = new Label();
		winner.setFont(new Font("Arial", 24));
		winner.setTextFill(Color.BLACK);
		
		Button restartButton = Generators.generateButton("Rejouer", 0, 0,Color.WHITE, Color.BLACK);
		restartButton.setOnAction(e -> {
			this.hide();
			this.setScene(this.getScene(ID_PLAY));
			this.show();
		});

		Button quitButton = Generators.generateButton("Quitter", 0, 0,Color.WHITE, Color.BLACK);
		quitButton.setOnAction(e -> System.exit(0) );

		BorderPane layout = new BorderPane();
		layout.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));

		// Placez le titre en haut et au centre de la page
		StackPane.setAlignment(title, Pos.TOP_CENTER);

		// Laissez un espace en haut de la page

		VBox vBoxTitle = new VBox(10);
		vBoxTitle.getChildren().addAll(title ,this.winner);
		vBoxTitle.setAlignment(Pos.TOP_CENTER);
		vBoxTitle.setSpacing(60);
		
		HBox buttonLayout = new HBox(20);
		buttonLayout.setPrefWidth(200); 
		buttonLayout.setPrefHeight(50);
		restartButton.setPrefWidth(150);
		restartButton.setPrefHeight(30); 
		quitButton.setPrefWidth(150); 		
		quitButton.setPrefHeight(30);
		buttonLayout.setSpacing(20); 
		buttonLayout.getChildren().addAll(restartButton, quitButton);
		
		buttonLayout.setAlignment(Pos.CENTER);
		
		
		VBox vBoxCredit = new VBox(10);
		vBoxCredit.getChildren().add(Credit);
		vBoxCredit.setAlignment(Pos.BOTTOM_LEFT);

		layout.setPadding(new Insets(20));

		// Superposez le titre et les boutons
		layout.setTop(vBoxTitle);
		layout.setCenter(buttonLayout);
		layout.setBottom(vBoxCredit);

		this.menus.put(Integer.valueOf(ID_GAMEOVER), new Scene(layout, this.display.getWindowHeight(), this.display.getWindowWidth(), this.current_theme.getFloorColor()));
	}

	/**
     * Fonction qui applique un thème spécifique au jeu en modifiant les couleurs d'affichage.
     *
	 */
	public void applyTheme() {
		for(Scene s:this.menus.values()) {
			s.setFill(this.current_theme.getFloorColor());
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
	
	public static void showOption(Maze maze, Text notification) {
		ButtonType bt_cancel= new ButtonType("Cancel");
		ButtonType bt_save = new ButtonType("Save the game & Leave");
		ButtonType bt_noSave = new ButtonType("Leave");
		
        TextField tf_saveMap = Generators.generateTextField(SaveLoadSystemMaps.DEFAULT_NAME_FOR_MAP_SAVE, 0, 0, 24, 'A', 'z');
        tf_saveMap.setMinWidth(200);
        Button b_saveMap = Generators.generateButton("save the map", 0, 0, Color.BLACK, Color.LIGHTGRAY);
        b_saveMap.setOnAction(e->{
        	try {
				String fileName = tf_saveMap.getText();
				if(fileName.isEmpty()||fileName.isBlank()) {
					SaveLoadSystemMaps.saveMap(maze.getWalls(), SaveLoadSystemMaps.DEFAULT_NAME_FOR_MAP_SAVE);
				}else {
					SaveLoadSystemMaps.saveMap(maze.getWalls(), fileName);
				}
				notification.setText("Map successfully saved.");
			}catch(IOException ioe) {
				LOGGER.info("ERROR - An error occurred while saving the map.");
				notification.setText("Error when saving the map.");
			}
        });
        TextField tf_saveGame = Generators.generateTextField(SaveLoadSystemGames.DEFAULT_NAME_FOR_GAME_SAVE, 0, 0, 24, 'A', 'z');
        tf_saveGame.setMinWidth(200);
        Button b_saveGame = Generators.generateButton("save the game", 0, 0, Color.BLACK, Color.LIGHTGRAY);
        b_saveGame.setOnAction(e->{
        	notification.setText("Sauvegarde impossible car pas encore implémanté :/");
			LOGGER.info("Sauvegarde impossible car pas encore implémanté :/"); //TODO implémanter la sauvegarde
        });
        
        ArrayList<ButtonType> alb = new ArrayList<>();
		alb.add(bt_cancel);
		alb.add(bt_save);
		alb.add(bt_noSave);
		
        ArrayList<Node> ligne1 = new ArrayList<>();
        ligne1.add(Generators.generateLabel("File name for the map : ", 0, 0));
        ligne1.add(tf_saveMap);
        ligne1.add(b_saveMap);
        ArrayList<Node> ligne2 = new ArrayList<>();
        ligne2.add(Generators.generateLabel("File name for the save : ", 0, 0));
        ligne2.add(tf_saveGame);
        ligne2.add(b_saveGame);
        ArrayList<ArrayList<Node>> nodes = new ArrayList<>();
        nodes.add(ligne1);
        nodes.add(ligne2);
        
		Dialog<ButtonType> dialog = Generators.generateDialog("Leaving the game", "Are you sure you want to leave the game ?\n", alb, nodes);
		dialog.showAndWait().ifPresent(response -> {
			if (response.equals(bt_save)) {
				notification.setText("Sauvegarde impossible car pas encore implémanté :/");
				LOGGER.info("Sauvegarde impossible car pas encore implémanté :/"); //TODO implémanter la sauvegarde
				maze.triggersGameOver();
			}else if(response.equals(bt_noSave)) {
				maze.triggersGameOver();
			}
		});
	}

}