package src.jsonapi_y;

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
	//import org.json.simple.JSONArray;
	import org.json.simple.parser.ParseException;
	import org.json.simple.parser.JSONParser;

public class JSONapi_y_complete {
	public static void main(String[] args) throws Exception {
		// Llamada a la API
        String url = "https://apis.bbvabancomer.com/datathon/zipcodes/44190/age_distribution?date_min=20140101&date_max=20140331&group_by=month";
        HttpClientBuilder hcBuilder = HttpClients.custom();
        HttpClient client = hcBuilder.build() ;
        HttpGet request = new HttpGet(url);
        // Configuracion de los parametros de cabecera
        request.addHeader("Accept", "application/json");
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Authorization", "Basic YXBwLmJidmEueWFnb2dnOjAwODc1MTBlNzBjYjJjMGU3ZTU5MmQyNzA3MzU3MGYzMGFhNTAxMDU=");
        // Ejecucion de la llamada
        HttpResponse response = client.execute(request);
        System.out.println("\nEnviando 'GET' to " + url);
        System.out.println("Respuesta HTTP: " + response.getStatusLine().getStatusCode());
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        // Lectura de la respuesta
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        String serverData = result.toString();
        System.out.println(serverData);
        decode(serverData);
    }
	
	public static void decode(String serverData) {
		System.out.println("Lectura de datos del servidor:");
		JSONParser parser = new JSONParser();	// Analizador semántico (parser)
		
		try {
			Object obj = parser.parse(serverData);
			
			JSONObject objeto = (JSONObject)obj;
			System.out.println("  Result:");
			System.out.println("	Code: " + ((JSONObject)objeto.get("result")).get("code"));
			System.out.println("	Info: " + ((JSONObject)objeto.get("result")).get("info"));
		}
		catch (ParseException e){
			e.printStackTrace();
		}
	}
	
}