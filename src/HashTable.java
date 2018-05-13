
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
}
