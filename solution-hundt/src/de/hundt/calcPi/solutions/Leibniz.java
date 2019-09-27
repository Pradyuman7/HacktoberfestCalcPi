package de.hundt.calcPi.solutions;

import de.hundt.calcPi.concurrent.AtomicRunnable;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Leibniz
		extends AtomicRunnable
{
	private String piString = "";
	private BigDecimal pi = new BigDecimal("0.0");

	private StringProperty observableStringValue = new SimpleStringProperty(this.piString);


	public Leibniz(int interval)
	{
		super(interval);
	}


	public void calculate()
	{
		BigDecimal piFourth = new BigDecimal("0.0");
		BigDecimal minusOne = new BigDecimal("-1.0");
		BigDecimal four = new BigDecimal("4.0");

		for (int i = 0; this.running.get(); i++)
		{

			try
			{
				Thread.sleep(this.interval);
			}
			catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
				System.out.println("Thread was interrupted, Failed to complete operation");
			}

			piFourth = piFourth.add(minusOne.pow(i).divide(new BigDecimal(2 * i + 1), RoundingMode.HALF_UP));
			this.pi = piFourth.multiply(four);
			this.observableStringValue.set(this.pi.toPlainString());
		}
	}


	public BigDecimal getPi()
	{
		return this.pi;
	}


	public StringProperty getStringRepresentation()
	{
		return this.observableStringValue;
	}
}


