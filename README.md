.
├── backend
│   ├── pom.xml
│   └── src
│       ├── main.java.com.projet.api
│       │   │               ├── ApiApplication.java
│       │   │               ├── config
│       │   │               ├── controller
|                               ├──
│       │   │               ├── exception
│       │   │               ├── mapper
│       │   │               │   ├── CommandeMapper.java
│       │   │               │   ├── MenuMapper.java
│       │   │               │   ├── ReserverMapper.java
│       │   │               │   └── RestaurantTableMapper.java
│       │   │               ├── model
│       │   │               │   ├── Commande.java
│       │   │               │   ├── Menu.java
│       │   │               │   ├── Reserver.java
│       │   │               │   └── RestaurantTable.java
│       │   │               ├── repository
│       │   │               │   ├── CommandeRepository.java
│       │   │               │   ├── MenuRepository.java
│       │   │               │   ├── ReserverRepository.java
│       │   │               │   └── RestaurantTableRepository.java
│       │   │               └── service
│       │   │                   └── impl
│       │   └── resources
│       └── test.java.com.projet.api
│                           ├── controller
│                           └── service
├── common-dto
│   ├── pom.xml
│   └── src.main.java.com.projet.common.dto
│                               ├── CommandeDTO.java
│                               ├── MenuDTO.java
│                               ├── ReserverDTO.java
│                               └── TableDTO.java
├── docker
│   ├── dataExample.sql
│   ├── docker-compose.yml
│   ├── init.sql
│   └── postgres
├── frontend
│   ├── pom.xml
│   └── src.main.java.com.projet.ui
│           │               ├── config
│           │               ├── exception
│           │               ├── navigation
│           │               ├── services
│           │               ├── view
│           │               └── viewmodel
│           └── resources
│               ├── css
│               ├── fxml
│               └── images
└── pom.xml
