package currentmood.util.classifier;

import java.util.ArrayList;
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
		double nbNegatif = 0.0, nbNeutre = 0.0, nbBon = 0.0;
		for(Tweet tw: database)
		{
			if(tw.getValue()==Tweet.BAD)
			{
				//System.out.println("BAD");
				nbNegatif++;
			}
			else if(tw.getValue()==Tweet.NEUTRAL)
			{
				//System.out.println("NEU");
				nbNeutre++;
			}
			else
			{
				//System.out.println("GOO");
				nbBon++;
			}
		}
		double[] listeRatioClasse = new double[3];
		listeRatioClasse[0] = nbNegatif/database.size();
		listeRatioClasse[1] = nbNeutre/database.size();
		listeRatioClasse[2] = nbBon/database.size();
		
		//System.out.println("getRatioClasse : NEGATIVE = " + listeRatioClasse[0] + " ; NEUTRAL = " + listeRatioClasse[1] + " ; POSITIVE = " + listeRatioClasse[2]);
		
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
		
		//System.out.println("getEstimateurLaplace : " + laplaceEstimateur);
		
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
		
		//System.out.println("getnDeCs : NEGATIVE = " + nbTotaloccurencedeMotsParClasse[0] + " ; NEUTRAL = " + nbTotaloccurencedeMotsParClasse[1] + " ; POSITIVE = " + nbTotaloccurencedeMotsParClasse[2]);
		
		return nbTotaloccurencedeMotsParClasse;
	}
	
	public static int[] getnbMotClasse(List<Tweet> database)
	{
		HashMap<String, Integer>[] nDeCs = getnDeCs(database);
	
		int[] tabNbMotsClasse = {nDeCs[0].size(), nDeCs[1].size(), nDeCs[2].size()};
		
		//System.out.println("getnbMotClasse : NEGATIVE = " + tabNbMotsClasse[0] + " ; NEUTRAL = " + tabNbMotsClasse[1] + " ; POSITIVE = " + tabNbMotsClasse[2]);
		
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
		double res = (numérateur+1.0)/(nbmotsclassec+laplace);
		
//		System.out.println("mSachantc : " + res);
//		System.out.println(">0 :"+(res>0));
//		System.out.println("numérateur :"+numérateur);
//		System.out.println("nbmotsclassec :"+nbmotsclassec);
//		System.out.println("mot : "+mot+"---->resultat : "+res);
		
		return res;

	}
	
	public static double classeSachantTweet(int classe, Tweet tw,List<Tweet> database, boolean frequence)
	{
		return classeSachantTweet(classe, tw, database, frequence, false, false);
	}
	
	public static double classeSachantTweet(int classe, Tweet tw,List<Tweet> database, boolean frequence, boolean sansMotsCourts, boolean bigrammes)
	{
		double res = 1.0;
		double ratioclasse = getRatioClasse(database)[classe];
		String[] mots = tw.getText().split(" ");
		
		if(sansMotsCourts)
		{
			List<String> tempMots = new ArrayList<String>();
			for(String mot : mots)
			{
				if(mot.length() > 2)
					tempMots.add(mot);
			}
			
			mots = (String[]) tempMots.toArray(new String[tempMots.size()]);
		}
		
		if(bigrammes)
		{
			String[] motsBigrammes = new String[mots.length - 1];
			int i = 0;
			
			for(int j = 0; j < mots.length - 1; j++)
				motsBigrammes[i++] = mots[j] + " " + mots[j + 1];
			
			mots = motsBigrammes;
		}
		
		//for(String mot : mots)
			//System.out.print(mot + " ; ");
		
		if(!frequence)
		{
			for(int i=0; i<mots.length;i++)
			{
				res = res * (mSachantc(mots[i], database, classe) * ratioclasse);
			}
		}
		
		else
		{
			for(int i = 0;i<mots.length;i++)
			{
				double nbmot=0.0;
				for(int j = 0; j<mots.length;j++)
				{
					if(mots[i].equals(mots[j]))
						nbmot++;
				}
				res = res * (Math.pow(mSachantc(mots[i], database, classe),nbmot) * ratioclasse);
			}
		}
		
		//System.out.println("classeSachantTweet : " + res);
		//System.out.println("Ratioclasse : "+ratioclasse);
		
		return res;
		
	}
	
	/**
	 * @param tw Le tweet à évaluer
	 * @param database Labase d'aprentissage
	 * @param frequence vrai si on fait par fréquence, faux par présence
	 * @param sansMotsCourts vrai on enlève les mots courts, faux on les laisse
	 * @param bigrammes, vrai on considère des bigrammes, faux des unigrammes;
	 * @return
	 */
	public static int evaluateTweet(Tweet tw,List<Tweet> database, boolean frequence, boolean sansMotsCourts,boolean bigrammes)
	{
		double probabad = classeSachantTweet(0,tw,database,frequence, sansMotsCourts,bigrammes);
		double probaneutre = classeSachantTweet(1,tw,database,frequence, sansMotsCourts,bigrammes);
		double probagood=classeSachantTweet(2,tw,database,frequence, sansMotsCourts,bigrammes);
		double temp;
		 int res;
		
		if(probabad > probaneutre && probabad > probagood)
		{
			temp=probabad;
			res = Tweet.BAD;
			
		}
		
		else if(probaneutre > probabad && probaneutre > probagood)
		{
			temp=probaneutre;
			res= Tweet.NEUTRAL;
		}
		
		else
		{
			res= Tweet.GOOD;
		}
		
		System.out.println("evaluateTweet : " + res + "(B = "+probabad+" ; N = "+probaneutre+" ; G = "+probagood+")");
		
		return res;
		
		
	}
	
	
	
	
}
