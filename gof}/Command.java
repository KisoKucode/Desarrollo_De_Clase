//Encapsula una petición como un objeto, permitiendo colas o historial (Undo).

public interface Command { void execute(); }

class Luz { // El Receptor
    void encender() { System.out.println("Luz encendida"); }
}

class EncenderLuzCommand implements Command {
    private Luz luz;
    public EncenderLuzCommand(Luz luz) { this.luz = luz; }
    public void execute() { luz.encender(); }
}

class ControlRemoto { // El Invocador
    private Command slot;
    public void setCommand(Command c) { slot = c; }
    public void presionarBoton() { slot.execute(); }
} {
    
}
