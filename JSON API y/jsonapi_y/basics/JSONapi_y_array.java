package basics;

// JSON - para interpretar los datos del servidor
	//import org.json.simple.JSONObject;
	import org.json.simple.JSONArray;
	import org.json.simple.parser.ParseException;
	import org.json.simple.parser.JSONParser;

public class JSONapi_y_array {
	public static void main(String[] args) throws Exception {
        decode();
    }
	
	public static void decode() {
	      JSONParser parser = new JSONParser();
	      try{
	    	 String s = "[\"obj0\", {\"nombre_obj_1\" : \"contenido_obj_1\"}, [\"objeto2.0\", {\"nombre_obj_2.1\" : \"contenido_objeto_2.1\"}, {\"nombre_obj_2.2\" : \"contenido_objeto_2.2\"}]]";
	         
	    	 Object obj = parser.parse(s);	// Analizador semántico (parser)
	    	 
	         JSONArray array = (JSONArray)obj;	// Array (toda la string es en sí un array)
	         String objeto0 = (String)array.get(0);	// Lectura del primer objeto del array
	         System.out.println("El primer item [0] es: " + objeto0);
	         String subObjeto1 = (String)(((JSONArray)array.get(2)).get(0));	// Lectura del primer objeto del tercer objeto del array, que es otro array
	         System.out.println("El primer item [0] del array [3] es: " + subObjeto1);
	      }
	      catch(ParseException e){
	         e.printStackTrace();
	      }
	  }
}