import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class hashload {
	public static void main(String[] args)
    {
        HashTable ht = new HashTable(10); //change the number later!!!!!!!!!!!!!!!!
//        String heapSize = args[0];
        String heapSize = "10000";
        int pageSize = Integer.parseInt(heapSize);
        String fileName = "heap."+heapSize;
        File file = new File(fileName);
        String newFileName = "hash."+heapSize; //Used to write the data to
        // a regex which can find the specific word (not case sensitive)
        // open the file to read
        int lineCount = 0;
        // a regex which can find the specific word (not case sensitive)
        // open the file to read
        try (RandomAccessFile data = new RandomAccessFile(file, "r")) {
              byte[] page = new byte[pageSize];
              // read the specific pageSize one at a time
              for (long i = 0, len = data.length() / pageSize; i < len; i++) {
                data.readFully(page);
                String result = new String(page);
                // split by record
                String [] split = result.split("#");
                for(String tok : split) {
                    // split by word
                    String[] tokSplit = tok.split("\t");
                    if(tokSplit.length > 2) {
                        String value = i + "-"+lineCount;
                        ht.insert(tokSplit[2].toLowerCase(), value);
                    }  
                    lineCount++; 
                }
              }  
        } catch (IOException e) {
            e.printStackTrace();
        }
        ht.writeHashTable(newFileName);
    }

	public static class HashTable {
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
	    
	    /* Function to print hash hashtable */
	    public void writeHashTable(String newFileName)
	    {
	       System.out.println("print hashTable");
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
	           writer.print((i + 1)+" ");
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

}
