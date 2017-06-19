package controller.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import model.data.level.GeneralLevelLoader;
import model.data.level.GeneralLevelLoaderCreator;
import model.data.level.LevelCreator;
import model.data.util.Utilities;
import model.policy.MySokobanPolicy;
import model.Model;
import view.View;

public class LoadFile extends GeneralCommand {

	private static final long serialVersionUID = 1L;
	private GeneralLevelLoader generalLevelLoader;
	private InputStream inputStream;
	private LevelCreator lc;
	private String fileName;

	public LoadFile(GeneralLevelLoader generalLevelLoader,InputStream inputStream,Model model)
	{
		this.generalLevelLoader=generalLevelLoader;
		this.inputStream=inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public LoadFile(LevelCreator lc)
	{
		this.lc=lc;
	}

	public LoadFile(Model model,View view) {
		this.view=view;
		this.model=model;
	}

	public LevelCreator getLc() {
		return lc;
	}

	public void setLc(LevelCreator lc) {
		this.lc = lc;
	}

	public GeneralLevelLoader getGeneralLevelLoader() {
		return generalLevelLoader;
	}

	public void setGeneralLevelLoader(GeneralLevelLoader generalLevelLoader) {
		this.generalLevelLoader = generalLevelLoader;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public void execute() {
		try {
			this.fileName=this.commandArgs;
			if(this.commandArgs==null)
			{
				view.displayError("Invalid level file name.");
				return;
			}
			this.lc = new LevelCreator(new FileInputStream("Hash Maps/saveHashMap.obj"),new FileInputStream("Hash Maps/loadHashMap.obj"));
			GeneralLevelLoaderCreator gllc = this.lc.getLoadHashMap().get(Utilities.getExtension(fileName));
			if(!this.commandArgs.contains("."))
			{
				view.displayError("File extension is needed.");
				return;
			}
			else if(gllc==null)
			{
				view.displayError(Utilities.getExtension(this.commandArgs)+" extension is not supported.");
				return;
			}
			if(levelExist())
			{
				this.inputStream = new FileInputStream ("Level Files/"+this.commandArgs);
				this.generalLevelLoader = gllc.create();
			}
			else
			{
				view.displayError("Level doesn't exist");
				return;
			}
		} catch (FileNotFoundException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		this.model.loadLevel(generalLevelLoader, inputStream);
		setLevel();
	}

	private boolean levelExist() {
		LevelList ll = new LevelList(model);
		ll.initLevelList();
		for(String levelName : ll.getListLevel())
			if(levelName.toLowerCase().equals(this.commandArgs.toLowerCase()))
				return true;
		return false;
	}

	public void setLevel() {
		this.model.getLevel().setPolicy(new MySokobanPolicy());
		this.model.setLevel(this.model.getLevel());
		if(this.model.getLevel().getLevelName()!=null)
			this.model.getLevel().setLevelName(fileName.replaceAll("."+Utilities.getExtension(fileName), ""));
		if(!this.model.getLevel().checkLevelValidation())
		{
			view.displayError("Level is not valid");
			model.setLevel(null);
			return;
		}
		this.view.showLevelDetails(this.model.getLevel());
		if(this.model.getLevel().getLevelName()!="")
			view.displayMessage("Level "+model.getLevel().getLevelName()+" has been loaded from "+Utilities.getExtension(fileName)+" file");
		if(this.model.getLevel().checkLevelCompleted())
		{
			view.displayLevel(this.model.getLevel());
			view.levelCompleted();
		}
	}
}
