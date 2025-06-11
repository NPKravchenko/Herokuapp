# Restful Booker API Test Suite

This project contains automated tests for the [Restful Booker API](https://restful-booker.herokuapp.com) using Java, TestNG, and Rest Assured. It includes CRUD operations and validations for booking scenarios with Allure reporting and GitHub Actions CI integration.

## ✅ Features

- Create, Get, Update, Partially Update, and Delete bookings
- Health check endpoint validation
- Tests written using:
    - TestNG
    - REST Assured
    - Allure for test reporting

## 🛠 Project Setup

### Prerequisites

- Java 17 or higher (Java 24 is used in this project)
- Maven 3.6+
- Git

### Clone the Repository

```bash
git clone https://github.com/nkravchenko/Herokuapp.git
cd Herokuapp
```

### Run Tests Locally

```bash
mvn clean test
```

### Generate Allure Report

```bash
allure serve target/allure-results
```

Or generate and open the report manually:

```bash
mvn allure:report
mvn allure:serve
```

## 🚀 GitHub Actions CI

This project is configured to run tests automatically using GitHub Actions.

### Example Workflow

```yaml
name: API Tests

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  test:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v3

      - name: Set up JDK
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Build and test
        run: mvn clean test

      - name: Generate Allure report
        run: mvn allure:report

      - name: Upload Allure results
        uses: actions/upload-artifact@v3
        with:
          name: allure-report
          path: target/site/allure-maven-plugin
```

> 📝 Make sure to include a `.github/workflows/main.yml` file with this configuration to enable CI in your repo.

## 📁 Project Structure

```
.
├── src
│   └── test
│       └── java
│           └── com
│               └── herocuapp
│                   └── restfulbooker
│                       ├── BaseTest.java
│                       ├── CreateBookingTest.java
│                       ├── DeleteBookingTest.java
│                       ├── GetBookingTest.java
│                       ├── GetBookingsTest.java
│                       ├── HealthCheckTest.java
│                       ├── PartialUpdateBookingTest.java
│                       └── UpdateBookingTest.java
├── pom.xml
└── README.md
```

## 📦 Dependencies

- TestNG
- REST Assured
- JSON
- Jackson
- Allure TestNG
- Allure REST Assured

## 📄 License

This project is licensed under the MIT License.



