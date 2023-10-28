package test.utils;

import main.utils.Observer;
import main.utils.Subject;

public class ConcreteObserver implements Observer{

	public boolean updated = false;
	public Object data = null;

	@Override
	public void update(Subject s) {
		this.updated=true;
		
	}

	@Override
	public void update(Subject s, Object o) {
		this.updated=true;
		data = o;
	}

}
