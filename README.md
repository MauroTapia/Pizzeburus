# Documentación de Pizzeburus

## **1. Resumen del Proyecto**
- **Nombre del Proyecto**: Pizzeburus
- **Descripción**: Pizzeburus es una aplicación back-end basada en una arquitectura de microservicios. Su propósito principal es gestionar usuarios, pizzas y las pizzas favoritas de cada usuario.
- **Características principales**:
    - Gestión de usuarios: Crear, leer, actualizar y eliminar usuarios.
    - Gestión de pizzas: Crear, listar, modificar y eliminar pizzas.
    - Gestión de favoritos: Agregar y remover pizzas de la lista de favoritos de un usuario.
    - API Gateway para enrutar solicitudes.
- **Tecnologías utilizadas**:
    - Spring Boot
    - Postgres y H2 como bases de datos
    - Docker para contenedores
    - Feign para comunicación entre servicios
    - Circuit Breaker para tolerancia a fallos
    - RabbitMQ para mensajería
    - Sleuth y Zipkin para rastreo distribuido
    - Swagger para documentación de APIs
    - Grafana para monitoreo del sistema

---

## **2. Instrucciones para la Ejecución**

### **Requisitos Previos**
1. **Docker**: Asegúrate de tener Docker instalado en tu sistema. Puedes descargarlo desde [Docker](https://www.docker.com/).
2. **Docker Compose**: Verifica que Docker Compose esté instalado. Esto generalmente viene incluido con Docker Desktop.

### **Pasos para ejecutar el proyecto**

1. Clona el repositorio del proyecto:
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   cd <NOMBRE_DEL_PROYECTO>
   ```
2. Construye las imágenes y levanta los contenedores con Docker Compose:
   ```bash
   docker-compose up --build
   ```
3. Verifica que todos los servicios estén en ejecución. Puedes hacerlo con:
   ```bash
   docker ps
   ```
4. Accede a los servicios principales desde tu swagger o herramientas como Postman:
    - **PIZZAS-READ**: http://localhost:8081/swagger-ui/index.html
    - **PIZZAS-WRITE**: http://localhost:8082/swagger-ui/index.html
    - **USER-CRUD**: http://localhost:8083/swagger-ui/index.html
    -
### **Apagar el entorno**
Para detener todos los servicios, ejecuta:
```bash
   docker-compose down
```

---

## **3. Arquitectura del Sistema**

### **Diagrama de Arquitectura**
(Debe incluirse un diagrama visual que muestre los microservicios, bases de datos y sus interacciones.)

### **Componentes del Sistema**
1. **API Gateway**
    - Enruta todas las solicitudes externas a los microservicios correspondientes.
2. **Servicios relacionados con pizzas**:
    - **pizza-write**: Gestiona las operaciones de creación, actualización y eliminación de pizzas.
    - **pizza-read**: Gestiona la recuperación de datos de las pizzas; se conecta con `user-crud` mediante Feign.
3. **Servicios relacionados con usuarios**:
    - **user-crud**: Gestiona la creación, lectura, actualización y eliminación de usuarios, y también gestiona las pizzas favoritas de cada usuario.
4. **Config Server**: Proporciona configuración centralizada para todos los microservicios.
5. **Eureka Server**: Proporciona descubrimiento de servicios para facilitar la comunicación entre microservicios.
6. **Herramientas de monitoreo y rastreo**:
    - Sleuth y Zipkin para rastreo distribuido.
    - Grafana para visualización de métricas.

---

## **4. Diseño de Bases de Datos**

### **Bases de datos**
1. **Postgres**: Utilizado por `pizza-write` y `pizza-read` para gestionar la información de las pizzas.
2. **H2**: Utilizado por `user-crud` para almacenar información de los usuarios y sus favoritos.

### **Entidades principales**
1. **Usuario**:
    - ID (UUID)
    - Nombre
    - Email
    - Contraseña
    - Lista de favoritos (relación con pizzas)
2. **Pizza**:
    - ID (UUID)
    - Nombre
    - Descripción
    - Precio
3. **Favoritos**:
    - ID de Usuario (UUID)
    - ID de Pizza (UUID)

---

## **5. Descripción de Servicios**

### **5.1. API Gateway**
- **Descripción**: Centraliza las solicitudes externas y las dirige al microservicio correspondiente.
- **Características**: Manejo de autenticación, enrutamiento y balanceo de carga.

### **5.2. Servicios relacionados con pizzas**
#### **pizza-write**
- **Responsabilidades**: CRUD de datos de pizzas.
- **Endpoints**:
    - `POST /api/pizzas`: Crear una nueva pizza.
    - `PUT /api/pizzas/{id}`: Actualizar datos de una pizza existente.
    - `DELETE /api/pizzas/{id}`: Eliminar una pizza.

#### **pizza-read**
- **Responsabilidades**: Recuperación de datos de pizzas.
- **Endpoints**:
    - `GET /api/pizzas`: Listar todas las pizzas.
    - `GET /api/pizzas/{id}`: Obtener detalles de una pizza específica.
- **Comunicación**: Utiliza Feign para obtener información adicional de `user-crud`.

### **5.3. Servicio de usuarios (user-crud)**
- **Responsabilidades**: Gestión de usuarios y sus favoritos.
- **Endpoints**:
    - `POST /api/users`: Crear un nuevo usuario.
    - `GET /api/users/{id}`: Obtener información de un usuario.
    - `DELETE /api/users/{id}`: Eliminar un usuario.
    - `POST /api/users/{id}/favorites`: Agregar una pizza a los favoritos del usuario.
    - `DELETE /api/users/{id}/favorites/{pizzaId}`: Remover una pizza de los favoritos del usuario.

---

## **6. Pruebas**

### **Pruebas unitarias**
- **Frameworks**: JUnit, Mockito.
- **Cobertura**: Controladores y servicios.

### **Pruebas de integración**
- **Frameworks**: Spring Boot Test.
- **Foco**: Comunicación entre servicios y operaciones de bases de datos.

---

## **7. Despliegue**

- **Dockerización**:
    - Cada microservicio tiene su propio Dockerfile.
    - Uso de Docker Compose para orquestar los contenedores.
- **Pasos para el despliegue**:
    1. Construir los microservicios.
    2. Levantar el entorno con `docker-compose up`.

---

## **8. Desafíos y Soluciones**
1. **Tolerancia a fallos**:
    - **Desafío**: Garantizar la disponibilidad en caso de fallos en la comunicación entre servicios.
    - **Solución**: Implementación de Circuit Breaker con Resilience4j.
2. **Monitoreo del sistema**:
    - **Desafío**: Rastreo de solicitudes en un entorno distribuido.
    - **Solución**: Integración de Sleuth y Zipkin.

---

## **9. Mejoras Futuras**
- Migrar la base de datos de `user-crud` de H2 a Postgres para un entorno de producción más robusto.
- Crear un microservicio independiente para gestionar los favoritos, si la funcionalidad crece en complejidad.
- Añadir autenticación y autorización mediante OAuth2.

---

