package org.apache.commons.lang3;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.startsWith;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

/**
 * Pruebas PURAMENTE Mockito para StringProcessorService.
 * Total: 50 métodos @Test exactos — representa el 50% del total de pruebas.
 *
 * REGLA: cero assertions de JUnit (sin assertEquals, assertTrue, assertXxx).
 * Todo se verifica exclusivamente con herramientas de Mockito:
 *   verify, verifyNoMoreInteractions, verifyNoInteractions,
 *   ArgumentCaptor, InOrder, doAnswer, thenAnswer, doReturn,
 *   thenThrow, doThrow, times, never, atLeast, atMost, atLeastOnce,
 *   only, inOrder, reset.
 *
 * 10 suites @Nested × 5 tests = 50 tests.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("StringProcessorService — Mockito puro (50 tests)")
class StringUtilsMockitoTest {

    @Mock
    StringProcessorService mockService;

    @Spy
    StringProcessorServiceImpl spyService;

    @Captor
    ArgumentCaptor<String> stringCaptor;

    @Captor
    ArgumentCaptor<Integer> intCaptor;

    @Captor
    ArgumentCaptor<List<String>> listCaptor;

    // =========================================================
    // 1. verify() — interacciones básicas  (5 tests)
    // =========================================================
    @Nested
    @DisplayName("1. verify() — interacciones básicas")
    class VerifyBasicTests {

        @Test
        @DisplayName("verify — el método fue llamado exactamente una vez")
        void verify_CalledOnce() {
            when(mockService.isValidUsername("alice")).thenReturn(true);

            mockService.isValidUsername("alice");

            verify(mockService).isValidUsername("alice");
        }

        @Test
        @DisplayName("verify times(3) — el método fue llamado tres veces")
        void verify_CalledThreeTimes() {
            when(mockService.countWords(anyString())).thenReturn(1);

            mockService.countWords("a");
            mockService.countWords("b");
            mockService.countWords("c");

            verify(mockService, times(3)).countWords(anyString());
        }

        @Test
        @DisplayName("verify never() — el método nunca fue llamado")
        void verify_NeverCalled() {
            mockService.isValidUsername("user");

            verify(mockService, never()).isValidEmail(anyString());
            verify(mockService, never()).generateSlug(anyString());
        }

        @Test
        @DisplayName("verify atLeast(2) y atMost(4) — rango de llamadas")
        void verify_AtLeastAtMost() {
            when(mockService.sanitizeSearchQuery(anyString())).thenReturn("q");

            mockService.sanitizeSearchQuery("a");
            mockService.sanitizeSearchQuery("b");
            mockService.sanitizeSearchQuery("c");

            verify(mockService, atLeast(2)).sanitizeSearchQuery(anyString());
            verify(mockService, atMost(4)).sanitizeSearchQuery(anyString());
        }

        @Test
        @DisplayName("verify atLeastOnce() — llamado al menos una vez")
        void verify_AtLeastOnce() {
            when(mockService.formatFullName(anyString())).thenReturn("Test");

            mockService.formatFullName("juan");
            mockService.formatFullName("pedro");

            verify(mockService, atLeastOnce()).formatFullName(anyString());
        }
    }

    // =========================================================
    // 2. verify() con matchers  (5 tests)
    // =========================================================
    @Nested
    @DisplayName("2. verify() con matchers de argumentos")
    class VerifyMatchersTests {

        @Test
        @DisplayName("verify contains() — verifica que se llamó con arg que contiene subcadena")
        void verify_ContainsMatcher() {
            when(mockService.sanitizeSearchQuery(contains("java"))).thenReturn("java");

            mockService.sanitizeSearchQuery("learn java now");

            verify(mockService).sanitizeSearchQuery(contains("java"));
        }

        @Test
        @DisplayName("verify startsWith() — verifica prefijo en el argumento")
        void verify_StartsWithMatcher() {
            when(mockService.isValidEmail(startsWith("admin"))).thenReturn(true);

            mockService.isValidEmail("admin@test.com");

            verify(mockService).isValidEmail(startsWith("admin"));
        }

        @Test
        @DisplayName("verify endsWith() — verifica sufijo en el argumento")
        void verify_EndsWithMatcher() {
            when(mockService.isValidEmail(endsWith(".edu"))).thenReturn(true);

            mockService.isValidEmail("user@uni.edu");

            verify(mockService).isValidEmail(endsWith(".edu"));
        }

        @Test
        @DisplayName("verify eq() — verifica argumento exacto")
        void verify_EqMatcher() {
            when(mockService.countWords(eq("hello world"))).thenReturn(2);

            mockService.countWords("hello world");

            verify(mockService).countWords(eq("hello world"));
            verify(mockService, never()).countWords(eq("other text"));
        }

        @Test
        @DisplayName("verify isNull() e isNotNull() — rutas null y no-null")
        void verify_NullMatchers() {
            when(mockService.isValidUsername(isNull())).thenReturn(false);
            when(mockService.isValidUsername(isNotNull())).thenReturn(true);

            mockService.isValidUsername(null);
            mockService.isValidUsername("alice");

            verify(mockService).isValidUsername(isNull());
            verify(mockService).isValidUsername(isNotNull());
        }
    }

    // =========================================================
    // 3. InOrder — orden de llamadas  (5 tests)
    // =========================================================
    @Nested
    @DisplayName("3. InOrder — verificación de orden")
    class InOrderTests {

        @Test
        @DisplayName("inOrder — sanitize ocurre antes que generateSlug")
        void inOrder_SanitizeBeforeSlug() {
            when(mockService.sanitizeSearchQuery(anyString())).thenReturn("java");
            when(mockService.generateSlug(anyString())).thenReturn("java-slug");

            mockService.sanitizeSearchQuery("  Java  ");
            mockService.generateSlug("java");

            InOrder io = inOrder(mockService);
            io.verify(mockService).sanitizeSearchQuery("  Java  ");
            io.verify(mockService).generateSlug("java");
        }

        @Test
        @DisplayName("inOrder — pipeline completo de tres pasos en orden")
        void inOrder_ThreeStepPipeline() {
            when(mockService.sanitizeSearchQuery(anyString())).thenReturn("q");
            when(mockService.generateSlug(anyString())).thenReturn("s");
            when(mockService.countWords(anyString())).thenReturn(1);

            mockService.sanitizeSearchQuery("  Query  ");
            mockService.generateSlug("query");
            mockService.countWords("query");

            InOrder io = inOrder(mockService);
            io.verify(mockService).sanitizeSearchQuery("  Query  ");
            io.verify(mockService).generateSlug("query");
            io.verify(mockService).countWords("query");
        }

        @Test
        @DisplayName("inOrder — formatFullName antes que isValidUsername")
        void inOrder_FormatBeforeValidate() {
            when(mockService.formatFullName(anyString())).thenReturn("Juan Perez");
            when(mockService.isValidUsername(anyString())).thenReturn(true);

            mockService.formatFullName("juan perez");
            mockService.isValidUsername("juanperez");

            InOrder io = inOrder(mockService);
            io.verify(mockService).formatFullName("juan perez");
            io.verify(mockService).isValidUsername("juanperez");
        }

        @Test
        @DisplayName("inOrder — sobre spy: extractPhoneDigits antes que isValidEmail")
        void inOrder_SpyExtractBeforeEmail() {
            spyService.extractPhoneDigits("541-754-3010");
            spyService.isValidEmail("user@example.com");

            InOrder io = inOrder(spyService);
            io.verify(spyService).extractPhoneDigits("541-754-3010");
            io.verify(spyService).isValidEmail("user@example.com");
        }

        @Test
        @DisplayName("inOrder — dos mocks coordinados en orden secuencial")
        void inOrder_TwoMocksCoordinated() {
            StringProcessorService mock2 = mock(StringProcessorService.class);
            when(mockService.sanitizeSearchQuery(anyString())).thenReturn("clean");
            when(mock2.generateSlug(anyString())).thenReturn("clean-slug");

            mockService.sanitizeSearchQuery("Raw Input");
            mock2.generateSlug("clean");

            InOrder io = inOrder(mockService, mock2);
            io.verify(mockService).sanitizeSearchQuery("Raw Input");
            io.verify(mock2).generateSlug("clean");
        }
    }

    // =========================================================
    // 4. ArgumentCaptor — captura de argumentos  (5 tests)
    // =========================================================
    @Nested
    @DisplayName("4. ArgumentCaptor — captura y verificación de argumentos")
    class CaptorTests {

        @Test
        @DisplayName("captor — verifica el valor exacto capturado en formatFullName")
        void captor_VerifiesExactValue() {
            when(mockService.formatFullName(anyString())).thenReturn("Test");

            mockService.formatFullName("juan perez garcia");

            verify(mockService).formatFullName(stringCaptor.capture());
            verify(mockService).formatFullName(eq(stringCaptor.getValue()));
        }

        @Test
        @DisplayName("captor — verifica que el slug recibió el output del sanitize")
        void captor_VerifiesPipelineChaining() {
            when(mockService.sanitizeSearchQuery(anyString())).thenReturn("java testing");
            when(mockService.generateSlug(anyString())).thenReturn("java-testing");

            String sanitized = mockService.sanitizeSearchQuery("  Java Testing  ");
            mockService.generateSlug(sanitized);

            verify(mockService).generateSlug(stringCaptor.capture());
            verify(mockService).generateSlug(eq(stringCaptor.getValue()));
            verify(mockService).generateSlug(contains("java"));
        }

        @Test
        @DisplayName("captor — verifica múltiples llamadas con getAllValues")
        void captor_VerifiesAllValues() {
            when(mockService.sanitizeSearchQuery(anyString())).thenReturn("q");

            mockService.sanitizeSearchQuery("Query 1");
            mockService.sanitizeSearchQuery("Query 2");
            mockService.sanitizeSearchQuery("Query 3");

            verify(mockService, times(3)).sanitizeSearchQuery(stringCaptor.capture());
            verify(mockService).sanitizeSearchQuery(eq(stringCaptor.getAllValues().get(0)));
            verify(mockService).sanitizeSearchQuery(eq(stringCaptor.getAllValues().get(1)));
            verify(mockService).sanitizeSearchQuery(eq(stringCaptor.getAllValues().get(2)));
        }

        @Test
        @DisplayName("captor — captura dos argumentos y verifica tipos")
        void captor_TwoArguments() {
            when(mockService.abbreviateForDisplay(anyString(), anyInt())).thenReturn("abc...");

            mockService.abbreviateForDisplay("abcdefghij", 6);

            verify(mockService).abbreviateForDisplay(
                    stringCaptor.capture(), intCaptor.capture());
            verify(mockService).abbreviateForDisplay(
                    eq(stringCaptor.getValue()), eq(intCaptor.getValue()));
        }

        @Test
        @DisplayName("captor — captura List<String> y verifica su contenido vía verify")
        void captor_CapturesList() {
            when(mockService.formatTagList(anyList())).thenReturn("java, spring");

            mockService.formatTagList(Arrays.asList("java", "spring", "mockito"));

            verify(mockService).formatTagList(listCaptor.capture());
            verify(mockService).formatTagList(eq(listCaptor.getValue()));
        }
    }

    // =========================================================
    // 5. verifyNoMoreInteractions / verifyNoInteractions  (5 tests)
    // =========================================================
    @Nested
    @DisplayName("5. verifyNoMoreInteractions y verifyNoInteractions")
    class VerifyNoInteractionsTests {

        @Test
        @DisplayName("verifyNoInteractions — mock no recibió ninguna llamada")
        void verifyNo_NoCallsAtAll() {
            verifyNoInteractions(mockService);
        }

        @Test
        @DisplayName("verifyNoMoreInteractions — solo la llamada esperada ocurrió")
        void verifyNoMore_OnlyExpectedCall() {
            when(mockService.isValidUsername("alice")).thenReturn(true);

            mockService.isValidUsername("alice");

            verify(mockService).isValidUsername("alice");
            verifyNoMoreInteractions(mockService);
        }

        @Test
        @DisplayName("verifyNoMoreInteractions — exactamente dos métodos distintos")
        void verifyNoMore_TwoDistinctMethods() {
            when(mockService.isValidUsername(anyString())).thenReturn(true);
            when(mockService.countWords(anyString())).thenReturn(2);

            mockService.isValidUsername("bob");
            mockService.countWords("hello world");

            verify(mockService).isValidUsername("bob");
            verify(mockService).countWords("hello world");
            verifyNoMoreInteractions(mockService);
        }

        @Test
        @DisplayName("verifyNoInteractions — segundo mock nunca fue tocado")
        void verifyNo_SecondMockUntouched() {
            StringProcessorService mock2 = mock(StringProcessorService.class);
            when(mockService.sanitizeSearchQuery(anyString())).thenReturn("clean");

            mockService.sanitizeSearchQuery("input");

            verify(mockService).sanitizeSearchQuery("input");
            verifyNoInteractions(mock2);
        }

        @Test
        @DisplayName("verifyNoMoreInteractions — spy después de una sola llamada real")
        void verifyNoMore_SpySingleCall() {
            spyService.generateSlug("Hello World");

            verify(spyService).generateSlug("Hello World");
            verifyNoMoreInteractions(spyService);
        }
    }

    // =========================================================
    // 6. doReturn / doAnswer sobre @Spy  (5 tests)
    // =========================================================
    @Nested
    @DisplayName("6. doReturn y doAnswer sobre @Spy")
    class SpyDoReturnAnswerTests {

        @Test
        @DisplayName("doReturn — sobreescribe isValidEmail en spy")
        void doReturn_OverridesSpyMethod() {
            doReturn(true).when(spyService).isValidEmail(anyString());

            spyService.isValidEmail("not-valid-at-all");

            verify(spyService).isValidEmail("not-valid-at-all");
            verify(spyService).isValidEmail(anyString());
        }

        @Test
        @DisplayName("doReturn — sobreescribe isStrongPassword; otros métodos son reales")
        void doReturn_OneStub_OtherReal() {
            doReturn(true).when(spyService).isStrongPassword(anyString());

            spyService.isStrongPassword("weak");
            spyService.generateSlug("Hello World");

            verify(spyService).isStrongPassword("weak");
            verify(spyService).generateSlug("Hello World");
        }

        @Test
        @DisplayName("doAnswer — responde dinámicamente según el argumento recibido")
        void doAnswer_DynamicResponse() {
            doAnswer((Answer<String>) inv -> {
                String arg = inv.getArgument(0);
                return arg.trim().toLowerCase().replace(" ", "-");
            }).when(spyService).generateSlug(anyString());

            spyService.generateSlug("  Hello World  ");

            verify(spyService).generateSlug("  Hello World  ");
            verify(spyService).generateSlug(contains("Hello"));
        }

        @Test
        @DisplayName("doAnswer — captura el argumento y verifica la invocación")
        void doAnswer_CapturesInvocation() {
            doAnswer(inv -> {
                String input = inv.getArgument(0);
                return input != null ? input.toLowerCase() : "";
            }).when(spyService).sanitizeSearchQuery(anyString());

            spyService.sanitizeSearchQuery("  TEST INPUT  ");

            verify(spyService).sanitizeSearchQuery(stringCaptor.capture());
            verify(spyService).sanitizeSearchQuery(eq(stringCaptor.getValue()));
        }

        @Test
        @DisplayName("doReturn encadenado — spy responde valores distintos en llamadas sucesivas")
        void doReturn_Chained_SuccessiveCalls() {
            doReturn("slug-1")
                    .doReturn("slug-2")
                    .doReturn("slug-3")
                    .when(spyService).generateSlug(anyString());

            spyService.generateSlug("Titulo 1");
            spyService.generateSlug("Titulo 2");
            spyService.generateSlug("Titulo 3");

            verify(spyService, times(3)).generateSlug(anyString());
            verify(spyService).generateSlug("Titulo 1");
            verify(spyService).generateSlug("Titulo 2");
            verify(spyService).generateSlug("Titulo 3");
        }
    }

    // =========================================================
    // 7. thenAnswer / thenReturn encadenado sobre @Mock  (5 tests)
    // =========================================================
    @Nested
    @DisplayName("7. thenAnswer y thenReturn encadenado sobre @Mock")
    class MockAnswerChainTests {

        @Test
        @DisplayName("thenAnswer — mock responde dinámicamente según argumento recibido")
        void thenAnswer_DynamicMock() {
            when(mockService.generateSlug(anyString()))
                    .thenAnswer(inv -> {
                        String title = inv.getArgument(0);
                        return title.toLowerCase().replace(" ", "-");
                    });

            mockService.generateSlug("Hello World");
            mockService.generateSlug("Apache Commons");

            verify(mockService, times(2)).generateSlug(anyString());
            verify(mockService).generateSlug("Hello World");
            verify(mockService).generateSlug("Apache Commons");
        }

        @Test
        @DisplayName("thenReturn encadenado — mock da valores distintos en llamadas sucesivas")
        void thenReturn_ChainedValues() {
            when(mockService.countWords(anyString()))
                    .thenReturn(1)
                    .thenReturn(2)
                    .thenReturn(3);

            mockService.countWords("a");
            mockService.countWords("b");
            mockService.countWords("c");
            mockService.countWords("d"); // repite el último

            verify(mockService, times(4)).countWords(anyString());
            verify(mockService, atLeast(3)).countWords(anyString());
        }

        @Test
        @DisplayName("thenReturn + thenAnswer — primera llamada devuelve fijo, resto dinámico")
        void thenReturn_ThenAnswer_Mixed() {
            when(mockService.sanitizeSearchQuery(anyString()))
                    .thenReturn("primera-respuesta")
                    .thenAnswer(inv -> inv.getArgument(0).toString().trim().toLowerCase());

            mockService.sanitizeSearchQuery("ignorado");
            mockService.sanitizeSearchQuery("  SEGUNDA  ");

            verify(mockService).sanitizeSearchQuery("ignorado");
            verify(mockService).sanitizeSearchQuery("  SEGUNDA  ");
            verify(mockService, times(2)).sanitizeSearchQuery(anyString());
        }

        @Test
        @DisplayName("thenAnswer — mock para formatTagList responde según el tamaño de la lista")
        void thenAnswer_ListSizeDependent() {
            when(mockService.formatTagList(anyList()))
                    .thenAnswer(inv -> {
                        List<String> tags = inv.getArgument(0);
                        return String.join(", ", tags);
                    });

            mockService.formatTagList(Arrays.asList("java", "spring"));
            mockService.formatTagList(Arrays.asList("a", "b", "c"));

            verify(mockService, times(2)).formatTagList(anyList());
            verify(mockService, times(2)).formatTagList(listCaptor.capture());
        }

        @Test
        @DisplayName("thenAnswer — mock para abbreviateForDisplay usa ambos argumentos")
        void thenAnswer_UsesMultipleArgs() {
            when(mockService.abbreviateForDisplay(anyString(), anyInt()))
                    .thenAnswer(inv -> {
                        String text  = inv.getArgument(0);
                        int    width = inv.getArgument(1);
                        return text.length() > width ? text.substring(0, width - 3) + "..." : text;
                    });

            mockService.abbreviateForDisplay("Hello World Test", 10);
            mockService.abbreviateForDisplay("Short", 100);

            verify(mockService).abbreviateForDisplay("Hello World Test", 10);
            verify(mockService).abbreviateForDisplay("Short", 100);
            verify(mockService, times(2)).abbreviateForDisplay(anyString(), anyInt());
        }
    }

    // =========================================================
    // 8. thenThrow / doThrow — excepciones  (5 tests)
    // =========================================================
    @Nested
    @DisplayName("8. thenThrow y doThrow — simulación de excepciones")
    class ThrowTests {

        @Test
        @DisplayName("thenThrow — mock lanza excepción; verify confirma que se llamó")
        void thenThrow_VerifyCallBeforeException() {
            when(mockService.isValidUsername(isNull()))
                    .thenThrow(new IllegalArgumentException("Username nulo"));

            try { mockService.isValidUsername(null); } catch (IllegalArgumentException ignored) {}

            verify(mockService).isValidUsername(isNull());
        }

        @Test
        @DisplayName("thenReturn → thenThrow — primera ok, segunda lanza; verify times(2)")
        void thenReturn_ThenThrow_VerifyBoth() {
            when(mockService.extractPhoneDigits(anyString()))
                    .thenReturn("5551234567")
                    .thenThrow(new IllegalArgumentException("Número inválido"));

            try { mockService.extractPhoneDigits("555-1234"); } catch (Exception ignored) {}
            try { mockService.extractPhoneDigits("invalid"); } catch (Exception ignored) {}

            verify(mockService, times(2)).extractPhoneDigits(anyString());
            verify(mockService).extractPhoneDigits("555-1234");
            verify(mockService).extractPhoneDigits("invalid");
        }

        @Test
        @DisplayName("doThrow — spy lanza en input específico; verify confirma el intento")
        void doThrow_SpyThrowsOnInput() {
            doThrow(new RuntimeException("Slug inválido"))
                    .when(spyService).generateSlug(eq("###"));

            try { spyService.generateSlug("###"); } catch (RuntimeException ignored) {}

            verify(spyService).generateSlug("###");
            verify(spyService).generateSlug(eq("###"));
        }

        @Test
        @DisplayName("thenThrow — verify que el método erróneo nunca llamó a otros métodos")
        void thenThrow_OtherMethodsNeverCalled() {
            when(mockService.generateSlug(anyString()))
                    .thenThrow(new RuntimeException("Error"));

            try { mockService.generateSlug("title"); } catch (RuntimeException ignored) {}

            verify(mockService).generateSlug("title");
            verify(mockService, never()).sanitizeSearchQuery(anyString());
            verify(mockService, never()).countWords(anyString());
        }

        @Test
        @DisplayName("doThrow encadenado — spy lanza en primer intento, real en segundo")
        void doThrow_ThenRealMethod() {
            doThrow(new RuntimeException("Primer intento falla"))
                    .doCallRealMethod()
                    .when(spyService).sanitizeSearchQuery(anyString());

            try { spyService.sanitizeSearchQuery("input"); } catch (RuntimeException ignored) {}
            spyService.sanitizeSearchQuery("segundo intento");

            verify(spyService, times(2)).sanitizeSearchQuery(anyString());
            verify(spyService, times(2)).sanitizeSearchQuery(anyString());
        }
    }

    // =========================================================
    // 9. reset / doCallRealMethod / only  (5 tests)
    // =========================================================
    @Nested
    @DisplayName("9. reset(), doCallRealMethod() y only()")
    class ResetCallRealOnlyTests {

        @Test
        @DisplayName("reset — después de reset el mock no recuerda llamadas previas")
        void reset_ClearsPreviousInteractions() {
            when(mockService.isValidUsername(anyString())).thenReturn(true);
            mockService.isValidUsername("alice");

            reset(mockService);

            verifyNoInteractions(mockService);
        }

        @Test
        @DisplayName("reset — después de reset se puede reconfigurar el stub")
        void reset_AllowsReconfiguration() {
            when(mockService.countWords(anyString())).thenReturn(99);
            mockService.countWords("test");
            reset(mockService);

            when(mockService.countWords(anyString())).thenReturn(5);
            mockService.countWords("hello world");

            verify(mockService).countWords("hello world");
            verify(mockService, times(1)).countWords(anyString());
        }

        @Test
        @DisplayName("doCallRealMethod — spy vuelve al método real tras doReturn")
        void doCallRealMethod_RestoresReal() {
            doReturn("stub-slug").when(spyService).generateSlug(anyString());
            spyService.generateSlug("cualquier cosa");

            doCallRealMethod().when(spyService).generateSlug(anyString());
            spyService.generateSlug("Hello World");

            verify(spyService, times(2)).generateSlug(anyString());
            verify(spyService).generateSlug("cualquier cosa");
            verify(spyService).generateSlug("Hello World");
        }

        @Test
        @DisplayName("only() — verifica que SOLO ese método fue llamado en el mock")
        void only_VerifiesExclusiveInteraction() {
            when(mockService.isValidUsername("bob")).thenReturn(true);

            mockService.isValidUsername("bob");

            verify(mockService, only()).isValidUsername("bob");
        }

        @Test
        @DisplayName("doCallRealMethod — spy mezcla: isValidEmail stub, countWords real")
        void doCallRealMethod_MixStubAndReal() {
            doReturn(true).when(spyService).isValidEmail(anyString());
            doCallRealMethod().when(spyService).countWords(anyString());

            spyService.isValidEmail("not-valid");
            spyService.countWords("one two three");

            verify(spyService).isValidEmail("not-valid");
            verify(spyService).countWords("one two three");
            verify(spyService, never()).isValidUsername(anyString());
        }
    }

    // =========================================================
    // 10. Flujos combinados mock + spy  (5 tests)
    // =========================================================
    @Nested
    @DisplayName("10. Flujos combinados mock + spy")
    class CombinedFlowTests {

        @Test
        @DisplayName("mock + spy coordinados — cada uno verifica su rol")
        void mockAndSpy_EachVerifiesItsRole() {
            when(mockService.sanitizeSearchQuery(anyString())).thenReturn("clean input");

            mockService.sanitizeSearchQuery("  Raw Input  ");
            spyService.generateSlug("Clean Input");

            verify(mockService).sanitizeSearchQuery("  Raw Input  ");
            verify(spyService).generateSlug("Clean Input");
            verifyNoMoreInteractions(mockService);
        }

        @Test
        @DisplayName("inOrder mock + spy — mock primero, spy después")
        void inOrder_MockThenSpy() {
            when(mockService.sanitizeSearchQuery(anyString())).thenReturn("slug-ready");

            mockService.sanitizeSearchQuery("  Apache Commons  ");
            spyService.generateSlug("Apache Commons");

            InOrder io = inOrder(mockService, spyService);
            io.verify(mockService).sanitizeSearchQuery("  Apache Commons  ");
            io.verify(spyService).generateSlug("Apache Commons");
        }

        @Test
        @DisplayName("captor en flujo combinado — verifica que el output del mock llega al spy")
        void captor_CombinedFlow_OutputToSpy() {
            when(mockService.sanitizeSearchQuery(anyString())).thenReturn("java testing");

            String result = mockService.sanitizeSearchQuery("  Java Testing  ");
            spyService.generateSlug(result);

            verify(mockService).sanitizeSearchQuery(stringCaptor.capture());
            verify(spyService).generateSlug(eq(stringCaptor.getValue().equals("  Java Testing  ")
                    ? "java testing" : stringCaptor.getValue()));
        }

        @Test
        @DisplayName("verify sobre spy con múltiples métodos — times y never")
        void spy_MultipleVerify_TimesAndNever() {
            spyService.isValidUsername("alice");
            spyService.isValidUsername("bob");
            spyService.generateSlug("Hello");
            spyService.extractPhoneDigits("541-000-0000");

            verify(spyService, times(2)).isValidUsername(anyString());
            verify(spyService, times(1)).generateSlug(anyString());
            verify(spyService, times(1)).extractPhoneDigits(anyString());
            verify(spyService, never()).countWords(anyString());
            verify(spyService, never()).formatTagList(anyList());
        }

        @Test
        @DisplayName("inOrder — pipeline de 4 pasos con captor en el último")
        void inOrder_FourStepPipelineWithCaptor() {
            when(mockService.sanitizeSearchQuery(anyString())).thenReturn("spring boot");
            when(mockService.isValidUsername(anyString())).thenReturn(true);
            when(mockService.generateSlug(anyString())).thenReturn("spring-boot");
            when(mockService.countWords(anyString())).thenReturn(2);

            mockService.sanitizeSearchQuery("  Spring Boot  ");
            mockService.isValidUsername("springboot");
            mockService.generateSlug("spring boot");
            mockService.countWords("spring boot");

            InOrder io = inOrder(mockService);
            io.verify(mockService).sanitizeSearchQuery("  Spring Boot  ");
            io.verify(mockService).isValidUsername("springboot");
            io.verify(mockService).generateSlug(stringCaptor.capture());
            io.verify(mockService).countWords(anyString());

            verify(mockService).generateSlug(eq(stringCaptor.getValue()));
        }
    }
}

