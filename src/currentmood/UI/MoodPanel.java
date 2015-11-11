package currentmood.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

import currentmood.util.Tweet;
import currentmood.util.classifier.ClassificationBaysienne;
import currentmood.util.classifier.ClassificationKNN;
import currentmood.util.classifier.ClassificationMotCle;
import currentmood.util.classifier.OutOfBoundsException;

import twitter4j.Status;

public class MoodPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4687360968395176153L;
	protected Status status;
	protected Tweet tweet;
	protected JLabel lIdTweet;
	protected ButtonGroup moodGroup;
	protected JRadioButton JRNone, JRNeutral, JRBad, JRGood;
	protected JButton btnAddToList, MPClose, KNNButton, BayesButton,motscleButton;
	protected JPanel buttonPanel, choicePanel, statPanel; 
	protected ActionListener CancelAction, OKAction, KNNAction, BayesAction, motclesAction;
	
	public MoodPanel(){
		super();
		
		this.setBackground(new Color(255, 255,0));
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		this.choicePanel = new JPanel();
		this.choicePanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		this.statPanel = new JPanel();
		this.statPanel.setLayout(new BorderLayout());
		
		
	
		this.lIdTweet= new JLabel();
		c.fill =GridBagConstraints.NONE;
		c.gridwidth=GridBagConstraints.REMAINDER;
		c.gridheight=1;
		c.gridx=0;
		c.gridy=0;
		c.weightx=0.5;
		c.anchor =GridBagConstraints.FIRST_LINE_START;
		c.insets = new Insets(-200,0,0,0);
		this.choicePanel.add(this.lIdTweet,c);
		
		this.moodGroup = new ButtonGroup();
		
		this.JRBad = new JRadioButton(":(");
		this.JRBad.setActionCommand("0");
		this.JRBad.setVisible(false);
		c.gridx=0;
		c.gridy=1;
		c.weightx=0.0;
		c.insets = new Insets(-180,0,0,0);
		this.choicePanel.add(this.JRBad,c);
		
		
		
		this.JRNeutral = new JRadioButton(":|");
		this.JRNeutral.setActionCommand("2");
		this.JRNeutral.setVisible(false);
		c.weightx=0.0;
		c.gridx=1;
		c.gridy=1;
		c.insets = new Insets(-180,75,0,0);
		this.choicePanel.add(this.JRNeutral,c);
		
		
		

		
		this.JRGood = new JRadioButton(":)");
		this.JRGood.setVisible(false);
		this.JRGood.setActionCommand("4");
		c.weightx=0.0;
		c.gridx=2;
		c.gridy=1;
		c.insets = new Insets(-180,150,0,0);
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
		this.KNNButton = new JButton("Utiliser K-NN");
		this.KNNButton.addActionListener(KNNAction);
		this.BayesButton = new JButton("Utiliser Bayes");
		this.BayesButton.addActionListener(BayesAction);
		this.motscleButton = new JButton("Mots-cles");
		this.motscleButton.addActionListener(motclesAction);
		this.buttonPanel.add(btnAddToList);
		this.buttonPanel.add(MPClose);
		this.buttonPanel.setVisible(false);
		this.buttonPanel.setSize(new Dimension(this.buttonPanel.getPreferredSize().width,this.btnAddToList.getPreferredSize().height));
		
		
		
		
		
		
	
		c.gridx=0;
		c.gridy=2;
		c.weightx=0.0;
		c.gridwidth=3;
		c.insets = new Insets(0,0,0,0);
		this.choicePanel.add(motscleButton);
		this.choicePanel.add(KNNButton);
		this.choicePanel.add(BayesButton);
		this.choicePanel.add(buttonPanel,c);
		this.add(choicePanel,BorderLayout.NORTH);
		this.add(statPanel,BorderLayout.SOUTH);
		
		
		
	}
	
	public void setStatus(Status status)
	{
		this.status = status;
		this.lIdTweet.setText(String.valueOf(status.getId()));
		this.appeared();
	}
	
	public void setStatus(Tweet tweet)
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
				MoodPanel.this.addAnnotedTweet(MoodPanel.this.tweet);
				
			}
		};
		
		this.KNNAction = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					MoodPanel.this.tweet= ClassificationKNN.knnTweet(MoodPanel.this.displayK(), MoodPanel.this.tweet, MoodPanel.this.mainWindow().annotatedTweets );
				} catch (OutOfBoundsException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				MoodPanel.this.addAnnotedTweet(MoodPanel.this.tweet);
				JOptionPane.showMessageDialog(MoodPanel.this,"Le tweet a été annoté : "+tweet.getAnnotation(true));
				
			}
		};
		
		this.BayesAction = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Tweet> listTweet= MoodPanel.this.mainWindow().annotatedTweets;
				MoodPanel.this.tweet.setValue(ClassificationBaysienne.evaluateTweet(MoodPanel.this.tweet,listTweet));
				MoodPanel.this.addAnnotedTweet(MoodPanel.this.tweet);
				JOptionPane.showMessageDialog(MoodPanel.this,"Le tweet a été annoté : "+tweet.getAnnotation(true));
				
			}
		};
		
		this.motclesAction =new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String cp = mainWindow().cheminPositif;
				String cn = mainWindow().cheminNegatif;
				ClassificationMotCle classifier = new ClassificationMotCle(cp, cn);
				MoodPanel.this.tweet = classifier.evaluateTweet(MoodPanel.this.tweet);
				MoodPanel.this.addAnnotedTweet(MoodPanel.this.tweet);
				JOptionPane.showMessageDialog(MoodPanel.this,"Le tweet a été annoté : "+tweet.getAnnotation(true));
				
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
		 
	}
	
	/**
	 * @return La valeur numérique de l'humeur sélectionnée (-1 si rien)
	 */
	private int getFeelValue()
	{
		if(this.JRBad.isSelected())
			return 0;
		else if(this.JRNeutral.isSelected())
			return 2;
		else if(this.JRGood.isSelected())
			return 4;
		return -1;
	}
	
	private void addAnnotedTweet(Tweet tw)
	{
		Tweet tweet = new Tweet(tw.getId(), tw.getUser(), tw.getText(), tw.getCreatedAt() ,tw.getQuery(), this.getFeelValue());
		mainWindow().annotatedTweets.remove(tw);
		mainWindow().annotatedTweets.add(tweet);
	}
	
	private int displayK()
	{
		JOptionPane jop = new JOptionPane();
		String choix = jop.showInputDialog(this, "Indiquez un nombre de plus proches voisins :", "Choix du nombre des plus proches voisins", JOptionPane.QUESTION_MESSAGE);
		return Integer.parseInt(choix);
	}
	
	private Win mainWindow()
	{
		return (Win) (SwingUtilities.getRoot(this));
	}


}
