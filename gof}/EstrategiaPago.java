//Permite cambiar el algoritmo de una operación en tiempo de ejecución.

interface EstrategiaPago { void pagar(int monto); }

class PagoPayPal implements EstrategiaPago {
    public void pagar(int monto) { System.out.println("Pagando " + monto + " con PayPal"); }
}

class Carrito {
    private EstrategiaPago estrategia;
    public void setEstrategia(EstrategiaPago e) { this.estrategia = e; }
    public void procesar(int monto) { estrategia.pagar(monto); }
}
