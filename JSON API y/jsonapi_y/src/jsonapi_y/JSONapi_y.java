package jsonapi_y;

import java.io.BufferedReader;
import java.io.InputStreamReader;

// APACHE  - para la REST API (coger los datos del servidor)
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

// JSON - para interpretar los datos del servidor
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class JSONapi_y {
	public static void main(String[] args) throws Exception {
        //API end point to make a call
        String url = "https://apis.bbvabancomer.com/datathon/zipcodes/44190/age_distribution?date_min=20140101&date_max=20140331&group_by=month";
        HttpClientBuilder hcBuilder = HttpClients.custom();
        HttpClient client = hcBuilder.build() ;
        HttpGet request = new HttpGet(url);
        //Setting header parameters
        request.addHeader("Accept", "application/json");
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Authorization", "Basic YXBwLmJidmEueWFnb2dnOjAwODc1MTBlNzBjYjJjMGU3ZTU5MmQyNzA3MzU3MGYzMGFhNTAxMDU=");
        //Executing the call
        HttpResponse response = client.execute(request);
        System.out.println("\nSending 'GET' to " + url);
        System.out.println("HTTP Response: " + response.getStatusLine().getStatusCode());
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        //Reading the response
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result.toString());
        decode();
        /*String serverOutput = result.toString();
        decode(serverOutput);*/
    }
	
	public static void decode(/*String str*/) 
	   {
	      JSONParser parser = new JSONParser();
	      try{
	    	 String s = "{\"tag1\" : \"contenido1\", \"tag2\" : \"contenido2\", \"tag3\" : [{\"subtag1\" : \"subcontenido1\"}, {\"subtag2\" : \"subcontenido2\"}, {\"subtag3\": \"subcontenido3\"}]}";
	         
	    	 Object obj = parser.parse(s);
	         JSONObject jsonObject = (JSONObject)obj;
	         
	         String tag1 = (String)jsonObject.get("tag1");
	         System.out.println("El contenido de tag1 es: " + tag1);
	      }
	      catch(ParseException e){
	         e.printStackTrace();
	      }
	   }
}