import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintStream;
import java.io.FileNotFoundException;


/* The Playlist implementation */

class Song {
    public String track;
    public Song next;
    //constructors
    
    Song(String track){
        this.track = track;
        this.next = null;
    }

  }
  
/* The SongHistoryList implementation */
class SongHistoryList {
    private Song first;
    
    SongHistoryList(){
      first = null;
    }
    public boolean isEmpty(){
        return (first == null);
    }
    public void addSong(Song s){
      
      s.next = first;
      first = s;


    }
    public String lastListened(){
      // retrieves the next song to listen to
      if(isEmpty()) return "the SongHistory is Empty"; 
      else{
        Song temp = first;
        first = first.next;

        return temp.track;
      }
    }
  }


  // Custom class Playlist for creating a playlist object
class Playlist{
    private Song first;
    Playlist(){
      // constructor for creating a new list
        first = null;
    }
    public boolean isEmpty(){

        return (first == null);
    }
    public void addSong(String track){
     Song temp = new Song(track);

        temp.next = first;
        first = temp;
    }
    public Song listenToSong(){
      // retrieves the next song to listen to
      return first;  // ???
    }

      // it prints the contains of the list
  public void display(){
    Song current = first;
    while(current != null){
      System.out.println(current.track);
      current = current.next;
    }
  }
}



class MyQueue extends Playlist{


    String fileName;
    /* example of a constructor that creates a linked list that stores songs from a text file
       this method header says it takes a string as filename, but you don't have to stick with that */
    public MyQueue(String filename){
      Scanner sc;
      //PrintStream ps;

      try{
        sc = new Scanner(new File(filename));

        while(sc.hasNextLine()){
            String trackname = Filter(sc.nextLine());
            this.addSong(trackname);
        }
      }
      catch (FileNotFoundException e) {
        e.printStackTrace();
      }


    }

    
    /* the function below helps filter the informations get from the input file
    and return the track name*/
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

       String trackName = line.substring(IndiceOfComma.get(0), IndiceOfComma.get(1));  // "WAP (feat. Megan Thee Stallion)"
              if(trackName.contains("(")){
                   trackName = trackName.substring(2,trackName.indexOf("("));
              } 
              else if(trackName.contains("feat.")){
                   trackName = trackName.substring(2,trackName.indexOf("feat.") - 2); // the "- 2" helps ignore the " - " afterthe track name
              }
              else if(trackName.contains("\"")){
                   trackName = trackName.substring(2,trackName.length()-1);
              } 
              else{
                trackName = trackName.substring(1,trackName.length()); 
              }
       
       return trackName ;
    }
}


/* This class is an example for working with multiple text files */
class MyApp{

    public static void main(String[] args) {
        // The files you'll be reading stored in a data structure to make it iterable 
        String [] myFiles = {"week1.csv", "week2.csv", "week3.csv"};
    
        ArrayList<MyQueue> allTheWeeks = new ArrayList<>();
    
        // Iterate through myFiles to read in each of the files
        for (int i=0; i < myFiles.length-1; i++){
        MyQueue dataExtract = new MyQueue(myFiles[i]);
        allTheWeeks.add(dataExtract);
        }

        for(int i=0; i<allTheWeeks.size(); i++){
            allTheWeeks.get(i).display();
        }
    }
  
} //end MyApp
