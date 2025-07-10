# Resume Generator Project

This project transforms structured resume data (in JSON format) into professional, publication-ready documents across multiple formats. It takes a standardized JSON input containing a person's work history, education, skills, and contact information, then generates polished output documents including:
* Print-ready PDF resumes with proper styling
* Markdown versions for technical portfolios
* Web-friendly HTML profiles
* Plain text for simple sharing

## Project Goal
The system ensures all output formats contain identical content while respecting each format's conventions - whether that's section headers in Markdown, page breaks in PDFs, or semantic HTML tags. Developers can add new output formats by implementing a single interface while maintaining all existing functionality.

Example transformation:
**resume.json or through CLI** → **Alex_Chen_Resume.pdf** + **Alex_Chen_Resume.md** + **Alex_Chen_Resume.html** + **Alex_Chen_Resume.txt**

The project solves the problem of maintaining multiple resume versions manually while ensuring consistency across all formats.

A **clean architecture** implementation for generating professional resumes in multiple formats (PDF, Markdown, HTML, etc.) from a standardized JSON input. The system:

- Provides **consistent, well-formatted outputs** across all formats  
- Enables **easy extension** to new output formats  
- Maintains **separation of concerns** between business logic and presentation  
- Offers **customizable templates** for different industries/roles  

## Project Structure
```plaintext
org.adeniuobesu
├── core/ # Business logic and domain models
│ ├── models/ # Resume data structure (entities)
│ └── services/ # Business rules and operations
│
├── ports/ # Interface definitions
│ ├── InputPort.java # parsing contract
│ └── OutputPort.java # Generation contract
│
├── adapters/
│ ├── input/ # parsing implementations
│ └── output/ # Format generators (PDF, Markdown, etc.)
│
└── main/ # Application configuration
```
## Key Features

1. **Multi-Format Support**
    ```java
        public interface OutputPort<T> {
            void generate(T data);  // Unified interface, implemented by all adapters
        }
    ```

* PDF (via Apache PDFBox)
* Markdown (clean text formatting)
* HTML (web-friendly version)
* CLI (terminal preview)
* ...etc

2. **Standardized Input**
    ```json
        {
        "fullName": "Alexandra Chen",
        "professionalTitle": "Senior Java Engineer",
        "workExperiences": [
            {
            "companyName": "TechNova Inc.",
            "jobTitle": "Lead Developer",
            "startDate": "2020-03",
            "keyAchievements": [
                "Designed scalable microservices"
            ]
            }
        ]
        }
    ```
3. **Entensible Design**
New formats require only:
* Implementing OutputPort
* Adding to OutputAdapterFactory