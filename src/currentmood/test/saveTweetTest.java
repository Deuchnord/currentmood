package currentmood.test;

import java.util.List;
import java.io.IOException;
import java.util.Date;
import currentmood.util.CSVFile;
import currentmood.util.Tweet;
import currentmood.util.classifier.ClassificationBaysienne;

public class saveTweetTest {

	public static void main(String[] args) throws IOException {
		
		Tweet t = new Tweet(1, "Deuchnord", "Bonjour :D\nCeci est un tweet\navec un retour à la ligne et un lien : http://korben.info/42", new Date(), "korben");
		List<Tweet> lt = CSVFile.readTweetsInCSV("/home/m1/tanghe/workspace/currentmood/tweets.csv");
		CSVFile.washTweet(t);
		ClassificationBaysienne.classeSachantTweet(0, t, lt, true, true);
		
	}

}
