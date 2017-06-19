package controller.commands;

import java.io.File;
import java.util.ArrayList;

import model.Model;
import view.View;

public class LevelList extends GeneralCommand {
	private static final long serialVersionUID = 1L;
	private ArrayList<String> listLevel;

	public ArrayList<String> getListLevel() {
		return listLevel;
	}

	public void setListLevel(ArrayList<String> listLevel) {
		this.listLevel = listLevel;
	}

	public LevelList(Model model) {
		this.model=model;
	}

	public LevelList(Model model, View view) {
		this.model=model;
		this.view=view;
	}

	public void initLevelList()
	{
		this.listLevel = new ArrayList<>();
		File[] listOfFiles = new File("Level Files").listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				listLevel.add(listOfFiles[i].getName());
			}
		}
	}

	@Override
	public void execute() {
		if(this.commandArgs!=null)
		{
			view.displayError("Help command does not require any arguments.");
			return;
		}
		if(listLevel==null)
			initLevelList();
		view.displayMessage("The Available levels are:");
		for(String s : listLevel)
			view.displayMessage(s);
	}
}
