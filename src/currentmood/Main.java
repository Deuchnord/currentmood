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
		
		//Win win = new Win(); 
		
		CMTwitter cmTwitter = new CMTwitter();
		cmTwitter.setProxy(new Proxy("cache-etu.univ-lille1.fr", 3128));
		
		try {
			cmTwitter.connect();
			List<Status> tweets = cmTwitter.searchTweets("SundayRandomSentence");
			for(Status status : tweets)
			{
				System.out.println("### Tweet from @"+status.getUser().getScreenName()+" ###\n"+status.getText()+"\n\n");
			}
			
		} catch (TwitterException e) {
			System.out.println("Cannot connect: " + e.getMessage());
		} catch (NotConnectedException e) {
			System.out.println("You must be connected to perform this action!");
		}
		
	}

}
