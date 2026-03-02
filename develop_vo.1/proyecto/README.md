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




### Arquitectura del Sistema TeleVentas

```mermaid
classDiagram
    class Usuario {
        <<Abstract>>
        -String nombre
        -String email
        +autenticar()
    }

    class Cliente
    class AgenteDeposito
    class GerenteRelaciones

    Usuario <|-- Cliente
    Usuario <|-- AgenteDeposito
    Usuario <|-- GerenteRelaciones

    class Producto {
        -String codigo
        -String descripcion
        -double precio
    }

    class ItemOrden {
        -int cantidad
        -double precioUnitario
    }

    class OrdenCompra {
        -String codigo
        -Date fecha
        -EstadoOrden estado
        +calcularTotal()
        +confirmar()
        +cancelar()
    }

    class Queja {
        -String motivo
        -Date fecha
    }

    class MetodoPago {
        <<Interface>>
        +procesarPago(monto)
    }

    class PagoTarjetaCredito

    MetodoPago <|.. PagoTarjetaCredito
    OrdenCompra --> MetodoPago

    class ISistemaInventario {
        <<Interface>>
        +consultarStock(codigo)
        +actualizarStock(codigo, cantidad)
    }

    class OrdenService
    class QuejaService
    class CatalogoService

    OrdenService --> ISistemaInventario
    OrdenService --> MetodoPago
    QuejaService --> GerenteRelaciones

    Cliente --> OrdenService
    Cliente --> QuejaService
    Cliente --> CatalogoService

    OrdenCompra "1" *-- "1..*" ItemOrden
    ItemOrden "*" --> "1" Producto
    Cliente "1" --> "*" OrdenCompra
    Cliente "1" --> "*" Queja
```