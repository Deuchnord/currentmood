package currentmood.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.TwitterException;

import currentmood.util.CMTwitter;
import currentmood.util.CSVFile;
import currentmood.util.NotConnectedException;
import currentmood.util.Proxy;
import currentmood.util.Tweet;

public class Win extends JFrame {
	
	private static final long serialVersionUID = -3653464037872958325L;

	protected List<Tweet> annotatedTweets;
	
	protected JMenuBar menu;
	protected JMenu fileMenu, aboutMenu, optionMenu;
	protected JMenuItem openCSVItem, createCSVItem, proxyItem ;
	protected JPanel searchpanel, infopanel, tweetpanel;
	protected MoodPanel moodPanel;
	protected JTextField search;
	protected JButton Btn_search_Ok;
	protected CMTwitter cmTwitter;
	protected JScrollPane scrollTweetPanel;
	protected JLabel lInfo, lInfoNb, lInfoTimeReload, lInfoTimeReloadNb;
	protected JRadioButton JRNone, JRNeutral, JRBad, JRGood;
	
	
	public Win()
	{
		this.annotatedTweets = new ArrayList<Tweet>();
		cmTwitter = new CMTwitter();
		//cmTwitter.setProxy(new Proxy("cache-etu.univ-lille1.fr", 3128));
		System.out.println("1 " + (cmTwitter == null));
		
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		//Paramétrage de la fenetre 
		this.setSize(screen);
		
		this.setTitle("#currentmood");
		//paramétrage du menu
		this.menu = new JMenuBar();
		this.fileMenu = new JMenu("Fichier");
		this.menu.add(this.fileMenu);
		this.openCSVItem = new JMenuItem("Ouvrir un CSV", KeyEvent.VK_O);
		this.openCSVItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				if(jfc.showOpenDialog((Component) e.getSource())==JFileChooser.APPROVE_OPTION)
				{
					try {
						Win.this.annotatedTweets = CSVFile.readTweetsInCSV(jfc.getSelectedFile().getAbsolutePath());
						Win.this.readTweet();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		
		this.createCSVItem = new JMenuItem("Créer un CSV", KeyEvent.VK_C);
		this.createCSVItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(annotatedTweets.isEmpty())
				{
					JOptionPane.showMessageDialog(Win.this, "Vous ne pouvez pas enregistrer maintenant.\nVeuillez commencer par annoter des tweets.", "Impossible d'enregistrer un CSV", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				JFileChooser jfc = new JFileChooser();
				
				if(jfc.showSaveDialog((Component) e.getSource()) == JFileChooser.APPROVE_OPTION)
				{
					try {
						CSVFile.writeTweetsInCSV(jfc.getSelectedFile().getAbsolutePath(), Win.this.annotatedTweets, true);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(Win.this, "Une erreur s'es produite lors de l'enregistrement.\nVérifiez que vous avez les droits d'écriture à l'emplacement choisi.\n\nErreur : "+e1.getLocalizedMessage(), "Erreur à l'enregistrement", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		this.fileMenu.add(this.openCSVItem);
		this.fileMenu.add(this.createCSVItem);
		this.optionMenu = new JMenu("Options");
		this.proxyItem = new JMenuItem("Paramètres proxy");
		this.proxyItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				WinProxy wProxy = new WinProxy(Win.this);
				
			}
		});
		this.optionMenu.add(this.proxyItem);
		this.menu.add(optionMenu);
		this.aboutMenu = new JMenu("A propos");
		this.menu.add(this.aboutMenu);
		
		this.setJMenuBar(menu);
		
		//Ajout de la barre de recherche
		this.searchpanel = new JPanel();
		this.searchpanel.setLayout(new BoxLayout(this.searchpanel,BoxLayout.X_AXIS));
		this.search = new JTextField("Rechercher les tweets");
		this.search.setSize((int) screen.getWidth(), 40);
		this.searchpanel.add(this.search);
		//this.searchpanel.setBackground(new Color(255, 0, 0));
		this.add(this.searchpanel,BorderLayout.NORTH);
		this.Btn_search_Ok = new JButton("Recherche");
		this.Btn_search_Ok.setMaximumSize(new Dimension(250, this.Btn_search_Ok.getMinimumSize().height));
		this.Btn_search_Ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					Win.this.cmTwitter.connect();
					List<Status> tweets = Win.this.cmTwitter.searchTweets(Win.this.search.getText());
					Win.this.refreshLimit(Win.this.cmTwitter.getRateLimit());
					for(Status status : tweets)
					{
						TweetUI tw = new TweetUI(status);
						System.out.println("### Tweet from @"+status.getUser().getScreenName()+" ###\n"+status.getText()+"\n\n");
						Win.this.tweetpanel.add(tw, BorderLayout.CENTER);
						
					}
					//TweetUI tw = new TweetUI(tweets.get(0));
					
					
					Win.this.validate();
					
				}
				catch (TwitterException ex) {
					System.out.println("Cannot connect: " + ex.getMessage());
				} catch (NotConnectedException ex) {
					System.out.println("You must be connected to perform this action!");
				}
				
			}
		});
		this.search.setMaximumSize(new Dimension(this.getWidth()-200,this.Btn_search_Ok.getMinimumSize().height));
		this.searchpanel.add(this.Btn_search_Ok);
		
		this.infopanel = new JPanel();
		this.moodPanel = new MoodPanel();
		this.tweetpanel= new JPanel();
		//this.infopanel.setBackground(new Color(0, 255, 0));
		
		
		
		
		//this.tweetpanel.setBackground(new Color(0, 0, 255));
		this.tweetpanel.setLayout(new BoxLayout(this.tweetpanel, BoxLayout.Y_AXIS));
		this.scrollTweetPanel=new JScrollPane(this.tweetpanel);
		this.lInfo = new JLabel("Nombre de requêtes possible : ");
		this.lInfoNb = new JLabel("___");
		this.lInfoTimeReload = new JLabel("Rechargement dans : ");
		this.lInfoTimeReloadNb = new JLabel("___");
		this.infopanel.add(lInfo);
		this.infopanel.add(lInfoNb);
		this.infopanel.add(lInfoTimeReload);
		this.infopanel.add(lInfoTimeReloadNb);
		this.add(infopanel,BorderLayout.SOUTH);
		this.add(moodPanel,BorderLayout.EAST);
		this.add(scrollTweetPanel,BorderLayout.CENTER);
		
		this.setVisible(true);
		
		
	}
	
	private void refreshLimit(RateLimitStatus rl)
	{
		this.lInfoNb.setText(rl.getRemaining()+"/"+rl.getLimit());
		this.lInfoTimeReloadNb.setText(rl.getSecondsUntilReset()+" secondes");
	
	}
	
	private void readTweet()
	{
		for(Tweet tweet : this.annotatedTweets)
		{
			this.tweetpanel.add(new TweetUI(tweet),BorderLayout.CENTER);
		}
	}

}
