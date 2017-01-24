package model.receiver;

import java.io.OutputStream;

import common.GeneralReceiver;
import model.data.level.GeneralLevelSaver;

public class SaveLevelReceiver extends GeneralReceiver {

	private GeneralLevelSaver levelSaver;
	private OutputStream os;
	
	public SaveLevelReceiver(GeneralLevelSaver levelSaver, OutputStream os) {
		this.levelSaver = levelSaver;
		this.os = os;
	}

	public GeneralLevelSaver getLevelSaver() {
		return levelSaver;
	}

	public void setLevelSaver(GeneralLevelSaver levelSaver) {
		this.levelSaver = levelSaver;
	}

	public OutputStream getOs() {
		return os;
	}

	public void setOs(OutputStream os) {
		this.os = os;
	}

	@Override
	public void action() {
		this.levelSaver.saveLevel(os);
	}

}
