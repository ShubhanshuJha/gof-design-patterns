# Behavioral Design Patterns

Behavioral design patterns focus on communication, responsibility assignment, and the flow of control between objects. They help make object collaboration flexible while keeping individual components focused on clear responsibilities.

## Learning objectives

By completing this category, you should be able to:

- Design flexible communication between collaborating objects.
- Encapsulate changing algorithms and workflows.
- Model state-dependent behavior without large conditional blocks.
- Understand event notification, request routing, undo operations, and traversal.
- Compare object collaboration techniques in Java and Python.

## Patterns

| # | Pattern | Main idea | Documentation |
|---:|---|---|---|
| 1 | [Strategy](./strategy/README.md) | Encapsulate interchangeable algorithms. | README |
| 2 | [Observer](./observer/README.md) | Notify dependents when an object's state changes. | README |
| 3 | [Command](./command/README.md) | Encapsulate a request as an object. | README |
| 4 | [State](./state/README.md) | Change behavior when internal state changes. | README |
| 5 | [Template Method](./template-method/README.md) | Define an algorithm skeleton with customizable steps. | README |
| 6 | [Chain of Responsibility](./chain-of-responsibility/README.md) | Pass a request through a chain of handlers. | README |
| 7 | [Iterator](./iterator/README.md) | Traverse a collection without exposing its representation. | README |
| 8 | [Mediator](./mediator/README.md) | Centralize communication between objects. | README |
| 9 | [Memento](./memento/README.md) | Save and restore object state. | README |
| 10 | [Interpreter](./interpreter/README.md) | Represent and evaluate a language grammar. | README |
| 11 | [Visitor](./visitor/README.md) | Add operations without changing object structures. | README |

## Recommended learning order

1. Strategy
2. Observer
3. Command
4. State
5. Template Method
6. Chain of Responsibility
7. Iterator
8. Mediator
9. Memento
10. Interpreter
11. Visitor

The first five patterns establish the most commonly used behavioral techniques: interchangeable algorithms, notifications, encapsulated requests, state transitions, and reusable workflows. The remaining patterns cover request pipelines, collection traversal, coordination, snapshots, language interpretation, and operations over object structures.

## Directory structure

Each pattern contains its explanation and separate implementation areas for both languages:

```text
behavioral/
├── README.md
├── chain-of-responsibility/
├── command/
├── interpreter/
├── iterator/
├── mediator/
├── memento/
├── observer/
├── state/
├── strategy/
├── template-method/
└── visitor/
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
- Guidance on when the pattern is useful and when simpler code is preferable.
