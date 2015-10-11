package currentmood.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

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
	protected JPanel buttonPanel, choicePanel, statPanel; 
	protected ActionListener CancelAction, OKAction;
	
	public MoodPanel(){
		super();
		
		this.setBackground(new Color(255, 255,0));
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		this.choicePanel = new JPanel();
		this.choicePanel.setLayout(new GridBagLayout());
		this.choicePanel.setBackground(new Color(255, 0, 0));
		GridBagConstraints c = new GridBagConstraints();
		this.statPanel = new JPanel();
		this.statPanel.setLayout(new BorderLayout());
		
		
		
		this.lIdTweet= new JLabel();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth=3;
		c.gridx=0;
		c.gridy=0;
		c.weightx=0.0;
		c.anchor =GridBagConstraints.FIRST_LINE_START;
		this.choicePanel.add(this.lIdTweet,c);
		
		this.moodGroup = new ButtonGroup();
		
		this.JRBad = new JRadioButton("Mauvais");
		this.JRBad.setActionCommand("0");
		this.JRBad.setVisible(false);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=1;
		c.weightx=0.0;
		this.choicePanel.add(this.JRBad,c);
		
		
		
		this.JRNeutral = new JRadioButton("Neutre");
		this.JRNeutral.setActionCommand("2");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx=1.0;
		c.insets = new Insets(0,70, 0, 0);
		this.choicePanel.add(this.JRNeutral,c);
		this.JRNeutral.setVisible(false);
		
		

		
		this.JRGood = new JRadioButton("Bon");
		this.JRGood.setVisible(false);
		this.JRGood.setActionCommand("4");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx=0.0;
		c.insets = new Insets(0,0, 0, 0);
		this.choicePanel.add(this.JRGood,c);
		
		
	
		
		
		this.moodGroup.add(this.JRBad);
		this.moodGroup.add(this.JRNeutral);
		this.moodGroup.add(this.JRGood);
		
		
		
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
		this.buttonPanel.setSize(new Dimension(this.buttonPanel.getPreferredSize().width,this.btnAddToList.getPreferredSize().height));
		this.buttonPanel.setBackground(new Color(60, 90, 60));
		
		
		
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=1;
		c.weightx=0.0;
		c.gridwidth=3;
		this.choicePanel.add(buttonPanel,c);
		this.add(choicePanel,BorderLayout.NORTH);
		this.add(statPanel,BorderLayout.SOUTH);
		
		
		
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
				MoodPanel.this.disappeared();
				
			}
		};
		
		this.OKAction = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	public void disappeared()
	{
		this.lIdTweet.setText("");
		this.JRBad.setVisible(false);
		this.JRNeutral.setVisible(false);
		this.JRGood.setVisible(false);
		this.buttonPanel.setVisible(false);
		
	}
	
	private void annote()
	{
		 //((Win) SwingUtilities.getRoot(this)).annotatedTweets.put(key, value);
	}

}
