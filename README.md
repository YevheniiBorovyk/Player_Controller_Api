# API Java Retrofit Project

## Description
This project provides a set of API clients using Retrofit for Java. It is intended to be used as a dependency in other projects, such as UI testing projects.

## Requirements
- Java 11 or higher
- Maven 3.6.0 or higher

## Setup
Follow these steps to clone and set up the project:

1. Clone the repository:
    ```bash
    git clone https://github.com/YOUR_USERNAME/api-java-retrofit.git
    ```

2. Build the project and install the dependencies:
    ```bash
    mvn clean install -DskipTests
    ```

3. This will generate the JAR file for the API project, which can be included as a dependency in other projects.

## Using as a Dependency
To use this API project in another Maven project, add the following dependency to your `pom.xml` file:

```xml
<dependency>
    <groupId>com.yourorganization</groupId>
    <artifactId>api-java-retrofit</artifactId>
    <version>1.0.0</version>
</dependency>
