"""Tests for the Factory Method example."""

import unittest

from factory_method import (
    CsvIngestionJob,
    CsvRecordParser,
    JsonIngestionJob,
    JsonRecordParser,
    ingest,
)


class FactoryMethodTests(unittest.TestCase):
    def test_csv_creator_produces_csv_parser(self) -> None:
        self.assertIsInstance(CsvIngestionJob().create_parser(), CsvRecordParser)

    def test_json_creator_produces_json_parser(self) -> None:
        self.assertIsInstance(JsonIngestionJob().create_parser(), JsonRecordParser)

    def test_csv_job_runs_the_common_workflow(self) -> None:
        payload = "asset_id,power_kw\nINV-01,42.5\n"

        self.assertEqual(
            ingest(CsvIngestionJob(), payload),
            [{"asset_id": "INV-01", "power_kw": "42.5"}],
        )

    def test_json_job_runs_the_common_workflow(self) -> None:
        payload = '[{"asset_id": "INV-01", "power_kw": 42.5}]'

        self.assertEqual(
            ingest(JsonIngestionJob(), payload),
            [{"asset_id": "INV-01", "power_kw": 42.5}],
        )

    def test_json_parser_rejects_non_array_input(self) -> None:
        with self.assertRaisesRegex(ValueError, "array of objects"):
            JsonRecordParser().parse('{"asset_id": "INV-01"}')


if __name__ == "__main__":
    unittest.main()
