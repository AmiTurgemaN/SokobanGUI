package model.data.level;

public abstract class GeneralLevelSaverCreator implements LevelSaverCreator {

	@Override
	abstract public GeneralLevelSaver create(Level level);

}
