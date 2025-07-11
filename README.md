# Resume Generator (Hexagonal Architecture)

A tribute to Uncle Bob — whose work shaped my thinking as a software engineer.

As a passionate believer in clean code and software craftsmanship, this project is the result of my deep dive into Clean Architecture by Robert C. Martin.
It’s a résumé generator written in Java, structured with Hexagonal Architecture (Ports & Adapters) to ensure clarity, testability, and long-term maintainability.
Currently CLI-based, but easily extendable to REST APIs, databases, or even AI-powered summarization.

This isn’t just a tool — it’s a practical application of architectural principles I now carry with me as a developer.

## ✨ Features

- ✅ CLI-driven resume generation (`resume.json` ➜ `resume.pdf`)
- ✅ Hexagonal architecture: domain logic is fully isolated from I/O concerns
- ✅ Extensible output formats (PDF, HTML, JSON)
- ✅ AI-ready: supports pluggable AI-based summary generation
- ✅ Pure Java (no Spring) – fast startup, minimal dependencies

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
├── core/                  # Domain logic (models, services, exceptions, ports)
│   ├── models/            # Resume, ContactMethod, etc.
│   ├── validation/        # Domain validation logic
│   └── ports/             # Input/Output contracts (interfaces)
├── application/           # Use cases (e.g., BuildResumeUseCase)
├── adapters/              # Implementations for input/output ports
│   ├── input/
│   │   ├── JSON           # Current json input adapter
│   │   ├── cli            # Future CLI input adapter
│   │   └── web            # Future REST adapter
│   └── output/
│       ├── pdf            # PDF generator
│       └── json           # JSON file support
│       └── ...            # As many as you want to implement...
├── infrastructure/        # Composition root (Main.java, config)
└── resources/
    └── samples/           # Example JSON resumes
```

## 🧱 Clean Architecture Principles
* Dependency Rule: Core domain knows nothing about frameworks, filesystems, or libraries.
* Stable Core: Only interfaces inside the core; everything else plugs in.
* Adapters = Replaceable: Swap CLI for Web, JSON for DB, or add AI summaries — no impact on core.
* Testable: Business logic tested with mocked ports — no need to touch infrastructure.

## 🛣️ Roadmap
1. Add RESTful web interface
2. Add database persistence (MySQL, PostgreSQL or MongoDB)
3. Support more input formats (YAML, XML)
4. UI layer (HTML/JS) to generate resumes visually
5. Add HTML output adapter
6. Integrate AI summary (e.g., OpenAI GPT)


## 👨‍💻 Author
Project by [@Moukhafi-Anass](https://github.com/adeniuobesu) — built to demonstrate true Clean Architecture in action.