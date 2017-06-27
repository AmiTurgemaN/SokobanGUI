package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import model.data.beans.Level;

public class Client {

	private String ip;
	private int port;
	private Socket socket;
	private Level level;
	private BufferedReader serverInput;
	private ObjectOutputStream outToServer;
	private String userName;

	public Client(String ip, int port) {
		this.ip=ip;
		this.port=port;
	}

	public void connect()
	{
		try {
			this.socket = new Socket(ip, port);
			System.out.println(userName +" has connected to server");
			serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outToServer = new ObjectOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void start(Level level) {
		try {
			level.setUserName(this.userName);
			outToServer.writeObject(level);
			String solution="";
			String line;
			while ((line = serverInput.readLine()) != null)
				solution += line+"\n";

			System.out.println("Solution received from server:\n" + solution);

			serverInput.close();
			outToServer.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public BufferedReader getServerInput() {
		return serverInput;
	}

	public void setServerInput(BufferedReader serverInput) {
		this.serverInput = serverInput;
	}

	public ObjectOutputStream getOutToServer() {
		return outToServer;
	}

	public void setOutToServer(ObjectOutputStream outToServer) {
		this.outToServer = outToServer;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
