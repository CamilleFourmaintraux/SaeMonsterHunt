package main.utils;

public interface Observer {
	public void update(Subject s);
	public void update(Subject s, Object o);
}
