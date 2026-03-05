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
    %% Usuarios
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

    %% Modelos
    class Producto {
      -String codigo
      -String descripcion
      -double precio
    }

    class ItemOrden {
      -int cantidad
      -double precioUnitario
      +getCantidad(): int
      +getPrecioUnitario(): double
    }

    class OrdenCompra {
      -String codigo
      -Date fecha
      -EstadoOrden estado
      -boolean empacado
      -String transportista
      +calcularTotal(): double
      +confirmar(): void
      +cancelar(): void
      +marcarEmpacado(): void
      +asignarTransportista(String): void
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

    %% Relacion de composición y asociaciones
    OrdenCompra "1" *-- "1..*" ItemOrden : contiene
    ItemOrden "*" --> "1" Producto : refiere
    Cliente "1" --> "*" OrdenCompra
    Cliente "1" --> "*" Queja
    Queja --> GerenteRelaciones : remitida a

    note right of OrdenCompra : El ticket/orden incluye items; al confirmar se actualiza stock, se puede marcar empacado y asignar transportista.

    %% Pagos e inventario (interfaces y impls)
    class MetodoPago {
      <<interface>>
      +procesarPago(monto: double)
    }
    class PagoTarjetaCredito {
      +procesarPago(monto: double)
    }
    MetodoPago <|.. PagoTarjetaCredito

    class ISistemaInventario {
      <<interface>>
      +consultarStock(codigo: String): int
      +actualizarStock(codigo: String, cantidad: int)
    }
    class InMemoryInventario
    ISistemaInventario <|.. InMemoryInventario

    %% Servicios y adaptadores
    class IOrdenService {
      <<interface>>
      +crearOrden(orden: OrdenCompra)
      +confirmarOrden(codigo: String)
      +cancelarOrden(codigo: String)
    }
    class OrdenService {
      -ISistemaInventario inventario
      -MetodoPago metodoPago
      +procesarOrden(orden: OrdenCompra)
      +obtenerOrdenesConfirmadas(): List~OrdenCompra~
      +marcarEmpacado(orden: OrdenCompra)
      +asignarTransportista(orden: OrdenCompra, transportista: String)
      +cancelarOrden(orden: OrdenCompra)
    }

    class PagoTarjetaAdapter
    class OrdenServiceAdapter

    IOrdenService <|.. OrdenServiceAdapter
    IOrdenService <|.. OrdenService  %% conceptual: OrdenService cumple el contrato (adaptador presenta)
    MetodoPago <|.. PagoTarjetaAdapter
    PagoTarjetaAdapter ..> PagoTarjetaCredito : adapta
    OrdenService ..> ISistemaInventario : usa
    OrdenService ..> MetodoPago : usa

    %% Otros servicios y UI
    class CatalogoService {
      +agregarProducto(Producto)
      +getProductos(): List~Producto~
    }
    class QuejaService {
      +registrarQueja(cliente: Cliente, q: Queja)
    }
    class TeleventasGUI {
      +main(args)
      -IOrdenService ordenService
      -IPago pago
    }

    %% Relaciones de uso
    Cliente --> IOrdenService : usa
    Cliente --> QuejaService : usa
    Cliente --> CatalogoService : consulta
    QuejaService --> GerenteRelaciones : notifica
    TeleventasGUI --> IOrdenService : utiliza
    TeleventasGUI --> MetodoPago : utiliza
    TeleventasGUI --> CatalogoService : muestra

    %% Notas finales
    note left of TeleventasGUI
      TeleventasGUI es la interfaz cliente/operador (demo Swing).
      En una implementación web esto se mapearía a controladores/servlets.
    end note