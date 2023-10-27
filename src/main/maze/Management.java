package main.maze;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import main.utils.Observer;
import main.utils.Subject;
import main.utils.Utils;


public class Management extends Stage implements Observer{
	
	final int ID_SETTINGS = 1;
	final int ID_PLAY = 2;
	
	final String[] IA_LEVELS = new String[] {"Player","IA-Easy","IA-Moderate","IA-Hardcore"};
	
	final int SPACING = 5;
	final int LABEL_MIN_WIDTH = 120;
	
	protected Maze maze;
	public MonsterView mv;
	public HunterView hv;
	
	//Affichage
	public int window_height; //500 par défault
	public int window_width;//500 par défault
	public Color colorOfWalls;
	public Color colorOfFloors;
	public Map<Integer, Scene> menus;
	public boolean sameScreen;
	
	
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

	@Override
	public void update(Subject s) {
		if(!this.gameOver()) {
			this.switchInGameView();
		}
	}

	@Override
	public void update(Subject s, Object o) {
		if(!this.gameOver()) {
			this.switchInGameView();
		}
		
	}
	
	public boolean gameOver() {
		if(this.maze.isGameOver) {
			gameOverScreen();
			this.setScene(this.getScene(this.ID_PLAY));
			maze.isGameOver=false;
			return true;
		}
		return false;
	}
	
	public void switchInGameView() {
		if(this.maze.isMonsterTurn) {
			this.setScene(mv.scene);
		}else {
			this.setScene(hv.scene);
		}
	}
	
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
	
	public Button generateButton(String msg, double x, double y) {
		Button button = new Button(msg);
		//this.download(button);
		this.setLayout(button, x-(button.getWidth()/2) ,y);
		this.applyStyleToButton(button);
		return button;
	}
	
	public ComboBox<String> generateComboBox(String[] values, double x, double y) {
		ComboBox<String> theme = new ComboBox<String>();
		theme.getItems().addAll(values);
		theme.setValue(values[0]);
		//this.download(theme);
		this.setLayout(theme, x ,y);
		return theme;
	}
	
	public Label generateLabel(String msg, double x, double y, double minWidth) {
		Label label = new Label(msg);
		this.setMinWidth(minWidth);
		//this.download(label);
		this.setLayout(label, x,y);
		return label;
	}
	
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
	
	public Label generateTitle(String title) {
		Label label = new Label(" "+title+" ");
		this.setLayout(label, 0, 0);
		this.applyStyleToTitle(label);
		label.setTextAlignment(TextAlignment.CENTER);
		return label;
	}
	
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
	
	public void setBackGround(int id, Color fill) {
		menus.get(id).setFill(fill);
	}
	
	public Scene getScene(int id) {
		return this.menus.get(Integer.valueOf(id));
	}
	
	public void setLayout(Node node, double x, double y) {
		node.setLayoutX(x);
		node.setLayoutY(y);
	}
	
	public double calculPercentage(double total, double percentage) {//percentage must be between 0 and 100
		return (percentage/100)*total;
	}

	public void gameOverScreen() {
		Stage gameOverStage = new Stage();
		gameOverStage.setTitle("Game Over");
		gameOverStage.setWidth(400);
		gameOverStage.setHeight(200);

		// Créez des éléments pour l'interface "perdu"
		Button restartButton = new Button("Rejouer");
		restartButton.setOnAction(e -> {
			// Gérez l'action de redémarrage du jeu ici
			gameOverStage.close(); // Fermez la fenêtre "perdu" après avoir cliqué sur le bouton "Rejouer"
		});

		// Ajoutez ces éléments à la scène
		VBox layout = new VBox(20);
		layout.getChildren().addAll(restartButton);

		// Créez une scène et ajoutez le conteneur à la scène
		Scene scene = new Scene(layout);
		gameOverStage.setScene(scene);

		// Affichez la fenêtre "perdu"
		gameOverStage.show();
	}
	
}

