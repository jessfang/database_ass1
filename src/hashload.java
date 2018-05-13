import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class hashload {
	public static void main(String[] args)
    {
        HashTable ht = new HashTable(10); //change the number later!!!!!!!!!!!!!!!!
        String heapSize = args[0];
        int pageSize = Integer.parseInt(heapSize);
        String fileName = "heap."+heapSize;
        File file = new File(fileName);
        String newFileName = "hash."+heapSize; //Used to write the data to
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
                        ht.insert(tokSplit[2].toLowerCase(), tok);
                    }   
                }
              }  
        } catch (IOException e) {
            e.printStackTrace();
        }
        ht.writeHashTable(newFileName);
    }
}
