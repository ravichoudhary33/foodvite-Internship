/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foursquare.scrapper;

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
public class FourSquareScrapper {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException, IOException {
        // TODO code application logic here
        String urlString = "https://api.foursquare.com/v2/venues/search?categoryId=4d4b7105d754a06374d81259&limit=50&ll=1.3521,103.8198&client_id=I1F5QCHNLGDK4TIIT44X3O2TKQAHZ0V5UNUKJTBCJLQ3L4KF&client_secret=0RKXFCU2AOTEF1WX0KYRAYZEBEH1XDHFMDZTKOCM32D0W231&v=20170508";
        
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        URL url = null;
        try{
            url = new URL(urlString);
    
        }catch(MalformedURLException e){
            System.out.println(e.getMessage());
        }
        
        urlConnection = (HttpURLConnection) url.openConnection();
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
//        System.out.println(result);
        

        JSONObject baseResponse = (JSONObject) JSONValue.parse(result);
        JSONObject response = (JSONObject) baseResponse.get("response");
        JSONArray venues = (JSONArray) response.get("venues");
        for(int i = 0; i < venues.size(); i++){
            JSONObject venue = (JSONObject) venues.get(i);
            String name = (String) venue.get("name");
            JSONObject location = (JSONObject) venue.get("location");
            String address = (String) location.get("address");
            String city = (String) location.get("city");
            System.out.println("Venue: [" + name + "], Address: [" + address + "], City: [" + city + "]\n");
        }

        
        
    }
    
}
