package currentmood.UI;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import twitter4j.Status;

public class MoodPanel extends JPanel {
	protected Status tweet;
	protected JLabel lIdTweet;
	protected JRadioButton JRNone, JRNeutral, JRBad, JRGood;
	protected JButton btnAddToList;
	
	public MoodPanel(){
		super();
		
	}
	
	public void setSatus(Status tweet)
	{
		this.tweet = tweet;
		this.lIdTweet.setText(String.valueOf(tweet.getId()));
	}

}
