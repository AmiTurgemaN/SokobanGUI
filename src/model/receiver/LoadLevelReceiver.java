package model.receiver;

import java.io.InputStream;

import common.GeneralReceiver;
import model.data.level.GeneralLevelLoader;
import model.data.level.Level;

public class LoadLevelReceiver extends GeneralReceiver {

	private GeneralLevelLoader levelLoader;
	private InputStream is;
	private Level level;
	
	public Level getLevel() {
		return level;
	}


	public void setLevel(Level level) {
		this.level = level;
	}

	public LoadLevelReceiver(GeneralLevelLoader levelLoader, InputStream is) {
		super();
		this.levelLoader = levelLoader;
		this.is = is;
	}


	public GeneralLevelLoader getLevelLoader() {
		return levelLoader;
	}


	public void setLevelLoader(GeneralLevelLoader levelLoader) {
		this.levelLoader = levelLoader;
	}


	public InputStream getIs() {
		return is;
	}


	public void setIs(InputStream is) {
		this.is = is;
	}


	@Override
	public void action() {
		this.level=this.getLevelLoader().loadLevel(is);
	}

}
