# Project Management Tool - Ticketing Project

### 📖 Information

<ul style="list-style-type:disc">
  <li>This app serves as a versatile
project management solution. At its core, the ticketing app is a powerful project management tool with
three distinct roles: <b>admin, manager, and employee</b>. Each role has specific
privileges and responsibilities.</li> 
  <li>Here is the explanation of roles:
       <ul><b>Admin:</b> Have the authority to create users and initiate new projects,
serving as the backbone of the app's administrative functions.</ul> <ul><b>Manager:</b> Can view their assigned projects and assign tasks to
employees. Their role is essential for overseeing projects and task
delegation.</ul> <ul><b>Employee:</b>  Focus on executing tasks assigned by managers,
contributing to project execution and success.</ul>
  </li>
</ul>

### Technical Information
<ul style="list-style-type:disc">
<li>Built on Spring Boot, this microservices project employs Spring Cloud API Gateway, Netflix Eureka for service discovery, Resilience4j for fault tolerance, Prometheus and Grafana for monitoring, Sleuth and Zipkin for distributed tracing, and Spring Cloud Config Server with GitHub integration. Utilizing Maven, PostgreSQL, JPA with Hibernate, and OAuth2 security via Keycloak, the RESTful APIs are documented using OpenAPI 3.0. This comprehensive stack ensures resilience, scalability, and secure, efficient communication</li>
</ul>

### Technologies

---
- Java 11
- Spring Boot 2.3.4.RELEASE
- Spring Cloud 2.2.6.RELEASE
- Netflix Eureka
- Spring Cloud API Gateway
- Resilience4j
- Prometheus and Grafana
- Sleuth and Zipkin
- Spring Cloud Config Server with GitHub integration
- Restful API
- Spring data, JPA & Hibernate,
- PostgreSQL
- OAuth2 security framework
- Spring Security
- Keycloak
- Maven
- Docker 
- OpenAPI3
- Lombok

---
<p align="center">
    <img src="ticketing_project_microservices_architecture.png" alt="Main Information">
</p>

---
### Business Logics

<ul style="list-style-type:disc">
<li><B>Soft Delete Implementation:</B>
<ul>The system uses a soft delete approach. This ensures that records are not permanently deleted. It protects historical data and maintains data integrity.</ul>
</li>

<li><B>Unique Constraints:</B>
<ul>Made usernames and project codes unique in the system. This prevents duplication and ensures reliable identification.</ul>
</li>

<li><B>Manager Deletion Restriction:</B>
<ul>Managers cannot be deleted if they are assigned to a project. This precaution prevents data loss by keeping managerial associations with ongoing projects.</ul>
</li>

<li><B>Employee Deletion Restriction:</B>
<ul>Cannot delete employees with assigned tasks. This is to keep task data and avoid losing information by mistake.</ul>
</li>

<li><B>Manager Project Completion Authorization:</B>
<ul>Managers can mark a project as completed only if all tasks for the project are also marked as completed. This ensures that projects are considered complete only when all related tasks are successfully finished, keeping a full view of project status.</ul>
</li>

<li><B>Admin Project Completion Authorization:</B>
<ul>When an admin user marks a project as completed, all tasks associated with that project are also marked as completed. This keeps everything consistent and shows how projects and tasks are connected.</ul>
</li>
</ul>


---

### Prerequisites


- Maven or Docker
---


### Docker Run

The application can be built and run by the `Docker` engine.

Please follow directions shown below in order to build and run the application with Docker Compose file;

```
$ cd My-ticketing-project-MIKROSERVICES
$ docker-compose up -d
```

If you change anything in the project and run it on Docker, you can also use this command shown below

```sh
$ cd My-ticketing-project-MIKROSERVICES
$ docker-compose up --build
```

---
### Maven Run
To build and run the application with `Maven`, please follow the directions shown below;

```sh
$ cd My-ticketing-project-MIKROSERVICES
$ mvn clean install
$ mvn spring-boot:run
```


### Creator

- [Muhammed Ihsan SOLAK](https://github.com/muhammedihsansolak)