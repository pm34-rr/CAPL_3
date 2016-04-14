import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static String            fullText;
    public static ArrayList<String> nearWords;
    public static String[]          words;

    public static String            uniqueWord;
    public static int               wordsCount;
    public static int               uniqueCount;

    public static int delta( String s ) {
        int count = 0;
        for ( int i = 0, n = Math.min( s.length(), uniqueWord.length() ); i < n; ++i ) {
            if ( uniqueWord.charAt( i ) == s.charAt( i ) )
                ++count;
        }
        return s.length() - count;
    }

    public static void readParameters() {
        System.out.println( "Write unique word" );
        uniqueWord = System.console().readLine();

        System.out.println( "Write count of words" );
        wordsCount = Integer.parseInt( System.console().readLine() );
    }

    public static void readText( String path ) throws IOException {
        byte[] bytes = Files.readAllBytes( Paths.get(path) );
        fullText = new String( bytes );
        words = fullText.split( "[\n ]" );
    }

    public static void makeUnique(  ) {
        Arrays.sort( words );
        int posToSwap = 0;
        uniqueCount = 1;
        for ( int i = 0, n = words.length - 1; i < n; ++i ) {
            if ( words[i].compareTo( words[i+1] ) != 0 ) {
                ++posToSwap;
                if ( posToSwap != (i+1) )
                    words[posToSwap] = words[i+1];
                ++uniqueCount;
            }
        }
        System.out.println( "Res:" );
        for ( String s : words )
            System.out.println( s );
    }

    public static void getResult() {
        Arrays.sort( words, 0, uniqueCount, new Comparator<String>() {
            public int compare( String s1, String s2 ) {
                return delta( s1 ) - delta( s2 );
            }
        } );
    }

    public static void writeAnswer() {

        for ( int i = 0, n = Math.min( wordsCount, uniqueCount ); i < n; ++i )
            System.out.println( words[i] );
    }

    public static void main(String[] args) throws IOException {
        readParameters();
        int count = 1;
        Writer writer = new FileWriter( "output.txt" );
        for ( int i = 0; i < 1; ++i ) {
            String path = "strings" + String.valueOf( count ) + "000000.txt";
            readText( path );
            long begin = System.currentTimeMillis();
            makeUnique();
            getResult();
            long end = System.currentTimeMillis() - begin;
            // writeAnswer();
            System.out.println( uniqueCount );
            writer.write( count + "000000" + " " + end + "\n" );
            count *= 2;
        }
        writer.close();
    }
}