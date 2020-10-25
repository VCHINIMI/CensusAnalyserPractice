package censusanalyser;
import censusanalyserBuilder.*;
//import Builder.*;

public class CSVBuilderFactory {
	public static ICSVBuilder createCSVBuilder() {
        return new OpenCSVBuilder();
    }
}
