package view.receiver;

import common.GeneralReceiver;
import model.data.level.Level;

public class DisplayReceiver extends GeneralReceiver {

	private Level level;
	private String levelString;
	
	public String getLevelString() {
		return levelString;
	}

	public void setLevelString(String levelString) {
		this.levelString = levelString;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public DisplayReceiver(Level level) {
		super();
		this.level = level;
	}

	@Override
	public void action() {
		this.levelString=level.getLevelString();
	}

}
