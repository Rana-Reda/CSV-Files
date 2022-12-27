
import edu.duke.*;
import org.apache.commons.csv.*;
public class WhichCountriesExport {
  //this method will create CSVParser and call each of the methods below
  public void tester (){
    //create a new file resource to choose a file
    FileResource fr = new FileResource();
    //get the csv parser from your file
    CSVParser parser= fr.getCSVParser();
    //print inf from calling the method
    String inf = countryInfo(parser, "Nauru");
    System.out.println(inf);
    //reset the parser to use with another method
    parser = fr.getCSVParser();
    listExportersTwoProducts(parser,"cotton", "flowers");
    parser= fr.getCSVParser();
    int count= numberOfExporters(parser, "cocoa");
    System.out.println("\nnumber of exports: " + count);
    parser= fr.getCSVParser();
    bigExporters(parser, "$999,999,999,999");
 

  }
  // This method returns a string of information about the country.
  public String  countryInfo(CSVParser parser, String country){
    //for every record in the parser
    for(CSVRecord record : parser){
       //get country name
       String Country = record.get("Country");
       //if it is the same name you have
       if(Country.contains(country)){
         //get the country exports items and money value
         String exports = record.get("Exports");
         String value = record.get("Value (dollars)");
         String countryData= country +":" + exports + ":" + value; 
         return countryData;
       }
    }
    //return not found if there is no information about the country
    return "NOT FOUND";
  }
  //his method prints the names of all the countries that have both exportItem1 and exportItem2 as export items.
  public void listExportersTwoProducts(CSVParser parser, String exportItem1 ,String exportItem2){
     for(CSVRecord record: parser){
        String export = record.get("Exports");
        //if the exports items contains these two items
        if(export.contains(exportItem1) && export.contains(exportItem2)){
           //get the country name and print it
           String countryName = record.get("Country");
           System.out.println("\nlist of countries that export:" + countryName);
        }

     }

  }
  //This method returns the number of countries that export exportItem. 
  public int numberOfExporters(CSVParser parser, String exportItem){
    int count =0;
    for (CSVRecord record : parser){
       String export = record.get("Exports");
       //if the export items for a record contain this item
       if(export.contains(exportItem)){
          //increment the count for the item
          count=count+1;
    
       }
    }
    return count;

  }
  //This method prints the names of countries and their Value amount for all countries whose Value (dollars) string is longer than the amount string. 
  public void bigExporters(CSVParser parser, String amount){
     for (CSVRecord record : parser){
        String value = record.get("Value (dollars)");
        if(value.length() > amount.length()){
          System.out.print ("\nbig exporters" + record.get("Country"));
          System.out.println(record.get("Value (dollars)"));
        }

     }
   }

}

