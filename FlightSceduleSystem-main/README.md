# Flight Scheduling System
Guide to build and deploy project

---
### Prerequisites  📝
- An IDE (IntelliJ/MyEclipse) should be isnatlled on your machine.
- GIT(with bundled GIT Bash) should be installed on your machine
- Credentials should be configured in GIT Bash
- You should have db visulizer installed with its informix driver.
-
---
### Steps
- Clone this repository in separate directory.
- Execute the scripts/DDLs.sql using command line or db visulizer for creations of tables.
- Execute the scripts/DMLs.sql for dummy data insertions.
- Open project as Java Project in your IDE.
- Change parameters values in src/resources/dbconfig.xml with your respective connection string and credentials.
- Run / Debug Project.

> **Note:**
>
> For login, use admin user with its password admin.
> This module focused on admin side menus of flight scheduling system.
> Customer side menus will be developed in next releases.
> 
> 
---
### Entity-Relationship Diagram
![Screenshot](https://github.com/Iqra5545/FlightSceduleSystem/blob/main/Screenshots/ERD.png)
---
