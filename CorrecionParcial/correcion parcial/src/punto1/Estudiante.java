package punto1;

public class Estudiante {
    private final String nombre;
    private final String apellido;
    private final Integer edad;
    private final String carrera;
    private final Double promedio;
    private final String correo;
    private final String telefono;

    private Estudiante(Builder builder) {
        this.nombre = builder.nombre;
        this.apellido = builder.apellido;
        this.edad = builder.edad;
        this.carrera = builder.carrera;
        this.promedio = builder.promedio;
        this.correo = builder.correo;
        this.telefono = builder.telefono;
    }

    public static class Builder {
        private final String nombre;
        private final String apellido;
        private Integer edad;
        private String carrera;
        private Double promedio;
        private String correo;
        private String telefono;

        public Builder(String nombre, String apellido) {
            this.nombre = nombre;
            this.apellido = apellido;
        }

        public Builder edad(Integer edad) {
            this.edad = edad;
            return this;
        }

        public Builder carrera(String carrera) {
            this.carrera = carrera;
            return this;
        }

        public Builder promedio(Double promedio) {
            this.promedio = promedio;
            return this;
        }

        public Builder correo(String correo) {
            this.correo = correo;
            return this;
        }

        public Builder telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Estudiante build() {
            return new Estudiante(this);
        }
    }

    @Override
    public String toString() {
        return "Estudiante{" +
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
