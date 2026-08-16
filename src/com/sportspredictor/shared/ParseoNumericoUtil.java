package com.sportspredictor.shared;

/**
 * Utilidad compartida para el parseo seguro de enteros a partir de texto.
 *
 * Refactor "Extract Class" para el code smell Duplicate Code: tanto
 * {@link ResultadoFutbol#parse(String)} como
 * {@link ResultadoTenis} (método interno de parseo de sets) repetían el
 * mismo patrón try/catch(NumberFormatException) para convertir un texto
 * a número de forma segura. Ahora ambas clases delegan aquí.
 */
public final class ParseoNumericoUtil {

    private ParseoNumericoUtil() {
        // Clase utilitaria: no debe instanciarse.
    }

    /**
     * Intenta convertir el texto (recortando espacios) a un entero.
     * Si el texto es nulo o no tiene un formato numérico válido,
     * retorna {@code null} en vez de propagar la excepción, para que
     * cada llamador decida cómo manejar el caso inválido.
     */
    public static Integer parseEnteroSeguro(String texto) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
