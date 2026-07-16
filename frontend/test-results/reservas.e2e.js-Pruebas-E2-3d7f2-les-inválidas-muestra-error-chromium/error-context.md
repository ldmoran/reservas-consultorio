# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: reservas.e2e.js >> Pruebas E2E de reservas >> Login admin con credenciales inválidas muestra error
- Location: tests\reservas.e2e.js:61:3

# Error details

```
Error: expect(locator).toBeVisible() failed

Locator: locator('text=Error al iniciar sesión')
Expected: visible
Timeout: 10000ms
Error: element(s) not found

Call log:
  - Expect "toBeVisible" with timeout 10000ms
  - waiting for locator('text=Error al iniciar sesión')

```

```yaml
- link "Volver al inicio":
  - /url: /
- heading "Acceso Administrador" [level=2]
- paragraph: Ingresa tus credenciales para acceder al panel de administración
- text: Email
- textbox "admin@reservas.com": wrong@reservas.com
- text: Contraseña
- textbox "Tu contraseña": badpass
- button
- button "Iniciar Sesión"
- heading "💡 Credenciales de prueba:" [level=4]
- paragraph: "Email: admin@reservas.com Contraseña: password"
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
  58 |     await expect(page.locator('text=Sistema de Reservas')).toBeVisible();
  59 |   });
  60 | 
  61 |   test('Login admin con credenciales inválidas muestra error', async ({ page }) => {
  62 |     await page.goto('/admin/login');
  63 |     await page.fill('input[type="email"]', 'wrong@reservas.com');
  64 |     await page.fill('input[type="password"]', 'badpass');
  65 |     await page.click('button:has-text("Iniciar Sesión")');
  66 | 
> 67 |     await expect(page.locator('text=Error al iniciar sesión')).toBeVisible({ timeout: 10000 });
     |                                                                ^ Error: expect(locator).toBeVisible() failed
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