import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Init {
	public static void main(String[] args){
		String filename = args[0];
		int knapsackCapacity = Integer.parseInt(args[1]);
		int popSize = Integer.parseInt(args[2]);
		
		FileReader itemsFile;
		FileReader relationsFile;
		try {
			/**************
			* SETUP ITEMS
			***************/
			itemsFile = new FileReader(filename);
			BufferedReader itemsReader = new BufferedReader(itemsFile);
			
			String line;
			// read first line to get number of items
			int numItems = Integer.parseInt(itemsReader.readLine());
			// Create Item array 
			DataElement[] items = new DataElement[numItems];
			
			int itemCounter = 0;
			while((line = itemsReader.readLine()) != null){
				String[] splitLine = line.split(",");
				items[itemCounter++] = new DataElement(Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[1]));
			}
			
			Algorithm algo = new Algorithm(items, knapsackCapacity, popSize);
			algo.start();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: File not found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: Error while reading file");
		}
	}
}
