To run application : 
You must have installed docker. All dependencies will be installed after running following commands
1.In terminal run : "mvn clean install -P dockerize"
2.In terminal run : "docker-compose -f compose.yaml up"

In path "src/main/resources/xmlDirectory" you have folders Internal and External where are xml test data.
I made application that allow to work on xml dataset and also sql database.
If you work on xml files and want to load new users to database run "/api/xml/load" endpoint via postman.

While you working at xml files new files will be created in docker filesystem ("api/xml/person")
While you working on sql db new records will be added to postgres database ("api/sql/person")

App also contains Api and prepared postman collection is in "/postman"

