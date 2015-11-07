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
	
	/**
	 * @param database la base d'apprentisage
	 * @return La liste pour chaque mot apparant le nombre de fois où il apparait selon les classes
	 */
	public static HashMap<String, Integer>[] getnDeCs(List<Tweet> database)
	{
		HashMap<String, Integer>[] nbTotaloccurencedeMotsParClasse = new HashMap[3];
		nbTotaloccurencedeMotsParClasse[0]= new HashMap<String, Integer>();
		nbTotaloccurencedeMotsParClasse[1]= new HashMap<String, Integer>();
		nbTotaloccurencedeMotsParClasse[2]= new HashMap<String, Integer>();
		int valuetweet;
		int indiceclass=0;
		for(Tweet tw : database)
		{
			valuetweet = tw.getValue();
			String[] mots = tw.getText().split(" ");
			if(valuetweet==Tweet.BAD)
				indiceclass=0;
			else if(valuetweet==Tweet.NEUTRAL)
				indiceclass=1;
			else if(valuetweet==Tweet.GOOD)
				indiceclass=2;
			for(int i=0; i<mots.length; i++)
			{
				if(!nbTotaloccurencedeMotsParClasse[indiceclass].containsKey(mots[i]))
				{
					nbTotaloccurencedeMotsParClasse[indiceclass].put(mots[i], 1);
				}
				else
				{
					nbTotaloccurencedeMotsParClasse[indiceclass].put(mots[i], nbTotaloccurencedeMotsParClasse[indiceclass].get(mots[i]) + 1);
				}
			}
			
			
		}
		return nbTotaloccurencedeMotsParClasse;
	}
	
	public static int[] getnbMotClasse(List<Tweet> database)
	{
		int[] tabNbMotsClasse =new int[3];
		int valuetweet;
		int indiceclass=0;
		for(Tweet tw : database)
		{
			valuetweet = tw.getValue();
			String[] mots = tw.getText().split(" ");
			if(valuetweet==Tweet.BAD)
				indiceclass=0;
			else if(valuetweet==Tweet.NEUTRAL)
				indiceclass=1;
			else if(valuetweet==Tweet.GOOD)
				indiceclass=2;
			for(int i=0; i<mots.length; i++)
			{
				tabNbMotsClasse[indiceclass]++;
			}
		}
		return tabNbMotsClasse;
		
	}
	
	public static double mSachantc(String mot,List<Tweet> database, int classe)
	{
		//On récupére la liste des occurences de mot.
		HashMap<String, Integer> occurences = ClassificationBaysienne.getnDeCs(database)[classe];
		//On récupère le nombre de mot de la classe C
		int nbmotsclassec = ClassificationBaysienne.getnbMotClasse(database)[classe];
		//On récupère le nombre de laplace
		int laplace = ClassificationBaysienne.getEstimateurLapalace(database);
		//On fait le calcul
		int numérateur = 0;
		if(occurences.containsKey(mot))
		{
			numérateur = occurences.get(mot);
		}
		double res = (numérateur+1)/(nbmotsclassec+laplace);
		return res;

	}
	
	public static double classeSachantTweet(int classe, Tweet tw,List<Tweet> database)
	{
		double res = 1.0;
		double ratioclasse = getRatioClasse(database)[classe];
		String[] mots = tw.getText().split(" ");
		for(int i=0; i<mots.length;i++)
		{
			res = res*(ClassificationBaysienne.mSachantc(mots[i], database, classe)*ratioclasse);
		}
		return res;
		
	}
	
	public static int evaluateTweet(Tweet tw,List<Tweet> database)
	{
		double probabad = classeSachantTweet(0,tw,database);
		double probaneutre = classeSachantTweet(1,tw,database);
		double probagood=classeSachantTweet(2,tw,database);
		double temp;
		 int res;
		
		if(probabad>probaneutre)
		{
			temp=probabad;
			res = Tweet.BAD;
			
		}
		else
		{
			temp=probaneutre;
			res= Tweet.NEUTRAL;
		}
		if(temp<probagood)
		{
			res= Tweet.GOOD;
		}
		return res;
		
		
	}
	
	
	
	
}
