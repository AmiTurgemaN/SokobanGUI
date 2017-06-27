package model.data.beans;

import java.io.Serializable;

public class Level implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Level() {
	}
	
	public Level(String levelName, String userName, String levelString) {
		this.levelName = levelName;
		this.userName = userName;
		this.levelString = levelString;
	}
	
	private String levelName;
	private String userName;
	private String levelString;
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLevelString() {
		return levelString;
	}
	public void setLevelString(String levelString) {
		this.levelString = levelString;
	}
	
	
}
