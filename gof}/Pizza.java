//Evita constructores con 10 parámetros (telescoping constructors).

public class Pizza {
    private String masa;
    private String salsa;
    private String extra;

    // Constructor privado: solo el Builder puede instanciarlo
    private Pizza(Builder builder) {
        this.masa = builder.masa;
        this.salsa = builder.salsa;
        this.extra = builder.extra;
    }

    public static class Builder {
        private String masa;
        private String salsa;
        private String extra;

        public Builder conMasa(String masa) { this.masa = masa; return this; }
        public Builder conSalsa(String salsa) { this.salsa = salsa; return this; }
        public Builder conExtra(String extra) { this.extra = extra; return this; }

        public Pizza build() { return new Pizza(this); }
    }
}
// Uso: Pizza p = new Pizza.Builder().conMasa("Fina").conSalsa("Tomate").build();