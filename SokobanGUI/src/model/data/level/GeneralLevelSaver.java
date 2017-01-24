package model.data.level;

import java.io.OutputStream;

public abstract class GeneralLevelSaver implements LevelSaver {

	protected Level level;
	
	@Override
	public void saveLevel(OutputStream outputStream) {
	}

}
