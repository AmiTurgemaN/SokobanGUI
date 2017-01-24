package model.data.level;

import java.io.InputStream;
/*
 * Returns a Level object that can be displayed.
 * The InputStream argument must be valid.
 * 
 * There is a separation between the loading classes(LevelLoader classes) to the informative class(Level).
 * LevelLoaders classes (MyTextLevelLoader,MyObjectLevelLoader,MyXMLLevelLoader) are classes that implements
 * this interface (LevelLoader) through (GeneralLevelLoader) and must implement the method "loadLevel" that returns level.
 * in this case there is a use in Strategy Pattern.
 * 
 * that separation keep the Open/Close Principle because in the future if another LevelLoader class is needed
 * from another extension file or from any other input stream we can easily implement it.
 * 
 * that separation also keep the Liskov Substitution Principle because at run-time
 * the GeneralLevelLoader(implements LevelLoader interface) must be able to use objects of
 * derived classes without knowing that.
 * 
 * we chose to use InputStream and not String filename because in this way we can get any InputStream from any
 * source (File,Piped,String,etc..), our goal is to make the code as general as we can. 
 */


public interface LevelLoader {
	public Level loadLevel(InputStream stream);
}
