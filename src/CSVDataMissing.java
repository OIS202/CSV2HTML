public class CSVDataMissing extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CSVDataMissing(){
		super("Error: Input row cannot be parsed due to missing information.");
	}
	public CSVDataMissing(String message){
		super(message);
	}
}
