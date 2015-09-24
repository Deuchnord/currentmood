package currentmood.UI;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import twitter4j.Status;

public class TweetUI extends JPanel   {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -579383294132336090L;
	protected Status tweet;
	protected ActionListener listener;
	protected JLabel name, text;
	
	public TweetUI (Status tweet)
	{
		this.setBackground(new Color(255, 255, 255));
		this.name = new JLabel(tweet.getUser().getName()+" - "+tweet.getUser().getScreenName());
		this.text = new JLabel(tweet.getText());
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(this.name);
		this.add(this.text);
	}
	
	public void addActionListener(ActionListener al)
	{
		this.listener= al;
	}

	
		

}
