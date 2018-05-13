import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

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
    int hash(String key)
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
    
    /* Function to print hash hashtable */
    public void writeHashTable(String newFileName)
    {
       PrintWriter writer=null;
       try {
        writer = new PrintWriter(newFileName, "UTF-8");
       }
       catch (FileNotFoundException e) {
       // TODO Auto-generated catch block
       e.printStackTrace();
       } catch (UnsupportedEncodingException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       for (int i = 0; i < length_table; i++)
       {
           HashLinkedList entry = hashtable[i];
           while (entry != null)
           {
               writer.print(entry.value +" ");
               entry = entry.nextHash;
           }            
           writer.println();
       }
       writer.close();
   }
}
