package controller.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import model.Model;
import view.View;

public class CommandCreator implements Serializable{

	private static final long serialVersionUID = 1L;
	private HashMap<String,GeneralCommand> commandsHashMap;
	private Model model;
	private View view;
	
	public CommandCreator(Model model , View view,FileInputStream fileInputStream) {
		this.model=model;
		this.view=view;
		try {
			initHash(fileInputStream);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public CommandCreator(Model model,View view)
	{
		this.model=model;
		this.view=view;
		initHash();
	}

	public CommandCreator(FileInputStream fileInputStream) {
		try {
			initHash(fileInputStream);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, GeneralCommand> getCommandsHashMap() {
		return commandsHashMap;
	}

	public void setCommandsHashMap(HashMap<String, GeneralCommand> commandsHashMap) {
		this.commandsHashMap = commandsHashMap;
	}
	
	private class displayCommandCreator implements Serializable{
		protected static final long serialVersionUID = 1L;
		public GeneralCommand create(Model model,View view) {
			return new Display(model,view);
		}
	}
	private class exitCommandCreator  implements Serializable{
		private static final long serialVersionUID = 1L;
		public GeneralCommand create(Model model,View view) {
			return new Exit(model,view);
		}
	}
	private class loadCommandCreator  implements Serializable{
		protected static final long serialVersionUID = 1L;
		public GeneralCommand create(Model model,View view) {
			return new LoadFile(model,view);
		}
	}
	
	private class saveCommandCreator  implements Serializable{
		protected static final long serialVersionUID = 1L;
		public GeneralCommand create(Model model,View view) {
			return new SaveFile(model,view);
		}
	}
	
	private class moveCommandCreator implements Serializable{
		protected static final long serialVersionUID = 1L;
		public GeneralCommand create(Model model,View view) {
			return new Move(model);
		}
	}
	private class helpCommandCreator implements Serializable{
		protected static final long serialVersionUID = 1L;
		public GeneralCommand create(Model model,View view) {
			return new Help(model,view);
		}
	}
	private class levelListCommandCreator implements Serializable{
		protected static final long serialVersionUID = 1L;
		public GeneralCommand create(Model model,View view) {
			return new LevelList(model,view);
		}
	}
	private class solveCommandCreator implements Serializable{
		protected static final long serialVersionUID = 1L;
		public GeneralCommand create(Model model,View view) {
			return new Solve(model,view);
		}
	}
	
	public void initHash() {
		this.commandsHashMap = new HashMap <String,GeneralCommand>();
		this.commandsHashMap.put("save",new saveCommandCreator().create(model,view));
		this.commandsHashMap.put("load",new loadCommandCreator().create(model,view));
		this.commandsHashMap.put("display",new displayCommandCreator().create(model, view));
		this.commandsHashMap.put("exit",new exitCommandCreator().create(model,view));
		this.commandsHashMap.put("move",new moveCommandCreator().create(model,view));
		this.commandsHashMap.put("help",new helpCommandCreator().create(model,view));
		this.commandsHashMap.put("?",new helpCommandCreator().create(model,view));
		this.commandsHashMap.put("levels",new levelListCommandCreator().create(model,view));
		this.commandsHashMap.put("solve",new solveCommandCreator().create(model,view));
	}
	
	public void serializeHash() throws FileNotFoundException, IOException
	{
			ObjectOutputStream saveOut = new ObjectOutputStream(new FileOutputStream("Hash Maps/commandHashMap.obj"));
			saveOut.writeObject(this.commandsHashMap);
			saveOut.close();
            System.out.println("Serialized HashMap data has been is saved");
	}
	
	@SuppressWarnings("unchecked")
	private void initHash(InputStream commandHashMapInput) throws IOException, ClassNotFoundException {
		this.commandsHashMap = new HashMap <String,GeneralCommand>();
		ObjectInputStream saveIn = new ObjectInputStream(commandHashMapInput);
		this.commandsHashMap=(HashMap<String, GeneralCommand>) saveIn.readObject();
		saveIn.close();
	}
	
}

