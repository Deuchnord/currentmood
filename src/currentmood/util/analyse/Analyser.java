package currentmood.util.analyse;

import java.io.IOException;
import java.util.List;

import currentmood.util.Tweet;
import currentmood.util.classifier.ClassificationBaysienne;
import currentmood.util.classifier.ClassificationKNN;
import currentmood.util.classifier.ClassificationMotCle;
import currentmood.util.classifier.OutOfBoundsException;

public class Analyser {
	protected List<Tweet> database;
	protected List<Tweet> part1;
	protected List<Tweet> part2;
	protected List<Tweet> part3;
	
	public Analyser(List<Tweet> base)
	{
		this.database = base;
		this.part1 = database.subList(0,(database.size()/3)-1);
		this.part2 = database.subList((database.size()/3), (2*database.size()/3)-1);
		this.part3 = database.subList((2*database.size()/3), database.size());
	}
	
	public int motclesAnalyserPart(List<Tweet> emptyBase, List<Tweet> learningBaseOne,List<Tweet>learningBaseTwo,String pos, String neg) throws IOException
	{
		int error=0;
		List<Tweet> temp = emptyBase;
		List<Tweet> base = learningBaseOne;
		base.addAll(learningBaseTwo);
		for(Tweet tw : temp)
			tw.setValue(-1);
		ClassificationMotCle classifier = new ClassificationMotCle(pos, neg);
		for(int i=0; i<temp.size(); i++)
		{
			Tweet evaluate = classifier.evaluateTweet(temp.get(i));
			if(evaluate.getValue()!=temp.get(i).getValue())
				error++;
		}
		return error;
	}
	
	public int motclesAnalyser(String pos, String neg) throws IOException
	{
		int errorun = this.motclesAnalyserPart(this.part1, this.part2, this.part3, pos, neg);
		int errordeux = this.motclesAnalyserPart(this.part2, this.part1, this.part3, pos, neg);
		int errortrois=this.motclesAnalyserPart(this.part3, this.part2, this.part1, pos, neg);
		return((errorun+errordeux+errortrois)/3);
	}
	
	public int knnAnalyserpart(List<Tweet> emptyBase, List<Tweet> learningBaseOne,List<Tweet>learningBaseTwo,int k) throws OutOfBoundsException
	{
		int error=0;
		List<Tweet> temp = emptyBase;
		List<Tweet> base = learningBaseOne;
		base.addAll(learningBaseTwo);
		for(int i =0; i<temp.size(); i++)
			temp.get(i).setValue(-1);
		for(int i=0; i<temp.size(); i++)
		{
			Tweet evaluate =ClassificationKNN.knnTweet(k, temp.get(i), base);
			if(evaluate.getValue()!=temp.get(i).getValue())
				error++;
		}
		return error;
	}
	
	public int knnAnalyser(int k) throws OutOfBoundsException
	{
		int errorun =this.knnAnalyserpart(this.part1, this.part2, this.part3, k);
		int errordeux= this.knnAnalyserpart(this.part2, this.part1, this.part3, k);
		int errortrois=this.knnAnalyserpart(this.part3, this.part1, this.part2, k);
		return((errorun+errordeux+errortrois)/3);
	}
	
	public int bayesAnalyserpart(List<Tweet> emptyBase, List<Tweet> learningBaseOne,List<Tweet>learningBaseTwo,boolean frequence,boolean sansMotsCourts,boolean bigrammes)
	{
		int error=0;
		List<Tweet> temp = emptyBase;
		List<Tweet> base = learningBaseOne;
		base.addAll(learningBaseTwo);
		for(Tweet tw : temp)
			tw.setValue(-1);
		for(int i=0; i<temp.size(); i++)
		{
			int evaluate =ClassificationBaysienne.evaluateTweet(temp.get(i), base, frequence, sansMotsCourts, bigrammes);
			if(evaluate!=temp.get(i).getValue())
				error++;
		}
		return error;
	}
	
	public int bayesAnalyser(boolean frequence,boolean sansMotsCourts,boolean bigrammes)
	{
		int errorun =this.bayesAnalyserpart(this.part1, this.part2, this.part3, frequence,sansMotsCourts,bigrammes);
		int errordeux= this.bayesAnalyserpart(this.part2, this.part1, this.part3, frequence,sansMotsCourts,bigrammes);
		int errortrois=this.bayesAnalyserpart(this.part3, this.part1, this.part2, frequence,sansMotsCourts,bigrammes);
		return((errorun+errordeux+errortrois)/3);
	}
	
	
	

}
