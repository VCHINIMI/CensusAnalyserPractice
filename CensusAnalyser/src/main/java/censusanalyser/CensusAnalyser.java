package censusanalyser;

import java.io.IOException;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;
import censusanalyserBuilder.*;
//import Builder.*;

public class CensusAnalyser {
	public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			return csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class).size();
		} catch(CSVBuilderException e){
			throw new CensusAnalyserException(e.getMessage(),
					e.type.name());
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} catch (RuntimeException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
		} 
	}
	
	public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
        	ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			return csvBuilder.getCSVFileList(reader, IndiaStateCodeCSV.class).size();
        } catch(CSVBuilderException e){
			throw new CensusAnalyserException(e.getMessage(),
					e.type.name());
		} catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM); 
        } catch (RuntimeException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CSV_FILE_PROBLEM);
		}
    }
	
	private <E> int getCount(Iterator<E> iterator) {
		Iterable<E> csvIterable = () -> iterator;
		int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
		return numOfEnteries;
	}
}
