import java.util.List;
import java.util.Map;

/**
 * Lightweight tests for the Factory Method example.
 *
 * Run with:
 *   javac FactoryMethod.java FactoryMethodTest.java
 *   java -ea FactoryMethodTest
 */
public final class FactoryMethodTest {
    private FactoryMethodTest() {
    }

    public static void main(String[] args) {
        testCsvCreatorProducesCsvParser();
        testTsvCreatorProducesTsvParser();
        testCsvJobRunsCommonWorkflow();
        testTsvJobRunsCommonWorkflow();
        testRejectsColumnMismatch();

        System.out.println("All Factory Method tests passed.");
    }

    private static void testCsvCreatorProducesCsvParser() {
        assertTrue(
                new FactoryMethod.CsvIngestionJob().createParser()
                        instanceof FactoryMethod.CsvRecordParser,
                "CSV creator should produce a CSV parser"
        );
    }

    private static void testTsvCreatorProducesTsvParser() {
        assertTrue(
                new FactoryMethod.TsvIngestionJob().createParser()
                        instanceof FactoryMethod.TsvRecordParser,
                "TSV creator should produce a TSV parser"
        );
    }

    private static void testCsvJobRunsCommonWorkflow() {
        String payload = "asset_id,power_kw\nINV-01,42.5\n";
        List<Map<String, String>> expected = List.of(
                Map.of("asset_id", "INV-01", "power_kw", "42.5")
        );

        assertEquals(
                expected,
                FactoryMethod.ingest(
                        new FactoryMethod.CsvIngestionJob(),
                        payload
                ),
                "CSV ingestion should return parsed records"
        );
    }

    private static void testTsvJobRunsCommonWorkflow() {
        String payload = "asset_id\\tpower_kw\nINV-01\\t42.5\n";
        List<Map<String, String>> expected = List.of(
                Map.of("asset_id", "INV-01", "power_kw", "42.5")
        );

        assertEquals(
                expected,
                FactoryMethod.ingest(
                        new FactoryMethod.TsvIngestionJob(),
                        payload
                ),
                "TSV ingestion should return parsed records"
        );
    }

    private static void testRejectsColumnMismatch() {
        String invalidPayload = "asset_id,power_kw\nINV-01\n";

        try {
            FactoryMethod.ingest(
                    new FactoryMethod.CsvIngestionJob(),
                    invalidPayload
            );
            throw new AssertionError("Expected a column mismatch exception");
        } catch (IllegalArgumentException expected) {
            // Expected path.
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertEquals(
            Object expected,
            Object actual,
            String message
    ) {
        if (!expected.equals(actual)) {
            throw new AssertionError(
                    message + System.lineSeparator()
                            + "Expected: " + expected + System.lineSeparator()
                            + "Actual: " + actual
            );
        }
    }
}

