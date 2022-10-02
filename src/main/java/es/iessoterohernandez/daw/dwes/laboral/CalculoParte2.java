
package es.iessoterohernandez.daw.dwes.laboral;

import static es.iessoterohernandez.daw.dwes.laboral.CalculaNominas.escribe;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;


public class CalculoParte2 {

    
    public static void main(String[] args) {
       
    
        try {
        	//Fichero de entrada
        	File fentrada = new File("empleados.txt");
	        FileReader fr = new FileReader(fentrada);
	        BufferedReader br = new BufferedReader(fr);
	        
	        //Fichero de texto de salida para actualizar el de entrada
        	File fentradaActualizada = new File("empleadosActualizados.txt");
	        FileWriter fw = new FileWriter(fentradaActualizada);
	        BufferedWriter bw = new BufferedWriter(fw);
	        
	        //Fichero binario de salida
        File fsalida = new File("salarios.dat");
	        FileOutputStream fos = new FileOutputStream(fsalida);
	        BufferedOutputStream bos = new BufferedOutputStream(fos);
	        OutputStreamWriter bs = new OutputStreamWriter(fos);
	        
	        //Variables
	        String line;;
	        Empleado e;
	        String []datosEmp;
	        
	        while (br.ready()) {
	        	//Leemos línea por línea el fichero de entrada
	        	line = br.readLine();
	        	datosEmp = line.split(";");
	        	if (datosEmp.length == 3) //en el caso de que en la línea solo haya 3 datos del empleado
	        		e = new Empleado(datosEmp[0], datosEmp[1], datosEmp[2]);
	        	else //en el caso de que vayan todos los datos
	        		e = new Empleado(datosEmp[0], datosEmp[1], datosEmp[2], Integer.parseInt(datosEmp[3]), Integer.parseInt(datosEmp[4]));

	        	escribe(e);
	        	
	        	//Escribimos en el fichero binario
	        	bos.write((e.dni + ";" + Nomina.sueldo(e)+'\n').getBytes());
	        	bos.write(e.dni + ";" + Nomina.sueldo(e)+'\n');
	        	
	        	if (e.nombre.equalsIgnoreCase("James Cosling")) {
	        		e.setCategoria(9);
	        	}else if (e.nombre.equalsIgnoreCase("Ada Lovealace")) {
	        		e.incAnyo();
	        	}

	        	//Actualizamos el fichero de entrada
	        	bw.write(e.nombre+";"+e.dni+";"+e.sexo+";"+e.getCategoria()+";"+e.anyos+'\n');
	        }
	        
	        //Cerramos todos los búferes
	        br.close();
	        bos.close();
	        bw.close();
	        
	        //renombramos para dejar el mismo fichero de entrada
	        fentrada.delete();
	        fentradaActualizada.renameTo(fentrada);
	                
	      }catch (FileNotFoundException e){
	    	  e.printStackTrace();
	      }catch (IOException e) {
	    	  e.printStackTrace();
	      }catch (DatosNoCorrectosException e) {
			e.printStackTrace();
	      }
		}
    }
    
    private static void escribe(Empleado e1) {
    	System.out.println(e1.imprime() +  " - Sueldo="+ Nomina.sueldo(e1));
    }
    }
    
}
