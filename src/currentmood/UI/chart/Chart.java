package currentmood.UI.chart;

import org.jfree.chart.ChartPanel;

public abstract class Chart {

	/**
	 * Creates and returns a chart from the data set in parameters
	 * that can be displayed in a Java window.
	 * @param negatives the number of negative tweets
	 * @param neutrals the number of neutral tweets
	 * @param positives the number of positive tweets
	 * @return the chart
	 */
	public abstract ChartPanel createChart(int negatives, int neutrals, int positives);
	
}
