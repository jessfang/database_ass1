import java.io.File;

public class hashquery {
	public static void main(String[] args)
    {
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
    }
}
