package censusanalyserBuilder;

import java.io.Reader;
import java.util.Iterator;

public interface ICSVBuilder {
	 public  <E> Iterator<E> getCSVFileIterator(Reader reader, Class<E> csvClass) throws CSVBuilderException;
	 public  <E> Iterator<E> getCSVFileIteratorCommons(Reader reader, Class<E> csvClass) throws CSVBuilderException;
}
