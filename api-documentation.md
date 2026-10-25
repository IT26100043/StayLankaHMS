### Component 01: User Management

----
### Customer User Api

1. Get Method - Get All Customers in Database
`localhost:8080/customer/customers`
2. Get Method - Get Customer by Id
`localhost:8080/customer/1`
3. Post Method - create new User
`localhost:8080/customer/register`
#### input format
*phoneNumber and userName are Unique Attribute*
````
{
    "userName": "leslyyys",
    "password": "Lesly123",
    "phoneNumber": "7128942147",
    "securityNumber": "600"
}
````
#### output format
````
{
    "userName": "leslyyys",
    "password": "Lesly123",
    "phoneNumber": "7128942147",
    "securityNumber": "600",
    "Role": "CUSTOMER",
    "id": 7
}
````
4. Put method - Update User By Id
`http://localhost:8080/customer/1`

#### input format 
*phone number and username are uniqe*
```
{
    "userName": "loeslyyys",
    "password": "Lesly123",
    "phoneNumber": "71289425417",
    "securityNumber": "600"
}
```
#### output format
```
{
    "userName": "loeslyyys",
    "password": "Lesly123",
    "phoneNumber": "71289425417",
    "securityNumber": "500",
    "Role": "CUSTOMER",
    "id": 1
}
```
5. Delete method - delete user by Id
`http://localhost:8080/customer/1`



### Component 01: Admin Management

----
### Admin User Api

*input and output method same as customer user and phone number and username are unique*

1. Get Method - Get All Customers in Database<br>
   `localhost:8080/admin/admins`

2. Get Method - Get Customer by Id <br>
   `localhost:8080/admin/1`

3. Post Method - create new User<br>
   `localhost:8080/admin/register`

4. Put method - Update User By Id<br>
   `localhost:8080/admin/1`

5. Delete method - delete user by Id<br>
   `localhost:8080/admin/1`
