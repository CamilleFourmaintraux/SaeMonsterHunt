package main.strategy.hunter;


import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.maze.cells.Coordinate;
import main.utils.Observer;
import main.utils.Subject;
import main.utils.Utils;

public class HunterView extends Stage implements Observer{
	//Affichage
	protected int window_height; //500 par défault
	protected int window_width; //500 par défault
	protected int gap_X; //0 par défault
	protected int gap_Y; //0 par défault
	protected int zoom; //30 par défault
	protected Color colorOfWalls;
	protected Color colorOfFloors;
	
	//Subject
	Hunter hunter;
	
	//Sprite (Rectangle pour le moment)
	Rectangle sprite_shot;
	Rectangle selection;
	
	Group root;

	public HunterView(int window_height, int window_width, int gap_X, int gap_y, int zoom, Color colorOfWalls,
		Color colorOfFloors, Hunter hunter) {
		this.window_height = window_height;
		this.window_width = window_width;
		this.gap_X = gap_X;
		this.gap_Y = gap_y;
		this.zoom = zoom;
		this.colorOfWalls = colorOfWalls;
		this.colorOfFloors = colorOfFloors;
		this.hunter = hunter;
		this.hunter.attach(this);
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
		this.root = new Group();
		root.getChildren().add(this.selection);
		for(int h=0; h<this.hunter.traces.length; h++) {
			for(int l=0; l<this.hunter.traces[h].length; l++) {
				//Codage des rectangles
				RectangleWithText r = new RectangleWithText(new Text(""), l*zoom+gap_X, h*zoom+gap_Y, zoom,zoom, Color.BLACK,Color.DARKGREY,1);
				//System.out.println(r.getRect().getPa);
				r.setOnMouseClicked(e->{
					this.select(r,e);
				});
				root.getChildren().add(r);
				r.getText().setOnMouseClicked(e->{
					this.select(r,e);
				});
				root.getChildren().add(r.getText());
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
			/*if(e.isShiftDown()) {
				hunter.shoot(new Coordinate(((int)this.selection.getY()-gap_Y)/zoom,((int)this.selection.getX()-gap_X)/zoom));
				
			}else {
				this.selection.setVisible(false);
			}*/
			this.select(selection, e);
		});
		this.selection.setStroke(Color.RED);
		this.selection.setStrokeWidth(3);
		this.selection.setVisible(false);
		
		//initialisation du sprite du tir
		this.sprite_shot=Utils.makeRectangle(this.hunter.lastShot.getCol()*zoom+gap_X,this.hunter.lastShot.getRow()*zoom+gap_Y, zoom,zoom, Color.TRANSPARENT);
		this.selection.setStroke(Color.YELLOW);
		this.selection.setStrokeWidth(5);
		this.sprite_shot.setOnMouseClicked(e->{
			this.select(this.sprite_shot,e);
			//this.sprite_shot.setVisible(false);
		});
	}
	
	public void select(Rectangle r, MouseEvent e) {
		int y = ((int)(r.getY()-gap_Y)/zoom);
		int x = ((int)(r.getX()-gap_X)/zoom);
		this.selection.setY(y*zoom+gap_Y);
		this.selection.setX(x*zoom+gap_X);
		this.selection.toFront();
		this.selection.setVisible(true);
		//System.out.println("("+y+","+x+") est sélectionné.");
		if(e.isShiftDown()&&!this.hunter.monsterTurn) {
			Coordinate c = new Coordinate(y,x);
			hunter.shoot(c);
			try{
				this.searchSprite(root, c).setStroke(Color.TRANSPARENT);
				if(this.hunter.traces[this.hunter.lastShot.getRow()][this.hunter.lastShot.getCol()]==-1) {
					this.searchSprite(root, c).setFill(this.colorOfWalls);
				}else {
					this.searchSprite(root, c).setFill(this.colorOfFloors);
					int trace = this.hunter.traces[c.getRow()][c.getCol()];
					if(trace>0) {
						this.searchSprite(root, c).setText(""+trace);
					}
				}
			}catch(Exception exception) {
				System.out.println("Error-Aucun Rectangle Correspondant !");
			}
		}else {
			System.out.println("Chasseur-Juste sélection:"+this.hunter.monsterTurn);
		}
		}
		/*public void select(Sprite r, MouseEvent e) {
			int y = ((int)(r.getY()-gap_Y)/zoom);
			int x = ((int)(r.getX()-gap_X)/zoom);
			this.selection.setY(y*zoom+gap_Y);
			this.selection.setX(x*zoom+gap_X);
			this.selection.toFront();
			this.selection.setVisible(true);
			System.out.println("("+y+","+x+") est sélectionné.");
			if(e.isShiftDown()) {
				Coordinate c = new Coordinate(y,x);
				hunter.shoot(c);
				try{
					if(this.hunter.traces[this.hunter.lastShot.getRow()][this.hunter.lastShot.getCol()]==-1) {
						this.searchSprite(root, c).setFill(Color.BLUE);
					}else {
						this.searchSprite(root, c).setFill(Color.AQUA);
					}
				}catch(Exception exception) {
					System.out.println("Pas de Sprite trouvé !");
				}
				
			}*/
				/*r.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent ke) {if(ke.getCode()==KeyCode.ENTER) {}}
		        });
	}*/
	
	public RectangleWithText searchSprite(Group group, Coordinate c) {
		for(Node e:group.getChildren()) {
			//System.out.println(e.getClass());
			if(e.getClass()==RectangleWithText.class) {
				RectangleWithText s = (RectangleWithText) e;
				//System.out.println("Classe sprite!");
				int y = ((int)(s.getY()-gap_Y)/zoom);
				int x = ((int)(s.getX()-gap_X)/zoom);
				if(c.getRow()==y && c.getCol()==x) {
					return (RectangleWithText)e;
				}
			}
		}
		return null;
	}
	

}
