package model.data.level;

import java.io.Serializable;

import model.data.gameObjects.Area;
import model.data.gameObjects.Box;
import model.data.gameObjects.GeneralGameObject;
import model.data.gameObjects.Player;
import model.data.gameObjects.Space;
import model.data.gameObjects.Wall;
import model.data.gameObjects.objectType;
import model.data.point.GeneralIntegerPoint;
import model.data.point.Point2D;
import model.policy.GeneralSokobanPolicy;

public class Level implements Serializable{

	private static final long serialVersionUID = 1L;
	private GeneralGameObject objectsMatrix[][];
	private int levelWidth;
	private int levelHeight;
	private boolean completed;
	private String levelString;
	private String levelName;
	private String userName;
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	private GeneralSokobanPolicy policy;
	public Level(){
	}

	public GeneralIntegerPoint getPlayerLocation()
	{
		for(int i=0;i<levelHeight;i++)
			for(int j=0;j<levelWidth;j++)
				if(objectsMatrix[j][i].getSymbol()=='A' || objectsMatrix[j][i].getSymbol()=='B')
					return objectsMatrix[j][i].getPoint();
		return null;
	}

	public void setLevelWidth(int levelWidth) {
		this.levelWidth = levelWidth;
	}

	public void setLevelHeight(int levelHeight) {
		this.levelHeight = levelHeight;
	}

	public Level(GeneralSokobanPolicy policy)
	{
		this.policy=policy;
		this.levelWidth=0;
		this.levelHeight=0;
		this.completed=false;
		this.levelString="";
		this.levelName="";
	}

	private void initLevel()
	{
		this.levelWidth=0;
		this.levelHeight=0;
		this.completed=false;
		this.levelName="";
		this.policy=null;
	}

	public GeneralGameObject[][] getObjectsMatrix() {
		return objectsMatrix;
	}

	public void setObjectsMatrix(GeneralGameObject[][] objectsMatrix) {
		this.objectsMatrix = objectsMatrix;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public Level(String level)	
	{
		this.levelString=level;
		initLevel();
		init2DLevel();
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName)
	{
		this.levelName = levelName;
	}

	public String getLevelString() {
		return levelString;
	}

	public void setLevelString(String levelString) {
		this.levelString = levelString;
	}

	public GeneralSokobanPolicy getPolicy() {
		return policy;
	}

	public void setPolicy(GeneralSokobanPolicy policy) {
		this.policy = policy;
	}

	public int getLevelWidth() 
	{
		return this.levelWidth;
	}

	public int getLevelHeight() 
	{
		return this.levelHeight;
	}

	public void updateLevel(GeneralGameObject source, GeneralGameObject dest,
			GeneralGameObject newDest) {
		int xSource = source.getPoint().getX();
		int ySource = source.getPoint().getY();
		int xDest = dest.getPoint().getX();
		int yDest = dest.getPoint().getY();
		int xNewDest = newDest.getPoint().getX();
		int yNewDest = newDest.getPoint().getY();
		String newLevelString="";
		if(!source.isOnArea())
			this.objectsMatrix[xSource][ySource]=new Space(new Point2D(xSource,ySource));
		else
			this.objectsMatrix[xSource][ySource]=new Area(new Point2D(xSource,ySource));
		this.objectsMatrix[xDest][yDest]=new Player(new Point2D(xDest,yDest));
		if(dest.getType()==objectType.BOX)
		{
			if(dest.isOnArea())
				this.objectsMatrix[xDest][yDest].setOnArea(true);
			this.objectsMatrix[xNewDest][yNewDest]=new Box(new Point2D(xNewDest,yNewDest));
			if(newDest.getType()==objectType.AREA)
				this.objectsMatrix[xNewDest][yNewDest].setOnArea(true);
		}
		else if(dest.getType()==objectType.AREA)
			this.objectsMatrix[xDest][yDest].setOnArea(true);
		for(int i=0;i<levelHeight;i++)
		{
			for(int j=0;j<levelWidth;j++)
			{
				newLevelString+=objectsMatrix[j][i].getSymbol();
			}
			newLevelString+="\n";
		}
		this.levelString = newLevelString;
		checkLevelCompleted();
	}

	public boolean checkLevelCompleted() {
		for(int i=0;i<levelHeight;i++)
			for(int j=0;j<levelWidth;j++)
				if(objectsMatrix[j][i].getType()==objectType.BOX && !objectsMatrix[j][i].isOnArea())
					return false;
		this.completed=true;
		return true;
	}

	public void init2DLevel()
	{
		updateLevelDimensions();

		String [] levelRows = levelString.split("\r\n");
		for(int i=0;i<levelRows.length;i++)
			for(int j=0;j<levelRows[i].length();j++)
				switch(levelRows[i].charAt(j))
				{
				case '#':
					this.objectsMatrix[j][i]=new Wall(new Point2D(j,i));
					break;
				case 'o':
					this.objectsMatrix[j][i]=new Area(new Point2D(j,i));
					break;
				case '$':
					this.objectsMatrix[j][i]=new Box(new Point2D(j,i));
					this.objectsMatrix[j][i].setOnArea(true);
					break;
				case 'B':
					this.objectsMatrix[j][i]=new Player(new Point2D(j,i));
					this.objectsMatrix[j][i].setOnArea(true);
					break;
				case '@':
					this.objectsMatrix[j][i]=new Box(new Point2D(j,i));
					break;
				case 'A':
					this.objectsMatrix[j][i]=new Player(new Point2D(j,i));
					break;
				case ' ' :
					this.objectsMatrix[j][i]=new Space(new Point2D(j,i));
					break;
				case '\r':
				case '\n':
					break;
				default :
					initLevel();
					return;
				}

		for(int i=0;i<levelHeight;i++)
			for(int j=0;j<levelWidth;j++)
				if(i>=levelRows.length || j>=levelRows[i].length())
					this.objectsMatrix[j][i]=new Space(new Point2D(j,i));
	}

	public boolean checkLevelValidation() {
		int boxes=0,areas=0,player=0;
		for(int i=0;i<levelHeight;i++)
			for(int j=0;j<levelWidth;j++)
				switch (objectsMatrix[j][i].getSymbol()) {
				case '$':
					boxes++;
					areas++;
					break;
				case '@':
					boxes++;
					break;
				case 'B':
					areas++;
					player++;
					break;
				case 'o':
					areas++;
					break;
				case 'A':
					player++;
				default:
					break;
				}
		if(boxes!=areas || player!=1 || boxes==0 || areas==0)
			return false;
		return true;
	}

	private void updateLevelDimensions() {
		int tempCols=0;
		int rows=1;
		int cols=1;
		for(int i=0;i<this.levelString.length();i++)
		{
			if(this.levelString.charAt(i)=='\n')
			{
				rows++;
				if(--tempCols>cols)
				{
					cols=tempCols;
				}
				tempCols=0;
			}
			else
				tempCols++;
		}
		if(tempCols>cols)
		{
			cols=tempCols;
		}
		this.levelWidth=cols;
		this.levelHeight=rows;
		this.objectsMatrix=new GeneralGameObject[levelWidth][levelHeight];
	}

	public GeneralGameObject getObjectByPoint(GeneralIntegerPoint point) {
		return objectsMatrix[point.getX()][point.getY()];
	}

}

