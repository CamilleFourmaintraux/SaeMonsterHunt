package main.application;

//--module-path /home/iutinfo/eclipse-workspace/Jars/javafx-sdk-21/lib --add-modules=javafx.controls

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import main.maze.Management;

public class MonsterHunt extends Application{

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Management gestion = new Management(500,500,0,0,50,Color.DARKGRAY,Color.LIGHTGRAY);
		Stage view1 = new Stage();
		Stage view2 = new Stage();
		view1.setScene(gestion.mv.draw());
		view1.show();
		view2.setScene(gestion.hv.draw());
		view2.show();
		//System.out.println(maze.toString());
		
	}
	
	

}
