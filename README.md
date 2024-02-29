# Travel-Planner
 School project description:

This API is developed as part of a school project and simulates a public transport application. the destinations used are predefined and fictitious.

**GET method**
/api / v1 / publicTransportation / allTransportation**:
   - This endpoint returns information about all available public transport routes.
  This endpoint is used to retrieve information about all available public transport routes in the system.

**GET method**
/api / v1/publicTransportation/searchForTransportation / {from}/{to}/{travelTime}**:
This endpoint searches for public transport routes based on the specified start and end destinations as well as the desired travel time. This endpoint is used to search for public transport routes from a predefined starting point to a predefined final destination at a specific time.
Prededifned station names
GARDEN_MALL
Edu_station
UNIVERSITY_SCHOOL
Lib_station
Evil_street_station
POLICE_STATION
Blue_street_station
Out_station


**GET method**
/ api / v1 / publicTransportation / favoriteTransportation**:
 This endpoint retrieves information about the user's favorite collective traffic routes.
Use this endpoint to retrieve information about the public transport routes that the user has marked as favorites.

**PATCH method**
/api/v1/publicTransportation / {today
 This endpoint changes the status of a favorite collections This endpoint is used to change the status of a specific public transport route to favorite or not favorite.

**POST-method**
/api/v1/publicTransportation/reportIssue / {id}**:
This endpoint reports an issue or an expected delay for a public transit route. This endpoint is used to report a problem or expected delay for a specific public transport route.

**GET method** 
/ api / v1 / publicTransportation/dilationAndIssues**:
This endpoint retrieves information about delays and problems for public transport routes.
 This endpoint is used to retrieve information about expected delays and reported problems for public transit routes.


