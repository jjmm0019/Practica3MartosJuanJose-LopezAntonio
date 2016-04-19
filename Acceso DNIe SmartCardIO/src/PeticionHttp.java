import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class PeticionHttp {

  public String get(String uri) {
      String line = null;
	try{
	  URL url = new URL(uri);
	  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	  conn.setRequestMethod("GET");
	  BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
          
          while((line=rd.readLine())!=null){
              //Para ver si la peticion se realiza correctamente o no
              System.out.println(line);
              if(line.endsWith("</h4>")){
              
              if(line.contains("Bienvenido")){
                  System.out.println("Correcto");
                  
              }
              else System.out.println("Incorrecto");
              }//line=line+"\n"+rd.readLine();
          }
	  rd.close();
	} 
	catch(Exception e){System.out.print(e);}
	
	return line;
  }
  
}

