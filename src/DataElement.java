
public class DataElement {
	private int value;
	private int weight;
    private int ID;
	
	public DataElement(int weight, int value, int ID){
		this.value = value;
		this.weight = weight;
        this.ID = ID;
	}
	
	public int getValue(){
		return value;
	}
	public int getWeight(){
		return weight;
	}

    public int getID(){
        return ID;
    }
}
