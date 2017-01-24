package model.data.gameObjects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
		Image playerImage=null;
		if(this.onArea)
		{
			try {
				playerImage = new Image(new FileInputStream("resources/PlayerOnArea.png"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else
		{
			try {
				playerImage = new Image(new FileInputStream("resources/player.png"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return playerImage;
	}
	
}
