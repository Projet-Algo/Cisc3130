import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.PrintStream;
import java.io.FileNotFoundException;

/* The Playlist implementation */

class Song {
    public String track;
    public int streamsAverageCount;
    public String artistName;
    public int artistAverage;
    public Song next;
    // constructors

    Song(String track, int streamsAverageCount, String artistName, int artistAverage) {
        this.track = track;
        this.streamsAverageCount = streamsAverageCount;
        this.artistName = artistName;
        this.artistAverage = artistAverage;
        this.next = null;
    }

}

// Custom class Playlist for creating a playlist object
class Playlist {
    private Song first;

    // constructor
    Playlist() {
        first = null;
    }

    public boolean isEmpty() { // method to check if the play list is empty

        return (first == null);
    }

    public void addSong(String track, int streamsAverageCount, String artistName, int artistAverage) { // method to add
                                                                                                       // a new song in
                                                                                                       // the play list
        Song temp = new Song(track, streamsAverageCount, artistName, artistAverage);

        temp.next = first;
        first = temp;
    }

    // it checks if the track with the name "name " exist already
    public boolean find(String name) {
        Song current = first;

        while (current != null) {
            if (current.track.equals(name))
                return true;
            current = current.next;
        }
        return false;
    }

    // find artist
    public int findAposition(String name) {
        Song current = first;
        int result =1;

        while (current != null && !current.track.contains(name)) {
            current = current.next;
            result ++;
        }
        if(current == null) return -1;
        else return result;
        
    }



    // the Function that sortes the linked list

    public void sortList() {  
        //songtrack current will point to first  
        Song current = first, index = null;  
        String temp1, temp2; int temp3, temp4;  
          
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
                        temp1 = current.track;
                        temp2 = current.artistName;
                        temp3 = current.artistAverage;
                        temp4 = current.streamsAverageCount;
                        current.track = index.track; 
                        current.artistName = index.artistName;
                        current.artistAverage = index.artistAverage;
                        current.streamsAverageCount = index.streamsAverageCount;
                        index.track = temp1;  
                        index.artistName =temp2;
                        index.artistAverage = temp3;
                        index.streamsAverageCount = temp4;
                    }  
                    index = index.next;  
                }  
                current = current.next;  
            }      
        }  
      }

    // it prints the contains of the list
    public void display(PrintStream ps) {
        Song current = first;
        while (current != null) {
            ps.println(current.track);
            current = current.next;
        }
    }

    // Selects song titles that fall alphabetically between start and end.

    public void subSet(String start, String end, PrintStream ps){
        Song current = first;
        int begin = this.findAposition(start);
        int fin = this.findAposition(end);
        if(begin<0 || fin<0) ps.println(" the interval doesn't exist or is not valid!!");
        else {
            int count =1;
            ps.println(" Using the method subSet to Filter the output From the position "+begin+" to "+fin+"\n");
            while(current != null ){

                if(count >= begin && count <= fin) ps.println(current.track);
                count++;
                current = current.next;
            }

        }
    }
}

// creates a linked list that stores songs from an array of text file
class MyQueue extends Playlist {

    String[] fileName;

    MyQueue(String[] filenames) {
        Scanner sc;

        try {

            HashMap<String, Integer> streams = new HashMap<>();
            HashMap<String, Integer> artist = new HashMap<>();
            

            for (int i = 0; i < filenames.length; i++) {
                sc = new Scanner(new File(filenames[i]));

                while (sc.hasNextLine()) {
                    String[] info = Filter(sc.nextLine());
                    int val;
                    if (streams.containsKey(info[0])) {
                        val = streams.get(info[0]);
                    } else val = 0;
                    streams.put(info[0], val + Integer.parseInt(info[2]));

                    if(!artist.containsKey(info[1])) artist.put(info[1], 1);
                    else{
                        int count = artist.get(info[1]) + 1;
                         artist.put(info[1], count);
                    }

                }
            }
         //artist.forEach((key, value) -> System.out.println(key+" "+value));
         for (int i = 0; i < filenames.length; i++) {
            sc = new Scanner(new File(filenames[i]));

            while (sc.hasNextLine()) {
                String[] info = Filter(sc.nextLine());
                
                if(!this.find(info[0])){
                    int streamsAverageCount = streams.get(info[0]) / 3;
                    int artistAverage = artist.get(info[1])/3;
                    this.addSong(info[0], streamsAverageCount, info[1], artistAverage);
                } 

            }
        }
      }
      catch (FileNotFoundException e) {
        e.printStackTrace();
      }


    }

    
    /* the function below helps filter the informations get from the input file
    and return the track name*/
    public  String[] Filter(String line){

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
        String  artistName = line.substring(IndiceOfComma.get(1), IndiceOfComma.get(2)); // ,"Cardi B", "Maddie & Tae"
             if(artistName.contains("\"")){
                artistName = artistName.substring(2,artistName.length()-1);
             }
             else{
                artistName = artistName.substring(1,artistName.length());
             }
        
        String streams = line.substring(IndiceOfComma.get(2)+1, IndiceOfComma.get(3));
       
         
         String [] result = {trackName, artistName, streams};
        return result ;
    }
}

class MyApp{

    public static void main(String[] args) throws FileNotFoundException {
       
        PrintStream ps = new PrintStream("exampleOutput");

        String [] myFiles = {"week1.csv", "week2.csv", "week3.csv"};
    
        MyQueue allTheWeeks = new MyQueue(myFiles); // the variable that contain all the song's track from the three weeks

          allTheWeeks.sortList();
          allTheWeeks.display(ps);
          ps.println();
          ps.println();
          ps.println();
          allTheWeeks.subSet("Circles","Dior", ps);
          ps.close();
    }
   
    
}
