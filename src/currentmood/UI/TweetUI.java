package currentmood.UI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import currentmood.util.Tweet;

import twitter4j.Status;

public class TweetUI extends JPanel implements ActionListener  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -579383294132336090L;
	protected Status status;
	protected ActionListener listener;
	protected JLabel name, text;
	protected JCheckBox feelButton;
	protected JPanel tPanelBtn;
	protected JButton btnAnnote;
	protected Tweet tweet;
	
	public TweetUI (Status status)
	{
		//this.setBackground(new Color(255, 255, 255));
		this.status = status;
		this.setSize((int)this.getMaximumSize().getWidth(), 1000);
		this.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 0, 10, 10), new EtchedBorder()));
		this.setLabel(status.getUser().getName(), status.getUser().getScreenName(), status.getText());
		this.tPanelBtn = new JPanel();
		this.tPanelBtn.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		addButton();
	}
	
	public TweetUI(Tweet tweet)
	{
		this.tweet = tweet;
		this.setSize((int)this.getMaximumSize().getWidth(), 1000);
		this.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 0, 10, 10), new EtchedBorder()));
		this.setLabel(tweet.getUser(), tweet.getUser(), tweet.getText());
		this.tPanelBtn = new JPanel();
		this.tPanelBtn.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		addButton();
	}

	private void addButton() {
		this.btnAnnote= new JButton("Annoter");
		this.btnAnnote.addActionListener(this);
		this.add(btnAnnote);
	}
	
	private void setLabel(String user, String screenName, String text)
	{
		this.name = new JLabel(user+" - "+screenName);
		this.text = new JLabel(text);
		this.add(this.name);
		this.add(this.text);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(TweetUI.this.status !=null && TweetUI.this.tweet==null)
			((Win) SwingUtilities.getRoot(this)).moodPanel.setStatus(status);
		else if(TweetUI.this.status == null && TweetUI.this.tweet!=null)
			((Win) SwingUtilities.getRoot(this)).moodPanel.setStatus(tweet);
	}
	
	
	
	

	
		

}
