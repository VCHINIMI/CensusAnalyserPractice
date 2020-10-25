package censusanalyser;

public class CensusAnalyserException extends Exception {
	private static final long serialVersionUID = 1L;

	public enum ExceptionType {
		CENSUS_FILE_PROBLEM,
		UNABLE_TO_PARSE,
		CSV_FILE_PROBLEM, 
		NO_CENSUS_DATA
	}

	ExceptionType type;

	public CensusAnalyserException(String message, String name) {
		super(message);
		this.type = ExceptionType.valueOf(name);
	}
	
	public CensusAnalyserException(String message, ExceptionType type) {
		super(message);
		this.type = type;
	}

	public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
		super(message, cause);
		this.type = type;
	}
}
