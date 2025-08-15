# crudeoil-spring-analytics
# Spring Boot Data Analytics 🌱

A simple Spring boot application for an analytics engine that takes data from MySQL and performs analysis and generates report in real-time. 

## Overview

This project showcases how to leverage Spring Boot for building a backend service that performs data analytics on a dataset, integrating with a MySQL database.The original goal was to see if Java could handle data analytics tasks similarly to Python. While Python remains simpler for quick analysis, this project has grown to incorporate *MySQL* and *Docker*, moving it toward an **enterprise-style application* rather than a simple analytics script.

## Features

- Connects to a MySQL database containing oil production data.
- Uses Spring Data JPA for ORM and database interactions.
- Custom YearAttributeConverter for mapping Java Year to integer in the database.
- Provides data aggregation operations: minimum, maximum, and average quantity.
- Clean, modular code to demonstrate best practices with Spring Boot and JPA.

## Future Potential
- In theory, this setup can evolve into a *real-time report generator*. Once fully developed:  
- Data fed into MySQL would immediately update calculations and instantaneous download of up-to-date reports
- Fully modular ERP-style architecture
- Reports could be generated instantaneously with a GUI for interactive report generation 
- Continuous monitoring of datasets would be possible (ideal for frequently updating data like stock prices)

Currently, this project serves as a *comparative study between Spring Boot and Python for data analytics*, while laying groundwork for an enterprise application with modular architecture, database integration, and Docker support.

## Tech Stack (if you're still curious)

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
   git clone https://github.com/AdityaShankar1/spring-data-analytics.git
   cd spring-data-analytics
