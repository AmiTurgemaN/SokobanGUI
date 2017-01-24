package model.data.level;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public class LevelCreator implements Serializable
{
	protected static final long serialVersionUID = 1L;
	private HashMap<String, GeneralLevelSaverCreator> saveHashMap;
	private HashMap<String, GeneralLevelLoaderCreator> loadHashMap;
	
	public LevelCreator() {
		super();
		initHash();
	}
	
	public LevelCreator(InputStream levelSaverInput,InputStream levelLoaderInput) throws ClassNotFoundException {
		super();
		try {
			initHash(levelLoaderInput,levelSaverInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public HashMap<String, GeneralLevelSaverCreator> getSaveHashMap() {
		return saveHashMap;
	}

	public void setSaveHashMap(HashMap<String, GeneralLevelSaverCreator> saveHashMap) {
		this.saveHashMap = saveHashMap;
	}

	public HashMap<String, GeneralLevelLoaderCreator> getLoadHashMap() {
		return loadHashMap;
	}

	public void setLoadHashMap(HashMap<String, GeneralLevelLoaderCreator> loadHashMap) {
		this.loadHashMap = loadHashMap;
	}

	private class txtLoaderCreator extends GeneralLevelLoaderCreator implements Serializable{
		protected static final long serialVersionUID = 1L;
		public GeneralLevelLoader create() {
			return new MyTextLevelLoader();
		}
	}
	private class xmlLoaderCreator extends GeneralLevelLoaderCreator implements Serializable{
		protected static final long serialVersionUID = 1L;
		public GeneralLevelLoader create() {
			return new MyXMLLevelLoader();
		}
	}
	private class objLoaderCreator extends GeneralLevelLoaderCreator implements Serializable{
		protected static final long serialVersionUID = 1L;
		public GeneralLevelLoader create() {
			return new MyObjectLevelLoader();
		}

	}

	private class txtSaverCreator extends GeneralLevelSaverCreator implements Serializable{
		protected static final long serialVersionUID = 1L;
		public GeneralLevelSaver create(Level level) {
			return new MyTextLevelSaver(level);
		}
	}
	private class xmlSaverCreator extends GeneralLevelSaverCreator implements Serializable{
		protected static final long serialVersionUID = 1L;
		public GeneralLevelSaver create(Level level) {
			return new MyXMLLevelSaver(level);
		}
	}
	private class objSaverCreator extends GeneralLevelSaverCreator implements Serializable{
		protected static final long serialVersionUID = 1L;
		public GeneralLevelSaver create(Level level) {
			return new MyObjectLevelSaver(level);
		}
	}
	
	private void initHash() {
		this.saveHashMap = new HashMap <String,GeneralLevelSaverCreator>();
		this.loadHashMap = new HashMap <String,GeneralLevelLoaderCreator>();
		this.saveHashMap.put("txt",new txtSaverCreator());
		this.saveHashMap.put("obj",new objSaverCreator());
		this.saveHashMap.put("xml",new xmlSaverCreator());
		this.loadHashMap.put("txt",new txtLoaderCreator());
		this.loadHashMap.put("obj",new objLoaderCreator());
		this.loadHashMap.put("xml",new xmlLoaderCreator());
	}
	
	public void serializeHash() throws FileNotFoundException, IOException
	{
			ObjectOutputStream saveOut = new ObjectOutputStream(new FileOutputStream("Hash Maps/saveHashMap.obj"));
			ObjectOutputStream loadOut = new ObjectOutputStream(new FileOutputStream("Hash Maps/loadHashMap.obj"));
			saveOut.writeObject(this.saveHashMap);
			saveOut.close();
			loadOut.writeObject(this.loadHashMap);
			loadOut.close();
            System.out.println("Serialized HashMap data has been is saved");
	}
	
	@SuppressWarnings("unchecked")
	private void initHash(InputStream levelLoaderInput,InputStream levelSaverInput) throws IOException, ClassNotFoundException {
		this.saveHashMap = new HashMap <String,GeneralLevelSaverCreator>();
		this.loadHashMap = new HashMap <String,GeneralLevelLoaderCreator>();
		ObjectInputStream saveIn = new ObjectInputStream(levelSaverInput);
		ObjectInputStream loadIn = new ObjectInputStream(levelLoaderInput);
		this.saveHashMap=(HashMap<String, GeneralLevelSaverCreator>) saveIn.readObject();
		saveIn.close();
		this.loadHashMap=(HashMap<String, GeneralLevelLoaderCreator>) loadIn.readObject();
		loadIn.close();
	}
}
