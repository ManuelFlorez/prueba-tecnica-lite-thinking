# prueba-tecnica-lite-thinking

## PRUEBA TÉCNICA LITE THINKING - 2024 DESARROLLADOR JAVA Y SPRING BOOT

- [Código](#codigo)
- [Modelo entidad-relacion](#modelo-entidad-relación)
- [Vistas planteadas](#vistas-planteadas)
- [Archivo PDF descargado de inventario](#archivo-pdf-descargado-de-inventario)
- [Recursos utilizados en la construición de este proyecto](#recursos-utilizados-en-la-construición-de-este-proyecto)
- [Deploy de la aplicación en AWS](#deploy-de-la-aplicación-en-aws)

## [Codigo](#codigo)

Contenido a nivel superior del proyecto

```txt
.
├── frontend                               # Proyecto frontend reactjs
├── backend                                # proyecto Java Spring Boot
├── docker-compose.yml                     # documento de despliegue (entorno de desarrollo)
└── README.md                              # documentación general de la prueba
```

## Correr el proyecto en entorno local

Para esto podemos utiliza Docker y docker-compose, ingresamos al projecto __frontend__ (reactjs) usamos _npm install_ para descargar las dependencias y compilamos el proyecto frontend con __npm build__, luego ingresamos al proyecto backend compilamos con gradle ejemplo: __./gradlew build__. despues de haver compilado los proyectos, nos ubicamos en la raiz del proyecto para construir con docker-compose _docker-compose build_ y desplegamos con _docker_compose up -d_

```bash
cd frontend
npm install
npm run build

cd ..
cd backend
./gradlew build

cd..

docker-compose build
docker-compose up -d
```

Si usamos Docker desktop podremos observar los contenedores en ejecución correctamente

![docker-compose](/frontend/public/assets/images/docs/docker-compose.png)

## [Modelo Entidad-Relación](#modelo-entidad-relación)

Se planteó el siguiente modelo entidad-relación

![modelo-entidad-relacion](/frontend/public/assets/images/docs/modelo-entidad-relacion.png)

## [Vistas planteadas](#vistas-planteadas)

Vista Inicio de Sesion

![Inicio de sesion](/frontend/public/assets/images/docs/vista-inicio-sesion.png)

Vista Empresa

![Listado de Empresas](/frontend/public/assets/images/docs/vista-empresa.png)

Editar Empresa

![Listado de Empresas](/frontend/public/assets/images/docs/vista-empresa-editar.png)

Vista Listado de Empresas

![Listado de Empresas](/frontend/public/assets/images/docs/vista-empresa-lista.png)

Vista de Productos

![Nuevo Producto](/frontend/public/assets/images/docs/vista-producto.png)

Vista Listado de Productos

![Lista de Productos](/frontend/public/assets/images/docs/vista-producto-lista.png)

Vista Inventario

![inventario](/frontend/public/assets/images/docs/vista-inventario.png)

## [Archivo PDF descargado de inventario](#archivo-pdf-descargado-de-inventario)

Con la opción de descargar el inventario, se genera un archivo en Formato PDF con los productos registrados de la tabla productos extrae nombre, código y precio, y de la relación con la tabla empresas estrae el nombre el cual es la empresa con que fue registrado cada producto.

![inventario](/frontend/public/assets/images/docs/pdf-inventario.png)

## [Recursos utilizados en la construición de este proyecto](#recursos-utilizados-en-la-construición-de-este-proyecto)

### react

- matx-react [Admin Dashboard template](<https://github.com/uilibrary/matx-react>)

### java

- Spring Boot
- Gradle
- JPA
- Potgresql

## [Deploy de la aplicación en AWS](#deploy-de-la-aplicación-en-aws)

- Elastic Beanstalk
- Amazon RDS
- Amazon S3

Se utilizó __Elastic Beanstalk__ para el backend creado con Java y Spring Boot, Se utilizó el servicio __Amazon RDS__ para la base de datos _POSTGRES_, se utilizó __Amazon S3__ para desplegar el frontend creado con _reactjs_.

## Elastic Beanstalk

![Elastic Beanstalk](/frontend/public/assets/images/docs/elastic-beanstalk.png)

## Amazon RDS

![Amazon rds](/frontend/public/assets/images/docs/amazon-rds.png)

## Amazon S3

![Amazon s3](/frontend/public/assets/images/docs/amazon-s3.png)
