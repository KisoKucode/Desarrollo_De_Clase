public enum TipoPaciente {
    ADULTO_MAYOR('M'), 
    EMBARAZADA('E'), 
    REGULAR('N');

    public final char prefijo;
    TipoPaciente(char prefijo) {
        this.prefijo = prefijo;
    }
}