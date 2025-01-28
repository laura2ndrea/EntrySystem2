# 🏬 EntrSystem 

## 🚀 Descripción del Proyecto
EntrSystem es un sistema de acceso diseñado para registrar entradas y salidas de personas en edificios corporativos. Nuestro objetivo es proporcionar una solución eficiente y organizada para la gestión de accesos, integrando funcionalidades que facilitan el control y registro de personas, equipos, vehículos y la venta de membresías.

## Características
- **Registro de personas**: Permite registrar y gestionar la entrada y salida de individuos.
- **Registro de equipos**: Mantiene un inventario y controla el ingreso y egreso de equipos tecnológicos.
- **Vehículos**: Gestión de acceso y registro de vehículos.
- **Venta de membresías**: Ofrece una opción para la venta y gestión de membresías.

## Tecnologías Utilizadas
- **Backend**: Java Spring Boot
- **Base de datos**: MySQL
- **Frontend**: React, Tailwind CSS

## Beneficios
- **Estructura organizada**: Posibilidad de crecimiento y expansión según las necesidades del edificio.
- **Diseño limpio e intuitivo**: Interfaz de usuario amigable y fácil de usar.

## Comenzando 🚀

## 🌐 Instrucciones de Instalación


El proyecto estará disponible en GitHub. Puedes obtener una copia del proyecto de dos formas:

1. **Fork del repositorio**:
   - Ve al repositorio del proyecto en GitHub.
   - Haz clic en el botón "Fork" en la esquina superior derecha.
   - Esto creará una copia del repositorio en tu cuenta de GitHub.

2. **Descargar la copia**:
   - Ve al repositorio del proyecto en GitHub.
   - Haz clic en el botón "Code" y selecciona "Download ZIP".
   - Extrae los archivos del ZIP descargado en tu máquina local.

Una vez que tengas una copia del proyecto, puedes seguir las instrucciones específicas de instalación y configuración incluidas en el repositorio.



## Deployment
Mira **Deployment** para conocer cómo desplegar el proyecto.

### Pre-requisitos 📋

_Que cosas necesitas para instalar el software y cómo instalarlas_

- **Java**: Necesitas tener Java instalado en tu máquina. Puedes descargarlo e instalarlo desde [aquí](https://www.oracle.com/java/technologies/javase-downloads.html).
- **Spring Boot Security**: Nuestro proyecto utiliza Spring Boot Security. Asegúrate de seguir la configuración específica en la documentación de Spring Boot.
- **Navegador**: Un navegador web moderno para acceder a la interfaz del proyecto.
- **NetBeans**: Utiliza NetBeans IDE para el desarrollo. Puedes descargarlo desde [aquí](https://netbeans.apache.org/download/index.html).
- **React**: Necesitas tener Node.js y npm instalados para trabajar con React. Puedes descargarlos e instalarlos desde [aquí](https://nodejs.org/).



## Instalación

### Paso 1: Clonar el repositorio
Clona el repositorio del proyecto desde GitHub a tu máquina local.


### Paso 2: Instalar Java
Asegúrate de tener Java instalado en tu máquina. 

### Paso 3: Configurar Spring Boot

Nuestro proyecto utiliza Spring Boot Security.

### Paso 4: Configurar NetBeans

### Paso 5: Instalar Node.jsy npm

### Paso 6: Instalar dependencias de React























## Construido con 🛠️

_Menciona las herramientas que utilizaste para crear tu proyecto_

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - El framework web usado
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [ROME](https://rometools.github.io/rome/) - Usado para generar RSS
* [Java Spring Boot](https://spring.io/projects/spring-boot) - Framework para el backend
* [MySQL](https://www.mysql.com/) - Sistema de gestión de bases de datos
* [React](https://reactjs.org/) - Librería de JavaScript para construir interfaces de usuario
* [Tailwind CSS](https://tailwindcss.com/) - Framework de CSS para diseño

## Contribuyendo 🖇️
- https://github.com/AlejandroRinconPerez
- https://github.com/laura2ndrea
- https://github.com/JaimePrada11
 
 
 el proceso para enviarnos pull requests.

## Wiki 📖

Puedes encontrar mucho más de cómo utilizar este proyecto en nuestra [Wiki](https://github.com/tu/proyecto/wiki)

## Versionado 📌

Usamos [SemVer](http://semver.org/) para el versionado. Para todas las versiones disponibles, mira los [tags en este repositorio](https://github.com/tu/proyecto/tags).

## Autores ✒️



## Licencia 📄

Este proyecto está bajo la Licencia (Tu Licencia) - mira el archivo [LICENSE.md](LICENSE.md) para detalles

## Expresiones de Gratitud 🎁

* Comenta a otros sobre este proyecto 📢
* Invita una cerveza 🍺 o un café ☕ a alguien del equipo. 
* Da las gracias públicamente 🤓.
* Dona con cripto a esta dirección: `0xf253fc233333078436d111175e5a76a649890000`
* etc.

# Documentación de la API

## Descripción General
Esta API proporciona endpoints para la gestión de usuarios, porteros, personas, empresas, carnets, control de acceso, membresías y facturas en un sistema de gestión de instalaciones.

## URL Base
```
http://localhost:3000
```

## Autenticación
Los detalles de autenticación no están especificados en la documentación de la API.

## Recursos

### 1. Gestión de Usuarios

#### Listar Todos los Usuarios
- **GET** `/api/user`
- **Descripción**: Obtiene una lista de todos los usuarios
- **Respuesta**: Array de objetos Usuario

#### Obtener Usuario por ID
- **GET** `/api/user/{id}`
- **Descripción**: Obtiene un usuario específico por ID
- **Parámetros**:
  - `id` (path, requerido): ID del usuario
- **Respuesta**: Objeto Usuario

#### Actualizar Usuario
- **PUT** `/api/user`
- **Descripción**: Actualiza un usuario existente
- **Cuerpo de la Petición**: Objeto Usuario
- **Respuesta**: Objeto Usuario actualizado

#### Crear Usuario para Portero
- **POST** `/api/user/{idPorter}`
- **Descripción**: Crea un nuevo usuario asociado a un portero
- **Parámetros**:
  - `idPorter` (path, requerido): ID del portero
- **Cuerpo de la Petición**: Objeto Usuario
- **Respuesta**: Objeto Usuario creado

### 2. Gestión de Porteros

#### Listar Todos los Porteros
- **GET** `/api/porters`
- **Descripción**: Obtiene una lista de todos los porteros
- **Respuesta**: Array de objetos Portero

#### Obtener Portero por ID
- **GET** `/api/porters/{id}`
- **Descripción**: Obtiene un portero específico por ID
- **Parámetros**:
  - `id` (path, requerido): ID del portero
- **Respuesta**: Objeto Portero

#### Crear Portero
- **POST** `/api/porters`
- **Descripción**: Crea un nuevo portero
- **Cuerpo de la Petición**: Objeto Portero
- **Respuesta**: Objeto Portero creado

#### Actualizar Portero
- **PUT** `/api/porters/{id}`
- **Descripción**: Actualiza un portero existente
- **Parámetros**:
  - `id` (path, requerido): ID del portero
- **Cuerpo de la Petición**: Objeto Portero
- **Respuesta**: Objeto Portero actualizado

#### Añadir Jefe a Portero
- **PUT** `/api/porters/{idPorter}/boss/{idBoss}`
- **Descripción**: Asigna un jefe a un portero
- **Parámetros**:
  - `idPorter` (path, requerido): ID del portero
  - `idBoss` (path, requerido): ID del jefe
- **Respuesta**: Objeto Portero actualizado

### 3. Gestión de Personas

#### Listar Todas las Personas
- **GET** `/api/people`
- **Descripción**: Obtiene una lista de todas las personas
- **Respuesta**: Array de objetos Persona

#### Obtener Persona por ID
- **GET** `/api/people/{id}`
- **Descripción**: Obtiene una persona específica por ID
- **Parámetros**:
  - `id` (path, requerido): ID de la persona
- **Respuesta**: Objeto Persona

#### Crear Persona
- **POST** `/api/people`
- **Descripción**: Crea una nueva persona
- **Cuerpo de la Petición**: Objeto Persona
- **Respuesta**: Objeto Persona creado

#### Actualizar Persona
- **PUT** `/api/people`
- **Descripción**: Actualiza una persona existente
- **Cuerpo de la Petición**: Objeto Persona
- **Respuesta**: Objeto Persona actualizado

### 4. Gestión de Equipos

#### Añadir Equipo a Persona
- **POST** `/api/people/{personId}/equipment`
- **Descripción**: Añade un equipo al registro de una persona
- **Parámetros**:
  - `personId` (path, requerido): ID de la persona
- **Cuerpo de la Petición**: Objeto EquipoRegistrado
- **Respuesta**: Objeto Persona actualizado

#### Actualizar Equipo
- **PUT** `/api/people/equipment`
- **Descripción**: Actualiza un equipo existente
- **Cuerpo de la Petición**: Objeto EquipoRegistrado
- **Respuesta**: Objeto EquipoRegistrado actualizado

### 5. Gestión de Empresas

#### Listar Todas las Empresas
- **GET** `/api/company`
- **Descripción**: Obtiene una lista de todas las empresas
- **Respuesta**: Array de objetos Empresa

#### Obtener Empresa por ID
- **GET** `/api/company/{id}`
- **Descripción**: Obtiene una empresa específica por ID
- **Parámetros**:
  - `id` (path, requerido): ID de la empresa
- **Respuesta**: Objeto Empresa

#### Crear Empresa
- **POST** `/api/company`
- **Descripción**: Crea una nueva empresa
- **Cuerpo de la Petición**: Objeto Empresa
- **Respuesta**: Objeto Empresa creado

#### Añadir Empleado a Empresa
- **POST** `/api/company/{idCompany}/employee/{idEmployee}`
- **Descripción**: Añade un empleado a una empresa
- **Parámetros**:
  - `idCompany` (path, requerido): ID de la empresa
  - `idEmployee` (path, requerido): ID del empleado
- **Respuesta**: Objeto Empresa actualizado

### 6. Control de Acceso

#### Listar Todos los Registros de Acceso
- **GET** `/api/access`
- **Descripción**: Obtiene todos los registros de acceso
- **Respuesta**: Array de objetos Acceso

#### Crear Registro de Acceso
- **POST** `/api/access`
- **Descripción**: Crea un nuevo registro de acceso
- **Cuerpo de la Petición**: Objeto Acceso
- **Respuesta**: Objeto Acceso creado

#### Añadir Nota a Registro de Acceso
- **POST** `/api/access/add-note/{idAccess}`
- **Descripción**: Añade una nota a un registro de acceso
- **Parámetros**:
  - `idAccess` (path, requerido): ID del acceso
- **Cuerpo de la Petición**: Objeto NotaAcceso
- **Respuesta**: Objeto Acceso actualizado

### 7. Gestión de Carnets

#### Listar Todos los Carnets
- **GET** `/api/carnet`
- **Descripción**: Obtiene todos los carnets
- **Respuesta**: Array de objetos Carnet

#### Crear Carnet
- **POST** `/api/carnet`
- **Descripción**: Crea un nuevo carnet
- **Cuerpo de la Petición**: Objeto Carnet
- **Respuesta**: Objeto Carnet creado

#### Actualizar Estado de Carnet
- **PUT** `/api/carnet/{carnetId}`
- **Descripción**: Actualiza el estado de un carnet
- **Parámetros**:
  - `carnetId` (path, requerido): ID del carnet
  - `newStatus` (query, requerido): Estado booleano
- **Respuesta**: Objeto Carnet actualizado

## Modelos de Datos

### Usuario
```json
{
  "id": "entero",
  "userName": "string",
  "password": "string",
  "porter": "objeto Portero"
}
```

### Portero
```json
{
  "id": "entero",
  "name": "string",
  "cedula": "string",
  "telefono": "string",
  "employmentDate": "fecha y hora",
  "position": "booleano",
  "id_jefe": "objeto Portero",
  "accesses": "array de enteros",
  "user": "objeto Usuario",
  "invoices": "array de objetos Factura"
}
```

### Persona
```json
{
  "id": "entero",
  "name": "string",
  "cedula": "string",
  "telefono": "string",
  "personType": "booleano",
  "company": "objeto Empresa",
  "invoices": "array de objetos Factura",
  "equipments": "array de objetos EquipoRegistrado",
  "vehicles": "array de objetos Vehículo",
  "carnet": "objeto Carnet"
}
```

### Empresa
```json
{
  "id_company": "entero",
  "name": "string",
  "peopleList": "array de objetos Persona"
}
```

### Acceso
```json
{
  "idAccess": "entero",
  "entryAccess": "fecha y hora",
  "exitAccess": "fecha y hora",
  "accessType": "booleano",
  "people": "objeto Persona",
  "accessNotes": "array de objetos NotaAcceso",
  "porters": "array de objetos Portero"
}
```

### Carnet
```json
{
  "code": "string",
  "status": "booleano",
  "people": "objeto Persona",
  "id": "entero"
}
```

### EquipoRegistrado
```json
{
  "id": "entero",
  "serial": "string",
  "registrationDate": "fecha y hora",
  "description": "string",
  "people": "objeto Persona"
}
```

### Factura
```json
{
  "idInvoice": "entero",
  "date": "fecha y hora",
  "status": "booleano",
  "people": "objeto Persona"
}
```


