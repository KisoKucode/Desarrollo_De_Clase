## uml pollos
```mermaidS
classDiagram
    %% Definición de Paquetes y Clases
    class Ave {
        <<abstract>>
        -TipoVuelo miFormaDeVuelo
        -TipoSonido miFormaDeCanto
        +realizarVuelo() void
        +realizarSonido() void
    }

    class Canario
    class AvesTruz

    class Main {
        +main(args: String[]) void
    }

    class TipoVuelo {
        <<interface>>
        +vuelo() void
    }

    class TipoSonido {
        <<interface>>
        +makeSound() void
    }

    class VueloConAlas {
        +vuelo() void
    }

    class SinVuelo {
        +vuelo() void
    }

    class Canto {
        +makeSound() void
    }

    class SinCanto {
        +makeSound() void
    }

    %% Relaciones de Herencia
    Ave <|-- Canario
    Ave <|-- AvesTruz

    %% Implementaciones de Interfaz
    TipoVuelo <|.. VueloConAlas
    TipoVuelo <|.. SinVuelo
    TipoSonido <|.. Canto
    TipoSonido <|.. SinCanto

    %% Agregación (Strategy Pattern)
    Ave o-- TipoVuelo : tiene
    Ave o-- TipoSonido : tiene

    %% Dependencias
    Main ..> Ave
    Canario ..> VueloConAlas : usa
    Canario ..> Canto : usa
    AvesTruz ..> SinVuelo : usa
    AvesTruz ..> SinCanto : usa

    %% Notas (Mermaid no soporta notas flotantes como PlantUML, 
    %% se suelen representar como comentarios o texto externo)