# java-backend-mastery
# Java Backend Mastery 👨‍💻

A hands-on teaching repository for aspiring and intermediate Java,elopers who want to  write code the way it's actually written in production.

---

## Who is this for?

This repository is for,elopers who:

- Know basic Java but want to level up to **senior-level thinking**
- Are preparing for **backend or full-stack Java interviews**
- Want to see SOLID principles, design patterns, and system design concepts explained  through **real, runnable code** - not just theory
- Are tired of tutorials that don't show you how things connect in a real codebase

---

## About the Author

Hi, I am **Junaid Mohd Qamar** - a Java backend and full-stack developer with **8+ years of industry experience** across financial services, enterprise platforms, and cloud-native systems.

I have worked on:
- **Billing systems at J.P. Morgan** processing large-scale APAC transactions using Kafka, 
  Kubernetes, and AWS
- **HR platforms at Jio Platforms** built on Azure and MongoDB
- **Trade reconciliation systems** using Java, Spring Boot, and microservices
- **Research infrastructure** at WEHI using cloud and React

I have created this repository because I believe the best way to truly master something is to teach it. Every concept here is something I have applied in production and I am breaking it 
down the way I wish someone had explained it to me early in my career.

---

## What you will learn

This is not a crash course. This is a **structured, phase-by-phase curriculum** that builds 
on itself - the same way real systems are built.

| Phase | Topic | Key Technologies | Status |
|-------|-------|-----------------|--------|
| 1 | Java Core & OOP | Java 17, JUnit 5, Mockito, AssertJ | Complete |
| 2 | Spring Boot & REST APIs | Spring Boot 3, JWT, Swagger, TestContainers | In Progress |
| 3 | SQL, JPA & Hibernate | PostgreSQL, Hibernate, Flyway, Spring Data JPA | Upcoming |
| 4 | Messaging & Kafka | Kafka, Avro, Schema Registry, Docker | Upcoming |
| 5 | Design Patterns & Architecture | Spring Cloud, Redis, Resilience4j | Upcoming |
| 6 | Docker & Kubernetes | Docker, Kubernetes, Helm, Prometheus | Upcoming |
| 7 | CI/CD & DevOps | GitHub Actions, SonarQube, Bash, AWS/Azure | Upcoming |
| 8 | Protocols & Advanced Topics | gRPC, WebSocket, OAuth2, System Design | Upcoming |

---

## How I teach here

Most tutorials show you **what** to write. I focus on **why**, because that's what separates a junior developer from a senior one.

Every phase in this repo follows the same structure:
phaseN-topic/
├── README.md        - concept explanation, interview Q&A, and teaching notes
├── pom.xml          - Maven dependencies
└── src/
├── main/java/   - clean, commented production-style source code
└── test/java/   - full JUnit 5 test suite with explanations in comments

The comments in the code are part of the lesson. Read them. They explain not just  what the code does, but **why it's written that way** and what would go wrong if you did it differently.

---

## Start here - Phase 1

**[ Phase 1 - Java Core & OOP](./phase1-java-core/README.md)**

We start with the foundation that every other phase builds on - Java 17 and the SOLID principles.

```bash
git clone https://github.com/jqama/java-backend-mastery.git
cd phase1-java-core
mvn test
```

If all tests go green, you're set up correctly and ready to read the code.

---

## The philosophy behind this repo

After 8+ years in the industry I have interviewed dozens of developers and reviewed  thousands of lines of code. Here is what I have consistently seen separate good developers  from great ones:

**Great developers don't just write code that works - they write code that communicates.**

Every class name, every interface, every test in this repository is written with that  principle in mind. By the time you work through all 8 phases you won't just know Java - you'll think in systems.

---

## Full tech stack covered

- **Language:** Java 17+
- **Build:** Maven
- **Testing:** JUnit 5, Mockito, AssertJ, TestContainers
- **Frameworks:** Spring Boot 3, Spring Data JPA, Spring Security, Spring Kafka
- **Databases:** PostgreSQL, Redis, MongoDB
- **Messaging:** Apache Kafka, Avro
- **Infrastructure:** Docker, Kubernetes, Helm
- **Cloud:** AWS (ECS, EKS, Lambda, S3), Azure (AKS, Service Bus)
- **CI/CD:** GitHub Actions, SonarQube, JaCoCo

---

## Questions, feedback, or stuck on something?

If you are working through this repo and get stuck, open a **GitHub Issue** describing where you are and what's confusing. I read every one.

If this repository helped you land a job or level up your skills - I would genuinely love to hear about it.

- **LinkedIn:** https://www.linkedin.com/in/junaid-qamar0610/
- **Email:** junaidqamar2013@gmail.com

---

**"The best engineers I have worked with weren't the ones who knew the most, they were the ones who could explain it the clearest."**


## Git & Contribution Guidelines

This repository follows real-world Git practices used in professional development teams. Please read this before contributing.
# Branching Strategy
- main → Stable, production-ready code
- develop → Active development branch
- Feature branches → Created from develop

# Naming Convention
feature/<short-description> e.g. feature/add-user-service
fix/<short-description> e.g. fix/null-pointer-login
refactor/<short-description> e.g refactor/simplify-payment-flow
docs/<short-description> e.g. docs/pdf-generation-design

# Commit Message Convention 
We follow a structured commit format:
    <type>: <short description>

| Type | When to use |
| feat | Adding a new feature or functionality |
| fix  | Fixing a bug |
| test | Adding or updating tests only |
| refactor | Restructuring code without changing behavior |
| docs | README, comments, or documentation changes |
| chore | Build config, dependencies, tooling (e.g., pom.xml) |
| style | Formatting, whitespace (no logic changes) |
Example:
    - feat: add user authentication service
    - fix: resolve null pointer exception in login
    - refactor: simplify order processing logic
    - docs: update setup instructions
    - chore: upgrade spring boot version

# Pull Request Workflow
    - Fork the repository
    - Create a new branch from develop
    - Make your changes
    - Commit using proper conventions
    - Push your branch
    - Open a Pull Request to develop

# Code Quality Expectations
    - Follow clean code principles
    - Write meaningful variable and method names
    - Keep methods small and focused
    - Add tests where applicable
    - Avoid unnecessary complexity

# What NOT to Do
    - Push directly to develop or main branch
    - Use unclear commit messages like update, fix stuff
    - Mix multiple changes in one commit
    - Submit untested code

# Pro Tips (Real-World Practices)
    - Make small, focused commits
    - Commit early and often
    - Write commits as if you are explaining to your future self
    - Review your own PR before submitting
    - Keep PRs small and easy to review