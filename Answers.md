# Laboratorio 5 - Blueprints Security

**Elaborado por**

Sebastián Villarraga

Juan Carlos Leal Cruz

## **Parte 1. Revisión de la Configuración de Seguridad**
En el `SecurityConfig`, el enrutamiento y la protección de los endpoints se manejan completamente dentro del método `filterChain`.

Spring Security utiliza el objeto `HttpSecurity` para crear una cadena de filtros que intercepta todas las peticiones HTTP entrantes. El método específico que se usa para definir las reglas de acceso es `.authorizeHttpRequests()`. Dentro de este método, las peticiones se evalúan de arriba hacia abajo usando `.requestMatchers()`, lo que significa que la primera regla que coincida con la ruta URL de la petición es la que se aplica.

### **1. Endopoint Públicos**
Los endpoints públicos son aquellos a los que cualquier persona puede acceder sin necesidad de un token o autenticación. Se definen utilizando el método `.permitAll()`.
- Salud y Login: "/actuator/health": Se usa para verificar si la aplicación está en ejecución.
  - `/auth/login`: El endpoint donde los usuarios enviarán sus credenciales para recibir un token JWT.
  - Regla: `.requestMatchers("/actuator/health", "/auth/login").permitAll()`

- Documentación de la API (Swagger): "/v3/api-docs/", "/swagger-ui/", "/swagger-ui.html". Estas rutas exponen la interfaz gráfica y los datos de la documentación de tu API.
  - Regla: `.requestMatchers("/v3/api-docs/", "/swagger-ui/", "/swagger-ui.html").permitAll()`

### **4. Endpoints Protegidos**
Los endpoints protegidos requieren que el usuario envíe un token JWT válido en las cabeceras (headers) de su petición HTTP. Tu configuración define dos niveles de protección:
- Protección de la API por Scope
  - Cualquier endpoint que comience con "/api/" (por ejemplo, `/api/blueprints`) está fuertemente protegido. No solo se requiere que el usuario esté autenticado, sino que su token JWT también debe contener permisos específicos (llamados `scopes`).
  - Regla: `.requestMatchers("/api/**").hasAnyAuthority("SCOPE_blueprints.read", "SCOPE_blueprints.write")`
  
  Para acceder a cualquier parte de la API, el token debe tener forzosamente el scope blueprints.read o el scope blueprints.write.

- Protección General (Regla de captura):
  De igual forma se tiene definida una regla para las URLs que no están definidas de forma explícita.
  - Regla: `.anyRequest().authenticated()`
  
  Esto lo que hace es que si un usuario intenta acceder a cualquier otro endpoint de la aplicación, debe al menos haber iniciado sesión (estar autenticado con un JWT válido), incluso si la ruta no requiere un scope específico.

---

## **Parte 2. Implementación de seguridad**
### **1. Inclusión parte 1 BluePrints**
Se añade a este laboratorio las clases del laboratorio 4, es decir, se le añade persistencia usando el mismo método que se usó en la parte 1. Para ello se copiaron las carpetas y archivos dentro de este nuevo repo, de tal forma que la protección de Endpoints se va a realizar sobre la persistencia creada para la parte 1 del lab.
#### **❗️ IMPORTANTE. Ejecución**
Para la correcta ejecución de este lab es preciso ejectuar el siguiente comando:
```bash
docker compose up -d
```

De esta forma, se usa el archivo `docker-compose.yml`para levantar el contenedor de Docker que nos ayudara a persistir los datos en una PostgreSQL, tal como se explica en [Lab4_ARSW_BluePrint_Part1]([https://github.com/tu-usuario/tu-repo](https://github.com/Sebastian-villarraga/LAB04_ARSW_26))




