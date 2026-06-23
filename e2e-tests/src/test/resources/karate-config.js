function fn() {
  var config = {
    clienteUrl: karate.properties['cliente.url'] || 'http://localhost:8081',
    cuentaUrl: karate.properties['cuenta.url'] || 'http://localhost:8082'
  };
  config.apiKey = karate.properties['api.key'] || 'devsu-secret-key';
  karate.configure('headers', { 'X-API-Key': config.apiKey });
  karate.configure('connectTimeout', 5000);
  karate.configure('readTimeout', 5000);
  return config;
}
