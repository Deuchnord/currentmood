package currentmood.UI.chart;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.data.general.DefaultPieDataset;

public class PieChart extends Chart {

	/**
	 * Creates and returns a pie chart from the data set in parameters
	 */
	@Override
	public ChartPanel createChart(int negatives, int neutrals, int positives) {
		
		long sum = negatives + neutrals + positives; 
		double negativeRatio = negatives /  sum * 100,
				neutralRatio = neutrals /  sum * 100,
				positiveRatio = positives /  sum * 100;
		
		DefaultPieDataset defaultPieDataset = new DefaultPieDataset();
		defaultPieDataset.setValue("Négatif", negativeRatio);
		defaultPieDataset.setValue("Neutre", neutralRatio);
		defaultPieDataset.setValue("Positif", positiveRatio);
		
		JFreeChart jFreeChart = ChartFactory.createPieChart("Répartition", defaultPieDataset, true, false, false);
		ChartPanel chartPanel = new ChartPanel(jFreeChart);
		
		setPlot(jFreeChart);
		
		chartPanel.setVisible(true);
		
		return chartPanel;
	}
	
	protected Plot setPlot(JFreeChart jFreeChart)
	{
		PiePlot plot = (PiePlot) jFreeChart.getPlot();
		
		plot.setSectionPaint("Négatifs", new Color(0xCC0000));
		plot.setSectionPaint("Neutres", new Color(0xCCCCCC));
		plot.setSectionPaint("Positifs", new Color(0x00CC00));
		
		plot.setLabelFont(new Font("DejaVu Sans", Font.PLAIN, 14));
		plot.setLabelGap(0.02);
		plot.setLabelOutlinePaint(null);
        plot.setLabelShadowPaint(null);
        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setMaximumLabelWidth(0.20);
        plot.setLabelLinkStyle(PieLabelLinkStyle.CUBIC_CURVE);
        plot.setSimpleLabels(true);
        
        PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{0} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        ((PiePlot)plot).setLabelGenerator(gen);
		
		return plot;
	}

}
