# Design Patterns GoF - Java Implementation

Este repositorio contiene una implementación práctica de los patrones de diseño de la "Banda de los Cuatro" (**Gang of Four - GoF**) utilizando **Java**. El objetivo es servir como guía de referencia para entender la teoría y la aplicación de estas soluciones arquitectónicas.

---

## Tabla Comparativa (Resumen)

| Patrón | Categoría | Problema que resuelve | Casos de Uso |
| :--- | :--- | :--- | :--- |
| **Builder** | Creacional | Construcción compleja paso a paso | Generación de objetos con muchos parámetros |
| **Factory Method** | Creacional | Instanciación desacoplada | Creación de tipos según configuración (Logs, UI) |
| **Singleton** | Creacional | Instancia única global | Conexiones a DB, Loggers, Configuración |
| **Decorator** | Estructural | Extensión dinámica de funcionalidad | Filtros de I/O, Decoración de UI, Menús |
| **Facade** | Estructural | Interfaz simple para sistemas complejos | Wrappers de APIs, simplificación de subsistemas |
| **Strategy** | Comportamiento | Cambio de algoritmos en runtime | Métodos de pago, tipos de compresión |
| **Command** | Comportamiento | Encapsulación de peticiones | Sistemas de "Undo", colas de tareas |

---

## Estructura del Proyecto

El código está organizado por categorías de patrones para facilitar su estudio:

### 1. Creacionales (Creational)
*Se enfocan en cómo se crean los objetos, encapsulando la lógica de instanciación.*
* **[Builder]):** Separa la construcción de un objeto complejo de su representación.
* **[Factory Method]:** Define una interfaz para crear un objeto, pero deja que las subclases decidan qué clase instanciar.
* **[Singleton]:** Garantiza que una clase tenga una única instancia y proporciona un punto de acceso global.

### 2. Estructurales (Structural)
*Se enfocan en cómo las clases y objetos se combinan para formar estructuras más grandes.*
* **[Decorator]:** Permite añadir responsabilidades a objetos de forma dinámica mediante composición.
* **[Facade]:** Proporciona una interfaz unificada y simplificada para un conjunto de interfaces en un subsistema.

### 3. Comportamiento (Behavioral)
*Se encargan de la comunicación entre objetos y la asignación de responsabilidades.*
* **[Strategy]:** Define una familia de algoritmos, encapsula cada uno y los hace intercambiables.
* **[Command]:** Convierte una petición en un objeto independiente que contiene toda la información sobre la petición.



