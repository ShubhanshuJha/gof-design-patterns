# Factory Method

> Define an interface for creating an object, but let subclasses decide which concrete object to create.

## Overview

The **Factory Method** is a creational design pattern. It moves product creation from a general workflow into a specialized creator.

The creator owns a stable algorithm, while the Factory Method represents the step that varies. Each concrete creator overrides that method and returns the appropriate concrete product.

This lesson uses a data-ingestion example:

- The workflow is an `IngestionJob`.
- The product is a `RecordParser`.
- Concrete products are CSV and JSON parsers.
- Concrete creators choose which parser the workflow uses.

## Terminology

| Term | Meaning |
|---|---|
| Product | The common interface expected by the creator; here, `RecordParser`. |
| Concrete Product | A specific product implementation; here, `CsvRecordParser` or `JsonRecordParser`. |
| Creator | The class containing the stable workflow and declaring the factory method; here, `IngestionJob`. |
| Concrete Creator | A subclass that overrides the factory method; here, `CsvIngestionJob` or `JsonIngestionJob`. |
| Factory Method | The creation method; here, `create_parser()`. |
| Client | Code that uses the creator without directly constructing a concrete parser. |

## Problem

An ingestion workflow may support CSV, JSON, XML, or vendor-specific formats. A naive client often owns both orchestration and object creation:

~~~python
if file_format == "csv":
    parser = CsvRecordParser()
elif file_format == "json":
    parser = JsonRecordParser()
else:
    raise ValueError("Unsupported format")

records = parser.parse(payload)
~~~

This creates several problems:

- The client is coupled to every concrete parser.
- Every new format changes the client.
- Creation logic becomes mixed with business workflow.
- Large conditional blocks become difficult to test.
- The stable ingestion process is harder to reuse.

## Requirements

Factory Method is a good fit when most of these requirements exist:

1. There is a stable workflow shared by multiple variants.
2. One workflow step creates a product with multiple implementations.
3. All products follow a common interface or protocol.
4. New product types are likely to be added.
5. The client should not depend on concrete product classes.
6. Subclassing or another creator-extension mechanism is acceptable.

If the requirement is only a small mapping from a string to a class, a simple factory function or dictionary is usually clearer.

## Solution

The creator defines a stable workflow and calls a factory method:

~~~text
Client
  |
  v
Concrete Creator ---- creates ----> Concrete Product
  |
  v
Creator workflow ---- uses ----> Product interface
~~~

The workflow calls `create_parser()` without knowing whether it receives a CSV parser, JSON parser, or a future parser.

## Participants and responsibilities

### Creator: `IngestionJob`

- Defines the common ingestion workflow.
- Declares the Factory Method.
- Uses the product interface rather than a concrete parser.

### Concrete Creators

`CsvIngestionJob` and `JsonIngestionJob` select the concrete parser while reusing the common workflow.

### Product: `RecordParser`

Defines the operation required by the creator: `parse()`.

### Concrete Products

`CsvRecordParser` and `JsonRecordParser` implement format-specific parsing.

## Python implementation

The implementation uses:

- `abc.ABC` and `@abstractmethod` for explicit contracts.
- Standard-library `csv` and `json` modules.
- Type hints.
- No third-party dependencies.

Files:

- [`factory_method.py`](./python/factory_method.py) — runnable implementation.
- [`test_factory_method.py`](./python/test_factory_method.py) — unit tests using `unittest`.

Run from the repository root:

~~~bash
python creational/factory-method/python/factory_method.py
python -m unittest discover -s creational/factory-method/python -p "test_*.py" -v
~~~

## Use cases

- Data parsers for CSV, JSON, XML, or vendor-specific formats.
- Database connectors for PostgreSQL, MySQL, or SQLite.
- Cloud-storage clients for Amazon S3, Azure Blob Storage, or Google Cloud Storage.
- Exporters for CSV, JSON, Parquet, or Excel.
- Notification providers such as email, SMS, or Slack.
- Payment gateway integrations.
- Platform-specific UI components.
- Environment-specific logging or event handlers.
- Report generators for PDF, HTML, or Markdown.

## Bottlenecks and risks

The Factory Method call itself is rarely a performance bottleneck. The important risks are usually design and operational complexity.

### Class proliferation

Each product variant may require a concrete product and concrete creator. Too many variants can create many small classes.

### Unnecessary inheritance

The classic pattern uses subclassing. In Python, a factory function, registry, or dependency injection may be simpler when the workflow does not vary by creator.

### Product-contract drift

If concrete products do not honor the same interface, failures can appear at runtime. Abstract base classes, protocols, and tests reduce this risk.

### Expensive object creation

Parser construction is cheap in this example. In production, creation might load schemas, open network connections, or initialize clients. Use caching, connection pooling, or explicit lifecycle management when appropriate.

### Configuration failures

If a creator is selected from configuration, invalid values may fail at runtime. Validate supported creator types during application startup.

### Debugging indirection

The final product may be created several calls away from the client. Use clear names, creation-boundary logging, and tests for each concrete creator.

## Advantages

- Encapsulates object creation.
- Reduces client coupling to concrete products.
- Keeps the common workflow stable.
- Supports extension through new creators and products.
- Makes product creation easier to test and replace.

## Disadvantages

- Adds abstraction and indirection.
- Can increase the number of classes.
- Often requires inheritance.
- May be excessive for a small, stable set of product types.

## Factory Method vs. related approaches

| Approach | Best suited for |
|---|---|
| Direct constructor | One stable concrete implementation. |
| Simple factory function | A small creation decision with no varying creator workflow. |
| Factory Method | A stable workflow with a creation step customized by creators. |
| Abstract Factory | Creating families of related products. |
| Dependency injection | Supplying an already-created dependency from outside the class. |

## Python-specific guidance

Python's functions, first-class classes, duck typing, and dependency injection can make a full class hierarchy unnecessary. Use the classic pattern when the creator workflow is meaningful and subclass-specific creation is part of the design.

Prefer a simpler factory function when:

- Product selection is only a small mapping.
- There is no creator workflow to customize.
- The number of variants is small and unlikely to grow.

Use an abstract base class or `typing.Protocol` when a clear product contract matters. Do not use unrestricted reflection or `eval()` to construct classes from untrusted configuration.

## Exercises

1. Add `TsvRecordParser` and `TsvIngestionJob` without changing `IngestionJob`.
2. Add a parser for newline-delimited JSON.
3. Replace the abstract base class with a `typing.Protocol`.
4. Add parser metrics such as record count and parse duration.
5. Build a registry-based alternative and compare it with Factory Method.

## Java implementation

The Java implementation will be added in [`java/`](./java/) after the Python version is understood.

