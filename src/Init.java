import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Init {

	public static void main(String[] args){
		String filename = args[0];
		int knapsackCapacity = Integer.parseInt(args[1]);
		int popSize = Integer.parseInt(args[2]);
        String relationsFilename = args[3];
		HashMap<TwoInt, Integer> relations;

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
			
            /*******************
            * SET UP RELATIONS
            ********************/
            relationsFile = new FileReader(relationsFilename);
            BufferedReader relationsReader = new BufferedReader(relationsFile);
            String rLine;
            //Read first line to get number of relations.
            int numRelations = Integer.parseInt(relationsReader.readLine());
            // Create relations hashmap
            relations = new HashMap<TwoInt, Integer>(numRelations);

            while((rLine = relationsReader.readLine()) != null)
            {
                // Assume line is entered 3,4,10
                // 3 = first number in key
                // 4 = second number in key
                // 10 = value of pair
                String[] split = line.split(",");
                TwoInt key = new TwoInt(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                Integer value = Integer.parseInt(split[2]);

                relations.put(key, value);
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
