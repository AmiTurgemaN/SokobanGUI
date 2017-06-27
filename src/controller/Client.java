package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import model.data.beans.Level;

public class Client {
	public void start(String ip, int port, Level level) {
		try {			
			Socket socket = new Socket(ip, port);
			System.out.println("connected to server");

			BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
			
			outToServer.writeObject(level);
			String solution="";
			String line;
		    while ((line = serverInput.readLine()) != null) {
		    	solution += line+"\n";
		    }
			
			System.out.println("Solution received from server:\n" + solution);

			serverInput.close();
			outToServer.close();
			socket.close();
		} 
		catch (UnknownHostException e) {/*...*/}
		catch (IOException e) {
		
		}
	}
}
