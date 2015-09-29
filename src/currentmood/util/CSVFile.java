package currentmood.util;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import twitter4j.Status;

public class CSVFile {
	
	/**
	 * Reads the content of the file and returns its content.
	 * @return the content of the CSV file. The first list is the lines, the second the cells.
	 * @throws IOException thrown if the file cannot be read, for instance if it does not exist.
	 */
	public static List<List<String>> readCSV(String fileName) throws IOException
	{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
		List<List<String>> returned = new ArrayList<List<String>>(); 
		
		String line = null;
		
		while((line = bufferedReader.readLine()) != null)
		{
			String[] splittedLine = line.split(",");
			List<String> lineAsList = new ArrayList<String>();
			for(String s : splittedLine)
				lineAsList.add(s);
			
			returned.add(lineAsList);
		}
		
		bufferedReader.close();
		
		return returned;
	}
	
	/**
	 * Writes the data given in parameters in a CSV file.
	 * @param data the content of the file. The first list is the lines, the second the cells.
	 * @throws IOException
	 */
	public static void writeCSV(String fileName, List<List<String>> data) throws IOException
	{
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
		
		String fileContent = "";
		
		for(List<String> line : data)
		{
			if(!fileContent.equals(""))
				fileContent += "\n";
			
			String lineString = "";
			for(String cell : line)
			{
				if(!lineString.equals(""))
					lineString += ",";
				
				lineString += cell;
			}
			
			fileContent += lineString;
			
		}
		
		bufferedWriter.write(fileContent);		
		bufferedWriter.close();
	}
	
	public static HashMap<Status, Integer> readTweetsInCSV(String fileName) throws IOException
	{
		List<List<String>> csvContent = readCSV(fileName);
		
		HashMap<Status, Integer> hashTweets = new HashMap<Status, Integer>();
		
		for(List<String> line : csvContent)
		{
			for(String cell : line)
			{
				Status status = new Tweet();
			}
		}
	}

}
