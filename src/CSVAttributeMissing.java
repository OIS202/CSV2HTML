public class CSVAttributeMissing extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CSVAttributeMissing(){
		super("Error: Input row cannot be parsed due to missing information.");
	}
	public CSVAttributeMissing(String message){
		super(message);
	}
}
