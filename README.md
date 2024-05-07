# prueba-tecnica-lite-thinking

## PRUEBA TÉCNICA LITE THINKING - 2024 DESARROLLADOR JAVA Y SPRING BOOT

Contenido a nivel superior del proyecto

```txt
.
├── frontend                               # Proyecto frontend reactjs
├── backend                                # proyecto Java Spring Boot
├── docker-compose.yml                     # documento de despliegue (entorno de desarrollo)
└── README.md                              # documentación general de la prueba
```

## Objectivo de la prueba

Validar tus conocimientos y técnica en desarrollo de aplicaciones en las tecnologías __JAVA__, __SPRING BOOT__ y __REACT__ y despliegue en __AWS__. No está descrito en la prueba, pero tener en cuenta las buenas practicas de desarrollo (Principios SOLID, pruebas unitarias, documentación, entre otros), estilo y patrones de arquitectruera.

## Descripción de la prueba: Construir una aplicación que exponga las siguientes vistas

a) Vista __Empresa__ con un formulario que capture la siguiente información:

- NIT (Llave primaria).
- Nombre de la empresa.
- Dirección.
- Teléfono.

b) Vista de __Productos__ con un formulario que capture la siguiente información:

- Código.
- Nombre del producto.
- Características.
- Precio en varias monedas.
- Empresa.

c) Vista de __Inicio de Sesión__ con un formulario que capture la información del usuario: correo y contraseña.

d) Vista de __inventario__ con un formulario que permita la descarga de un PDF con la información de esa tabla y adicional utilizar alguna API de AWS para poder enviar ese PDF a un correo deseado.

e) Deben existir dos tipos de usuarios:

- __Administrador__ Tiene acceso a las funciones de eliminación, registro y/o edición de una empresa.
Adicionalmente, ese usuario podrá registrar productos por empresa y guardarlos en una tabla inventario, donde se vean los productos por empresa.

- __Externo__ Puede visualizar las empresas como visitante.

f) El modelo entidad-relación de la base de datos para guardar la información anterior debe contener: Empresa, Producto, Categoria, Cliente y Ordenes. Asegúrate que la Base de Datos que plantees cumpla con los siguientes requisitos:

- Un Producto puede pertenecer a múltiples Categorías.
- Un Cliente puede tener multiples Órdenes.
- Las ordenes pueden tener múltiples Productos.

g) la contraseña utilizada debe estar encriptada para autenticación del Usuario __Administrador__

h) Publique tu aplicación en en servidor en la nube de AWS.

## __Entregables:__

- Enlace de la aplicación desplegada en AWS.
- Usuario y contraseña de los tipos de usuario: __Administrador__ y __Externo__.
- Enlace del repositorio donde esta almacenado el código fuente.
- Readme de todo el proyecto.

>## __Nota:__
>
> La funcionalidad de la aplicación es totalmente abierta a tu análisis y diseño, es parte de lo que se pretende evaluar, así que eres libre de definir la forma de capturar y generar la información. Además utilizar las buenas prácticas de la arquitectura de software para el desarrollo de aplicaciones web modernas.
>
>Toda la información registrada en los formularios debe ir almacenada en una base de datos.
>
>Cualquier duda o inquietud te puedes comunicar al WhatsApp __+57 3246859004__
