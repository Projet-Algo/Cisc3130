import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintStream;
import java.io.File;
import java.io.FileNotFoundException;


class Artist {
  public String name;
  public Artist next;
 
  // constructor  
 Artist(String name){
   this.name = name;
   this.next = null;
 }

}

/* The List TopStreamingArtists is composed of a series of artist names */
class TopStreamingArtists {
  private Artist first;

  //constructor
  TopStreamingArtists(){
    first = null;
  }
  // the method to check if the list is empty or not
  public boolean isEmpty(){
   return (first == null);
  }

  // the methode that helps insert an artist
  public boolean insertFirst(String name){
    Artist artist = new Artist(name);
    
   artist.next = first;
   first =artist;

    return true;
  }

  // it checks if the artist with the name "name " exist already
  public boolean find(String name){
   Artist current = first;

    while(current != null){
      if(current.name.equals(name)) return true;
      current = current.next;
    }
   return false;
  }

  
  // the Function that sortes the linked list
  public void sortList() {  
    //Artist current will point to first  
    Artist current = first, index = null;  
    String temp;  
      
    if(first == null) {  
        return;  
    }  
    else {  
        while(current != null) {  
            //Artist index will point to Artist next to current  
            index = current.next;  
              
            while(index != null) {  
                //If current Artist's name is greater than index's name, swap the data between them  
                if(Character.toUpperCase(current.name.charAt(0)) > Character.toUpperCase(index.name.charAt(0))) {   
                    temp = current.name;  
                    current.name = index.name;  
                    index.name = temp;  
                }  
                index = index.next;  
            }  
            current = current.next;  
        }      
    }  
  }

  // it prints the contains of the list
  public void display(PrintStream ps){
    Artist current = first;
    while(current != null){
      ps.println(current.name);
      current = current.next;
    }
  }
}



class LISTARTIST {

    public static void main(String[] args) {
        Scanner sc;                               
        PrintStream ps;
        try {
            
            sc = new Scanner(new File("input.CSV"));
            ps = new PrintStream("ArtistsSorted-WeekOf10012020.txt");

            ps.println("-- ARTIST NAME ---\n");

            TopStreamingArtists artistNames = new TopStreamingArtists();
            while(sc.hasNextLine()){ 
                  String name = Filter(sc.nextLine());
                  if(!artistNames.find(name)) artistNames.insertFirst(name);
            }
            artistNames.sortList();
            artistNames.display(ps);

            sc.close(); // closing of the Scanner class
            ps.close(); // closing of the PrintStream class
         } 
        catch (FileNotFoundException e) {
        
            e.printStackTrace();
        }
    }


    /* the function below helps filter the informations get from the input file
    and return the name of the artist*/
    public static  String Filter(String line){
             
        ArrayList<Integer> IndiceOfComma = new ArrayList<Integer>();
        int countQuote = 0; // to count the number of quote that we ancounter in order to get the position of the right commas(which seperate the colunms).
       for(int i=0; i<line.length(); i++){
          
          if(line.charAt(i) == '\"'){
            if(countQuote == 0) countQuote++;
            else countQuote = 0;
          } 
          if(line.charAt(i) == ','){
            if(countQuote == 0)IndiceOfComma.add(i);
          }  
       }

       String  artistName = line.substring(IndiceOfComma.get(1), IndiceOfComma.get(2));
             if(artistName.contains("\"")){
                artistName = artistName.substring(2,artistName.length()-1);
             }
             else{
                artistName = artistName.substring(1,artistName.length());
             }

       return artistName ;
    }

}