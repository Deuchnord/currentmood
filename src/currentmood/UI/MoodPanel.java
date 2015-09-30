package currentmood.UI;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import twitter4j.Status;

public class MoodPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4687360968395176153L;
	protected Status tweet;
	protected JLabel lIdTweet;
	protected JRadioButton JRNone, JRNeutral, JRBad, JRGood;
	protected JButton btnAddToList;
	
	public MoodPanel(){
		super();
		
		this.setBackground(new Color(255, 255,0));
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		this.lIdTweet= new JLabel();
		
		this.JRBad = new JRadioButton("Mauvais");
		this.JRBad.setVisible(false);
		this.JRNeutral = new JRadioButton("Neutre");
		this.JRNeutral.setVisible(false);
		this.JRGood = new JRadioButton("Bon");
		this.JRGood.setVisible(false);
		
		this.add(this.JRBad);
		this.add(this.JRNeutral);
		this.add(this.JRGood);
		
	}
	
	public void setStatus(Status tweet)
	{
		this.tweet = tweet;
		this.lIdTweet.setText(String.valueOf(tweet.getId()));
	}

}
