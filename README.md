# 🏦 Sistema Bancario Microservicios - DEVSU

Sistema bancario desarrollado con arquitectura de microservicios utilizando Spring Boot, Java 17 y Clean Architecture. El sistema implementa funcionalidades completas de gestión de clientes, cuentas y movimientos bancarios.

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Arquitectura](#️-arquitectura)
- [Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [Instalación](#-instalación)
- [Uso](#-uso)
- [API Documentation](#-api-documentation)
- [Testing](#-testing)
- [Base de Datos](#️-base-de-datos)
- [Monitoreo](#-monitoreo)
- [Contribuir](#-contribuir)

## ✨ Características

### Funcionalidades Principales

- **🧑‍💼 Gestión de Clientes**: CRUD completo para clientes y personas
- **💳 Gestión de Cuentas**: CRU (Create, Read, Update) para cuentas bancarias
- **💰 Gestión de Movimientos**: CRU para movimientos bancarios con validación de saldo
- **📊 Reportería**: Generación de estados de cuenta por cliente y rango de fechas
- **🔒 Validaciones**: Validación de saldo insuficiente con mensaje "Saldo no disponible"
- **🔄 Comunicación Asíncrona**: RabbitMQ para eventos entre microservicios

### Características Técnicas

- **Clean Architecture**: Implementación de arquitectura hexagonal
- **API REST**: Endpoints RESTful con documentación Swagger/OpenAPI
- **Service Discovery**: Netflix Eureka para descubrimiento de servicios
- **API Gateway**: Spring Cloud Gateway para enrutamiento
- **Messaging**: RabbitMQ para comunicación asíncrona
- **Base de Datos**: PostgreSQL con JPA/Hibernate
- **Containerización**: Docker y Docker Compose
- **Testing**: Pruebas unitarias con JUnit 5 y Mockito

## 🏗️ Arquitectura

### Microservicios

```
┌─────────────────┐    ┌─────────────────┐
│   Gateway       │    │ Discovery       │
│   (Port 8080)   │    │ Server          │
│                 │    │ (Port 8761)     │
└─────────────────┘    └─────────────────┘
         │                       │
         └───────────┬───────────┘
                     │
         ┌───────────┴───────────┐
         │                       │
┌─────────────────┐    ┌─────────────────┐
│ Cliente-Persona │    │ Cuenta-         │
│ Microservice    │◄──►│ Movimiento      │
│ (Port 8081)     │    │ Microservice    │
│                 │    │ (Port 8082)     │
└─────────────────┘    └─────────────────┘
         │                       │
         └───────────┬───────────┘
                     │
         ┌───────────┴───────────┐
         │                       │
┌─────────────────┐    ┌─────────────────┐
│   PostgreSQL    │    │   RabbitMQ      │
│   (Port 5432)   │    │   (Port 5672)   │
└─────────────────┘    └─────────────────┘
```

### Comunicación Entre Servicios

- **Síncrona**: HTTP REST API a través del Gateway
- **Asíncrona**: RabbitMQ para eventos de ciclo de vida de clientes
- **Service Discovery**: Eureka para registro y descubrimiento automático

### Estructura de Clean Architecture

```
src/
├── domain/
│   ├── models/          # Entidades de dominio
│   ├── ports/
│   │   ├── in/         # Casos de uso (interfaces)
│   │   └── out/        # Puertos de salida (repositories)
│   └── exceptions/     # Excepciones de dominio
├── application/
│   ├── useCases/       # Implementación de casos de uso
│   └── services/       # Servicios de aplicación
└── infrastructure/
    ├── controllers/    # Controladores REST
    ├── repositories/   # Implementación JPA
    ├── config/         # Configuración Spring
    ├── events/         # Manejo de eventos RabbitMQ
    └── entities/       # Entidades JPA
```

## 🛠 Tecnologías Utilizadas

### Backend
- **Java 17** - Lenguaje de programación
- **Spring Boot 3.2.12** - Framework principal
- **Spring Cloud 2023.0.4** - Microservicios
- **Spring Data JPA** - Persistencia
- **Netflix Eureka** - Service Discovery
- **Spring Cloud Gateway** - API Gateway
- **RabbitMQ** - Message Broker
- **PostgreSQL** - Base de datos relacional
- **Lombok** - Reducción de boilerplate
- **Maven** - Gestión de dependencias

### DevOps & Tools
- **Docker & Docker Compose** - Containerización
- **Swagger/OpenAPI 3** - Documentación API
- **JUnit 5 & Mockito** - Testing
- **pgAdmin** - Administración de BD

## 🚀 Instalación

### Prerrequisitos

- Java 17 o superior
- Docker y Docker Compose
- Maven 3.8+
- Git

### Clonar el Repositorio

```bash
git clone <repository-url>
cd sistema-bancario-microservicios
```

### Despliegue con Docker (Recomendado)

```bash
# Levantar todos los servicios
docker compose up --build

# Ejecutar en segundo plano
docker compose up -d --build

# Ver logs
docker compose logs -f

# Detener servicios
docker compose down
```

### Despliegue Manual

#### 1. Iniciar Infraestructura

```bash
# PostgreSQL
docker run -d --name postgres-db \
  -e POSTGRES_DB=devsu_db \
  -e POSTGRES_USER=devsu_user \
  -e POSTGRES_PASSWORD=devsu_password \
  -p 5432:5432 postgres:15-alpine

# RabbitMQ
docker run -d --name rabbitmq \
  -p 5672:5672 -p 15672:15672 \
  rabbitmq:management
```

#### 2. Compilar y Ejecutar Servicios

```bash
# Discovery Server
cd discovery_server
./mvnw clean package
./mvnw spring-boot:run

# Gateway
cd ../gateway
./mvnw clean package
./mvnw spring-boot:run

# Cliente-Persona
cd ../cliente_persona
./mvnw clean package
./mvnw spring-boot:run

# Cuenta-Movimiento
cd ../cuenta_movimiento
./mvnw clean package
./mvnw spring-boot:run
```

## 📖 Uso

### Endpoints Principales

#### Gestión de Clientes
```bash
# Crear cliente
POST http://localhost:8080/clientes
{
  "persona": {
    "name": "Jose Lema",
    "gender": "Masculino",
    "age": 35,
    "identification": "1234567890",
    "address": "Otavalo sn y principal",
    "telephone": "098254785"
  },
  "password": "1234",
  "status": true
}

# Obtener todos los clientes
GET http://localhost:8080/clientes

# Obtener cliente por ID
GET http://localhost:8080/clientes/1

# Actualizar cliente
PUT http://localhost:8080/clientes/1

# Eliminar cliente
DELETE http://localhost:8080/clientes/1
```

#### Gestión de Cuentas
```bash
# Crear cuenta
POST http://localhost:8080/cuentas
{
  "accountNumber": "478758",
  "accountType": "Ahorros",
  "initialBalance": 2000.0,
  "status": true,
  "clientId": 1
}

# Obtener cuentas
GET http://localhost:8080/cuentas

# Obtener cuentas por cliente
GET http://localhost:8080/cuentas/cliente/1
```

#### Gestión de Movimientos
```bash
# Crear movimiento (depósito)
POST http://localhost:8080/movimientos
{
  "accountId": 1,
  "movementType": "Deposito",
  "value": 600.0,
  "date": "2024-02-10"
}

# Crear movimiento (retiro)
POST http://localhost:8080/movimientos
{
  "accountId": 1,
  "movementType": "Retiro",
  "value": -575.0,
  "date": "2024-02-10"
}
```

#### Reportes
```bash
# Estado de cuenta por cliente y rango de fechas
GET http://localhost:8080/reportes?cliente=1&fechaInicio=2024-02-01&fechaFin=2024-02-28
```

### Casos de Uso de Ejemplo

El sistema viene preconfigurado con datos de prueba:

```sql
-- Clientes
INSERT INTO persona (name, gender, age, identification, address, telephone) VALUES
('Jose Lema', 'Masculino', 35, '1234567890', 'Otavalo sn y principal', '098254785'),
('Marianela Montalvo', 'Femenino', 28, '0987654321', 'Amazonas y NNUU', '097548965'),
('Juan Osorio', 'Masculino', 40, '1122334455', '13 junio y Equinoccial', '098874587');

-- Cuentas
INSERT INTO account (account_number, account_type, initial_balance, status, client_id) VALUES
('478758', 'Ahorros', 2000, true, 1),
('225487', 'Corriente', 100, true, 2),
('495878', 'Ahorros', 0, true, 3),
('496825', 'Ahorros', 540, true, 2);
```

## 📚 API Documentation

### Swagger UI

Una vez que los servicios estén ejecutándose, acceda a la documentación interactiva:

- **Cliente-Persona API**: http://localhost:8081/swagger-ui.html
- **Cuenta-Movimiento API**: http://localhost:8082/swagger-ui.html
- **Gateway (Agregado)**: http://localhost:8080/swagger-ui.html

### Colección Postman

Importe la colección `Postman_Collection_DEVSU.json` en Postman para probar todos los endpoints:

1. Abra Postman
2. Importe el archivo `Postman_Collection_DEVSU.json`
3. Configure la variable `baseUrl` como `http://localhost:8080`
4. Ejecute las pruebas en el orden sugerido

## 🧪 Testing

### Ejecutar Pruebas

```bash
# Todas las pruebas
./mvnw test

# Pruebas de un servicio específico
cd cliente_persona
./mvnw test

# Pruebas con cobertura
./mvnw test jacoco:report
```

### Cobertura de Pruebas

- **Pruebas Unitarias**: ClientTest.java (17 casos de prueba)
- **Pruebas de Casos de Uso**: CreateClientUseCaseImplTest.java
- **Validaciones de Dominio**: Todas las reglas de negocio cubiertas

## 🗄️ Base de Datos

### Esquema

La base de datos se inicializa automáticamente con el script `BaseDatos.sql`:

```sql
-- Tablas principales
- persona: Datos personales
- client: Información de clientes (hereda de persona)
- account: Cuentas bancarias
- movement: Movimientos bancarios

-- Relaciones
persona (1) -> (1) client
client (1) -> (N) account  
account (1) -> (N) movement
```

### Acceso a Base de Datos

```bash
# pgAdmin
http://localhost:5050
Email: admin@devsu.com
Password: admin123

# Línea de comandos
docker exec -it postgres-db psql -U devsu_user -d devsu_db
```

## 📊 Monitoreo

### Eureka Dashboard
http://localhost:8761

### RabbitMQ Management
http://localhost:15672
- Usuario: guest
- Contraseña: guest

### Health Checks

```bash
# Gateway
curl http://localhost:8080/actuator/health

# Discovery Server  
curl http://localhost:8761/actuator/health

# Microservicios
curl http://localhost:8081/actuator/health
curl http://localhost:8082/actuator/health
```

## 🔧 Configuración

### Variables de Entorno

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `SPRING_DATASOURCE_URL` | URL de PostgreSQL | `jdbc:postgresql://localhost:5432/devsu_db` |
| `SPRING_DATASOURCE_USERNAME` | Usuario de BD | `devsu_user` |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña de BD | `devsu_password` |
| `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE` | URL de Eureka | `http://localhost:8761/eureka/` |
| `SPRING_RABBITMQ_HOST` | Host de RabbitMQ | `localhost` |
| `SPRING_RABBITMQ_PORT` | Puerto de RabbitMQ | `5672` |

### Perfiles de Spring

- **default**: Configuración para desarrollo local
- **docker**: Configuración para contenedores
- **test**: Configuración para pruebas

## 🚨 Manejo de Errores

### Códigos de Estado HTTP

| Código | Descripción |
|--------|-------------|
| 200 | Operación exitosa |
| 201 | Recurso creado |
| 204 | Recurso eliminado |
| 400 | Datos inválidos / Saldo no disponible |
| 404 | Recurso no encontrado |
| 500 | Error interno del servidor |

### Ejemplo de Respuesta de Error

```json
{
  "timestamp": "2024-02-10T15:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Saldo no disponible",
  "path": "/movimientos"
}
```

## 🔄 Comunicación Asíncrona

### Eventos RabbitMQ

- **ClientCreatedEvent**: Cuando se crea un cliente
- **ClientUpdatedEvent**: Cuando se actualiza un cliente  
- **ClientDeletedEvent**: Cuando se elimina un cliente

### Configuración de Colas

```yaml
# Exchange: client.exchange
# Queue: client.created.queue
# Routing Key: client.created
```

## 🏆 Buenas Prácticas Implementadas

- ✅ **Clean Architecture**: Separación clara de responsabilidades
- ✅ **SOLID Principles**: Aplicación de principios SOLID
- ✅ **Dependency Injection**: Inversión de dependencias con Spring
- ✅ **Repository Pattern**: Abstracción de acceso a datos
- ✅ **DTO Pattern**: Transferencia de datos entre capas
- ✅ **Exception Handling**: Manejo global de excepciones
- ✅ **Validation**: Validación de datos de entrada
- ✅ **Documentation**: Documentación completa con Swagger
- ✅ **Testing**: Pruebas unitarias y de casos de uso
- ✅ **Containerization**: Despliegue con Docker

## 📝 Notas de Desarrollo

### Estructura de Commits

```bash
git commit -m "feat: implementar validación de saldo insuficiente"
git commit -m "fix: corregir cálculo de saldo en movimientos"
git commit -m "docs: actualizar documentación de API"
```

### Versionado

El proyecto sigue [Semantic Versioning](https://semver.org/):

- **MAJOR**: Cambios incompatibles en la API
- **MINOR**: Nuevas funcionalidades compatibles
- **PATCH**: Corrección de errores

## 🔮 Roadmap

### Futuras Mejoras

- [ ] Implementación de JWT para autenticación
- [ ] Cache distribuido con Redis
- [ ] Métricas con Prometheus y Grafana
- [ ] Trazabilidad distribuida con Zipkin
- [ ] Circuit Breaker con Resilience4j
- [ ] API Rate Limiting
- [ ] Auditoría de transacciones
- [ ] Notificaciones en tiempo real

## 📄 Licencia

Este proyecto está bajo la Licencia Apache 2.0. Ver el archivo `LICENSE` para más detalles.

## 👥 Contribuir

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -m 'feat: agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

---

## 📞 Soporte

Para soporte o preguntas:

- **Email**: desarrollo@devsu.com
- **Issues**: [GitHub Issues](./issues)
- **Documentación**: [Wiki del Proyecto](./wiki)

---

**Desarrollado con ❤️ para DEVSU**