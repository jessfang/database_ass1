package database_ass1;

import java.awt.List;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int pageSize = 4098;
		String heapSize = Integer.toString(pageSize);
		String fileName = "heap."+heapSize;
		System.out.println("File name: "+fileName);
		DataOutputStream os = null;
		try {
			os = new DataOutputStream(new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<byte[]> pageList = new ArrayList();
		ArrayList<Integer> counterList = new ArrayList<Integer>();
		// intialise first pageSize as 0
		counterList.add(0);
		try{
			File file = new File("./src/database_ass1/test.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			StringBuffer sb = new StringBuffer();
			String line = br.readLine();
			
			byte[] page = createByteArray(pageSize);
			pageList.add(page);
//			int numPagesCreated = 1;
			
			while(line !=null){
				String[] arr = line.split("\t");
				ArrayList<Byte> byteArray = new ArrayList<Byte>();
				for(String s : arr){
					System.out.println("word: "+s);
					boolean isInt = false;
					// check if line is an int type
					byte[] delim = "#".getBytes();
					for(byte b : delim){
						byteArray.add(b);
					}
					if(!s.isEmpty()){
						try{
							int i = Integer.parseInt(s);
							byte[] array = toByteArray(i);
							// TODO: need to store integer into page
							for(byte b : array){
								byteArray.add(b);
							}
							isInt = true;
						}
						catch(NumberFormatException ex){
							//do nothing
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
				// check if the record can fit into the page
				byte[] delim = "#".getBytes();
				for(byte b : delim){
					byteArray.add(b);
				}
				int lineSize = byteArray.size();
				// check if record length is longer than page size
				if(lineSize < pageSize) {
					int pageSizeAmountUsed = counterList.get(counterList.size()-1);
					// if existing page does not have enough space, create a new page
					if(lineSize + pageSizeAmountUsed > pageSize) {
						//write the existing page to os and then create new page
						os.write(pageList.get(pageList.size()-1));
						
						byte[] newPage = createByteArray(pageSize);
						pageList.add(newPage);
						int count = -1;
						for(byte x : byteArray){
							++count;
							newPage[count] = x;
							
						}
						// add counter to the counterList
						counterList.add(count);
					// if existing page has space then add to existing page	
					} else {
						int currentCount = counterList.get(counterList.size()-1);
						for(byte x : byteArray){
							byte[] currentPage = pageList.get(counterList.size()-1);
							++currentCount;
							currentPage[currentCount] = x;
							pageList.remove(pageList.size()-1);
							pageList.add(currentPage);
						}
						//TODO: check if the remove and add is correct!!!
						counterList.remove(counterList.size()-1);
						counterList.add(currentCount);
						
					}
				} else {
					// do nothing if record length is longer than page size
					// do not add in
				}
				line = br.readLine();
			}
			fr.close();
//			System.out.println(sb.toString());
			for(byte[] a : pageList) {
				for(byte i : a){
					System.out.print(i);	
				}
				System.out.println();
			}
			search("my", pageSize);
			
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		

	}
	
	public static byte[] toByteArray(int value) {
	    return new byte[] {
	            (byte)(value >> 24),
	            (byte)(value >> 16),
	            (byte)(value >> 8),
	            (byte)value};
	}
	
	public static byte[] createByteArray(int size) {
		return new byte[size];
	}
	
	public static String search(String line, int pageSize) {
		byte[] delim = "#".getBytes();
		File file = new File("./src/database_ass1/heap.4098");
		String lineRegex = "(?i).*"+line+"*";
		 try (RandomAccessFile data = new RandomAccessFile(file, "r")) {
		      byte[] page = new byte[pageSize];
		      for (long i = 0, len = data.length() / pageSize; i < len; i++) {
		        data.readFully(page);
		        String result = page.toString();
		        String [] split = result.split("#");
		        for(String tok : split) {
		        	boolean isLineFound = false;
		        	isLineFound = tok.matches(lineRegex);
		        	if(isLineFound) {
		        		System.out.println(tok);
		        	}
		        }
		        
		      }  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void addStringToByteArray() {
//		int size = array.length;
//		boolean isAdded = false;
//		int currentCount;
//		// check if can add record to existing pages
//		for(int i=0;i<counterList.size();i++){
//			// check if there is enough free space for record in page
//			if(counterList.get(i) + size < pageSize) {
//				currentCount = counterList.get(i);
//				for(byte x : array){
//					byte[] currentPage = pageList.get(i);
//					++currentCount;
//					currentPage[currentCount] = x;
//				}
//				//TODO: check if the remove and add is correct!!!
//				counterList.remove(i);
//				counterList.add(i, currentCount);
//				isAdded = true;
//				break;
//			}
//		}
//		// if did not add to existing pages, create a new page
//		if(!isAdded) {
//			byte[] newPage = createByteArray(pageSize);
//			pageList.add(newPage);
////			numPagesCreated++;
//			int count = -1;
//			for(byte x : array){
//				++count;
//				newPage[count] = x;
//				
//			}
//			// add counter to the counterList
//			counterList.add(count);
//		}
	}
}
