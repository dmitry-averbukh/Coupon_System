# Coupon_System_2.0

In that stage of the _**Coupon-System**_ project I improved my previous project using `Spring` and `Hibernate` frameworks;

API Reference
=


The _**Coupon_System_v2.0**_   API is organized around REST. 

That API has predictable resource-oriented URLs, accepts and returns JSON-encoded responses

These URLs represent information or content accessed at that location, which will be returned as JSON.

_**Errors**_
---

200 - OK   ………………   Everything worked as expected.


400 - Bad Request …… The request was unacceptable, often due to missing a required parameter.

401 - Unauthorized …… No valid API token provided.

404 - Not Found ……… The requested resource doesn't exist.

500, 502, 503, 504 - Server Errors ……… Something went wrong on _Coupon-System's_ end. (These are rare.)

----

**_Endpoint_**
------

All requests go to https://coupon_system.org/api

----

**_LOGIN_**
--

Login in your account:

```GET - /login?email=_ _ _&password=_ _ _```

responses 200-OK

Content type **`json`** - contains `token`

After you get your token, you can use it in subsequent requests;

Token will be deleted after 30 minutes from the last request

Response sample:

```
 {
   "token": "031d3bf57377463"
 }
```

----

**_ADMIN_**
-

###### GET ALL COUPONS

to get all coupons:

```GET - /admin/all_coupons?token=_ _ _ _```

responses 200-OK



Response sample:

```
     {
         "id": 1,
         "company": {
             "id": 1,
             "name": "nike",
             "email": "nike@gmail.com"
         },
         "title": "second one 50% off",
         "startDate": "2222-08-10T17:59:00",
         "endDate": "2222-08-10T17:59:00",
         "category": 1488,
         "amount": 1488,
         "description": "shoes sale",
         "price": 50.0,
         "imgUrl": "nike.com/photos/9393948"
     },
     {
         "id": 8,
         "company": {
             "id": 2,
             "name": "adidas",
             "email": "adidas@mail.ru"
         },
         "title": "final sale",
         "startDate": "2021-08-27T02:26:19",
         "endDate": "2020-08-27T02:26:04",
         "category": 12,
         "amount": 1000,
         "description": "all for sale 50%",
         "price": 50.0,
         "imgUrl": "adimdas.cam/image/345687"
     }

```

###### GET ALL CUSTOMER COUPONS

to get all customer coupons by id:

```GET - /admin/customer_coupons?token=_ _ _ &id=_ _ _```

responses 200-OK

Response sample:

```
     {
         "id": 1,
         "company": {
             "id": 1,
             "name": "nike",
             "email": "nike@gmail.com"
         },
         "title": "second one 50% off",
         "startDate": "2222-08-10T17:59:00",
         "endDate": "2222-08-10T17:59:00",
         "category": 1488,
         "amount": 1488,
         "description": "shoes sale",
         "price": 50.0,
         "imgUrl": "nike.com/photos/9393948"
     },
     {
         "id": 8,
         "company": {
             "id": 2,
             "name": "adidas",
             "email": "adidas@mail.ru"
         },
         "title": "final sale",
         "startDate": "2021-08-27T02:26:19",
         "endDate": "2020-08-27T02:26:04",
         "category": 12,
         "amount": 1000,
         "description": "all for sale 50%",
         "price": 50.0,
         "imgUrl": "adimdas.cam/image/345687"
     }

```

###### GET ALL COMPANY COUPONS

to get company coupons by company id:

```GET - /admin/company_coupons?token=_ _ _ &id=_ _ _```

responses 200-OK

Response sample:

```
          {
             "id": 8,
             "company": {
                 "id": 2,
                 "name": "adidas",
                 "email": "adidas@mail.ru"
             },
             "title": "test",
             "startDate": "2021-08-27T02:26:19",
             "endDate": "2020-08-27T02:26:04",
             "category": 1488,
             "amount": 1488,
             "description": "test",
             "price": 0.0,
             "imgUrl": "test"
         },
         {
             "id": 12,
             "company": {
                 "id": 2,
                 "name": "adidas",
                 "email": "adidas@mail.ru"
             },
             "title": "",
             "startDate": "2020-08-10T17:59:00",
             "endDate": "2021-08-10T17:59:00",
             "category": 1488,
             "amount": 1488,
             "description": "testing",
             "price": 14.88,
             "imgUrl": "pozilaya zigotta"
         }

```


###### ADD COUPON

to add a new coupon:

```POST - /admin/add_coupon?token=_ _ _```

in the `body` of your request enter coupon, in JSON format, you wanted to add:

Request sample:

```

{
    "company":{
        "id":"3"
    },
    "amount":"1",
    "category":"1",
    "description":"testing",
    "imgUrl":"test",
    "price":"1",
    "title":"test",
    "startDate":"2020-08-10T17:59",
    "endDate":"2021-08-10T17:59"
}

```


responses 200-OK


Response sample:

```

{
         "id": 1,
         "company": {
             "id": 3,
             "name": "nike",
             "email": "nike@gmail.com"
         },
         "title": "test",
         "startDate": "2222-08-10T17:59:00",
         "endDate": "2222-08-10T17:59:00",
         "category": 1,
         "amount": 1,
         "description": "test",
         "price": 1,
         "imgUrl": "test"
}

```

###### EDIT COUPON

to edit existing coupon:

```POST - /admin/update_coupon?token=_ _ _```

in the `body` of your request enter coupon, in JSON format, you wanted to edit:

Request sample:

```

{
    "id":"1"
    "company":{
        "id":""
    },
    "amount":"",
    "category":"",
    "description":"",
    "imgUrl":"",
    "price":"",
    "title":"NEW SALE!!!",
    "startDate":"2020-08-10T17:59",
    "endDate":"2021-08-10T17:59"
}


```

responses 200-OK


Response sample:

```

{
         "id": 1,
         "company": {
             "id": 3,
             "name": "nike",
             "email": "nike@gmail.com"
         },
         "title": "NEW SALE!!!",
         "startDate": "2222-08-10T17:59:00",
         "endDate": "2222-08-10T17:59:00",
         "category": 1,
         "amount": 1,
         "description": "test",
         "price": 1,
         "imgUrl": "test"
}

```


###### DELETE COUPON

if you want to delete a coupon by id:

```GET - /admin/delete_coupon?token=_ _ _&id=_ _ _```

Response sample:

```
     {
         "id": 1,
         "company": {
             "id": 1,
             "name": "nike",
             "email": "nike@gmail.com"
         },
         "title": "second one 50% off",
         "startDate": "2222-08-10T17:59:00",
         "endDate": "2222-08-10T17:59:00",
         "category": 1488,
         "amount": 1488,
         "description": "shoes sale",
         "price": 50.0,
         "imgUrl": "nike.com/photos/9393948"
     }

```
------
**_COMPANY_**
-
###### GET ALL COMPANY COUPONS

to get all company coupons:

```GET - /call_coupons?token=_ _ _ ```

responses 200-OK

Response sample:

```
          {
             "id": 8,
             "company": {
                 "id": 2,
                 "name": "adidas",
                 "email": "adidas@mail.ru"
             },
             "title": "test",
             "startDate": "2021-08-27T02:26:19",
             "endDate": "2020-08-27T02:26:04",
             "category": 1488,
             "amount": 1488,
             "description": "test",
             "price": 0.0,
             "imgUrl": "test"
         },
         {
             "id": 12,
             "company": {
                 "id": 2,
                 "name": "adidas",
                 "email": "adidas@mail.ru"
             },
             "title": "",
             "startDate": "2020-08-10T17:59:00",
             "endDate": "2021-08-10T17:59:00",
             "category": 1488,
             "amount": 1488,
             "description": "testing",
             "price": 14.88,
             "imgUrl": "pozilaya zigotta"
         }

```


###### ADD COUPON

to add a new coupon:

```POST - /add_coupon?token=_ _ _```

in the `body` of your request enter coupon, in JSON format, you wanted to add:

Request sample:

```

{
    "company":{
        "id":"3"
    },
    "amount":"1",
    "category":"1",
    "description":"testing",
    "imgUrl":"test",
    "price":"1",
    "title":"test",
    "startDate":"2020-08-10T17:59",
    "endDate":"2021-08-10T17:59"
}

```


responses 200-OK


Response sample:

```

{
         "id": 1,
         "company": {
             "id": 3,
             "name": "nike",
             "email": "nike@gmail.com"
         },
         "title": "test",
         "startDate": "2222-08-10T17:59:00",
         "endDate": "2222-08-10T17:59:00",
         "category": 1,
         "amount": 1,
         "description": "test",
         "price": 1,
         "imgUrl": "test"
}

```

###### DELETE COUPON

if you want to delete a coupon by id:

```POST - /delete_coupon?token=_ _ _&id=_ _ _```

Response sample:

```
     {
         "id": 1,
         "company": {
             "id": 1,
             "name": "nike",
             "email": "nike@gmail.com"
         },
         "title": "second one 50% off",
         "startDate": "2222-08-10T17:59:00",
         "endDate": "2222-08-10T17:59:00",
         "category": 1488,
         "amount": 1488,
         "description": "shoes sale",
         "price": 50.0,
         "imgUrl": "nike.com/photos/9393948"
     }

```

###### UPDATE COUPON

if you want to update a coupon:

```POST - /update_coupon?token=_ _ _```

in the `body` of your request enter coupon, in JSON format, you wanted to edit:

Request sample:

```

{
    "amount":"",
    "category":"",
    "description":"",
    "imgUrl":"",
    "price":"",
    "title":"NEW SALE!!!",
    "startDate":"2020-08-10T17:59",
    "endDate":"2021-08-10T17:59"
}


```

responses 200-OK


Response sample:

```

{
         "title": "NEW SALE!!!",
         "startDate": "2222-08-10T17:59:00",
         "endDate": "2222-08-10T17:59:00",
         "category": 1,
         "amount": 1,
         "description": "test",
         "price": 1,
         "imgUrl": "test"
}

```

----

**_CUSTOMER_**
-

###### GET ALL CUSTOMER COUPONS

to get all customer coupons:

```GET - /coupons?token=_ _ _```

responses 200-OK

Response sample:

```
     {
         "id": 1,
         "company": {
             "id": 1,
             "name": "nike",
             "email": "nike@gmail.com"
         },
         "title": "second one 50% off",
         "startDate": "2222-08-10T17:59:00",
         "endDate": "2222-08-10T17:59:00",
         "category": 1488,
         "amount": 1488,
         "description": "shoes sale",
         "price": 50.0,
         "imgUrl": "nike.com/photos/9393948"
     },
     {
         "id": 8,
         "company": {
             "id": 2,
             "name": "adidas",
             "email": "adidas@mail.ru"
         },
         "title": "final sale",
         "startDate": "2021-08-27T02:26:19",
         "endDate": "2020-08-27T02:26:04",
         "category": 12,
         "amount": 1000,
         "description": "all for sale 50%",
         "price": 50.0,
         "imgUrl": "adimdas.cam/image/345687"
     }

```

