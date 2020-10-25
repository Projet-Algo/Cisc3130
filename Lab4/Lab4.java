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
  

  // Custom class Playlist for creating a playlist object
class Playlist{
    private Song first;

    //constructor
    Playlist(){
        first = null;
    }
    public boolean isEmpty(){  // method to check if the play list is empty

        return (first == null);
    }
    public void addSong(String track){ // method to add a new song in the play list
     Song temp = new Song(track);

        temp.next = first;
        first = temp;
    }
    public Song listenToSong(){   // retrieves the next song to listen to
      if(isEmpty()) {
        System.out.println(" End of the playList");

        return first;
      }
      else{
        Song temp = first;
        first = first.next;
        return temp;  // ???
      }
    }

  // it checks if the track  with the name "name " exist already
   public boolean find(String name){
    Song current = first;
 
     while(current != null){
       if(current.track.equals(name)) return true;
       current = current.next;
     }
    return false;
   }


  // the Function that sortes the linked list
  public void sortList() {  
    //songtrack current will point to first  
    Song current = first, index = null;  
    String temp;  
      
    if(first == null) {  
        return;  
    }  
    else {  
        while(current != null) {  
            //Songtrack  index will point to Songtrack next to current  
            index = current.next;  
              
            while(index != null) {  
                //If current trackname is greater than index's name, swap the data between them  
                if(Character.toUpperCase(current.track.charAt(0)) > Character.toUpperCase(index.track.charAt(0))) {   
                    temp = current.track;  
                    current.track = index.track;  
                    index.track = temp;  
                }  
                index = index.next;  
            }  
            current = current.next;  
        }      
    }  
  }

      // it prints the contains of the list
  public void display(PrintStream ps){
    Song current = first;
    while(current != null){
      ps.println(current.track);
      current = current.next;
    }
  }
}

   //creates a linked list that stores songs from an array of text file
class MyQueue extends Playlist{


    String [] fileName;
 
    MyQueue(String [] filenames){
      Scanner sc;

      try{

        for(int i=0; i<filenames.length; i++){
            sc = new Scanner(new File(filenames[i]));

            while(sc.hasNextLine()){
                String trackname = Filter(sc.nextLine());
                if(!this.find(trackname)) this.addSong(trackname);
            }
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

/* The SongHistoryList implementation */

class SongHistoryList{
  private Song first;
  //constructor
  SongHistoryList(){
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

  public Song lastListened(){
    // retrieves the next song to listen to

    if(this.isEmpty()) {
      System.out.println(" End of the playList history");

      return this.first;
    }
    else{
      Song temp = this.first;
      first = first.next;
      return temp;  // ???
    }
  }

  public void display(PrintStream ps){
    Song current = first;
    while(current != null){
      ps.println(current.track);
      current = current.next;
    }
  }

}

class MyApp{

    public static void main(String[] args) throws FileNotFoundException {
       
        PrintStream ps = new PrintStream("exampleOutput");

        String [] myFiles = {"week1.csv", "week2.csv", "week3.csv"};

        SongHistoryList PlayListHistory = new SongHistoryList(); // this is the play list history variable
    
        MyQueue allTheWeeks = new MyQueue(myFiles); // the variable that contain all the song's track from the three weeks
          
          allTheWeeks.sortList();
          
          ps.println("\n   Play List     \n\n");
          for(int i=0; i< 30; i++){
            String listen = allTheWeeks.listenToSong().track;
            ps.println(listen);
            PlayListHistory.addSong(listen);
          }

          ps.println("\n\n   Play List History  \n\n");
          PlayListHistory.display(ps);


          ps.close();
    }
   
    
}
