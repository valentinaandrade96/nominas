
package es.iessoterohernandez.daw.dwes.laboral;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class EmpleadosDao {
    
    
      public static List<Empleado> getEmpleados() throws DatosNoCorrectosException {
		Connection con = Conexion.getConnection();
		ArrayList<Empleado> lista_empleados = new ArrayList<Empleado>();

		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from empleados");

			while (rs.next()) {
                           
				Empleado e = new Empleado(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4),
						rs.getInt(1));
				lista_empleados.add(e);
			}

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return lista_empleados;

	}

	public static List<Nomina> getNominas() {
		//Conexón a la base de datos
        Connection con = Conexion.getConnection();
        ArrayList<Nomina> lista_nominas = new ArrayList<Nomina>();
        String[] n = new String[2];	        
        try {    
	        Statement st=con.createStatement();
	        ResultSet rs=st.executeQuery("select dni, sueldo from nominas");
	        
			while (rs.next()) {
				Nomina nomina=new Nomina(rs.getString(1), rs.getInt(2));
				
				lista_nominas.add(nomina);
			}
        }catch (SQLException ex) {
        	ex.printStackTrace();
		}
        
		return lista_nominas;
	}
	
	public static boolean altaEmpleado(Empleado e) {
	 boolean insertado=false;
        String sql = "Insert into empleados (dni, nombre, sexo, categoria, anyos) VALUES (?,?,?,?,?)";
        String sqlDos="insert into nominas(dni) values (?)";
	        
        try {    Connection con = Conexion.getConnection();
	        PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			//Insertamos el empleado
	    stmt.setString(1, e.dni);
            stmt.setString(2, e.nombre);
            stmt.setString(3, e.sexo);
            stmt.setInt(4, e.getCategoria());
             stmt.setInt(5, e.getAnyos());
             
             
	        //Insertamos el sueldo del empleado
                PreparedStatement st = con.prepareStatement(sqlDos);
                 int SUELDO_BASE[] =
{50000, 70000, 90000, 110000, 130000,
150000, 170000, 190000, 210000, 230000};
                int sueldo=SUELDO_BASE[e.getCategoria()]+5000*e.getAnyos();
		stmt.setString(1, e.dni);
                stmt.setInt(2, sueldo);
                
                
                

			backup(e);
                         int affectedRows = stmt.executeUpdate();
            if(affectedRows<=0){

               insertado=false;
            }else{


                // Queries the DB
                insertado=true;
	
        }}catch (SQLException ex) {
	    	  ex.printStackTrace();
	    }return insertado; }
	
	
	

	
	public static void altaEmpleado(String nombreFichero) {
        String line;;
        Empleado e;
        String []datosEmp;
        
        try {
            //Fichero de entrada
            File fentrada = new File(nombreFichero);
            FileReader fr = new FileReader(fentrada);        
            BufferedReader br = new BufferedReader(fr);
            
	        while (br.ready()) {
	        	//Leemos línea por línea el fichero de entrada
	        	line = br.readLine();
	        	datosEmp = line.split(";");
	        	if (datosEmp.length == 3) //en el caso de que en la línea solo haya 3 datos del empleado
	        		e = new Empleado(datosEmp[0], datosEmp[1], datosEmp[2].toCharArray()[0]);
	        	else //en el caso de que vayan todos los datos
	        		e = new Empleado(datosEmp[0], datosEmp[1], datosEmp[2].toCharArray()[0], Integer.parseInt(datosEmp[3]), Integer.parseInt(datosEmp[4]));
	        	altaEmpleado(e);
//TO-DO	        updateSueldo(e.dni); //Comprobar
	        }
	        br.close();
        }catch (FileNotFoundException ex){
        	ex.printStackTrace();
	    }catch (IOException ex) {
	    	ex.printStackTrace();
	    }catch (DatosNoCorrectosException ex) {
	    	ex.printStackTrace();
	    }
	}
	
	
	/**
	 * @param dni
	 * @return
	 */
	public static int getSueldo(String dni) {
		//Conexón a la base de datos
        Connection con = Conexion.getConnection();
          String sql = "select sueldo from nominas where dni = ?";
        int sueldo=0;
        try {    
	        PreparedStatement stmt=con.prepareStatement(sql);
                 stmt.setString(1,dni);
	        ResultSet rs=stmt.executeQuery();
	        
	       
				while (rs.next()) {
					sueldo = rs.getInt(1);
				}
        }catch (SQLException ex) {
        	ex.printStackTrace();
                sueldo=0;
		}
        
		return sueldo;
	}
	
	/**
	 * @param dni
	 * @return
	 */
	public static boolean exists (String dni) {
		//Conexón a la base de datos
        Connection con = Conexion.getConnection();
        boolean exists = false;
        
        try {    
	        Statement st=con.createStatement();
	        ResultSet rs=st.executeQuery("select count(*) from empleados where dni='"+dni+"'");
	        
	        if (rs!=null)
				exists=true;
        }catch (SQLException ex) {
        	ex.printStackTrace();
		}
        
		return exists;
	}
	
	public static Empleado getEmpleado(String dni)throws DatosNoCorrectosException{
		//Conexón a la base de datos
        Connection con = Conexion.getConnection();
        Empleado e=null;
	        
        try {    
	        Statement st=con.createStatement();
	        ResultSet rs=st.executeQuery("select nombre, dni, sexo, categoria, anyos from empleados");
	        
	        //Solo puede haber un empleado con el mismo dni
			while (rs.next()) {
				e = new Empleado(rs.getString(1), rs.getString(2),  rs.getString(3), rs.getInt(4), rs.getInt(5));
			}
        }catch (SQLException ex) {
        	ex.printStackTrace();
    	}catch (DatosNoCorrectosException ex) {
    		ex.printStackTrace();
		}
        
		return e;
	}
	
	public static void updateEmpleado(Empleado e) {
            String sql="update nominas set sueldo=? where dni=?";
		//Conexón a la base de datos
        Connection con = Conexion.getConnection();
	    
        try {    
	        PreparedStatement st=con.prepareStatement(sql);
	      
	      st.setString(1, e.dni);
	       
			
		
        }catch (SQLException ex) {
	    	  ex.printStackTrace();
	    }
	}
	
	public static void updateSueldo(String dni) {
		//Conexón a la base de datos
        Connection con = Conexion.getConnection();
	        
             String sql="update nominas set sueldo=? where dni=?";
		//Conexón a la base de datos
       
	    
        try {    
	        PreparedStatement st=con.prepareStatement(sql);
	      
	      st.setString(1, dni);
	       
			
		
        }catch (SQLException ex) {
	    	  ex.printStackTrace();
	    }
	}
	
	public static void bajaEmpleado(String dni) {
		//Conexón a la base de datos
        Connection con = Conexion.getConnection();
	        
        try {    
	        Statement st=con.createStatement();
			//Insertamos el empleado
	        st.execute("delete from empleados e where e.dni = '"+dni+"'"); //Debería borrar en cascada	
        }catch (SQLException ex) {
	    	  ex.printStackTrace();
	    }
	}
	
	
	/**
	 * @param e
	 */
	public static void cerrarConexion() {
        Conexion.close();
	}
		
		
	/**
	 * @param e
	 */
	public static void backup(Empleado e){
        //Fichero de texto de salida para actualizar el de entrada
    	File fbackup = new File("backup-empleados.txt");
    	try {
	        FileWriter fw = new FileWriter(fbackup.getAbsoluteFile(),true);
	        BufferedWriter bw = new BufferedWriter(fw);
	        
	       	//Actualizamos el fichero de entrada, incluyendo el salario en el mismo fichero de backup
	       	bw.write(e.nombre+";"+e.dni+";"+e.sexo+";"+e.getCategoria()+";"+e.anyos+";"+Nomina.getSueldo(e)+'\n');
	        
	        //Cerramos todos los búferes
	        bw.close();
	        fw.close();
    	}catch (FileNotFoundException ex) {
    		ex.printStackTrace();
    	}catch (IOException ex) {
    		ex.printStackTrace();
    	}
	}
    
    
}
