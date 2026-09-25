# Builder

> Construct complex objects step by step while keeping construction separate from representation.

## Category

Creational Design Pattern

## Overview

The **Builder** pattern separates the construction of a complex object from the final object itself.

Instead of creating an object through one large construction operation, the Builder divides construction into clear steps. The same construction process can produce different representations or configurations.

The main concept is:

> Build a complex object incrementally, validate it at the correct boundary, and expose only the completed result.

## Problem

Complex objects often have:

- Many required and optional attributes.
- Multiple construction steps.
- Different valid configurations.
- Dependencies between attributes.
- Rules that must be checked before completion.
- Several possible representations.

Direct construction can lead to long parameter lists, unclear argument order, partially initialized objects, and repeated construction logic.

This creates several problems:

- Object creation becomes difficult to read.
- Optional values are confused with required values.
- Invalid combinations may be created.
- Construction logic is duplicated.
- Different representations require separate workflows.
- The final object may be exposed before it is complete.

## Intent

The intent of Builder is to:

1. Separate construction steps from the final object.
2. Make complex construction readable and explicit.
3. Support optional attributes without confusing parameter lists.
4. Centralize validation before object creation.
5. Allow the same construction process to create different representations.
6. Prevent incomplete objects from escaping construction.

## Requirements

Builder is appropriate when most of these requirements exist:

1. The object has several construction steps.
2. The object contains multiple optional attributes.
3. Construction order matters.
4. Some attributes depend on other attributes.
5. The object must be validated before it is returned.
6. Multiple representations of the same construction process are required.
7. Construction logic is reused in multiple places.
8. A readable construction flow is important to maintainability.

If the object has only a few simple attributes, direct construction is usually clearer.

## Key Terminology

| Term | Meaning |
|---|---|
| Product | The complex object produced by the construction process. |
| Builder | The contract or component that defines construction steps. |
| Concrete Builder | A builder that implements the construction steps and creates a specific representation. |
| Director | An optional component that defines a reusable construction sequence. |
| Construction Step | One operation that contributes part of the final object. |
| Validation Boundary | The point at which the builder verifies that the object is complete and valid. |
| Representation | The final form of the product created by a builder. |
| Client | The component that selects a builder and requests the completed product. |

## Participants and Responsibilities

### Product

Represents the complex object being created.

### Builder

Defines the operations required to construct the product.

### Concrete Builder

Maintains construction state, performs the construction steps, validates the result, and returns the completed product.

### Director

Defines a standard sequence of construction steps. The Director is optional and should be used only when a construction sequence is reused.

### Client

Selects the builder, supplies construction decisions, and requests the final product.

## General Structure

~~~text
Client
  |
  v
Builder <---------------- Director
  |
  | performs construction steps
  v
Concrete Builder
  |
  | creates
  v
Product
~~~

## Collaboration Flow

1. The client selects a builder.
2. The client provides required values and optional configuration.
3. The builder performs construction steps.
4. The builder stores intermediate construction state.
5. An optional Director executes a standard sequence.
6. The builder validates the accumulated state.
7. The builder creates and returns the Product.
8. The completed Product is used independently from the builder.

## Example Scenario

Consider a reporting system that creates a complex report.

A report may contain:

- A title.
- Multiple sections.
- Metadata.
- Formatting options.
- A footer.
- Output-specific structure.

The construction process can remain the same while different builders produce different representations, such as a printable report, a web report, or a machine-readable report.

The client focuses on what the report should contain. The Concrete Builder decides how that report is represented.

## Use Cases

Builder can be useful for:

- Complex reports and documents.
- Query objects with many optional filters.
- Configuration objects.
- Request payloads.
- Infrastructure definitions.
- Test fixtures.
- Deployment specifications.
- Multi-step workflow definitions.
- Search criteria.
- Dashboard configurations.
- Serialization models.
- Resource provisioning requests.
- Notification messages with optional content.
- Complex domain objects.
- Objects with strict validation rules.

## When to Use Builder

Use Builder when:

- An object requires multiple construction steps.
- The object has many optional properties.
- Construction order or dependencies matter.
- Validation should happen before the object is returned.
- The same construction process produces different representations.
- Construction logic is reused.
- A readable, self-documenting construction flow is valuable.
- Direct construction would require a long or confusing parameter list.

## When Not to Use Builder

Avoid Builder when:

- The object has only a few simple attributes.
- Construction is a single straightforward operation.
- There are no optional values or validation rules.
- A direct constructor clearly communicates the object.
- The builder would only forward every value without adding meaningful construction logic.
- The additional abstraction makes the design harder to understand.

## Bottlenecks and Risks

Builder is rarely a performance bottleneck by itself. Its primary risks are unnecessary abstraction, mutable construction state, and validation complexity.

### 1. Builder Proliferation

A different representation may require a different Concrete Builder. Too many representations can create a large builder hierarchy.

### 2. Mutable Intermediate State

The builder often stores partially constructed state. Reusing the same builder carelessly can leak values from one product into another.

Possible mitigations include:

- Resetting the builder after completion.
- Creating a fresh builder for each product.
- Making construction state private.
- Preventing reuse after completion.

### 3. Incomplete Products

If validation is weak, a builder may return an object missing required information.

Possible mitigations include:

- Validating before product creation.
- Separating required and optional construction steps.
- Making invalid states difficult to represent.
- Testing boundary conditions.

### 4. Hidden Construction Order

Some steps may depend on earlier steps. If the builder allows steps in any order, invalid intermediate states may be possible.

Possible mitigations include:

- Enforcing order through the builder contract.
- Grouping dependent operations.
- Validating dependencies during each step.
- Using a Director for standard sequences.

### 5. Director Rigidity

A Director can make a standard construction sequence convenient, but it may become restrictive when clients need unusual combinations.

Use a Director for reusable workflows, not as a mandatory layer for every product.

### 6. Duplicate Validation

Validation may be repeated in individual setters, the builder, and the Product. This can lead to inconsistent rules.

Define clear ownership:

- Step validation checks local constraints.
- Final validation checks cross-field constraints.
- The Product protects its permanent invariants.

### 7. Expensive Construction

Some construction steps may perform I/O, allocate significant resources, or call external services. A builder can make these steps readable but does not make them inexpensive.

Possible mitigations include:

- Delaying expensive operations.
- Separating planning from execution.
- Caching reusable resources.
- Supporting cancellation and failure cleanup.
- Measuring each construction step.

## Performance Considerations

Builder usually adds a small amount of object and method-call overhead. The meaningful performance considerations are related to the construction process:

- Number of intermediate objects created.
- Memory held during construction.
- Repeated copying of accumulated state.
- Validation cost.
- External calls made during construction.
- Whether the builder can be reused safely.
- Whether the final product can be shared or cached.

A Builder should generally construct an object in memory and leave external side effects to a separate execution step when possible.

## Advantages

- Makes complex construction readable.
- Separates construction from representation.
- Supports optional attributes cleanly.
- Centralizes validation.
- Can create different representations through the same process.
- Reduces constructor complexity.
- Supports reusable construction sequences.
- Makes construction easier to test step by step.

## Disadvantages

- Adds additional components.
- May require a separate builder for each representation.
- Can introduce mutable intermediate state.
- May duplicate validation if responsibilities are unclear.
- Can be excessive for simple objects.
- A Director may restrict unusual construction flows if overused.

## Builder Compared with Related Patterns

| Pattern | Main Idea | Suitable When |
|---|---|---|
| Direct Construction | Creates an object in one operation. | The object is simple and stable. |
| Factory Method | Delegates the choice of product creation. | One product type varies within a workflow. |
| Abstract Factory | Creates a family of related products. | Several compatible products must be created together. |
| Prototype | Creates an object by copying an existing instance. | New objects are based on an existing object. |
| Builder | Constructs one complex product step by step. | Construction has multiple steps, options, or representations. |
| Fluent Interface | Makes chained operations readable. | The primary goal is expressive method chaining. |
| Dependency Injection | Supplies dependencies from outside the object. | A composition layer should control dependencies. |

## Design Principles Involved

### Encapsulate What Varies

Construction steps and representation-specific logic are isolated inside builders.

### Single Responsibility Principle

The Product represents the result, while the Builder owns construction.

### Separation of Concerns

The client describes the desired product, while the builder handles construction details.

### Open/Closed Principle

New representations can often be added through new builders without changing the client workflow.

### Fail Fast

Validation can reject incomplete or invalid configurations before the Product is used.

## Implementation Guidance

When applying Builder:

1. Identify the complex Product.
2. Separate required values from optional values.
3. Identify the construction steps.
4. Identify dependencies between steps.
5. Define the Builder contract.
6. Create Concrete Builders for different representations when needed.
7. Decide whether a Director provides real value.
8. Validate local constraints during construction.
9. Validate cross-field constraints before completion.
10. Prevent incomplete Products from escaping.
11. Define builder reuse and reset behavior.
12. Keep external side effects outside construction when possible.
13. Add tests for valid, invalid, minimal, and maximal configurations.
14. Reconsider the pattern if construction remains simple.

## Testing Strategy

A complete test suite should verify:

- Required values are enforced.
- Optional values receive correct defaults.
- Construction steps behave correctly.
- Dependent steps reject invalid order or configuration.
- Cross-field validation works.
- Minimal valid products can be created.
- Fully configured products can be created.
- Invalid products are rejected before completion.
- Different builders produce valid representations.
- Builder reuse does not leak state.
- Construction failures release temporary resources.
- A Director produces the expected standard sequence.

## Exercises

1. Add a required construction step and update validation.
2. Add optional metadata without changing existing client usage.
3. Add a second representation of the same Product.
4. Add cross-field validation between two construction options.
5. Create a reusable Director sequence.
6. Support both a standard construction flow and a custom flow.
7. Add a reset or one-time-use policy for the builder.
8. Move external side effects out of the construction process.
9. Compare the Builder with a long direct-construction operation.
10. Identify a real system where Builder would be unnecessary overengineering.

## Summary

Builder is useful when a complex Product must be assembled through multiple steps, especially when it has optional values, validation rules, or multiple representations.

The most important separation is between:

- **What the final Product should contain**
- **How the Product is constructed**
- **How the Product is represented**

Use Builder when this separation improves readability, validation, reuse, or flexibility. Avoid it when direct construction is already simple and clear.
