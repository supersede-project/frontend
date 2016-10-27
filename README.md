<link rel="shortcut icon" type="image/png" href="images/favicon.png">
# [![SUPERSEDE](images/SUPERSEDE-logo.png)](https://www.supersede.eu/) project: Front-End (FE)
This repository contains the code developed in SUPERSEDE WP5, which provides
the interface between the aggregated SUPERSEDE components and the end-users.

See deliverable D5.5 in [Supersede Portal](https://www.supersede.eu/) for more details about this interface,
including its funcional description, its architecture and its components.

Repository Structure:
- **applications/supersede-frontend-core**: contains some services that are used by the main FE module
- **applications/frontend**: contains the actual implementation of the SUPERSEDE frontend
- **applications/supersede-client**: contains support classes that serve as base for the implementation of
additional micro-services by the other WPs
- **applications/admin-user-manager-app**: contains an application that allows to manage FE users with a
multi-tenancy mechanism
- **applications/redis-sessions-inspector**: contains an application that allows to obtain information
about the current session in the FE
- **conf**: contains some configuration templates for Spring applications, the httpd service and the PostgreSQL database
- **documents**: contains the installation manual and the developer manual
- **licenses**: contains the licenses used by the FE

SUPERSEDE Front-End is licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)

*Please check the respective README.md for details.*

Main contact: Mauro Fruet <mauro.fruet@deltainformatica.eu>

<center>![Project funded by the European Union](images/european.union.logo.png)</center>