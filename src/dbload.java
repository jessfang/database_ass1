

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class dbload {
	public static void main(String[] args) {
		if(args[0].equals("-p")) {
			String size = args[1];
			try{
				int pageSize = Integer.parseInt(size);
				String dataFile = args[2];
				loadHeapFile(dataFile,pageSize);
			}
			catch(NumberFormatException ex){
				System.out.println("Must specify page size");
			}
		} else {
			System.out.println("Must specify page size");
		}
//		loadHeapFile("C:/Users/jess_/git/database_ass1/src/test.txt",100);
	}
	
	public static void loadHeapFile(String dataFile, int pageSize){
		
		long startTime = System.currentTimeMillis(); // Used for calculating overall time taken
		String heapSize = Integer.toString(pageSize); // Used for creating the page size
		String fileName = "heap."+heapSize; //Used to write the data to
		DataOutputStream os = null;
		int recordCount = 0; // Counts how many records are being passed
		try {
			os = new DataOutputStream(new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ArrayList<byte[]> pageList = new ArrayList<byte[]>(); // contains all the pages
		ArrayList<Integer> counterList = new ArrayList<Integer>(); // contains each pages amount of space used
		// initialise first pageSize to 0 because it has used 0 amount of space
		counterList.add(0);
		
		try{
			File file = new File(dataFile);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			
			byte[] page = createByteArray(pageSize);
			pageList.add(page);
			
			while(line !=null){
				recordCount++; 
				String[] arr = line.split("\t");
				// The byte array holds the record data in bytes
				ArrayList<Byte> byteArray = new ArrayList<Byte>();
				// create a delimiter to show when a record starts and ends
				byte[] lineDelim = "#".getBytes();
				int count = counterList.get(counterList.size()-1);
				for(byte b : lineDelim){
					byteArray.add(b);
					count++;
				}
				counterList.remove(counterList.size()-1);
				counterList.add(count);
				for(String s : arr){
					boolean isInt = false;
					count = counterList.get(counterList.size()-1);
					// add in a delimiter to separate the words
					byte[] wordDelim = "\t".getBytes();
					for(byte b : wordDelim){
						byteArray.add(b);
						count++;
					}
					counterList.remove(counterList.size()-1);
					counterList.add(count);
					// check if the line is an int type
					if(!s.isEmpty()){
						try{
							int i = Integer.parseInt(s);
							byte[] array = toByteArray(i);
							
							for(byte b : array){
								byteArray.add(b);
							}
							isInt = true;
						}
						catch(NumberFormatException ex){
							// Do nothing
						}
					}
					// if line is not int type treat it as string
					if(!isInt) {
						byte[] array = s.getBytes();
						for(byte b : array) {
							byteArray.add(b);
						}
					}
				}
				
				// add a delimiter to the end of the record to know when the record ends
				byte[] delim = "#".getBytes();
				count = counterList.get(counterList.size()-1);
				for(byte b : delim){
					byteArray.add(b);
					count++;
				}
				counterList.remove(counterList.size()-1);
				counterList.add(count);
				
				int lineSize = byteArray.size();
				// check if record length is longer than page size
				if(lineSize < pageSize) {
					int pageSizeAmountUsed = counterList.get(counterList.size()-1);
					// if existing page does not have enough space, create a new page
					if(lineSize + pageSizeAmountUsed > pageSize) {
						// write the existing page to os and then create new page
						os.write(pageList.get(pageList.size()-1));
						
						byte[] newPage = createByteArray(pageSize);
						pageList.add(newPage);
						// add the bytes to the new page
						int count2 = -1;
						for(byte x : byteArray){
							++count2;
							newPage[count2] = x;
							
						}
						// add updated counter to the counterList
						counterList.add(count2);
					// if existing page has space then add to existing page	
					} else {
						// get the current page list counter
						int currentCount = counterList.get(counterList.size()-1);
						byte[] currentPage = null;
						for(byte x : byteArray){
							currentPage = pageList.get(counterList.size()-1);
							++currentCount;
							currentPage[currentCount] = x;
							
						}
						// update the page list and counter list with the new page and counter
						pageList.remove(pageList.size()-1);
						pageList.add(currentPage);
						counterList.remove(counterList.size()-1);
						counterList.add(currentCount);
						
					}
				} else {
					// do nothing if record length is longer than page size
				}
				line = br.readLine();
			}
			os.write(pageList.get(pageList.size()-1));
			fr.close();
			long estimatedTime = System.currentTimeMillis() - startTime;
			System.out.println("Amount of Pages:" +pageList.size());
			System.out.println("Record Count: "+recordCount);
			System.out.println("Time Taken(ms): "+estimatedTime+"ms");
			
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		

	}
	
	// turns the int value to a byte array
	public static byte[] toByteArray(int v) {
	    return new byte[] {
	            (byte)(v >> 24),
	            (byte)(v >> 16),
	            (byte)(v >> 8),
	            (byte)v};
	}
	
	public static byte[] createByteArray(int size) {
		return new byte[size];
	}

}

