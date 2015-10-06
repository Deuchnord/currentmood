package currentmood.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
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
	protected ButtonGroup moodGroup;
	protected JRadioButton JRNone, JRNeutral, JRBad, JRGood;
	protected JButton btnAddToList, MPClose;
	protected JPanel buttonPanel, choicePanel;
	protected ActionListener CancelAction, OKAction;
	
	public MoodPanel(){
		super();
		
		this.setBackground(new Color(255, 255,0));
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		this.choicePanel = new JPanel();
		this.choicePanel.setLayout(new BoxLayout(this.choicePanel,BoxLayout.Y_AXIS));
		
		this.lIdTweet= new JLabel();
		
		this.moodGroup = new ButtonGroup();
		
		this.JRBad = new JRadioButton("Mauvais");
		this.JRBad.setVisible(false);
		this.JRBad.setActionCommand("0");
		this.JRNeutral = new JRadioButton("Neutre");
		this.JRNeutral.setVisible(false);
		this.JRNeutral.setActionCommand("2");
		this.JRGood = new JRadioButton("Bon");
		this.JRGood.setVisible(false);
		this.JRGood.setActionCommand("4");
		this.choicePanel.add(lIdTweet);
		this.moodGroup.add(this.JRBad);
		this.moodGroup.add(this.JRNeutral);
		this.moodGroup.add(this.JRGood);
		
		this.choicePanel.add(this.JRBad);
		this.choicePanel.add(this.JRNeutral);
		this.choicePanel.add(this.JRGood);
		
		this.buttonPanel = new JPanel();
		this.buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.intializingListener();
		this.btnAddToList = new JButton("Ajouter");
		this.btnAddToList.addActionListener(OKAction);
		this.MPClose= new JButton("Fermer");
		this.MPClose.addActionListener(CancelAction);
		this.buttonPanel.add(btnAddToList);
		this.buttonPanel.add(MPClose);
		this.buttonPanel.setVisible(false);
		this.add(choicePanel, BorderLayout.WEST);
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		
	}
	
	public void setStatus(Status tweet)
	{
		this.tweet = tweet;
		this.lIdTweet.setText(String.valueOf(tweet.getId()));
		this.appeared();
	}
	
	public void appeared()
	{
		this.JRBad.setVisible(true);
		this.JRNeutral.setVisible(true);
		this.JRGood.setVisible(true);
		this.buttonPanel.setVisible(true);
		
	}
	
	private void intializingListener()
	{
		this.CancelAction = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//MoodPanel.this.disappeared();
				
			}
		};
		
		this.OKAction = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
	}

}
