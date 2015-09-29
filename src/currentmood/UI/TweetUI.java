package currentmood.UI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import twitter4j.Status;

public class TweetUI extends JPanel   {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -579383294132336090L;
	protected Status tweet;
	protected ActionListener listener;
	protected JLabel name, text;
	protected JCheckBox feelButton;
	protected JPanel tPanelBtn;
	protected JButton btnAnnote;
	
	public TweetUI (Status tweet)
	{
		//this.setBackground(new Color(255, 255, 255));
		this.setSize((int)this.getMaximumSize().getWidth(), 1000);
		this.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 0, 10, 10), new EtchedBorder()));
		this.name = new JLabel(tweet.getUser().getName()+" - "+tweet.getUser().getScreenName());
		this.text = new JLabel(tweet.getText());
		this.tPanelBtn = new JPanel();
		this.tPanelBtn.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.btnAnnote= new JButton("Annoter");
		this.tPanelBtn.add(btnAnnote);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(this.name);
		this.add(this.text);
		this.add(btnAnnote);
	}
	
	public void addActionListener(ActionListener al)
	{
		this.listener= al;
	}

	
		

}
