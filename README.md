# To-Do List — Sistema de Gestión de Tareas

---

## Índice

- [Descripción General](#descripción-general)
- [Requisitos Previos](#requisitos-previos)
- [Configuración y Ejecución](#configuración-y-ejecución)
    - [Clonar el repositorio](#1-clonar-el-repositorio)
    - [Levantar la base de datos](#2-levantar-la-base-de-datos)
    - [Verificar la conexión](#3-verificar-la-conexión)
    - [Compilar](#4-compilar)
    - [Ejecutar](#5-ejecutar)
- [Credenciales del Usuario Sistema](#credenciales-del-usuario-sistema)
- [Requerimientos de Contraseña](#requerimientos-de-contraseña)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Módulos del Proyecto](#módulos-del-proyecto)
    - [Config — ConnectionSQL.java](#config--connectionsqljava)
    - [Utils — PasswordHasher.java](#utils--passwordhasherjava)
    - [Utils — SecurePasswordReader.java](#utils--securepasswordreaderjava)
    - [Models — org/models](#models--orgmodels)
    - [Enums — Status.java](#enums--statusjava)
    - [Repository — org/repository](#repository--orgrepository)
    - [Controllers — org/controllers](#controllers--orgcontrollers)
    - [Views — org/views](#views--orgviews)
- [Flujo de Trabajo MVC](#flujo-de-trabajo-mvc)
- [Base de Datos](#base-de-datos)

---

## English

- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Setup and Execution](#setup-and-execution)
    - [Clone the repository](#1-clone-the-repository)
    - [Start the database](#2-start-the-database)
    - [Verify the connection](#3-verify-the-connection)
    - [Build](#4-build)
    - [Run](#5-run)
- [System User Credentials](#system-user-credentials)
- [Password Requirements](#password-requirements)
- [Project Structure](#project-structure)
- [Project Modules](#project-modules)
    - [Config — ConnectionSQL.java](#config--connectionsqljava-1)
    - [Utils — PasswordHasher.java](#utils--passwordhasherjava-1)
    - [Utils — SecurePasswordReader.java](#utils--securepasswordreaderjava-1)
    - [Models — org/models](#models--orgmodels-1)
    - [Enums — Status.java](#enums--statusjava-1)
    - [Repository — org/repository](#repository--orgrepository-1)
    - [Controllers — org/controllers](#controllers--orgcontrollers-1)
    - [Views — org/views](#views--orgviews-1)
- [MVC Workflow](#mvc-workflow)
- [Database](#database)

---

### Descripción General

Aplicación de consola (CLI) para la gestión de tareas pendientes, desarrollada en Java con persistencia en MySQL. Implementa arquitectura MVC, permite organizar tareas por categorías y espacios de trabajo, y protege el acceso mediante autenticación con contraseñas encriptadas en SHA-256.

---

### Requisitos Previos

- Java JDK 22 o superior
- Maven 3.6 o superior
- Docker y Docker Compose

---

### Configuración y Ejecución

**1. Clonar el repositorio**

```bash
git clone https://github.com/AkemiAyca-1/DataBase-2
cd DataBase-2
```

**2. Levantar la base de datos**

```bash
docker compose up -d
```

Esto inicia un contenedor MySQL 8.0 y ejecuta automáticamente `mysql/init.sql`, que crea la base de datos, tablas, vistas, procedimientos, trigger e índices, e inserta el usuario sistema y la categoría "General" por defecto.

**3. Verificar la conexión**

La clase `ConnectionSQL` en `src/main/java/org/Config/` se conecta a `localhost:3306` con usuario `root` y contraseña `Hola123!`. Si tu entorno difiere, edita esos valores antes de compilar.

**4. Compilar**

```bash
mvn clean package -DskipTests
```

Genera el JAR en `target/to-do-list-1.0-SNAPSHOT.jar` con todas las dependencias incluidas.

**5. Ejecutar**

```bash
java -jar target/to-do-list-1.0-SNAPSHOT.jar
```

Ejecutar desde la terminal (no desde el IDE) es necesario para que el ocultamiento de contraseña funcione correctamente.

---

### Credenciales del Usuario Sistema

Al inicializar la base de datos se crea un usuario de sistema con el que puedes hacer login inmediatamente:

```
Username: system
Password: !SystemS12!
```

Este usuario existe para que la categoría "General" por defecto tenga un propietario válido en la base de datos.

---

### Requerimientos de Contraseña

Al registrar un usuario o actualizar uno existente, la contraseña debe cumplir:

- Minimo 6 caracteres
- Al menos 2 letras mayúsculas
- Al menos 2 letras minúsculas
- Al menos 2 caracteres especiales (`!@#$%^&*` etc.)
- Al menos 2 números

---

### Estructura del Proyecto

```
DataBase-2/
├── docker-compose.yml
├── mysql/
│   └── init.sql                   # Script de inicialización de la BD
├── pom.xml
└── src/main/java/org/
    ├── App.java                   # Punto de entrada
    ├── Config/
    │   └── ConnectionSQL.java     # Conexión JDBC a MySQL
    ├── ValidateException/
    │   └── ValidationException.java
    ├── controllers/               # Capa de control (C en MVC)
    ├── enums/
    │   └── Status.java            # Estados de una tarea
    ├── models/                    # Capa de modelo (M en MVC)
    ├── repository/                # Acceso a datos (parte del Modelo)
    ├── utils/
    │   ├── PasswordHasher.java    # Encriptación SHA-256
    │   └── SecurePasswordReader.java  # Lectura oculta de contraseña
    └── views/                     # Capa de vista (V en MVC)
```

---

### Módulos del Proyecto

**Config — `ConnectionSQL.java`**

Establece la conexión JDBC con MySQL. Se instancia una sola vez en `App.java` y se pasa a todos los repositorios. Si la conexión falla, la aplicación informa el error en consola pero no se cierra abruptamente.

**Utils — `PasswordHasher.java`**

Convierte cualquier contraseña en su representación SHA-256 en formato hexadecimal. Se llama al guardar y al verificar credenciales, de modo que la contraseña en texto plano nunca llega a la base de datos.

**Utils — `SecurePasswordReader.java`**

Lee la contraseña desde la terminal sin mostrarla en pantalla. Intenta primero `System.console()` (disponible al ejecutar con `java -jar`), luego usa `stty` sobre `/dev/tty` como alternativa para entornos donde la consola no está disponible (como Maven), y como último recurso muestra la contraseña visiblemente con una advertencia. También contiene los métodos de validación de formato de contraseña y correo electrónico.

**Models — `org/models/`**

Clases POJO que representan las entidades del dominio: `User` (abstracta), `RegularUser`, `AdminUser`, `Category`, `Workspace`, `Task`, `TaskComment`, `Role`, `WorkspaceSummary` y `TaskDashboardRow`. No contienen lógica de negocio ni acceso a datos.

**Enums — `Status.java`**

Define los cuatro estados posibles de una tarea: `PENDING`, `IN_PROGRESS`, `COMPLETED` y `CANCELLED`. El método `fromDbValue` permite reconstruir el enum a partir del string que devuelve MySQL.

**Repository — `org/repository/`**

Cada clase maneja las queries SQL de una entidad usando `PreparedStatement`. Reciben la conexión por constructor y lanzan `SQLException` hacia arriba para que el controller decida cómo manejarlo. Los repositorios también incluyen métodos de existencia (`existsWorkspace`, `existsUserWorkspace`) usados para validar FKs antes de insertar.

**Controllers — `org/controllers/`**

Orquestan el flujo entre la vista y el repositorio. Validan IDs, verifican existencia de entidades referenciadas, manejan la lógica de la categoría "General" por defecto, y capturan excepciones con mensajes amigables. `UserController` es abstracta; `AdminUserController` y `RegularUserController` la extienden.

**Views — `org/views/`**

Manejan exclusivamente la entrada y salida en consola. No toman decisiones de negocio: piden datos al usuario, los devuelven al controller, y muestran los resultados que el controller les pasa. `Menu.java` es el punto de entrada de la interfaz y es responsable de instanciar todos los controllers, repositorios y vistas.

---

### Flujo de Trabajo MVC

El proyecto sigue el patrón MVC de forma estricta, donde cada capa tiene una responsabilidad única:

```
Usuario escribe en consola
        |
        v
   [View] askTaskData()         <- Solicita datos, valida formato básico
        |
        v
   [Controller] create()        <- Orquesta: valida existencia de FKs,
        |                          decide categoría por defecto,
        |                          llama al repositorio
        v
   [Repository] save()          <- Ejecuta el INSERT con PreparedStatement
        |
        v
   [Base de Datos] MySQL         <- Trigger before_task_insert valida título
        |                          y asigna fecha si es nula
        v
   [Controller]                 <- Recibe el objeto guardado o la excepción
        |
        v
   [View] showSuccess() / showError()  <- Muestra resultado al usuario
```

Este flujo aplica a todas las operaciones CRUD. La vista nunca accede al repositorio directamente, y el repositorio nunca conoce la vista. El controller es el único que conoce ambas capas.

---

### Base de Datos

**Tablas:** `user`, `user_roles`, `roles_and_users`, `category`, `workspace`, `user_workspace`, `task`, `task_comment`

**Vistas:**
- `task_dashboard` — tareas con su categoría y usuario responsable
- `comment_details` — comentarios con tarea y autor
- `workspace_summary` — conteo de tareas por estado para cada workspace
- `usuariosConRoles` — usuarios con su rol asignado

**Procedimiento:** `get_pending_tasks(workspace_name)` — retorna el conteo de tareas no completadas por usuario en un workspace dado.

**Trigger:** `before_task_insert` — rechaza inserciones con título vacío y asigna `created_at` automáticamente si no se provee.

**Índices:** `idx_user_name` sobre `user(name)`, `idx_task_title` sobre `task(title)`

---
---

## English

### Overview

A command-line (CLI) task management application built in Java with MySQL persistence. It implements MVC architecture, allows organizing tasks by categories and workspaces, and protects access through authentication with SHA-256 encrypted passwords.

---

### Prerequisites

- Java JDK 15 or higher
- Maven 3.6 or higher
- Docker and Docker Compose

---

### Setup and Execution

**1. Clone the repository**

```bash
git clone https://github.com/AkemiAyca-1/DataBase-2
cd DataBase-2
```

**2. Start the database**

```bash
docker compose up -d
```

This starts a MySQL 8.0 container and automatically runs `mysql/init.sql`, which creates the database, tables, views, stored procedures, trigger, indexes, and inserts the system user and the default "General" category.

**3. Verify the connection**

The `ConnectionSQL` class in `src/main/java/org/Config/` connects to `localhost:3306` with username `root` and password `Hola123!`. If your environment differs, edit those values before building.

**4. Build**

```bash
mvn clean package -DskipTests
```

Generates the JAR at `target/to-do-list-1.0-SNAPSHOT.jar` with all dependencies bundled.

**5. Run**

```bash
java -jar target/to-do-list-1.0-SNAPSHOT.jar
```

Running from the terminal (not from the IDE) is required for password hiding to work correctly.

---

### System User Credentials

The database initialization creates a system user you can log in with immediately:

```
Username: system
Password: !SystemS12!
```

This user exists so the default "General" category has a valid owner in the database.

---

### Password Requirements

When registering or updating a user, the password must meet:

- At least 6 characters
- At least 2 uppercase letters
- At least 2 lowercase letters
- At least 2 special characters (`!@#$%^&*` etc.)
- At least 2 digits

---

### Project Structure

```
DataBase-2/
├── docker-compose.yml
├── mysql/
│   └── init.sql                   # Database initialization script
├── pom.xml
└── src/main/java/org/
    ├── App.java                   # Entry point
    ├── Config/
    │   └── ConnectionSQL.java     # JDBC connection to MySQL
    ├── ValidateException/
    │   └── ValidationException.java
    ├── controllers/               # Controller layer (C in MVC)
    ├── enums/
    │   └── Status.java            # Task status values
    ├── models/                    # Model layer (M in MVC)
    ├── repository/                # Data access (part of the Model)
    ├── utils/
    │   ├── PasswordHasher.java    # SHA-256 encryption
    │   └── SecurePasswordReader.java  # Hidden password input
    └── views/                     # View layer (V in MVC)
```

---

### Project Modules

**Config — `ConnectionSQL.java`**

Establishes the JDBC connection with MySQL. Instantiated once in `App.java` and passed to all repositories. If the connection fails, the application prints the error to the console without crashing abruptly.

**Utils — `PasswordHasher.java`**

Converts any password into its SHA-256 hexadecimal representation. Called both when saving and when verifying credentials, ensuring the plaintext password never reaches the database.

**Utils — `SecurePasswordReader.java`**

Reads the password from the terminal without displaying it on screen. It first tries `System.console()` (available when running with `java -jar`), then uses `stty` on `/dev/tty` as an alternative for environments where the console is unavailable (such as Maven), and as a last resort displays the password visibly with a warning. Also contains the validation methods for password and email format.

**Models — `org/models/`**

POJO classes representing the domain entities: `User` (abstract), `RegularUser`, `AdminUser`, `Category`, `Workspace`, `Task`, `TaskComment`, `Role`, `WorkspaceSummary`, and `TaskDashboardRow`. They contain no business logic or data access.

**Enums — `Status.java`**

Defines the four possible task states: `PENDING`, `IN_PROGRESS`, `COMPLETED`, and `CANCELLED`. The `fromDbValue` method reconstructs the enum from the string returned by MySQL.

**Repository — `org/repository/`**

Each class handles the SQL queries for one entity using `PreparedStatement`. They receive the connection via constructor and throw `SQLException` upward so the controller can decide how to handle it. Repositories also include existence-check methods (`existsWorkspace`, `existsUserWorkspace`) used to validate foreign keys before inserting.

**Controllers — `org/controllers/`**

Orchestrate the flow between the view and the repository. They validate IDs, verify the existence of referenced entities, handle the default "General" category logic, and catch exceptions with user-friendly messages. `UserController` is abstract; `AdminUserController` and `RegularUserController` extend it.

**Views — `org/views/`**

Handle exclusively console input and output. They make no business decisions: they ask the user for data, return it to the controller, and display whatever results the controller passes back. `Menu.java` is the interface entry point and is responsible for instantiating all controllers, repositories, and views.

---

### MVC Workflow

The project follows the MVC pattern strictly, where each layer has a single responsibility:

```
User types in console
        |
        v
   [View] askTaskData()         <- Requests data, validates basic format
        |
        v
   [Controller] create()        <- Orchestrates: validates FK existence,
        |                          decides default category,
        |                          calls the repository
        v
   [Repository] save()          <- Executes INSERT with PreparedStatement
        |
        v
   [Database] MySQL              <- Trigger before_task_insert validates title
        |                          and assigns date if null
        v
   [Controller]                 <- Receives the saved object or the exception
        |
        v
   [View] showSuccess() / showError()  <- Displays result to user
```

This flow applies to all CRUD operations. The view never accesses the repository directly, and the repository never knows about the view. The controller is the only layer that knows both.

---

### Database

**Tables:** `user`, `user_roles`, `roles_and_users`, `category`, `workspace`, `user_workspace`, `task`, `task_comment`

**Views:**
- `task_dashboard` — tasks with their category and responsible user
- `comment_details` — comments with their task and author
- `workspace_summary` — task count by status for each workspace
- `usuariosConRoles` — users with their assigned role

**Stored Procedure:** `get_pending_tasks(workspace_name)` — returns the count of incomplete tasks per user in a given workspace.

**Trigger:** `before_task_insert` — rejects inserts with an empty title and automatically assigns `created_at` if not provided.

**Indexes:** `idx_user_name` on `user(name)`, `idx_task_title` on `task(title)`