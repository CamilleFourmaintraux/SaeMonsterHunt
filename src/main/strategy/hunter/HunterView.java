package main.strategy.hunter;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.maze.cells.Coordinate;
import main.utils.Observer;
import main.utils.Subject;
import main.utils.Utils;

public class HunterView extends Stage implements Observer{
	//Affichage
	int window_height = 500; //500 par défault
	int window_width = 500; //500 par défault
	int gap_X=0; //0 par défault
	int gap_Y=0; //0 par défault
	int zoom = 50 ; //30 par défault
	
	//Subject
	Hunter hunter;
	
	//Sprite (Rectangle pour le moment)
	Rectangle sprite_shot;
	Rectangle selection;
	
	public HunterView(Hunter hunter) {
		this.hunter=hunter;
		hunter.attach(this);
		this.initiateSprites();
	}

	@Override
	public void update(Subject s) {
		this.sprite_shot.setX(this.hunter.lastShot.getCol()*zoom+gap_X);
		this.sprite_shot.setY(this.hunter.lastShot.getRow()*zoom+gap_Y);
		
	}

	@Override
	public void update(Subject s, Object o) {
		this.sprite_shot.setX(this.hunter.lastShot.getCol()*zoom+gap_X);
		this.sprite_shot.setY(this.hunter.lastShot.getRow()*zoom+gap_Y);
		
	}
	
	public Scene draw() {
		Group root = new Group();
		Color paint;
		root.getChildren().add(this.selection);
		for(int h=0; h<this.hunter.traces.length; h++) {
			for(int l=0; l<this.hunter.traces[h].length; l++) {
				paint=Color.AZURE;
				//Codage des rectangles
				Rectangle r = Utils.makeRectangle(l*zoom+gap_X, h*zoom+gap_Y, zoom,zoom, paint);
				r.setOnMouseClicked(e->{
					this.select(r,e);
					});
				root.getChildren().add(r);
				}
			}
		root.getChildren().add(sprite_shot);
		Scene scene = new Scene(root, 500, 500);
		return scene;
	}
	
	private void initiateSprites() {
		//initialisation du sprite de selection
		this.selection = Utils.makeRectangle(-1,-1, zoom,zoom, Color.TRANSPARENT);
		this.selection.setOnMouseClicked(e->{
			if(e.isShiftDown()) {
				hunter.shoot(new Coordinate(((int)this.selection.getY()-gap_Y)/zoom,((int)this.selection.getX()-gap_X)/zoom));
			}else {
				this.selection.setVisible(false);
			}
		});
		this.selection.setStroke(Color.RED);
		this.selection.setStrokeWidth(3);
		this.selection.setVisible(false);
		
		//initialisation du sprite du tir
		this.sprite_shot=Utils.makeRectangle(this.hunter.lastShot.getCol()*zoom+gap_X,this.hunter.lastShot.getRow()*zoom+gap_Y, zoom,zoom, Color.YELLOW);
		this.sprite_shot.setOnMouseClicked(e->{
			this.select(this.sprite_shot,e);
			this.sprite_shot.setVisible(false);
		});
	}
	
	public void select(Rectangle r, MouseEvent e) {
		int y = ((int)(r.getY()-gap_Y)/zoom);
		int x = ((int)(r.getX()-gap_X)/zoom);
		this.selection.setY(y*zoom+gap_Y);
		this.selection.setX(x*zoom+gap_X);
		this.selection.toFront();
		this.selection.setVisible(true);
		System.out.println("("+y+","+x+") est sélectionné.");
		if(e.isShiftDown()) {
			hunter.shoot(new Coordinate(y,x));
			r.setFill(Color.AQUA);
		}
				/*r.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent ke) {if(ke.getCode()==KeyCode.ENTER) {}}
		        });*/
	}

}
