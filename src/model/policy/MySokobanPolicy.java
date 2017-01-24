package model.policy;

import java.io.Serializable;

public class MySokobanPolicy extends GeneralSokobanPolicy implements Serializable{

	private static final long serialVersionUID = 1L;
	@Override
	public boolean WalkThroughWall() {
		return false;
	}

	@Override
	public boolean pushBlockedBox() {
		return false;
	}

	@Override
	public boolean pullBox() {
		return false;
	}

	public MySokobanPolicy() {
		super();
	}
	
}
