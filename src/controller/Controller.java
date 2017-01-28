package controller;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import controller.commands.Command;

public class Controller {
	private BlockingQueue<Command> queue;
	private boolean stop = false;
	
	public Controller() {
		queue = new ArrayBlockingQueue<Command>(10);		
	}
	
	public void insertCommand(Command cmd) {
		try {
			queue.put(cmd);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
	
	public void start() {
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (!stop) {
					try {
						Command cmd = queue.poll(1, TimeUnit.SECONDS);
						if (cmd != null)
							cmd.execute();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		thread.start();
	}
	
	public void stop() {
		stop = true;
	}
	
}
