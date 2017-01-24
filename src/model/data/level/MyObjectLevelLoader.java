package model.data.level;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class MyObjectLevelLoader extends GeneralLevelLoader {

	@Override
	public Level loadLevel(InputStream inputStream) {
		Level level=null;
		try {
	         ObjectInputStream in = new ObjectInputStream(inputStream);
	         level = (Level) in.readObject();
	         in.close();
	         return level;
	      }catch(IOException i) {
	         i.printStackTrace();
	         return null;
	      }catch(ClassNotFoundException c) {
	         c.printStackTrace();
	         return null;
	      }
	}


}
