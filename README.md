# My Store

## Project Description
Project is made with SpringBoot for back end and uses Thymleaf and JS for front end. Use store.env file to store database connection information - file added for project setup for SoftUnit course with removed username and passwords for project setup.
It is intended to be a simple online store project with the flowing entities

* User
* Product
* Cart - uses helper entity CartItem
* Order - uses helper entity OrderItem

## Project properties
Project is build using Maven, Java 17, Spring 4.0.6. 
The project uses hibernate for entity management with PostgreSQL database.

## Functionalityes
* User login
* User register - curently only supports editing uses FistName, LastName and ImageUrl
* User profile - view
* Crate Product
* Update Product - only users that own the product can edit the product
* Delete Product
* Add Product to Users Cart
* Edit quantity of Product in users Cart - removes the product if quantity is less than 0
* Checkout cart into orders for users
* Edit order status - on order shipping product stock quantity is updated as well as all cart items that have that product in them