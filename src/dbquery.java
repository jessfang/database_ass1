
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

class dbquery {

	public static void main(String[] args) {
		String line = args[1];
		String heapSize = args[2];
		String fileName = "heap."+heapSize;
		search(fileName,line,Integer.parseInt(heapSize));

	}
	public static void search(String fileName, String line, int pageSize) {
		long startTime = System.currentTimeMillis();
		File file = new File(fileName);
		// a regex which can find the specific word (not case sensitive)
		String lineRegex = "(?i).*"+line+"*";
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
			        	if(innerTok.matches(lineRegex)) {
			        		System.out.println("found: "+tok);
			        	}
		        	}
		        }
		        System.out.println();
		      }  
		} catch (IOException e) {
			e.printStackTrace();
		}
		 long estimatedTime = System.currentTimeMillis() - startTime;
		 System.out.println("Time Taken(ms):"+estimatedTime+"ms");
	}
}
