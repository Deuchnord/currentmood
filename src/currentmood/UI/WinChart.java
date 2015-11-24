package currentmood.UI;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;

import currentmood.UI.chart.PieChart;

public class WinChart extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6264810561528922939L;
	protected PieChart chart;
	
	public WinChart(PieChart panel ,int negatives, int neutrals, int positives)
	{
		this.chart = panel;
		this.setTitle("Résultat de la classification");
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		//Paramétrage de la fenetre 
		this.setSize(new Dimension((int)screen.getWidth()/2,(int)screen.getHeight()/2));
		this.add(chart.createChart(negatives, neutrals, positives));
		this.setVisible(true);
	}

}
