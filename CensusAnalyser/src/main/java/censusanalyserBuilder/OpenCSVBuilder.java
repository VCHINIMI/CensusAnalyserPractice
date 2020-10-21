package censusanalyserBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import censusanalyser.CensusAnalyserException;

public class OpenCSVBuilder implements ICSVBuilder{
	public  <E> Iterator<E> getCSVFileIterator(Reader reader, Class<E> csvClass) throws CSVBuilderException {
	        try {
	            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
	            csvToBeanBuilder.withType(csvClass);
	            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
	            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
	            return csvToBean.iterator();
	        } catch (IllegalStateException e) {
	        	 throw new CSVBuilderException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
	        }
	    }
	   
	@SuppressWarnings("unchecked")
	public <E> Iterator<E> getCSVFileIteratorCommons(Reader reader, Class<E> csvClass) throws CSVBuilderException{
		try {
			CSVParser parser = CSVParser.parse(reader,CSVFormat.DEFAULT.withFirstRecordAsHeader().
								withIgnoreHeaderCase().withTrim());
			Iterator<CSVRecord> records = parser.iterator();
			return (Iterator<E>) records;
		   } catch (IOException e) {
			   throw new CSVBuilderException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		   }		   
	   }
}