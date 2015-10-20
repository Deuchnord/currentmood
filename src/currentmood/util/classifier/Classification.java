package currentmood.util.classifier;

import java.util.ArrayList;
import java.util.List;

import currentmood.util.Tweet;

public class Classification {
	
	public static float distanceTweet(Tweet t1, Tweet t2)
	{
		List<String> motsEnCommun;
		
		//On compte le nombre de mots pour chaque tweet.
		String textTrime = t1.getText().trim();
		String[] motTexteUn =  textTrime.split("\\s+");
		textTrime = t2.getText().trim();
		String[] motTexteDeux = textTrime.split("\\s+");
		int nbTotalMots = motTexteUn.length+motTexteDeux.length;
		motsEnCommun = motsCommun(motTexteUn, motTexteDeux);
		return (nbTotalMots-(motsEnCommun.size()*2))/nbTotalMots;
		
	}
	
	public static List<String> motsCommun(String[] s1, String[] s2)
	{
		List<String> s = new ArrayList<String>();
		
		for(int i = 0; i < s1.length; i++)
		{
			for(int j = 0; j < s2.length; j++)
			{
				if(s1[i].toLowerCase().equals(s2[j].toLowerCase()))
					s.add(s1[i]);
			}
		}
		
		return s;
	}
	
	public static Tweet knnTweet(int k, Tweet tweetAAnnonter, List<Tweet> tweetsAComparer)
	{
		float tempScore = 1.0f;
		Tweet tempTweet = null;
		if(k ==1)
		{
			for(Tweet tweetAComparer : tweetsAComparer )
			{
				float distance = distanceTweet(tweetAComparer,tweetAAnnonter);
				if(distance<tempScore)
				{
					tempScore=distance;
					tempTweet = tweetAComparer;
				}

			}
		}
		
		else if(k == tweetsAComparer.size())
		{
			int compteurMauvais=0, compteurBon=0, compteurNeutre=0;
			for(Tweet tweetAComparer : tweetsAComparer )
			{
				compteurMauvais += (tweetAComparer.getValue() == Tweet.BAD) ? 1 : 0;
				compteurBon += (tweetAComparer.getValue() == Tweet.GOOD) ? 1 : 0;
				compteurNeutre += (tweetAComparer.getValue() == Tweet.NEUTRAL) ? 1 : 0;
			}
			
			tempTweet = annotateTweet(tweetAAnnonter, compteurBon, compteurMauvais, compteurNeutre);
		}
		
		return tempTweet;
	}

	protected static Tweet annotateTweet(Tweet tweet, int compteurBon, int compteurMauvais, int compteurNeutre) {
		Tweet t = tweet;
		
		if(compteurBon > compteurMauvais && compteurBon > compteurNeutre)
			t.setValue(Tweet.GOOD);
		
		else if(compteurMauvais > compteurBon && compteurMauvais > compteurNeutre)
			t.setValue(Tweet.BAD);
		
		else if(compteurNeutre > compteurBon && compteurNeutre > compteurMauvais)
			t.setValue(Tweet.NEUTRAL);
		
		return t;
	}

}
