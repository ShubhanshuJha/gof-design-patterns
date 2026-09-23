import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Factory Method pattern demonstrated with format-specific record parsers.
 */
public final class FactoryMethod {
    private FactoryMethod() {
    }

    /**
     * Product interface used by the ingestion workflow.
     */
    public interface RecordParser {
        List<Map<String, String>> parse(String payload);
    }

    /**
     * Concrete product for comma-separated records.
     */
    public static final class CsvRecordParser implements RecordParser {
        @Override
        public List<Map<String, String>> parse(String payload) {
            return parseDelimited(payload, ',');
        }
    }

    /**
     * Concrete product for tab-separated records.
     */
    public static final class TsvRecordParser implements RecordParser {
        @Override
        public List<Map<String, String>> parse(String payload) {
            return parseDelimited(payload, '\t');
        }
    }

    /**
     * Creator containing the stable ingestion workflow.
     *
     * createParser is the Factory Method. The workflow does not know
     * which concrete parser it receives.
     */
    public abstract static class IngestionJob {
        public final List<Map<String, String>> run(String payload) {
            RecordParser parser = createParser();
            List<Map<String, String>> records = parser.parse(payload);
            validateRecords(records);
            return records;
        }

        public abstract RecordParser createParser();

        private static void validateRecords(List<Map<String, String>> records) {
            if (records.stream().anyMatch(Map::isEmpty)) {
                throw new IllegalArgumentException("Records must not be empty");
            }
        }
    }

    /**
     * Concrete creator that creates a CSV parser.
     */
    public static final class CsvIngestionJob extends IngestionJob {
        @Override
        public RecordParser createParser() {
            return new CsvRecordParser();
        }
    }

    /**
     * Concrete creator that creates a TSV parser.
     */
    public static final class TsvIngestionJob extends IngestionJob {
        @Override
        public RecordParser createParser() {
            return new TsvRecordParser();
        }
    }

    /**
     * Client function independent of concrete parser classes.
     */
    public static List<Map<String, String>> ingest(
            IngestionJob job,
            String payload
    ) {
        return job.run(payload);
    }

    private static List<Map<String, String>> parseDelimited(
            String payload,
            char delimiter
    ) {
        Objects.requireNonNull(payload, "payload must not be null");

        String trimmedPayload = payload.strip();
        if (trimmedPayload.isEmpty()) {
            return List.of();
        }

        String[] lines = trimmedPayload.split("\\R");
        String[] headers = splitLine(lines[0], delimiter);
        List<Map<String, String>> records = new ArrayList<>();

        for (int lineNumber = 1; lineNumber < lines.length; lineNumber++) {
            String[] values = splitLine(lines[lineNumber], delimiter);
            if (values.length != headers.length) {
                throw new IllegalArgumentException(
                        "Column count mismatch on line " + (lineNumber + 1)
                );
            }

            Map<String, String> record = new LinkedHashMap<>();
            for (int column = 0; column < headers.length; column++) {
                record.put(headers[column].trim(), values[column].trim());
            }
            records.add(record);
        }

        return records;
    }

    private static String[] splitLine(String line, char delimiter) {
        return line.split(Pattern.quote(String.valueOf(delimiter)), -1);
    }

    public static void main(String[] args) {
        String csvPayload = "asset_id,power_kw\nINV-01,42.5\nINV-02,38.1\n";
        String tsvPayload = "asset_id\\tpower_kw\nINV-01\\t42.5\n";

        for (IngestionJob job : List.of(
                new CsvIngestionJob(),
                new TsvIngestionJob()
        )) {
            String payload = job instanceof CsvIngestionJob
                    ? csvPayload
                    : tsvPayload;
            System.out.println(
                    job.getClass().getSimpleName() + ": "
                            + ingest(job, payload)
            );
        }
    }
}

