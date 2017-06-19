package controller.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Observable;

import controller.SokobanController;
import model.SokobanModel;
import view.CLI;


public class SingleClientHandler  extends Observable implements ClientHandler {


	@Override
	public void handleClinet(InputStream inFromClient, OutputStream outToClient,String exitString) {
		try {
			BufferedReader readFromClient=new BufferedReader(new InputStreamReader(inFromClient));
			PrintWriter writeToClient=new PrintWriter(outToClient);
			CLI view=new CLI(readFromClient,writeToClient,exitString);
			SokobanModel model=new SokobanModel();
			SokobanController controller=new SokobanController(model, view);
			model.addObserver(controller);
			view.addObserver(controller);
			this.addObserver(controller);
			Thread t1=aSyncReadInputAndSent( readFromClient, writeToClient, exitString);
			t1.run();
			readFromClient.close();
			writeToClient.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Thread aSyncReadInputAndSent(BufferedReader in, PrintWriter out,String exitStr){
		Thread t=new Thread(new Runnable() {
			@Override
			public void run() {
				readInputAndSend(in, out, exitStr);
			}
		});
		return t;
	}

	private void readInputAndSend(BufferedReader in, PrintWriter out,String exitStr){
		String commandLine = "";
		do {
			try {
				commandLine = in.readLine();
				setChanged();
				notifyObservers(commandLine);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (!commandLine.equals(exitStr));		
		out.println("bye");
		out.flush();
	}
}
