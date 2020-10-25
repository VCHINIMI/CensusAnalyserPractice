package censusanalyserBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class OpenCSVBuilder implements ICSVBuilder {
	@Override
	public <E> Iterator<E> getCSVFileIterator(Reader reader, Class<E> csvClass) throws CSVBuilderException {
		return getCsvToBean(reader, csvClass).iterator();
	}

	@Override
	public <E> List<E> getCSVFileList(Reader reader, Class<E> csvClass) throws CSVBuilderException {
		return getCsvToBean(reader, csvClass).parse();

	}

	private <E> CsvToBean<E> getCsvToBean(Reader reader, Class<E> csvClass) throws CSVBuilderException {
		try {
			CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
			csvToBeanBuilder.withType(csvClass);
			csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
			CsvToBean<E> csvToBean = csvToBeanBuilder.build();
			return csvToBean;
		} catch (IllegalStateException e) {
			throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
		}
	}
}