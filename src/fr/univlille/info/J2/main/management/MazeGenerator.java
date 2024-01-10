package fr.univlille.info.J2.main.management;

import fr.univlille.info.J2.main.utils.Utils;

/**
 * La classe MazeGenerator est utilisée pour générer aléatoirement un labyrinthe.
 * Elle utilise l'algorithme de génération de labyrinthe basé sur la méthode de "recursive backtracking".
 * 
 * Le labyrinthe généré est représenté par une grille de cases avec des passages ou des murs.
 * La classe utilise des tags pour représenter différentes étapes du processus de génération.
 * 
 * @author arthur.debacq.etu
 * @author camille.fourmaintraux.etu
 * @author jessy.top.etu
 * @author theo.franos.etu
 * 
 */
public class MazeGenerator {
	
	/**
	 * Instance unique de MazeGenerator, suivant le modèle Singleton.
	 */
	private static MazeGenerator maze_generator;
	
	/**
	 * Dimensions de la grille pour la génération du labyrinthe.
	 */
	private int[] grid_size;
	
	/**
	 * Grille représentant la structure initiale du labyrinthe (avant la génération).
	 */
	private boolean[][] grid;
	
	/**
	 * Labyrinthe généré après l'application de l'algorithme.
	 */
	private boolean[][] maze;
	
	/**
	 * Valeurs associées à chaque cellule de la grille (pour des traitements spécifiques).
	 */
	private int[][] grid_val;
	
	/**
	 * Probabilité d'apparition de murs lors de la génération du labyrinthe.
	 */
	private int probability;
	
	/**
	 * Coordonnée X actuelle lors de la génération du labyrinthe.
	 */
	private int cX;
	
	/**
	 * Coordonnée Y actuelle lors de la génération du labyrinthe.
	 */
	private int cY;
	
	/**
	 * Indicateur indiquant si la génération du labyrinthe est terminée.
	 */
	private boolean over;
	
	/**
	 * Constructeur privé pour suivre le modèle Singleton.
	 */
	protected MazeGenerator(){}
	
	/**
	 * Génère un labyrinthe aléatoire avec les dimensions spécifiées.
	 * 
	 * @param hauteur La hauteur du labyrinthe.
	 * @param largeur La largeur du labyrinthe.
	 * @param probability La probabilité de créer un passage au lieu d'un mur.
	 * @return Une représentation du labyrinthe généré.
	 */
	public boolean[][] generateRandomMaze(int hauteur, int largeur, int probability){
		this.grid_size = new int[2];
		this.cX = 0;
		this.cY = 0;
		this.over = false;
		this.grid_size[0] = hauteur/2;
		this.grid_size[1] = largeur/2;
		this.probability=probability;
		this.setup();
		return this.maze;
	}

	/**
	 * Initialise les structures de données pour la génération du labyrinthe.
	 */
	void setup(){
	  grid = new boolean[2*grid_size[0]+1][2*grid_size[1]+1];
	  maze = new boolean[2*grid_size[0]+1][2*grid_size[1]+1];
	  grid_val = new int[grid_size[0]][grid_size[1]];
	  for(int i = 0; i < 2*grid_size[0]+1; i++){
	    for(int j = 0; j < 2*grid_size[1]+1; j++){
		      if(i%2 == 1 && j%2 == 1) {
		    	  grid[i][j] = true;
		    	  maze[i][j] = true;
		      }else {
		    	  if(probability>=Utils.random.nextInt(99)+1) {
			    	  maze[i][j] = false;
			    	}else {
			    		maze[i][j] = true;
			    	}
			    		
		    	  grid[i][j] = false;
		    	  
	    }
	    	
	    }
	  }
	  grid_val[cX][cY] = -1;
	  while(!this.over) {
			for(int i = 0; i < 50; i++) this.iterate();
	  }
	}

	/**
	 * Effectue une itération de la génération du labyrinthe.
	 */
	void iterate(){
	  int[] dir = new int[4];
	  int count_dir = 0;
	  if(cX < grid_size[0]-1){
	    if(grid_val[cX+1][cY] == 0 && !grid[2+cX*2][1+cY*2]){
	      dir[count_dir] = 1;
	      count_dir++;
	    }
	  }
	  if(cY > 0){
	    if(grid_val[cX][cY-1] == 0 && !grid[1+cX*2][cY*2]){
	      dir[count_dir] = 2;
	      count_dir++;
	    }
	  }
	  if(cX > 0){
	    if(grid_val[cX-1][cY] == 0 && !grid[cX*2][1+cY*2]){
	      dir[count_dir] = 3;
	      count_dir++;
	    }
	  }
	  if(cY < grid_size[1]-1){
	    if(grid_val[cX][cY+1] == 0 && !grid[1+cX*2][2+cY*2]){
	      dir[count_dir] = 4;
	      count_dir++;
	    }
	  }
	  if(count_dir > 0){
	    int i = Utils.random.nextInt(count_dir);
	    if(dir[i] == 1){
	    	maze[2+cX*2][1+cY*2]=true;
	      grid[2+cX*2][1+cY*2] = true;
	      cX++;
	    }
	    else if(dir[i] == 2){
	    	maze[1+cX*2][cY*2]=true;
	      grid[1+cX*2][cY*2] = true;
	      cY--;
	    }
	    else if(dir[i] == 3){
	    	maze[cX*2][1+cY*2]=true;
	      grid[cX*2][1+cY*2] = true;
	      cX--;
	    }
	    else{ 
	    	maze[1+cX*2][2+cY*2]=true;
	      grid[1+cX*2][2+cY*2] = true;
	      cY++;
	    }
	    grid_val[cX][cY] = dir[i];
	  }
	  else{
	    if(grid_val[cX][cY] == 1) cX--;
	    else if(grid_val[cX][cY] == 2) cY++;
	    else if(grid_val[cX][cY] == 3) cX++;
	    else if(grid_val[cX][cY] == 4) cY--;
	    else over = true;
	  }
	  
	}
	
	/**
	 * Retourne une représentation textuelle du labyrinthe.
	 * 
	 * @return Une chaîne de caractères représentant le labyrinthe.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int h = 0; h < 2*grid_size[0]+1; h++){
			for(int l = 0; l < 2*grid_size[1]+1; l++){
				if(grid[h][l]) {
					sb.append('.');
				}else {
					sb.append('X');
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * Retourne l'instance unique de MazeGenerator.
	 * Si aucune instance n'existe, elle est créée.
	 * 
	 * @return L'instance unique de MazeGenerator.
	 */
	protected static MazeGenerator getMazeGenerator() {
		if(maze_generator==null) {
			maze_generator=new MazeGenerator();
		}
		return maze_generator;
	}
}

