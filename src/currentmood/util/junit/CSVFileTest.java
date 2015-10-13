package currentmood.util.junit;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import currentmood.util.CSVFile;
import currentmood.util.Tweet;

public class CSVFileTest {

	@Test
	public void testWashTweets() {
		List<Tweet> list = new ArrayList<Tweet>();
		Tweet t = new Tweet(1, "User1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent fringilla venenatis faucibus. Sed at odio sed turpis ullamcorper volutpat.", new Date(2015, 10, 12), "lorem ipsum");
		list.add(t);
		
		t = new Tweet(2, "User2", "RT @User1: Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent fringilla venenatis faucibus. Sed at odio sed turpis ullamcorper...", new Date(2015, 10, 12), "lorem ipsum");
		list.add(t);
		
		t = new Tweet(3, "User1", "@User2 stop RTing me", new Date(2015, 10, 13), "lorem ipsum");
		list.add(t);
		
		t = new Tweet(4, "User1", "I love #hashtags!", new Date(2015, 10, 13), "lorem ipsum");
		list.add(t);
		
		t = new Tweet(5, "User2", "This tweet is so strange , with all these spaces     !", new Date(2015, 10, 13), "lorem ipsum");
		list.add(t);
		
		t = new Tweet(6, "User1", "Today I sold a t-shirt at $100.", new Date(2015, 10, 13), "lorem ipsum");
		list.add(t);
		
		t = new Tweet(7, "User2", "@User1 srsly? That 100% too much!", new Date(2015, 10, 13), "lorem ipsum");
		list.add(t);
		
		t = new Tweet(8, "User1", "For those who can't make a simple search on Google: http://lmgtfy.com/?s=search+on+Google", new Date(2015, 10, 13), "lorem ipsum");
		list.add(t);
		
		t = new Tweet(9, "User2", "Because I'm happyyyyyyyyyyyyyyyyyyyyyyyyyyyy!!!!! :D", new Date(2015, 10, 13), "lorem ipsum");
		list.add(t);
		
		t = new Tweet(10, "User1", "@User1 shut up! life is so unfair :'(", new Date(2015, 10, 13), "lorem ipsum");
		list.add(t);
		
		t = new Tweet(11, "User2", "                          ", new Date(2015, 10, 13), "lorem ipsum");
		list.add(t);
		
		List<Tweet> listWashed = CSVFile.washTweets(list);
		
		assertEquals(9, listWashed.size());
		
		//////////////////////////////////////////////////////////////////////////////////////////////////
		
		Tweet tweet = listWashed.get(0);
		assertEquals(list.get(0), tweet);
		
		tweet = listWashed.get(1);
		assertEquals(" @  stop RTing me", tweet.getText());
		
		tweet = listWashed.get(2);
		assertEquals("I love  #! ", tweet.getText());
		
		tweet = listWashed.get(3);
		assertEquals("This tweet is so strange[[[VIRGULE__HERE]]] with all these spaces! ", tweet.getText());
		
		tweet = listWashed.get(4);
		assertEquals("Today I sold a t-shirt at $XX. ", tweet.getText());
		
		tweet = listWashed.get(5);
		assertEquals(" @  srsly? That XX% too much! ", tweet.getText());
		
		tweet = listWashed.get(6);
		assertEquals("For those who can't make a simple search on Google:  ", tweet.getText());
		
		tweet = listWashed.get(7);
		assertEquals("Because I'm happyyyyyyyyyyyyyyyyyyyyyyyyyyyy!!!!!  ", tweet.getText());
		
		tweet = listWashed.get(8);
		assertEquals(" @  shut up! life is so unfair  ", tweet.getText());
	}

}
