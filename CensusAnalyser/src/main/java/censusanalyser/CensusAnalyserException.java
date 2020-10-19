package censusanalyser;

public class CensusAnalyserException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8981185638235446673L;

	enum ExceptionType {
        CENSUS_FILE_PROBLEM
    }

    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
