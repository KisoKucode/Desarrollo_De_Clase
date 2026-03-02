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
        -LocalTime hora
        -boolean atendido
        +getTicket() String
        +getId() String
        +getHoraFormateada() String
        +isAtendido() boolean
        +setAtendido(boolean)
    }

    class AppClinica {
        -List~Paciente~ pacientes
        -int contadorTurno
        -DefaultTableModel modeloTabla
        +AppClinica()
        -actualizarTabla()
        +main(String[] args)
    }

    Paciente o-- TipoPaciente : usa
    AppClinica "1" -- "*" Paciente : gestiona