
public class Chromosome {
	private DataElement[] items;
	private int totalValue;
	private int totalWeight;
	private int capacity;
	private int counter = 0;
	
	public Chromosome(int maxItems, int capacity){
		items = new DataElement[maxItems];
		this.totalValue = 0;
		this.capacity = capacity;
	}
	
	public boolean addItem(DataElement item){
		if(totalWeight + item.getWeight() <= capacity){
			items[counter++] = item;
			totalWeight+= item.getWeight();
			return true;
		}
		return false;
	}
	
	public void setTotalValue(int totalValue){
		this.totalValue = totalValue;
	}
	
	public int getTotalValue(){
		return totalValue;
	}

    public void setTotalWeight(int totalWeight){
        this.totalWeight = totalWeight;
    }

    public int getTotalWeight(){
        return totalWeight;
    }
	
	public DataElement[] getItems(){
		return items;
	}
	
	public String print(){
		String string = "(";
		
		for(DataElement data: items){
			string+="("+data.getWeight()+", "+data.getValue()+")";
		}
		
		return string+")";
	}	
}
