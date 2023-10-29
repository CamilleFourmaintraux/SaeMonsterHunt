package main.maze;


import fr.univlille.iutinfo.cam.player.perception.ICoordinate;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import main.maze.cells.CellWithText;
import main.maze.cells.Coordinate;
import main.utils.Observer;
import main.utils.Subject;

public class HunterView implements Observer{
	//Affichage
	public int window_height; //500 par défault
	public int window_width; //500 par défault
	public int gap_X; //0 par défault
	public int gap_Y; //0 par défault
	public int zoom; //30 par défault
	public Color colorOfWalls;
	public Color colorOfFloors;
	Color colorOfFog;
	
	//Subject
	Maze maze;
	
	//Sprite (Rectangle pour le moment)
	CellWithText sprite_shot;
	CellWithText selection;
	
	Group group_stage;
	Group group_sprite;
	Group group_map;
	Group group_texts;
	
	Scene scene;

	public HunterView(int window_height, int window_width, int gap_X, int gap_y, int zoom, Color colorOfWalls,
		Color colorOfFloors, Color colorOfFog, Maze maze) {
		
		//Initiation de la fenetre
		this.window_height = window_height;
		this.window_width = window_width;
		this.gap_X = gap_X;
		this.gap_Y = gap_y;
		this.zoom = zoom;
		this.colorOfWalls = colorOfWalls;
		this.colorOfFloors = colorOfFloors;
		this.colorOfFog = colorOfFog;
		
		this.maze = maze;
		this.maze.attach(this);
		
		//Initiation des groupes
		this.group_stage=new Group();
		this.group_sprite=new Group();
		this.group_texts=new Group();
		this.group_map = new Group();
		
		this.initiateSprites();
		this.draw();
		//group_texts.getChildren().add(new CellWithText(0,0,50,Color.CORAL,this.gap_X,this.gap_Y,"pomme"));
		this.group_stage.getChildren().add(group_map);
		this.group_stage.getChildren().add(group_sprite);
		this.group_stage.getChildren().add(group_texts);
		
		//Scene
		this.scene=new Scene(this.group_stage, this.window_height, this.window_width);
	}

	
	
	@Override
	public void update(Subject s) {
		this.sprite_shot.setX(calculDrawX(this.maze.hunter.getCol()));
		this.sprite_shot.setY(calculDrawY(this.maze.hunter.getRow()));
		this.sprite_shot.setVisible(true);
		
	}

	@Override
	public void update(Subject s, Object o) {
		this.sprite_shot.setX(calculDrawX(this.maze.hunter.getCol()));
		this.sprite_shot.setY(calculDrawY(this.maze.hunter.getRow()));
		this.sprite_shot.setVisible(true);
	}
	
	public void draw() {
		for(int h=0; h<this.maze.hunter.traces.length; h++) {
			for(int l=0; l<this.maze.hunter.traces[h].length; l++) {
				//Codage des rectangles
				CellWithText cell = new CellWithText(l, h, zoom, this.colorOfFog,Color.DARKGREY,1,this.gap_X,this.gap_Y,new Text(""));
				cell.setOnMouseClicked(e->{
					this.select(cell,e);
				});
				group_map.getChildren().add(cell);
				cell.getText().setOnMouseClicked(e->{
					this.select(cell,e);
				});
				this.group_texts.getChildren().add(cell.getText());
				}
			}
	}
	/*//TODO Corriger et finir les fonctions redraw
	public void redraw(int new_gap_X, int new_gap_Y, int new_zoom) {
		for(Node e:this.group_map.getChildren()) {
			CellWithText r = (CellWithText)e;
			this.redrawCell(r, new_gap_X, new_gap_Y, new_zoom);
		}
		for(Node e:this.group_sprite.getChildren()) {
			CellWithText r = (CellWithText)e;
			this.redrawCell(r, new_gap_X, new_gap_Y, new_zoom);
		}
	}
	
	public void redrawCell(CellWithText r,int new_gap_X, int new_gap_Y, int new_zoom) {
		int y = this.calculCoordX(r);
		int x = this.calculCoordY(r);
		this.setZoom(new_zoom);
		this.setGap_X(new_gap_X);
		this.setGap_Y(new_gap_Y);
		r.setX(calculDrawX(x));
		r.setY(calculDrawY(y));
		r.setWidth(zoom);
		r.setHeight(zoom);
		r.setX(x+(zoom/3));
		r.setY(y+(zoom/2));
	}*/
	
	public int calculCoordX(Rectangle r) {
		return (int)((r.getX()-gap_X)/zoom);
	}
	public int calculCoordY(Rectangle r) {
		return (int)((r.getY()-gap_Y)/zoom);
	}
	public int calculDrawX(int x) {
		return x*zoom+gap_X;
	}
	public int calculDrawY(int y) {
		return y*zoom+gap_Y;
	}
	
	private void initiateSprites() {
		//initialisation du sprite de selection
		this.selection =  new CellWithText(0,0, this.zoom, Color.TRANSPARENT, Color.RED, 3, this.gap_X, this.gap_Y, "Shot");
		this.selection.setOnMouseClicked(e->{
			this.select(selection, e);
		});
		this.selection.setVisible(false);
		
		//initialisation du sprite du tir
		this.sprite_shot=new CellWithText(this.maze.hunter.getCoord(), this.zoom, Color.TRANSPARENT, Color.YELLOW, 5, this.gap_X, this.gap_Y, "Shot");
		this.sprite_shot.setOnMouseClicked(e->{
			this.select(this.sprite_shot,e);
			//this.sprite_shot.setVisible(false);
		});
		this.sprite_shot.setVisible(false);
		
		//On ajoute les sprites au groupe associé.
		this.group_sprite.getChildren().add(this.sprite_shot);
		this.group_sprite.getChildren().add(this.selection);
	}
	
	public void select(CellWithText r, MouseEvent e) {
		int y = this.calculCoordY(r);
		int x = this.calculCoordX(r);
		this.selection.setY(this.calculDrawY(y));
		this.selection.setX(this.calculDrawX(x));
		this.selection.toFront();
		this.selection.setVisible(true);
		//System.out.println("("+y+","+x+") est sélectionné.");
		if(e.isShiftDown()) {
			ICoordinate c = new Coordinate(y,x);
			if(this.maze.shoot(c)) {
				this.actualizeCell(c);
			}
			
		}
	}
	
	//TODO Aucun rectangle trouvé
	public CellWithText searchSprite(Group group, ICoordinate c) {
		for(Node e:group.getChildren()) {
			if(e.getClass()==CellWithText.class) {
				CellWithText s = (CellWithText) e;
				if(s.getCoord().equals(c)) {
					return (CellWithText)e;
				}
			}
		}
		return null;
	}
	
	public void actualizeCell(ICoordinate c) {
		try{
			CellWithText cwt = searchSprite(this.group_map, c);
			this.maze.revealCell(cwt,this.colorOfWalls,this.colorOfFloors);
		}catch(Exception exception) {
			System.out.println("Error-Aucun Rectangle Correspondant !");
		}
	}



	public int getWindow_height() {
		return window_height;
	}



	public void setWindow_height(int window_height) {
		this.window_height = window_height;
	}



	public int getWindow_width() {
		return window_width;
	}



	public void setWindow_width(int window_width) {
		this.window_width = window_width;
	}



	public int getGap_X() {
		return gap_X;
	}



	public void setGap_X(int gap_X) {
		this.gap_X = gap_X;
	}



	public int getGap_Y() {
		return gap_Y;
	}



	public void setGap_Y(int gap_Y) {
		this.gap_Y = gap_Y;
	}



	public int getZoom() {
		return zoom;
	}



	public void setZoom(int zoom) {
		this.zoom = zoom;
	}



	public Color getColorOfWalls() {
		return colorOfWalls;
	}



	public void setColorOfWalls(Color colorOfWalls) {
		this.colorOfWalls = colorOfWalls;
	}



	public Color getColorOfFloors() {
		return colorOfFloors;
	}



	public void setColorOfFloors(Color colorOfFloors) {
		this.colorOfFloors = colorOfFloors;
	}
	
	

}
