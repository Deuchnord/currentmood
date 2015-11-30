package currentmood.UI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import currentmood.UI.chart.PieChart;
import currentmood.util.CMTwitter;
import currentmood.util.CSVFile;
import currentmood.util.NotConnectedException;
import currentmood.util.Tweet;
import currentmood.util.classifier.ClassificationBaysienne;
import currentmood.util.classifier.ClassificationKNN;
import currentmood.util.classifier.ClassificationMotCle;
import currentmood.util.classifier.OutOfBoundsException;

public class Win extends JFrame {
	
	private static final long serialVersionUID = -3653464037872958325L;

	protected List<Tweet> annotatedTweets;
	protected String cheminPositif, cheminNegatif;
	
	protected JMenuBar menu;
	protected JMenu fileMenu, aboutMenu, annotation, optionMenu,bayesMenu,bayesFreqMenu,bayesPresMenu, bayesfreqpasmot,bayesfreqavecmot;
	protected JMenuItem openCSVItem, createCSVItem, proxyItem,motcleItem,allMotsclesItem,allKNNItem,bayesfreqpasmotunigramme,bayesfreqpasmotbigramme,
	bayesfreqavecmotunigramme,bayesfreqavecmotbigramme;
	protected JPanel searchpanel, infopanel, tweetpanel;
	//protected MoodPanel moodPanel;
	protected JTextField search;
	protected JButton Btn_search_Ok;
	protected CMTwitter cmTwitter;
	protected JScrollPane scrollTweetPanel;
	protected JLabel lInfo, lInfoNb, lInfoTimeReload, lInfoTimeReloadNb;
	protected JRadioButton JRNone, JRNeutral, JRBad, JRGood;
	protected String lastSearch;
	protected ActionListener searchMotsClesAction,searchKNNAction;
	
	public Win()
	{
		this.annotatedTweets = new ArrayList<Tweet>();
		cmTwitter = new CMTwitter();
		this.initializeListener();
		//cmTwitter.setProxy(new Proxy("cache-etu.univ-lille1.fr", 3128));
		System.out.println("1 " + (cmTwitter == null));
		
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		//Paramétrage de la fenetre 
		this.setSize(screen);
		
		this.setTitle("#currentmood");
		//paramétrage du menu
		this.menu = new JMenuBar();
		this.fileMenu = new JMenu("Base de données");
		this.menu.add(this.fileMenu);
		this.openCSVItem = new JMenuItem("Charger la base de données", KeyEvent.VK_O);
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
		
		this.createCSVItem = new JMenuItem("Sauvegarder la base de données", KeyEvent.VK_C);
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
		
		this.annotation = new JMenu("Annotation");
		this.allMotsclesItem= new JMenuItem("Annotés résultats par mots-clés");
		this.allMotsclesItem.addActionListener(searchMotsClesAction);
		this.annotation.add(this.allMotsclesItem);
		this.allKNNItem= new JMenuItem("Tout annoter par KNN");
		this.allKNNItem.addActionListener(searchKNNAction);
		
		this.bayesMenu = new JMenu("Tout annoter par la méthode Bayésienne");
		this.bayesPresMenu = new JMenu("Par présence");
		this.bayesFreqMenu = new JMenu("Par fréquence");
		this.bayesfreqpasmot = new JMenu("Pas < 3");
		this.bayesfreqavecmot = new JMenu("En utilisant tous les mots");
		
		this.bayesfreqavecmotunigramme = new JMenuItem("Unigramme");
		this.bayesfreqavecmotbigramme = new JMenuItem("bigramme");
		
		this.bayesfreqpasmotunigramme = new JMenuItem("Unigramme");
		this.bayesfreqpasmotbigramme = new JMenuItem("bigramme");
		
		this.bayesfreqavecmot.add(this.bayesfreqavecmotunigramme);
		this.bayesfreqavecmot.add(this.bayesfreqavecmotbigramme);
		
		this.bayesFreqMenu.add(this.bayesfreqavecmot);
		this.bayesFreqMenu.add(this.bayesfreqpasmot);
		
		this.bayesMenu.add(this.bayesPresMenu);
		this.bayesMenu.add(this.bayesFreqMenu);
		
		this.annotation.add(this.allKNNItem);
		this.annotation.add(bayesMenu);
		this.menu.add(this.annotation);
		
		
		
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
		
		this.motcleItem = new JMenuItem("Annotation mots clés");
		this.motcleItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Choisissez le fichier des mots positifs");
				
				if(jfc.showSaveDialog((Component) e.getSource()) == JFileChooser.APPROVE_OPTION)
				{
					Win.this.cheminPositif=jfc.getSelectedFile().getAbsolutePath();
				}
				
				jfc.setDialogTitle("Choisissez le fichier des mots negatifs");
				
				if(jfc.showSaveDialog((Component) e.getSource()) == JFileChooser.APPROVE_OPTION)
				{
					Win.this.cheminNegatif=jfc.getSelectedFile().getAbsolutePath();
				}
				
			}
		});
		this.optionMenu.add(motcleItem);
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
				Win.this.lastSearch = Win.this.search.getText();
				
				try{
					List<Status> tweets = getTweets(Win.this.lastSearch);
					for(Status status : tweets)
					{
						TweetUI tw = new TweetUI(status, Win.this.lastSearch);
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
		//this.moodPanel = new MoodPanel();
		this.tweetpanel= new JPanel();
		//this.infopanel.setBackground(new Color(0, 255, 0));
		
		
		
		
		//this.tweetpanel.setBackground(new Color(0, 0, 255));
		this.tweetpanel.setLayout(new BoxLayout(this.tweetpanel, BoxLayout.Y_AXIS));
		this.scrollTweetPanel=new JScrollPane(this.tweetpanel);
		this.scrollTweetPanel.getVerticalScrollBar().setUnitIncrement(20);
		this.lInfo = new JLabel("Nombre de requêtes possible : ");
		this.lInfoNb = new JLabel("___");
		this.lInfoTimeReload = new JLabel("Rechargement dans : ");
		this.lInfoTimeReloadNb = new JLabel("___");
		this.infopanel.add(lInfo);
		this.infopanel.add(lInfoNb);
		this.infopanel.add(lInfoTimeReload);
		this.infopanel.add(lInfoTimeReloadNb);
		this.add(infopanel,BorderLayout.SOUTH);
		///this.add(moodPanel,BorderLayout.EAST);
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
		/*for(Tweet tweet : this.annotatedTweets)
		{
			this.tweetpanel.add(new TweetUI(tweet),BorderLayout.CENTER);
		}
		this.validate();*/
		HashMap[] test= ClassificationBaysienne.getnDeCs(this.annotatedTweets);
		int[] testt = ClassificationBaysienne.getnbMotClasse(this.annotatedTweets);
		System.out.println("mauvais : " +test[0].size());
		System.out.println("neutre : " +test[1].size());
		System.out.println("bon : " +test[2].size());
		
		System.out.println("mauvais : " +testt[0]);
		System.out.println("neutre : " +testt[1]);
		System.out.println("bon : " +testt[2]);
		
	}
	
	private void initializeListener()
	{
		this.searchMotsClesAction = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				annoteAllTweetKey();
				
			}
		};
		
		this.searchKNNAction = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Win.this.annotatedTweets==null)
				{
					JOptionPane.showMessageDialog(Win.this, "La base de tweets n'est pas chargée" , "Erreur lors de l'annotation", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					String search = JOptionPane.showInputDialog(Win.this,"Quelle est votre recherche ?","Indiquez votre recherche",JOptionPane.QUESTION_MESSAGE);
					String choix = JOptionPane.showInputDialog(Win.this, "Indiquez un nombre de plus proches voisins :", "Choix du nombre des plus proches voisins", JOptionPane.QUESTION_MESSAGE);
					int choixint= Integer.parseInt(choix);
					int bad = 0,neutral = 0,good = 0;
					List<Status> tweets;
					try {
						tweets = Win.this.getTweets(search);
						for(Status st :tweets)
						{
							Tweet tw = new Tweet(st, search);
							
							try {
								tw=ClassificationKNN.knnTweet(choixint, tw, Win.this.annotatedTweets);
								if(tw.getValue()==Tweet.BAD)
									bad++;
								else if(tw.getValue()==Tweet.NEUTRAL)
									neutral++;
								else
									good++;
								
								
							} catch (OutOfBoundsException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						WinChart resultat = new WinChart(new PieChart(),bad,neutral,good);
					} catch (TwitterException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NotConnectedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
				
			}
		};
	}

	private List<Status> getTweets(String searchWord) throws TwitterException,NotConnectedException 
	{
		Win.this.cmTwitter.connect();
		List<Status> tweets = Win.this.cmTwitter.searchTweets(searchWord);
		Win.this.refreshLimit(Win.this.cmTwitter.getRateLimit());
		return tweets;
	}

	private void annoteAllTweetKey() {
		if(Win.this.annotatedTweets==null)
		{
			JOptionPane.showMessageDialog(Win.this, "La base de tweets n'est pas chargée" , "Erreur lors de l'annotation", JOptionPane.ERROR_MESSAGE);
		}
		else if(Win.this.cheminNegatif== null ||Win.this.cheminPositif == null)
		{
			JOptionPane.showMessageDialog(Win.this, "Les fichiers de mots-clés ne sont pas chargés", "Erreur lors de l'annotation", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			String search = JOptionPane.showInputDialog(Win.this,"Quelle est votre recherche ?","Indiquez votre recherche",JOptionPane.QUESTION_MESSAGE);
			int bad = 0,neutral = 0,good = 0;
			try 
			{
				List<Status> tweets = Win.this.getTweets(search);
				ClassificationMotCle classifier = new ClassificationMotCle(Win.this.cheminPositif, Win.this.cheminNegatif);
				for(Status st : tweets)
				{
					Tweet tw = new Tweet(st, search);
					tw = classifier.evaluateTweet(tw);
					if(tw.getValue()==Tweet.BAD)
						bad++;
					else if(tw.getValue()==Tweet.NEUTRAL)
						neutral++;
					else
						good++;
					
					
				}
				WinChart resultat = new WinChart(new PieChart(),bad,neutral,good);
			} 
			catch (TwitterException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (NotConnectedException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
