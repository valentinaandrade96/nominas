
package es.iessoterohernandez.daw.dwes.laboral;


public class Persona {
    
    public String nombre;
    public String dni;
    public String sexo;

    public Persona(String nombre, String dni, String sexo) {
        this.nombre = nombre;
        this.dni = dni;
        this.sexo = sexo;
    }

    public Persona(String nombre, String sexo) {
        this.nombre = nombre;
        this.sexo = sexo;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
    
    
    public void imprime(){
    
        System.out.println("Se llama "+ nombre+ " y tiene el dni "+ dni);
    }
    
    
}
