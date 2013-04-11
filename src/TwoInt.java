/*
 * Class that holds the key for the HashMap.
 * Two integers are held in this class,
 * lowest of the two is first.
 *
 */

public class TwoInt
{
    private int intOne;
    private int intTwo;

    public TwoInt(int one, int two)
    {
        intOne = one;
        intTwo = two;
    }

    public int getIntOne()
    {
        return intOne;
    }

    public int getIntTwo()
    {
        return intTwo;
    }

    public boolean compareIntOne(int one)
    {
        return (intOne == one);
    }

    public boolean compareIntTwo(int two)
    {
        return (intTwo == two);
    }

}
