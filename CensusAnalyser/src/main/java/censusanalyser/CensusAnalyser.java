package censusanalyser;

import java.io.IOException;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

import com.google.gson.Gson;

import censusanalyserBuilder.*;
//import Builder.*;

public class CensusAnalyser {

	List<IndiaCensusCSV> censusCSVList = null;
	List<IndiaStateCodeCSV> stateCodeList = null;

	public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			censusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
			return censusCSVList.size();
		} catch (CSVBuilderException e) {
			throw new CensusAnalyserException(e.getMessage(), e.type.name());
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} catch (RuntimeException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
		}
	}

	public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			stateCodeList = csvBuilder.getCSVFileList(reader, IndiaStateCodeCSV.class);
			return stateCodeList.size();
		} catch (CSVBuilderException e) {
			throw new CensusAnalyserException(e.getMessage(), e.type.name());
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} catch (RuntimeException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
		}
	}

	private <E> int getCount(Iterator<E> iterator) {
		Iterable<E> csvIterable = () -> iterator;
		int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
		return numOfEnteries;
	}

	public String getStateNameWiseSortedCensusData() throws CensusAnalyserException {
		if (censusCSVList == null || censusCSVList.size() == 0) {
			throw new CensusAnalyserException("No List Available",
					CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
		}
		Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
		this.sort(censusComparator, censusCSVList);
		String sortedStateCensusJson = new Gson().toJson(censusCSVList);
		return sortedStateCensusJson;
	}

	public String getStateCodeWiseSortedCensusData() throws CensusAnalyserException {
		if (stateCodeList == null || stateCodeList.size() == 0) {
			throw new CensusAnalyserException("No List Available",
					CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
		}
		Comparator<IndiaStateCodeCSV> stateCodeComparator = Comparator.comparing(StateCode -> StateCode.stateCode);
		this.sort(stateCodeComparator, stateCodeList);
		String sortedStateCensusJson = new Gson().toJson(stateCodeList);
		return sortedStateCensusJson;
	}

	public String getPopulationWiseSortedData() throws CensusAnalyserException {
		if (censusCSVList == null || censusCSVList.size() == 0) {
			throw new CensusAnalyserException("No List Available",
					CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
		}
		Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.population);
		this.sort(censusComparator, censusCSVList);
		Collections.reverse(censusCSVList);
		String sortedStateCensusJson = new Gson().toJson(censusCSVList);
		return sortedStateCensusJson;
	}
	
	public String getPopulationDensityWiseSortedData() throws CensusAnalyserException {
		if (censusCSVList == null || censusCSVList.size() == 0) {
			throw new CensusAnalyserException("No List Available",
					CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
		}
		Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
		this.sort(censusComparator, censusCSVList);
		Collections.reverse(censusCSVList);
		String sortedStateCensusJson = new Gson().toJson(censusCSVList);
		return sortedStateCensusJson;
	}


	private <E> void sort(Comparator<E> censusComparator, List<E> sortList) {
		for (int i = 0; i < sortList.size() - 1; i++) {
			for (int j = 0; j < sortList.size() - i - 1; j++) {
				E census1 = sortList.get(j);
				E census2 = sortList.get(j + 1);
				if (censusComparator.compare(census1, census2) > 0) {
					sortList.set(j, census2);
					sortList.set(j + 1, census1);
				}
			}
		}
	}
}
