package model.receiver;

import common.GeneralReceiver;
import controller.commands.Direction;
import model.Model;
import model.SokobanModel;
import model.data.gameObjects.GeneralGameObject;
import model.data.gameObjects.objectType;
import model.data.point.GeneralIntegerPoint;

public class MoveReceiver extends GeneralReceiver {

	private Direction direction;
	private GeneralIntegerPoint playerPoint;
	private SokobanModel model;
	private boolean moveError;

	public boolean isMoveError() {
		return moveError;
	}

	public void setError(boolean moveError) {
		this.moveError = moveError;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(SokobanModel model) {
		this.model = model;
	}

	public GeneralIntegerPoint getPlayerPoint() {
		return playerPoint;
	}

	public void setPlayerPoint(GeneralIntegerPoint playerPoint) {
		this.playerPoint = playerPoint;
	}

	public MoveReceiver(Direction direction,GeneralIntegerPoint playerPoint)
	{
		this.direction=direction;
		this.playerPoint=playerPoint;
	}

	public MoveReceiver(Direction direction, SokobanModel model) {
		this.direction=direction;
		this.model = model;
		this.playerPoint=model.getLevel().getPlayerLocation();
		this.moveError=false;
	}

	public GeneralIntegerPoint getDestinationPoint()
	{
		return playerPoint.getNextPointByDirection(this.direction);
	}

	public GeneralIntegerPoint getBoxNewDestinationPoint()
	{
		return playerPoint.getNextPointByDirection(this.direction).getNextPointByDirection(this.direction);
	}

	public void action()
	{
		GeneralIntegerPoint destPoint = getDestinationPoint();
		GeneralIntegerPoint newDestPoint = getBoxNewDestinationPoint();
		GeneralGameObject sourceObject = model.getLevel().getObjectByPoint(playerPoint);
		sourceObject.setPoint(playerPoint);
		GeneralGameObject destObject = model.getLevel().getObjectByPoint(destPoint);
		destObject.setPoint(getDestinationPoint());
		GeneralGameObject boxNewDestObject;
		if(destObject.getType()==objectType.WALL)
			boxNewDestObject = null;
		else
			boxNewDestObject = model.getLevel().getObjectByPoint(newDestPoint);
		if(boxNewDestObject!=null)
			boxNewDestObject.setPoint(getBoxNewDestinationPoint());
		if(checkMove(sourceObject,destObject,boxNewDestObject) == true)
			model.getLevel().updateLevel(sourceObject,destObject,boxNewDestObject);
		else
			this.moveError=true;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public boolean checkMove(GeneralGameObject source,GeneralGameObject dest,GeneralGameObject newDest)
	{
		switch(dest.getType())
		{
		case BOX:
			switch(newDest.getType())
			{
			case BOX:
			case PLAYER:
			case WALL:
				return model.getLevel().getPolicy().pushBlockedBox();
			default:
				return true;
			}
		case PLAYER:
			return false;
		case WALL:
			return model.getLevel().getPolicy().WalkThroughWall();
		default :
			return true;
		}
	}
}
