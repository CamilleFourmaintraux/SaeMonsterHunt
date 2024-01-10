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

import fr.univlille.info.J2.main.application.system.Save;
import fr.univlille.info.J2.main.application.system.SaveLoadSystemGames;
import fr.univlille.info.J2.main.application.system.SaveLoadSystemMaps;
import fr.univlille.info.J2.main.management.view.HunterView;
import fr.univlille.info.J2.main.management.view.MonsterView;
import fr.univlille.info.J2.main.strategy.hunter.GameplayHunterData;
import fr.univlille.info.J2.main.strategy.monster.GameplayMonsterData;
import fr.univlille.info.J2.main.utils.Utils;
import fr.univlille.info.J2.main.utils.patrons.Observer;
import fr.univlille.info.J2.main.utils.patrons.Subject;
import fr.univlille.info.J2.main.utils.resources.DisplayValues;
import fr.univlille.info.J2.main.utils.resources.Generators;
import fr.univlille.info.J2.main.utils.resources.Theme;
import fr.univlille.iutinfo.cam.player.perception.ICoordinate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	 * Constante ID du menu play.
	 */
	private static final int ID_PLAY = 1;

	/**
	 * Constante ID du menu settings.
	 */
	private static final int ID_SETTINGS = 2;


	/**
	 * Constante ID du menu de settings-Miscellaneous.
	 */
	private static final int ID_MISCELLANEOUS_SETTINGS = 3;

	/**
	 * Constante ID du menu de settings-Hunter.
	 */
	private static final int ID_MAZE_SETTINGS = 4;

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
	 * Le nom du joueur Monstre par défaut.
	 */
	private static final String DEFAULT_NAME_MONSTER = "Monster";
	/**
	 * Le nom du joueur Chasseur par défaut.
	 */
	private static final String DEFAULT_NAME_HUNTER = "Hunter";
	/**
	 * Si aucune IA spécifique n'est fournie, cette IA par défaut est utilisée.
	 */
	private static final String DEFAULT_IA_PLAYER = "Player";
	/**
	 * Constante utilisée dans les comboBox pour le choix des joueurs.
	 */
	protected static final String[] IA_LEVELS = new String[] {DEFAULT_IA_PLAYER,"IA-Easy","IA-Moderate","IA-Hardcore"};

	/**
	 * Constante utilisée dans les comboBox pour le choix des themes.
	 */
	protected static final String[] THEMES = new String[] {Theme.THEME_DUNGEON, Theme.THEME_CAVE, Theme.THEME_MEADOW, Theme.THEME_FOREST, Theme.THEME_OCEAN};
	/**
	 * Theme actuellement utilisé par le jeu
	 */
	private Theme current_theme;
	/**
	 * Constante de largeur minimum des labels.
	 */
	private static final int LABEL_MIN_WIDTH = 120;

	/**
	 * Constante de taille minimal du labyrinthe pour les textFields
	 */
	private static final int MIN_MAZE_SIZE_MANDATORY = 1;
	
	/**
	 * Constante de taille minimal du labyrinthe.
	 */
	private static final int MIN_MAZE_SIZE = 3;

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
	 * La vue du Monstre.
	 */
	private MonsterView mv;

	/**
	 * La vue du Chasseur.
	 */
	private HunterView hv;
	
	/**
	 * bjet pour stocker les valeurs relative à l'écran (taille de la fenêtre, etc..)
	 */
	private DisplayValues display;
	
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
	 * indique si le jeu se deroule sur la meme fenetre (true) ou sur des fenetres separees (false).
	 */
	private boolean isSameScreen;
	
	/**
	 * indique si le jeu aura des effets sonores.
	 */
	private boolean areSoundsActivated;

	/**
	 * Map contenant les différents menus du jeu.
	 */
	private Map<Integer, Scene> menus;

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
	 * boolean permettant de déterminer si le maze devrais se générer aléatoirement ou non.
	 */
	public boolean isGenerationRandom;

	/**
	 * Objet File représentant un labyrinthe personnnalisé et importé par le joueur.
	 */
	public File importedmap;
	
	/**
	 * Objet File représentant une save importée par le joueur
	 */
	public File loadedSave;
	
	private GameplayHunterData gameplayH;
	private GameplayMonsterData gameplayM;

	/**
	 * Constructeur de la classe Management.
	 *
	 * @param display Les valeurs d'affichage.
	 */
	public Management(DisplayValues display) {
		Theme.initThemes();
		this.current_theme=Theme.themesMap.get(Theme.THEME_DUNGEON);
		this.display=display;
		
		
		this.menus=new HashMap<>();
		this.maze_height=DEFAULT_MAZE_SIZE;
		this.maze_width=DEFAULT_MAZE_SIZE;
		this.probability=DEFAULT_PROBABILITY;
		this.isGenerationRandom=true;
		this.isSameScreen=true;
		this.areSoundsActivated=true;

		gameplayH = new GameplayHunterData(DEFAULT_NAME_HUNTER, DEFAULT_IA_PLAYER, DEFAULT_BONUS_RANGE);
		gameplayM = new GameplayMonsterData(DEFAULT_NAME_MONSTER, DEFAULT_IA_PLAYER, false, DEFAULT_VISION_RANGE, DEFAULT_MOVING_RANGE);
		
		//Génération des menus
		this.menus.put(Integer.valueOf(ID_SETTINGS), this.generateSettingsMainMenu());
		this.menus.put(Integer.valueOf(ID_PLAY), this.generatePlayMenu());
		this.menus.put(Integer.valueOf(ID_WAIT), this.generateWaitingNextPlayer());
		this.menus.put(Integer.valueOf(ID_MAZE_SETTINGS), this.generateSettingsMaze());
		this.menus.put(Integer.valueOf(ID_MISCELLANEOUS_SETTINGS), this.generateSettingsMiscellaneous());
		this.menus.put(Integer.valueOf(ID_MONSTER_SETTINGS), this.generateSettingsMonster());
		this.menus.put(Integer.valueOf(ID_HUNTER_SETTINGS), this.generateSettingsHunter());
		this.menus.put(Integer.valueOf(ID_MAZE_EDITOR), this.generateMazeEditor());
		//Game Over est généré à chaque fin de partie
		
		this.viewM = new Stage();
		this.viewH = new Stage();
		this.viewCommon = new Stage();
		viewM.setFullScreenExitHint("");
		viewH.setFullScreenExitHint("");
		viewM.setTitle("MONSTERHUNT - "+DEFAULT_NAME_MONSTER+"View");
		viewH.setTitle("MONSTERHUNT - "+DEFAULT_NAME_HUNTER+"View");
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
			this.setScene(this.generateGameOverScreen());
			this.setHeight(this.display.getWindowHeight());
			this.setWidth(this.display.getWindowWidth());
			this.show();
			if(this.maze.getDataMan().isSameScreen()) {
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
     * 
     * @param c Coordonnée à laquelle le monstre veut se déplacer.
	 */
	public void monsterPlayAt(ICoordinate c) {
		this.maze.move(c);
		this.mv.actualize();
	}

	/**
     * Gère le tir du chasseur par l'IA.
     * 
     * @param c Coordonnée à laquelle le chasseur veut tirer.
	 */
	public void hunterPlayAt(ICoordinate c) {
		//MediaLoader.playSound("shot.mp3");
		this.maze.shoot(c);
		this.hv.actualize();
	}

	/**
	 * Bascule entre la vue du monstre et la vue du chasseur en fonction du tour actuel du jeu.
	 */
	public void switchInGameView() {
		ICoordinate c;
		if(this.maze.isMonsterTurn()) {
			this.toMonsterView(this.getMonster_IA(), this.getHunter_IA(), this.maze.getDataMan().isSameScreen());
			c = this.maze.getMonster().play();
			if(c!=null) {
				this.monsterPlayAt(c);
			}
		}else {
			this.toHunterView(this.getMonster_IA(), this.getHunter_IA(), this.maze.getDataMan().isSameScreen());
			c = this.maze.getHunter().play();
			if(c!=null) {
				this.hunterPlayAt(c);
			}
		}
	}

	/**
	 * Affiche une boîte d'avertissement indiquant que c'est le tour du chasseur.
	 *
	 * @param IAMonster 	Le niveau de l'IA du monstre.
	 * @param IAHunter  	Le niveau de l'IA du chasseur.
	 * @param isSameScreen 	Un indicateur si c'est la même vue.
	 */
	public void toHunterView(String IAMonster, String IAHunter, boolean isSameScreen) {
		if(isSameScreen) {
			if(IAMonster.equals(DEFAULT_IA_PLAYER) && IAHunter.equals(Management.IA_LEVELS[0])) {
				this.viewCommon.setScene(this.getScene(ID_WAIT));
				ArrayList<ButtonType> alb = new ArrayList<>();
				ButtonType boutonJouer = new ButtonType("Play");
				alb.add(boutonJouer);
				Alert alert = Generators.generateAlert("It’s the "+DEFAULT_NAME_HUNTER+" turn", "Do you want to start your turn?", alb);// Attendre la réponse de l'utilisateur
				alert.setOnCloseRequest(e-> this.viewCommon.setScene(hv.getScene()) );
				alert.showAndWait().ifPresent(response -> {
					if(response == boutonJouer){
						this.viewCommon.setScene(hv.getScene());
					}
				});
			}else {
				this.viewCommon.setScene(hv.getScene());
			}
			this.viewCommon.setTitle("MONTERHUNT - "+DEFAULT_NAME_HUNTER+"View");
		}

	}
	
	/**
     * Renvoie le niveau de l'IA du chasseur utilisé dans le jeu.
     *
     * @return Le niveau de l'IA du chasseur.
     */
	private String getHunter_IA() {
		return this.gameplayH.getIA();
	}

	/**
     * Renvoie le niveau de l'IA du monstre utilisé dans le jeu.
     *
     * @return Le niveau de l'IA du monstre.
     */
	private String getMonster_IA() {
		return this.gameplayM.getIA();
	}

	/**
	 * Affiche une boîte d'avertissement indiquant que c'est le tour du monstre.
	 *
	 * @param IAMonster 	Le niveau de l'IA du monstre.
	 * @param IAHunter  	Le niveau de l'IA du chasseur.
	 * @param isSameScreen 	Un indicateur si c'est la même vue.
	 */
	public void toMonsterView(String IAMonster, String IAHunter, boolean isSameScreen) {
		if(isSameScreen) {
			if(IAMonster.equals(DEFAULT_IA_PLAYER)&&IAHunter.equals(Management.IA_LEVELS[0])){
				this.viewCommon.setScene(this.getScene(ID_WAIT));
				ArrayList<ButtonType> alb = new ArrayList<>();
				ButtonType boutonJouer = new ButtonType("Play");
				alb.add(boutonJouer);
				Alert alert = Generators.generateAlert("It’s the "+DEFAULT_NAME_MONSTER+" turn", "Do you want to start your turn?", alb);// Attendre la réponse de l'utilisateur
				alert.setOnCloseRequest(e-> this.viewCommon.setScene(mv.getScene()) );
				alert.showAndWait().ifPresent(response -> {
					if(response == boutonJouer){
						this.viewCommon.setScene(mv.getScene());
					}
				});
			}else {
				this.viewCommon.setScene(mv.getScene());
			}
			viewCommon.setTitle("MONTERHUNT - "+DEFAULT_NAME_MONSTER+"View");
		}
	}


	/**
     * Génère le menu principal du jeu, permettant à l'utilisateur de définir des paramètres pour le jeu
     * (noms des personnages, niveaux d'IA, etc.) et de lancer une partie.
     *
     * @return La scène du menu principal du jeu.
     */
	public Scene generatePlayMenu() {

		Label title = Generators.generateTitle("Main Menu");

		TextField tf_name_monster = Generators.generateTextField(DEFAULT_NAME_MONSTER, 16, 'A', 'z');
		TextField tf_name_hunter = Generators.generateTextField(DEFAULT_NAME_HUNTER, 16, 'A', 'z');
		
		Label l_nameM = Generators.generateLabel(DEFAULT_NAME_MONSTER+" Name", tf_name_monster.getLayoutX(),tf_name_monster.getLayoutY()-15);
		Label l_nameH = Generators.generateLabel(DEFAULT_NAME_HUNTER+" Name", tf_name_hunter.getLayoutX(),tf_name_hunter.getLayoutY()-15);

		ComboBox<String> choixIA_Monster = Generators.generateComboBox(Management.IA_LEVELS);
		ComboBox<String> choixIA_Hunter = Generators.generateComboBox(Management.IA_LEVELS);
		
		Button bPlay = Generators.generateButton("PLAY",Color.WHITE, Color.BLACK);
		bPlay.setOnAction(e->{
			//intantiation of the settings
			this.gameplayM.setName(tf_name_monster.getText());
			this.gameplayH.setName(tf_name_hunter.getText());
			this.gameplayM.setIA(choixIA_Monster.getValue());
			this.gameplayH.setIA(choixIA_Hunter.getValue());
			
			//Adaptation du zoom
			if(this.display.getWindowHeight()>this.display.getWindowWidth()) {
				this.display.setZoom(this.display.getWindowWidth()/(this.maze_height+this.maze_width));
			}else {
				this.display.setZoom(this.display.getWindowHeight()/(this.maze_height+this.maze_width));
			}
			
			//Création des paquets de data de management
			if(!choixIA_Monster.getValue().equals(DEFAULT_IA_PLAYER) && !choixIA_Hunter.getValue().equals(DEFAULT_IA_PLAYER)) {
				 this.areSoundsActivated=false;
			}
			SaveManagementData dataMan = new SaveManagementData(this.current_theme.getName(), this.isSameScreen, this.areSoundsActivated);
			
			//Creation of the maze
			if(this.isGenerationRandom) {
				this.maze = new Maze(this.probability, this.maze_height, this.maze_width, gameplayH, gameplayM,dataMan );
			}else {
				try {
					this.maze = new Maze(SaveLoadSystemMaps.loadMap(this.importedmap), gameplayH, gameplayM,dataMan );
				} catch (Exception exception) {
					this.maze = new Maze(this.probability, this.maze_height, this.maze_width, gameplayH, gameplayM,dataMan );
				}
			}
			this.maze.attach(this);

			this.mv=new MonsterView(this.display,this.maze,this.current_theme);
			this.hv=new HunterView(this.display,this.maze,this.current_theme);
			if(this.isSameScreen) {
				this.viewCommon.setScene(hv.getScene());
				this.viewCommon.show();
				this.setScene(hv.getScene());
			}else {
				this.viewM.setScene(mv.getScene());
				this.viewH.setScene(hv.getScene());
				this.viewM.show();
				this.viewH.show();
			}
			this.hide();
			this.switchInGameView(); //Ici Vérifie qui joue (IA ou joueur) pour pouvoir démarrer le jeu.
		});
		
		Button bLoad = Generators.generateButton("Load",Color.WHITE, Color.BLACK);
		bLoad.setOnAction(e->
			this.showLoadMenu()
		);

		Button bSettings = Generators.generateButton("Modify Settings",Color.WHITE, Color.BLACK);
		bSettings.setOnAction(e->
			this.setScene(this.getScene(ID_SETTINGS))
		);

		Button bQuit = Generators.generateButton("Quitter", Color.WHITE, Color.BLACK);
		bQuit.setOnAction(e ->
			System.exit(0)
		);
		  
		  HBox monsterLayout = new HBox(10);
		  monsterLayout.getChildren().addAll(l_nameM, tf_name_monster, choixIA_Monster);
		  HBox hunterLayout = new HBox(10);
		  hunterLayout.getChildren().addAll(l_nameH, tf_name_hunter, choixIA_Hunter);

		 // Créez un layout vertical pour les boutons
		  VBox playersLayout = new VBox(15);

		  // Ajoutez les éléments du menu aux boutons
		  playersLayout.getChildren().addAll(monsterLayout, hunterLayout);
		  
		  VBox buttonsLayout = new VBox(15);
		  buttonsLayout.getChildren().addAll(bPlay, bLoad, bSettings, bQuit);

		  // Centrez les boutons horizontalement et verticalement
		  monsterLayout.setAlignment(Pos.CENTER);
		  hunterLayout.setAlignment(Pos.CENTER);
		  buttonsLayout.setAlignment(Pos.CENTER);
		  
		  Group group = new Group();
		  group.getChildren().add(playersLayout);

		  // Créez un layout pour le titre et les boutons
		  BorderPane root = new BorderPane(group);
		  root.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));

		  // Ajoutez le titre et les boutons au layout
		  root.setTop(title);
		  BorderPane.setAlignment(title, Pos.CENTER);
		  root.setBottom(buttonsLayout);

		  // Centrez le layout sur l'écran
		  StackPane.setAlignment(root, Pos.CENTER);

		  // Laissez un espace en haut de la page
		  root.setPadding(new Insets(30));

		  // Créez une scène avec le layout
		  Scene scene = new Scene(root, this.display.getWindowHeight(), this.display.getWindowWidth(), this.current_theme.getFloorColor());

		  // Ajoutez la scène aux menus
		  return scene;
		}

	/**
     * Génère la scène à afficher lorsque l'on attend le prochain joueur.
     *
     * @return la scène représentant l'interface d'attente du prochain joueur.
     */
	public Scene generateWaitingNextPlayer() {

		Label label = new Label("Waiting for the next player.");
		label.setTextFill(this.current_theme.getTextColor());

		BorderPane root = new BorderPane(label);
		root.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));
		
		return new Scene(root, this.display.getWindowHeight(), this.display.getWindowWidth(), this.current_theme.getBackgroundColor());
		
	}


	/**
	 * Génére le menu des paramètres du jeu, permettant  l'utilisateur de personnaliser diverses options telles que
	 * la taille du labyrinthe, le thème, etc.
	 * 
	 * @return Scene La scène générée pour le menu principal des paramètres.
	 */
	public Scene generateSettingsMainMenu() {
		Label title = Generators.generateTitle("Settings");

		Button toMisc = Generators.generateButton("Screen",Color.WHITE, Color.BLACK);
		//this.calculPercentage(this.display.getWindowWidth(), 5), this.calculPercentage(this.display.getWindowHeight(),30)
		toMisc.setOnAction(e->
			this.setScene(this.getScene(ID_MISCELLANEOUS_SETTINGS))
		);
		toMisc.setMinWidth(150);

		Button toMaze = Generators.generateButton("Maze",Color.WHITE, Color.BLACK);
		//this.calculPercentage(this.display.getWindowWidth(), 5), this.calculPercentage(this.display.getWindowHeight(),40)
		toMaze.setOnAction(e->
			this.setScene(this.getScene(ID_MAZE_SETTINGS))
		);
		toMaze.setMinWidth(150);

		Button toMons = Generators.generateButton(DEFAULT_NAME_MONSTER,Color.WHITE, Color.BLACK);
		//this.calculPercentage(this.display.getWindowWidth(), 5), this.calculPercentage(this.display.getWindowHeight(),50)
		toMons.setOnAction(e->
			this.setScene(this.getScene(ID_MONSTER_SETTINGS))
		);
		toMons.setMinWidth(150);

		Button toHunt = Generators.generateButton(DEFAULT_NAME_HUNTER,Color.WHITE, Color.BLACK);
		//this.calculPercentage(this.display.getWindowWidth(), 5), this.calculPercentage(this.display.getWindowHeight(),60)
		toHunt.setOnAction(e->
			this.setScene(this.getScene(ID_HUNTER_SETTINGS))
		);
		toHunt.setMinWidth(150);

		Button bBack = Generators.generateButton("Back",Color.WHITE, Color.BLACK);
		//this.calculPercentage(this.display.getWindowWidth(), 5), this.calculPercentage(this.display.getWindowHeight(),90)
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
		return new Scene(bp, this.display.getWindowHeight(), this.display.getWindowWidth(), this.current_theme.getFloorColor());
	}


	 /**
     * Génère le menu des paramètres qui gèrent des paramètres généraux.
     *
     * @return Scene La scène générée pour les paramètres divers.
     */
	public Scene generateSettingsMiscellaneous() {
		Label title = Generators.generateTitle("Settings - Miscellaneous");
		
		Button bScreenType = Generators.generateButton("Same Screen", Color.WHITE, Color.BLACK);
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
		Label l_screenType = Generators.generateLabel("Choose theme mode");
		
		Button bSound = Generators.generateButton("Sounds:ON", Color.WHITE, Color.BLACK);
		bSound.setOnAction(e->{
			if(this.areSoundsActivated) {
				this.areSoundsActivated=false;
				bSound.setText("Sounds:OFF");
			}else {
				this.areSoundsActivated=true;
				bSound.setText("Sounds:ON");
			}
		});
		bSound.setMinWidth(150);
		
		Button bDisplayMode = Generators.generateButton("Image Mode", Color.WHITE, Color.BLACK);
		bDisplayMode.setOnAction(e->{
			if(this.current_theme.isWithImages()) {
				this.current_theme.setWithImages(false);
				bDisplayMode.setText("Color Mode");
			}else {
				this.current_theme.setWithImages(true);
				bDisplayMode.setText("Image Mode");
			}
		});
		bDisplayMode.setMinWidth(150);
		Label l_bDisplayMode = Generators.generateLabel("Choose display settings");

		ComboBox<String> theme = Generators.generateComboBox(THEMES);
		theme.setOnAction(e-> {
			this.current_theme = Theme.themesMap.get(theme.getValue());
			if(this.current_theme.isWithImages()) {
				bDisplayMode.setText("Image Mode");
			}else {
				bDisplayMode.setText("Color Mode");
			}
			this.applyTheme();
		});
		Label l_theme = Generators.generateLabel("Choose a theme");
		
		Button bBack = Generators.generateButton("Back",Color.WHITE, Color.BLACK);
		bBack.setOnAction(e->
			this.setScene(this.getScene(ID_SETTINGS))
		);

		VBox vbox = new VBox(10);
		vbox.getChildren().addAll(l_screenType,bScreenType,Generators.createEmptySpace(20),l_theme,theme,l_bDisplayMode,bDisplayMode,bSound);
		
		Group group = new Group(vbox);

		BorderPane bp = new BorderPane(group);
		bp.setPadding(new Insets(30, 30, 30, 30));
		bp.setTop(title);
		BorderPane.setAlignment(title, Pos.TOP_CENTER);
		bp.setBottom(bBack);
		BorderPane.setAlignment(bBack, Pos.BOTTOM_CENTER);
		bp.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));
		return new Scene(bp, this.display.getWindowHeight(), this.display.getWindowWidth(), this.current_theme.getFloorColor());
		
	}

	 /**
     * Génère le menu des paramètres gérant le labyrinthe.
     *
     * @return La scène représentant le menu des paramètres.
     */
	public Scene generateSettingsMaze() {

		Label title = Generators.generateTitle("Settings - Maze");

		Label lGen = Generators.generateLabel("Set the generation mode :",45,0);

		Button bGen = Generators.generateButton("Random",75,20,Color.WHITE, Color.BLACK);
		bGen.setMinWidth(100);

		TextField tf_height = Generators.generateTextField("10");
		TextField tf_width = Generators.generateTextField("10");
		Generators.addCheckNumericalValueToTextField(tf_height, MIN_MAZE_SIZE_MANDATORY , MAX_MAZE_SIZE);
		Generators.addCheckNumericalValueToTextField(tf_width, MIN_MAZE_SIZE_MANDATORY , MAX_MAZE_SIZE);

		TextField tf_probability = Generators.generateTextField("20", 3, '0', '9');
		Generators.addCheckNumericalValueToTextField(tf_probability, 0, 100);
		Label l_probability= Generators.generateLabel("Spawn Rate of walls (%)");

		Label l_height = Generators.generateLabel("Maze Height ("+MIN_MAZE_SIZE+"-"+MAX_MAZE_SIZE+")", tf_height.getLayoutX()-LABEL_MIN_WIDTH-5, tf_height.getLayoutY());
		Label l_width= Generators.generateLabel("Maze Width ("+MIN_MAZE_SIZE+"-"+MAX_MAZE_SIZE+")", tf_width.getLayoutX()-LABEL_MIN_WIDTH-5, tf_width.getLayoutY());

		Slider slider_height = Generators.generateSlider(MIN_MAZE_SIZE_MANDATORY,MAX_MAZE_SIZE,DEFAULT_MAZE_SIZE);
		Generators.setLayout(slider_height, l_height.getLayoutX(),tf_height.getLayoutY()+25);
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
					slider_height.setValue(MIN_MAZE_SIZE_MANDATORY);
					maze_height=MIN_MAZE_SIZE_MANDATORY;
					tf_height.setText(""+MIN_MAZE_SIZE_MANDATORY);
				}
			}
		});

		Slider slider_width = Generators.generateSlider(MIN_MAZE_SIZE_MANDATORY,MAX_MAZE_SIZE,DEFAULT_MAZE_SIZE);
		Generators.setLayout(slider_width, l_width.getLayoutX(),tf_width.getLayoutY()+25);
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
					slider_width.setValue(MIN_MAZE_SIZE_MANDATORY);
					maze_width=MIN_MAZE_SIZE_MANDATORY;
					tf_width.setText(""+MIN_MAZE_SIZE_MANDATORY);
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
			if(this.maze_height<MIN_MAZE_SIZE) {
				this.maze_height=MIN_MAZE_SIZE;
			}if(this.maze_width<MIN_MAZE_SIZE) {
				this.maze_width=MIN_MAZE_SIZE;
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
		
		Group gen = new Group();
		gen.getChildren().addAll(Generators.createEmptySpace(10),lGen,bGen);


		VBox vGen = new VBox();
		vGen.getChildren().addAll(gen);

		StackPane mainsp = new StackPane();
		mainsp.getChildren().addAll(settingsForRandom,settingsForImport);
		
		StackPane topsp = new StackPane();
		topsp.getChildren().addAll(vGen);
		
		VBox centerPanel = new VBox(10);
		centerPanel.getChildren().addAll(topsp,mainsp);

		VBox topPanel = new VBox(10);
		topPanel.getChildren().addAll(title);
		topPanel.setAlignment(Pos.CENTER);

		VBox bottomPanel = new VBox(10);
		bottomPanel.getChildren().addAll(bBack);
		bottomPanel.setAlignment(Pos.CENTER);
		
		Group group = new Group(centerPanel);

		BorderPane bp = new BorderPane(group);
		bp.setPadding(new Insets(30, 30, 30, 30));
		bp.setTop(topPanel) ;
		BorderPane.setAlignment(topPanel, Pos.TOP_CENTER);
		bp.setBottom(bottomPanel);
		BorderPane.setAlignment(bottomPanel, Pos.BOTTOM_CENTER);
		bp.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));
		return new Scene(bp, this.display.getWindowHeight(), this.display.getWindowWidth(), this.current_theme.getFloorColor());
		
	}

	/**
     * Génère le menu des paramètres gérant le monstre.
     *
     * @return Une scène représentant le menu des paramètres.
     */
	public Scene generateSettingsMonster() {
		Label title = Generators.generateTitle("Settings - "+DEFAULT_NAME_MONSTER);
		TextField tf_vision = Generators.generateTextField(""+DEFAULT_VISION_RANGE, 1, '0', '9');
		Generators.setLayout(tf_vision, this.calculPercentage(this.display.getWindowWidth(),70), this.calculPercentage(this.display.getWindowHeight(),39));
		Generators.addCheckNumericalValueToTextField(tf_vision, MIN_VISION_RANGE, MAX_VISION_RANGE);
		tf_vision.setDisable(true);
		Button b_vision = Generators.generateButton("NO", calculPercentage(this.display.getWindowWidth(),70),calculPercentage(this.display.getWindowHeight(),30),Color.WHITE, Color.BLACK);
		b_vision.setMinWidth(50);
		b_vision.setOnAction(e->{
			if(this.gameplayM.isVisionLimited()) {
				this.gameplayM.setVisionLimited(false);
				b_vision.setText("NO");
				tf_vision.setDisable(true);
			}else {
				this.gameplayM.setVisionLimited(true);
				b_vision.setText("YES");
				tf_vision.setDisable(false);
			}
		});
		Label l_b_vision= Generators.generateLabel("Activate limited Vision", b_vision.getLayoutX()-LABEL_MIN_WIDTH-40, b_vision.getLayoutY());
		Label l_tf_vision= Generators.generateLabel("Vision Range", tf_vision.getLayoutX()-LABEL_MIN_WIDTH-40, tf_vision.getLayoutY());

		TextField tf_range = Generators.generateTextField("1", 1, '0', '9');
		Generators.setLayout(tf_range, this.calculPercentage(this.display.getWindowWidth(),70), this.calculPercentage(this.display.getWindowHeight(),50));
		Generators.addCheckNumericalValueToTextField(tf_range, 1, 9);
		Label l_range = Generators.generateLabel("Moving Range", tf_range.getLayoutX()-LABEL_MIN_WIDTH-40, tf_range.getLayoutY());
		Button bBack = Generators.generateButton("Back",Color.WHITE, Color.BLACK);
		Generators.setLayout(bBack, this.calculPercentage(this.display.getWindowWidth(), 5), this.calculPercentage(this.display.getWindowHeight(),90));
		bBack.setOnAction(e->{
			this.setScene(this.getScene(ID_SETTINGS));
			if(tf_vision.getText().isEmpty()) {
				this.gameplayM.setVisionRange(DEFAULT_VISION_RANGE);
				tf_vision.setText(""+DEFAULT_VISION_RANGE);
			}else {
				this.gameplayM.setVisionRange(Integer.parseInt(tf_vision.getText()));
			}
			if(tf_range.getText().isEmpty()) {
				this.gameplayM.setMovingRange(DEFAULT_MOVING_RANGE);
				tf_range.setText(""+DEFAULT_MOVING_RANGE);
			}else {
				this.gameplayM.setMovingRange(Integer.parseInt(tf_range.getText()));
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
		return new Scene(bp, this.display.getWindowHeight(), this.display.getWindowWidth(), this.current_theme.getFloorColor());
		
	}

	/**
     * Génère le menu des paramètres gérant le monstre.
     * 
     * @return Une scène représentant le menu des paramètres du monstre.
     */
	public Scene generateSettingsHunter() {
		Label title = Generators.generateTitle("Settings - "+DEFAULT_NAME_HUNTER);

		TextField tf_bonusRange = Generators.generateTextField(""+DEFAULT_BONUS_RANGE, 1, '0', '9');
		Label l_bonusRange = Generators.generateLabel("Bonus Vision Range", tf_bonusRange.getLayoutX()-LABEL_MIN_WIDTH-40, tf_bonusRange.getLayoutY());

		Button bBack = Generators.generateButton("Back", this.calculPercentage(this.display.getWindowWidth(), 5), this.calculPercentage(this.display.getWindowHeight(),90),Color.WHITE, Color.BLACK);
		bBack.setOnAction(e->{
			this.setScene(this.getScene(ID_SETTINGS));
			if(tf_bonusRange.getText().isEmpty()) {
				this.gameplayH.setBonusRange(DEFAULT_BONUS_RANGE);
				tf_bonusRange.setText(""+DEFAULT_BONUS_RANGE);
			}else {
				this.gameplayH.setBonusRange(Integer.parseInt(tf_bonusRange.getText()));
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
		return new Scene(bp, this.display.getWindowHeight(), this.display.getWindowWidth(), this.current_theme.getFloorColor());
		
	}
	
	/**
     * Génère le menu de l'éditeur de labyrinthe.
     *
     * @return Une scène représentant le menu de l'éditeur de labyrinthe.
     */
	public Scene generateMazeEditor() {
		MazeEditor mEdit;
		Label title = Generators.generateTitle("Maze Editor");

		mEdit = new MazeEditor(DEFAULT_MAZE_SIZE,DEFAULT_MAZE_SIZE,this.display,this.current_theme);

		Label l_height = Generators.generateLabel("Maze Height ("+MIN_MAZE_SIZE+"-"+MAX_MAZE_SIZE+")", 0, 0);
		Label l_width= Generators.generateLabel("Maze Width ("+MIN_MAZE_SIZE+"-"+MAX_MAZE_SIZE+")", 0, 0);

		Slider slider_editor_height = Generators.generateSlider(MIN_MAZE_SIZE_MANDATORY,MAX_MAZE_SIZE,DEFAULT_MAZE_SIZE);
		slider_editor_height.valueProperty().addListener(e->{
			mEdit.editor_height=(int)slider_editor_height.getValue();
			l_height.setText("Height of the maze ("+MIN_MAZE_SIZE+"-"+MAX_MAZE_SIZE+") : "+mEdit.editor_height);
		});
		Slider slider_editor_width = Generators.generateSlider(MIN_MAZE_SIZE_MANDATORY,MAX_MAZE_SIZE,DEFAULT_MAZE_SIZE);
		slider_editor_width.valueProperty().addListener(e->{
			mEdit.editor_width=(int)slider_editor_width.getValue();
			l_width.setText("Width of the maze ("+MIN_MAZE_SIZE+"-"+MAX_MAZE_SIZE+") : "+mEdit.editor_width);
		});

		Button bReset = Generators.generateButton("Reset map",Color.WHITE, Color.BLACK);
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

		Button bImport = Generators.generateButton("Import",Color.WHITE,Color.BLACK);
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
		TextField tf_saveMap = Generators.generateTextField(SaveLoadSystemMaps.DEFAULT_NAME_FOR_MAP_SAVE, 24, 'A', 'z');
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
		return new Scene(bp, this.display.getWindowHeight(), this.display.getWindowWidth(), this.current_theme.getFloorColor());
	}


	/**
     * Génère le menu de GameOver.
     * 
     * @return Une scène représentant l'écran de fin de jeu.
     */
	public Scene generateGameOverScreen() {
		Group board;
		
		Label title = Generators.generateTitle("Game Over");
		Label winner = new Label();
		Label Credit = Generators.generateLabel("Jeu réalisé par Fourmaintraux Camille | Top Jessy | Debacq Arthur | Franos Théo ", 0, 0);
		
		if (this.maze.getIdWinner() == 1) {
			winner.setText(this.gameplayM.getName()+" won !");
			board = this.hv.getGameBoard();
		}else if (this.maze.getIdWinner() == 2) {
			winner.setText(this.gameplayH.getName()+" won !");
			board = this.hv.getGameBoard();
		}else {
			winner.setText("Tie - The game was stopped.");
			board = new Group();
		}
		
		winner.setFont(new Font("Arial", 24));
		winner.setTextFill(Color.BLACK);
		
		Button restartButton = Generators.generateButton("Rejouer", 0, 0,Color.WHITE, Color.BLACK);
		restartButton.setOnAction(e -> {
			this.hide();
			this.setScene(this.getScene(ID_PLAY));
			this.show();
		});
		
		Button statButton = Generators.generateButton("stat", 0, 0,Color.WHITE, Color.BLACK);
		statButton.setOnAction(e -> {
			String stats = "The game ended at turn N°"+this.maze.getTurn()+"."
					+"\n"+
					"The monster was at "+(Maze.calculDistance(this.maze.getMonster().getCoord(), this.maze.getExit().getCoord()))+" cases of the exit."
					+"\n"+
					"The closest distance the hunter was to the monster was "+this.maze.getClosestDistanceToMonster()+" cases.";
			Alert stat = new Alert(AlertType.INFORMATION);
			stat.setTitle("Statistics of the game");
			stat.setContentText(stats);
			if(this.maze.getIdWinner()==1) {
				stat.setHeaderText("Monster won !");
			}else if(this.maze.getIdWinner()==2) {
				stat.setHeaderText("Hunter won !");
			}else {
				stat.setHeaderText("Tie.");
			}
			stat.showAndWait();
		});

		Button quitButton = Generators.generateButton("Quitter", 0, 0,Color.WHITE, Color.BLACK);
		quitButton.setOnAction(e -> System.exit(0) );

		BorderPane layout = new BorderPane();
		layout.setBackground(Utils.setBackGroungFill(Color.TRANSPARENT));

		// Placez le titre en haut et au centre de la page
		StackPane.setAlignment(title, Pos.TOP_CENTER);

		// Laissez un espace en haut de la page

		VBox vBoxTitle = new VBox(10);
		vBoxTitle.getChildren().addAll(title ,winner);
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
		buttonLayout.getChildren().addAll(restartButton, statButton, quitButton);
		
		buttonLayout.setAlignment(Pos.CENTER);
		
		
		VBox vBoxCredit = new VBox(10);
		vBoxCredit.getChildren().addAll(buttonLayout ,Credit);
		vBoxCredit.setAlignment(Pos.BOTTOM_LEFT);
		
		layout.setPadding(new Insets(20));
		

		// Superposez le titre et les boutons
		layout.setTop(vBoxTitle);
		layout.setCenter(board);
		layout.setBottom(vBoxCredit);
		
		return new Scene(layout, this.display.getWindowHeight(), this.display.getWindowWidth(), this.current_theme.getFloorColor());
	}

	/**
     * Fonction qui applique un thème spécifique au jeu en modifiant les couleurs d'affichage.
	 */
	public void applyTheme() {
		for(Scene s:this.menus.values()) {
			if(s.equals(this.getScene(ID_WAIT))) {
				s.setFill(this.current_theme.getBackgroundColor());
			}else {
				s.setFill(this.current_theme.getFloorColor());
			}
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
	
	/**
     * Affiche les options de gestion du jeu.
     *
     * @param maze          L'instance du labyrinthe associée à la gestion.
     * @param notification  Le composant de texte pour afficher les notifications.
     */
	public static void showOption(Maze maze, Text notification) {
		ButtonType bt_cancel= new ButtonType("Cancel");
		ButtonType bt_save = new ButtonType("Save the game & Leave");
		ButtonType bt_noSave = new ButtonType("Leave");
		
        TextField tf_saveMap = Generators.generateTextField(SaveLoadSystemMaps.DEFAULT_NAME_FOR_MAP_SAVE, 24, 'A', 'z');
        tf_saveMap.setMinWidth(200);
        Button b_saveMap = Generators.generateButton("Save the map", 0, 0, Color.BLACK, Color.LIGHTGRAY);
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
				LOGGER.info("IOException - An error occurred while saving the map.");
				notification.setText("Error - Impossible to save the map.");
			}
        });
        TextField tf_saveGame = Generators.generateTextField(SaveLoadSystemGames.DEFAULT_NAME_FOR_GAME_SAVE, 24, 'A', 'z');
        tf_saveGame.setMinWidth(200);
        Button b_saveGame = Generators.generateButton("save the game", 0, 0, Color.BLACK, Color.LIGHTGRAY);
        b_saveGame.setOnAction(e->{
        	try {
				SaveLoadSystemGames.saveGame(createSave(maze), tf_saveGame.getText());
				notification.setText("Game successfully saved.");
			} catch (IOException ioe) {
				LOGGER.info("IOException - An error occured when saving the game.");
				notification.setText("Error - Impossible to save the game.");
			}
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
				try {
					SaveLoadSystemGames.saveGame(createSave(maze), tf_saveGame.getText());
					notification.setText("Game successfully saved.");
				} catch (IOException ioe) {
					LOGGER.info("IOException - An error occured when saving the game.");
					notification.setText("Error - Impossible to save the game.");
				}
				maze.triggersGameOver();
			}else if(response.equals(bt_noSave)) {
				ArrayList<ButtonType> bt_list = new ArrayList<ButtonType>();
				bt_list.add(bt_cancel);
				bt_list.add(bt_noSave);
				Alert areYouSure = Generators.generateAlert("Are you sure ?", "The game will not be saved.", bt_list);
				areYouSure.showAndWait().ifPresent(confirmation->{
					if (confirmation.equals(bt_noSave)) {
						maze.triggersGameOver();
					}
				});
			}
		});
	}
	
	/**
     * Affiche le menu de chargement d'une sauvegarde existante.
     */
	public void showLoadMenu() {
		
        //////////::
		ButtonType bt_cancel= new ButtonType("Cancel");
		ButtonType bt_load = new ButtonType("Load this game");
        
        ArrayList<ButtonType> alb = new ArrayList<>();
		alb.add(bt_cancel);
		alb.add(bt_load);
		
		Text message = new Text();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose a map to import");
		// Créer un filtre pour les fichiers .dat
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers DAT (*.obj)", "*.obj");
        fileChooser.getExtensionFilters().add(extFilter);
        // Définir le répertoire initial du FileChooser
        File initialDirectory = new File(SaveLoadSystemGames.GAMES_DIRECTORY);
        fileChooser.setInitialDirectory(initialDirectory);
        
        Button bLoad = Generators.generateButton("Import",Color.WHITE,Color.BLACK);
		bLoad.setOnAction(e->{
			loadedSave = fileChooser.showOpenDialog(this);
			if(loadedSave==null) {
				message.setText("Operation canceled : no file selected");
			}else {
				message.setText("File selected : "+loadedSave.getName());
			}
		});
        
        ArrayList<Node> ligne1 = new ArrayList<>();
        ligne1.add(bLoad);
        ligne1.add(message);
        ArrayList<ArrayList<Node>> nodes = new ArrayList<>();
        nodes.add(ligne1);
        
        Dialog<ButtonType> dialog = Generators.generateDialog("Loading a save", "Which save do you want to load ?", alb, nodes);
		dialog.setResizable(true);
        dialog.showAndWait().ifPresent(response -> {
			if(response.equals(bt_load)&&loadedSave!=null) {
				ArrayList<ButtonType> bt_list = new ArrayList<ButtonType>();
				bt_list.add(bt_cancel);
				bt_list.add(bt_load);
				Alert areYouSure = Generators.generateAlert("Are you sure ?", "This save will be loaded and a game will started.", bt_list);
				areYouSure.showAndWait().ifPresent(confirmation->{
					if (confirmation.equals(bt_load)) {
						try {
							Save save = SaveLoadSystemGames.loadGame(loadedSave.getName());
							//Adaptation du zoom
							if(this.display.getWindowHeight()>this.display.getWindowWidth()) {
								this.display.setZoom(this.display.getWindowWidth()/(this.maze_height+this.maze_width));
							}else {
								this.display.setZoom(this.display.getWindowHeight()/(this.maze_height+this.maze_width));
							}
							this.maze = new Maze(save);
							
							this.maze.attach(this);
							
							Theme theme = Theme.themesMap.get(save.getData_management().getTheme());
							this.mv=new MonsterView(this.display,this.maze, theme); 
							this.hv=new HunterView(this.display,this.maze,theme);
							if(save.getData_management().isSameScreen()) {
								this.viewCommon.setScene(hv.getScene());
								this.viewCommon.show();
								this.setScene(hv.getScene());
							}else {
								this.viewM.setScene(mv.getScene());
								this.viewH.setScene(hv.getScene());
								this.viewM.show();
								this.viewH.show();
							}
							this.hide();
							this.switchInGameView(); //Ici Vérifie qui joue (IA ou joueur) pour pouvoir démarrer le jeu.
							
						} catch (ClassNotFoundException e1) {
							LOGGER.info("ClassNotFoundException when loading a save.");
							e1.printStackTrace();
						} catch (IOException e1) {
							LOGGER.info("IOException when loading a save.");
							e1.printStackTrace();
						}
						
						
					}
				});
			}
		});
	}
	
	/**
     * Crée un objet Save à partir des données d'un labyrinthe.
     *
     * @param maze Le labyrinthe à partir duquel créer l'objet Save.
     * @return Un objet Save contenant les données du labyrinthe.
     */
	public static Save createSave(Maze maze) {
		return new Save(maze.getDataMan(), maze.getData(), maze.getExit().getData(), maze.getMonster().getData(), maze.getHunter().getData());
	}

	/**
     * Obtient le nom par défaut du monstre.
     *
     * @return Le nom par défaut du monstre.
     */
	public static String getDefaultNameMonster() {
		return DEFAULT_NAME_MONSTER;
	}

	/**
     * Obtient le nom par défaut du chasseur.
     *
     * @return Le nom par défaut du chasseur.
     */
	public static String getDefaultNameHunter() {
		return DEFAULT_NAME_HUNTER;
	}
	
	/**
     * Obtient le niveau par défaut de l'IA du joueur.
     *
     * @return Le niveau par défaut de l'IA du joueur.
     */
	public static String getDefaultIaPlayer() {
		return DEFAULT_IA_PLAYER;
	}
	
	

}