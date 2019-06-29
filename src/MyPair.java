public class MyPair
{
    private final int key;
    private final int value;

    public MyPair(int aKey, int aValue)
    {
        key   = aKey;
        value = aValue;
    }

    public int key()   { return key; }
    public int value() { return value; }
}