package enums;

public enum TipoPaciente {
    ADULTO_MAYOR('M'),
    EMBARAZADA('E'),
    REGULAR('R');

    public final char prefijo;

    TipoPaciente(char prefijo) {
        this.prefijo = prefijo;
    }
}
