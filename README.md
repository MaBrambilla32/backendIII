# Sistema Bancario Multi-Canal con Patrón Backend For Frontend (BFF)
**Asignatura:** Desarrollo Backend III (PBY2203) - Duoc UC  
**Grupo:** Experiencia 2 - Semana 4  

---

## 1. Estrategia de Implementación de BFF (Justificación Técnica)

Para este sistema se seleccionó la estrategia de **Microservicios Desacoplados e Independientes por Canal** (`bff-web`, `bff-mobile`, `bff-atm`), descartando un único API Gateway monolítico. Las razones son:

* **Optimización de Payloads y Consumo:** El canal móvil requiere respuestas ultraligeras y truncadas (resúmenes y últimas 3 transacciones) para conservar batería y ancho de banda en redes móviles, mientras que la web necesita estructuras completas y detalladas.
* **Segregación del Perímetro de Seguridad:** El canal de Cajeros Automáticos (ATM) no comparte el ciclo de vida de usuario web/móvil; requiere autenticación de hardware/terminal (`x-atm-key`) y validación de PIN en operaciones atómicas.
* **Aislamiento de Fallos y Despliegue:** Un pico de concurrencia o caída en el canal web o móvil no compromete la disponibilidad operativa de la red crítica de cajeros automáticos.

---

## 2. Estructura del Proyecto
* `/core_legacy` (Puerto 8080): Fuente de datos central y persistencia (`data.sql`).
* `/bff-web` (Puerto 8081): Respuestas extendidas para navegadores web.
* `/bff-mobile` (Puerto 8082): Respuestas ligeras y acotadas para apps móviles.
* `/bff-atm` (Puerto 8083): Operaciones críticas de consulta y giro con validación de saldo.

---

## 3. Guía de Ejecución

Levantar cada servicio en terminales independientes con Maven:

```bash
# 1. Core Legacy
cd core_legacy && ./mvnw spring-boot:run

# 2. BFF Web
cd bff-web && ./mvnw spring-boot:run

# 3. BFF Móvil
cd bff-mobile && ./mvnw spring-boot:run

# 4. BFF ATM
cd bff-atm && ./mvnw spring-boot:run 
