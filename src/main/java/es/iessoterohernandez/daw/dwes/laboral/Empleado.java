
package es.iessoterohernandez.daw.dwes.laboral;




public class Empleado extends Persona {
    
   private int categoria;
   public int anyos;

    public Empleado(String nombre, String dni, String sexo)  {
    
          super(nombre, dni, sexo);
     
     
    }

    public Empleado  (String nombre, String dni, String sexo,int categoria, int anyos) throws DatosNoCorrectosException {
       super(nombre, dni, sexo);
		this.setCategoria(categoria);
		if (anyos>=0)
			this.anyos = anyos;
		else throw new DatosNoCorrectosException();
        
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getCategoria() {
        return categoria;
    }
   
   public void incAnyo(){
   anyos++;
   }
   
   public void imprime(){
       System.out.println(nombre);
       System.out.println(dni);
       System.out.println(sexo);
       System.out.println("Categotia: "+categoria);
       System.out.println("Años trabajados: "+ anyos);
   
   
   }

    public int getAnyos() {
        return anyos;
    }
    
}

