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

