# Resume Generator (Hexagonal Architecture, Clean and Tested)

> "A tribute to Uncle Bob — whose work shaped my thinking as a software engineer."

As a passionate believer in clean code and software craftsmanship, this project is the result of my deep dive into Clean Architecture by Robert C. Martin.
It’s a résumé generator written in Java, structured with Hexagonal Architecture (Ports & Adapters) to ensure clarity, testability, and long-term maintainability.
Currently CLI-based, but easily extendable to REST APIs, databases, or even AI-powered summarization.

This isn’t just a tool — it’s a practical application of architectural principles I now carry with me as a developer.

## ✨ Features

- ✅ **CLI-driven résumé generation** (`resume.text` ➜ `resume.pdf`...)
- ✅ **Hexagonal architecture** with strict boundaries
- ✅ **DTO mapping** between layers (core ↔ application)
- ✅ **ArchUnit tests** enforce clean architecture rules
- ✅ **Pluggable I/O strategies** via input/output ports
- ✅ **Extensible output formats** (PDF, HTML, Markdown, Text)
- ✅ **AI-ready**: future support for LLM-based summarization
- ✅ **Minimal dependencies** – no Spring Boot, pure Java

---

## 🚀 Getting Started

### 📦 Prerequisites

- Java 17+
- Maven 3+

### 🔧 Build

```bash
mvn clean package
```

### ▶️ Run CLI Mode
```bash
java -jar resume-generator-1.0.0.jar --json resume.json --output-dir ~/docs --format TEXT
```

### 🧪 Run Tests
```bash
mvn test
```

## 📁 Project Structure (Hexagonal)

```bash
src/
├── core/
│   ├── models/              # Resume, ContactMethod, etc.
│   ├── validation/          # Domain validators
│   └── exceptions/          # Domain-level exceptions
│
├── application/
│   ├── usecases/            # BuildResumeUseCase, etc.
│   ├── ports/               # Input/OutputStrategy<T>
│   ├── dtos/                # ResumeDto, etc.
│   └── mappers/             # ResumeMapper: DTO ↔ Domain
│
├── adapters/
│   ├── input/               # JsonInputStrategy, CliInputStrategy
│   └── output/              # PdfExporter, HtmlExporter, etc.
│
├── infrastructure/
│   ├── factories/           # Strategy factories
│   └── Main.java            # Entry point and composition root
│
└── resources/
    └── samples/             # Example JSON input files
```

## 🧱 Clean Architecture Principles
* Dependency Rule: Core domain knows nothing about frameworks, filesystems, or libraries.
* Stable Core: Only interfaces inside the core; everything else plugs in.
* Adapters = Replaceable: Swap CLI for Web, JSON for DB, or add AI summaries — no impact on core.
* Testable: Business logic tested with mocked ports — no need to touch infrastructure.

| Layer           | Knows About                           | Never Knows About             |
|-----------------|---------------------------------------|-------------------------------|
| Core            | Domain models, validation rules       | Frameworks, I/O, UI, adapters |
| Application     | Use cases, DTOs, ports                | Infrastructure, concrete I/O  |
| Adapters        | Application ports, DTOs, external I/O | Core internals, use cases     |
| Infrastructure  | Composition root, factories, config   | Business rules, domain models |

### Layered dependencies (from inner to outer)

```yaml
[ Core Domain ]
  ↳ Models: Resume, Experience, Education...
  ↳ Rules: Validation, Exceptions
        |
[ Application Layer ]
  ↳ Use cases: BuildResumeUseCase
  ↳ Ports: InputStrategy<T>, OutputStrategy<T>
  ↳ Mappers: DTO ↔ Domain
        |
[ Adapters (I/O) ]
  ↳ Input: JSON, CLI
  ↳ Output: PDF, HTML, Markdown, Text
        |
[ Infrastructure ]
  ↳ Factories, Configuration, Composition root (Main.java)
```

### Dependency Rule
> Inner layers know nothing about outer layers.
* core knows nothing about application or adapters.
* application defines ports that adapters implement.
* adapters are plugin modules around the clean core.

## 🔄 Extending the system
Want to add a REST API? AI summaries? YAML input?
Just add new adapters — no need to touch the business rules.
### Plug In a New Input Format:
* Implement InputStrategy
* Register it via the InputStrategyFactory
### Add a New Output Format:
* Implement OutputStrategy
* Register in OutputStrategyFactory
### Introduce a New Use Case:
* Add it to the application.usecases package
* It’ll interact with ports and domain models

## 🛣️ Roadmap
1. ✅ Clean Architecture + CLI support
2. ✅ HTML output adapter
3. ⏳ RESTful web interface (Spring Boot or Micronaut, optional)
4. ⏳ Persistent storage (MySQL / PostgreSQL / MongoDB)
5. ⏳ Visual UI: HTML + JS Resume Builder
6. ⏳ AI summary integration (OpenAI GPT, Claude, etc.)
7. ⏳ More input formats: YAML, XML

## Why this matters
We don’t write software to please the compiler — we write it for humans to read and maintain.
This project is an exercise in clarity:
* No service soup.
* No dependency injection frameworks.
* No leaky abstractions.
Just `pure intent`, `good design`, and `testable code`.

## 👨‍💻 Author
Project by [@Moukhafi-Anass](https://github.com/adeniuobesu)

> "This project is my homage to Clean Architecture and the timeless lessons of Robert C. Martin." — Moukhafi Anass

> "The only way to go fast is to go well." — Uncle Bob (Robert C. Martin)