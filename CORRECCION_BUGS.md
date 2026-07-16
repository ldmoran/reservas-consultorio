# Corrección de bugs

Se corrigieron 2 fallos de navegación en el frontend que impedían usar el acceso de administrador y abrir el detalle de una reserva desde el panel.

## Bug 1: acceso al login de administrador roto

Antes:
- El botón de acceso administrador apuntaba a `/admin/login`, pero esa ruta no existía en el router.
- El flujo de login también intentaba enviar al usuario a `/admin/dashboard`, y esa ruta tampoco estaba registrada.

Cómo se corrigió:
- Se agregaron las rutas `/admin/login` y `/admin/dashboard` en el router principal.
- Se mantuvo compatibilidad con las rutas anteriores para no romper el resto del flujo.

## Bug 2: enlace al detalle de reserva incorrecto en el panel

Antes:
- Desde el panel de administración, el icono para ver una reserva navegaba a `/admin/reserva/:id`.
- El router solo reconocía `/reserva/:id`, por lo que el enlace llevaba a una ruta inexistente.

Cómo se corrigió:
- Se alineó el enlace del panel para que apunte a `/reserva/:id`.
- Además, se dejó registrada también la ruta `/admin/reserva/:id` como alias para compatibilidad.