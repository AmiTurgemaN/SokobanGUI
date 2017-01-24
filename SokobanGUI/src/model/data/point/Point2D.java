package model.data.point;

import controller.commands.Direction;

public class Point2D extends GeneralIntegerPoint{

	private static final long serialVersionUID = 1L;

	public Point2D(int x,int y)
	{
		this.x=x;
		this.y=y;
	}

	public Point2D()
	{
		this.x=0;
		this.y=0;
	}
	
	public void setX(int x)
	{
		this.x=x;
	}
	public void setY(int y)
	{
		this.y=y;
	}
	public int getX()
	{
		return this.x;
	}
	public int getY()
	{
		return this.y;
	}
	
	public String toString(){
		return ("("+this.x + ","+ this.y+")");
	}

	@Override
	public int compareTo(GeneralIntegerPoint point) {
		if(this.getX()!=point.getX())
			return this.getX()-point.getX();
		else if(this.getY()!=point.getY())
			return this.getY()-point.getY();
		return 0;
	}

	@Override
	public GeneralIntegerPoint getNextPointByDirection(Direction direction) {
		Point2D point = new Point2D();
		switch(direction)
		{
		case UP:
			point.setX(this.getX());
			point.setY(this.getY()-1);
			break;
		case DOWN:
			point.setX(this.getX());
			point.setY(this.getY()+1);
			break;
		case RIGHT:
			point.setX(this.getX()+1);
			point.setY(this.getY());
			break;
		case LEFT:
			point.setX(this.getX()-1);
			point.setY(this.getY());
			break;
		}
		return point;
	}
}
