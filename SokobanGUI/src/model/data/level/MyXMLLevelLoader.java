package model.data.level;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class MyXMLLevelLoader extends GeneralLevelLoader {

	@Override
	public Level loadLevel(InputStream inputStream) {
		Level level=null;
		XMLDecoder decoder=null;
		try {
			decoder=new XMLDecoder(new BufferedInputStream(inputStream));
			level=(Level)decoder.readObject();
			decoder.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return level;
	}

}

