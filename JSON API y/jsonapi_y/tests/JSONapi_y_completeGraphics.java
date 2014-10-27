package tests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.ArrayList;





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
	
import org.jfree.chart.ChartFactory;
// JFREECHART - para crear las gráficas de datos
	import org.jfree.chart.*;
	import org.jfree.data.*;
	import org.jfree.data.general.DefaultPieDataset;

public class JSONapi_y_completeGraphics {
	static ArrayList<ZipAgeStat> statsArray = new ArrayList<ZipAgeStat>();	// Array de estadisticas
	
	public static void main(String[] args) throws Exception {
		// Petición de datos al usuario
		System.out.print("Código postal: ");
		Scanner input = new Scanner(System.in);
		int zip = input.nextInt();
		// Llamada a la API
        String url = "https://apis.bbvabancomer.com/datathon/zipcodes/" + String.format("%05d", zip) + "/age_distribution?date_min=20140101&date_max=20140331&group_by=month";
        HttpClientBuilder hcBuilder = HttpClients.custom();
        HttpClient client = hcBuilder.build() ;
        HttpGet request = new HttpGet(url);
        // Configuracion de los parametros de cabecera
        request.addHeader("Accept", "application/json");
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Authorization", "Basic YXBwLmJidmEueWFnb2dnOjAwODc1MTBlNzBjYjJjMGU3ZTU5MmQyNzA3MzU3MGYzMGFhNTAxMDU=");
        // Ejecucion de la llamada
        HttpResponse response = client.execute(request);
        System.out.println("Enviando 'GET' to " + url);
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
        System.out.print("Mes (1 - 3): ");
        short inputMonth = (short)(input.nextShort() - 1);
        System.out.print("¿Num_payments [1] o avg [0]?: ");
        if(input.nextShort() == 1){	// Num_payments
            createGraphic(inputMonth, true);
        }else{	// Avg
        	createGraphic(inputMonth, false);
        }
        input.close();
    }
	
	public static void decode(String serverData) {
		System.out.println("Lectura de datos del servidor:");
		JSONParser parser = new JSONParser();	// Analizador semantico (parser)
		
		try {
			Object obj = parser.parse(serverData);	// Lectura de datos recogidos del servidor
			
			JSONObject object = (JSONObject)obj;
			System.out.println("  Result:");
			System.out.println("	Code: " + ((JSONObject)object.get("result")).get("code"));
			System.out.println("	Info: " + ((JSONObject)object.get("result")).get("info"));
			System.out.println("  Data:");
			JSONArray stats = (JSONArray)(((JSONObject)object.get("data")).get("stats"));
			System.out.println("	Stats:");
			byte month = 0, age = 0;
			for(month = 0; month <= 2; month++){	// Repeticion para todos los meses (3 -> 0 al 2)
				statsArray.add(new ZipAgeStat());	// Creación del objeto (que almacena fecha y grupo de edad) en el array, para esta vuelta
				statsArray.get(month).date = Integer.parseInt((String)((JSONObject)stats.get(month)).get("date"));	// Escritura de la fecha en el objeto
				System.out.println("		Date: " + statsArray.get(month).date);
				JSONArray histogram = (JSONArray)((JSONObject)stats.get(month)).get("histogram");
				System.out.println("		Histogram:");
				for(age = 0; age <= 7; age++){	// Repeticion para todos los grupos de edad (8 -> 0 al 7)
					statsArray.get(month).ageGroup.add(new AgeGroup());	// Creación del objeto de los grupos de edad en el sub-array, para esta vuelta
					statsArray.get(month).ageGroup.get(age).group = (String)((JSONObject)histogram.get(age)).get("ages");
					System.out.println("			Ages: " + statsArray.get(month).ageGroup.get(age).group);
					statsArray.get(month).ageGroup.get(age).numPayments = (long)((JSONObject)histogram.get(age)).get("num_payments");
					System.out.println("			Num_payments: " + statsArray.get(month).ageGroup.get(age).numPayments);
					statsArray.get(month).ageGroup.get(age).avg = (double)((JSONObject)histogram.get(age)).get("avg");
					System.out.println("			Avg: " + statsArray.get(month).ageGroup.get(age).avg);
					System.out.println("			----------");
				}
			}
		}
		catch (ParseException e){
			e.printStackTrace();
		}
	}
	
	public static void createGraphic(int queryDate, boolean dataToShow) {
        DefaultPieDataset graphic = new DefaultPieDataset();
    	int i;
    	double k;
    	String reading;
        if(dataToShow == true){	// Num_payments
        	reading = "Num_payments";
        	long total = 0;
        	for(i = 0; i <= 7; i++){	// Creacion del total
        		total = total + statsArray.get(queryDate).ageGroup.get(i).numPayments;
        	}
        	k = ((double)100) / total;
        	for(i = 0; i <= 7; i++){	// Introduccion de datos en la grafica
        		graphic.setValue(statsArray.get(queryDate).ageGroup.get(i).group, k * (double)statsArray.get(queryDate).ageGroup.get(i).numPayments);
        	}
        }else{	// Avg
        	reading = "Avg";
        	double total = 0;
        	for(i = 0; i <= 7; i++){	//Creacion del total
        		total = total + statsArray.get(queryDate).ageGroup.get(i).avg;
        	}
        	k = 100 / total;
        	for(i = 0; i <= 7; i++){	// Introduccion de datos en la grafica
        		graphic.setValue(statsArray.get(queryDate).ageGroup.get(i).group, k * statsArray.get(queryDate).ageGroup.get(i).avg);
        	}
        }
        JFreeChart chart = ChartFactory.createPieChart3D(statsArray.get(queryDate).date + " / " + reading, graphic, true, true, true);	// Objeto de la grafica
        try {
        	ChartUtilities.saveChartAsPNG(new java.io.File("Graph.png"), chart, 1000, 600);	// Generacion del archivo de grafica
        	System.out.println("Grafica generada con éxito.");
        }
        catch (java.io.IOException exc) {
        	System.err.println("Problema escribiendo imagen.");
        }
	}
	
}