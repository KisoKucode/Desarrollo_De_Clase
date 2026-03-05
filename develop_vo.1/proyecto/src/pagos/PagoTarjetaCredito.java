package pagos;

public class PagoTarjetaCredito implements MetodoPago {
    private String numeroTarjeta;

    public PagoTarjetaCredito(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    @Override
    public void procesarPago(double monto) {
        // implementación simulada
        System.out.println("Procesando pago con tarjeta " + numeroTarjeta + ": " + monto);
    }
}
