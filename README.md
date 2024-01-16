# Solar Sedum EWA X
### Introduction
This an application designed for the Solar Sedum team to efficiently manage inventory, projects, teams, and more. 
This guide provides instructions for setting up and running the application locally.

### Technologies Used
- Vue.js: A progressive JavaScript framework for building user interfaces.
- Spring Boot: A Java-based framework for building stand-alone, production-grade Spring-based applications.
- Vuetify: A material design component framework for Vue.js.

## Getting Started

### Prerequisites

1. First Clone the Repository:
```bash
git clone https://gitlab.fdmci.hva.nl/se-ewa/2023-2024-1/solar-3.git
```

2. Next go over the [Frontend Setup](/frontend/README.md)

3. Finally go over the [Backend Setup](/backend/README.md)

After setting up both the frontend and backend, the Solar Sedum EWA X application should be accessible 
through a web browser for frontend interactions and an IDE for backend management.

## Techno
All classes and who made them :D

### Backend

backend/...

- Dockerfile: Jayden
- README: Jayden

backend/src/main/java/teamx/app/backend/...

controllers:
- AuthenticationController: Nizar
- ChartController: Junior
- EmailController: Johnny, Joey, Kaifie
- InventoryController: Junior
- OrderController: Junior
- ProductController: Jayden, Junior
- ProjectController: Nizar, Junior
- TeamController: Joey, Junior
- TransactionController:Junior
- UserController: Kaifie, Johnny, Junior
- WarehouseController: Junior, Joey

models:
- dto:  Junior
  - InventoryProjectDTO: Nizar
  - TaskDTO: Nizar
- Capacity: Junior
- InventoryOrder: Junior
- JWToken: Nizar
- Notification:
- Product: Jayden
- ProductCategory: Junior
- Project: Nizar
- Task: Junior
- Team: Joey, Junior
- Transaction: Junior
- User: Kaifie, Junior
- Warehouse: Junior, Joey

repositories:
- CapacityRepository: Joey
- OrderRepository: Junior
- ProductCategoryRepository: Junior
- ProductRepository: Jayden
- ProjectRepository: Nizar
- TaskRepository: Junior
- TeamRepository: Joey
- TransactionRepository: Junior
- UserRepository: Johnny, Kaifie
- WarehouseRepository: Joey

services:
- AuthenticationService: Johnny, Nizar, Junior
- CapacityService: Joey, Junior
- ChartService: Junior
- EmailService: Joey
- InventoryService: Junior
- OrderService: Junior
- ProductService: Jayden, Junior
- ProjectService: Nizar, Junior
- TeamService: Joey, Junior
- TransactionService: Junior
- UserService: Johnny, Kaifie
- WarehouseService: Junior

utils:
- DTO: Junior
- Model: Junior

src/main/...

resources:

- templates: Joey
- data.sql: Junior

### Frontend

frontend/...

- Dockerfile: Jayden
- README: Jayden

frontend/src/...

components:
- base: Junior
- charts: Junior
- inventory: Junior
- order: Junior
- product: Jayden, Junior
- project: Nizar, Junior
- team: Joey, Junior
- user: Johnny, Kaifie, Junior
- warehouses: Joey, Junior
- ErrorComponent: Junior, Johnny
- NavbarComponent: Junior, Nizar & Jayden

models:
- charts: Junior
- InventoryProduct: Junior
- InventoryProject: Nizar
- Model: Junior
- Order: Junior
- Product: Jayden
- ProductCategory: Junior
- ProductLine: Junior
- Project: Nizar
- Task: Junior
- Team: Joey
- Transaction:
- User: Johnny, Kaifie
- Warehouse: Joey

plugins, router: Everyone

services:
- Adaptor: Junior
- AuthenticationAdaptor: Nizar
- ChartsAdaptor: Junior
- EmailAdaptor: Johnny
- FetchInterceptor: Johnny
- InventoryAdaptor: Junior
- OrderAdaptor: Junior
- ProductAdaptor: Jayden, Junior
- ProjectAdaptor: Nizar, Junior
- TeamsAdaptor: Joey, Junior
- TransactionsAdaptor: Junior
- UserAdaptor: Johnny, Kaifie, Junior
- ValidationRules: Junior
- WarehousesAdaptor: Joey, Junior

frontend/src/views/...

admin:
- DashboardView: Junior
- InventoriesView: Junior
- OrdersView: Junior
- ProductsView: Jayden, Junior
- ProjectsView: Nizar, Junior
- TeamsView: Joey, Junior
- UsersView: Kaifie, Junior
- WarehousesView: Joey, Junior

user:
- projects
  - ProjectsComponent: Nizar
