package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.data.level.Level;

public class LevelDisplayer extends Canvas{
	private Level level;
	
	public LevelDisplayer(Level level)
	{
		this.level=level;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
		redraw();
	}
	
	public void redraw()
	{
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0,0,this.getWidth(),this.getHeight());
		
		if(this.level!=null)
		{
			double height = getWidth();
			double width = getWidth();
			double w=width/this.level.getLevelWidth();
			double h=height/this.level.getLevelHeight();
			
			for(int i=0;i<this.level.getLevelHeight();i++)
				for(int j=0;j<this.level.getLevelWidth();j++)
				{
					gc.drawImage(this.level.getObjectsMatrix()[j][i].getImage(), j*w, i*h);
				}
		}
	}
}
