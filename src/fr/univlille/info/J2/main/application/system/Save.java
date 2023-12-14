package fr.univlille.info.J2.main.application.system;

import java.io.Serializable;

import fr.univlille.info.J2.main.management.exit.SaveExitData;

public class Save implements Serializable{
	private static final long serialVersionUID = -1006454071348557007L;
	
	private SaveMazeData data_maze;
	private SaveExitData data_exit;
	private SaveMonsterData data_monster;
	private SaveHunterData data_hunter;
	
	public Save(SaveMazeData data_maze, SaveExitData data_exit, SaveMonsterData data_monster,
			SaveHunterData data_hunter) {
		this.data_maze = data_maze;
		this.data_exit = data_exit;
		this.data_monster = data_monster;
		this.data_hunter = data_hunter;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SaveMazeData getData_maze() {
		return data_maze;
	}

	public SaveExitData getData_exit() {
		return data_exit;
	}

	public SaveMonsterData getData_monster() {
		return data_monster;
	}

	public SaveHunterData getData_hunter() {
		return data_hunter;
	}
}
