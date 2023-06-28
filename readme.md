## Setting Up a Grails Project Documentation

This documentation provides step-by-step instructions for setting up a Grails project and configuring it to use the MySQL database with the zemployee name.

## Prerequisites

Before starting, make sure you have the following prerequisites installed on your system:

- Java Development Kit (JDK)
- Grails Framework
- MySQL database server

## Step 1: Add MySQL Connector Dependency

Clone the GitHub repository

`git clone [https://github.com/RupakShrestha01/ZEMPLOYEE-BE]`(https://github.com/RupakShrestha01/ZEMPLOYEE-BE)

## Step 2: Add MySQL Connector Dependency

1. Open the build.gradle file in your project's root directory.
1. Locate the dependencies section.
1. Add the following line to include the MySQL Connector/J dependency: 4.

`implementation group: 'mysql', name: 'mysql-connector-java', version: '5.1.6'`

1. Save the build.gradle file.
1. Run the following command to download and install the dependency:

`grails compile`

## Step 3: Configure the Database

1. Open the grails-app/conf/application.yml file in a text editor.
1. Locate the dataSource section.
1. Update the url, username, and password properties to match your MySQL database configuration. For example:

`dataSource:`
`pooled: true`
`jmxExport: true`
`driverClassName: com.mysql.cj.jdbc.Driver  `
`username: ‘your username’`
`password: ‘your password’`
`environments:  `
`development:  `
`dataSource:`
`dbCreate: update`
`url: 'jdbc:mysql://localhost/zemployee? useUnicode=yes&characterEncoding=UTF-8&useJDBCCompliantTimezoneShif t=true&useLegacyDatetimeCode=false&serverTimezone=UTC&jdbcCompliantT runcation=false'`

4. Replace ‘your username’ and ‘your password’ with the appropriate credentials.
5. Save the application.yml file.

## Step 4: Create the zemployee Database

1. Open a MySQL client (e.g., MySQL command-line client, phpMyAdmin, or MySQL Workbench).
1. Connect to your MySQL database server using the appropriate credentials.
1. Execute the following SQL command to create the zemployee database:

`CREATE DATABASE zemployee;`

1. Verify that the zemployee database has been created successfully.

## Step 5: Verify the Configuration

To verify that the configuration is correct and the project is set up properly, follow these steps:

1. Run the following command to start the Grails application:

`grails run-app`

2. Open a web browser and navigate to http://localhost:8080.
3. If you see the Grails welcome page, it indicates that the project has been set up successfully.

Congratulations! You have successfully set up a Grails project and configured it to use the MySQL database with the zemployee name. You can now start developing your application using Grails and utilize the zemployee database for your needs.
