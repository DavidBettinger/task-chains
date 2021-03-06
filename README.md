## The development of this app is currently on hold, because I am switching to Angular for the frontend

# Task-Chains Spring Boot Web App

A Spring Boot web app to create and manage task-chains.

## Introduction

The purpose of this app is to create and manage lists (chains) of tasks as well as create the accounts for 
the users who use my [task-chain android app](https://github.com/DavidBettinger/task-chains-android-app). 

My aim for this project is primarily to learn how to build good Java Spring Boot programs.

### Example of use

A task-chain could represent the things that need to be done in connection with the opening of a new branch. Such as:

1. To sign the rental agreement
2. Read the eletricity meter (at the new branch)
3. Register the new branch with the electricity provider (at the main office)
4. ...

### Planned and implemented functionalities

At the moment this project is in a very early development stage. So far you can:

- Create a task-chain and save it to the server
- Create tasks associated with a task-chain and save them
- Review and edit tasks and task-chains
- Use the API to get task-chains and users stored on the server (my  [task-chain android app](https://github.com/DavidBettinger/task-chains-android-app) is using this API)

There are many more features I plan to add to the app. At the moment the most important ones are:

- Add Spring Security to the project to implement the login funtionality
- Add the user management
- Add the option to assign tasks to specific users or user groups
- Add the possibility to monitor the completion of task
- Rework the UI
- Move from the in-memory DB to a permanent DB
- ...



### Technologies

- Java
- Maven
- Spring Boot
- Vaadin (maybe I will move to Thymeleaf)
- Spring Security (planned)
- JPA



### Other information

For the past few months I have been doing Spring Framework, Spring Security and Spring Boot Microservice online courses.
I use this project to deepen my Spring Boot knowledge and gain more programming experience. And at the same time I want to create a program that may be useful to me or other people in the future.


