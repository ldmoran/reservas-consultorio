const { test, expect } = require('@playwright/test');

const ADMIN_EMAIL = 'admin@reservas.com';
const ADMIN_PASSWORD = 'password';

test.describe('Pruebas E2E de reservas', () => {
  test('Login admin y acceso al panel', async ({ page }) => {
    await page.goto('/admin/login');

    await page.fill('input[type="email"]', ADMIN_EMAIL);
    await page.fill('input[type="password"]', ADMIN_PASSWORD);
    await page.click('button:has-text("Iniciar Sesión")');

    await expect(page).toHaveURL(/.*\/admin\/dashboard$/);
    await expect(page.locator('text=Panel de Administración')).toBeVisible();
  });

  test('Navegar a formulario de reserva y enviar una reserva', async ({ page }) => {
    await page.goto('/reservar');

    await page.fill('input[name="nombre"]', 'Test Usuario');
    await page.fill('input[name="telefono"]', '+57123456789');
    await page.fill('input[name="email"]', 'testuser@example.com');

    await page.selectOption('select[name="idServicio"]', { index: 1 });
    await page.fill('input[name="fecha"]', '2026-12-31');
    await page.selectOption('select[name="hora"]', '10:00');
    await page.fill('textarea[name="observaciones"]', 'Prueba E2E reserva');

    await page.click('button:has-text("Confirmar Reserva")');

    await expect(page.locator('text=Reserva creada exitosamente')).toBeVisible({ timeout: 10000 });
  });

  test('Filtro de reservas en panel admin', async ({ page }) => {
    await page.goto('/admin/login');

    await page.fill('input[type="email"]', ADMIN_EMAIL);
    await page.fill('input[type="password"]', ADMIN_PASSWORD);
    await page.click('button:has-text("Iniciar Sesión")');
    await expect(page).toHaveURL(/.*\/admin\/dashboard$/);

    await page.selectOption('select[name="estado"]', 'Pendiente');

    await expect(page.locator('text=Reservas (')).toBeVisible();
    await expect(page.locator('tr:has-text("Pendiente")').first()).toBeVisible();
  });

  test('Admin puede cerrar sesión desde el panel', async ({ page }) => {
    await page.goto('/admin/login');
    await page.fill('input[type="email"]', ADMIN_EMAIL);
    await page.fill('input[type="password"]', ADMIN_PASSWORD);
    await page.click('button:has-text("Iniciar Sesión")');
    await expect(page).toHaveURL(/.*\/admin\/dashboard$/);

    await page.click('button:has-text("Cerrar Sesión")');
    await expect(page).toHaveURL('http://localhost:3000/');
    await expect(page.locator('text=Sistema de Reservas')).toBeVisible();
  });

  test('Login admin con credenciales inválidas muestra error', async ({ page }) => {
    await page.goto('/admin/login');
    await page.fill('input[type="email"]', 'wrong@reservas.com');
    await page.fill('input[type="password"]', 'badpass');
    await page.click('button:has-text("Iniciar Sesión")');

    await expect(page.locator('text=Error al iniciar sesión')).toBeVisible({ timeout: 10000 });
  });

  test('Validación de formulario de reserva muestra errores con campos vacíos', async ({ page }) => {
    await page.goto('/reservar');
    await page.click('button:has-text("Confirmar Reserva")');

    await expect(page.locator('text=El nombre es obligatorio')).toBeVisible();
    await expect(page.locator('text=El teléfono es obligatorio')).toBeVisible();
    await expect(page.locator('text=El email es obligatorio')).toBeVisible();
    await expect(page.locator('text=Debes seleccionar un servicio')).toBeVisible();
    await expect(page.locator('text=La fecha es obligatoria')).toBeVisible();
    await expect(page.locator('text=La hora es obligatoria')).toBeVisible();
  });
});
