package de.hundt.calcPi.concurrent;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicRunnable implements Runnable, PiRunner
{
	protected AtomicBoolean running;
	private AtomicBoolean stopped;

	public AtomicRunnable(int interval)
	{
		this.running = new AtomicBoolean(false);
		this.stopped = new AtomicBoolean(true);


		this.interval = interval;
	}

	protected int interval;
	private Thread worker;

	public void start()
	{
		this.worker = new Thread(this);
		this.worker.start();
	}


	public void stop()
	{
		this.running.set(false);
	}


	public void interrupt()
	{
		this.running.set(false);
		this.worker.interrupt();
	}


	public boolean isRunning()
	{
		return this.running.get();
	}


	public boolean isStopped()
	{
		return this.stopped.get();
	}


	public void run()
	{
		this.running.set(true);
		this.stopped.set(false);
		calculate();
	}

	public void calculate()
	{
	}
}
