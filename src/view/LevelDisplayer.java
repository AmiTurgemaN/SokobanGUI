package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.data.level.Level;

public class LevelDisplayer extends Canvas{
	private Level level;
	
	public LevelDisplayer(Level level)
	{
		setLevel(level);
	}
	
	public LevelDisplayer()
	{
		setLevel(null);
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
			int max = this.level.getLevelHeight() > this.level.getLevelWidth() ? this.level.getLevelHeight() : this.level.getLevelWidth();
			double height = getHeight();
			double width = getWidth();
			double w=width/max;
			double h=height/max;
			double diff = this.level.getLevelHeight() - this.level.getLevelWidth();
			if(diff>0)
				diff/=2.0;
			else
				diff=0;
			System.out.println(diff);
			for(int i=0;i<this.level.getLevelHeight();i++)
				for(int j=0;j<this.level.getLevelWidth();j++)
				{
					gc.drawImage(this.level.getObjectsMatrix()[j][i].getImage(), j*w+diff*w, i*h,0.98*w,0.98*h);
					gc.setFill(Color.BLACK);
					gc.fillRect(j*w+0.98*w+diff*w, i*h, 0.02*w, 0.98*h);
					gc.fillRect(j*w+diff*w, i*h+0.98*h, 0.98*w, 0.02*h);
				}
		}
	}
}
