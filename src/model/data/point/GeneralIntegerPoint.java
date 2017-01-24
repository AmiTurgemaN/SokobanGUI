package model.data.point;

import java.io.Serializable;

import controller.commands.Direction;

public abstract class GeneralIntegerPoint implements Serializable,Comparable<GeneralIntegerPoint>{

	private static final long serialVersionUID = 1L;
	
	protected int x;
	protected int y;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public abstract GeneralIntegerPoint getNextPointByDirection(Direction direction);
}
