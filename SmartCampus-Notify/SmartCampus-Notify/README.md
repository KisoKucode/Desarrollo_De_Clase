# SmartCampus-Notify

Sistema académico emulado de notificaciones y correos con arquitectura **MVC** y persistencia **JSON**.

## UML del proyecto

El siguiente diagrama muestra la arquitectura principal del proyecto:

```mermaid
graph LR
    View["View\nSmartCampusFrame"] --> Controller["Controller\nCorreoController\nTareaController\nNotificacionController"]
    Controller --> Service["Service\nCorreoService\nTareaService\nNotificacionService"]
    Service --> Repository["Repository\nCorreoRepositoryJson\nTareaRepositoryJson"]
    Repository --> JsonFiles["JSON Storage\ncorreos.json\ntareas.json"]
    Controller --> Model["Model\nCorreo\nTarea\nNotificacion"]
    Service --> Model
    Repository --> Model
    JsonFiles -.-> Repository
```

## Diagrama de clases

```mermaid
classDiagram
    class Correo {
        +String id
        +String destinatario
        +String asunto
        +String contenido
        +String fecha
        +void asignarIdSiFalta()
        +String toJson()
        +static Correo fromJson(String)
    }

    class Tarea {
        +String id
        +String titulo
        +String descripcion
        +LocalDate fechaLimite
        +String materia
        +boolean completada
        +void asignarIdSiFalta()
        +long diasRestantes()
        +boolean esProxima()
        +boolean estaVencida()
        +String toJson()
        +static Tarea fromJson(String)
    }

    class Notificacion {
        +String id
        +String titulo
        +String mensaje
        +TipoNotificacion tipo
        +String referencia
        +LocalDateTime fechaCreacion
        +boolean leida
    }

    class CorreoController {
        +String enviarCorreo(String, String, String)
        +List<Correo> obtenerCorreos()
        +String actualizarCorreo(String, String, String, String)
        +void eliminarCorreo(String)
    }

    class TareaController {
        +String crearTarea(String, String, String, String)
        +List<Tarea> obtenerTareas()
        +String actualizarTarea(String, String, String, String, String, boolean)
        +void eliminarTarea(String)
        +String marcarCompletada(String)
    }

    class NotificacionController {
        +void generarNotificaciones()
        +List<Notificacion> obtenerNotificaciones()
        +void marcarComoLeida(String)
    }

    class CorreoService {
        +String enviarCorreo(Correo)
        +List<Correo> listarCorreos()
        +String actualizarCorreo(Correo)
        +void eliminarCorreo(String)
    }

    class TareaService {
        +String crearTarea(String, String, LocalDate, String)
        +List<Tarea> listarTareas()
        +List<Tarea> listarTareasProximas()
        +List<Tarea> listarTareasVencidas()
        +String actualizarTarea(Tarea)
        +void eliminarTarea(String)
        +String marcarCompletada(String)
    }

    class NotificacionService {
        +void generarNotificacionesAcademicas()
        +List<Notificacion> obtenerNotificaciones()
        +void marcarComoLeida(String)
    }

    class CorreoRepositoryJson {
        +List<Correo> obtenerTodos()
        +Correo guardar(Correo)
        +void actualizar(Correo)
        +void eliminar(String)
    }

    class TareaRepositoryJson {
        +List<Tarea> obtenerTodas()
        +Tarea guardar(Tarea)
        +void actualizar(Tarea)
        +void eliminar(String)
        +List<Tarea> obtenerProximas()
        +List<Tarea> obtenerVencidas()
    }

    CorreoController --> CorreoService
    TareaController --> TareaService
    NotificacionController --> NotificacionService
    CorreoService --> CorreoRepositoryJson
    TareaService --> TareaRepositoryJson
    NotificacionService --> TareaService
    CorreoController --> Correo
    TareaController --> Tarea
    NotificacionController --> Notificacion
    CorreoRepositoryJson --> Correo
    TareaRepositoryJson --> Tarea
    NotificacionService --> Notificacion
    NotificacionService --> Tarea
```

## Secciones principales

- `src/model`: entidades `Correo`, `Tarea`, `Notificacion`
- `src/controller`: controladores de entrada para la UI
- `src/service`: lógica de negocio y validaciones
- `src/repository`: persistencia en JSON
- `src/util`: utilidades de parseo JSON
- `src/view`: interfaz Swing completa
- `src/exception`: excepciones personalizadas

## Ejecución

```bash
cd /home/poveita/Documents/desarrollo/Desarrollo_De_Clase/SmartCampus-Notify/SmartCampus-Notify
mkdir -p bin
find src -name '*.java' | grep -v 'src/test' | xargs javac -d bin
java -cp bin App
```
