package controller.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server extends Observable {
	private int port;
	private boolean interrupted;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setInterrupted(boolean interrupted) {
		this.interrupted = interrupted;
	}

	public Server(int port) {
		this.port=port;
		interrupted=false;
	}

	public void start()
	{
		new Thread(new Runnable() {

			@Override
			public void run() {
				try{
					runServer();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@SuppressWarnings("resource")
	protected void runServer() throws Exception{
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		ServerSocket server = new ServerSocket(port);
		try
		{
			System.out.println("Waiting for user to connect");
			while(!isInterrupted())
			{
				Socket aClient=server.accept();
				cachedThreadPool.execute(new Runnable() {

					@Override
					public void run() {
						try {
							System.out.println("User Connected");
							ClientHandler ch = new SingleClientHandler();
							ch.handleClinet(aClient.getInputStream(), aClient.getOutputStream(),"exit");
							System.out.println("User Disconnected");
							aClient.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			}
		}
		catch(SocketTimeoutException e)
		{
			e.printStackTrace();
		}

	}

	private boolean isInterrupted() {
		return interrupted;
	}
	public void interrupt()
	{
		System.out.println("Server has been interrupted. Users can't connect.");
		this.interrupted=true;
	}
}
