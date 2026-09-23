"""Factory Method pattern demonstrated with format-specific record parsers."""

from __future__ import annotations

import csv
import io
import json
from abc import ABC, abstractmethod
from typing import Any


class RecordParser(ABC):
    """Product interface used by the ingestion workflow."""

    @abstractmethod
    def parse(self, payload: str) -> list[dict[str, Any]]:
        """Convert a raw payload into a list of records."""


class CsvRecordParser(RecordParser):
    """Concrete product for comma-separated records."""

    def parse(self, payload: str) -> list[dict[str, Any]]:
        return [dict(row) for row in csv.DictReader(io.StringIO(payload))]


class JsonRecordParser(RecordParser):
    """Concrete product for a JSON array of objects."""

    def parse(self, payload: str) -> list[dict[str, Any]]:
        try:
            parsed: Any = json.loads(payload)
        except json.JSONDecodeError as exc:
            raise ValueError("The payload is not valid JSON") from exc

        if not isinstance(parsed, list) or not all(
            isinstance(record, dict) for record in parsed
        ):
            raise ValueError("JSON input must be an array of objects")

        return [dict(record) for record in parsed]


class IngestionJob(ABC):
    """Creator containing the stable ingestion workflow.

    create_parser is the Factory Method. The workflow does not know
    which concrete parser it receives.
    """

    def run(self, payload: str) -> list[dict[str, Any]]:
        parser = self.create_parser()
        records = parser.parse(payload)
        self._validate_records(records)
        return records

    @abstractmethod
    def create_parser(self) -> RecordParser:
        """Create the parser required by this ingestion job."""

    @staticmethod
    def _validate_records(records: list[dict[str, Any]]) -> None:
        if any(not record for record in records):
            raise ValueError("Records must not be empty")


class CsvIngestionJob(IngestionJob):
    """Concrete creator that creates a CSV parser."""

    def create_parser(self) -> RecordParser:
        return CsvRecordParser()


class JsonIngestionJob(IngestionJob):
    """Concrete creator that creates a JSON parser."""

    def create_parser(self) -> RecordParser:
        return JsonRecordParser()


def ingest(job: IngestionJob, payload: str) -> list[dict[str, Any]]:
    """Client function independent of concrete parser classes."""

    return job.run(payload)


if __name__ == "__main__":
    csv_payload = "asset_id,power_kw\nINV-01,42.5\nINV-02,38.1\n"
    json_payload = '[{"asset_id": "INV-01", "power_kw": 42.5}]'

    for job, payload in (
        (CsvIngestionJob(), csv_payload),
        (JsonIngestionJob(), json_payload),
    ):
        print(f"{job.__class__.__name__}: {ingest(job, payload)}")
