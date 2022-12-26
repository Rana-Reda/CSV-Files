
/**
 * Write a description of MaxCSV here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
public class MaxCSV {
  //this method returns the coldest temperature in a file and all the information about this temperature
  public CSVRecord coldestHourInFile(CSVParser parser){
    //create a new csv record and intilaize it to null
    CSVRecord coldestSoFar=null;
    //for every record in the parser
    for(CSVRecord currentRow : parser){
        //use the method coldestOfTwo to compare between the last coldest one and the current one for every record
        coldestSoFar= ColdestOfTwo(currentRow, coldestSoFar);

    }
    return coldestSoFar;

  }
  //a method to compare two temperatures and return the coldest of them
  public CSVRecord ColdestOfTwo(CSVRecord currentRow, CSVRecord coldestSoFar){
    //make the coldest one the first temperature for the first record
      if(coldestSoFar == null){
      coldestSoFar = currentRow;
    }
    else {
        //convert the string temperature to double value to be able to compare
        double currentTemp= Double.parseDouble(currentRow.get("TemperatureF"));
        double largestTemp= Double.parseDouble(coldestSoFar.get("TemperatureF"));
        if(currentTemp< largestTemp && currentTemp != -9999){
          coldestSoFar= currentRow;
        }

    }
    return coldestSoFar;
  }
  //a method like the perviouse one but compares humidities
  public CSVRecord LowestOfTwo(CSVRecord currentRow, CSVRecord lowestSoFar){
    if(lowestSoFar == null){
      lowestSoFar = currentRow;
    }
    else{
        String currenthum= currentRow.get("Humidity");
        String lowesthum = lowestSoFar.get("Humidity");
        //if there is no inf. about the humidity in this record return the last one you have
        if(currenthum.equals("N/A")){
          return lowestSoFar;
        }
        else if(lowesthum.equals("N/A")){
            lowestSoFar= currentRow;
        }
        else{    
           double currentHum= Double.parseDouble(currentRow.get("Humidity"));
           double lowestHum= Double.parseDouble(lowestSoFar.get("Humidity"));
           if(currentHum< lowestHum){
             lowestSoFar= currentRow;
           }

        }
    }
    return lowestSoFar;
  }
  //his method should return a string that is the name of the file from selected files that has the coldest temperature. 
  public void fileWithColdestTemperature(){
    CSVRecord coldestSoFar = null;
    //create a new directory resource to choose multiple files
    DirectoryResource dr= new DirectoryResource();
    //for every file in the directory resource files
    for (File f : dr.selectedFiles()){
      //create a new file resource and a new parser to work with the csv files
      FileResource fr = new FileResource(f);
      CSVParser parser = fr.getCSVParser();
      CSVRecord currentRow = coldestHourInFile(parser);
      //call the method coldest of two to get the coldest temperature
      coldestSoFar= ColdestOfTwo(currentRow, coldestSoFar); 

    }
    //print the dateUTC which have the information about the file with the coldest temperature
    System.out.println("coldest day was in file weather:" + coldestSoFar.get("DateUTC") + ".csv");
    System.out.println("\ncoldest temperature in this day was: " + coldestSoFar.get("TemperatureF") );
    System.out.println("\nall temperatures on this day were: ");

  }
  // This method returns a CSVRecord that has the lowest humidity over all the files.
  public CSVRecord lowestHumidityInManyFiles(){
    CSVRecord lowestSoFar=null;
    DirectoryResource dr= new DirectoryResource();
    for (File f : dr.selectedFiles()){
       FileResource fr = new FileResource(f);
       CSVParser parser = fr.getCSVParser();
       CSVRecord currentRow = lowestHumidityInFile(parser);
       lowestSoFar= LowestOfTwo(currentRow, lowestSoFar); 

    }
    return lowestSoFar;

  }
  // This method returns the CSVRecord that has the lowest humidity.
  public CSVRecord lowestHumidityInFile(CSVParser parser){
    CSVRecord lowestSoFar = null;
    for(CSVRecord currentRow: parser){
       lowestSoFar= LowestOfTwo(currentRow, lowestSoFar);
    }
    return lowestSoFar;
  }
  //This method returns a double that represents the average temperature in the file.
  public double averageTemperatureInFile(CSVParser parser){
    double sum=0;
    double avg=0;
    int totalRow=0;
    //for every record in the parser
    for(CSVRecord record: parser){
       double currentTemp= Double.parseDouble(record.get("TemperatureF"));
       //increment the sum with the temperature
       sum=sum+currentTemp;
       totalRow++;
    }
    //calculate the average
    avg= sum/totalRow;
    return avg;

  }
  // This method returns a double that represents the average temperature of only those temperatures when the humidity was greater than or equal to value.
  public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
    double sum=0.0;
    int count=0;
    double avg=0.0;
    for(CSVRecord record: parser){
       double Hum= Double.parseDouble(record.get("Humidity"));    
       if (Hum >= value){
         double currentTemp= Double.parseDouble(record.get("TemperatureF"));
         sum+= currentTemp;
         count++;
       }
    }
    //if there is no temperatures with humidity higher than the given one return zero
    if(count==0){
      return 0.0;
    }
    avg= sum/count;
    return avg;
  }
  //the below methods are to test the pervious ones and print the inf. from them. 
  public void testColdestHourInFile(){
    FileResource fr= new FileResource();
    CSVParser parser= fr.getCSVParser();
    CSVRecord largest= coldestHourInFile(parser);
    System.out.println("coldest temperature was: "+largest.get("TemperatureF") + "at"  );
  }
  public void testFileWithColdestTemperature() {
    fileWithColdestTemperature();
   
  }
  public void testLowestHumidityInFile(){
    FileResource fr= new FileResource();
    CSVParser parser=fr.getCSVParser();
    CSVRecord lowest= lowestHumidityInFile(parser);
    System.out.println("lowest humidity in file " + lowest.get("DateUTC") + "is" + lowest.get("Humidity"));

  }
  public void testLowestHumidityInManyFiles(){
    CSVRecord lowest= lowestHumidityInManyFiles();
    System.out.println("lowest humidity in file " + lowest.get("DateUTC") + "is" + lowest.get("Humidity"));


  }

  public void testAverageTemperatureInFile(){
    FileResource fr= new FileResource();
    CSVParser parser=fr.getCSVParser();
    double average = averageTemperatureInFile(parser);
    System.out.println("average temperature in file is:" +average);

  }
  public void testAverageTemperatureWithHighHumidityInFile(){
    FileResource fr= new FileResource();
    CSVParser parser=fr.getCSVParser();
    double average = averageTemperatureWithHighHumidityInFile(parser, 80);
    if(average != 0.0){
      System.out.println("average temperature when high humidity is" + average);
    }
    else{
        System.out.println("No temperatures with that humidity");
    }
  }
}
