
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

Diagrama de clases (Mermaid):

```mermaid
classDiagram
    class Usuario {
        <<abstract>>
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
        +getCantidad() int
        +getPrecioUnitario() double
    }

    class OrdenCompra {
        -String codigo
        -Date fecha
        -EstadoOrden estado
        -boolean empacado
        -String transportista
        +calcularTotal() double
        +confirmar() void
        +cancelar() void
        +marcarEmpacado() void
        +asignarTransportista(String) void
    }

    class Queja {
        -String motivo
        -Date fecha
    }

    class EstadoOrden {
        <<enumeration>>
        PENDIENTE
        CONFIRMADA
        CANCELADA
    }

    class MetodoPago {
        <<interface>>
        +procesarPago(double monto)
    }

    class PagoTarjetaCredito {
        +procesarPago(double monto)
    }

    class ISistemaInventario {
        <<interface>>
        +consultarStock(String codigo) int
        +actualizarStock(String codigo, int cantidad)
    }

    class IOrdenService {
        <<interface>>
        +crearOrden(OrdenCompra orden)
        +confirmarOrden(String codigo)
        +cancelarOrden(String codigo)
    }

    class OrdenService {
        -ISistemaInventario inventario
        -MetodoPago metodoPago
        +procesarOrden(OrdenCompra orden)
        +obtenerOrdenesConfirmadas() List
        +marcarEmpacado(OrdenCompra orden)
        +asignarTransportista(OrdenCompra orden, String transportista)
        +cancelarOrden(OrdenCompra orden)
    }

    class CatalogoService {
        +agregarProducto(Producto p)
        +getProductos() List
    }

    class QuejaService {
        +registrarQueja(Cliente cliente, Queja q)
    }

    class TeleventasGUI {
        -IOrdenService ordenService
        +main(String[] args)
    }

    %% Relaciones
    OrdenCompra "1" *-- "1..*" ItemOrden : contiene
    ItemOrden "*" --> "1" Producto : refiere
    Cliente "1" --> "*" OrdenCompra
    Cliente "1" --> "*" Queja
    Queja --> GerenteRelaciones : remitida a
    MetodoPago <|.. PagoTarjetaCredito
    ISistemaInventario <|.. InMemoryInventario
    IOrdenService <|.. OrdenServiceAdapter
    IOrdenService <|.. OrdenService
    MetodoPago <|.. PagoTarjetaAdapter
    
    PagoTarjetaAdapter ..> PagoTarjetaCredito : adapta
    OrdenService ..> ISistemaInventario : usa
    OrdenService ..> MetodoPago : usa
    
    Cliente --> IOrdenService : usa
    Cliente --> QuejaService : usa
    Cliente --> CatalogoService : consulta
    QuejaService --> GerenteRelaciones : notifica
    TeleventasGUI --> IOrdenService : utiliza
    TeleventasGUI --> MetodoPago : utiliza
    TeleventasGUI --> CatalogoService : muestra

    %% Notas
    %% Nota: Mermaid requiere que las notas se definan de forma simple