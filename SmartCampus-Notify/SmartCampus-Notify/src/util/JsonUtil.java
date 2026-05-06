package util;

import model.Correo;
import model.Tarea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {
    public static String correoArrayToJson(List<Correo> correos) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < correos.size(); i++) {
            builder.append(correos.get(i).toJson());
            if (i < correos.size() - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
        return builder.toString();
    }

    public static List<Correo> parseCorreoArray(String json) {
        List<Correo> correos = new ArrayList<>();
        if (json == null || json.isBlank()) {
            return correos;
        }
        String trimmed = json.trim();
        if (trimmed.equals("[]")) {
            return correos;
        }
        if (!trimmed.startsWith("[") || !trimmed.endsWith("]")) {
            throw new IllegalArgumentException("Formato JSON inválido para la lista de correos.");
        }
        String contenido = trimmed.substring(1, trimmed.length() - 1).trim();
        if (contenido.isBlank()) {
            return correos;
        }
        List<String> objetos = extraerObjetos(contenido);
        for (String objeto : objetos) {
            correos.add(parseCorreo(objeto));
        }
        return correos;
    }

    public static String correoToJson(Correo correo) {
        return "{"
            + "\"id\":" + encodeString(correo.getId()) + ","
            + "\"destinatario\":" + encodeString(correo.getDestinatario()) + ","
            + "\"asunto\":" + encodeString(correo.getAsunto()) + ","
            + "\"contenido\":" + encodeString(correo.getContenido()) + ","
            + "\"fecha\":" + encodeString(correo.getFecha())
            + "}";
    }

    public static Correo parseCorreo(String json) {
        Map<String, String> datos = parseJsonObject(json);
        return new Correo(
            datos.get("id"),
            datos.get("destinatario"),
            datos.get("asunto"),
            datos.get("contenido"),
            datos.get("fecha")
        );
    }

    private static List<String> extraerObjetos(String contenido) {
        List<String> objetos = new ArrayList<>();
        int profundidad = 0;
        int inicio = 0;
        for (int i = 0; i < contenido.length(); i++) {
            char c = contenido.charAt(i);
            if (c == '{') {
                if (profundidad == 0) {
                    inicio = i;
                }
                profundidad++;
            } else if (c == '}') {
                profundidad--;
                if (profundidad == 0) {
                    objetos.add(contenido.substring(inicio, i + 1));
                }
            }
        }
        return objetos;
    }

    private static Map<String, String> parseJsonObject(String json) {
        String texto = json.trim();
        if (!texto.startsWith("{") || !texto.endsWith("}")) {
            throw new IllegalArgumentException("Formato JSON inválido para objeto.");
        }
        texto = texto.substring(1, texto.length() - 1).trim();
        Map<String, String> datos = new HashMap<>();
        int i = 0;
        while (i < texto.length()) {
            i = saltarEspacios(texto, i);
            String clave = parseQuoted(texto, i);
            i += clave.length() + 2;
            i = saltarEspacios(texto, i);
            if (texto.charAt(i) != ':') {
                throw new IllegalArgumentException("Formato JSON inválido: se esperaba ':'");
            }
            i++;
            i = saltarEspacios(texto, i);
            String valor;
            if (texto.charAt(i) == '"') {
                int fin = indexFinCadena(texto, i + 1);
                valor = unescape(texto.substring(i + 1, fin));
                i = fin + 1;
            } else if (texto.startsWith("null", i)) {
                valor = null;
                i += 4;
            } else if (texto.startsWith("true", i)) {
                valor = "true";
                i += 4;
            } else if (texto.startsWith("false", i)) {
                valor = "false";
                i += 5;
            } else {
                throw new IllegalArgumentException("Formato JSON inválido en el valor.");
            }
            datos.put(clave, valor);
            i = saltarEspacios(texto, i);
            if (i < texto.length() && texto.charAt(i) == ',') {
                i++;
            }
        }
        return datos;
    }

    private static int saltarEspacios(String texto, int index) {
        while (index < texto.length() && Character.isWhitespace(texto.charAt(index))) {
            index++;
        }
        return index;
    }

    private static String parseQuoted(String texto, int index) {
        int inicio = texto.indexOf('"', index);
        if (inicio < 0) {
            throw new IllegalArgumentException("Formato JSON inválido: no se encontró comilla de apertura.");
        }
        int fin = indexFinCadena(texto, inicio + 1);
        return unescape(texto.substring(inicio + 1, fin));
    }

    private static int indexFinCadena(String texto, int inicio) {
        for (int i = inicio; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (c == '"') {
                int escapes = 0;
                int j = i - 1;
                while (j >= 0 && texto.charAt(j) == '\\') {
                    escapes++;
                    j--;
                }
                if (escapes % 2 == 0) {
                    return i;
                }
            }
        }
        throw new IllegalArgumentException("Formato JSON inválido: comilla de cierre no encontrada.");
    }

    private static String encodeString(String valor) {
        if (valor == null) {
            return "null";
        }
        StringBuilder escaped = new StringBuilder();
        for (char c : valor.toCharArray()) {
            switch (c) {
                case '"': escaped.append("\\\""); break;
                case '\\': escaped.append("\\\\"); break;
                case '\n': escaped.append("\\n"); break;
                case '\r': escaped.append("\\r"); break;
                case '\t': escaped.append("\\t"); break;
                default: escaped.append(c); break;
            }
        }
        return "\"" + escaped + "\"";
    }

    private static String unescape(String text) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\\' && i + 1 < text.length()) {
                char siguiente = text.charAt(i + 1);
                switch (siguiente) {
                    case '"': builder.append('"'); i++; break;
                    case '\\': builder.append('\\'); i++; break;
                    case 'n': builder.append('\n'); i++; break;
                    case 'r': builder.append('\r'); i++; break;
                    case 't': builder.append('\t'); i++; break;
                    default: builder.append(siguiente); i++; break;
                }
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    // MÉTODOS PARA TAREAS
    public static String tareaArrayToJson(List<Tarea> tareas) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < tareas.size(); i++) {
            builder.append(tareas.get(i).toJson());
            if (i < tareas.size() - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
        return builder.toString();
    }

    public static List<Tarea> parseTareaArray(String json) {
        List<Tarea> tareas = new ArrayList<>();
        if (json == null || json.isBlank()) {
            return tareas;
        }
        String trimmed = json.trim();
        if (trimmed.equals("[]")) {
            return tareas;
        }
        if (!trimmed.startsWith("[") || !trimmed.endsWith("]")) {
            throw new IllegalArgumentException("Formato JSON inválido para la lista de tareas.");
        }
        String contenido = trimmed.substring(1, trimmed.length() - 1).trim();
        if (contenido.isBlank()) {
            return tareas;
        }
        List<String> objetos = extraerObjetos(contenido);
        for (String objeto : objetos) {
            tareas.add(parseTarea(objeto));
        }
        return tareas;
    }

    public static String tareaToJson(Tarea tarea) {
        return "{"
            + "\"id\":" + encodeString(tarea.getId()) + ","
            + "\"titulo\":" + encodeString(tarea.getTitulo()) + ","
            + "\"descripcion\":" + encodeString(tarea.getDescripcion()) + ","
            + "\"fechaLimite\":" + encodeString(tarea.getFechaLimite().toString()) + ","
            + "\"materia\":" + encodeString(tarea.getMaterial()) + ","
            + "\"completada\":" + tarea.isCompletada()
            + "}";
    }

    public static Tarea parseTarea(String json) {
        Map<String, String> datos = parseJsonObject(json);
        return new Tarea(
            datos.get("id"),
            datos.get("titulo"),
            datos.get("descripcion"),
            datos.get("fechaLimite"),
            datos.get("materia"),
            "true".equalsIgnoreCase(datos.get("completada"))
        );
    }
}
