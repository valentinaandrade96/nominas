
package es.iessoterohernandez.daw.dwes.laboral;

public class Nomina {
    
    private static final int SUELDO_BASE[] =
{50000, 70000, 90000, 110000, 130000,
150000, 170000, 190000, 210000, 230000};
    private String dni;
    private int sueldo;

    public Nomina(String e, int sueldo) {
       this.sueldo=sueldo;
       this.dni=e;
    }
    
  
    
    public int sueldo(Empleado e){
    
      int sueldo=SUELDO_BASE[e.getCategoria()]+5000*e.anyos;
      
        
    
    return sueldo;
    }

    public int getSueldo() {
        return sueldo;
    }
    
}
