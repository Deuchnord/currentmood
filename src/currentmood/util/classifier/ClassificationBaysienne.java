package currentmood.util.classifier;

import java.util.HashMap;
import java.util.List;

import currentmood.util.Tweet;

public class ClassificationBaysienne 
{
	/**
	 * @param database La base d'apprentissage
	 * @return Un tableau contenant le ratio pour chaque classe de tweet : 0=negatif, 1=neutre, 2=bon
	 */
	public static double[] getRatioClasse(List<Tweet> database)
	{
		int nbNegatif =0, nbNeutre=0, nbBon=0;
		for(Tweet tw: database)
		{
			if(tw.getValue()==Tweet.BAD)
				nbNegatif++;
			else if(tw.getValue()==Tweet.NEUTRAL)
				nbNeutre++;
			else
				nbBon++;
		}
		double[] listeRatioClasse = new double[3];
		listeRatioClasse[0] = nbNegatif/database.size();
		listeRatioClasse[1] = nbNeutre/database.size();
		listeRatioClasse[2] = nbBon/database.size();
		return listeRatioClasse;
		
	}
	
	/**
	 * @param database La base d'apprentissage
	 * @return le ratio précalculé de l'ensemble des mots de la base d'apprentissage
	 */
	public static int getEstimateurLapalace(List<Tweet> database)
	{
		int laplaceEstimateur = 0;
		for(Tweet tw : database)
		{
			String[] words = tw.getText().split(" ");
			laplaceEstimateur= laplaceEstimateur+words.length;
		}	
		return laplaceEstimateur;
		
	}
	
	public static void getratioclasseword()
	{
		
	}
}
