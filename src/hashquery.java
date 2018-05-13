import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class hashquery {
	public static void main(String[] args)
    {
		long startTime = System.currentTimeMillis();
    	HashTable ht = new HashTable(10);
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
    	// System.out.println(query);
    	try{
    		heapSize = args[args.length-1];
			pageSize = Integer.parseInt(heapSize);
			// System.out.println("heap size: "+heapSize);
		} catch(NumberFormatException ex){
			System.out.println("Must input page size");
			return;
		}
        String fileName = "hash."+heapSize;
        File file = new File(fileName);
        int lineCount=0;
        int hashValue = ht.hash(query);
        // System.out.println(hashValue);
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
	    // System.out.println(line);
	    String[] split = line.split(" ");
	    String pageNum = null;
	    String pageLine = null;
	    
    }
}
