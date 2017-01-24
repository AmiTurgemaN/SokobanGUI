package model.policy;

import java.io.Serializable;

public abstract class GeneralSokobanPolicy implements SokobanPolicy , Serializable{

	private static final long serialVersionUID = 1L;
	@Override
	public abstract boolean WalkThroughWall();

	@Override
	public abstract boolean pushBlockedBox();

	@Override
	public abstract boolean pullBox();

}
