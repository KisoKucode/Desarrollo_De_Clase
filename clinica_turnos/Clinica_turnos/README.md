## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## UML resolucion de problema clinica_turnos

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