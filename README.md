![Veronica Logo](https://raw.githubusercontent.com/rolandopalermo/Veronica/master/static/veronica.jpg)

[![PayPal Donate](http://ionicabizau.github.io/badges/paypal.svg?style=plastic&colorB=68B7EB)]()
[![Platforms](https://img.shields.io/badge/plaform-windows%20%7C%20linux%20%7C%20macOS-blue.svg?style=plastic&colorB=68B7EB)]()
[![Release](https://img.shields.io/badge/version-1.0.0-green.svg?style=plastic&colorB=68B7EB)]()

`Veronica` es una API Rest de código abierto utilizada para la emisión y autorización de comprobantes electrónicos según la normativa vigente del [Sistema de Rentas Internas del Ecuador](http://www.sri.gob.ec/). El proyecto ha sido desarrollado a través de una aplicación [Spring-Boot 1.5.9.RELEASE](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot/1.5.9.RELEASE). Adicionalmente, `Veronica` almacena los comprobantes en una base de datos PostgreSQL lo cual le permite realizar posteriores consultas más allá de las comunes como por ejemplo, consultar detalles de facturas, consultar totales o listar comprobantes por emisor o receptor.

Todo comprobante electrónico gestionado a través de `Veronica` manejará un ciclo de vida basado en 4 fases:

<p align="center">
<img src="https://raw.githubusercontent.com/rolandopalermo/veronica/master/static/veronica_ciclo_vida.png" width="500">
</p>

Cotenidos
=================
- [Software requerido](#software-requerido)
- [Pasos previos](#pasos-previos)
- [Instalación](#instalación)
	- [Despliegue estándar](#coffee-despliegue-estándar)
- [Seguridad](#seguridad)
	- [Obtención de tokens OAuth2.0](#obtención-de-tokens-oauth20)
- [Documentación](#documentación)
	- [Swagger](#swagger)
	- [Postman](#postman)
- [Bitácora](#bitácora)
- [Autor](#autor)
- [Colaboradores](#colaboradores)
- [Patrocinadores](#patrocinadores)

## Software requerido
- JDK 1.8.0_121
- Apache Maven 3.5.3
- PostgreSQL 11.1-1

## Pasos previos
- [Instalar y configurar Maven](https://www.mkyong.com/maven/how-to-install-maven-in-windows/)

`Veronica` posee una lista de dependencias que no se encuentran disponibles en el repositorio remoto de Maven por lo que se tendrá que hacer la instalación de forma manual. Para esto, ejecutar los comandos listados a continuación.
```bash
$ cd /veronica/additional_libs
$ mvn install:install-file -Dfile=jss-4.2.5.jar -DgroupId=org.mozilla -DartifactId=jss -Dversion=4.2.5 -Dpackaging=jar
$ mvn install:install-file -Dfile=MITyCLibAPI-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=api -Dversion=1.0.4 -Dpackaging=jar
$ mvn install:install-file -Dfile=MITyCLibCert-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=cert -Dversion=1.0.4 -Dpackaging=jar
$ mvn install:install-file -Dfile=MITyCLibOCSP-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=ocsp  -Dversion=1.0.4 -Dpackaging=jar
$ mvn install:install-file -Dfile=MITyCLibPolicy-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=policy -Dversion=1.0.4 -Dpackaging=jar
$ mvn install:install-file -Dfile=MITyCLibTrust-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=trust -Dversion=1.0.4 -Dpackaging=jar
$ mvn install:install-file -Dfile=MITyCLibTSA-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=tsa -Dversion=1.0.4 -Dpackaging=jar
$ mvn install:install-file -Dfile=MITyCLibXADES-1.0.4.jar -DgroupId=es.mityc.javasign -DartifactId=xades -Dversion=1.0.4 -Dpackaging=jar
$ mvn install:install-file -Dfile=xmlsec-1.4.2-ADSI-1.0.jar -DgroupId=org.apache.xmlsec-adsi -DartifactId=xmlsec-adsi -Dversion=1.4.2 -Dpackaging=jar
```
## Instalación
`Veronica` puede ser ejecutado como una aplicación Spring-Boot la cual requiere la instalación previa de PostgreSQL.

### :coffee: Despliegue estándar

1. Abrir una consola o shell y crear la base de datos.
```bash
$ psql -U postgres
# CREATE DATABASE "veronica";
# \q
```
2. Crear la estructura de tablas ejecutando el script **veronica.sql**.
```bash
$ cd veronica-open-api/src/sql
$ psql -U postgres veronica < veronica.sql
```

4. Agregar la siguiente entrada al final del archivo **postgresql.conf** y reiniciar el servidor de base de datos.
```bash
encrypt.key = 8qxBjzCdQkwdpu
```

5. Instalar `Veronica`.
```bash
$ cd veronica-open-api
$ mvn clean package install
```

`Veronica` proporciona dos perfiles de despliegue: Desarrollo y Producción. Cada uno de estos perfiles posee un archivo de configuración situado en **/veronica/src/filters**. Para desplegar el proyecto con el perfil adecuado, indicar el ambiente como argumento de ejecución.

`Desarrollo`
```bash
$ cd veronica-open-api/app
$ mvn spring-boot:run -Pdevelopment
```

`Producción`
```bash
$ cd veronica-open-api/app
$ mvn spring-boot:run -Pproduction
```

## Seguridad
Al instalar la base de datos de `Veronica`, automáticamente se crearán dos usuarios con sus respectivas contraseñas y roles.

| Usuario | Contraseña |     Rol    |
|:-------:|:----------:|:----------:|
| admin   | veronica   | ROLE_ADMIN |
| user    | veronica   | ROLE_USER  |

### Obtención de tokens OAuth2.0
Para generar un token para el usuario admin, por ejemplo, podermos ejecutar el siguiente comando curl:
```bash
curl -u veronica:veronica -X POST http://localhost:8080/veronica/oauth/token -H "Accept:application/json" -d "username=admin&password=veronica&grant_type=password"
```
También en el archivo **/veronica/Verónica API Reference.postman_collection.json** de postman podemos encontrar un ejemplo de llamada a este endpoint.

Con el token generado podemos hacer uso de cualquier de los endpoints que ofrece `Veronica` a través de su API Rest. Para esto, debemos utilizar el token generado a través de una llamada con autenticación Bearer, tal como se muestra a continuación:
```bash
curl http://localhost:8080/veronica/api/v1.0/facturas/2204201901109170199100120010010001467560014675614/archivos/xml -H "Authorization: Bearer 77ed953e-b3b6-4ea1-820e-2e9acc702293"
```

## Documentación
### Swagger
Para acceder, debemos utilizar los usuarios indicados en la sección anterior.

http://localhost:8080/veronica/swagger-ui.html

### Postman
`Veronica` también pone a disposición de los usuarios una colección de llamadas y ejemplos que se encuentra en la ruta **/src/postman/Verónica API Reference.postman_collection.json**.

## Bitácora

- V1: 2018-04-12, Primera versión.
- V2: 2018-04-27, Perfiles de Maven.
- V3: 2018-04-28, Habilitar Swagger2.
- V4: 2018-11-10, Generación de RIDEs.
- V5: 2018-11-19, Integración con Postman.
- V6: 2019-01-09, Integración con PostgreSQL.
- V7: 2019-02-21, Soporte para Retenciones y Guías de remisión.
- V8: 2019-05-18, Soporte para Notas de débito.
- ~~V9: 2019-05-22, Soporte para Docker con Fabric8.~~
- V10: 2019-05-28, Seguridad con OAuth2.0.
- V11: 2019-12-27, Mover dependencias genéricas de Verónica a Sonatype

## Autor
| [![](https://avatars1.githubusercontent.com/u/11875482?v=4&s=80)](https://github.com/rolandopalermo) |
|-|
| [@RolandoPalermo](https://github.com/rolandopalermo) |

## Colaboradores

| [![](https://avatars2.githubusercontent.com/u/24358710?s=80&v=4)](https://github.com/XaviMontero) | [![](https://avatars0.githubusercontent.com/u/3452663?s=80&v=4)](https://github.com/XaviMontero) |  [![](https://avatars0.githubusercontent.com/u/211490?s=80&v=4)](https://github.com/vperilla) | [![](https://avatars0.githubusercontent.com/u/4059893?s=80&v=4)](https://github.com/andresluzu) 
|-|-|-|-|
| [@XaviMontero](https://github.com/XaviMontero) | [@Japstones](https://github.com/japstones) | [@vperilla](https://github.com/vperilla) | [@andresluzu](https://github.com/andresluzu) |

## Patrocinadores
Apoya este proyecto haciéndote patrocinador. Tu logotipo aparecerá aquí con un enlace a tu sitio web. [[Conviértete en un patrocinador](mailto:rolando.roc@gmail.com)]
