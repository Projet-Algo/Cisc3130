import java.util.Scanner;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;

class LISTARTIST {

    public static void main(String[] args) {
        Scanner sc;                               
        PrintStream ps;
        try {
            HashMap<String, Integer> storage = new HashMap<String, Integer>(); // store the artist's name as the key  and the number of time he/she appears as the value
            
            sc = new Scanner(new File("input.CSV"));
            ps = new PrintStream("Artists-WeekOf09052020.txt");
            ps.println("                             ARTIST NAME                        NUMBER OF APPEARENCES  \n");
            while(sc.hasNextLine()){ 
                
               String info = Filter(sc.nextLine()); // info contains the artist name
               
               if(storage.containsKey(info)){
                 storage.replace(info, storage.get(info) + 1); // increment the number of time an artist appears
               }
               else{
                 storage.put(info, 1);                // store a new artist
               }
            }

            for(Map.Entry<String, Integer> entry : storage.entrySet()) {
              ps.printf("%40s",entry.getKey());
              ps.print("                                    ");
              ps.println(entry.getValue());
            }

            sc.close(); // closing of the Scanner class
            ps.close(); // closing of the PrintStream class
         } 
        catch (FileNotFoundException e) {
        
            e.printStackTrace();
        }
    }


    //the function that helps getting the artist's name
    public static  String Filter(String line){
             
        ArrayList<Integer> IndiceOfComma = new ArrayList<Integer>(); // contains the position of the commas which seperate the colunms (only)
        int countQuote = 0;                                     // to count the number of quote that we ancounter in order to get the position of the right commas(which seperate the colunms).
       for(int i=0; i<line.length(); i++){
          
          if(line.charAt(i) == '\"'){
            if(countQuote == 0) countQuote++;
            else countQuote = 0;
          } 
          if(line.charAt(i) == ','){
            if(countQuote == 0)IndiceOfComma.add(i);
          }  
       }

      String  artistName;      // will contain the artist name
      artistName = line.substring(IndiceOfComma.get(1), IndiceOfComma.get(2));
      if(artistName.contains("\"")){
          artistName = artistName.substring(2,artistName.length()-1);
      }
      else{
        artistName = artistName.substring(1,artistName.length());
      }
    return artistName;

  }
}