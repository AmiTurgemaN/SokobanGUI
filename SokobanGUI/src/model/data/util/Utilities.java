package model.data.util;

public final class Utilities {

	static public String getExtension(String path)
	{
		return path.substring(path.lastIndexOf('.')+1, path.length());
	}
}
