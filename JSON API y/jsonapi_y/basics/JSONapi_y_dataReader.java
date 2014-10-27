package basics;

import java.io.BufferedReader;
import java.io.InputStreamReader;

// APACHE  - para la REST API (coger los datos del servidor)
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

public class JSONapi_y_dataReader {
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
    }
}