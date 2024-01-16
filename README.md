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
- ProductController: Jayden
- ProjectController: Nizar
- TeamController: Joey
- TransactionController:
- UserController: Kaifie, Johnny, Junior
- WarehouseController: Joey

models:
- dto:  
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
- Team: Joey
- Transaction: Junior
- User: Kaifie
- Warehouse: Joey

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
- AuthenticationService: Johnny, Nizar
- CapacityService: Joey
- ChartService: Junior
- EmailService: Joey
- InventoryService: Junior
- OrderService: Junior
- ProductService: Jayden
- ProjectService: Nizar
- TeamService: Joey
- TransactionService: Junior
- UserService: Johnny, Kaifie
- WarehouseService: Joey

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
- product: Jayden
- project: Nizar
- team: Joey, Junior
- user: Johnny, Kaifie
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
- ProductCategory:
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
- ProductAdaptor: Jayden
- ProjectAdaptor: Nizar
- TeamsAdaptor: Joey
- TransactionsAdaptor: Junior
- UserAdaptor: Johnny, Kaifie
- ValidationRules: Junior
- WarehousesAdaptor: Joey

frontend/src/views/...

admin:
- DashboardView: Junior
- InventoriesView: Junior
- OrdersView: Junior
- ProductsView: Jayden
- ProjectsView: Nizar
- TeamsView: Joey, Junior
- UsersView: Kaifie
- WarehousesView: Joey, Junior

user:
- projects
  - ProjectsComponent: Nizar
