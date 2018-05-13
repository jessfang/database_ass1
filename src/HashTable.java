
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
    
    /* Function to insert an entry */
    public void insert(String key, String value) 
    {
        int hashValue = (hash(key) % length_table);
        // if there are no entries in this bucket yet add a new linked list
        if (hashtable[hashValue] == null)
            hashtable[hashValue] = new HashLinkedList(key, value);
        // if there are entries in the bucket add to the end of the linked list
        else 
        {
            HashLinkedList entry = hashtable[hashValue];
            // iterate through the linked list 
            while (entry.nextHash != null && !entry.key.equals(key)) {
                entry = entry.nextHash;
            }
            entry.nextHash = new HashLinkedList(key, value);
        }
    }
}
