package censusanalyser;

import java.io.IOException;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

import com.google.gson.Gson;

import censusanalyserBuilder.*;
//import Builder.*;

public class CensusAnalyser {

	List<IndiaCensusCSV> censusCSVList = null;

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
			return csvBuilder.getCSVFileList(reader, IndiaStateCodeCSV.class).size();
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

	public String getStateWiseSortedCensusData() throws CensusAnalyserException {
		if (censusCSVList == null || censusCSVList.size() == 0) {
			throw new CensusAnalyserException("No List Available",
					CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
		}
		Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
		this.sort(censusComparator);
		String sortedStateCensusJson = new Gson().toJson(censusCSVList);
		return sortedStateCensusJson;
	}

	private void sort(Comparator<IndiaCensusCSV> censusComparator) {
		for (int i = 0; i < censusCSVList.size() - 1; i++) {
			for (int j = 0; j < censusCSVList.size() - i - 1; j++) {
				IndiaCensusCSV census1 = censusCSVList.get(j);
				IndiaCensusCSV census2 = censusCSVList.get(j + 1);
				if (censusComparator.compare(census1, census2) > 0) {
					censusCSVList.set(j, census2);
					censusCSVList.set(j + 1, census1);
				}
			}
		}
	}
}
