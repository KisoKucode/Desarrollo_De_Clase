package punto1;

public class EstudianteTelescopico {
    private final String nombre;
    private final String apellido;
    private final Integer edad;
    private final String carrera;
    private final Double promedio;
    private final String correo;
    private final String telefono;

    public EstudianteTelescopico(String nombre, String apellido) {
        this(nombre, apellido, null);
    }

    public EstudianteTelescopico(String nombre, String apellido, Integer edad) {
        this(nombre, apellido, edad, null);
    }

    public EstudianteTelescopico(String nombre, String apellido, Integer edad, String carrera) {
        this(nombre, apellido, edad, carrera, null);
    }

    public EstudianteTelescopico(String nombre, String apellido, Integer edad, String carrera, Double promedio) {
        this(nombre, apellido, edad, carrera, promedio, null);
    }

    public EstudianteTelescopico(String nombre, String apellido, Integer edad, String carrera, Double promedio, String correo) {
        this(nombre, apellido, edad, carrera, promedio, correo, null);
    }

    public EstudianteTelescopico(String nombre, String apellido, Integer edad, String carrera, Double promedio, String correo, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.carrera = carrera;
        this.promedio = promedio;
        this.correo = correo;
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "EstudianteTelescopico{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", carrera='" + carrera + '\'' +
                ", promedio=" + promedio +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
