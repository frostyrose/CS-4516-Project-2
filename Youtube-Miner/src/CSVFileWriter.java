
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class CSVFileWriter {
	//Delimiters used in CSV 
    private static final String DELIMITER = ",";
    private static final String NEW_LINE = "\n";
    //CSV header - needs to be tweaked
    private static final String FILE_HEADER = "CountryCode,NumberVideos";
    
    public static void writeCsvFile(String fileName, int[] continentVals, Catalog countryVals) {
    	FileWriter fileWriter = null;
    	

        try {
            fileWriter = new FileWriter(fileName);
            fileWriter.append(FILE_HEADER.toString());
            fileWriter.append(NEW_LINE);
            
            int counter = 0;
            fileWriter.append("TOTAL_VIDEOS");
            fileWriter.append(DELIMITER);
            fileWriter.append(Integer.toString(Main.totalVideos));
            fileWriter.append(NEW_LINE);
            fileWriter.append("AFRICA");
            fileWriter.append(DELIMITER);
            fileWriter.append(Integer.toString(continentVals[0]));
            fileWriter.append(NEW_LINE);
            fileWriter.append("ASIA");
            fileWriter.append(DELIMITER);
            fileWriter.append(Integer.toString(continentVals[1]));
            fileWriter.append(NEW_LINE);
            fileWriter.append("OCEANIA");
            fileWriter.append(DELIMITER);
            fileWriter.append(Integer.toString(continentVals[2]));
            fileWriter.append(NEW_LINE);
            fileWriter.append("EUROPE");
            fileWriter.append(DELIMITER);
            fileWriter.append(Integer.toString(continentVals[3]));
            fileWriter.append(NEW_LINE);
            fileWriter.append("NORTH_AMERICA");
            fileWriter.append(DELIMITER);
            fileWriter.append(Integer.toString(continentVals[4]));
            fileWriter.append(NEW_LINE);
            fileWriter.append("SOUTH_AMERICA");
            fileWriter.append(DELIMITER);
            fileWriter.append(Integer.toString(continentVals[5]));
            fileWriter.append(NEW_LINE);
            
           HashMap<String,Integer> countryData = countryVals.regions;
           Set<String> keys = countryData.keySet();
            
            for (String k : keys) {
                fileWriter.append(k);
                fileWriter.append(DELIMITER);
                fileWriter.append(Integer.toString(countryData.get(k)));
                fileWriter.append(NEW_LINE);
                counter++;
            }

            System.out.println("CSV file was Writen");

       } catch (Exception e) {
                System.out.println("Error in Writing");
                e.printStackTrace();
       } finally {
            try {
                fileWriter.flush();
                fileWriter.close();   
            } catch (IOException e) {
                    System.out.println("Error while Closing File");
                    e.printStackTrace();
            }
       }
    }
}