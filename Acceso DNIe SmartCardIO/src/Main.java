

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.DatatypeConverter;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author toni
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        ByteArrayInputStream bais=null;
       //read("cert.cer");
       
        //Este trozo es para leer un certificado.
        
      /* FileInputStream fis = new FileInputStream("cert.cer");
      
       
       byte value[] = new byte[fis.available()];
         fis.read(value);
        bais = new ByteArrayInputStream(value);
       */
        
        //TODO: Obtener los datos del DNIe
        ObtenerDatos od = new ObtenerDatos();
        //String nif = od.LeerNIF();
        //String nombre=od.LeerNombre();
        //String apellido=od.LeerApellido();
        String nif="26258810D";
        String nombre="Juan Jose";
        String apellido="Martos Muñoz";
        String usuario;
        System.out.println("NIF: "+nif);
        System.out.println("Nombre: "+nombre);
        System.out.println("Apellido: "+apellido);
        
        //Pedimos clave al usuario
        String clave;
        clave=od.Clave();
        
        //Concatenamos nuestro Nombre y apellido para generar nuestro usuario
        
        usuario=Character.toString(nombre.charAt(0));
        usuario=usuario.concat(apellido.substring(0,6));
        usuario=usuario.concat(Character.toString(apellido.charAt(7)));
        System.out.println("Usuario: "+usuario);
        
        //Junto clave y usuario
        usuario=clave+usuario;
        String informacion="user"+usuario+"dni"+nif+"password"+clave;
        
        //Generamos el hash Sha1
        String Sha1;
                
        Sha1=od.Sha1(informacion);
        System.out.println("Hash1: "+Sha1);
        
        //Codificamos en base a 64
        
        byte[] message = Sha1.getBytes("UTF-8");
        String encoded = DatatypeConverter.printBase64Binary(message);
        byte[] decoded = DatatypeConverter.parseBase64Binary(encoded);

        System.out.println("Codificado: "+encoded);
        //Decodificar(opcional)
        //System.out.println(new String(decoded, "UTF-8"));
        
        
        //TODO: Autenticarse en el servidor
        
    
    }

}
