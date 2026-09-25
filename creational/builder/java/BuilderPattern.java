import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Builder pattern demonstrated with multiple report representations.
 */
public final class BuilderPattern {
    private BuilderPattern() {
    }

    public interface ReportDocument {
        String render();
    }

    public static final class MarkdownReport implements ReportDocument {
        private final String title;
        private final List<String> sections;
        private final String footer;
        private final Map<String, String> metadata;

        private MarkdownReport(
                String title,
                List<String> sections,
                String footer,
                Map<String, String> metadata
        ) {
            this.title = title;
            this.sections = List.copyOf(sections);
            this.footer = footer;
            this.metadata = Map.copyOf(metadata);
        }

        @Override
        public String render() {
            StringBuilder output = new StringBuilder("# ")
                    .append(title)
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());

            for (String section : sections) {
                output.append(section)
                        .append(System.lineSeparator())
                        .append(System.lineSeparator());
            }

            if (!metadata.isEmpty()) {
                output.append("## Metadata")
                        .append(System.lineSeparator())
                        .append(System.lineSeparator());

                metadata.forEach((key, value) -> output
                        .append("- ")
                        .append(key)
                        .append(": ")
                        .append(value)
                        .append(System.lineSeparator()));

                output.append(System.lineSeparator());
            }

            if (footer != null) {
                output.append(footer).append(System.lineSeparator());
            }

            return output.toString().strip();
        }
    }

    public static final class PlainTextReport implements ReportDocument {
        private final String title;
        private final List<String> sections;
        private final String footer;
        private final Map<String, String> metadata;

        private PlainTextReport(
                String title,
                List<String> sections,
                String footer,
                Map<String, String> metadata
        ) {
            this.title = title;
            this.sections = List.copyOf(sections);
            this.footer = footer;
            this.metadata = Map.copyOf(metadata);
        }

        @Override
        public String render() {
            StringBuilder output = new StringBuilder(title)
                    .append(System.lineSeparator())
                    .append("=" .repeat(title.length()))
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());

            for (String section : sections) {
                output.append(section)
                        .append(System.lineSeparator())
                        .append(System.lineSeparator());
            }

            if (!metadata.isEmpty()) {
                output.append("Metadata:")
                        .append(System.lineSeparator());

                metadata.forEach((key, value) -> output
                        .append(key)
                        .append(": ")
                        .append(value)
                        .append(System.lineSeparator()));

                output.append(System.lineSeparator());
            }

            if (footer != null) {
                output.append(footer).append(System.lineSeparator());
            }

            return output.toString().strip();
        }
    }

    public abstract static class ReportBuilder {
        private String title;
        private final List<String> sections = new ArrayList<>();
        private String footer;
        private final Map<String, String> metadata = new LinkedHashMap<>();

        public ReportBuilder reset() {
            title = null;
            sections.clear();
            footer = null;
            metadata.clear();
            return this;
        }

        public ReportBuilder setTitle(String title) {
            this.title = requireText(title, "title");
            return this;
        }

        public ReportBuilder addSection(String section) {
            sections.add(requireText(section, "section"));
            return this;
        }

        public ReportBuilder setFooter(String footer) {
            this.footer = requireText(footer, "footer");
            return this;
        }

        public ReportBuilder addMetadata(String key, String value) {
            metadata.put(
                    requireText(key, "metadata key"),
                    Objects.requireNonNull(value, "metadata value")
            );
            return this;
        }

        public final ReportDocument build() {
            if (title == null) {
                throw new IllegalStateException("title is required");
            }
            if (sections.isEmpty()) {
                throw new IllegalStateException(
                        "at least one section is required"
                );
            }

            ReportDocument product = createProduct(
                    title,
                    sections,
                    footer,
                    metadata
            );
            reset();
            return product;
        }

        protected abstract ReportDocument createProduct(
                String title,
                List<String> sections,
                String footer,
                Map<String, String> metadata
        );

        private static String requireText(String value, String fieldName) {
            Objects.requireNonNull(value, fieldName + " must not be null");
            if (value.isBlank()) {
                throw new IllegalArgumentException(
                        fieldName + " must not be empty"
                );
            }
            return value.strip();
        }
    }

    public static final class MarkdownReportBuilder extends ReportBuilder {
        @Override
        protected ReportDocument createProduct(
                String title,
                List<String> sections,
                String footer,
                Map<String, String> metadata
        ) {
            return new MarkdownReport(title, sections, footer, metadata);
        }
    }

    public static final class PlainTextReportBuilder extends ReportBuilder {
        @Override
        protected ReportDocument createProduct(
                String title,
                List<String> sections,
                String footer,
                Map<String, String> metadata
        ) {
            return new PlainTextReport(title, sections, footer, metadata);
        }
    }

    public static final class ReportDirector {
        private ReportDirector() {
        }

        public static ReportDocument buildStandardReport(
                ReportBuilder builder
        ) {
            return builder
                    .reset()
                    .setTitle("Operational Summary")
                    .addSection(
                            "Production remained within the expected range."
                    )
                    .addSection(
                            "No critical availability events were recorded."
                    )
                    .addMetadata("status", "complete")
                    .setFooter("Generated by the reporting workflow.")
                    .build();
        }
    }

    public static void main(String[] args) {
        ReportDocument markdownReport =
                ReportDirector.buildStandardReport(
                        new MarkdownReportBuilder()
                );
        ReportDocument plainTextReport =
                ReportDirector.buildStandardReport(
                        new PlainTextReportBuilder()
                );

        System.out.println(markdownReport.render());
        System.out.println();
        System.out.println(plainTextReport.render());
    }
}

