package de.hundt.calcPi.ui;

import de.hundt.calcPi.concurrent.AtomicRunnable;
import de.hundt.calcPi.solutions.Leibniz;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;


public class MainViewController
{
	@FXML
	private ScatterChart<?, ?> scatterChart;
	@FXML
	private BarChart<?, ?> barChart;
	@FXML
	private TextArea textAreaPi;
	@FXML
	private RadioButton solution1;
	@FXML
	private RadioButton solution2;
	@FXML
	private RadioButton solution3;
	@FXML
	private Slider speedSlider;
	@FXML
	private Button btnCalculate;
	@FXML
	private Button btnAbort;
	private AtomicRunnable atomicRunnable = null;

	private IntegerProperty speedProperty = new SimpleIntegerProperty();

	private XYChart.Series series = new XYChart.Series();
	private XYChart.Series barChartSeries = new XYChart.Series();

	@FXML
	private void initialize()
	{
		setListeners();
		this.series.setName("Pi");
		this.scatterChart.getData().add(series);
		this.barChart.getData().setAll(barChartSeries);
	}


	private void setListeners()
	{
		RadioButton[] radioButtons = {this.solution1, this.solution2, this.solution3};

		for (RadioButton radioButton : radioButtons)
		{

			radioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue.booleanValue())
				{
					for (RadioButton rB : radioButtons)
					{

						if (rB != radioButton)
						{
							rB.setSelected(false);
						}
					}
				}
			});
		}

		this.speedSlider.valueProperty().addListener((observable, oldValue, newValue) ->
				this.speedProperty.set(newValue.intValue()));


		this.textAreaPi.textProperty().addListener((observable, oldValue, newValue) -> {
			Platform.runLater(() ->
			{
				System.out.println(newValue);
				series.getData().add(new XYChart.Data<>(String.valueOf(series.getData().size()), Double.parseDouble(textAreaPi.getText())));
				fillBarChart(textAreaPi.getText());
			});
		});
	}


	private void fillBarChart(String pi)
	{
		List<Integer> digits = pi.split("\\.")[1].chars().mapToObj(Character::getNumericValue).collect(Collectors.toList());
		Map<Integer, Long> countDigits = digits.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		this.barChartSeries.getData().clear();

		for (Map.Entry entry : countDigits.entrySet())
		{
			this.barChartSeries.getData().add(new XYChart.Data(String.valueOf(entry.getKey()), entry.getValue()));
		}
	}


	@FXML
	void btnAbortClicked(ActionEvent event)
	{
		if (this.atomicRunnable != null)
		{
			this.atomicRunnable.interrupt();
		}

		this.btnAbort.setDisable(true);
		this.btnCalculate.setDisable(false);
	}


	@FXML
	void btnCalculateClicked(ActionEvent event)
	{
		this.btnAbort.setDisable(false);
		this.btnCalculate.setDisable(true);

		if (this.solution1.isSelected())
		{

			Leibniz leibniz = new Leibniz((int) this.speedSlider.getValue());
			this.atomicRunnable = leibniz;
			this.textAreaPi.textProperty().bind(leibniz.getStringRepresentation());


			leibniz.start();
		}
		else if (!this.solution2.isSelected())
		{


			if (!this.solution3.isSelected())
			{


				this.btnAbort.setDisable(true);
				this.btnCalculate.setDisable(false);
			}
		}
	}
}
