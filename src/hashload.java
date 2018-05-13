
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;

public class hashload {
    public static void main(String[] args)
    {
        HashTable ht = new HashTable(10);
        String heapSize = args[0];
        int pageSize = Integer.parseInt(heapSize);
        String fileName = "heap."+heapSize;
        File file = new File(fileName);

        String newFileName = "hash."+heapSize; //Used to write the data to
       

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
                        String value = i + " "+lineCount;
                        ht.insert(tokSplit[2].toLowerCase(), value);
                    }  
                    lineCount++; 
                }
              }  
        } catch (IOException e) {
            e.printStackTrace();
        }

        ht.writeHashTable(newFileName);
        
        // System.out.println(ht.get("warby wares")); // must turn to lower case
        
    }

    public static class HashLinkedList 
    {
        HashLinkedList nextHash;
        String key;
        String value;

        HashLinkedList(String key, String value) 
        {
            this.nextHash = null;
            this.key = key;
            this.value = value;
        }
    }
     
    /* Class HashTable */
    public static class HashTable
    {
        private int length_table;
        private HashLinkedList[] hashtable;
        private int entryAmount; 
     
         /* Constructor */
        public HashTable(int ts) 
        {
            length_table = ts;
            hashtable = new HashLinkedList[length_table];
            for (int x = 0; x < length_table; x++) {
                hashtable[x] = null;
            }
        } 

        /* Function to retrieve a value from a given key */
        public String get(String key) 
        {
            int hashValue = (hash(key) % length_table);
            // if there is no value with the given key return null
            if (hashtable[hashValue] == null) {
                return null;
            } else {
                HashLinkedList entry = hashtable[hashValue];
                // if value is not null and it does not match the search key then keep searching
                while (entry != null && !entry.key.equals(key)) {
                    entry = entry.nextHash;
                }
                // check if entry has been found or not
                if (entry == null) {
                    return null;
                } else {
                    return entry.value;
                }
            }
        }
        /* Function to insert an entry */
        public void insert(String key, String value) 
        {

            int hashValue = (hash(key) % length_table);
            System.out.println("key: "+key+" hashValue: "+hashValue);
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
        /* A function to create a hash from a given string */
        private int hash(String key)
        {
            int hashVal = key.hashCode( );
            hashVal = hashVal % length_table;
            if (hashVal < 0)
                hashVal += length_table;
            return hashVal;
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
                // writer.print((i)+" ");
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

