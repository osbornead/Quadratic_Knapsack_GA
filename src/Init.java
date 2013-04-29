import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Init {

	public static void main(String[] args){
        String filename = args[0];
		int popSize = Integer.parseInt(args[1]);
        String relationsFilename = args[2];
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
			
            while((line = itemsReader.readLine()).contains("#")) ;

            // read first line to get number of items
			int numItems = Integer.parseInt(line);

			// Create Item array 
			DataElement[] items = new DataElement[numItems];

            //read second line to get knapsack capacity.
            int knapsackCapacity = Integer.parseInt(itemsReader.readLine());

			int itemCounter = 0;
            int IDCounter = 0;
			while(itemCounter < numItems){
                
                line = itemsReader.readLine();
				String[] splitLine = line.split("[\\s,]+");

                // Assume line is entered value, weight
                // ID is order in the file. 
				items[itemCounter++] = new DataElement(Integer.parseInt(splitLine[1]), 
                                            Integer.parseInt(splitLine[0]), IDCounter++);
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

            for(int i = 0; i < numRelations; i++)
            {
                rLine = relationsReader.readLine();

                // Assume line is entered 3,4,10
                // 3 = first number in key
                // 4 = second number in key
                // 10 = value of pair
                String[] split = rLine.split("[,]");
                TwoInt key = new TwoInt(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                Integer value = Integer.parseInt(split[2]);

                relations.put(key, value);
            }


			Algorithm algo = new Algorithm(items, relations, knapsackCapacity, popSize);
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
