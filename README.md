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
- AuthenticationController:
- ChartController:
- EmailController: Johnny, Joey, Kaifie
- InventoryController:
- OrderController:
- ProductController: Jayden
- ProjectController:
- TeamController:
- TransactionController:
- UserController: Kaifie, Johnny, Junior
- WarehouseController:

models:
- dto:  
  - InventoryProjectDTO:
  - TaskDTO:
- Capacity:
- InventoryOrder:
- JWToken:
- Notification:
- Product: Jayden
- ProductCategory:
- Project:
- Task:
- Team:
- Transaction:
- User:
- Warehouse:

repositories:
- CapacityRepository:
- OrderRepository:
- ProductCategoryRepository:
- ProductRepository: Jayden
- ProjectRepository:
- TaskRepository:
- TeamRepository:
- TransactionRepository:
- UserRepository: Johnny
- WarehouseRepository:

services:
- AuthenticationService: Johnny, Nizar
- CapacityService:
- ChartService:
- EmailService:
- InventoryService:
- OrderService:
- ProductService: Jayden
- ProjectService:
- TeamService:
- TransactionService:
- UserService: Johnny
- WarehouseService:

utils:
- DTO:
- Model:

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
- charts: 
- inventory:
- order:
- product: Jayden
- project:
- team:
- user: Johnny
- warehouses:
- ErrorComponent: Junior, Johnny
- NavbarComponent: Junior, Nizar & Jayden

models:
- charts: Junior
- InventoryProduct:
- InventoryProject:
- Model:
- Order:
- Product: Jayden
- ProductCategory:
- ProductLine:
- Project:
- Task:
- Team:
- Transaction:
- User: Johnny, Kaifie
- Warehouse:

plugins, router: Everyone

services:
- Adaptor:
- AuthenticationAdaptor:
- ChartsAdaptor:
- EmailAdaptor: Johnny
- FetchInterceptor: Johnny
- InventoryAdaptor:
- OrderAdaptor:
- ProductAdaptor: Jayden
- ProjectAdaptor:
- TeamsAdaptor:
- TransactionsAdaptor:
- UserAdaptor: Johnny
- ValidationRules:
- WarehousesAdaptor:

frontend/src/views/...

admin:
- DashboardView: Junior
- InventoriesView:
- OrdersView:
- ProductsView: Jayden
- ProjectsView:
- TeamsView:
- UsersView:
- WarehousesView:

user:
- projects
  - ProjectsComponent: Nizar
