package controller.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import model.data.level.GeneralLevelSaver;
import model.data.level.GeneralLevelSaverCreator;
import model.data.level.LevelCreator;
import model.data.util.Utilities;
import model.Model;
import view.View;

public class SaveFile extends GeneralCommand {

	private static final long serialVersionUID = 1L;
	private GeneralLevelSaver generalLevelSaver;
	private OutputStream outputStream;
	private LevelCreator lc;

	public LevelCreator getLc() {
		return lc;
	}

	public void setLc(LevelCreator lc) {
		this.lc = lc;
	}

	public SaveFile(LevelCreator lc)
	{
		this.lc=lc;
	}

	public GeneralLevelSaver getGeneralLevelSaver() {
		return generalLevelSaver;
	}

	public void setGeneralLevelSaver(GeneralLevelSaver generalLevelSaver) {
		this.generalLevelSaver = generalLevelSaver;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	
	public SaveFile(GeneralLevelSaver generalLevelSaver,OutputStream outputStream,Model model)
	{
		this.generalLevelSaver=generalLevelSaver;
		this.outputStream=outputStream;
		this.model=model;
	}

	public SaveFile(Model model,View view)
	{
		this.model=model;
		this.view=view;
	}

	@Override
	public void execute() {
		try {
			String extension = Utilities.getExtension(this.commandArgs);
			this.lc = new LevelCreator(new FileInputStream("Hash Maps/saveHashMap.obj"),new FileInputStream("Hash Maps/loadHashMap.obj"));
			GeneralLevelSaverCreator glsc = this.lc.getSaveHashMap().get(extension);
			if(!this.commandArgs.contains("."))
			{
				view.displayError("File extension is needed.");
				return;
			}
			else if(glsc==null)
			{
				view.displayError(extension+" extension is not supported.");
				return;
			}
			this.outputStream=new FileOutputStream(new File("Level Files/"+this.commandArgs));
			this.generalLevelSaver = glsc.create(model.getLevel());
			this.model.getLevel().setLevelName(this.commandArgs.replaceAll("."+extension, ""));
			this.model.saveLevel(generalLevelSaver,outputStream);
			view.displayMessage("Level "+model.getLevel().getLevelName()+" has been saved as "+extension+" file");
		} catch (FileNotFoundException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
