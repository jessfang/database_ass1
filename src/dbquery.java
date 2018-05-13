

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class dbquery {

	public static void main(String[] args) {
		String line = args[0];
		String heapSize = args[1];
		int size=0;
		try{
			size = Integer.parseInt(heapSize);
			String fileName = "heap."+heapSize;
			search(fileName,line,size);
		}
		catch(NumberFormatException ex){
			System.out.println("Must place number for second query");
		}
	}
	public static void search(String fileName, String line, int pageSize) {
		long startTime = System.currentTimeMillis();
		boolean hasfound = false;
		File file = new File(fileName);
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
		        	for(String innerTok : tokSplit){
		        		System.out.println(innerTok);
			        	if(innerTok.toLowerCase().contains(line.toLowerCase())) {
			        		System.out.println("found: "+tok);
			        		hasfound = true;
			        	}
		        	}
		        	if(tokSplit.length > 2) {
                        // System.out.println(tokSplit[2]);
                    }  
		        }
		      }  
		} catch (IOException e) {
			e.printStackTrace();
		}
		 long estimatedTime = System.currentTimeMillis() - startTime;
		 if(!hasfound) {
			 System.out.println("Text was not found.");
		 }
		 System.out.println("Time Taken(ms):"+estimatedTime+"ms");
	}
}
