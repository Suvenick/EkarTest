Run system:

docker-compose up

open project in IDE and run main class:
com.ekar.Main

ENDPOINTS(Use curl/postman/insomnia):

1. GET localhost:8080/get 
Read actual value from system

2.POST localhost:8080/create?value=90
Set new value for shared variable

3.POST localhost:8080/increase?consumer=1&producer=1
Create new producers/consumers

 
