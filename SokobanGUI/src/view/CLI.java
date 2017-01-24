package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Observable;

import model.data.level.Level;
import view.receiver.DisplayReceiver;
import view.receiver.ExitReciever;

public class CLI extends Observable implements View,Serializable{
	
	private static final long serialVersionUID = 1L;
	private BufferedReader in;
	private PrintWriter out;
	private String exitString;
	
	public CLI()
	{
		
	}
	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

	public PrintWriter getOut() {
		return out;
	}
	
	public void setOut(PrintWriter out) {
		this.out = out;
	}

	public String getExitString() {
		return exitString;
	}

	public void setExitString(String exitString) {
		this.exitString = exitString;
	}

	public CLI(BufferedReader in, PrintWriter out,String exitString)
	{
		this.in = in;
		this.out = out;
		this.exitString = exitString;
	}

	@Override
	public void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				String commandLine = "";
				do {
					try {
						commandLine = in.readLine();
						setChanged();
						notifyObservers(commandLine);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} while (!commandLine.equals(exitString));				
			}
		}).start();	
	}

	@Override
	public void displayError(String msg) {
		out.println("Error: " + msg);
		out.flush();
	}

	@Override
	public void displayLevel(Level level) {
		DisplayReceiver dr = new DisplayReceiver(level);
		dr.action();
		out.println(dr.getLevelString());
		out.flush();
	}

	@Override
	public void displayMessage(String msg) {
		out.println(msg);
		out.flush();
	}
	
	@Override
	public void exit(String exitString) {
		ExitReciever er = new ExitReciever(exitString, out);
		er.action();
	}
}
