package com.tunepulse;

import java.net.URI;
import java.net.http.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import com.google.gson.*;

public class getAPI {
    //$env:GETSONG_API_KEY=""
    //mvn clean compile exec:java "-Dexec.mainClass=com.tunepulse.App"
    private static final String keyAPI = System.getenv("GETSONG_API_KEY");
    private static final String webSiteURL = "https://api.getsong.co";


    //this method takes the song title and artist
    public static void findSong(String songTitle, String artist) throws Exception {

        //URLEncoder.encode() changes the URL to be hidden and have no spaces
        String encodedeSong = URLEncoder.encode(songTitle, StandardCharsets.UTF_8);
        String encodedArtist = URLEncoder.encode(artist,    StandardCharsets.UTF_8);


        //This is builing the URL
        String url = webSiteURL + "/search/?api_key=" + keyAPI + "&type=both" + "&lookup=song:"
            + encodedeSong + "%20artist:" + encodedArtist;

        //Built in tool that makes requests
        HttpClient client = HttpClient.newHttpClient();

        //This is a builder pattern 
        //it sends the request, adds the http header(makes it look like its http when its not),
        //fetches data, creates the object
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("User-Agent", "Mozilla/5.0")
            .GET()
            .build();
        
        // generic type (return a string)= sends it over the internet(, tells Java how to read it)
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        //=tool to convert raw text into Java object(getss the raw text.top level response)
        //=.looks in object for the key "search" and gets its value
        JsonObject root = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonArray results = root.getAsJsonArray("search");

        for (JsonElement el : results) {
            JsonObject song = el.getAsJsonObject();
            JsonObject artist_ = song.getAsJsonObject("artist");

            String title = song.get("title").getAsString();
            String artistName = artist_.get("name").getAsString();
            String bpm = song.get("tempo").getAsString();
            String key = song.get("key_of").getAsString();
            String openKey = song.get("open_key").getAsString();

            System.out.println("Title: " + title);
            System.out.println("Artist: " + artistName);
            System.out.println("BPM: " + bpm);
            System.out.println("Key: " + key);
            System.out.println("Camelot Key: " + openKey);
            break;
        }
    }
}