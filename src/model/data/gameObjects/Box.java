package model.data.gameObjects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import model.data.point.GeneralIntegerPoint;

public class Box extends GeneralGameObject {

	private static final long serialVersionUID = 1L;

	public Box()
	{
		super();
	}

	public Box(GeneralIntegerPoint point)
	{
		super(point);
	}

	@Override
	public objectType getType() {
		return objectType.BOX;
	}

	public char getSymbol() {
		if(this.onArea==true)
			return '$';
		return '@';
	}

	@Override
	public Image getImage() {
		Image boxImage=null;
		try{
			if((this.point.getX()%2==0 && this.point.getY()%2==0) || (this.point.getX()%2==1 && this.point.getY()%2==1))
			{
				if(this.onArea)
					boxImage = new Image(new FileInputStream("resources/BoxOnArea.png"));
				else
					boxImage = new Image(new FileInputStream("resources/box.png"));
			}
			else
			{
				if(this.onArea)
					boxImage = new Image(new FileInputStream("resources/BoxOnArea black.png"));
				else
					boxImage = new Image(new FileInputStream("resources/black box.png"));
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return boxImage;
	}
}
