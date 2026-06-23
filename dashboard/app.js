"use strict";

const CLIENTE = "/api/cliente";
const CUENTA = "/api/cuenta";

function dashboard() {
  return {
    apiKey: "devsu-secret-key",
    auto: true,
    health: { cliente: null, cuenta: null },

    clientes: [],
    cuentas: [],
    movimientos: [],
    reporte: [],
    logs: [],

    // Estado de los formularios
    fCliente: { nombre: "Juan Pérez", genero: "MASCULINO", edad: 28, identificacion: "0999888777", clienteId: "juanp", contrasena: "clave1234" },
    fCuenta: { numeroCuenta: "478758", tipoCuenta: "AHORRO", saldoInicial: 2000, clienteId: 1 },
    fMov: { cuentaId: 1, tipoMovimiento: "DEPOSITO", valor: 100, idem: "" },
    fReporte: { cliente: 1, fechaInicio: "2000-01-01", fechaFin: "2999-12-31" },

    init() {
      this.refreshAll();
      setInterval(() => { if (this.auto) this.refreshAll(); }, 5000);
    },

    // --- HTTP + utilidades ---
    async http(method, url, { body, idem } = {}) {
      const headers = { "Content-Type": "application/json" };
      if (method !== "GET") headers["X-API-Key"] = this.apiKey.trim();
      if (idem) headers["Idempotency-Key"] = idem;
      const r = await fetch(url, { method, headers, body: body ? JSON.stringify(body) : undefined });
      let data = null;
      try { data = await r.json(); } catch (_) {}
      return { status: r.status, ok: r.ok, data };
    },

    log(msg, level = "info") {
      this.logs.unshift({ time: new Date().toLocaleTimeString(), msg, level });
      if (this.logs.length > 60) this.logs.pop();
    },

    logResult(label, res) {
      const msg = `${label} → HTTP ${res.status}` + (res.data ? " " + JSON.stringify(res.data) : "");
      const lvl = res.ok ? "ok" : ([401, 404, 422, 429].includes(res.status) ? "warn" : "bad");
      this.log(msg, lvl);
    },

    // --- Lecturas ---
    async setHealth(svc, base) {
      try {
        const r = await this.http("GET", `${base}/actuator/health`);
        this.health[svc] = (r.ok && r.data && r.data.status) ? r.data.status : "DOWN";
      } catch (_) { this.health[svc] = "DOWN"; }
    },
    async loadClientes() { this.clientes = (await this.http("GET", `${CLIENTE}/clientes?size=50`)).data || []; },
    async loadCuentas() { this.cuentas = (await this.http("GET", `${CUENTA}/cuentas?size=50`)).data || []; },
    async loadMovimientos() { this.movimientos = (await this.http("GET", `${CUENTA}/movimientos?size=50`)).data || []; },

    refreshAll() {
      this.setHealth("cliente", CLIENTE);
      this.setHealth("cuenta", CUENTA);
      this.loadClientes(); this.loadCuentas(); this.loadMovimientos();
    },

    // --- Escrituras (CRUD) ---
    async crearCliente() {
      const f = this.fCliente;
      const body = { ...f, edad: +f.edad, direccion: "N/A", telefono: "0000000", estado: true };
      this.logResult("Crear cliente", await this.http("POST", `${CLIENTE}/clientes`, { body }));
      this.loadClientes();
    },
    async eliminarCliente(id) {
      this.logResult(`Eliminar cliente ${id}`, await this.http("DELETE", `${CLIENTE}/clientes/${id}`));
      this.loadClientes();
    },
    async crearCuenta() {
      const f = this.fCuenta;
      const body = { numeroCuenta: f.numeroCuenta, tipoCuenta: f.tipoCuenta, saldoInicial: +f.saldoInicial, estado: true, clienteId: +f.clienteId };
      this.logResult("Crear cuenta", await this.http("POST", `${CUENTA}/cuentas`, { body }));
      this.loadCuentas();
    },
    async registrarMovimiento() {
      const f = this.fMov;
      const body = { cuentaId: +f.cuentaId, tipoMovimiento: f.tipoMovimiento, valor: +f.valor };
      const idem = (f.idem || "").trim() || null;
      this.logResult("Registrar movimiento", await this.http("POST", `${CUENTA}/movimientos`, { body, idem }));
      this.loadCuentas(); this.loadMovimientos();
    },
    async generarReporte() {
      const f = this.fReporte;
      const url = `${CUENTA}/reportes?cliente=${f.cliente}&fechaInicio=${f.fechaInicio}&fechaFin=${f.fechaFin}`;
      const res = await this.http("GET", url);
      if (res.ok) { this.reporte = res.data || []; this.log(`Reporte: ${this.reporte.length} línea(s)`, "ok"); }
      else this.logResult("Reporte", res);
    }
  };
}

document.addEventListener("alpine:init", () => window.Alpine.data("dashboard", dashboard));
