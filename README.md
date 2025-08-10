# crudeoil-spring-analytics
# Spring Boot Data Analytics

A simple Spring Boot application demonstrating basic data analytics operations such as minimum, maximum, and average calculations on an oil production dataset.

## Overview

This project showcases how to leverage Spring Boot for building a backend service that performs data analytics on a dataset, integrating with a MySQL database. The focus is on illustrating the use of Spring Data JPA, entity mapping with Java 8 Year, and custom attribute converters.

## Features

- Connects to a MySQL database containing oil production data.
- Uses Spring Data JPA for ORM and database interactions.
- Custom YearAttributeConverter for mapping Java Year to integer in the database.
- Provides data aggregation operations: minimum, maximum, and average quantity.
- Clean, modular code to demonstrate best practices with Spring Boot and JPA.

## Tech Stack

- Java 21
- Spring Boot 3.5.4
- Spring Data JPA
- MySQL
- Maven

## Getting Started

### Prerequisites

- Java 21 installed
- MySQL database running with appropriate credentials
- Maven 3.x installed

### Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/spring-data-analytics.git
   cd spring-data-analytics
