package model.policy;

public interface SokobanPolicy {

	public boolean WalkThroughWall();
	public boolean pushBlockedBox();
	public boolean pullBox();
	
}
