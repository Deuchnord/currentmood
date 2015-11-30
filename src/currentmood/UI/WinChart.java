package currentmood.UI;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;

import currentmood.UI.chart.PieChart;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.*;
import org.jfree.data.general.*;

public class WinChart extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6264810561528922939L;
	private DefaultPieDataset dataset = new DefaultPieDataset();
	private JFreeChart chart;
	private ChartPanel cp;
	private JPanel panelw;
	
	
	public WinChart(PieChart panel ,int negatives, int neutrals, int positives)
	{
		panelw = new JPanel();
		
		dataset.setValue("Mauvais", negatives);
		dataset.setValue("Neutre", neutrals);
		dataset.setValue("Bon", positives);
		chart = ChartFactory.createPieChart(
				"Répartion des sentiments", 
				dataset,
				true, 
				true, 
				false);
		
		this.cp=new ChartPanel(chart);
		this.add(this.cp);
		this.setTitle("Résultat de la classification");
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		//Paramétrage de la fenetre 
		this.setSize(new Dimension((int)screen.getWidth()/2,(int)screen.getHeight()/2));
		this.setVisible(true);
		
		
		
		
		
		
	}

}
