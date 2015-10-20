package currentmood.util.junit;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import currentmood.util.Tweet;
import currentmood.util.classifier.Classification;

public class ClassificationTest {

	@Test
	public void testDistanceTweet()
	{
		Tweet t1 = new Tweet(253604804, "fghrzdiol", "Fatigué de travailler super les vacances arrivent à point", new Date(), "");
		Tweet t2 = new Tweet(458647106, "", "Grève à Air France les vacances commencent mal", new Date(), "");
		Tweet t3 = new Tweet(458647106, "", "Direction soleil pour les vacances super", new Date(), "");
		Tweet t4 = new Tweet(458647106, "", "Je vais devoir travailler pendant les vacances pour rattraper mon retard en PJE", new Date(), "");
		
		assertEquals(((17-6)/17), Classification.distanceTweet(t1, t2),0.0f);
		assertEquals(((15-6)/15), Classification.distanceTweet(t1, t3),0.0f);
		assertEquals(((22-6)/22), Classification.distanceTweet(t1, t4),0.0f);
	}

}
