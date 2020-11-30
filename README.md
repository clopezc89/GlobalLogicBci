# GlobalLogicBci

_Servicio que permite crear y obtener usuarios creados. Inicialmente se hace una precarga en una BBDD Postgresql 
embebida de 4 usuarios con sus respectivas validaciones._

## Comenzando 🚀

_Estas instrucciones te permitirán obtener una copia del proyecto para hacerlo funcionar en tu maquina local._


### Pre-requisitos 📋

_Que cosas necesitas para desplegar el servicio en tu maquina local._

```
Java 8.
Gradle.
Puerto 8080 disponible.
La BBDD asigna cualquier puerto disponible.
Postman (Pero puede ser cualquier otro).
Intellij Idea (Pero puede ser cualquier otro).
```

### Ejecucion en entorno local 🔧

_Para ejecutar el proyecto en tu maquina local sigue las siguientes instrucciones._

_Clona el proyecto a tu maquina local_

_Para compilar el servicio puedes ejecutar las tareas gradle desde tu IDE_

```
clean
build
bootRun
```

_Para arrancar el servicio_

```
Con la tarea bootRun se ha levantado el servicio en el puerto 8080
```

_Ahora puedes consumir el servicio_

```
GET → http://localhost:8080/usuario/autenticar -> Para obtener un token valido

```

```
Incluir en la peticion el token obtenido en el endpoint anterior como header
Authorization Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI0MTFmOTkzMi0wMDk0LTQ3M2YtOGQ2Ny1kYjQ4YjdhYTdkZTEiLCJpYXQiOjE2MDY2ODIwNzcsImV4cCI6MTYwNjY4MjU3N30.lAEcTPh-ZAONQYuwf6Dy2RzDxTIEueScztKNr2n9dcEdFIRPmOkbbXdY-6xh-tSJGfc41v1BTaRCEs9_iC7-Gw
GET → http://localhost:8080/usuario/all -> Para obtener todos los usuarios
```

```
Incluir en la peticion el token obtenido en el endpoint anterior como header
Authorization Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI0MTFmOTkzMi0wMDk0LTQ3M2YtOGQ2Ny1kYjQ4YjdhYTdkZTEiLCJpYXQiOjE2MDY2ODIwNzcsImV4cCI6MTYwNjY4MjU3N30.lAEcTPh-ZAONQYuwf6Dy2RzDxTIEueScztKNr2n9dcEdFIRPmOkbbXdY-6xh-tSJGfc41v1BTaRCEs9_iC7-Gw
POST → http://localhost:8080/usuario -> Para crear un usuario nuevo
{
    "name": Cristian,
    "email": "cristian@gmail.com",
    "password": Abcdas11#,
    "isActive": true,
    "lastLogin": "2020-11-29",
    "phones": [
        {
            "number": "123",
            "cityCode": "1",
            "contryCode": "2"
        },
        {
            "number": "456",
            "cityCode": "3",
            "contryCode": "4"
        }
    ]
}
```

```
Incluir en la peticion el token obtenido en el endpoint anterior como header
Authorization Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI0MTFmOTkzMi0wMDk0LTQ3M2YtOGQ2Ny1kYjQ4YjdhYTdkZTEiLCJpYXQiOjE2MDY2ODIwNzcsImV4cCI6MTYwNjY4MjU3N30.lAEcTPh-ZAONQYuwf6Dy2RzDxTIEueScztKNr2n9dcEdFIRPmOkbbXdY-6xh-tSJGfc41v1BTaRCEs9_iC7-Gw
GET → http://localhost:8080/{id} -> Para obtener un usuario especifico
```

## Test ⚙️

_Para ejecutar los test_

```
Si estan trabajando con Intellij Idea y tienen problemas con ejecutar los test
comprueben que los parametros de gradle (dentro de Preferencias) contengan los siguientes valores:

Build an run using : Intellij Idea
Run test using : Intellij Idea

Ahora dentro del apartado Runner deja las siguientes opciones marcadas:
Marcar la opcion Delegate IDE build/run action to gradle
Run test using : Gradle Test Runner

Finalmente tambien dentro de Preferencias:
Ir a Compiler -> Annotations Processor -> Marcar opcion enable annotation processing
```

_Cobertura de test_

```			
class	83% (25/30)  method	83% (90/108)  line	84% (232/274)
```

## Construido con 🛠️

_Herramientas Utilizadas_

* SpringBoot 2.0.1.RELEASE - Framework
* Gradle - Manejador de dependencias
* JPA - Java Persistence API
* Hibernate - ORM
* PostgreSQL - BD

## Consideraciones 🖇️

_Para montar efectivamente una arquitectura para entorno de microservicios se tendria que tener el consideracion las siguientes implementaciones de la OSS Netflix:_

```
Eureka como register service 
```

```
Zuul como Edge Service 
```

```
Ribbon como Load Balancer
```

```
Spring Cloud Config como Configuration Properties Management
```

```
Hystrix como Circuit Breaker
```

```
Feign como Declarative Rest Client
```


