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
		Stage view1 = new Stage();
		Stage view2 = new Stage();
		view1.setScene(maze.mv.draw());
		view1.show();
		view2.setScene(maze.hv.draw());
		view2.show();
		System.out.println(maze.toString());
		
	}
	
	

}
