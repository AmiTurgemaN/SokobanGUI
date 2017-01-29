package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.data.level.Level;

public class LevelDisplayer extends Canvas{
	private Level level;
	private String player;

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

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
			String [] levelRows = this.level.getLevelString().split("\n");
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
			for(int i=0;i<this.level.getLevelHeight();i++)
				for(int j=0;j<this.level.getLevelWidth();j++)
				{
					if(j<=levelRows[i].lastIndexOf('#') && j>=levelRows[i].indexOf('#'))
					{
						if(!isPlayer(i,j) || (isPlayer(i, j) && (this.player==null || this.player.equals("Ogre Magi"))))
							gc.drawImage(this.level.getObjectsMatrix()[j][i].getImage(), j*w+diff*w, i*h,w,h);
						else
						{
							drawWithAnotherPlayer( gc,i, j,h,w,diff);
						}
					}
				}
		}
	}

	private boolean isPlayer(int i, int j) {
		if(this.level.getObjectsMatrix()[j][i].getSymbol()=='A' || this.level.getObjectsMatrix()[j][i].getSymbol()=='B')
			return true;
		return false;
	}

	private void drawWithAnotherPlayer(GraphicsContext gc,int i, int j,double h,double w,double diff) {
		Image image = null;
		int xPoint = this.level.getObjectsMatrix()[j][i].getPoint().getX();
		int yPoint = this.level.getObjectsMatrix()[j][i].getPoint().getY();
		boolean isOnArea = this.level.getObjectsMatrix()[j][i].isOnArea();
		try{
			if(this.player.equals("Rubick"))
			{
				if((xPoint%2==0 && yPoint%2==0) || (xPoint%2==1 && yPoint%2==1))
				{
					if(!isOnArea)
						image = new Image(new FileInputStream("resources/Rubick.png"));
					else
						image = new Image(new FileInputStream("resources/Rubick area.png"));
				}
				else
				{
					if(!isOnArea)
						image = new Image(new FileInputStream("resources/Rubick black.png"));
					else
						image = new Image(new FileInputStream("resources/Rubick black area.png"));
				}
			}
			else if(this.player.equals("Earthshaker"))
			{
				if((xPoint%2==0 && yPoint%2==0) || (xPoint%2==1 && yPoint%2==1))
				{
					if(!isOnArea)
						image = new Image(new FileInputStream("resources/Earthshaker.png"));
					else
						image = new Image(new FileInputStream("resources/es white area.png"));
				}
				else
				{
					if(!isOnArea)
						image = new Image(new FileInputStream("resources/es black.png"));
					else
						image = new Image(new FileInputStream("resources/es black area.png"));
				}
			}
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		gc.drawImage(image, j*w+diff*w, i*h,w,h);
	}

}
