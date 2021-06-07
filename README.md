# Notes

Webservice for managing and storing notes, with UI

Requirements:
-Java 11 -PostgreSQL

Setting up the database:

1. Connect to psql.
2. Create new database or use the default one (postgres).
3. Change the properties* to match postgres settings (database name, username and password).
4. You might also have to add your postgreSQL database in 'database' panel in your IDE.

*Database properties are located in src/main/resources/application.properties Note that '
spring.jpa.hibernate.ddl-auto=create-drop' will reset your database on every project startup.

Building and running the project:

1. Run NotesBackendApplication.java

Example usages:

-UI with list of notes
http://localhost:8080/notes

Example cURL commands:

-Show all notes

curl --location --request GET 'localhost:8080/notes/all'

-Add note

curl --location --request POST 'http://localhost:8080/notes/add' \
--header 'Content-Type: application/json' \
--data-raw '{
"title": "I'\''m a simple note, click me!",
"content": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis cursus interdum augue eget faucibus. Morbi
eleifend pellentesque ipsum, ac venenatis tellus bibendum sed."
}'

-Update note

curl --location --request PUT 'http://localhost:8080/notes/1' \
--header 'Content-Type: application/json' \
--data-raw '{
"title": "Here is something about wombats",
"content": "Wombats have an extraordinarily slow metabolism, taking around 8 to 14 days to complete digestion, which
aids their survival in arid conditions."
}'

-Delete note

curl --location --request DELETE 'http://localhost:8080/notes/1'

-Get note history

curl --location --request GET 'http://localhost:8080/notes/history/1'