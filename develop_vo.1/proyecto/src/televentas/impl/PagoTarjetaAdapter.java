package televentas.impl;

import pagos.PagoTarjetaCredito;
import televentas.api.IPago;

public class PagoTarjetaAdapter implements IPago {
    private final PagoTarjetaCredito impl;

    public PagoTarjetaAdapter(PagoTarjetaCredito impl) {
        this.impl = impl;
    }

    @Override
    public void procesarPago(double monto) {
        impl.procesarPago(monto);
    }
}
