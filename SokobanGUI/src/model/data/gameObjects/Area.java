package model.data.gameObjects;

import javafx.scene.image.Image;
import model.data.point.GeneralIntegerPoint;

public class Area extends GeneralGameObject {

	private static final long serialVersionUID = 1L;
	
	public Area()
	{
		super();
	}
	
	public Area(GeneralIntegerPoint point)
	{
		super(point);
	}

	@Override
	public objectType getType() {
		return objectType.AREA;
	}

	@Override
	public char getSymbol() {
		return 'o';
	}

	@Override
	public Image getImage() {
		return null;
		//return Area Image
	}

}
