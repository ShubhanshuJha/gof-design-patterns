# Factory Method

> Define an interface for creating an object, but let subclasses or specialized creators decide which concrete object to create.

## Category

Creational Design Pattern

## Overview

The **Factory Method** pattern separates object creation from the code that uses the object.

A creator defines a common workflow and declares a creation operation called the **Factory Method**. Specialized creators implement that operation and decide which concrete product should be returned.

The main concept is:

> The creator knows when an object is needed, but the specialized creator decides which object is created.

## Problem

Suppose an application needs to work with multiple product types. Without Factory Method, the client or business workflow may directly create concrete objects.

This creates several problems:

- The client becomes coupled to concrete products.
- Object-creation logic becomes mixed with business logic.
- Adding a new product requires modifying existing decision-making code.
- Conditional logic grows as product types increase.
- Testing different product variants becomes more difficult.
- The main workflow becomes less reusable.

## Intent

Factory Method aims to:

1. Define a common product contract.
2. Move object creation into a dedicated operation.
3. Allow specialized creators to choose concrete products.
4. Keep the main workflow independent of concrete products.
5. Make new product variants easier to introduce.

## Requirements

Factory Method is suitable when:

1. A common workflow must operate with multiple product variants.
2. Products share a common contract.
3. The exact product type should be decided by a specialized creator.
4. New product types may be added in the future.
5. The client should not depend directly on concrete products.
6. Object creation may change independently from the main workflow.
7. Different creators may provide different product implementations.
8. The design supports extension through inheritance, composition, or delegation.

If the requirement is only a small mapping between a value and an object, a simple factory may be clearer.

## Key Terminology

| Term | Meaning |
|---|---|
| Product | The common contract followed by all created objects. |
| Concrete Product | A specific implementation of the Product contract. |
| Creator | The component that defines the common workflow and declares the Factory Method. |
| Concrete Creator | A specialized creator that implements the Factory Method. |
| Factory Method | The operation responsible for creating and returning a Product. |
| Client | The component that uses the Creator and Product abstractions. |
| Creation Policy | The rule used to select or construct a product. |

## Participants and Responsibilities

### Product

Defines the operations that all concrete products must support.

### Concrete Product

Implements the Product contract and contains product-specific behavior.

### Creator

Contains the stable workflow. It calls the Factory Method when it needs a product without knowing its concrete type.

### Concrete Creator

Implements the Factory Method and decides which Concrete Product to create.

### Client

Uses the Creator and Product abstractions without containing detailed construction logic.

## General Structure

```text
Client
  |
  v
Creator
  |
  | calls
  v
Factory Method
  |
  | returns
  v
Product
  ^
  |
Concrete Product

Concrete Creator implements the Factory Method
and creates a Concrete Product.
