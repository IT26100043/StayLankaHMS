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


### Component 04: Review Management

----
### Review User Api

1.GET method - get all review details <br>
`http://localhost:8080/review/reviews`

#### output format 
```
[
    {
        "user": {
            "userName": "lesly",
            "password": "Lesly123",
            "phoneNumber": "078300468",
            "Role": "CUSTOMER",
            "securityNumber": "800",
            "hibernateLazyInitializer": {},
            "id": 6
        },
        "room": {
            "roomId": "R105",
            "pricePerNight": 5500,
            "capacity": 3,
            "status": "Available",
            "hibernateLazyInitializer": {},
            "type": "Standard"
        },
        "rating": 5,
        "comment": "Good Rooms",
        "Date": "2026-11-25",
        "id": 1
    }
]
```
2.POST method - create new Review <br>
`http://localhost:8080/review/newreview`
#### Input Method
``` 
{
    "customerId": 1,
    "roomId": "R101",
    "rating": 5,
    "comment": "ain krgnim"
}
```
#### output format

```
{
    "user": {
        "userName": "testuser1",
        "password": "200515600@alal",
        "phoneNumber": "0712655299",
        "securityNumber": "500",
        "Role": "CUSTOMER",
        "id": 1
    },
    "room": {
        "capacity": 2,
        "status": "Available",
        "pricePerNight": 5000,
        "roomId": "R101",
        "type": "Standard"
    },
    "rating": 5,
    "comment": "ain krgnim",
    "Date": "2026-10-25",
    "id": 2
}
```

3.GET Method - get review from Id <br>
`http://localhost:8080/review/1`

#### output format
```
{
    "user": {
        "userName": "lesly",
        "password": "Lesly123",
        "phoneNumber": "078300468",
        "Role": "CUSTOMER",
        "securityNumber": "800",
        "hibernateLazyInitializer": {},
        "id": 6
    },
    "room": {
        "roomId": "R105",
        "pricePerNight": 5500,
        "capacity": 3,
        "status": "Available",
        "hibernateLazyInitializer": {},
        "type": "Standard"
    },
    "rating": 5,
    "comment": "marumaru",
    "Date": "2026-11-25",
    "id": 1
}
```

4.DELETE Method - delete the review from it ID <br>
`http://localhost:8080/review/1`

5.PUT Method - Update existing Rating and comment <br>
`http://localhost:8080/review/2`

#### input format
```
{
    "rating": 5,
    "comment": "marumaruuuu"
}
```

#### output format
```
{
    "user": {
        "userName": "testuser1",
        "password": "200515600@alal",
        "phoneNumber": "0712655299",
        "Role": "CUSTOMER",
        "securityNumber": "500",
        "hibernateLazyInitializer": {},
        "id": 1
    },
    "room": {
        "roomId": "R101",
        "pricePerNight": 5000,
        "capacity": 2,
        "status": "Available",
        "hibernateLazyInitializer": {},
        "type": "Standard"
    },
    "rating": 5,
    "comment": "marumaruuuu",
    "Date": "2026-10-25",
    "id": 2
}

```

### Component 05: Payment Management

----
### Payment Api

1. GET Method - get all the payment methods <br>
`http://localhost:8080/payment/payments`

#### output format
```
[
    {
        "user": {
            "userName": "lesly",
            "password": "Lesly123",
            "phoneNumber": "078300468",
            "Role": "CUSTOMER",
            "securityNumber": "800",
            "hibernateLazyInitializer": {},
            "id": 6
        },
        "booking": {
            "roomId": "AAA",
            "guestName": "ABC",
            "guestPhone": "071233654",
            "checkIn": "2026-11-01",
            "checkOut": "2026-11-10",
            "totalPrice": 1500,
            "status": "CONFIRMED",
            "bookingId": 2,
            "hibernateLazyInitializer": {}
        },
        "status": "CONFIRMED",
        "paymentDate": "2026-11-20T10:10:20",
        "amount": 1000,
        "paymentId": 1
    }
]

```
2. GET method - get payment by ID <br>
`http://localhost:8080/payment/1`

#### output format
```
{
    "user": {
        "userName": "lesly",
        "password": "Lesly123",
        "phoneNumber": "078300468",
        "Role": "CUSTOMER",
        "securityNumber": "800",
        "hibernateLazyInitializer": {},
        "id": 6
    },
    "booking": {
        "roomId": "AAA",
        "guestName": "ABC",
        "guestPhone": "071233654",
        "checkIn": "2026-11-01",
        "checkOut": "2026-11-10",
        "totalPrice": 1500,
        "status": "CONFIRMED",
        "bookingId": 2,
        "hibernateLazyInitializer": {}
    },
    "status": "CONFIRMED",
    "paymentDate": "2026-11-20T10:10:20",
    "amount": 1000,
    "paymentId": 1
}
```
3. POST - create a new payment <br>
`http://localhost:8080/payment/newpay`

#### input format
```
{
    "customerId": 1,
    "bookingId": 2,
    "cardHolderName": "John Silva",
    "cardCvvNumber": "123"
}
```
#### output format
```
{
    "cardHolder": "John Silva",
    "cardCvvNumber": "123",
    "user": {
        "userName": "testuser1",
        "password": "200515600@alal",
        "phoneNumber": "0712655299",
        "securityNumber": "500",
        "Role": "CUSTOMER",
        "id": 1
    },
    "booking": {
        "roomId": "AAA",
        "guestName": "ABC",
        "guestPhone": "071233654",
        "checkIn": "2026-11-01",
        "checkOut": "2026-11-10",
        "totalPrice": 1500,
        "status": "CONFIRMED",
        "bookingId": 2
    },
    "status": "PENDING",
    "paymentDate": "2026-10-25T15:36:31.2754042",
    "amount": 1500,
    "paymentId": 2
}
```
4. PUT method - update cvv and card holder name <br>
*can update both same time or once at a time* <br>
`http://localhost:8080/payment/2`

#### input format 
```
{
    "cardHolder": "John Silva Updated 2000",
    "cardCvvNumber":"100"
}
```
#### output format

```
{
    "cardHolder": "John Silva Updated 2000",
    "cardCvvNumber": "100",
    "user": {
        "userName": "testuser1",
        "password": "200515600@alal",
        "phoneNumber": "0712655299",
        "Role": "CUSTOMER",
        "securityNumber": "500",
        "hibernateLazyInitializer": {},
        "id": 1
    },
    "booking": {
        "roomId": "AAA",
        "guestName": "ABC",
        "guestPhone": "071233654",
        "checkIn": "2026-11-01",
        "checkOut": "2026-11-10",
        "totalPrice": 1500,
        "status": "CONFIRMED",
        "bookingId": 2,
        "hibernateLazyInitializer": {}
    },
    "status": "PENDING",
    "paymentDate": "2026-10-25T15:36:31.275404",
    "amount": 1500,
    "paymentId": 2
}
```

5. DELETE - payment record by Id
`http://localhost:8080/payment/2`





