package model.data.level;

import java.io.InputStream;

public abstract class GeneralLevelLoader implements LevelLoader {

	@Override
	public abstract Level loadLevel(InputStream stream);

}
