package currentmood.util.classifier;

import currentmood.util.Tweet;

public class Classification {
	
	public static float distanceTweet(Tweet t1, Tweet t2)
	{
		//On compte le nombre de mots pour chaque tweet.
		String textTrime = t1.getText().trim();
		int nbMotTexteUn = textTrime.isEmpty() ? 0 : textTrime.split("\\s+").length;
		 textTrime = t2.getText().trim();
		int nbMotTexteDeux =textTrime.isEmpty() ? 0 : textTrime.split("\\s+").length;
		return 0.0F;
	}

}
