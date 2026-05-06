#!/bin/bash

# Script de compilación y ejecución para SmartCampus-Notify
# Compatible con Linux y macOS

set -e

PROYECTO_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$PROYECTO_DIR"

echo "🔨 SmartCampus-Notify - Build & Run"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

# Crear directorio de compilación
if [ ! -d "bin" ]; then
    echo "📁 Creando directorio bin..."
    mkdir -p bin
fi

# Compilar
echo "📦 Compilando código fuente..."
find src -name '*.java' -not -path '*/test/*' | xargs javac -d bin 2>&1

if [ $? -eq 0 ]; then
    echo "✅ Compilación exitosa"
else
    echo "❌ Error en compilación"
    exit 1
fi

# Ejecutar
echo ""
echo "🚀 Iniciando SmartCampus-Notify..."
java -cp bin App

