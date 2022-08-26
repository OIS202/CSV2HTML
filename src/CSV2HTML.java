import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
public class CSV2HTML {
	
	public static void main(String[] args) {
		System.out.println("Welcome to our program that converts csv files into HTML files.");
		boolean done = false;
		Scanner keyboard = new Scanner(System.in);
		do {
			Scanner readFile = null;
			PrintWriter writeFile = null; 
			PrintWriter log = null;
			System.out.println("Please enter the file name yopu want to convert without any file format at the end.");
			String fileName = keyboard.nextLine();
			
			try{
				readFile = new Scanner(new FileInputStream(fileName+".csv"));
				writeFile = new PrintWriter(new FileOutputStream(fileName+".html"));
				log = new PrintWriter(new FileOutputStream("Exceptions.log",true)); 
				CSVToHTML(readFile, keyboard, writeFile, false, fileName);
			}catch(FileNotFoundException notFound){
				System.out.println("File was not found.");
			}catch(CSVDataMissing dataMiss) {
				System.out.println(dataMiss.getMessage());
				log.println(dataMiss.getMessage());
				while(true) {
					try {
						boolean finished = CSVToHTML(readFile, keyboard, writeFile, true, fileName);
						if(finished == true)
							break;
					}catch(CSVDataMissing dataMiss2) {
						System.out.println(dataMiss2.getMessage());
						log.println(dataMiss2.getMessage());
					} catch (CSVAttributeMissing e) {
						System.out.println(e.getMessage());
						log.println(e.getMessage());
					}
				}
			}catch(CSVAttributeMissing attributeMiss) {
				System.out.println(attributeMiss.getMessage());
				log.println(attributeMiss.getMessage());
			}
			System.out.println("Do you want to convert ant more files? (Answer with y/n)");
			String answer = keyboard.nextLine();
			if(answer.equals("n")) {
				done = true;
			}
		}while(done == false);
		System.out.println("Input the name of one of the files you converted above and include its file type at the end: ");
		String fileName2 = keyboard.nextLine();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileName2));
			while(reader.readLine()!=null) {
				String line = reader.readLine();
				System.out.println(line);
			}
		}catch(FileNotFoundException notFound) {
			System.out.println("You have one last chance to enter a valid name or the program will terminate: ");
			fileName2 = keyboard.nextLine();
			try {
				reader = new BufferedReader(new FileReader(fileName2));
				while(reader.readLine()!=null) {
					String line = reader.readLine();
					System.out.println(line);
				}
			}catch(FileNotFoundException notFound2) {
				System.out.println("The program will terminate");
				System.exit(0);
			} catch (IOException e) {
				System.out.println("Exception being handled");
			}
		}catch (IOException e) {
			System.out.println("Exception being handled");
		}
		
	}
	public static boolean CSVToHTML(Scanner read, Scanner keyboard, PrintWriter pw, boolean accessed, String fileName)throws  CSVAttributeMissing, CSVDataMissing{
		int lineNum = 1;
		if(accessed == false) {
			pw.println("<html>");
			pw.println("<style>");
			pw.println("table {font-family: arial, san-serif;border-collapse: collapse;}");
			pw.println("td, th {border: 1px solid #000000; text-align: left;padding: 8px;}");
			pw.println("tr:nth-child(even) {background-color: #dddddd;}");
			pw.println("span{font-size: small}");
			pw.println("</style>");
			pw.println("<body>");
			pw.println();
			pw.println("<table>");
			String firstLine = read.nextLine();
			String[] captionLine = firstLine.split(",");
			pw.println("<caption>"+captionLine[0]+"</caption>");
			lineNum++;
			String attrLine = read.nextLine();
			String[] attrs = attrLine.split(",");
			if(attrs[0].equals("")||attrs[1].equals("")||attrs[2].equals("")||attrs[3].equals("")) {
				throw new CSVAttributeMissing("ERROR: In file "+fileName+".CSV. Missing attribute.File is not converted to HTML.");
			}else {
				pw.println("<tr>");
				for(int i = 0; i< 4; i++) {
					pw.println("<th>"+attrs[i]+"</th>");
				}
				pw.println("</tr>");
			}
		}
		while(read.hasNextLine()){
			String line = read.nextLine();
			lineNum++;
			if(line.substring(0, 4).equals("Note")) {
				pw.println("</table>");
				pw.println("<span>"+line+"</span>");
			}
			String[] data = line.split(",");
			if(data[0].equals("")||data[1].equals("")||data[2].equals("")||data[3].equals("")) {
				throw new CSVDataMissing("WARNING: In file "+fileName+".CSV line "+lineNum+" is not converted to HTML: missing data: ICU.");
			}else{
				pw.println("<tr>");
				for(int i = 0; i< 4; i++) {
					pw.println("<th>"+data[i]+"</th>");
				}
				pw.println("</tr>");
			}
		}
		pw.println("</body>");
		pw.println("</html>");
		return true;
	}
}
