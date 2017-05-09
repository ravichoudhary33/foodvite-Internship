/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zomato.scrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author ravi
 */
public class ZomatoScrapper {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException, IOException {
        // TODO code application logic here
        String urlString = "https://developers.zomato.com/api/v2.1/search";
        String API_KEY = "ce4a83fdc7c9cf9d260678b8925fc2c4";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        URL url = null;
        try{
            url = new URL(urlString);
    
        }catch(MalformedURLException e){
            System.out.println(e.getMessage());
        }
        
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Accept", " application/json");
        urlConnection.setRequestProperty("user-key", " "+API_KEY);
        urlConnection.setReadTimeout(10000);
        urlConnection.setConnectTimeout(15000);
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
            
        inputStream = urlConnection.getInputStream();
            
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        
        String result = output.toString();
        
//        for(int i = 0; i < result.length(); i++){
//            System.out.print(result.charAt(i));
//        }

        JSONObject response = (JSONObject) JSONValue.parse(result);
        System.out.println("Total response = " + response.get("results_found"));
//        System.out.println(response);
        JSONArray restaurants = (JSONArray) response.get("restaurants");
        
        for(int i = 0; i < restaurants.size(); i++){
            JSONObject restaurantInfo = (JSONObject) restaurants.get(i);
//            String name = (String) restaurant.get("name");
            JSONObject restaurant = (JSONObject) restaurantInfo.get("restaurant");
            System.out.print("Name: " + restaurant.get("name"));
            System.out.println(", Cuisines: [" + restaurant.get("cuisines") + "]");
        }
        
        
        
    }
    
}
