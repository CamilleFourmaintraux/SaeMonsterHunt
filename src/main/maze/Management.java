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
 * La classe Management represente une fenetre de gestion de jeu pour le jeu "MONSTER-HUNTER".
 * Elle genere les parametres du jeu, la creation de labyrinthes, et la transition entre les differentes vues du jeu.
 * Elle implemente l'interface Observer pour reagir aux evenements du jeu.
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
	 * Constante utilise dans les comboBox pour le choix des joueurs.
	 */
	final String[] IA_LEVELS = new String[] {"Player","IA-Easy","IA-Moderate","IA-Hardcore"};
	

	/**
	 * Constante utilise dans les comboBox pour le choix des themes
	 */
	final String[] THEMES = new String[] {"Cave","Forest","Ocean"};
	
	/**
	 * Constante pour le decalage lors de la generation des labels
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

	//Affichage
	public int window_height; //500 par défault
	public int window_width;//500 par défault
	public Color colorOfWalls;
	public Color colorOfFloors;
	public Color colorOfFog;
	
	//Maze settings	
	/**
	 * maze_height -> stocke la hauteur du labyrinthe
	 */
	public int maze_height;
	
	/**
	 * maze_width -> stocke la largeur du labyrinthe
	 */
	public int maze_width;
	
	/**
	 * probability-> taux d'apparrition des murs
	 */
	public int probability;
	

	/**
	 * zoom-> niveau de zoom sur le labyrinthe
	 */
	public int zoom;
	
	/**
	 * monster_name -> stocke le nom du joueur Monster
	 */
	public String monster_name;
	
	/**
	 * hunter_name -> stocke le nom du joueur Hunter
	 */
	public String hunter_name;
	
	/**
	 * monster_IA -> indique le type de joueur (humain, niveaux de l'ia)
	 */
	public String monster_IA;
	
	/**
	 * hunter_IA -> indique le type de joueur (humain, niveaux de l'ia)
	 */
	public String hunter_IA;
	
	/**
	 * theme -> indique les couleurs avec lesquels le jeu doit s'afficher
	 */
	public String theme;
	
	/**
	*sameScreen -> indique si le jeu se deroule sur la meme fenetre (true) ou sur des fenetres separees (false).
	**/
	public boolean sameScreen;
	
	
	
	/**
	 * Map contenant les differents menus du jeu.
	 */
	public Map<Integer, Scene> menus;
	
	

	/**
	 * Constructeur de la classe Management.
	 *
	 * @param probability le taux de chances que la case du labyrinthe soit un mur.
	 * @param maze_height La hauteur du labyrinthe.
	 * @param maze_width La largeur du labyrinthe.
	 * @param window_height La hauteur de la fenetre.
	 * @param window_width La largeur de la fenetre.
	 * @param gap_X L'ecart horizontal dans la vue du labyrinthe. Permet de décaler l'entirete du labyrinthe sur un axe horizontal.
	 * @param gap_Y L'ecart vertical dans la vue du labyrinthe.Permet de décaler l'entirete du labyrinthe sur un axe vertical.
	 * @param zoom Le niveau de zoom de la vue du labyrinthe.
	 * @param colorOfWalls La couleur des murs.
	 * @param colorOfFloors La couleur du sol.
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
	 * Met a jour l'observateur en reagissant a un changement dans le sujet observe.
	 *
	 * @param s Le sujet observe dont l'etat a ete modifie.
	 */
	@Override
	public void update(Subject s) {
		if(!this.gameOver()) {
			this.switchInGameView();
		}
	}

	/**
	 * Met a jour l'observateur en reagissant a un changement dans le sujet observe avec des donnes specifiques.
	 *
	 * @param s Le sujet observe dont l'etat a ete modifie.
	 * @param o Un objet contenant des informations specifiques sur la mise  jour.
	 */
	@Override
	public void update(Subject s, Object o) {
		if(!this.gameOver()) {
			this.switchInGameView();
		}

	}

	/**
	 * Vrifie si le jeu est termine.
	 *
	 * @return true si le jeu est termine, sinon faux.
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
	 * Affiche une boite de dialogue indiquant a qui est le tour.
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
	
	public void wait(int secondes) {
		try {
			Thread.sleep(secondes*1000);
		} catch (InterruptedException e) {
			System.out.println("InterruptedException");
		}
	}
	
	public void monsterIAplay() {
			this.maze.move(this.maze.monster.play());
	}
	
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
	 * Genere le menu principal du jeu, permettant  l'utilisateur de definir des parametres pour le jeu
	 * (noms des personnages, niveaux d'IA, etc.) et de lancer une partie.
	 *
	 * @param probability le taux de chances que la case du labyrinthe soit un mur.
	 * @param maze_height La hauteur du labyrinthe.
	 * @param maze_width La largeur du labyrinthe.
	 * @param gap_X L'espacement horizontal entre les cellules du labyrinthe.
	 * @param gap_Y L'espacement vertical entre les cellules du labyrinthe.
	 * @param zoom Le niveau de zoom pour l'affichage du labyrinthe.
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
	 * Genere le menu des parametres du jeu, permettant  l'utilisateur de personnaliser diverses options telles que
	 * la taille du labyrinthe, le theme, etc.
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
	 * Applique un style de base a un bouton, y compris le style lors du survol de la souris.
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
	 * Genere un bouton avec un texte donne et le positionne aux coordonnes specifies.
	 *
	 * @param msg Le texte affiche sur le bouton.
	 * @param x La position horizontale du bouton.
	 * @param y La position verticale du bouton.
	 * @return Le bouton genere.
	 */
	public Button generateButton(String msg, double x, double y) {
		Button button = new Button(msg);
		//this.download(button);
		this.setLayout(button, x-(button.getWidth()/2) ,y);
		this.applyStyleToButton(button);
		return button;
	}

	/**
	 * Genere une liste deroulante (ComboBox) avec les valeurs specifies et la positionne aux coordonnes specifies.
	 *
	 * @param values Les valeurs affiche dans la liste deroulante.
	 * @param x La position horizontale de la liste deroulante.
	 * @param y La position verticale de la liste deroulante.
	 * @return La liste deroulante genere.
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
	 * Genere un TextField avec une valeur par defaut et le positionne aux coordonnes specifies.
	 *
	 * @param defaultValue La valeur par defaut du champ de texte.
	 * @param x La position horizontale du champ de texte.
	 * @param y La position verticale du champ de texte.
	 * @param maxLength La longueur maximale du texte autorise dans le champ.
	 * @param limit1 Le premier caractere definissant le debut de l'ensemble des caracteres autorises
	 * @param limit2 Le deuxime caractere definissant la fin de l'ensemble des caracteres autorises
	 * @return Le TextField genere.
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
	 * Gnre un Label utilise comme titre de menu/fenetre.
	 *
	 * @param title le titre du menu/fenetre.
	 * @return Le label genere.
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
	 * Permet de definir la couleur de fond d'un element identifie par son ID.
	 *
	 * @param id L'identifiant de l'element modifie.
	 * @param fill La couleur de fond applique.
	 */
	public void setBackGround(int id, Color fill) {
		menus.get(id).setFill(fill);
	}

	/**
	 * Recupere une scene avec un identifiant donne.
	 *
	 * @param id L'identifiant de la scene que l'on cherche.
	 * @return La scene correspondant a l'identifiant specifie.
	 */
	public Scene getScene(int id) {
		return this.menus.get(Integer.valueOf(id));
	}

	/**
	 * Positionne un element aux coordonnes specifies.
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

}
