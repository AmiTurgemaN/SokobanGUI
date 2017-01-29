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
		try{
			if((this.point.getX()%2==0 && this.point.getY()%2==0) || (this.point.getX()%2==1 && this.point.getY()%2==1))
			{
				if(this.onArea)
					playerImage = new Image(new FileInputStream("resources/Ogre Magi area.png"));
				else
					playerImage = new Image(new FileInputStream("resources/Ogre Magi.png"));
			}
			else
			{
				if(this.onArea)
					playerImage = new Image(new FileInputStream("resources/Ogre Magi black area.png"));
				else
					playerImage = new Image(new FileInputStream("resources/Ogre Magi black.png"));
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return playerImage;
	}
}
