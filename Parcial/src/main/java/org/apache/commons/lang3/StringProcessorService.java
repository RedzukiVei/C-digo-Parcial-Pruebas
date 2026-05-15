package org.apache.commons.lang3;

import java.util.List;

/**
 * Servicio de procesamiento de cadenas que encapsula lógica de negocio
 * usando StringUtils internamente. Esta interfaz permite ser mockeada en pruebas.
 */
public interface StringProcessorService {

    /**
     * Valida si un nombre de usuario es válido (no vacío, solo alfanumérico).
     */
    boolean isValidUsername(String username);

    /**
     * Formatea un nombre completo: capitaliza y normaliza espacios.
     */
    String formatFullName(String name);

    /**
     * Sanitiza un input de búsqueda: recorta, limpia y convierte a minúsculas.
     */
    String sanitizeSearchQuery(String query);

    /**
     * Genera un slug para URL a partir de un título.
     */
    String generateSlug(String title);

    /**
     * Extrae solo los dígitos de un número de teléfono.
     */
    String extractPhoneDigits(String phoneNumber);

    /**
     * Valida si un email tiene formato básico válido.
     */
    boolean isValidEmail(String email);

    /**
     * Formatea una lista de tags separados por coma.
     */
    String formatTagList(List<String> tags);

    /**
     * Abrevia un texto largo para mostrar en UI.
     */
    String abbreviateForDisplay(String text, int maxWidth);

    /**
     * Verifica si una contraseña cumple requisitos mínimos.
     */
    boolean isStrongPassword(String password);

    /**
     * Cuenta palabras en un texto.
     */
    int countWords(String text);
}