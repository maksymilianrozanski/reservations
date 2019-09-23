# reservations
API allowing adding reservations by the admin and booking them by users. Project is using Kotlin, Spring Boot.
Require setting empty database with name and credentials matching application.properties file

example http requests (triple quotes in json required by Windows):

Logging in - admin (getting cookie)
curl -i -X POST -d username=admin -d password=pass -c cookies.txt http://localhost:8080/login

Logging in - user (getting cookie)
curl -i -X POST -d username=user1 -d password=pass -c cookies.txt http://localhost:8080/login

Getting user roles
curl -i -b cookies.txt http://localhost:8080/api/roles

Getting all reservations (admin only)
curl -i  -b cookies.txt http://localhost:8080/api/reservations

Getting unoccupied reservations (by user)
curl -i  -b cookies.txt http://localhost:8080/api/reservations

Adding new reservation (admin only)
curl -i -X POST --data {"""reservationId""":0,"""title""":"""Example title""","""description""":"""Some description""","""user""":null,"""start""":"""2019-01-01 12:00:00""","""end""":"""2019-01-01 13:00:00"""} -b cookies.txt --header "Content-Type: application/json"  http://localhost:8080/api/reservations

Deleting reservation (admin only)
curl -i -X DELETE -b cookies.txt http://localhost:8080/api/reservations/10

Editing reservation (admin only)
curl -i -X PUT -b cookies.txt --data {"""reservationId""":10,"""title""":"""Doctor Brown: edited title""","""description""":"""Two hours long reservation.""","""user""":{"""userId""":2},"""start""":"""2019-09-02 14:58:48""","""end""":"""2019-09-02 16:58:48"""} --header "Content-Type: application/json" http://localhost:8080/api/reservations

Booking reservation (by user)
curl -i -X PUT -b cookies.txt --data {"""reservationId""":11} --header "Content-Type: application/json" http://localhost:8080/api/reservations
