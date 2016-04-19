

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
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
        Scanner scanf=new Scanner(System.in);
        //Obtener los datos del DNIe
        ObtenerDatos od = new ObtenerDatos();
        PeticionHttp ch =new PeticionHttp();
        
        String nif = od.LeerNIF();
        String nombre=od.LeerNombre();
        String apellido=od.LeerApellido();
        
        
        //Para comprobaciones sin lector de tarjetas
        /*String nif="26258810D";
        String nombre="Juan Jose";
        String apellido="Martos Muñoz";
                */
        
        String usuario;
        System.out.println("NIF: "+nif);
        System.out.println("Nombre: "+nombre);
        System.out.println("Apellido: "+apellido);
        
        //Pedimos clave al usuario
        String clave;
        clave=od.Clave();
        
        //Concatenamos nuestro Nombre y apellido para generar nuestro usuario.
        
        usuario=Character.toString(nombre.charAt(0));
        usuario=usuario.concat(apellido.substring(0,6));
        usuario=usuario.concat(Character.toString(apellido.charAt(7)));
        //Hemos puesto el usuario en minúscula para evitar posibles problemas.
        usuario=usuario.toLowerCase();
        System.out.println("Usuario: "+usuario);
        
        //Codificamos en base64 el usuario
        byte[] message = usuario.getBytes("UTF-8");
        String usuario64 = DatatypeConverter.printBase64Binary(message);
        //Eliminamos el "=" que sale al final.
        usuario64=usuario64.substring(0,usuario64.length()-1);
        System.out.println("usuario Codificado: "+usuario64);
        
        //Hago hash de dni y codifico en base64
        String dnihash;
        //Lo ponemos en minúscula para evitar posibles errores
        nif=nif.toLowerCase();
        dnihash=od.Sha1(nif);
        byte[] message1 = dnihash.getBytes("UTF-8");
        String dni64 = DatatypeConverter.printBase64Binary(message1);
        //Eliminamos los dos "=" que aparecen al final
        dni64=dni64.substring(0,dni64.length()-2);
        System.out.println("dni Codificado: "+dni64);
        
        //Hago hash y codifico en base64 la clave
        String clavehash;
        clavehash=od.Sha1(clave);
        byte[] message2 = clavehash.getBytes("UTF-8");
        String clave64 = DatatypeConverter.printBase64Binary(message2);
        //Elliminamos los dos "=" que aparecen al final
        clave64=clave64.substring(0,clave64.length()-2);
        System.out.println("clave Codificada: "+clave64);
        
        //byte[] decoded = DatatypeConverter.parseBase64Binary(encoded); (opcional)
        //Decodificar(opcional)
        //System.out.println(new String(decoded, "UTF-8"));
        System.out.println("¿Desea codificar el envio mediante un codigo de auntenticacion de mensaje MAC? (si,no)");
        String ok;
        ok=scanf.nextLine();
        
        if(ok.contains("si")){
            //Esta es la informacion que mandamos al servidor empleando la codificacion MAC(autenticaMac.php)
            String InformacionEnviarMac="user="+usuario64+"&dni="+dni64+"&password="+clave64;
            //Creamos la uri que pasamos a nuestro PeticionHttp.java
            String uri="http://localhost:8082/dnie/autenticaMac.php?"+InformacionEnviarMac;
            String peticion =ch.get(uri);
        }
        else{
        //Esta es la informacion que mandamos al servidor autentica.php
        String InformacionEnviar="user="+usuario+"&dni="+nif+"&password="+clave;
        //Creamos la uri que pasamos a nuestro PeticionHttp.java
        String uri="http://localhost:8082/dniebasico/autentica.php?"+InformacionEnviar;
        String peticion =ch.get(uri);
        }
        
    }

}
