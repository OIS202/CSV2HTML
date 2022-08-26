import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.io.PrintWriter;
public class excpetiontesting {

	public static void main(String[] args) {
		Scanner scanner = null;
		try{
			scanner = new Scanner(new FileInputStream("123.txt"));
			//scanner.nextLine();
			//String firstLine = scanner.nextLine();
			//System.out.println(firstLine);
			something(scanner, false);
		}catch(FileNotFoundException notFound){
			System.out.println("File was not found.");
		}catch(Exception e) {
			System.out.println("Handled");
			System.out.println(e.getMessage());
			while(true) {
				try {
					boolean done = something(scanner, true);
					if(done == true)
						break;
				}catch(Exception e1) {
					System.out.println("again");
					//System.out.println(e1.getMessage());
				}
			}
		}
		System.out.println("done");
		PrintWriter log = null;
		try {
			log = new PrintWriter(new FileOutputStream("Exceptions.log",true)); 
		}catch(FileNotFoundException e){
			System.out.println("Not Found!");
		}
		String str = "Notes";
		System.out.println(str.substring(0, 4));
	}
	public static boolean something(Scanner scan, boolean accessed) throws Exception{
		if(accessed == false) {
			String firstLine = scan.nextLine();
			System.out.println(firstLine);
		}
		while(scan.hasNextLine()) {
			System.out.println(scan.nextLine());
			throw new Exception("hand");
		}
		boolean done = true;
		return done;
	}
}
