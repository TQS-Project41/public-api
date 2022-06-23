package com.tqs.project.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.json.simple.JSONArray;  
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NominatimService {
    

    public Map<String,Double> getAddress(String address,String city,String zipcode,String country) throws IOException, InterruptedException, ParseException {
        String URL="https://nominatim.openstreetmap.org/search?q="+address+" "+city+" "+ zipcode+" "+country+" &format=json";
        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create(URL.replaceAll(" ", "%20")))
		.method("GET", HttpRequest.BodyPublishers.noBody())
		.build();

        Map<String,Double> ret = new HashMap<>();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request,BodyHandlers.ofString() );
        JSONParser parser = new JSONParser();  
        JSONArray json = (JSONArray) parser.parse(response.body()); 
        if (json.size()==0){
            return ret ;
        }
       
        JSONObject tmp = (JSONObject)json.get(0);
        ret.put("lon", Double.parseDouble((String) tmp.get("lon")));
        ret.put("lat", Double.parseDouble((String) tmp.get("lat")));
        return ret; 
    } 
}
