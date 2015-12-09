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
		double somme = negatives+neutrals+positives;
		double neg,pos,neu;
		neg = (negatives/somme)*100;
		neu = (neutrals/somme)*100;
		pos = (positives/somme)*100;
		panelw = new JPanel();
		System.out.println(negatives/somme);
		System.out.println(neg+","+neu+","+pos);
		dataset.setValue("Mauvais",(int)neg );
		dataset.setValue("Neutre",(int)neu);
		dataset.setValue("Bon",(int)pos );
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
		cp.repaint();
		
		
		
		
		
		
	}
public WinChart(PieChart panel ,int negatives, int neutrals, int positives,String text)
	
	{
		double somme = negatives+neutrals+positives;
		double neg,pos,neu;
		neg = (negatives/somme)*100;
		neu = (neutrals/somme)*100;
		pos = (positives/somme)*100;
		panelw = new JPanel();
		System.out.println(negatives/somme);
		System.out.println(neg+","+neu+","+pos);
		dataset.setValue("Mauvais",(int)neg );
		dataset.setValue("Neutre",(int)neu);
		dataset.setValue("Bon",(int)pos );
		chart = ChartFactory.createPieChart(
				text, 
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
		cp.repaint();
		
		
		
		
		
		
	}

}
