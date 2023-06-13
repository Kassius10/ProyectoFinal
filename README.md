# Proyecto Final De Grado - GINKANA GO

Este proyecto ha sido desarrollado por [Daniel Alejandro Carmona Rodriguez](https://github.com/Kassius10) estudiante del **IES LUIS VIVES, cursando en 2º DAM**.

En este repositorio podemos encontrar tanto el servicio api que hay detras de la aplicación y la misma aplicación montada en NativeScript.

- - -

## INTRODUCCIÓN

El siguiente proyecto se basa en una aplicación para móvil Android que se centra en la interacción con el usuario para que pueda como el nombre indica hacer realizar una Ginakana. En la aplicación habrá eventos en los que el usuario podrá participar, donde tendrá que seguir las indicaciones de un mapa para ir a la zona donde tiene que encontrar las pistas. Cuyas pistas son QR que tendrán que leer mediante la app para poder avanzar y seguir disfrutando del evento.

La aplicación tiene un sistema de login y registro, que utiliza jwt para crear un token con el que podrán acceder al servicio api que está detrás de todo el funcionamiento y almacenamiento a la base de datos.

El proyecto esta hecho para que el usuario pueda disfrutar de eventos que se irán creando para que conozca lugares o simplemente pase un divertido momento.

- - -

## Tecnologias

- SERVICIO API

El servicio api ha sido creado con **Kotlin** y con **Ktor** de base para la generación de todo el backend de la api. Además ha sido utilizado **Koin** para la inyección de dependecias y **JWT** para la seguridad en la api.

- APLICACIÓN MOVIL

Como ya se ha dicho anteriormente, la aplicación ha sido creado utilizando el framework **NativeSript Core 7**, sin la utilización de cualquier otro framework de apoyo. Además que se ha utilizado como lenguaje **TypeScript**.

La aplicación por ahora esta diseñada para dispositivos **Android**, pero más adelante se plantea también llevar acabo para dispositivos **IOS**.
## Estructura del proyecto

El proyecto sigue una estructura organizada, separando los componentes clave de la siguiente manera:

- **Servicios:** Encargados de implementar la lógica de negocio y coordinar las operaciones del sistema.
- **Repositorios:** Capa de acceso a datos que interactúa con la base de datos u otros sistemas de almacenamiento.
- **DTOs (Data Transfer Objects):** Objetos utilizados para transferir datos entre diferentes capas o servicios.
- **Modelos:** Responsables de almacenar y presentar los datos al usuario.
- **Mapeadores:** Transforman datos entre diferentes objetos o estructuras.
- **Serializadores:** Convierten objetos en formato JSON para su envío.
- **Configuraciones:** Archivos de configuración del sistema en ktor.
- **Rutas:** Definen las operaciones disponibles y manejan la comunicación entre la aplicación y la base de datos.

- - -

## Serialización y DTOs

En este proyecto, se utiliza Kotlin Serialization para enviar y recibir datos en formato JSON. Los DTOs se emplean para enviar o recibir información específica entre los diferentes componentes.

## Servicios y Repositorios

Los servicios actúan como intermediarios para validar los datos entre la entrada y el almacenamiento. Los repositorios se encargan de interactuar con la base de datos u otros sistemas de almacenamiento. Ambos siguiendo un CRUD.

## Rutas y Autorización

Las rutas se utilizan como el puente de comunicación entre la aplicación y la base de datos. Se ha implementado un sistema de autorización con JWT (JSON Web Tokens) para proteger las rutas y controlar el acceso. Por lo tanto no se puede acceder a ciertas rutas de la api si no existe un token que se pueda validar por el servicio.

## Pruebas y Testing

Tanto los repositorios como los servicios han sido sometidos a pruebas unitarias, utilizando mocks en caso necesario. Además, todas las rutas han sido comprobadas su funcionamiento mediante el uso de Postman, contemplando todos los casos posibles, ya sean correctos o errores al introducir datos o autenticación.

- - -

## Aplicación

La aplicación esta montada bajo una estructura generada por NativeScript, es decir, mediante un template. Siendo luego modificada y adaptada a los cambios necesarios que requeria el proyecto, como el login, eventos, vistas de desafio y perfil.


Se ha integrado una herramienta HTTP para realizar las llamadas al servicio de la API y asi poder obtener los datos que son necesarios o en otros casos, para poder almacenar la información que el usuario indica en la aplicación. 

La aplicación contiene distintas vistas:
- Una vista de login y registro.
- Una vista principal donde se pueden ver todos los eventos disponibles donde se puede participar.
- Una vista de detalle del evento en concreto.
- Las vistas de los propios desafios.
- Vistas dependientes de las vistas de desafios.
- Vista de ranking del evento seleccionado.
- Vista de perfil y edición del perfil del usuario.

El sistema de inicio de sesión y registro permiten además de realizar las consultas propias al servicio para almacenar y verificar los datos. El token recibido al iniciar sesión se almacena de manera segura en el dispositivo mediante el uso de Secure Storage.

Finalmente, en la vista de los desafios la cual es más importante, existe un elemento que muestra el mapa donde se puede ver la ruta que se genera desde donde el usuario se encuentra a donde debe ir para buscar la pista del desafio. Una vez descubierto la pista, la aplicación permite atraves de una opción escanera el qr que haya encontrado, verificar si es correcto y permitir continuar al siguiente desafio.

La lectura de qr ha sido posible gracias al plugin **BarcodeScanner**, el cual permite la opción de escaneo.

La creación de mapa y toda la información sobre ella, se ha conseguido mediante los servicios Api de Google, teniendo acceso a la Api de Maps y a la Api de direcciones.


