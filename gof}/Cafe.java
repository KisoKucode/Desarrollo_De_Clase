//Añade funcionalidad a un objeto envolviéndolo, sin alterar su estructura original.

interface Cafe { double getCosto(); }

class CafeSimple implements Cafe {
    public double getCosto() { return 2.0; }
}

abstract class CafeDecorator implements Cafe {
    protected Cafe cafeDecorado;
    public CafeDecorator(Cafe cafe) { this.cafeDecorado = cafe; }
}

class ConLeche extends CafeDecorator {
    public ConLeche(Cafe cafe) { super(cafe); }
    public double getCosto() { return cafeDecorado.getCosto() + 0.5; }
}
// Uso: Cafe miCafe = new ConLeche(new CafeSimple());