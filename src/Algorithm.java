import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;


public class Algorithm {
	DataElement[] allItems;
    HashMap<TwoInt, Integer> relations;
	int maxItems;
	int knapsackCapacity;
	Chromosome[] population;
	int popSize;
	private int genCount = 0;
	Random random = new Random();
	
	public Algorithm(DataElement[] allItems, HashMap<TwoInt, Integer> relations, int knapsackCapacity, int popSize){
		this.allItems = allItems;
        this.relations = relations;
		this.knapsackCapacity = knapsackCapacity;
		this.maxItems = allItems.length;
		this.popSize = popSize;
		population = new Chromosome[popSize];
	}
	
	public void start(){
		populate();
        while(population[0].getTotalValue() != population[popSize-1].getTotalValue()){
			insertIntoPop(crossover(pickParents())[0]);
			genCount++;
		}

        System.out.println("Best: \n"+"Weight: "+population[0].getTotalWeight()+"\n Value: "+
        population[0].getTotalValue()+"\n Gen: "+genCount);
	}
	
	public void populate(){
        ArrayList<Integer> availableData= new ArrayList<Integer>();
		int i =0;
		int index;

		while(i<popSize){
			Chromosome child = new Chromosome(maxItems,knapsackCapacity);
			for(int j=0; j<maxItems;j++){
				availableData.add(new Integer(j));
			}
			
            int j = 0;
			while(j < maxItems){
                index = random.nextInt(availableData.size());
                index = availableData.remove(index);
				DataElement d = allItems[index];
				child.addItem(d);
			    
                j++;
            }
			insertIntoPop(child);
            
            System.out.println(population[i]);
			
            i++;
		}
	}
	
	/* pickParents
	 *  - picks the parents to be mated.  Should be skewed towards picking
	 *    the more fit members of the population.
	 */
	public Chromosome[] pickParents(){
		Chromosome[] parents = new Chromosome[2];
        
        // parent 1 from first 10 of population.
        parents[0] = population[random.nextInt(10)];
        // parent 2 from first half of population
        parents[1] = population[random.nextInt(popSize/2)];
		
		return parents;	
	}
	
	
	public Chromosome[] crossover(Chromosome[] parents){
		Chromosome[] children = new Chromosome[1];
        Chromosome child = new Chromosome(maxItems, knapsackCapacity);
		
        DataElement[] parent1 = parents[0].getItems();
        DataElement[] parent2 = parents[1].getItems();

        int length = parents[0].getLength();
        int length2 =  parents[1].getLength();
        int p1, p2;
        int i = 0;

        while(i<length && child.getTotalWeight() < knapsackCapacity)
        {
            if(i%2 == 0)
            {
                p1 = random.nextInt(length);

                while(contains(child,parent1[p1]))
                {
                    p1 = random.nextInt(length);
                }
                
                child.addItem(parent1[p1]);
            }

            else
            {
                p2 = random.nextInt(length2);

                while(contains(child,parent2[p2]))
                {
                    p2 = random.nextInt(length2);
                }
            
                child.addItem(parent2[p2]);
            }

            i++;

            findAndSetChildWeight(child);
        }
        
        child = mutate(child);    
        children[0] = child; 

		return children;
	}
	
	public boolean contains(Chromosome child, DataElement item)
    {
        DataElement[] children = child.getItems();

        if(children[0] == null) return false;

        for(int i=0;i<child.getLength();i++)
        {
            if(children[i].getID() == item.getID()) return true;
        }

        return false;
    }
    
    
    public Chromosome mutate(Chromosome victim){
		Chromosome newChromosome = new Chromosome(maxItems, knapsackCapacity);
        
        if(random.nextFloat() <= 0.01)
        {
            DataElement[] child = victim.getItems();
            int pos = random.nextInt(victim.getLength());
            int removedID = child[pos].getID();
            int weight = victim.getTotalWeight() - child[pos].getWeight();
            boolean done = false;
        
            while(!done)
            {
                int elem = random.nextInt(maxItems-1);
                if(!contains(victim, allItems[elem]) && (allItems[elem].getID() != removedID) &&
                    (weight+allItems[elem].getWeight()) < knapsackCapacity)
                {
                    child[pos] = allItems[elem];
                    done = true;
                }
            }

            for(int i = 0; i < victim.getLength(); i++)
            {
                newChromosome.addItem(child[i]);
            }
            findAndSetChildScore(newChromosome);
            findAndSetChildWeight(newChromosome);
        }
        else
        {
            newChromosome = victim;
        }
            
		
		return newChromosome;	
	}
	
	public void insertIntoPop(Chromosome child){
		int index = popSize-1;
		findAndSetChildScore(child);
		System.out.println("Inserting into pop: "+child.getTotalValue());
		if(population[0] == null) population[0] = child;
        else
        {
            while(index >= 0 && (population[index] == null || child.getTotalValue() >= population[index].getTotalValue())){
			    if(index != popSize-1){
				    population[index+1] = population[index];
			    }
	            index--;
            }
	    	if(population[0] != null && child.getTotalValue() > population[0].getTotalValue()){
		    	System.out.println("New Best: "+child.getTotalWeight()+"\n Score: "+child.getTotalValue()+"\n Gen: "+genCount);
		        population[0] = child;
            }
		    if(index+1 < popSize && index > 0)
            {
                if((population[index].getTotalWeight() != child.getTotalWeight()) && 
                (population[index].getTotalValue() != child.getTotalValue()))
                {
			        population[index+1] = child;
                }
            }
        }
	}
	

    public void findAndSetChildWeight(Chromosome child){
        DataElement[] values = child.getItems();
        int weight = 0;
        
        if(values[0] == null) child.setTotalWeight(0);

        for(int i=0; i < child.getLength(); i++)
        {
            weight += values[i].getWeight();
        }

        child.setTotalWeight(weight);
    }



	public void findAndSetChildScore(Chromosome child){
		// finds the total value by looking up all the relations and such
		// calls setScore on the child

        DataElement[] values = child.getItems();
        int length = child.getLength();
        TwoInt key;
        int value = 0;
        
        for(int i=0;i<length;i++)
        {
            value += values[i].getValue();
        }

        for(int l=0;l<length-1;l++)
        {
            for(int j=l+1;j<length;j++)
            {
                if(values[l].getValue() < values[j].getValue())
                {
                    key = new TwoInt(values[l].getValue(), values[j].getValue());
                }
                else
                {
                    key = new TwoInt(values[j].getValue(), values[l].getValue());
                }

                if(relations.containsKey(key))
                {
                    value -= values[l].getValue() + values[j].getValue();
                    value += relations.get(key);
                }
            }
        }

        child.setTotalValue(value);

	}
}
