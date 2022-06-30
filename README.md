# Getting Started

Launch Program:

1. Check that you have installed MySQL 8 with innodb on port 3306
2. Run in MySQL db.sql file which located src/main/resources/db/db.sql (It will crete DB + user)
3. Just run the program. Flyway will create table from src/main/resources/db/migration/V1__init.sql file

# API DOCS

    # Product APi
    1.  GET - /product | Search with params and pagination:
        a. No params- will return all from DB depends on Limit and Page
        b. "title" - LikeIgnoreCase search by title param
        c. "price" - Equal search by price
    2. POST /product | Create new product:
        Example of request body: 
                {
                    "title" : "Test Product",
                    "price" : 12.99
                }
    3. PATCH /product/{productId} | Partial update of entity
        a. You can send "title" or/and "price"
        b. Example : 
            {
               "title": "Real Product"
            }
    4. DELETE /product/{productId} | Remove product from DB

    #Cart API

     1. GET /cart/product | list all in session Cart - will return all cartItems which contains Product and quantities of
        products
     2. POST /cart/product/{productId} | Add product to the Cart - will add product by id to the Cart. If we have already the
      same product in Cart it will increase quantity value, if not - create new Item and put quantity as 1
     3. DELETE /cart/product/{productId} | Remove Product by id from Cart. If It's more than 1 - will decrease count
     4. POST /cart | Will create Initial Cart in Session



 
