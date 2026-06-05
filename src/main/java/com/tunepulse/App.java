package com.tunepulse;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args ) throws Exception{
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter song title: ");
        String tuneTitle = scanner.nextLine();

        System.out.print("Enter Artist: ");
        String tuneArtist = scanner.nextLine();
        
        getAPI.findSong(tuneTitle, tuneArtist);
    }


    // public static void main( String[] args ) throws Exception{
    //     getAPI.findSong( "Perm", "Bruno Mars" );
    //}
}
