# Mutant Service

_Servicio que permite determinar si un dna ingresado corresponde a mutante o humano. Ademas por cada verificacion se guarda en una base de datos los dna ingresados solo una vez y se marcan como mutante o humano.
Ademas se puede consumir otro servicio (/stats) que permite obtener las estadisticas de las verificaciones._

## Comenzando 🚀

_Estas instrucciones te permitirán obtener una copia del proyecto para hacerlo funcionar en tu maquina local y tambien para consumir el servicio directamente desde la nube._


### Pre-requisitos 📋

_Que cosas necesitas para desplegar el servicio en tu maquina local._

```
Java 8
Maven
Puerto 8081 disponible
Postman (Pero puede ser cualquier otro).

```

### Ejecucion en entorno local 🔧

_Para ejecutar el proyecto en tu maquina local sigue las siguientes instrucciones._

_Clona el proyecto a tu maquina local_

_Para generar el jar ejecuta los siguientes comando desde tu IDE o terminal_

```
mvn clean compile
mvn clean install
```

_Para arrancar el servicio ejecuta la siguiente intruccion_

```
java -jar mutant-service-0.0.1-SNAPSHOT.jar 
```

_Ahora puedes consumir el servicio_

```
POST → http://localhost:8081/mutant/
{ “dna”:["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"] }
```

```
GET → http://ec2-54-193-15-231.us-west-1.compute.amazonaws.com:8081/stats
```
### Ejecucion en la nube 🔧

_Para consumir el servicio alojado en la nube_

```
POST → http://ec2-54-193-15-231.us-west-1.compute.amazonaws.com:8081/mutant/
{ “dna”:["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"] }
```

```
GET → http://ec2-54-193-15-231.us-west-1.compute.amazonaws.com:8081/stats
```

## Test ⚙️

_Para ejecutar los test_

```
mvn test
```

_Cobertura de test_

```
class	85% (12/14)  method	89% (43/48)  line	93% (123/132)
```

## Construido con 🛠️

_Menciona las herramientas que utilizaste para crear tu proyecto_

* SpringBoot 2.1.8.RELEASE - Framework
* Maven 3.6.1 - Manejador de dependencias
* JPA - Java Persistence API
* Hibernate - ORM
* HikariCP - Pool ligero de conexiones JDBC
* PostgreSQL - BD
* AWS EC2 Intance
* AWS RDS PostgreSQL

## Consideraciones 🖇️

_Para montar efectivamente una arquitectura para entorno de microservicios se tendria que tener el consideracion las siguientes implementaciones de la OSS Netflix:_

```
Eureka como register service 
```

```
Zuul como edge service 
```

```
Ribbon como load balancer
```

```
Spring Cloud Config como configuration properties management
```

```
hystrix como circuit breaker
```

```
Feign como declarative rest client
```


