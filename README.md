# 🚀 QA Engineering: JUnit 5 & Mockito 5

**Autor:** Quispe Luque, Juan Alexis  
**Carrera:** Ingeniería de Software  
**Institución:** Universidad La Salle

Proyecto desarrollado para el **Examen Parcial de Pruebas de Software (Semestre IX)**. Se enfoca en la implementación de una suite de 100 pruebas automatizadas con un balance perfecto entre validación de estado e interacción.

## 📊 Distribución de Pruebas (50/50)

- **50% JUnit 5 (Estado):** Pruebas aplicadas sobre `Apache Commons StringUtils`.
- **50% Mockito 5 (Comportamiento):** Pruebas de integración simulada en `StringProcessorService`.

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java 17+
* **Gestor de Dependencias:** Maven
* **Frameworks de Testing:** 
    * JUnit 5 (Jupyter)
    * Mockito 5
* **Librerías Externas:** Apache Commons Lang3

## 🧪 Cobertura de Pruebas (100 Tests Totales)

### Parte 1: JUnit 5 (50 Tests)
Organizado en 10 suites `@Nested` que cubren:
- Operaciones de vacío y blanco (`isBlank`, `isEmpty`).
- Manipulación de casos (`capitalize`, `swapCase`).
- Búsqueda avanzada y conteo de matches.
- Transformaciones de cadena (Padding, Abbreviate, Reverse).

### Parte 2: Mockito 5 (50 Tests)
Implementación de técnicas avanzadas para simular servicios reales:
- **Verificación de Orden:** Uso de `InOrder` para asegurar flujos lógicos secuenciales.
- **Captura de Datos:** Uso de `ArgumentCaptor` para inspeccionar parámetros en tiempo de ejecución.
- **Dobles de Prueba:** Uso estratégico de `@Mock` para dependencias totales y `@Spy` para comportamiento parcial.
- **Simulación de Errores:** Pruebas de robustez mediante el lanzamiento de excepciones con `thenThrow`.

## 🚀 Ejecución de Pruebas

Para ejecutar la suite completa de pruebas desde la terminal, utiliza:

```bash
mvn test