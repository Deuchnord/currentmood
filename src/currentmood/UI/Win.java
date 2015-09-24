package currentmood.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import twitter4j.Status;
import twitter4j.TwitterException;

import currentmood.util.CMTwitter;
import currentmood.util.NotConnectedException;
import currentmood.util.Proxy;

public class Win extends JFrame {
	protected JMenuBar menu;
	protected JMenu fileMenu, aboutMenu, optionMenu;
	protected JMenuItem openCSVItem, createCSVItem, proxyItem ;
	protected JPanel searchpanel, infopanel;
	protected JTextField search;
	protected JButton Btn_search_Ok;
	protected CMTwitter cmTwitter;
	
	public Win()
	{
		cmTwitter = new CMTwitter();
		//cmTwitter.setProxy(new Proxy("cache-etu.univ-lille1.fr", 3128));
		System.out.println("1 " + (cmTwitter == null));
		
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		//Paramétrage de la fenetre 
		this.setSize(screen);
		
		this.setTitle("JframeTest");
		//paramétrage du menu
		this.menu = new JMenuBar();
		this.fileMenu = new JMenu("Fichier");
		this.menu.add(this.fileMenu);
		this.openCSVItem = new JMenuItem("Ouvrir un CSV", KeyEvent.VK_O);
		this.createCSVItem = new JMenuItem("Créer un CSV", KeyEvent.VK_C);
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
					for(Status status : tweets)
					{
						System.out.println("### Tweet from @"+status.getUser().getScreenName()+" ###\n"+status.getText()+"\n\n");
					}
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
		this.infopanel.setBackground(new Color(0, 255, 0));
		this.add(infopanel,BorderLayout.SOUTH);
		
		this.setVisible(true);
		

		
		
		
		
		
		
		
	}

}
