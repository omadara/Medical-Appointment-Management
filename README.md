## Medical-Appointment-Management
This is a servlet website that lets patients and doctors schedule appopintments, managing their time schedule, cancel them etc.
### Screenshots
![A screenshot was supposed to be here.](/screenshot1.PNG)
![A screenshot was supposed to be here.](/screenshot2.PNG)
### How to use
- Make a Postgresql database and then initiliaze it with the [sql code.](https://github.com/MadProgrammerGR/Medical-Appointment-Management/blob/master/db.sql)
- Download [Postgresql jar](https://jdbc.postgresql.org/download.html) and drop it in your Tomcat's lib folder.
- Add the the following to your Tomcat's conf/context.xml file filling in user/pass/dbname respectively:
```
<Resource name="jdbc/postgres" auth="Container"
    driverClassName="org.postgresql.Driver"
    type="javax.sql.DataSource"
    username="XXXXXXXXX"
    password="XXXXXXXXX"
    url="jdbc:postgresql://127.0.0.1:5432/XXXXXXXXX"
    maxActive="8" />
```
- Clone and then import the project on eclipse.
- Done!
