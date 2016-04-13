

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
        PeticionHttp ch =new PeticionHttp();
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
        usuario=usuario.toLowerCase();
        System.out.println("Usuario: "+usuario);
        
        //Codifico en base a 64 usuario
        byte[] message = usuario.getBytes("UTF-8");
        String usuario64 = DatatypeConverter.printBase64Binary(message);
        usuario64=usuario64.substring(0,usuario64.length()-1);
        System.out.println("usuario Codificado: "+usuario64);
        
        //Hago hash de dni y codifico en base a 64
        String dnihash;
        nif=nif.toLowerCase();
        dnihash=od.Sha1(nif);
        byte[] message1 = dnihash.getBytes("UTF-8");
        String dni64 = DatatypeConverter.printBase64Binary(message1);
        dni64=dni64.substring(0,dni64.length()-2);
        System.out.println("dni Codificado: "+dni64);
        
        //Hago hash y codifico en base 64 clave
        String clavehash;
        clavehash=od.Sha1(clave);
        byte[] message2 = clavehash.getBytes("UTF-8");
        String clave64 = DatatypeConverter.printBase64Binary(message2);
        clave64=clave64.substring(0,clave64.length()-2);
        System.out.println("clave Codificada: "+clave64);
        
        //byte[] decoded = DatatypeConverter.parseBase64Binary(encoded); (opcional)
        //Decodificar(opcional)
        //System.out.println(new String(decoded, "UTF-8"));
        
        //Esta es la informacion que mandamos al servidor
        String InformacionEnviar="user="+usuario64+"&dni="+dni64+"&password="+clave64;
        
        //Creamos la uri que pasamos a nuestro clientehttp.java
        String uri="http://localhost:8082/dnie/autentica.php?"+InformacionEnviar;
        
        String peticion =ch.get(uri);
       // System.out.println("Codificado: "+peticion);
        
       /* if(peticion.contains("incorrecto")){
            System.out.println("PETICION DENEGADA");
        }
        else System.out.println("PETICION CORRECTA");
        */
    }

}
