# Abstract Factory

> Create families of related objects without specifying their concrete classes.

## Category

Creational Design Pattern

## Overview

The **Abstract Factory** pattern provides an interface for creating a family of related objects.

The client works with the abstract factory and abstract products. It does not directly create concrete objects and does not need to know which concrete family is being used.

The main concept is:

> Select one factory, and receive a consistent family of compatible products from it.

For example, a selected visual theme may provide a matching button, checkbox, menu, and dialog. The client uses the products through their common contracts while the selected factory guarantees that the products belong to the same family.

## Problem

Consider an application that supports multiple product families. Each family contains several related products.

Without Abstract Factory, the client may create products independently:

- Select a button from one family.
- Select a checkbox from another family.
- Select a menu from a third family.

This can create inconsistent combinations and tightly couples the client to concrete products.

Common problems include:

- The client depends on many concrete product types.
- Related products can be mixed accidentally.
- Product-creation logic is scattered across the application.
- Adding a new product family requires changes in many places.
- Compatibility rules are difficult to enforce.
- Testing every valid product combination becomes harder.

## Intent

The intent of Abstract Factory is to:

1. Define a common factory contract.
2. Define contracts for each product type in a related family.
3. Create complete product families consistently.
4. Hide concrete product classes from the client.
5. Allow the entire product family to be changed by selecting another factory.

## Requirements

Abstract Factory is a good fit when most of the following requirements exist:

1. The system creates multiple types of related products.
2. Products are intended to work together as a family.
3. The client should remain independent of concrete product classes.
4. Product families may vary by environment, platform, theme, vendor, or configuration.
5. Mixing products from different families should be prevented.
6. All product families follow the same set of product contracts.
7. A complete family should be selected as one unit.
8. New families may be added without changing the client workflow.

If there is only one product type, Factory Method may be more appropriate.

## Key Terminology

| Term | Meaning |
|---|---|
| Abstract Factory | The contract for creating a family of related products. |
| Concrete Factory | A factory that creates products belonging to one specific family. |
| Abstract Product | The contract for one type of product. |
| Concrete Product | A product belonging to a specific family and implementing an Abstract Product contract. |
| Product Family | A set of related products designed to work together. |
| Client | The component that uses factories and products through their abstract contracts. |
| Compatibility | The guarantee that products created by one factory are designed to cooperate. |

## Participants and Responsibilities

### Abstract Factory

Declares creation operations for every product type in the family.

### Concrete Factory

Implements the Abstract Factory contract and creates products from one consistent family.

### Abstract Products

Define the contracts for the different product types.

### Concrete Products

Implement the Abstract Product contracts and belong to a particular product family.

### Client

Uses only the abstract factory and abstract product contracts. It should not contain concrete product-selection logic.

## General Structure

~~~text
                         Client
                           |
                           v
                    Abstract Factory
                    /               \
                   v                 v
           Abstract Product A   Abstract Product B
                   ^                 ^
                   |                 |
          Concrete Product A  Concrete Product B

Concrete Factory creates one compatible family
of all concrete products.
~~~

## Collaboration Flow

1. The client selects a Concrete Factory.
2. The client asks the factory for each required product type.
3. The Concrete Factory creates products from the same family.
4. The client uses all products through their abstract contracts.
5. The client remains unaware of the concrete product classes.
6. A different Concrete Factory can replace the complete product family.

## Example Scenario

Consider a user-interface system that supports multiple visual themes.

Each theme provides:

- A button.
- A checkbox.
- A menu.
- A dialog.

A theme factory creates all components belonging to one theme. The client requests a button and checkbox from the same factory, ensuring that they are visually and behaviorally compatible.

The client can switch the complete theme by replacing the factory rather than changing every product-creation operation.

## Use Cases

Abstract Factory can be useful for:

- User-interface component families.
- Platform-specific components.
- Database drivers and related database objects.
- Cloud-provider service families.
- Storage, queue, and notification clients for one provider.
- Document-generation components for different output families.
- Operating-system-specific integrations.
- Vendor-specific hardware components.
- Regional or regulatory product configurations.
- Test doubles and production implementations.
- Multiple versions of an external service contract.
- Multi-tenant products with tenant-specific behavior.

## When to Use Abstract Factory

Use Abstract Factory when:

- Multiple related products must be created together.
- Products must remain mutually compatible.
- The client should switch product families as one unit.
- Concrete product classes should be hidden from the client.
- Product families vary by environment, platform, vendor, or configuration.
- New product families are expected to be added.
- Compatibility rules should be enforced centrally.

## When Not to Use Abstract Factory

Avoid the pattern when:

- There is only one product type.
- Products are unrelated.
- Product compatibility is not important.
- Product families are unlikely to change.
- A simple factory can solve the problem clearly.
- The abstraction would introduce many empty or rarely used creation operations.
- New product types are added frequently while product families remain fixed.

## Bottlenecks and Risks

The factory call itself is rarely a performance bottleneck. The main risks are structural complexity and the cost of maintaining product families.

### 1. Interface Growth

Adding a new product type requires changes to:

- The Abstract Factory contract.
- Every Concrete Factory.
- Every product family.
- Potentially the client workflow.

This is the most important structural limitation of Abstract Factory.

### 2. Product-Family Explosion

Many combinations of products and families can lead to numerous concrete classes and creation paths.

### 3. Incomplete Families

A Concrete Factory may create some products correctly while leaving other creation operations unsupported or inconsistent.

### 4. Accidental Product Mixing

If products can be created outside the factory, the client may combine products from different families.

Possible mitigations include:

- Restricting direct construction.
- Passing the factory through the application boundary.
- Validating family identity.
- Adding compatibility tests.

### 5. Expensive Product Creation

Some products may allocate resources, open connections, load metadata, or contact external systems.

Possible mitigations include:

- Lazy creation.
- Safe reuse.
- Resource pooling.
- Explicit lifecycle management.
- Separating creation from activation.

### 6. Difficult Configuration

Selecting a family through configuration can make behavior difficult to trace.

Possible mitigations include:

- Validating the selected family at startup.
- Logging the active factory.
- Documenting supported families.
- Testing every family as a complete unit.

### 7. Over-Abstraction

If product families are small or stable, Abstract Factory may add unnecessary indirection and maintenance effort.

## Performance Considerations

Abstract Factory generally adds minimal dispatch overhead. The significant performance costs usually come from the products themselves.

Important considerations include:

- Whether products are created once or repeatedly.
- Whether products hold external resources.
- Whether products are thread-safe or shareable.
- Whether construction performs I/O.
- Whether products can be cached safely.
- Whether switching families requires rebuilding expensive resources.

Optimize product lifecycle and resource usage before optimizing factory selection.

## Advantages

- Creates compatible product families.
- Hides concrete product classes from the client.
- Allows the complete family to be changed centrally.
- Encapsulates family-specific creation rules.
- Reduces accidental mixing of incompatible products.
- Supports environment, platform, vendor, and configuration variation.
- Makes family-level testing easier.

## Disadvantages

- Adds multiple layers of abstraction.
- Can require many concrete factories and products.
- Adding a new product type may affect every factory.
- Product-family relationships can be difficult to understand initially.
- May be excessive for a small number of unrelated products.
- Can make simple creation logic appear more complex than necessary.

## Abstract Factory Compared with Related Patterns

| Pattern | Main Idea | Suitable When |
|---|---|---|
| Factory Method | Creates one product through a specialized creation operation. | One product type varies within a workflow. |
| Abstract Factory | Creates a family of related products. | Several compatible products must be created together. |
| Simple Factory | Centralizes a small product-selection decision. | Creation logic is simple and limited. |
| Builder | Constructs one complex product step by step. | A product has many construction steps or options. |
| Prototype | Creates products by copying existing instances. | New products should be based on existing objects. |
| Dependency Injection | Supplies products from outside the client. | A composition layer should control product selection. |

## Design Principles Involved

### Encapsulate What Varies

Product-family selection and family-specific creation are isolated inside Concrete Factories.

### Program to an Abstraction

The client depends on Abstract Factory and Abstract Product contracts.

### Open/Closed Principle

New product families can often be added without changing the client.

### Dependency Inversion Principle

High-level client logic depends on product abstractions rather than concrete implementations.

### Single Responsibility Principle

The client uses products, while factories own family-specific creation responsibilities.

## Implementation Guidance

When applying Abstract Factory:

1. Identify the related product types.
2. Identify which products must remain compatible.
3. Define an Abstract Product contract for each product type.
4. Define the Abstract Factory contract.
5. Create one Concrete Factory per product family.
6. Ensure each Concrete Factory creates a complete compatible family.
7. Keep the client dependent only on abstractions.
8. Prevent uncontrolled direct creation of family members.
9. Add compatibility tests for every family.
10. Validate family selection before product creation.
11. Review the design when adding a new product type.
12. Use a simpler pattern if family-level consistency is not required.

## Testing Strategy

A complete test suite should verify:

- Every Concrete Factory creates all required product types.
- Products from the same factory are compatible.
- Products from different families are not accidentally mixed.
- The client works with every supported factory.
- Invalid family selection is rejected clearly.
- Product-specific behavior is correct.
- Expensive resources are created and released correctly.
- Adding a new family does not break existing families.
- Adding a new product type is evaluated for impact across all factories.

## Exercises

1. Add another product family without changing the client workflow.
2. Add a new product type and identify every affected abstraction.
3. Add a compatibility rule between two product types.
4. Prevent products from different families from being combined.
5. Add a factory-selection mechanism based on configuration.
6. Compare the design with multiple independent Factory Methods.
7. Replace the factory with dependency injection and compare the trade-offs.
8. Design tests that verify every product family as a complete unit.
9. Add lifecycle management for products that own external resources.
10. Identify a real system where Abstract Factory would be unnecessary overengineering.

## Summary

Abstract Factory is useful when a system must create multiple related products that belong to a compatible family.

The most important idea is the separation between:

- **Which product family is selected**
- **How each product in that family is created**
- **How the client uses the products**

Use Abstract Factory when family-level consistency provides real value. Avoid it when products are unrelated or when a simpler creation mechanism communicates the design more clearly.
