package database_ass1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			File file = new File("./src/database_ass1/test.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			StringBuffer sb = new StringBuffer();
			String line = br.readLine();
			while(line !=null){
				sb.append(line);
				line = br.readLine();
			}
			fr.close();
			System.out.println(sb.toString());
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}

	}

}
