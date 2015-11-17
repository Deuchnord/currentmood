package currentmood.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import currentmood.util.CSVFile;
import currentmood.util.Tweet;

public class saveTweetTest {

	public static void main(String[] args) {
		
		Tweet t = new Tweet(1, "Deuchnord", "Bonjour :D\nCeci est un tweet\navec un retour à la ligne et un lien : http://korben.info/42", new Date(), "korben");
		List<Tweet> lt = new ArrayList<Tweet>();
		lt.add(t);
		CSVFile.washTweets(lt);
		System.out.println(lt.get(0).getText());
		
	}

}
