package src.jsonapi_y;

// JSON - para interpretar los datos del servidor
	import org.json.simple.JSONObject;
	//import org.json.simple.JSONArray;
	import org.json.simple.parser.ParseException;
	import org.json.simple.parser.JSONParser;

public class JSONapi_y_basic {
	public static void main(String[] args) throws Exception {
        decode();
    }
	
	public static void decode(/*String str*/) {
	      JSONParser parser = new JSONParser();
	      try{
	    	 String s = "{\"nombre0\" : \"cero\", \"nombre1\" : \"uno\", \"nombre2\" : \"dos\", \"nombre3\" : \"tres\"}";
	         
	    	 Object obj = parser.parse(s);	// Analizador semántico (parser)
	    	 
	         JSONObject object = (JSONObject)obj;	// Objeto que almacena todo el conjunto
	         String objeto0 = (String)object.get("nombre0");	// Lectura de la etiqueta "nombre0"
	         System.out.println("El contenido de nombre0 es: " + objeto0);
	      }
	      catch(ParseException e){
	         e.printStackTrace();
	      }
	  }
}