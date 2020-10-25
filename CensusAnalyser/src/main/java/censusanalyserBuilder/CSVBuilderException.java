package censusanalyserBuilder;


public class CSVBuilderException extends Exception {
	private static final long serialVersionUID = 1L;

	public enum ExceptionType {
        CENSUS_FILE_PROBLEM,
        UNABLE_TO_PARSE,
        CSV_FILE_PROBLEM
    }
	
	public ExceptionType type;

	public CSVBuilderException(String message, ExceptionType type) {
        super(message);
        this.type = type;
	}
    public CSVBuilderException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
