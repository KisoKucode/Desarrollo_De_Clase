# SmartCampus-Notify - Sistema Académico Integrado

## Descripción
Sistema de gestión académica que emula:
- **Correos electrónicos** (CRUD simulado)
- **Tareas académicas** (con fechas límite y estados)
- **Notificaciones inteligentes** (tareas próximas y vencidas)

**Importante**: Sistema completamente emulado, sin conexiones a APIs externas.

---

## Estructura del Proyecto

```
src/
├── model/              # Entidades
│   ├── Correo.java
│   ├── Tarea.java
│   └── Notificacion.java
├── view/               # Interfaz gráfica Swing
│   ├── CorreoAppFrame.java
│   └── SmartCampusFrame.java
├── controller/         # Controladores
│   ├── CorreoController.java
│   ├── TareaController.java
│   └── NotificacionController.java
├── service/            # Lógica de negocio
│   ├── ICorreoService.java
│   ├── CorreoService.java
│   ├── ITareaService.java
│   ├── TareaService.java
│   ├── INotificacionService.java
│   └── NotificacionService.java
├── repository/         # Persistencia JSON
│   ├── ICorreoRepository.java
│   ├── CorreoRepositoryJson.java
│   ├── ITareaRepository.java
│   └── TareaRepositoryJson.java
├── exception/          # Excepciones personalizadas
│   ├── CorreoException.java
│   └── NotificacionException.java
├── util/               # Utilidades
│   └── JsonUtil.java
├── test/               # Pruebas unitarias JUnit
│   └── java/service/
│       ├── CorreoServiceTest.java
│       ├── TareaServiceTest.java
│       └── NotificacionServiceTest.java
└── App.java            # Punto de entrada
```

---

## Requisitos
- **Java 11+**
- **No requiere librerías externas** (sin APIs)

---

## Compilación

### Opción 1: Terminal
```bash
cd /home/poveita/Documents/desarrollo/Desarrollo_De_Clase/SmartCampus-Notify/SmartCampus-Notify
mkdir -p bin
find src -name '*.java' | grep -v 'src/test' | xargs javac -d bin
```

### Opción 2: IDE (IntelliJ IDEA o VS Code)
- Abre el proyecto
- Ejecuta la compilación automática

---

## Ejecución

### Iniciar la aplicación
```bash
java -cp bin App
```

Se abrirá una ventana con tres pestañas:

#### 📧 **Pestaña 1: Correos**
- Crear, leer, actualizar y eliminar correos
- Validaciones: email válido, asunto ≥ 5 caracteres
- No permite duplicados
- Persistencia en `correos.json`

#### 📚 **Pestaña 2: Tareas**
- Crear tareas académicas con fecha límite
- Ver tareas próximas (≤ 3 días)
- Ver tareas vencidas
- Marcar como completada
- Persistencia en `tareas.json`

#### 🔔 **Pestaña 3: Notificaciones**
- Generar automáticamente notificaciones
- Mostrar tareas próximas y vencidas
- Marcar notificaciones como leídas
- Contador de no leídas

---

## Pruebas Unitarias

### Ejecutar tests con IDE
1. Abre `src/test/java/service/CorreoServiceTest.java`
2. Click derecho → "Run"
3. Repite con `TareaServiceTest.java` y `NotificacionServiceTest.java`

### Tests incluidos
- ✅ Validación de correos
- ✅ Prevención de duplicados
- ✅ Validación de tareas
- ✅ Detección de tareas próximas/vencidas
- ✅ Generación de notificaciones

---

## Validaciones de Negocio

### Correos
- ✓ Destinatario: formato email válido
- ✓ Asunto: mínimo 5 caracteres
- ✓ Contenido: obligatorio
- ✓ No duplicados: mismo destinatario + asunto

### Tareas
- ✓ Título: obligatorio
- ✓ Descripción: obligatoria
- ✓ Fecha límite: obligatoria
- ✓ Materia: obligatoria

### Notificaciones
- ✓ Tarea próxima: ≤ 3 días y no completada
- ✓ Tarea vencida: fecha pasada y no completada
- ✓ Se limpian cada 24 horas

---

## Archivos Generados

Después de ejecutar:
- `correos.json` - Almacena correos simulados
- `tareas.json` - Almacena tareas académicas

---

## Características Principales

| Característica | Correos | Tareas | Notificaciones |
|---|---|---|---|
| CRUD | ✅ Completo | ✅ Completo | ❌ Solo lectura |
| Validaciones | ✅ Sí | ✅ Sí | ✅ Sí |
| Persistencia JSON | ✅ Sí | ✅ Sí | ❌ En memoria |
| Interfaz Swing | ✅ Sí | ✅ Sí | ✅ Sí |
| Pruebas | ✅ JUnit | ✅ JUnit | ✅ JUnit |

---

## Arquitectura

```
┌─────────────────────────────────────┐
│       SmartCampusFrame (View)       │
│   (Swing con 3 pestañas)            │
└────────────┬──────────────┬─────────┘
             │              │
   ┌─────────▼──┐   ┌──────▼────────┐
   │ Controladores │  │ NotificacionController │
   ├─────────────┤   └───────┬──────┘
   │ CorreoCtrl  │           │
   │ TareaCtrl   │      ┌────▼─────────┐
   │             │      │ Services     │
   └──────┬──────┘      ├─────────────┤
          │             │ CorreoSvc   │
      ┌───▼─────────┐   │ TareaSvc    │
      │  Services   │   │ NotifcSvc   │
      ├────────────┤    └────┬────────┘
      │ CorreoSvc  │         │
      │ TareaSvc   │    ┌────▼────────────┐
      │ NotifcSvc  │    │ Repositories    │
      └────┬───────┘    ├────────────────┤
           │            │ CorreoRepoJson │
      ┌────▼──────────┐  │ TareaRepoJson  │
      │ Repositories  │  └────┬───────────┘
      ├──────────────┤        │
      │ CorreoRepo   │   ┌────▼─────────┐
      │ TareaRepo    │   │ JSON Files   │
      └────┬─────────┘   ├─────────────┤
           │             │ correos.json│
      ┌────▼──────────┐  │ tareas.json │
      │  Modelos      │  └─────────────┘
      ├──────────────┤
      │ Correo       │
      │ Tarea        │
      │ Notificacion │
      └──────────────┘
```

---

## Uso Rápido

### 1. Crear un correo
- Ingresa: `usuario@email.com` en "Destinatario"
- Ingresa: `Asunto de prueba` (mín. 5 caracteres)
- Ingresa: contenido cualquiera
- Click: `Enviar`

### 2. Crear una tarea
- Ingresa: `Examen Matemáticas`
- Ingresa: `Estudiar capítulos 1-5`
- Ingresa: `2026-05-10` (fecha futura)
- Ingresa: `Matemáticas`
- Click: `Crear`

### 3. Generar notificaciones
- Ve a la pestaña `Notificaciones`
- Click: `Generar notificaciones`
- Verás alertas de tareas próximas/vencidas

---

## Códigos de Error

| Error | Causa | Solución |
|---|---|---|
| "El destinatario debe ser un correo válido" | Email inválido | Usa formato: `usuario@dominio.com` |
| "El asunto debe tener al menos 5 caracteres" | Asunto muy corto | Agrega más caracteres |
| "Ya existe un correo con el mismo destinatario y asunto" | Duplicado | Cambia destinatario o asunto |
| "Seleccione un correo para actualizar" | Sin selección | Haz click en una fila de la tabla |

---

## Notas
- 🔐 Sistema completamente emulado: no envía correos reales
- 📊 Datos persisten en archivos JSON locales
- 🧪 Incluye suite de pruebas unitarias JUnit
- 🎨 Interfaz intuitiva con Swing
- ✨ Separación clara de responsabilidades (MVC + Clean Architecture)

---

## Licencia
Proyecto académico - Desarrollo de Clase
