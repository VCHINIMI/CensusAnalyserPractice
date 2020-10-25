package censusanalyser;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.gson.Gson;

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
	
	@Test
	public void givenIndianCensusDataSortedByStateName() {
		try {
			censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
			String stateNameWiseSortedCensusData = censusAnalyser.getStateNameWiseSortedCensusData();
			IndiaCensusCSV[] censusCSV = new Gson().fromJson(stateNameWiseSortedCensusData, IndiaCensusCSV[].class);
			assertEquals("Andhra Pradesh", censusCSV[0].state);				
		}catch (CensusAnalyserException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void givenIndianStateCodeSortByCode() {
		try {
			censusAnalyser.loadIndianStateCode(INDIA_STATECODE_CSV_FILE_PATH);
			String stateCodeWiseSortedStateData = censusAnalyser.getStateCodeWiseSortedCensusData();
			IndiaStateCodeCSV[] censusCSV = new Gson().fromJson(stateCodeWiseSortedStateData, IndiaStateCodeCSV[].class);
			assertEquals("Andhra Pradesh New", censusCSV[0].state);				
		}catch (CensusAnalyserException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void givenIndianCensusDataSortedByPopulation() {
		try {
			censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
			String populationWiseSortedCensusData = censusAnalyser.getPopulationWiseSortedData();
			IndiaCensusCSV[] censusCSV = new Gson().fromJson(populationWiseSortedCensusData, IndiaCensusCSV[].class);
			assertEquals("Uttar Pradesh", censusCSV[0].state);				
		}catch (CensusAnalyserException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void givenIndianCensusDataSortedByPopulationDensity() {
		try {
			censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
			String populationDensityWiseSortedCensusData = censusAnalyser.getPopulationDensityWiseSortedData();
			IndiaCensusCSV[] censusCSV = new Gson().fromJson(populationDensityWiseSortedCensusData, IndiaCensusCSV[].class);
			assertEquals("Bihar", censusCSV[0].state);				
		}catch (CensusAnalyserException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void givenIndianCensusDataSortedByArea() {
		try {
			censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
			String areaWiseSortedCensusData = censusAnalyser.getAreaWiseSortedData();
			IndiaCensusCSV[] censusCSV = new Gson().fromJson(areaWiseSortedCensusData, IndiaCensusCSV[].class);
			assertEquals("Rajasthan", censusCSV[0].state);				
		}catch (CensusAnalyserException e) {
			System.out.println(e.getMessage());
		}
	}
}