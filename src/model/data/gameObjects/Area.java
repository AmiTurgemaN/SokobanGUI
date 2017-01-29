package model.data.gameObjects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
		Image areaImage=null;
		try{
			if((this.point.getX()%2==0 && this.point.getY()%2==0) || (this.point.getX()%2==1 && this.point.getY()%2==1))
				areaImage = new Image(new FileInputStream("resources/area.png"));
			else
				areaImage = new Image(new FileInputStream("resources/black area.png"));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return areaImage;
	}

}
