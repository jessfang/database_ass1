
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

public class hashquery
{
	 public static void main(String[] args)
    {
    	long startTime = System.currentTimeMillis();
    	HashTable ht = new HashTable(1);
    	String heapSize=null;
    	String query="";
    	int pageSize=0;
    	for(int i = 0; i < args.length-1; i++){
    		String temp = args[i];
    		if(i != args.length-2) {
				query+=temp+" ";
			} else {
				query+=temp;
			}
    	}
    	query = query.toLowerCase();
    	System.out.println(query);
    	try{
    		heapSize = args[args.length-1];
			pageSize = Integer.parseInt(heapSize);
			System.out.println("heap size: "+heapSize);
		} catch(NumberFormatException ex){
			System.out.println("Must input page size");
			return;
		}
        String fileName = "hash."+heapSize;
        File file = new File(fileName);
        int lineCount=0;
        int hashValue = ht.hash(query);
        System.out.println(hashValue);
        BufferedReader br = null;
        try{
	    	br = new BufferedReader(new FileReader(fileName));
	    } catch(FileNotFoundException e) {
	    	e.printStackTrace();
	    	return;
	    }
	    for(int i=0;i<hashValue;i++){
	    	try {
				br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();	
			}
	    }
	    String line = null;
	    try {
				line = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();	
			}
	    System.out.println(line);
	    String[] split = line.split(" ");
	    String pageNum = null;
	    String pageLine = null;
	    System.out.println(split.length);
	    String value = null;
	    for(int x=0;x<split.length/2;x++) {
	    	pageNum = split[x];
	    	pageLine = split[x+1];
	    	System.out.println(pageNum);
	    	System.out.println(pageLine);

	    	String fName = "heap."+heapSize;
        	File f = new File(fName);
	    	try (RandomAccessFile data = new RandomAccessFile(f, "r")) {
	    		int pageSkip = Integer.parseInt(pageNum)*pageSize;
	    		data.skipBytes(pageSkip);
			     byte[] page = new byte[pageSize];
			      // read the specific pageSize one at a time
			      for (long i = Integer.parseInt(pageNum), len = data.length() / pageSize; i < len; i++) {
			        data.readFully(page);
			        String result = new String(page);
			        // split by record
			        String [] splitRecord = result.split("#");
			        for(String tok : splitRecord) {
			        	// split by word
			        	String[] tokSplit = tok.split("\t");
			        	for(String innerTok : tokSplit){
			        		// System.out.println(innerTok);
				        	if(innerTok.toLowerCase().contains(query.toLowerCase())) {
				        		// System.out.println("found: "+tok);
				        		value = tok;
				        		break;
				        	}
			        	}
			        }
			      }  
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(value != null) {
				break;		
			}
	    }
	    if(value == null) {
	    	System.out.println("Text was not found");
	    } else {
	    	System.out.println("Value: "+value);	
	    }
	    long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time Taken(ms):"+estimatedTime+"ms");
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

        //  /* Function to print hash hashtable */
        //  public void writeHashTable(String newFileName)
        //  {
        //     System.out.println("print hashTable");
        //     PrintWriter writer=null;
        //     try {
        //      writer = new PrintWriter(newFileName, "UTF-8");
        //     }
        //     catch (FileNotFoundException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        //     } catch (UnsupportedEncodingException e) {
        //         // TODO Auto-generated catch block
        //         e.printStackTrace();
        //     }
        //     for (int i = 0; i < length_table; i++)
        //     {
        //         writer.print((i + 1)+" ");
        //         HashLinkedList entry = hashtable[i];
        //         while (entry != null)
        //         {
        //             writer.print(entry.value +" ");
        //             entry = entry.nextHash;
        //         }            
        //         writer.println();
        //     }
        //     writer.close();
        // }
    }
}
