package model.data.gameObjects;

import java.io.Serializable;

import model.data.point.GeneralIntegerPoint;

public abstract class GeneralGameObject implements Serializable, gameObject {

	private static final long serialVersionUID = 1L;
	protected GeneralIntegerPoint point;
	protected boolean onArea;
	
	public boolean isOnArea() {
		return onArea;
	}
	public void setOnArea(boolean onArea) {
		this.onArea = onArea;
	}
	public GeneralIntegerPoint getPoint() {
		return point;
	}
	public void setPoint(GeneralIntegerPoint point) {
		this.point=point;
	}
	public abstract objectType getType();
	
	public GeneralGameObject(GeneralIntegerPoint point) {
		super();
		this.point = point;
		this.onArea=false;
	}
	public GeneralGameObject() {
		super();
		this.onArea=false;
	}
	public abstract char getSymbol();
	
}
