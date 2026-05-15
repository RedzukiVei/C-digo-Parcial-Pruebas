package org.apache.commons.lang3;

import java.util.List;

/**
 * Implementación del servicio de procesamiento de cadenas.
 * Usa StringUtils de Apache Commons como utilidad principal.
 */
public class StringProcessorServiceImpl implements StringProcessorService {

    @Override
    public boolean isValidUsername(String username) {
        if (StringUtils.isBlank(username)) return false;
        String trimmed = StringUtils.trim(username);
        return StringUtils.isAlphanumeric(trimmed) && trimmed.length() >= 3 && trimmed.length() <= 20;
    }

    @Override
    public String formatFullName(String name) {
        if (StringUtils.isBlank(name)) return StringUtils.EMPTY;
        String normalized = StringUtils.normalizeSpace(name);
        String[] parts = StringUtils.split(normalized);
        if (parts == null) return StringUtils.EMPTY;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) sb.append(" ");
            sb.append(StringUtils.capitalize(StringUtils.lowerCase(parts[i])));
        }
        return sb.toString();
    }

    @Override
    public String sanitizeSearchQuery(String query) {
        if (StringUtils.isBlank(query)) return StringUtils.EMPTY;
        return StringUtils.lowerCase(StringUtils.normalizeSpace(StringUtils.trim(query)));
    }

    @Override
    public String generateSlug(String title) {
        if (StringUtils.isBlank(title)) return StringUtils.EMPTY;
        String lower = StringUtils.lowerCase(StringUtils.normalizeSpace(StringUtils.strip(title)));
        return StringUtils.replaceChars(lower, ' ', '-');
    }

    @Override
    public String extractPhoneDigits(String phoneNumber) {
        return StringUtils.getDigits(phoneNumber);
    }

    @Override
    public boolean isValidEmail(String email) {
        if (StringUtils.isBlank(email)) return false;
        int atIndex = StringUtils.indexOf(email, '@');
        if (atIndex <= 0) return false;
        String domain = StringUtils.substringAfter(email, "@");
        return StringUtils.contains(domain, ".") && !StringUtils.startsWith(domain, ".");
    }

    @Override
    public String formatTagList(List<String> tags) {
        if (tags == null || tags.isEmpty()) return StringUtils.EMPTY;
        return StringUtils.join(tags.toArray(), ", ");
    }

    @Override
    public String abbreviateForDisplay(String text, int maxWidth) {
        return StringUtils.abbreviate(text, maxWidth);
    }

    @Override
    public boolean isStrongPassword(String password) {
        if (StringUtils.length(password) < 8) return false;
        return StringUtils.containsAny(password, "0123456789")
            && StringUtils.isMixedCase(password)
            && StringUtils.containsAny(password, "!@#$%^&*");
    }

    @Override
    public int countWords(String text) {
        if (StringUtils.isBlank(text)) return 0;
        String[] words = StringUtils.split(StringUtils.normalizeSpace(text));
        return words == null ? 0 : words.length;
    }
}