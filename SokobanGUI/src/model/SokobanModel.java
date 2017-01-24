package model;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Observable;

import controller.commands.Direction;
import model.data.level.GeneralLevelLoader;
import model.data.level.GeneralLevelSaver;
import model.data.level.Level;
import model.receiver.LoadLevelReceiver;
import model.receiver.MoveReceiver;
import model.receiver.SaveLevelReceiver;

public class SokobanModel extends Observable implements Model,Serializable {

	private static final long serialVersionUID = 1L;
	private Level level;
	
	public SokobanModel(Level level) {
		this.level = level;
	}
	
	public SokobanModel() {
	}

	@Override
	public Level getLevel() {
		return this.level;
	}
	@Override
	public boolean move(Direction direction) {
		MoveReceiver mr = new MoveReceiver(direction, this);
		mr.action();
		if(mr.isMoveError())
			return false;
		return true;
	}

	@Override
	public void setLevel(Level level) {
		this.level=level;
	}

	@Override
	public void saveLevel(GeneralLevelSaver generalLevelSaver, OutputStream outputStream) {
		SaveLevelReceiver slr = new SaveLevelReceiver(generalLevelSaver,outputStream);
		slr.action();
	}

	@Override
	public void loadLevel(GeneralLevelLoader generalLevelLoader, InputStream inputStream) {
		LoadLevelReceiver llr = new LoadLevelReceiver(generalLevelLoader, inputStream);
		llr.action();
		setLevel(llr.getLevel());
	}

}
