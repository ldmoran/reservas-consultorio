# Evidencias del Taller

## Integrantes

- David Moran
- Alison Miranda
- Gabriel Vivanco

---

# Actividades realizadas

## Corrección de Bugs

### Bug 1: Acceso al login de administrador

**Problema detectado**

El botón de acceso al administrador redireccionaba hacia la ruta:

```
/admin/login
```

Sin embargo, dicha ruta no estaba registrada dentro del router principal. Además, después de iniciar sesión el sistema intentaba navegar hacia:

```
/admin/dashboard
```

ruta que tampoco existía, impidiendo el acceso al panel administrativo.

**Solución implementada**

- Se agregó la ruta `/admin/login`.
- Se agregó la ruta `/admin/dashboard`.
- Se mantuvo compatibilidad con el resto del flujo de navegación del sistema.

---

### Bug 2: Enlace al detalle de una reserva

**Problema detectado**

Desde el panel administrativo el botón para visualizar una reserva redireccionaba hacia:

```
/admin/reserva/:id
```

No obstante, el router únicamente tenía registrada la ruta:

```
/reserva/:id
```

provocando un error de navegación.

**Solución implementada**

- Se actualizó el enlace para utilizar la ruta correcta `/reserva/:id`.
- Se registró adicionalmente `/admin/reserva/:id` como alias para mantener compatibilidad.

---

## Corrección de la estructura del Frontend

Durante la ejecución del proyecto se detectó que el frontend no podía iniciar debido a la ausencia de la carpeta **public** y del archivo **index.html**, mostrando el siguiente error:

```
Could not find a required file.
Name: index.html
Searched in: frontend/public
```

Para solucionar este inconveniente se realizaron las siguientes acciones:

- Creación de la carpeta `frontend/public`.
- Creación del archivo `frontend/public/index.html`.
- Restauración de la estructura mínima requerida por React para permitir la ejecución del frontend.

Con esta corrección el proyecto pudo ejecutarse correctamente.

---

# Distribución de Pruebas

Cada integrante deberá desarrollar:

- **2 pruebas unitarias.**
- **2 pruebas End-to-End (E2E).**

En total el proyecto contará con:

- **6 pruebas unitarias.**
- **6 pruebas End-to-End (E2E).**

---

# David Moran

## Pruebas Unitarias

- Se creó el archivo `backend/src/test/java/com/reservas/service/ReservaServiceTest.java` para validar la lógica del servicio de reservas sin depender de la base de datos.
- Prueba 1: Simula la creación de una nueva reserva usando un email que no existe en el sistema. Verifica que el servicio crea un nuevo usuario y guarda correctamente la reserva con estado "Pendiente".
- Prueba 2: Simula la actualización del estado de una reserva existente. Verifica que el servicio recupera la reserva, cambia el estado a "Confirmada" y devuelve los datos actualizados.
- Prueba 3: Simula la consulta de reservas filtradas por estado. Verifica que el servicio recupera una lista de reservas con estado "Pendiente" y las convierte correctamente a objetos `ReservaResponse`.

## Pruebas End-to-End (E2E)

- Pendiente.
- Pendiente.

---

# Alison Miranda

## Pruebas Unitarias

- Pendiente.
- Pendiente.

## Pruebas End-to-End (E2E)

- Pendiente.
- Pendiente.

---

# Gabriel Vivanco

## Pruebas Unitarias

- Se agregaron 3 pruebas unitarias adicionales al archivo `backend/src/test/java/com/reservas/service/ReservaServiceTest.java`, complementando la cobertura de la lógica del servicio de reservas con casos de reutilización de datos y manejo de errores.
- Prueba 1: Simula la creación de una reserva usando un email que ya existe en el sistema. Verifica que el servicio reutiliza el usuario existente, no crea un usuario nuevo (nunca se llama a `usuarioRepository.save`) y asocia correctamente la reserva al usuario registrado.
- Prueba 2: Simula la creación de una reserva con un servicio inexistente. Verifica que el servicio lanza una excepción con el mensaje "Servicio no encontrado" y que no se guarda ninguna reserva en el repositorio.
- Prueba 3: Simula la actualización del estado de una reserva que no existe. Verifica que el servicio lanza una excepción con el mensaje "Reserva no encontrada" y que no se realiza ningún guardado en el repositorio.

## Pruebas End-to-End (E2E)

- Pendiente.
- Pendiente.