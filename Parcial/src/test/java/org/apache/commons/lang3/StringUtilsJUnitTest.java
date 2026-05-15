package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Pruebas SOLO con JUnit 5 para StringUtils de Apache Commons Lang3.
 * Total: 50 métodos @Test exactos — representa el 50% del total de pruebas.
 *
 * 10 suites @Nested × 5 tests = 50 tests.
 */
@DisplayName("StringUtils — JUnit 5 (50 tests)")
class StringUtilsJUnitTest {

    // =========================================================
    // 1. isEmpty / isBlank  (5 tests)
    // =========================================================
    @Nested
    @DisplayName("1. isEmpty() y isBlank()")
    class IsEmptyBlankTests {

        @Test
        @DisplayName("isEmpty — null → true")
        void isEmpty_Null_ReturnsTrue() {
            assertTrue(StringUtils.isEmpty(null));
        }

        @Test
        @DisplayName("isEmpty — cadena vacía → true")
        void isEmpty_EmptyString_ReturnsTrue() {
            assertTrue(StringUtils.isEmpty(""));
        }

        @Test
        @DisplayName("isEmpty — espacio → false  |  isBlank — espacio → true")
        void isEmpty_Space_False__isBlank_Space_True() {
            assertFalse(StringUtils.isEmpty(" "));
            assertTrue(StringUtils.isBlank(" "));
        }

        @Test
        @DisplayName("isBlank — null, vacío y tabs → true")
        void isBlank_NullEmptyAndWhitespace_AllTrue() {
            assertTrue(StringUtils.isBlank(null));
            assertTrue(StringUtils.isBlank(""));
            assertTrue(StringUtils.isBlank("   "));
            assertTrue(StringUtils.isBlank("\t\n"));
        }

        @Test
        @DisplayName("isNotBlank y isAllBlank")
        void isNotBlank_And_isAllBlank() {
            assertTrue(StringUtils.isNotBlank("  bob  "));
            assertFalse(StringUtils.isNotBlank("   "));
            assertTrue(StringUtils.isAllBlank(null, "", "  "));
            assertFalse(StringUtils.isAllBlank(null, "foo"));
        }
    }

    // =========================================================
    // 2. trim / strip  (5 tests)
    // =========================================================
    @Nested
    @DisplayName("2. trim() y strip()")
    class TrimStripTests {

        @Test
        @DisplayName("trim — null → null")
        void trim_Null_ReturnsNull() {
            assertNull(StringUtils.trim(null));
        }

        @Test
        @DisplayName("trim — elimina espacios al inicio y final")
        void trim_WithSpaces_RemovesSpaces() {
            assertEquals("abc", StringUtils.trim("    abc    "));
        }

        @Test
        @DisplayName("trim — solo espacios → vacío  |  trimToNull → null")
        void trim_OnlySpaces_EmptyOrNull() {
            assertEquals("", StringUtils.trim("     "));
            assertNull(StringUtils.trimToNull("   "));
        }

        @Test
        @DisplayName("trimToEmpty — null → vacío  |  texto → sin espacios")
        void trimToEmpty_NullAndText() {
            assertEquals("", StringUtils.trimToEmpty(null));
            assertEquals("abc", StringUtils.trimToEmpty("  abc  "));
        }

        @Test
        @DisplayName("strip — equivalente trim ASCII  |  stripToNull — vacío → null")
        void strip_EquivalentToTrim() {
            assertEquals("ab c", StringUtils.strip(" ab c "));
            assertNull(StringUtils.stripToNull(""));
            assertEquals("", StringUtils.stripToEmpty(null));
        }
    }

    // =========================================================
    // 3. capitalize / case  (5 tests)
    // =========================================================
    @Nested
    @DisplayName("3. capitalize(), swapCase() y case ops")
    class CaseTests {

        @Test
        @DisplayName("capitalize — primera letra a mayúscula  |  null → null")
        void capitalize_LowerAndNull() {
            assertEquals("Cat", StringUtils.capitalize("cat"));
            assertNull(StringUtils.capitalize(null));
            assertEquals("", StringUtils.capitalize(""));
        }

        @Test
        @DisplayName("capitalize — no modifica el resto de la cadena")
        void capitalize_OnlyFirstLetter() {
            assertEquals("CAt", StringUtils.capitalize("cAt"));
        }

        @Test
        @DisplayName("uncapitalize — primera letra a minúscula")
        void uncapitalize_UpperFirst() {
            assertEquals("cat", StringUtils.uncapitalize("Cat"));
            assertEquals("cAT", StringUtils.uncapitalize("CAT"));
        }

        @Test
        @DisplayName("swapCase — invierte mayúsculas y minúsculas")
        void swapCase_InvertsCase() {
            assertEquals("tHE DOG HAS A bone",
                    StringUtils.swapCase("The dog has a BONE"));
        }

        @Test
        @DisplayName("upperCase / lowerCase / isMixedCase / isAllUpperCase")
        void upperLowerMixed() {
            assertEquals("ABC", StringUtils.upperCase("aBc"));
            assertEquals("abc", StringUtils.lowerCase("aBc"));
            assertTrue(StringUtils.isMixedCase("aBc"));
            assertFalse(StringUtils.isMixedCase("ABC"));
            assertTrue(StringUtils.isAllUpperCase("ABC"));
            assertTrue(StringUtils.isAllLowerCase("abc"));
        }
    }

    // =========================================================
    // 4. contains / search  (5 tests)
    // =========================================================
    @Nested
    @DisplayName("4. contains() y operaciones de búsqueda")
    class ContainsSearchTests {

        @Test
        @DisplayName("contains — existe y no existe  |  null → false")
        void contains_ExistsAndNotExists() {
            assertTrue(StringUtils.contains("abc", "a"));
            assertFalse(StringUtils.contains("abc", "z"));
            assertFalse(StringUtils.contains(null, "a"));
            assertFalse(StringUtils.contains("abc", null));
        }

        @Test
        @DisplayName("containsIgnoreCase — ignora mayúsculas")
        void containsIgnoreCase_DifferentCase() {
            assertTrue(StringUtils.containsIgnoreCase("abc", "A"));
            assertTrue(StringUtils.containsIgnoreCase("ABC", "abc"));
            assertFalse(StringUtils.containsIgnoreCase("abc", "Z"));
        }

        @Test
        @DisplayName("containsAny / containsNone")
        void containsAnyAndNone() {
            assertTrue(StringUtils.containsAny("zzabyycdxx", 'z', 'a'));
            assertFalse(StringUtils.containsAny("ab", 'x', 'y'));
            assertTrue(StringUtils.containsNone("abab", 'x', 'y', 'z'));
        }

        @Test
        @DisplayName("countMatches — cuenta ocurrencias de subcadena")
        void countMatches_Occurrences() {
            assertEquals(2, StringUtils.countMatches("abba", "a"));
            assertEquals(1, StringUtils.countMatches("abba", "ab"));
            assertEquals(0, StringUtils.countMatches("abba", "xxx"));
        }

        @Test
        @DisplayName("indexOf — posición primera ocurrencia  |  -1 si no existe")
        void indexOf_PositionAndNotFound() {
            assertEquals(0,  StringUtils.indexOf("aabaabaa", "a"));
            assertEquals(2,  StringUtils.indexOf("aabaabaa", "b"));
            assertEquals(-1, StringUtils.indexOf("aabaabaa", "z"));
        }
    }

    // =========================================================
    // 5. replace / remove  (5 tests)
    // =========================================================
    @Nested
    @DisplayName("5. replace() y remove()")
    class ReplaceRemoveTests {

        @Test
        @DisplayName("replace — todas las ocurrencias  |  null search → original")
        void replace_AllOccurrences() {
            assertEquals("zbz", StringUtils.replace("aba", "a", "z"));
            assertEquals("any", StringUtils.replace("any", null, "z"));
        }

        @Test
        @DisplayName("replaceOnce — solo la primera ocurrencia")
        void replaceOnce_OnlyFirst() {
            assertEquals("zba", StringUtils.replaceOnce("aba", "a", "z"));
        }

        @Test
        @DisplayName("replaceIgnoreCase — reemplaza ignorando caso")
        void replaceIgnoreCase_DifferentCase() {
            assertEquals("zbz", StringUtils.replaceIgnoreCase("aba", "A", "z"));
        }

        @Test
        @DisplayName("remove subcadena y carácter  |  removeStart / removeEnd")
        void removeVariants() {
            assertEquals("qd",         StringUtils.remove("queued", "ue"));
            assertEquals("qeed",        StringUtils.remove("queued", 'u'));
            assertEquals("domain.com",  StringUtils.removeStart("www.domain.com", "www."));
            assertEquals("www.domain",  StringUtils.removeEnd("www.domain.com", ".com"));
        }

        @Test
        @DisplayName("deleteWhitespace — elimina todos los espacios")
        void deleteWhitespace_RemovesAll() {
            assertEquals("abc", StringUtils.deleteWhitespace("   ab  c  "));
            assertEquals("",    StringUtils.deleteWhitespace("   "));
        }
    }

    // =========================================================
    // 6. split / join  (5 tests)
    // =========================================================
    @Nested
    @DisplayName("6. split() y join()")
    class SplitJoinTests {

        @Test
        @DisplayName("split — por espacio")
        void split_BySpace() {
            assertArrayEquals(new String[]{"abc", "def"},
                    StringUtils.split("abc def"));
        }

        @Test
        @DisplayName("split — por carácter  |  null → null")
        void split_ByCharAndNull() {
            assertArrayEquals(new String[]{"a", "b", "c"},
                    StringUtils.split("a.b.c", '.'));
            assertNull(StringUtils.split(null));
        }

        @Test
        @DisplayName("join — array con separador char")
        void join_ArrayWithChar() {
            assertEquals("a;b;c",
                    StringUtils.join(new Object[]{"a", "b", "c"}, ';'));
        }

        @Test
        @DisplayName("join — null en array se representa vacío")
        void join_ArrayWithNull() {
            assertEquals(";;a",
                    StringUtils.join(new Object[]{null, "", "a"}, ';'));
        }

        @Test
        @DisplayName("joinWith — varargs con separador string")
        void joinWith_Varargs() {
            assertEquals("a,b,c", StringUtils.joinWith(",", "a", "b", "c"));
            assertEquals("",      StringUtils.joinWith(","));
        }
    }

    // =========================================================
    // 7. substring ops  (5 tests)
    // =========================================================
    @Nested
    @DisplayName("7. Operaciones de substring")
    class SubstringTests {

        @Test
        @DisplayName("substring — posición normal y negativa (desde el final)")
        void substring_NormalAndNegative() {
            assertEquals("c",  StringUtils.substring("abc", 2));
            assertEquals("bc", StringUtils.substring("abc", -2));
            assertEquals("",   StringUtils.substring("abc", 4));
        }

        @Test
        @DisplayName("left / right — n caracteres del inicio y final")
        void leftAndRight() {
            assertEquals("ab", StringUtils.left("abc", 2));
            assertEquals("bc", StringUtils.right("abc", 2));
            assertNull(StringUtils.left(null, 2));
        }

        @Test
        @DisplayName("mid — subcadena desde posición con longitud")
        void mid_FromPosition() {
            assertEquals("ab", StringUtils.mid("abc", 0, 2));
            assertEquals("c",  StringUtils.mid("abc", 2, 4));
        }

        @Test
        @DisplayName("substringBefore / substringAfter")
        void substringBeforeAfter() {
            assertEquals("a",   StringUtils.substringBefore("abcba", "b"));
            assertEquals("cba", StringUtils.substringAfter("abcba", "b"));
            assertEquals("abc", StringUtils.substringBefore("abc", "d"));
        }

        @Test
        @DisplayName("substringBetween — un delimitador  |  dos distintos  |  null")
        void substringBetween_Variants() {
            assertEquals("abc", StringUtils.substringBetween("tagabctag", "tag"));
            assertEquals("b",   StringUtils.substringBetween("wx[b]yz", "[", "]"));
            assertNull(StringUtils.substringBetween(null, "tag"));
        }
    }

    // =========================================================
    // 8. padding / reverse / abbreviate  (5 tests)
    // =========================================================
    @Nested
    @DisplayName("8. padding, reverse y abbreviate")
    class PaddingReverseAbbreviateTests {

        @Test
        @DisplayName("leftPad — espacio y carácter personalizado")
        void leftPad_SpaceAndChar() {
            assertEquals("  bat", StringUtils.leftPad("bat", 5));
            assertEquals("zzbat", StringUtils.leftPad("bat", 5, 'z'));
            assertEquals("bat",   StringUtils.leftPad("bat", 3));
        }

        @Test
        @DisplayName("rightPad / center")
        void rightPadAndCenter() {
            assertEquals("bat  ", StringUtils.rightPad("bat", 5));
            assertEquals("batyz", StringUtils.rightPad("bat", 5, "yz"));
            assertEquals(" ab ", StringUtils.center("ab", 4));
            assertNull(StringUtils.center(null, 4));
        }

        @Test
        @DisplayName("reverse — normal, null, vacío  |  reverseDelimited")
        void reverseVariants() {
            assertEquals("tab",   StringUtils.reverse("bat"));
            assertNull(StringUtils.reverse(null));
            assertEquals("",      StringUtils.reverse(""));
            assertEquals("c.b.a", StringUtils.reverseDelimited("a.b.c", '.'));
        }

        @Test
        @DisplayName("abbreviate — trunca y lanza excepción si maxWidth muy pequeño")
        void abbreviate_TruncatesAndThrows() {
            assertEquals("abc...", StringUtils.abbreviate("abcdefg", 6));
            assertEquals("abcdefg", StringUtils.abbreviate("abcdefg", 7));
            assertNull(StringUtils.abbreviate(null, 4));
            assertThrows(IllegalArgumentException.class,
                    () -> StringUtils.abbreviate("abcdefg", 3));
        }

        @Test
        @DisplayName("abbreviateMiddle — abrevia el centro  |  sin cambio si es corta")
        void abbreviateMiddle_Variants() {
            assertEquals("ab.f", StringUtils.abbreviateMiddle("abcdef", ".", 4));
            assertEquals("abc",  StringUtils.abbreviateMiddle("abc", ".", 3));
        }
    }

    // =========================================================
    // 9. isNumeric / isAlpha / equals  (5 tests)
    // =========================================================
    @Nested
    @DisplayName("9. isNumeric, isAlpha, equals y compare")
    class CharTypeEqualsTests {

        @Test
        @DisplayName("isNumeric — solo dígitos → true  |  con letras/signos → false")
        void isNumeric_ValidAndInvalid() {
            assertTrue(StringUtils.isNumeric("123"));
            assertFalse(StringUtils.isNumeric("ab2c"));
            assertFalse(StringUtils.isNumeric("12.3"));
            assertFalse(StringUtils.isNumeric("-123"));
            assertFalse(StringUtils.isNumeric(""));
        }

        @Test
        @DisplayName("isAlpha / isAlphanumeric")
        void isAlphaAndAlphanumeric() {
            assertTrue(StringUtils.isAlpha("abc"));
            assertFalse(StringUtils.isAlpha("ab2c"));
            assertTrue(StringUtils.isAlphanumeric("ab2c"));
            assertFalse(StringUtils.isAlphanumeric("ab c"));
        }

        @Test
        @DisplayName("equals — mismo valor, distintos casos, null/null")
        void equals_Variants() {
            assertTrue(StringUtils.equals("abc", "abc"));
            assertTrue(StringUtils.equals(null, null));
            assertFalse(StringUtils.equals("abc", "ABC"));
            assertTrue(StringUtils.equalsIgnoreCase("abc", "ABC"));
        }

        @Test
        @DisplayName("equalsAny — true si hay coincidencia en el set")
        void equalsAny_MatchInSet() {
            assertTrue(StringUtils.equalsAny("abc", "abc", "def"));
            assertFalse(StringUtils.equalsAny("abc", "ABC", "DEF"));
            assertTrue(StringUtils.equalsAnyIgnoreCase("abc", "ABC", "DEF"));
        }

        @Test
        @DisplayName("compare — null < no-null  |  iguales → 0")
        void compare_NullHandling() {
            assertTrue(StringUtils.compare(null, "a") < 0);
            assertTrue(StringUtils.compare("a", null) > 0);
            assertEquals(0, StringUtils.compare(null, null));
            assertEquals(0, StringUtils.compare("abc", "abc"));
        }
    }

    // =========================================================
    // 10. startsWith / repeat / misc  (5 tests)
    // =========================================================
    @Nested
    @DisplayName("10. startsWith, endsWith, repeat y misc")
    class StartsWithRepeatMiscTests {

        @Test
        @DisplayName("startsWith / startsWithIgnoreCase / startsWithAny")
        void startsWith_Variants() {
            assertTrue(StringUtils.startsWith("abcdef", "abc"));
            assertFalse(StringUtils.startsWith("ABCDEF", "abc"));
            assertTrue(StringUtils.startsWithIgnoreCase("ABCDEF", "abc"));
            assertTrue(StringUtils.startsWithAny("abcxyz", "abc", "xyz"));
        }

        @Test
        @DisplayName("endsWith / endsWithIgnoreCase")
        void endsWith_Variants() {
            assertTrue(StringUtils.endsWith("abcdef", "def"));
            assertFalse(StringUtils.endsWith("ABCDEF", "def"));
            assertTrue(StringUtils.endsWithIgnoreCase("ABCDEF", "def"));
        }

        @Test
        @DisplayName("repeat — cadena y carácter n veces")
        void repeat_StringAndChar() {
            assertEquals("aaa",  StringUtils.repeat("a", 3));
            assertEquals("abab", StringUtils.repeat("ab", 2));
            assertEquals("eee",  StringUtils.repeat('e', 3));
            assertEquals("",     StringUtils.repeat("a", 0));
        }

        @Test
        @DisplayName("normalizeSpace / getDigits / length")
        void normalizeSpaceGetDigitsLength() {
            assertEquals("Hello World",
                    StringUtils.normalizeSpace("  Hello   World  "));
            assertEquals("5417543010",
                    StringUtils.getDigits("(541) 754-3010"));
            assertEquals(0, StringUtils.length(null));
            assertEquals(3, StringUtils.length("abc"));
        }

        @Test
        @DisplayName("truncate / defaultString / getCommonPrefix")
        void truncateDefaultStringCommonPrefix() {
            assertEquals("abcd",    StringUtils.truncate("abcdefg", 4));
            assertEquals("",        StringUtils.defaultString(null));
            assertEquals("bat",     StringUtils.defaultString("bat"));
            assertEquals("i am a ", StringUtils.getCommonPrefix(
                    "i am a machine", "i am a robot"));
        }
    }
}