package view;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import javafx.scene.input.KeyCode;

public class Keys implements Serializable{
	private static final long serialVersionUID = 1L;
	private KeyCode up;
	private KeyCode down;
	private KeyCode right;
	private KeyCode left;
	private HashMap<String,KeyCode> keysMap;

	public Keys()
	{

	}
	
	@SuppressWarnings("unchecked")
	public Keys(InputStream is) throws IOException, ClassNotFoundException
	{
		ObjectInputStream keyInputStream = new ObjectInputStream(is);
		this.keysMap = (HashMap<String,KeyCode>) keyInputStream.readObject();
		keyInputStream.close();
	}
	
	public HashMap<String, KeyCode> getKeysMap() {
		return keysMap;
	}

	public void setKeysMap(HashMap<String, KeyCode> keysMap) {
		this.keysMap = keysMap;
	}
	public KeyCode getUp() {
		return up;
	}
	public void setUp(KeyCode up) {
		this.up = up;
	}
	public KeyCode getDown() {
		return down;
	}
	public void setDown(KeyCode down) {
		this.down = down;
	}
	public KeyCode getRight() {
		return right;
	}
	public void setRight(KeyCode right) {
		this.right = right;
	}
	public KeyCode getLeft() {
		return left;
	}
	public void setLeft(KeyCode left) {
		this.left = left;
	}

	public void SerializeHash()
	{
		this.keysMap = new HashMap<String,KeyCode>();
		this.keysMap.put("up", KeyCode.UP);
		this.keysMap.put("down", KeyCode.DOWN);
		this.keysMap.put("right", KeyCode.RIGHT);
		this.keysMap.put("left", KeyCode.LEFT);
		ObjectOutputStream keysOutputStream;
		try {
			keysOutputStream = new ObjectOutputStream(new FileOutputStream("Hash Maps/keysHashMap.obj"));
			keysOutputStream.writeObject(this.keysMap);
			keysOutputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Serialized HashMap data has been is saved");
	}


}
