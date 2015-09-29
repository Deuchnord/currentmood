package currentmood.util;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVFile {
	
	protected String fileName;
	
	/**
	 * CSV file constructor.
	 * @param name the name of the file to read or create
	 * @param mode the file opening mode.
	 */
	public CSVFile(String name)
	{
		this.fileName = name;
	}
	
	
	/**
	 * Reads the content of the file and returns its content.
	 * @return the content of the CSV file. The first list is the lines, the second the cells.
	 * @throws IOException thrown if the file cannot be read, for instance if it does not exist.
	 */
	public List<List<String>> readCSV() throws IOException
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
	public void writeCSV(List<List<String>> data) throws IOException
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

}
