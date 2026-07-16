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

- Pendiente.
- Pendiente.

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

- Pendiente.
- Pendiente.

## Pruebas End-to-End (E2E)

- Pendiente.
- Pendiente.