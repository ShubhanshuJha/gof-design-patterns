# Structural Design Patterns

Structural design patterns explain how classes and objects can be composed to form larger, flexible structures while keeping responsibilities and dependencies manageable. They are especially useful when integrating existing components, extending behavior, or hiding subsystem complexity.

## Learning objectives

By completing this category, you should be able to:

- Combine objects without creating rigid inheritance hierarchies.
- Adapt incompatible interfaces safely.
- Add behavior without modifying existing classes.
- Build tree structures and simplified subsystem interfaces.
- Understand how Java and Python use composition, delegation, and wrappers.

## Patterns

| # | Pattern | Main idea | Documentation |
|---:|---|---|---|
| 1 | [Adapter](./adapter/README.md) | Make incompatible interfaces work together. | README |
| 2 | [Decorator](./decorator/README.md) | Add behavior dynamically through wrappers. | README |
| 3 | [Facade](./facade/README.md) | Provide a simple interface over a complex subsystem. | README |
| 4 | [Composite](./composite/README.md) | Treat individual objects and object trees uniformly. | README |
| 5 | [Proxy](./proxy/README.md) | Control access to another object. | README |
| 6 | [Bridge](./bridge/README.md) | Separate abstraction from implementation. | README |
| 7 | [Flyweight](./flyweight/README.md) | Share common state to reduce memory usage. | README |

## Recommended learning order

1. Adapter
2. Decorator
3. Facade
4. Composite
5. Proxy
6. Bridge
7. Flyweight

Adapter, Decorator, and Facade are common in everyday application development and provide a strong foundation in composition and delegation. Composite and Proxy build on those ideas, while Bridge and Flyweight introduce more specialized techniques for independent variation and memory optimization.

## Directory structure

Each pattern contains its explanation and separate implementation areas for both languages:

```text
structural/
├── README.md
├── adapter/
├── bridge/
├── composite/
├── decorator/
├── facade/
├── flyweight/
└── proxy/
```

Every pattern directory contains:

```text
pattern-name/
├── README.md
├── java/
└── python/
```

## Implementation standard

Every pattern lesson will use equivalent examples in Java and Python wherever practical. Each implementation will include:

- A small, runnable example.
- Clear participants and collaboration flow.
- Meaningful class and method names.
- A comparison of the Java and Python approaches.
- Tests or execution instructions as the implementation is added.
- Guidance on when the pattern is useful and when simpler composition is preferable.
