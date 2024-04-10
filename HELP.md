# Getting Started

### Build and Run
run 
`chmod +x build_and_deploy.sh`
Followed by
`./build_and_deploy.sh`

This will build the project and run the application on port 8080
along with the database on port 5432 and rabbitmq on port 5672.

I have handled base case scenarios for the application as mentioned in the problem statement.

RabbitMQ is used for messaging between the services. And Listener is implemented in the application itself to listen to the messages.


I have also added a few test cases to test the application, but I have not added all the test cases as I am running out of time.
Currently the tests are failing for some configuration issues.
