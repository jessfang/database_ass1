
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
        HashTable ht = new HashTable(2000000);
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
        try{
            heapSize = args[args.length-1];
            pageSize = Integer.parseInt(heapSize);
        } catch(NumberFormatException ex){
            System.out.println("Must input page size");
            return;
        }
        String fileName = "hash."+heapSize;
        File file = new File(fileName);
        int lineCount=0;
        int hashValue = ht.hash(query);
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
        String[] split = line.split(" ");
        String pageNum = null;
        String pageLine = null;
        String value = null;
        for(int x=0;x<split.length/2;x++) {
            pageNum = split[x];
            pageLine = split[x+1];
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
                            if(innerTok.toLowerCase().contains(query.toLowerCase())) {
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
}