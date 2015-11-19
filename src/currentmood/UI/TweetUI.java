package currentmood.UI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import currentmood.util.Tweet;
import currentmood.util.classifier.ClassificationBaysienne;
import currentmood.util.classifier.ClassificationKNN;
import currentmood.util.classifier.ClassificationMotCle;
import currentmood.util.classifier.OutOfBoundsException;

import twitter4j.Status;

public class TweetUI extends JPanel implements ActionListener  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -579383294132336090L;
	protected ActionListener listener;
	protected JLabel name, text, feel;
	protected JCheckBox feelButton;
	protected JPanel tPanelBtn;
	protected Tweet tweet;
	protected JPopupMenu menuContextuel;
	protected JMenu annotealamain,bayesMenu,presenceMenu,frequenceMenu, motsMenu,pasMotMenu, motsmenuFrequence, pasMotMenuFrequence;
	protected JMenuItem idTweet, motcleItem, knnItem,bayesItemPresence,bayesItemFrequence,badItem,neutralItem,goodItem,
	unigrammePresenceMots,unigrammePresencePasMots,unigrammeFrequenceMots,unigrammeFrequencePasMots,
	bigrammePresenceMots,bigrammePresencePasMots,bigrammeFrequenceMots,bigrammeFrequencePasMots;
	protected ActionListener knnAction, motcleAction, 
	bayesActionPresence,bayesActionFrequence,bayesPresenceBigrammeAction,bayesFrequenceBigrammeAction,
	bayesPresenceUnigrammePasMotsAction,bayesFrequenceUnigrammePasMotsAction, bayesPresenceBigrammePasMotsAction,bayesFrequenceBigrammePasMotsAction,
	badAction,neutralAction,goodAction;
	
	public TweetUI (Status status, String query)
	{
		this(new Tweet(status, query));
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
		this.intializingListener();
		this.setMenuContextuel();
	}

	private void setLabel(String user, String screenName, String text)
	{
		this.name = new JLabel(user+" - "+screenName);
		this.text = new JLabel(text);
		this.feel = new JLabel(this.getFeel());
		this.add(this.name);
		this.add(this.text);
		this.add(this.feel);
	}
	
	private void setMenuContextuel()
	{
		this.menuContextuel = new JPopupMenu("Tweet n°: "+this.tweet.getId());
		this.idTweet = new JMenuItem("Tweet n°: "+this.tweet.getId());
		this.knnItem = new JMenuItem("Classer avec KNN");
		this.motcleItem = new JMenuItem("Classer avec les mots-clés");
		this.motcleItem.addActionListener(motcleAction);
		this.bayesItemPresence = new JMenuItem("Présence");
		this.bayesItemFrequence = new JMenuItem("Fréquence");
		this.annotealamain = new JMenu("Annoter à la main");
		this.bayesMenu = new JMenu("Classer avec la méthode Bayésienne");
		this.presenceMenu=new JMenu("Par présence");
		this.frequenceMenu = new JMenu("Par fréquence");
		this.motsMenu = new JMenu("En utilisant tous les mots");
		this.pasMotMenu = new JMenu("Pas < 3");
		this.pasMotMenuFrequence = new JMenu("Pas < 3");
		this.motsmenuFrequence=new JMenu("En utilisant tous les mots");
		
		this.unigrammePresenceMots = new JMenuItem("Unigramme");
		this.unigrammePresenceMots.addActionListener(bayesActionPresence);
		this.motsMenu.add(unigrammePresenceMots);
		
		this.bigrammePresenceMots = new JMenuItem("Bigramme");
		this.bigrammePresenceMots.addActionListener(bayesPresenceBigrammeAction);
		this.motsMenu.add(bigrammePresenceMots);
		
		this.unigrammeFrequenceMots = new JMenuItem("Unigramme");
		this.unigrammeFrequenceMots.addActionListener(bayesActionFrequence);
		this.motsmenuFrequence.add(unigrammeFrequenceMots);
		
		this.bigrammeFrequenceMots = new JMenuItem("Bigramme");
		this.bigrammeFrequenceMots.addActionListener(bayesFrequenceBigrammeAction);
		this.motsmenuFrequence.add(bigrammeFrequenceMots);

		this.unigrammePresencePasMots = new JMenuItem("Unigramme");
		this.unigrammePresencePasMots.addActionListener(bayesPresenceUnigrammePasMotsAction);
		this.pasMotMenu.add(unigrammePresencePasMots);
		
		this.bigrammePresencePasMots = new JMenuItem("Bigramme");
		this.bigrammePresencePasMots.addActionListener(bayesPresenceBigrammePasMotsAction);
		this.pasMotMenu.add(bigrammePresencePasMots);
		
		this.unigrammeFrequencePasMots = new JMenuItem("Unigramme");
		this.unigrammeFrequencePasMots.addActionListener(bayesFrequenceUnigrammePasMotsAction);
		this.pasMotMenuFrequence.add(unigrammeFrequencePasMots);
		
		this.bigrammeFrequencePasMots = new JMenuItem("Bigramme");
		this.bigrammeFrequencePasMots.addActionListener(bayesFrequenceBigrammePasMotsAction);
		this.pasMotMenuFrequence.add(bigrammeFrequencePasMots);
		
		this.badItem=new JMenuItem("Mauvais");
		this.badItem.addActionListener(badAction);
		
		this.neutralItem= new JMenuItem("Neutre");
		this.neutralItem.addActionListener(neutralAction);
		
		this.goodItem = new JMenuItem("Bon");
		this.goodItem.addActionListener(goodAction);
		
		this.annotealamain.add(this.badItem);
		this.annotealamain.add(this.neutralItem);
		this.annotealamain.add(this.goodItem);
		
		this.knnItem.addActionListener(knnAction);
		
		this.bayesItemPresence.addActionListener(bayesActionPresence);
		this.bayesMenu.add(this.bayesItemPresence);
		
		this.bayesItemFrequence.addActionListener(bayesActionFrequence);
		this.bayesMenu.add(this.bayesItemFrequence);
		
		this.presenceMenu.add(this.motsMenu);
		this.presenceMenu.add(this.pasMotMenu);
		
		this.frequenceMenu.add(this.motsmenuFrequence);
		this.frequenceMenu.add(this.pasMotMenuFrequence);
		
		this.bayesMenu.add(this.presenceMenu);
		this.bayesMenu.add(this.frequenceMenu);
		
		this.menuContextuel.add(this.idTweet);
		this.menuContextuel.add(this.annotealamain);
		this.menuContextuel.add(this.motcleItem);
		this.menuContextuel.add(this.knnItem);
		this.menuContextuel.add(this.bayesMenu);
		
		this.setComponentPopupMenu(menuContextuel);
		
	}
	
	private void intializingListener()
	{
		
		
		this.knnAction = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					TweetUI.this.tweet= ClassificationKNN.knnTweet(TweetUI.this.displayK(), TweetUI.this.tweet, TweetUI.this.mainWindow().annotatedTweets );
				} catch (OutOfBoundsException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				TweetUI.this.addAnnotedTweet(TweetUI.this.tweet);
				JOptionPane.showMessageDialog(TweetUI.this,"Le tweet a été annoté : "+tweet.getAnnotation(true));
				
			}
		};
		
		initializeBayesListener();
		
		this.motcleAction =new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String cp = mainWindow().cheminPositif;
				String cn = mainWindow().cheminNegatif;
				ClassificationMotCle classifier = new ClassificationMotCle(cp, cn);
				TweetUI.this.tweet = classifier.evaluateTweet(TweetUI.this.tweet);
				TweetUI.this.addAnnotedTweet(TweetUI.this.tweet);
				JOptionPane.showMessageDialog(TweetUI.this.mainWindow(),"Le tweet a été annoté : "+tweet.getAnnotation(true));
				
			}
		};
		
		initializeFeelListener();
	}

	/**
	 * Initialise les listener pour l'annotation à la main
	 */
	private void initializeFeelListener() {
		this.badAction =new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TweetUI.this.tweet.setValue(Tweet.BAD);
				TweetUI.this.addAnnotedTweet(TweetUI.this.tweet);
				
			}
		};
		
		this.neutralAction =new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TweetUI.this.tweet.setValue(Tweet.NEUTRAL);
				TweetUI.this.addAnnotedTweet(TweetUI.this.tweet);
				
			}
		};
		
		this.goodAction =new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TweetUI.this.tweet.setValue(Tweet.GOOD);
				TweetUI.this.addAnnotedTweet(TweetUI.this.tweet);
				
			}
		};
	}

	/**
	 * Initialise les listener pour la classification Bayesienne
	 */
	private void initializeBayesListener() {
		this.bayesPresenceBigrammeAction = new ActionListener() {
			//Presence-bigramme-avec mots courts
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Tweet> listTweet= TweetUI.this.mainWindow().annotatedTweets;
				TweetUI.this.tweet.setValue(ClassificationBaysienne.evaluateTweet(TweetUI.this.tweet,listTweet,false,false,true));
				TweetUI.this.addAnnotedTweet(TweetUI.this.tweet);
				JOptionPane.showMessageDialog(TweetUI.this.mainWindow(),"Le tweet a été annoté : "+tweet.getAnnotation(true));
				
			}
		};
		
		this.bayesActionPresence = new ActionListener() {
			//Présence-unigramme-avec mots courts
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Tweet> listTweet= TweetUI.this.mainWindow().annotatedTweets;
				TweetUI.this.tweet.setValue(ClassificationBaysienne.evaluateTweet(TweetUI.this.tweet,listTweet,false,false,false));
				TweetUI.this.addAnnotedTweet(TweetUI.this.tweet);
				JOptionPane.showMessageDialog(TweetUI.this.mainWindow(),"Le tweet a été annoté : "+tweet.getAnnotation(true));
				
			}
		};
		
		this.bayesActionFrequence = new ActionListener() {
			//frequence-unigramme,avec mots courts
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Tweet> listTweet= TweetUI.this.mainWindow().annotatedTweets;
				TweetUI.this.tweet.setValue(ClassificationBaysienne.evaluateTweet(TweetUI.this.tweet,listTweet,true,false,false));
				TweetUI.this.addAnnotedTweet(TweetUI.this.tweet);
				JOptionPane.showMessageDialog(TweetUI.this.mainWindow(),"Le tweet a été annoté : "+tweet.getAnnotation(true));
				
			}
		};
		
		this.bayesFrequenceBigrammeAction = new ActionListener() {
			//frequence-bigramme-avec les mots courts
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Tweet> listTweet= TweetUI.this.mainWindow().annotatedTweets;
				TweetUI.this.tweet.setValue(ClassificationBaysienne.evaluateTweet(TweetUI.this.tweet,listTweet,true,false,true));
				TweetUI.this.addAnnotedTweet(TweetUI.this.tweet);
				JOptionPane.showMessageDialog(TweetUI.this.mainWindow(),"Le tweet a été annoté : "+tweet.getAnnotation(true));
				
			}
		};
		
		this.bayesPresenceUnigrammePasMotsAction = new ActionListener() {
			//Présence-Pas tous les mots--Unigramme
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Tweet> listTweet= TweetUI.this.mainWindow().annotatedTweets;
				TweetUI.this.tweet.setValue(ClassificationBaysienne.evaluateTweet(TweetUI.this.tweet,listTweet,false,true,false));
				TweetUI.this.addAnnotedTweet(TweetUI.this.tweet);
				JOptionPane.showMessageDialog(TweetUI.this.mainWindow(),"Le tweet a été annoté : "+tweet.getAnnotation(true));
				
			}
		};
		
		this.bayesPresenceBigrammePasMotsAction = new ActionListener() {
			//Présence-Pas tous les mots - Bigrammes
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Tweet> listTweet= TweetUI.this.mainWindow().annotatedTweets;
				TweetUI.this.tweet.setValue(ClassificationBaysienne.evaluateTweet(TweetUI.this.tweet,listTweet,false,true,true));
				TweetUI.this.addAnnotedTweet(TweetUI.this.tweet);
				JOptionPane.showMessageDialog(TweetUI.this.mainWindow(),"Le tweet a été annoté : "+tweet.getAnnotation(true));
			}
		};
		
		this.bayesFrequenceUnigrammePasMotsAction =new ActionListener() {
			//Frequence -Pas tous les mots - Unigramme
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Tweet> listTweet= TweetUI.this.mainWindow().annotatedTweets;
				TweetUI.this.tweet.setValue(ClassificationBaysienne.evaluateTweet(TweetUI.this.tweet,listTweet,true,true,false));
				TweetUI.this.addAnnotedTweet(TweetUI.this.tweet);
				JOptionPane.showMessageDialog(TweetUI.this.mainWindow(),"Le tweet a été annoté : "+tweet.getAnnotation(true));
				
			}
		};
		
		this.bayesFrequenceBigrammePasMotsAction = new ActionListener() {
			//Frequence - Pas tous les mots -bigramme
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Tweet> listTweet= TweetUI.this.mainWindow().annotatedTweets;
				TweetUI.this.tweet.setValue(ClassificationBaysienne.evaluateTweet(TweetUI.this.tweet,listTweet,true,true,true));
				TweetUI.this.addAnnotedTweet(TweetUI.this.tweet);
				JOptionPane.showMessageDialog(TweetUI.this.mainWindow(),"Le tweet a été annoté : "+tweet.getAnnotation(true));
				
			}
		};
	}
	
	private void addAnnotedTweet(Tweet tw)
	{
		
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

	@Override
	public void actionPerformed(ActionEvent e) {
		//((Win) SwingUtilities.getRoot(this)).moodPanel.setStatus(tweet);
	}
	
	private String getFeel()
	{
		if(this.tweet.getValue()==Tweet.GOOD)
		{
			return "Bon";
		}
		else if(this.tweet.getValue()==Tweet.NEUTRAL)
			return "Neutre";
		else if(this.tweet.getValue()==Tweet.BAD)
			return "Mauvais";
		else
			return "Non annoté";
	}
	
	private void refresh()
	{
		this.feel.setText(this.getFeel());
		this.validate();
	}
	
	
	
	

	
		

}
