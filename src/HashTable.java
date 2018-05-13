
public class HashTable {
	private int length_table;
    private HashLinkedList[] hashtable;
 
    public HashTable(int size) 
    {
        length_table = size;
        hashtable = new HashLinkedList[length_table];
        for (int x = 0; x < length_table; x++) {
            hashtable[x] = null;
        }
    }
    
    /* A function to create a hash from a given string */
    private int hash(String key)
    {
        int hashVal = key.hashCode();
        hashVal = hashVal % length_table;
        // in case hashVal is less than 0 which would cause an error add table length
        if (hashVal < 0)
            hashVal = hashVal + length_table;
        return hashVal;
    }
}
