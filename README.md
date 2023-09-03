<h1 align="center"> ⚡️ WondrWord ⚡️</h1>


![Logo](./Wonderland_Frontend/Images/image.png)


<!-- ### Link: https://drive.google.com/file/d/1F8R8e6eU3rrtEPq5xuSFkN33GKvMDMb4/view?usp=sharing  -->


## Acknowledgements:

Wondr Wrod, the amusement park web application, built with Java Spring Boot, HTML, CSS, and JavaScript, helps visitors to plan their trip, purchase tickets for different activities , and learn about park attractions. It features user authentication, user-friendly interfaces. The application provides a seamless experience for visitors, making their trip more enjoyable.
## Functions:

## Base:
- WondrWordApplication

## Controller:
- AdminController
- CustomerController
- TicketController
- ActivityController

## End Points:
 
 ### *Admin Controller Endpoints*

- **POST** `/admin/registerAdmin` Register a new admin user.
- **GET** `/admin/signin` Authenticate an admin user.
- **DELETE** `/admin/delete/{adminId}` Delete an admin user.
- **GET** `/admin/{adminId}` Get admin by ID.
- **GET** `/admin/all` Get all admins with pagination.
- **GET** `/admin/customers` Get all customers.
- **GET** `/admin/customers/{customerId}` Get customer by ID.
- **DELETE** `/admin/customers/delete/{customerId}` Delete a customer.

 ### *Customer Controller Endpoints*

- **POST** `/customers/registerCustomer` Register a new customer.
- **GET** `/customers/signin` Authenticate a customer.
- **PUT** `/customers/update/{customerId}` Update customer details.
- **DELETE** `/customers/delete/{customerId}` Delete a customer.
- **GET** `/customers/{customerId}` Get customer by ID.


### *Ticket Controller Endpoints*
- **GET** `/admin/ticket/getAllTicket` Get all tickets.
- **GET** `/admin/ticket/{ticketId}` Get ticket by ID.
- **POST** `/customers/ticket/{customerId}/{activityId}` Create a new ticket.
- **PUT** `/customers/ticket/{customerId}/{ticketId}` Update a ticket.
- **GET** `/customers/ticket/{customerId}/{ticketId}` Get ticket by ticket ID.
- **DELETE** `/customers/ticket/{customerId}/{ticketId}` Delete a ticket.
- **GET** `/customers/ticket/history/{customerId}` Get ticket booking history with pagination.
- **GET** `/customers/ticket/todayHistory/{customerId}` Get ticket booking history for the day.
- **GET** `/customers/ticket/fair/{customerId}` Get total fare for the customer.
### *Activity Controller Endpoints*
   
- **POST** `/admin/activity/add` Add a new activity.
- **PUT** `/admin/activity/update/{activityId}` Update an activity.
- **DELETE** `/admin/activity/delete/{activityId}` Delete an activity.
- **GET** `/admin/activity/all` Get all activities with pagination.
- **GET** `/admin/activity/{activityId}` Get activity by ID.
- **GET** `/admin/activity/getActivitiesByCharge` Get activities by charge.
- **GET** `/admin/activity/getNumberOfActivitiesByCharge/{charge}` Get count of activities by charge.
- **GET** `/admin/activity/getAllActivitiesByDate` Get activities within a date range.
- **GET** `/admin/activity/getAllActivitiesOfCustomerByDate/{customerId}/date` Get activities of a customer within a date range.

## Model:
- Admin
- Customer
- Ticket
- Activity

## DTO:
- TicketDTO

## Exceptions:
- AdminException
- CustomerException
- TicketException
- ActivityException
- GlobalErrorException
- ErrorDetails

## Teck Stacks:
- Java
- Hibernate
- SpringBoot
- REST API
- Lombok
- SQL
- Swagger
- Postman

<!-- ## Class method design:
![image](./)

## Class module design:
![image](https://user-images.githubusercontent.com/104348363/201664014-a1eb958f-0986-47e0-8c5d-16c760ba5113.png) -->

##  ER Diagram:
![alt text](./Wonderland_Frontend/Images/ER.jpg)

## Restrictions:
- Before performing any task the user should be confirmed whether the user is Admin or Customer and for that the user should have logged in.<br/>
- For each contoller we need to provide the login details for implementation of particular methods i.e, if the customer wants to purchase some planter then he/she should give his/her correct name during purchasing session and only customer can perform that action and for that One to Many relationship is established.
- If we want to add some plants or seeds then only admin can do that so here we have provided the validation i.e during adding a particular item  user should give the correct name of the admin otherwise it will throw an exception and that exception is properly handled.

