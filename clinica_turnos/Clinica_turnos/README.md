
## UML resolucion de problema clinica_turnos
```mermaid
classDiagram
    class TipoPaciente {
        <<enumeration>>
        ADULTO_MAYOR
        EMBARAZADA
        REGULAR
        +char prefijo
    }

    class Paciente {
        -String id
        -String ticket
        -LocalTime horaSolicitud
        -boolean atendido
        +getTicket() String
        +actualizarEstado(bool)
    }

    class ITurnoService {
        <<Interface>>
        +registrarTurno(String id, TipoPaciente tipo)
        +cancelarTurno(String ticket)
        +obtenerSiguientes() List
    }

    class TurnoServiceImpl {
        -List~Paciente~ repositorio
        -int contadorGlobal
        +atenderPaciente()
    }

    class AppClinicaGUI {
        -TurnoServiceImpl service
        -DefaultTableModel tablaModel
        +renderizarTabla()
        +main(String[] args)
    }

    %% Relaciones
    ITurnoService <|.. TurnoServiceImpl
    AppClinicaGUI --> ITurnoService : usa
    TurnoServiceImpl "1" *-- "*" Paciente : contiene
    Paciente --> TipoPaciente : clasificado por

    note for Paciente "El ticket se genera como \nPrefijo + Contador (ej: M-102)"