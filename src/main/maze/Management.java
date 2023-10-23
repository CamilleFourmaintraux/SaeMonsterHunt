package main.maze;

import main.strategy.hunter.HunterView;
import main.strategy.monster.MonsterView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class Management extends Stage{
	protected int window_height; //500 par défault
	protected int window_width; //500 par défault
	protected int gap_X; //0 par défault
	protected int gap_Y; //0 par défault
	protected int zoom; //30 par défault
	protected Color colorOfWalls;
	protected Color colorOfFloors;
	
	protected Maze maze;
	public MonsterView mv;
	public HunterView hv;
	
	public Management(int probability, int maze_height, int maze_width, int window_height, int window_width, int gap_X, int gap_Y, int zoom, Color colorOfWalls,
		Color colorOfFloors) {
		this.window_height = window_height;
		this.window_width = window_width;
		this.gap_X = gap_X;
		this.gap_Y = gap_Y;
		this.zoom = zoom;
		this.colorOfWalls = colorOfWalls;
		this.colorOfFloors = colorOfFloors;
		
		AnimationTimer windowsManagement = new AnimationTimer(){

			@Override
			public void handle(long arg0) {
				if(maze.isGameOver) {
					mv.close();
					hv.close();
					show();
					maze.isGameOver=false;
					this.stop();
				}
				
			}
			
		};
		
		//Initialisation du label (pour le titre)
		Label titre = new Label("MonsterHunt");
		titre.setStyle("-fx-font-size: 25px;-fx-background-color: #FAA420;-fx-text-fill: #7D0F07;");
		//Initialisation du button (pour démarrer le jeu)
		Button play = new Button("Play");
		play.setOnAction(e->{
			this.maze=new Maze(probability, maze_height, maze_width);
			this.mv=new MonsterView(this.window_height,this.window_width,this.gap_X,this.gap_Y,this.zoom,this.colorOfWalls,this.colorOfFloors,this.maze.monster);
			this.hv=new HunterView(this.window_height,this.window_width,this.gap_X,this.gap_Y,this.zoom,this.colorOfWalls,this.colorOfFloors,this.maze.hunter);
			this.mv.setX(this.window_width/2);
			this.hv.setX(this.window_width*2);
			this.mv.show();
			this.hv.show();
			this.close();
			windowsManagement.start();
		});
		play.setStyle("-fx-background-color: #3498db;\n"
				+ "    -fx-text-fill: #ffffff;\n"
				+ "    -fx-font-size: 14px;");
		play.setOnMouseEntered(e -> {
		    play.setStyle("-fx-background-color: #e74c3c;\n"
					+ "    -fx-text-fill: #ffffff;\n"
					+ "    -fx-font-size: 14px;");
		});

		// Rétablir le style de base lorsque la souris quitte le bouton
		play.setOnMouseExited(e -> {
			play.setStyle("-fx-background-color: #3498db;\n"
					+ "    -fx-text-fill: #ffffff;\n"
					+ "    -fx-font-size: 14px;");
		});
		BorderPane root = new BorderPane();
		root.setCenter(titre);
		root.setBottom(play);
		root.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), Insets.EMPTY)));
		BorderPane.setAlignment(play, Pos.CENTER);
		Scene scene = new Scene(root, this.window_height, this.window_width);
		//scene.getStylesheets().add("./css/style.css");
		this.setScene(scene);
		this.setTitle("MONSTERHUNTER - Menu");
		this.show();
		
	}
	
}
