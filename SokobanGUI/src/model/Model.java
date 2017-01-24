package model;

import java.io.InputStream;
import java.io.OutputStream;

import controller.commands.Direction;
import model.data.level.GeneralLevelLoader;
import model.data.level.GeneralLevelSaver;
import model.data.level.Level;

public interface Model {
	Level getLevel();
	void setLevel(Level level);
	boolean move(Direction direction);
	void saveLevel(GeneralLevelSaver generalLevelSaver, OutputStream outputStream);
	void loadLevel(GeneralLevelLoader generalLevelLoader, InputStream inputStream);
}
