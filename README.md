````
.
├── backend
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── projet
│       │   │           └── api
│       │   │               ├── ApiApplication.java
│       │   │               ├── config
│       │   │               ├── controller
│       │   │               │   ├── CommandeController.java
│       │   │               │   ├── LigneCommandeController.java
│       │   │               │   ├── MenuController.java
│       │   │               │   ├── ReserverController.java
│       │   │               │   └── RestaurantTableController.java
│       │   │               ├── exception
│       │   │               │   └── GlobalExceptionHandler.java
│       │   │               ├── mapper
│       │   │               │   ├── CommandeMapper.java
│       │   │               │   ├── LigneCommandeMapper.java
│       │   │               │   ├── MenuMapper.java
│       │   │               │   ├── ReserverMapper.java
│       │   │               │   └── RestaurantTableMapper.java
│       │   │               ├── model
│       │   │               │   ├── Commande.java
│       │   │               │   ├── LigneCommande.java
│       │   │               │   ├── Menu.java
│       │   │               │   ├── Reserver.java
│       │   │               │   └── RestaurantTable.java
│       │   │               ├── repository
│       │   │               │   ├── CommandeRepository.java
│       │   │               │   ├── LigneCommandeRepository.java
│       │   │               │   ├── MenuRepository.java
│       │   │               │   ├── ReserverRepository.java
│       │   │               │   └── RestaurantTableRepository.java
│       │   │               └── service
│       │   │                   ├── CommandeService.java
│       │   │                   ├── impl
│       │   │                   │   ├── CommandeServiceImpl.java
│       │   │                   │   ├── LigneCommandeServiceImpl.java
│       │   │                   │   ├── MenuServiceImpl.java
│       │   │                   │   ├── ReserverServiceImpl.java
│       │   │                   │   └── RestaurantTableServiceImpl.java
│       │   │                   ├── LigneCommandeService.java
│       │   │                   ├── MenuService.java
│       │   │                   ├── ReserverService.java
│       │   │                   ├── RestaurantTableService.java
│       │   │                   └── service.java
│       │   └── resources
│       │       ├── application.properties
│       │       └── static
│       └── test
│           └── java
│               └── com
│                   └── projet
│                       └── api
│                           ├── controller
│                           └── service
├── common-dto
│   ├── pom.xml
│   └── src
│       └── main
│           └── java
│               └── com
│                   └── projet
│                       └── common
│                           └── dto
│                               ├── CommandeDTO.java
│                               ├── LigneCommandeDTO.java
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
│   └── src
│       └── main
│           ├── java
│           │   └── com
│           │       └── projet
│           │           └── ui
│           │               ├── App.java
│           │               ├── config
│           │               │   ├── ApiConfig.java
│           │               │   └── AppContext.java
│           │               ├── controller
│           │               │   ├── CommandeController.java
│           │               │   ├── MainLayoutController.java
│           │               │   ├── MenuController.java
│           │               │   ├── ReserverController.java
│           │               │   └── TableController.java
│           │               ├── exception
│           │               ├── navigation
│           │               │   └── NavigationManager.java
│           │               ├── services
│           │               │   ├── ApiClient.java
│           │               │   ├── CommandeService.java
│           │               │   ├── MenuService.java
│           │               │   ├── ReserverService.java
│           │               │   └── TableService.java
│           │               ├── view
│           │               │   ├── CommandeView.java
│           │               │   ├── MenuView.java
│           │               │   ├── ReserverView.java
│           │               │   └── TableView.java
│           │               └── viewmodel
│           │                   ├── CommandeViewModel.java
│           │                   ├── MainViewModel.java
│           │                   ├── MenuViewModel.java
│           │                   ├── ReserverViewModel.java
│           │                   └── TableViewModel.java
│           └── resources
│               ├── css
│               │   └── style.css
│               ├── fxml
│               │   ├── commande-view.fxml
│               │   ├── dashboard-view.fxml
│               │   ├── main-layout.fxml
│               │   ├── menu-view.fxml
│               │   ├── reserver-view.fxml
│               │   └── table-view.fxml
│               └── images
├── init.sh
├── pom.xml
├── README.md
└── run.sh

53 directories, 79 files
