package main.strategy.hunter;

import main.strategy.IStrategy;

public interface IHunterStrategy extends IStrategy{
	public void initialize(int nbrRows, int nbrCols);
}
