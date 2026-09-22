# Creational Design Patterns

Creational design patterns provide flexible and reusable approaches for creating objects. They separate object construction from object usage, reduce unnecessary coupling to concrete classes, and make object creation easier to change or test.

## Learning objectives

By completing this category, you should be able to:

- Choose an appropriate object-creation strategy for a given problem.
- Understand the difference between class-based and object-based creation.
- Compare object construction in Java and Python.
- Recognize when a creation pattern adds value and when a simple constructor or function is better.

## Patterns

| # | Pattern | Main idea | Documentation |
|---:|---|---|---|
| 1 | [Factory Method](./factory-method/README.md) | Delegate creation to subclasses or implementations. | README |
| 2 | [Abstract Factory](./abstract-factory/README.md) | Create families of related objects. | README |
| 3 | [Builder](./builder/README.md) | Construct complex objects step by step. | README |
| 4 | [Prototype](./prototype/README.md) | Create objects by copying existing instances. | README |
| 5 | [Singleton](./singleton/README.md) | Provide controlled access to one shared instance. | README |

## Recommended learning order

1. Factory Method
2. Abstract Factory
3. Builder
4. Prototype
5. Singleton

Factory Method and Abstract Factory establish the core idea of separating creation from usage. Builder and Prototype address alternative construction techniques. Singleton is studied last because it is often overused and requires careful discussion of global state, testing, and concurrency.

## Directory structure

Each pattern contains its explanation and separate implementation areas for both languages:

```text
creational/
├── README.md
├── abstract-factory/
│   ├── README.md
│   ├── java/
│   └── python/
├── builder/
│   ├── README.md
│   ├── java/
│   └── python/
├── factory-method/
│   ├── README.md
│   ├── java/
│   └── python/
├── prototype/
│   ├── README.md
│   ├── java/
│   └── python/
└── singleton/
    ├── README.md
    ├── java/
    └── python/
```

## Implementation standard

Every pattern lesson will use the same example domain in Java and Python wherever practical. Each implementation will include:

- A small, runnable example.
- Meaningful class and method names.
- Comments only where they clarify the pattern.
- A comparison of the Java and Python approaches.
- Tests or execution instructions as the implementation is added.
