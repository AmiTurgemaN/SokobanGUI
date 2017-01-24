package model.data.gameObjects;

import javafx.scene.image.Image;
import model.data.point.GeneralIntegerPoint;

public class Player extends GeneralGameObject {

	private static final long serialVersionUID = 1L;
	
	public Player()
	{
		super();
	}
	
	public Player(GeneralIntegerPoint point)
	{
		super(point);
	}

	@Override
	public objectType getType() {
		return objectType.PLAYER;
	}
	
	@Override
	public char getSymbol() {
		if(this.onArea==true)
			return 'B';
		return 'A';
	}

	@Override
	public Image getImage() {
		if(this.onArea==true)
			return null;//return PlayerOnArea Image
		else
			return null;//return Player Image
	}
	
}
