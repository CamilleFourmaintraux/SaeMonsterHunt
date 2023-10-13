package main.application;

//--module-path /home/iutinfo/eclipse-workspace/Jars/javafx-sdk-21/lib --add-modules=javafx.controls

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import main.maze.Maze;

public class MonsterHunt extends Application{

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Maze maze = new Maze();
		System.out.println(maze);
		Scene scene = new Scene(maze.draw(),500,500);
		stage.setScene(scene);
		stage.show();
		
	}
	
	
	

}
