### Component 01: User Management

----
### Customer User Api

1. Get Method - Get All Customers in Database <br>
`localhost:8080/customer/customers`
2. Get Method - Get Customer by Id <br>
`localhost:8080/customer/1`
3. Post Method - create new User <br>
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
4. Put method - Update User By Id <br>
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
5. Delete method - delete user by Id <br>
`http://localhost:8080/customer/1`



### Component 02: Admin Management

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


### Component 03: Authentication

----
### Authentication Admin api
1.Post Method -  if this user present can login. <br>
`http://localhost:8080/auth/admin`

#### input format
```
{
    "userName": "Admin1",
    "password": "abcd@123"
}
```
#### output format
```
{
    "userName": "Admin1",
    "password": "abcd@123",
    "phoneNumber": "94118812388",
    "securityNumber": "600",
    "Role": "ADMIN",
    "id": 3
}
```
2. Post Method - if this user present can login. <br>
`http://localhost:8080/auth/customer`
#### input format
```
{
    "userName": "testuser1",
    "password": "abc@123"
}
```

#### output format
```
{
    "userName": "testuser1",
    "password": "abc@123",
    "phoneNumber": "0712655299",
    "securityNumber": "500",
    "Role": "CUSTOMER",
    "id": 1
}
```

3. PUT method - if you know username & phone number & Security code you dont know the password you can reset the password <br>
`http://localhost:8080/auth/customerforgot`

#### input format
```
{
    "userName": "testuser1",
    "phoneNumber": "0712655299",
    "SecurityNumber": "500",
    "password": "200515600@alal"
}
```

#### output format
```
{
    "userName": "testuser1",
    "password": "200515600@alal",
    "phoneNumber": "0712655299",
    "securityNumber": "500",
    "Role": "CUSTOMER",
    "id": 1
}
```
4. PUT method - same as customer <br>
`http://localhost:8080/auth/adminforgot`




