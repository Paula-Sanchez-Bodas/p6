# P5
Aplicación web que usa Spring JPA para persistir los datos de un API REST de gestión de usuarios.
El API permite el registro de nuevos usuarios y su identificación mediante email y password.
Una vez identificados, se emplea una cookie de sesión para autenticar las peticiones que permiten 
a los usuarios leer, modificar y borrar sus datos. También existe un endpoint para cerrar la sesión.  

## Endpoints

// TODO#1: rellena la tabla siguiente analizando el código del proyecto

| Método | Ruta                  | Descripción                           | Respuestas |
|--------|-----------------------|---------------------------------------|------------|
| Post   | /api/users            | Creación de un usuario nuevo          | 201 o 409  |
| Post   | /api/users/me/session | Intento de hacer login                | 201 o 401  |
| Delete | /api/users/me/session | Logout                                | 204 o 403  |
| Get    | /api/users/me         | Obtiene datos de un usuario conectado | 200 o 403  |
| Put    | /api/users/me         | Actualizar los datos del usuario      | 200 o 403  |
| Delete | /api/users/me         | Eliminar un usuario                   | 204 o 403  |


## Comandos 

- Construcción: 
  ```sh
  ./mvnw clean package
  ```

- Ejecución: 
  ```sh
  ./mvnw spring-boot:run
  ```

- Tests:
  ```sh
  ./mvnw test
  ```
