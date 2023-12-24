package fr.univlille.info.J2.main.management;

import fr.univlille.info.J2.main.utils.Utils;

public class MazeGenerator {
	
	private static MazeGenerator maze_generator;
	private int[] grid_size;
	private boolean[][] grid;
	private boolean[][] maze;
	private int[][] grid_val;
	private int probability;
	private int cX;
	private int cY;
	private boolean over;
	
	protected MazeGenerator(){}
	
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
	
	protected static MazeGenerator getMazeGenerator() {
		if(maze_generator==null) {
			maze_generator=new MazeGenerator();
		}
		return maze_generator;
	}
}

