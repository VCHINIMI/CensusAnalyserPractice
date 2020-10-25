package censusanalyserBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CommonsCSVBuilder implements ICSVBuilder {

	@Override
	@SuppressWarnings("unchecked")
	public <E> Iterator<E> getCSVFileIterator(Reader reader, Class<E> csvClass) throws CSVBuilderException {
		try {
			CSVParser parser = CSVParser.parse(reader,CSVFormat.DEFAULT.withFirstRecordAsHeader().
								withIgnoreHeaderCase().withTrim());
			Iterator<CSVRecord> records = parser.iterator();
			return (Iterator<E>) records;
		   } catch (IOException e) {
			   throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
		   }		   
	}

	@Override
	public <E> List<E> getCSVFileList(Reader reader, Class<E> csvClaSss) throws CSVBuilderException {
		try {
			CSVParser parser = CSVParser.parse(reader,CSVFormat.DEFAULT.withFirstRecordAsHeader().
								withIgnoreHeaderCase().withTrim());
			return null;
		   } catch (IOException e) {
			   throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
		   }		   
	}
}
