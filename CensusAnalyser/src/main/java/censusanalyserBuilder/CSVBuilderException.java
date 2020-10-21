package censusanalyserBuilder;

import censusanalyser.CensusAnalyserException;

public class CSVBuilderException extends Exception {
	private static final long serialVersionUID = 1L;

	enum ExceptionType {
        CENSUS_FILE_PROBLEM,
        UNABLE_TO_PARSE,
        CSV_FILE_PROBLEM
    }

    public CensusAnalyserException.ExceptionType type;

    public CSVBuilderException(String message, CensusAnalyserException.ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CSVBuilderException(String message, CensusAnalyserException.ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
