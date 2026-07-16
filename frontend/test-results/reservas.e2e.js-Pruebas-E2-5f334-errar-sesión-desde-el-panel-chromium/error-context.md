# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: reservas.e2e.js >> Pruebas E2E de reservas >> Admin puede cerrar sesión desde el panel
- Location: tests\reservas.e2e.js:49:3

# Error details

```
Error: expect(locator).toBeVisible() failed

Locator: locator('text=Sistema de Reservas')
Expected: visible
Error: strict mode violation: locator('text=Sistema de Reservas') resolved to 2 elements:
    1) <h1 class="text-4xl font-bold mb-4">Sistema de Reservas</h1> aka getByRole('heading', { name: 'Sistema de Reservas' })
    2) <p class="text-xs text-secondary-500">…</p> aka getByText('© 2026 Sistema de Reservas.')

Call log:
  - Expect "toBeVisible" with timeout 5000ms
  - waiting for locator('text=Sistema de Reservas')

```

# Page snapshot

```yaml
- generic [ref=e3]:
  - status [ref=e9]: Inicio de sesión exitoso
  - generic [ref=e10]:
    - generic [ref=e13]:
      - generic [ref=e14]:
        - img [ref=e15]
        - heading "Sistema de Reservas" [level=1] [ref=e17]
        - paragraph [ref=e18]: Plataforma integral para la gestión eficiente de reservas y servicios. Administra tu negocio con herramientas profesionales.
      - generic [ref=e19]:
        - generic [ref=e22]: Gestión completa de reservas
        - generic [ref=e25]: Panel administrativo avanzado
        - generic [ref=e28]: Reportes y analytics en tiempo real
    - generic [ref=e32]:
      - generic [ref=e33]:
        - heading "Iniciar Sesión" [level=2] [ref=e34]
        - paragraph [ref=e35]: Accede a tu panel administrativo
      - generic [ref=e36]:
        - generic [ref=e37]:
          - generic [ref=e38]:
            - generic [ref=e39]: Correo Electrónico
            - textbox "admin@reservas.com" [ref=e40]
          - generic [ref=e41]:
            - generic [ref=e42]: Contraseña
            - generic [ref=e43]:
              - textbox "Tu contraseña" [ref=e44]
              - button [ref=e45] [cursor=pointer]:
                - img [ref=e46]
          - button "Iniciar Sesión" [ref=e49] [cursor=pointer]
        - generic [ref=e50]:
          - heading "Credenciales de prueba" [level=4] [ref=e51]:
            - img [ref=e52]
            - text: Credenciales de prueba
          - generic [ref=e54]:
            - paragraph [ref=e55]:
              - strong [ref=e56]: "Email:"
              - text: admin@reservas.com
            - paragraph [ref=e57]:
              - strong [ref=e58]: "Contraseña:"
              - text: password
      - paragraph [ref=e60]: © 2026 Sistema de Reservas. Izquierdos Reservados.
```

# Test source

```ts
  1  | const { test, expect } = require('@playwright/test');
  2  | 
  3  | const ADMIN_EMAIL = 'admin@reservas.com';
  4  | const ADMIN_PASSWORD = 'password';
  5  | 
  6  | test.describe('Pruebas E2E de reservas', () => {
  7  |   test('Login admin y acceso al panel', async ({ page }) => {
  8  |     await page.goto('/admin/login');
  9  | 
  10 |     await page.fill('input[type="email"]', ADMIN_EMAIL);
  11 |     await page.fill('input[type="password"]', ADMIN_PASSWORD);
  12 |     await page.click('button:has-text("Iniciar Sesión")');
  13 | 
  14 |     await expect(page).toHaveURL(/.*\/admin\/dashboard$/);
  15 |     await expect(page.locator('text=Panel de Administración')).toBeVisible();
  16 |   });
  17 | 
  18 |   test('Navegar a formulario de reserva y enviar una reserva', async ({ page }) => {
  19 |     await page.goto('/reservar');
  20 | 
  21 |     await page.fill('input[name="nombre"]', 'Test Usuario');
  22 |     await page.fill('input[name="telefono"]', '+57123456789');
  23 |     await page.fill('input[name="email"]', 'testuser@example.com');
  24 | 
  25 |     await page.selectOption('select[name="idServicio"]', { index: 1 });
  26 |     await page.fill('input[name="fecha"]', '2026-12-31');
  27 |     await page.selectOption('select[name="hora"]', '10:00');
  28 |     await page.fill('textarea[name="observaciones"]', 'Prueba E2E reserva');
  29 | 
  30 |     await page.click('button:has-text("Confirmar Reserva")');
  31 | 
  32 |     await expect(page.locator('text=Reserva creada exitosamente')).toBeVisible({ timeout: 10000 });
  33 |   });
  34 | 
  35 |   test('Filtro de reservas en panel admin', async ({ page }) => {
  36 |     await page.goto('/admin/login');
  37 | 
  38 |     await page.fill('input[type="email"]', ADMIN_EMAIL);
  39 |     await page.fill('input[type="password"]', ADMIN_PASSWORD);
  40 |     await page.click('button:has-text("Iniciar Sesión")');
  41 |     await expect(page).toHaveURL(/.*\/admin\/dashboard$/);
  42 | 
  43 |     await page.selectOption('select[name="estado"]', 'Pendiente');
  44 | 
  45 |     await expect(page.locator('text=Reservas (')).toBeVisible();
  46 |     await expect(page.locator('tr:has-text("Pendiente")').first()).toBeVisible();
  47 |   });
  48 | 
  49 |   test('Admin puede cerrar sesión desde el panel', async ({ page }) => {
  50 |     await page.goto('/admin/login');
  51 |     await page.fill('input[type="email"]', ADMIN_EMAIL);
  52 |     await page.fill('input[type="password"]', ADMIN_PASSWORD);
  53 |     await page.click('button:has-text("Iniciar Sesión")');
  54 |     await expect(page).toHaveURL(/.*\/admin\/dashboard$/);
  55 | 
  56 |     await page.click('button:has-text("Cerrar Sesión")');
  57 |     await expect(page).toHaveURL('http://localhost:3000/');
> 58 |     await expect(page.locator('text=Sistema de Reservas')).toBeVisible();
     |                                                            ^ Error: expect(locator).toBeVisible() failed
  59 |   });
  60 | 
  61 |   test('Login admin con credenciales inválidas muestra error', async ({ page }) => {
  62 |     await page.goto('/admin/login');
  63 |     await page.fill('input[type="email"]', 'wrong@reservas.com');
  64 |     await page.fill('input[type="password"]', 'badpass');
  65 |     await page.click('button:has-text("Iniciar Sesión")');
  66 | 
  67 |     await expect(page.locator('text=Error al iniciar sesión')).toBeVisible({ timeout: 10000 });
  68 |   });
  69 | 
  70 |   test('Validación de formulario de reserva muestra errores con campos vacíos', async ({ page }) => {
  71 |     await page.goto('/reservar');
  72 |     await page.click('button:has-text("Confirmar Reserva")');
  73 | 
  74 |     await expect(page.locator('text=El nombre es obligatorio')).toBeVisible();
  75 |     await expect(page.locator('text=El teléfono es obligatorio')).toBeVisible();
  76 |     await expect(page.locator('text=El email es obligatorio')).toBeVisible();
  77 |     await expect(page.locator('text=Debes seleccionar un servicio')).toBeVisible();
  78 |     await expect(page.locator('text=La fecha es obligatoria')).toBeVisible();
  79 |     await expect(page.locator('text=La hora es obligatoria')).toBeVisible();
  80 |   });
  81 | });
  82 | 
```