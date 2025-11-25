# 🌱 Huerto Hogar - Backend API

Aplicación backend para la plataforma **Huerto Hogar**, un sistema de gestión y comercio de productos agrícolas caseros. Desarrollado con **Spring Boot 3.5.7** y **Java 21 LTS**.

## 📋 Características

✅ **Autenticación Multi-método**
- JWT (JSON Web Tokens) con expiración configurable
- Autenticación basada en sesiones HTTP
- OAuth2 compatible (Google, GitHub, etc.)

✅ **Gestión de Usuarios**
- Registro y login de usuarios
- Control de roles (CLIENTE, ADMINISTRADOR, etc.)
- Validación de tokens y protección de rutas

✅ **Catálogo de Productos**
- CRUD completo de productos
- Gestión de categorías
- Búsqueda y filtrado

✅ **Procesamiento de Pagos**
- Integración de servicios de pago
- Simulación de proveedores de pago
- Historial de transacciones

✅ **Seguridad**
- Encriptación de contraseñas (BCrypt)
- CORS configurado para desarrollo
- Filtros de autenticación JWT
- HTTPS-ready

✅ **Documentación API**
- OpenAPI/Swagger UI integrado
- Endpoints documentados automáticamente

## 🚀 Requisitos Previos

- **Java 21 LTS** o superior
- **Maven 3.6+** (incluido: mvnw)
- **MySQL 8.0+**
- **Git**

## 📦 Stack Tecnológico

| Componente | Versión | Propósito |
|-----------|---------|----------|
| Spring Boot | 3.5.7 | Framework principal |
| Java | 21 LTS | Lenguaje de programación |
| MySQL Connector | Latest | Driver de base de datos |
| JJWT | 0.11.5 | Generación y validación JWT |
| Spring Security | 3.5.7 | Seguridad y autenticación |
| Spring Data JPA | 3.5.7 | ORM e acceso a datos |
| SpringDoc OpenAPI | 2.1.0 | Documentación API (Swagger) |
| Spring OAuth2 Client | 3.5.7 | Integración OAuth2 |

## 🔧 Configuración Inicial

### 1. Clonar el repositorio

```bash
git clone https://github.com/Guerben/huertohogar.git
cd huertohogar
```

### 2. Configurar base de datos

Crea la base de datos en MySQL:

```sql
CREATE DATABASE tienda_huerto_hogar CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. Configurar `application.properties`

Edita `src/main/resources/application.properties`:

```properties
# Conexión a base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/tienda_huerto_hogar
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña

# JWT Secret (IMPORTANTE: Cambiar en producción)
jwt.secret=tu_clave_secreta_muy_segura_aqui
jwt.expiration=86400000  # 24 horas en milisegundos

# Puerto del servidor
server.port=8080

# Orígenes permitidos para CORS (desarrollo)
frontend.allowed-origins=http://localhost:3000,http://127.0.0.1:5500
```

### 4. Compilar y ejecutar

#### Opción A: Con Maven Wrapper (recomendado)

```bash
# Windows
mvnw.cmd clean package
mvnw.cmd spring-boot:run

# macOS/Linux
./mvnw clean package
./mvnw spring-boot:run
```

#### Opción B: Con Maven instalado

```bash
mvn clean package
mvn spring-boot:run
```

La aplicación estará disponible en: **http://localhost:8080**

## 📚 Documentación de API

### Swagger UI

Una vez que la aplicación esté ejecutándose, accede a:

```
http://localhost:8080/swagger-ui.html
```

### Endpoints Principales

#### Autenticación

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/auth/register` | Registrar nuevo usuario |
| `POST` | `/api/auth/login` | Login con JWT |
| `POST` | `/api/auth/login-session` | Login con sesión HTTP |
| `POST` | `/api/auth/logout-session` | Logout (sesión) |
| `GET` | `/api/auth/validate` | Validar token JWT |
| `GET` | `/api/auth/me` | Obtener usuario actual |
| `GET` | `/api/auth/oauth2/success` | Callback OAuth2 |

#### Usuarios

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/usuarios` | Listar usuarios |
| `GET` | `/api/usuarios/{id}` | Obtener usuario por ID |
| `PUT` | `/api/usuarios/{id}` | Actualizar usuario |
| `DELETE` | `/api/usuarios/{id}` | Eliminar usuario |

#### Productos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/productos` | Listar productos |
| `POST` | `/api/productos` | Crear producto |
| `GET` | `/api/productos/{id}` | Obtener producto |
| `PUT` | `/api/productos/{id}` | Actualizar producto |
| `DELETE` | `/api/productos/{id}` | Eliminar producto |

#### Categorías

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/categorias` | Listar categorías |
| `POST` | `/api/categorias` | Crear categoría |

#### Pagos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/payments/process` | Procesar pago |
| `GET` | `/api/payments/{id}` | Obtener detalles del pago |

## 🔐 Autenticación JWT

### Registro

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan Pérez",
    "email": "juan@example.com",
    "password": "MiPassword123!",
    "telefono": "555-1234",
    "direccion": "Calle 123",
    "rolId": null
  }'
```

### Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "juan@example.com",
    "password": "MiPassword123!"
  }'
```

**Respuesta:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "userId": 1,
  "email": "juan@example.com",
  "nombre": "Juan Pérez",
  "rol": "CLIENTE"
}
```

### Usar Token en Requests

Incluye el token en el header `Authorization`:

```bash
curl -X GET http://localhost:8080/api/auth/me \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

## 🔐 OAuth2 (Opcional)

Para habilitar login social con Google o GitHub:

### 1. Google OAuth2

1. Ve a [Google Cloud Console](https://console.cloud.google.com/)
2. Crea un nuevo proyecto y habilita Google OAuth 2.0
3. Descarga las credenciales (Client ID y Secret)
4. Añade a `application.properties`:

```properties
spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET
spring.security.oauth2.client.registration.google.scope=profile,email
```

5. Descomenta `.oauth2Login()` en `src/main/java/com/huertohogar/backend/config/SecurityConfig.java`

### 2. GitHub OAuth2

Similar al proceso anterior, configura en GitHub Settings → Developer settings → OAuth Apps.

## 🧪 Pruebas

### Ejecutar todas las pruebas

```bash
# Con Maven Wrapper
mvnw.cmd test

# Con Maven
mvn test
```

### Pruebas disponibles

- **JwtUtilTest**: Validación de generación y lectura de JWT
- **AuthServiceTest**: Lógica de autenticación y OAuth2
- **AuthControllerTest**: Endpoints de autenticación
- **PaymentServiceTest**: Procesamiento de pagos
- **PaymentControllerTest**: Endpoints de pagos
- **DTO Tests**: Validación de modelos de datos
- **Model Tests**: Validación de entidades JPA

### Ejecutar una prueba específica

```bash
mvn test -Dtest=AuthServiceTest
```

## 📂 Estructura del Proyecto

```
huertohogar/
├── pom.xml                          # Configuración Maven
├── README.md                        # Este archivo
├── mvnw / mvnw.cmd                 # Maven Wrapper
└── src/
    ├── main/
    │   ├── java/com/huertohogar/backend/
    │   │   ├── HuertohogarBackendApplication.java  # Clase principal
    │   │   ├── config/                # Configuración de seguridad, CORS, OpenAPI
    │   │   │   ├── CorsConfig.java
    │   │   │   ├── JwtAuthenticationFilter.java
    │   │   │   ├── OpenApiConfig.java
    │   │   │   └── SecurityConfig.java
    │   │   ├── controller/            # Controladores REST
    │   │   │   ├── AuthController.java
    │   │   │   ├── PaymentController.java
    │   │   │   ├── ProductoController.java
    │   │   │   ├── CategoriaController.java
    │   │   │   ├── UsuarioController.java
    │   │   │   └── RolController.java
    │   │   ├── service/               # Lógica de negocio
    │   │   │   ├── AuthService.java
    │   │   │   ├── PaymentService.java
    │   │   │   ├── ProductoService.java
    │   │   │   ├── CategoriaService.java
    │   │   │   ├── UsuarioService.java
    │   │   │   └── RolService.java
    │   │   ├── repository/            # Acceso a datos (JPA)
    │   │   │   ├── UsuarioRepository.java
    │   │   │   ├── PaymentRepository.java
    │   │   │   ├── ProductoRepository.java
    │   │   │   ├── CategoriaRepository.java
    │   │   │   └── RolRepository.java
    │   │   ├── model/                 # Entidades JPA
    │   │   │   ├── UsuarioModel.java
    │   │   │   ├── PaymentModel.java
    │   │   │   ├── Producto.java
    │   │   │   ├── CategoriaModel.java
    │   │   │   └── RolModel.java
    │   │   ├── dto/                   # Data Transfer Objects
    │   │   │   ├── UsuarioDTO.java
    │   │   │   ├── PaymentRequest.java
    │   │   │   ├── PaymentResponse.java
    │   │   │   ├── ProductoDto.java
    │   │   │   ├── CategoriaDto.java
    │   │   │   ├── LoginRequest.java
    │   │   │   ├── RegisterRequest.java
    │   │   │   ├── RolDto.java
    │   │   │   └── AuthResponse.java
    │   │   └── util/                  # Utilidades
    │   │       └── JwtUtil.java
    │   └── resources/
    │       ├── application.properties # Configuración de la app
    │       ├── data_huerto_backend.sql # Datos iniciales
    │       ├── usuario.csv             # Importación de usuarios
    │       └── db/migration/           # Scripts de migración
    │           └── V1__init.sql
    └── test/
        └── java/com/huertohogar/backend/
            ├── controller/            # Tests de controladores
            ├── service/               # Tests de servicios
            ├── util/                  # Tests de utilidades
            ├── dto/                   # Tests de DTOs
            └── model/                 # Tests de modelos
```

## 🌐 Configuración para Producción

### Variables de Entorno Recomendadas

```bash
# Database
SPRING_DATASOURCE_URL=jdbc:mysql://prod-db:3306/huerto_hogar
SPRING_DATASOURCE_USERNAME=prod_user
SPRING_DATASOURCE_PASSWORD=prod_password

# JWT
JWT_SECRET=tu_clave_muy_segura_en_produccion
JWT_EXPIRATION=86400000

# Server
SERVER_PORT=8080

# CORS
FRONTEND_ALLOWED_ORIGINS=https://tudominio.com,https://www.tudominio.com

# OAuth2 (si aplica)
SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_ID=...
SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_SECRET=...
```

### Docker (Opcional)

Si deseas containerizar la aplicación:

```dockerfile
FROM openjdk:21-slim
WORKDIR /app
COPY target/huertohogar-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Construcción y ejecución:

```bash
docker build -t huertohogar-backend .
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/tienda_huerto_hogar \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=root \
  huertohogar-backend
```

## 🐛 Solución de Problemas

### Error: "No suitable driver found for jdbc:mysql"

**Causa**: Dependencia de MySQL no instalada correctamente.

**Solución**:
```bash
mvn clean install
mvn spring-boot:run
```

### Error: "Cannot start maven from wrapper"

**Causa**: Maven Wrapper no tiene permisos de ejecución.

**Solución (Linux/macOS)**:
```bash
chmod +x mvnw
./mvnw clean install
```

### Error: "ClientRegistrationRepository not found"

**Causa**: OAuth2 está habilitado pero sin configuración.

**Solución**: Comenta `.oauth2Login()` en `SecurityConfig.java` hasta que configures OAuth2.

### Error de conexión a MySQL

**Verificar**:
```bash
# Asegúrate que MySQL está corriendo
mysql -u root -p
```

Luego verifica las credenciales en `application.properties`.

## 📝 Cambios Recientes

### v0.0.1
- ✅ Migración a Java 21 LTS
- ✅ Autenticación JWT completa
- ✅ Autenticación con sesiones HTTP
- ✅ Soporte para OAuth2 (opcional)
- ✅ Servicio de procesamiento de pagos
- ✅ Suite completa de pruebas unitarias
- ✅ Documentación OpenAPI/Swagger
- ✅ Configuración CORS para desarrollo

## 🤝 Contribuir

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Ver `LICENSE` para más detalles.

## 📧 Soporte

Para reportar bugs o sugerir mejoras, abre un issue en el repositorio.

---

**Desarrollado con ❤️ por el equipo de Huerto Hogar**

Última actualización: Noviembre 2025
