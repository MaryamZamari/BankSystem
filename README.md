## 🏦 Banking System _Java SE

A robust, console-based Banking System built with Java SE, focusing on clean code principles, thread-safe operations, and automated data persistence.

**📂 Project Structure****

    .
    ├── client.json (Client data storage)
    ├── initData.json (System initialization)
    ├── pom.xml (Maven dependencies)
    └── src
        ├── main/java/com/javasSE/banking
        │   ├── ApplicationRunner.java (Entry Point)
        │   ├── accountService (Logic, DTOs, Facades)
        │   ├── clientService (Validation, Exceptions)
        │   ├── conversionService (Currency exchange)
        │   └── common (Shared utilities & IdGenerators)
        └── test/java (JUnit Validation tests)

**🛠️ Key Features**
**1.Concurrency Support:** Uses AtomicInteger for thread-safe ID generation (clientIdCounter, accountIdCounter, transactionIdCounter).

**2.Validation Layer:**** Comprehensive validation for emails, passwords, and client types (Legal vs. Personal).

**3.Facade Pattern:** Simplifies complex sub-system interactions through AccountFacade and ClientFacade.

**4.Data Persistence:** At the moment automatically saves and loads system state from JSON files.

**5.Currency Conversion:** Built-in service for handling transactions across different currency types.

## ⚙️ Technical Stack

**Language:** Java 17+
**Build Tool:** Maven
**Mapping:** MapStruct (for DTO to Model conversion)
**Testing:** JUnit 5

**🚦 Getting Started**
**1.Clone the repository.**
**2.Compile the project:** mvn clean install
**3.Run the Application:** Execute ApplicationRunner.java to start the console interface.
**4.Initial Data:** The system will look for *initData.json* on startup to populate the bank's initial state.
