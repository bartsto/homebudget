# homebudget

### How to launch it locally
This project uses H2 database which is saved into the file in ```data``` 
directory in the project. At the beginning, configuration is set to build whole schema
and insert initial data. You can find it ```application.properties``` file:

`spring.jpa.hibernate.ddl-auto=create`

After first run, if you don't want to lose data, please change ```create``` to ```none```.

##### 1st solution (IDE)
You can run it from your IDE by starting main function. 

##### 2nd solution (terminal)
In the root folder of the project type in 
``mvn spring-boot:run`` 


### H2 Console
If you want, there is a possibility to login to H2 Console.
* run the project
* go to ```http://localhost:8080/h2-console```
* type in JDBC URL ```jdbc:h2:file:./data/homebudgetdb```
* login (without username and password)



### API
At the moment, three endpoints are implemented:
##### /register/recharge
Used to increase balance of chosen register.
How to use it:
* POST 
* localhost:8080/register/recharge
* Request body (application/json):

`{
 	"amount": 2500,
 	"registerId": 1,
 	"userId": 1
 }`
 
##### /register/summary/{userId}
Used to get current balance of all registers.
How to use it:
* GET 
* localhost:8080/register/1
 
##### /transaction/transfer
Used to transfer given amount between two existing registers.
How to use it:
* POST 
* localhost:8080/transaction/transfer
* Request body (application/json):

`{
 	"userId": 1,
 	"amount": 1000,
 	"sourceRegisterId": 1,
 	"destinationRegisterId": 2
 }` 
 