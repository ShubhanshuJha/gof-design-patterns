"""Builder pattern demonstrated with multiple report representations."""

from __future__ import annotations

from abc import ABC, abstractmethod
from dataclasses import dataclass
from typing import Mapping


class ReportDocument(ABC):
    """Product contract for completed report representations."""

    @abstractmethod
    def render(self) -> str:
        """Return the completed report as text."""


@dataclass(frozen=True)
class MarkdownReport(ReportDocument):
    title: str
    sections: tuple[str, ...]
    footer: str | None
    metadata: Mapping[str, str]

    def render(self) -> str:
        lines = [f"# {self.title}", ""]
        for section in self.sections:
            lines.extend([section, ""])

        if self.metadata:
            lines.extend(["## Metadata", ""])
            lines.extend(
                f"- {key}: {value}" for key, value in self.metadata.items()
            )
            lines.append("")

        if self.footer:
            lines.extend([self.footer, ""])

        return "\n".join(lines).rstrip()


@dataclass(frozen=True)
class PlainTextReport(ReportDocument):
    title: str
    sections: tuple[str, ...]
    footer: str | None
    metadata: Mapping[str, str]

    def render(self) -> str:
        lines = [self.title, "=" * len(self.title), ""]
        for section in self.sections:
            lines.extend([section, ""])

        if self.metadata:
            lines.append("Metadata:")
            lines.extend(
                f"{key}: {value}" for key, value in self.metadata.items()
            )
            lines.append("")

        if self.footer:
            lines.extend([self.footer, ""])

        return "\n".join(lines).rstrip()


class ReportBuilder(ABC):
    """Builder contract for constructing report documents."""

    @abstractmethod
    def reset(self) -> ReportBuilder:
        """Reset construction state."""

    @abstractmethod
    def set_title(self, title: str) -> ReportBuilder:
        """Set the required report title."""

    @abstractmethod
    def add_section(self, section: str) -> ReportBuilder:
        """Add a report section."""

    @abstractmethod
    def set_footer(self, footer: str) -> ReportBuilder:
        """Set an optional footer."""

    @abstractmethod
    def add_metadata(self, key: str, value: str) -> ReportBuilder:
        """Add report metadata."""

    @abstractmethod
    def build(self) -> ReportDocument:
        """Validate and return the completed product."""


class _BaseReportBuilder(ReportBuilder):
    """Shared construction state and validation."""

    def __init__(self) -> None:
        self.reset()

    def reset(self) -> ReportBuilder:
        self._title: str | None = None
        self._sections: list[str] = []
        self._footer: str | None = None
        self._metadata: dict[str, str] = {}
        return self

    def set_title(self, title: str) -> ReportBuilder:
        if not title.strip():
            raise ValueError("title must not be empty")
        self._title = title.strip()
        return self

    def add_section(self, section: str) -> ReportBuilder:
        if not section.strip():
            raise ValueError("section must not be empty")
        self._sections.append(section.strip())
        return self

    def set_footer(self, footer: str) -> ReportBuilder:
        if not footer.strip():
            raise ValueError("footer must not be empty")
        self._footer = footer.strip()
        return self

    def add_metadata(self, key: str, value: str) -> ReportBuilder:
        if not key.strip():
            raise ValueError("metadata key must not be empty")
        self._metadata[key.strip()] = value.strip()
        return self

    def _construction_state(self) -> tuple[
        str, tuple[str, ...], str | None, dict[str, str]
    ]:
        if self._title is None:
            raise ValueError("title is required")
        if not self._sections:
            raise ValueError("at least one section is required")

        return (
            self._title,
            tuple(self._sections),
            self._footer,
            dict(self._metadata),
        )


class MarkdownReportBuilder(_BaseReportBuilder):
    """Concrete Builder for Markdown reports."""

    def build(self) -> ReportDocument:
        title, sections, footer, metadata = self._construction_state()
        product = MarkdownReport(title, sections, footer, metadata)
        self.reset()
        return product


class PlainTextReportBuilder(_BaseReportBuilder):
    """Concrete Builder for plain-text reports."""

    def build(self) -> ReportDocument:
        title, sections, footer, metadata = self._construction_state()
        product = PlainTextReport(title, sections, footer, metadata)
        self.reset()
        return product


class ReportDirector:
    """Optional Director for a reusable standard construction sequence."""

    @staticmethod
    def build_standard_report(builder: ReportBuilder) -> ReportDocument:
        return (
            builder.reset()
            .set_title("Operational Summary")
            .add_section("Production remained within the expected range.")
            .add_section("No critical availability events were recorded.")
            .add_metadata("status", "complete")
            .set_footer("Generated by the reporting workflow.")
            .build()
        )


if __name__ == "__main__":
    markdown_report = ReportDirector.build_standard_report(
        MarkdownReportBuilder()
    )
    plain_text_report = ReportDirector.build_standard_report(
        PlainTextReportBuilder()
    )

    print(markdown_report.render())
    print()
    print(plain_text_report.render())

