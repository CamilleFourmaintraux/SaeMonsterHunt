package main.strategy.monster;

import main.strategy.IStrategy;

public interface IMonsterStrategy extends IStrategy{
	public void initialize(boolean walls[][]);
}
