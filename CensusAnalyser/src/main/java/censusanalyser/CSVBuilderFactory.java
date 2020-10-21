package censusanalyser;
import censusanalyserBuilder.*;

public class CSVBuilderFactory {
	public static ICSVBuilder createCSVBuilder() {
        return new OpenCSVBuilder();
    }
}
