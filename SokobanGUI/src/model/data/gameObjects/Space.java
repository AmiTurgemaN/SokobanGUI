package model.data.gameObjects;

import javafx.scene.image.Image;
import model.data.point.GeneralIntegerPoint;

public class Space extends GeneralGameObject {

	private static final long serialVersionUID = 1L;
	
	public Space()
	{
		super();
	}
	
	public Space(GeneralIntegerPoint point)
	{
		super(point);
	}

	@Override
	public objectType getType() {
		return objectType.SPACE;
	}

	@Override
	public char getSymbol() {
		return ' ';
	}

	@Override
	public Image getImage() {
		return null;
	}

}
