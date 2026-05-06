## 🚀 GUÍA RÁPIDA DE INICIO

### 1️⃣ Ubicación del Proyecto
```bash
cd /home/poveita/Documents/desarrollo/Desarrollo_De_Clase/SmartCampus-Notify/SmartCampus-Notify
```

### 2️⃣ Compilar (10 segundos)
```bash
mkdir -p bin
find src -name '*.java' | grep -v 'src/test' | xargs javac -d bin
```

### 3️⃣ Ejecutar (abre la ventana Swing)
```bash
java -cp bin App
```

### ✨ O usa el script
```bash
./run.sh
```

---

## 📋 Qué ver en la aplicación

### Tab 1: **CORREOS** 📧
1. Escribe: `usuario@ejemplo.com`
2. Escribe: `Asunto de prueba` (mín. 5 caracteres)
3. Escribe: contenido cualquiera
4. Click: `Enviar`
5. Click: `Ver correos` → verás la tabla actualizada

### Tab 2: **TAREAS** 📚
1. Escribe: `Examen Matemáticas`
2. Escribe: `Estudiar capítulos 1-5`
3. Escribe: `2026-05-10` (fecha futura, formato YYYY-MM-DD)
4. Escribe: `Matemáticas`
5. Click: `Crear`
6. Click: `Cargar` → verás la tarea en tabla

### Tab 3: **NOTIFICACIONES** 🔔
1. Click: `Generar notificaciones`
2. Verás alertas automáticas de tareas próximas (≤3 días)
3. Si hay tareas vencidas, aparecerán aquí
4. Click: `Marcar como leída` para marcar notificaciones

---

## 🎯 Archivos Generados Después de Usar

```
correos.json      ← Almacena correos simulados
tareas.json       ← Almacena tareas académicas
```

Abre estos archivos con editor de texto para ver el contenido JSON.

---

## ❌ Errores Comunes y Soluciones

| Problema | Solución |
|---|---|
| "El destinatario debe ser un correo válido" | Usa formato: `algo@ejemplo.com` |
| "El asunto debe tener al menos 5 caracteres" | Agrega más texto al asunto |
| Tabla vacía | Presiona "Cargar" o "Ver correos" |
| Error de compilación | Verifica ruta: `/home/poveita/Documentos/...` |

---

## ✅ Verificación Rápida

```bash
# ¿Está compilado?
ls -la bin/

# ¿Hay clases Java?
find src -name '*.java' | wc -l

# ¿Hay datos?
cat correos.json
cat tareas.json
```

---

## 📖 Documentación Completa

- `README_COMPLETO.md` → Documentación detallada
- `RESUMEN_IMPLEMENTACION.md` → Arquitectura y estadísticas
- `src/` → Código fuente comentado

---

**¡Listo para usar!** 🎉
