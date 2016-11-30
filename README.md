# GPE
## Gestión de Proyectos y Equipos

La aplicación **GPE**, _(Gestión de Proyectos y Equipos)_, es un sistema pensado para gestionar de forma más eficiente proyectos de software basados
en la metodología Métrica V3.
 
El alcance actual del proyecto se centrará en la planificación temporal de los proyectos, teniendo previstas futuras ampliaciones.

El proyecto cuenta actualmente con las siguientes funcionalidades:

* **Gestión de Proyectos**
    * Dashboard
    * Listado de proyectos
    * Listado de requerimientos
    * Creación de proyectos
    * Creación de requerimientos
    * Planificación temporal
* **Gestión de tareas**
    * Listado de tareas
    * Imputar horas trabajadas
    * Creación de tareas
    * Asignación de tareas
* **Gestión de usuarios**
    * Creación de usuarios
    * Listado de usuarios
    * Edición del perfil de usuario
    * Gestión de vacaciones
    * Gestión de ausencias
    * Gestión de horarios
* **Módulo de informes**
    * Informe de situación
    * Informe de imputaciones
    * Informe de usuarios
    
A continuación se detallará información sobre el proyecto. Para pasar a la guía rápida, pulse [aquí](#guiarapida)

## Estructura del Proyecto

El proyecto se divide en 4 módulos diferentes: `utils`, `data`, `batch` y `web`.

* El módulo `utils` es un proyecto java que contiene clases de utilidad para el resto de módulos.
* El módulo `data` es una aplicación de tipo **Spring Boot**, encargada de la capa de acceso a datos.
* El módulo `batch` es una aplicación web de tipo **Spring Boot**, encargada de planificar y realizar los trabajos diarios.
* El módulo `web` es una aplicación web de tipo **Spring Boot**, encargada de la visualización de la aplicación.


### Estructura de directorios

```
gpe/
  batch/
    src/
      main/
        java/
          batch/                  --> *directorios correspondientes a paquetes omitidos*
            appConfig/              --> Paquete con las clases de configuración
            jobs/                   --> Paquete con los distintos jobs
            listeners/              --> Paquete con los listeners   
            BatchApplication.java   --> Clase principal Spring Boot
        resources/
        webapp/
      test/
  data/
    src/
      main/
        java/
          data/
            converters/             --> Paquete con todos los convertidores
            dto/                    --> Paquete con los dtos necesarios
            enums/                  --> Enumerados de la aplicación (datos paramétricos)
            exceptions/             --> Excepciones
            filters/                --> Interfaces con los filtros disponibles para cada entidad
            model/                  --> Entidades JPA
            repositories/           --> Repositorios de JPA
            services/               --> Servicios de la aplicación
            DataConfiguration.java  --> Clase de configuración
      test/
  utils/
    src/
      main/
        java/
          utils/                    --> Paquete con todas las clases de utilidad
  web/
    src/
      main/
        java/
          web/                      --> *directorios correspondientes a paquetes omitidos*
            aspects/                --> Clases de AspectJ
            config/                 --> Paquete con las clases de configuración
            controllers/            --> Paquete con los controladores de JSF
            exceptions/             --> Paquete con las excepciones
            primefaces/             --> Paquete con las modificaciones de Primefaces
            utils/                  --> Paquete con las utilidades propias del proyecto web
            WebApplication.java     --> Clase principal Spring Boot
        resources/
        webapp/
          css/                      --> Carpeta con todos los archivos css
          error/                    --> Carpeta que contiene las páginas de error
          font/                     --> Carpeta con las fuentes
          img/                      --> Carpeta con las imágenes usadas en la aplicación
          js/                       --> Carpeta con los archivos javascript usados en la aplicación
          views/                    --> Carpeta con las distintas vistas de la aplicación
          WEB-INF/                  --> Carpeta conteniendo los archivos necesarios para formar el WAR
          index.xhtml               --> Página principal de la aplicación
          login.html                --> Página del login
      test/
```

## Arquitectura

### Instalación standalone

![diagramadesplieuge](https://github.com/PBarri/GPE/blob/master/lib/diagrama1.png)

### Docker

_En Construcción_

## Entorno de desarrollo

Para la ejecución del proyecto se han utilizado las siguientes tecnologías:

* Java jdk 1.8
* JBoss Wildfly 9+
* Gradle 3.1
* Oracle Database 11g
* Spring Boot 1.4.2
    * Spring Web
    * Spring Security
    * Spring Data JPA
    * Spring AOP
    * Spring Batch
    * Spring Integration
* JSF 2.2
* Primefaces 6.0
* jQuery 1.9
* QueryDsl 4
* Google Charts

## <a name="guiarapida"></a>Guía Rápida

### Instalar dependencias

Para gestionar las dependencias hemos usado la herramienta [gradle](https://gradle.org/).

Se ha preconfigurado `gradle` para automáticamente compilar y montar el proyecto, de forma que, en una terminal situada
en la raíz del proyecto, ejecutamos:

```
gradlew dist
```

Este comando realizará las siguientes acciones:

* Compilará el código fuente de todos los módulos
* Creará los archivos war de los módulos `batch` y `web`
* Moverá estos archivos a la carpeta `dist`

*_NOTA: Para instalar en Eclipse, necesitarás instalar el plugin  [Buildship](https://projects.eclipse.org/projects/tools.buildship), y después importar el proyecto._*

### Ejecutar la aplicación

_**NOTA:Una vez arrancada la aplicación, se podrá acceder a ella con el usuario y contraseña Admin/Admin**_

#### Docker

Se ha preconfigurado [Docker](https://www.docker.com/), junto con dos tareas de `gradle` para poder crear y ejecutar la aplicación
dentro de un contenedor de Docker.

```
gradlew dockerBuild
```

Esta tarea creará un contenedor de Docker con el nombre `pbarri\gpe`

```
gradlew dockerRun
```

Esta tarea ejecutará el contenedor anterior.

Después de ejecutar esta tarea, la aplicación estará disponible en `http://localhost:8080/GPE`.

*_NOTA: Necesitarás tener Docker instalado en tu ordenador para usar esta funcionalidad_*

#### Servidor de aplicaciones

Para ejecutar la aplicación, simplemente tendremos que ejecutar el comando de `gradle`

```
gradlew dist
```

Se creará una carpeta, `dist`, en la raíz del proyecto con los archivos war tanto de la parte batch como de la web.
 
Habrá que desplegar estos archivos en nuestro servidor de aplicaciones.

## Versionado de la aplicación

Este proyecto sigue el versionado semántico, con el formato `MAJOR.MINOR.PATCH`.

Dado una versión, habrá que incrementar:

* `MAJOR` cuando se hayan introducido cambios incompatibles con la API anterior. 
* `MINOR` cuando se ha introducido nueva funcionalidad compatible hacia atrás con la API existente. 
* `PATCH` para resolución de bugs con compatibilidad hacia atrás.

Ademas, se han incluido dos etiquetas, `SNAPSHOT` and `RELEASE` para distinguir entre candidatos a una nueva versión estable y las versiones estables.

Para ayudar con el versionado, se han incluído tareas de gradle para cambiar la versión. A continuación se muestran los comandos necesarios para crear
una nueva versión:

* `gradlew majorRelease dist`
* `gradlew minorRelease dist`
* `gradlew patchRelease dist`
* `gradlew majorSnapshot dist`
* `gradlew minorSnapshot dist`
* `gradlew patchSnapshot dist`
 
*NOTA: En la versión actual, el archivo `gradle.properties` debe ser subido al repositorio de forma manual después de cambiar la versión.*

## Testing

`SECCIÓN EN PROGRESO`


## Bibliografía

* [Documentación Metrica V3](http://administracionelectronica.gob.es/pae_Home/pae_Documentacion/pae_Metodolog/pae_Metrica_v3.html)
* [Documentación Spring](https://spring.io/)
* [Documentación Primefaces](http://www.primefaces.org/)
* [Documentación Gradle](http://gradle.org/)
* [Stackoverflow](http://stackoverflow.com/)


# Licencia

<a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/"><img alt="Licencia de Creative Commons" style="border-width:0" src="https://i.creativecommons.org/l/by-nc-sa/4.0/88x31.png" /></a><br /><span xmlns:dct="http://purl.org/dc/terms/" property="dct:title">GPE</span> by <span xmlns:cc="http://creativecommons.org/ns#" property="cc:attributionName">Pablo Barrientos</span> is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/">Creative Commons Reconocimiento-NoComercial-CompartirIgual 4.0 Internacional License</a>.
