package censusanalyser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

	public CensusAnalyser censusAnalyser;
	private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
	private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
	private static final String WRONG_FILE_HEADER = "./src/main/resources/IndiaStateCensusData.txt";
	private static final String WRONG_DELIMITER_FILE = "./src/test/resources/WrongIndiaStateCensusData.csv";
	private static final String WRONG_HEADER_FILE = "./src/test/resources/WrongHeaderIndiaStateCensusData.csv";
	private static final String INDIA_STATECODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
	private static final String INDIA_STATECODE_WRONG_FILE_HEADER = "./src/main/resources/IndiaStateCode.txt";
	private static final String WRONG_STATECODE_DELIMITER_FILE = "./src/test/resources/WrongIndiaStateCodeData.csv";
	private static final String INDIA_STATECODE_WRONG_HEADER_FILE = "./src/test/resources/WrongHeaderIndiaStateCodeData.csv";
	
	@Before
	public void intitializeTest() {
		censusAnalyser = new CensusAnalyser();
		ExpectedException exceptionRule = ExpectedException.none();
		exceptionRule.expect(CensusAnalyserException.class);
	}

	@Test
	public void givenIndianCensusCSVFileReturnsCorrectRecords() {
		try {
			int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
			Assert.assertEquals(29, numOfRecords);
		} catch (CensusAnalyserException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
		try {
			censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
		}
	}

	@Test
	public void testIfWrongFileExtension() {
		try {
			censusAnalyser.loadIndiaCensusData(WRONG_FILE_HEADER);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
		}
	}

	@Test
	public void if_WrongDelimiter_ShouldReturn_RunTimeException() {
		try {
			censusAnalyser.loadIndiaCensusData(WRONG_DELIMITER_FILE);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM, e.type);
		}
	}

	@Test
	public void if_WrongFileHeader_ShouldReturn_RunTimeException() {
		try {
			censusAnalyser.loadIndiaCensusData(WRONG_HEADER_FILE);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM, e.type);
		}
	}

	@Test
	public void givenIndianStateCSV_whenCorrect_shouldReturnExactCount() {
		try {
			int numberOfStateCode = censusAnalyser.loadIndianStateCode(INDIA_STATECODE_CSV_FILE_PATH);
			Assert.assertEquals(37, numberOfStateCode);
		} catch (CensusAnalyserException e) {
		}
	}
	
	@Test
	public void givenIndiaStateCode_WithWrongFile_ShouldThrowException() {
		try {
			censusAnalyser.loadIndianStateCode(WRONG_CSV_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
		}
	}
	
	@Test
	public void givenIndianStateCode_IfWrongFileExtension_RetursException() {
		try {
			censusAnalyser.loadIndianStateCode(INDIA_STATECODE_WRONG_FILE_HEADER);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
		}
	}
	
	@Test
	public void StateCode_if_WrongDelimiter_ShouldReturn_RunTimeException() {
		try {
			censusAnalyser.loadIndianStateCode(WRONG_STATECODE_DELIMITER_FILE);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM, e.type);
		}
	}

	@Test
	public void IndiaStateCode_if_WrongFileHeader_ShouldReturn_RunTimeException() {
		try {
			censusAnalyser.loadIndiaCensusData(INDIA_STATECODE_WRONG_HEADER_FILE);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM, e.type);
		}
	}
}
