package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import controller.commands.CommandCreator;
import controller.commands.GeneralCommand;
import controller.commands.Help;
import controller.commands.LevelList;
import model.Model;
import view.View;

public class SokobanController implements Observer {
	private Model model;
	private View view;
	private Controller controller;
	private Map<String, GeneralCommand> commands;
	
	public SokobanController(Model model, View view) {
		this.model = model;
		this.view = view;
		
		controller = new Controller();
		controller.start();
		
		initCommands();
	}

	private void initCommands() {
		commands = new HashMap<String,GeneralCommand>();
		try {
			CommandCreator cc = new CommandCreator(new FileInputStream("Hash Maps/commandHashMap.obj"));
			commands = cc.getCommandsHashMap();
			for(Map.Entry<String, GeneralCommand> entry : commands.entrySet())
			{
				entry.getValue().setModel(model);
				entry.getValue().setView(view);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object params) {	
		String [] commandArgs = params.toString().split(" ");
		String commandName = commandArgs[0].toLowerCase();
		String arg = null;
		if (commandArgs.length == 2)
			arg = commandArgs[1].toLowerCase();
		else if(commandArgs.length>2)
		{
			view.displayError("Invalid command");
			return;
		}
		GeneralCommand command = commands.get(commandName);
		if (command == null) {
			view.displayError("Command not found");
			return;
		}
		command.setCommandArgs(arg);
		controller.insertCommand(command);	
		if(params.equals(this.view.getExitString()))
			this.controller.stop();
		
	}
	
	
}
