# Servlets (Repair Agency)

The Repair Agency web application is written using Servlets. This site allows the customer to register and log into their account, where the user can leave an order for any repair service (example services are listed on the main page). 

There are also managers and workers who have access to the list of users and their orders, which they can edit and set the price, worker and execution status.

### Tech

* Servlets;
* Jetty / Tomcat;
* Postgresql;
* JDBC;
* Html/Css/Js;
* JSP.

### Functions 

1. Registration, authorization and logout.
2. There are three roles: manager, worker, user.
3. Checking for an existing user.
4. A registered user can: 
   - access to your order page;
   - create / edit / delete your own orders.
5. A manager and a worker can: 
   - access to the list of all users;
   - view the list of user's orders
   - delete a user and all his orders (only manager);
   - edit order (worker can change only state);
   - delete order (only manager).
6. Sorted orders by id, name, price.
7. Filter users by name, role, email (for manager and worker).
8. Implemented web interface.


### Result

**1. Registration** 

When registering, the user enters a Name, Email and Password. If email already exists, the user will not be able to create an account and a message will be displayed:

<p align="center">
  <img src="https://github.com/bbogdasha/repairAgencyServlets/blob/main/screen/registrationFalce.jpg" width="70%" />
</p>

If registration is successful, the user will be redirected to the login page:

<p align="center">
  <img src="https://github.com/bbogdasha/repairAgencyServlets/blob/main/screen/registrationTrue.jpg" width="70%" />
</p>

---

**2. User page** 

After successful registration and authorization, the user gets to a page where he can create an order - the subject of the order and a message that explains the problem (with a status of "Processing"):

<p align="center">
  <img src="https://github.com/bbogdasha/repairAgencyServlets/blob/main/screen/user1.png" width="60%" />
</p>

Let's create multiple orders:

<p align="center">
  <img src="https://github.com/bbogdasha/repairAgencyServlets/blob/main/screen/user2.png" width="60%" />
</p>

The user can view his order, it will have fields - title, message, the name of the client, price (put up the manager), the name of the worker (put up the manager) and status (automatically put up in the "process"):

<p align="center">
  <img src="https://github.com/bbogdasha/repairAgencyServlets/blob/main/screen/user3.png" width="60%" />
</p>

The user will also be able to edit his order, but only the title field and the message will be available for editing, the rest is not available to the user:

<p align="center">
  <img src="https://github.com/bbogdasha/repairAgencyServlets/blob/main/screen/user4.png" width="60%" />
</p>

The user can delete their order. And finally log out of the account.

---

**3. Sorting** 

All roles (manager, worker, user) can sort orders by id, name and price.
The result of sorting in the short gif

<p align="center">
  <img src="https://github.com/bbogdasha/repairAgencyServlets/blob/main/screen/sorting.gif" width="60%" />
</p>

---

**4. Manager page** 

The manager and worker have access to a page with a complete list of all users and their orders. They can filter all users by name, role, or email address. They can see all the orders of a particular user and be able to edit them.

Worker edits the job status field from "Processing" to "Working" to "Done". The manager can assign a worker from the list of possible workers and set the price of the order.

The manager can also delete the user and his orders.

An example of work on the manager's page is shown in a short gif:

<p align="center">
  <img src="https://github.com/bbogdasha/repairAgencyServlets/blob/main/screen/manager.gif" width="60%" />
</p>

---
