package currentmood;

import java.util.List;

import twitter4j.Status;
import twitter4j.TwitterException;
import currentmood.UI.*;
import currentmood.util.CMTwitter;
import currentmood.util.NotConnectedException;
import currentmood.util.Proxy;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Win win = new Win(); 
		//WinProxy winp= new WinProxy();
		
		CMTwitter cmTwitter = new CMTwitter();
		cmTwitter.setProxy(new Proxy("cache-etu.univ-lille1.fr", 3128));
	
		
	}

}
