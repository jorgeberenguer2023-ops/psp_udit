# Reto 2 · Pipeline de Auditoría UDITversum (Fase 1)

**Módulo:** 0490 · Programación de Servicios y Procesos  
**Autor:** Randel  
**Tecnología:** Java + ProcessBuilder  
**RA vinculado:** RA1 · Programación de aplicaciones compuestas por varios procesos  

---

## 📺 Qué es esta app

Un programa de consola en Java que simula la primera fase de una auditoría mediante la ejecución y coordinación de procesos concurrentes en el sistema operativo.

### Flujo de la aplicación:
1. Lanza dos procesos `ping` en paralelo.
2. Espera a que ambos terminen utilizando `waitFor()`.
3. Obtiene el código de salida (`exit value`) de cada proceso.
4. Evalúa el resultado combinado mediante una condición lógica:
   - Si ambos devuelven `0` (éxito) $\rightarrow$ Abre `notepad.exe`.
   - Si alguno falla $\rightarrow$ Abre `calc.exe`.

---

### Diagrama de Flujo

```text
                  ┌──────────────┐
                  │  Auditoría   │
                  │   (Java)     │
                  └──────┬───────┘
                         │ 
            ┌────────────┴────────────┐
            │ start()          start()│
            ▼                         ▼
      ┌───────────┐             ┌───────────┐
      │  ping A   │             │  ping B   │   ← Procesos en paralelo
      └─────┬─────┘             └─────┬─────┘
            │ waitFor()               │ waitFor()
            └───────────┬─────────────┘
                        ▼
                 Códigos de salida
                        │
            ┌───────────┴───────────┐
            ▼                       ▼
      Bloc de Notas            Calculadora
```

> 📸 <img width="808" height="433" alt="Captura de pantalla 2026-10-08 202739" src="https://github.com/user-attachments/assets/20a5962f-044a-49c9-83c6-9b0a76d57c45" />


---

## 🧠 Antes de empezar: Planificación

* **¿Qué me pide el reto?** Coordinar dos procesos externos en paralelo, recoger sus códigos de salida y tomar una decisión en función de ambos de manera simultánea.
* **¿Qué parte del Reto 1 reutilizo?** El uso de `ProcessBuilder`, el método `.start()`, `.waitFor()` y la lectura de códigos de salida.
* **¿Qué es nuevo y me da respeto?** El paralelismo real: lanzar ambos procesos antes de ejecutar cualquier `waitFor()` y coordinar dos resultados concurrentes.

### Mi plan en 5 pasos:
1. Crear dos objetos `ProcessBuilder` con los comandos de `ping`.
2. Lanzar ambos procesos de manera consecutiva **sin esperar** entre ellos.
3. Guardar las referencias en dos objetos `Process`.
4. Invocar los dos `waitFor()` al final para recoger los resultados.
5. Evaluar la condición lógica y abrir la aplicación correspondiente.

### Predicciones iniciales

| Escenario | Código esperado | App resultante |
| :--- | :--- | :--- |
| Dos pings a `127.0.0.1` | `0` y `0` | Bloc de Notas (`notepad.exe`) |
| Uno válido y otro inexistente | `0` y `≠ 0` | Calculadora (`calc.exe`) |
| Dos direcciones inexistentes | `≠ 0` y `≠ 0` | Calculadora (`calc.exe`) |

* **Predicción de tiempo:**
  * Ejecución en paralelo: $\approx 3$ segundos.
  * Ejecución secuencial (comparativa): $\approx 6$ segundos.

---

## 🎯 Objetivo del reto

Pasar de ejecutar procesos de forma lineal y bloqueante a coordinar varios procesos simultáneos haciendo uso de:
* `ProcessBuilder`
* Paralelismo real
* Sincronización con `waitFor()`
* Códigos de salida del sistema operativo
* Operadores lógicos avanzados (`&&`)
* Gestión robusta de excepciones (`IOException` e `InterruptedException`)

---

## 🛠️ Componentes y conceptos utilizados

| Componente | Para qué se usa | Con mis palabras |
| :--- | :--- | :--- |
| **ProcessBuilder** | Preparar comandos del SO | Construye la orden exacta que quiero lanzar al sistema. |
| **start()** | Ejecutar el proceso | Lo lanza de inmediato y permite que mi programa continúe. |
| **Process** | Representa el proceso en ejecución | Me da el control sobre el subproceso y permite vigilar su estado. |
| **waitFor()** | Esperar a que termine | Bloquea la ejecución del hilo principal hasta que finaliza el subproceso. |
| **Código de salida** | Saber si el proceso fue bien o mal | `0` significa éxito; cualquier otro número indica error. |
| **&& (AND lógico)** | Ambas condiciones deben cumplirse | Solo si los dos pings van bien se ejecuta la primera acción. |
| **try / catch** | Manejar errores del SO | Evita que el programa se caiga inesperadamente ante fallos externos. |
| **InterruptedException** | Controlar interrupciones de hilos | Excepción obligatoria al utilizar `waitFor()`. |

> **Mis objetos actuales:** `p1` (lanza un ping a `127.0.0.1`) y `p2` (lanza un ping a `error.invalid`).

---

## 🔀 Secuencial vs. Paralelo

### Fragmento de código clave:
```java
Process p1 = new ProcessBuilder("ping", "-n", "1", "127.0.0.1").start();
Process p2 = new ProcessBuilder("ping", "-n", "1", "error.invalid").start();

int salida1 = p1.waitFor();
int salida2 = p2.waitFor();
```

### Tabla de verdad aplicada:
| Código A | Código B | A OK? | B OK? | Condición (`A && B`) | App ejecutada |
| :---: | :---: | :---: | :---: | :---: | :---: |
| `0` | `0` | ✔️ | ✔️ | **True** | Bloc de Notas (`notepad.exe`) |
| `0` | `≠ 0` | ✔️ | ❌ | **False** | Calculadora (`calc.exe`) |
| `≠ 0` | `0` | ❌ | ✔️ | **False** | Calculadora (`calc.exe`) |
| `≠ 0` | `≠ 0` | ❌ | ❌ | **False** | Calculadora (`calc.exe`) |

> **¿Cambiaría algo si usara el operador OR (`\|\|`)?**  
> Sí: abriría el Bloc de Notas incluso con un solo ping correcto, lo cual rompería la lógica estricta de validación del reto.

---

## 🚀 Cómo ejecutar
* Probado y verificado en entornos **Windows 10 / 11**  
* Comandos nativos utilizados: `ping -n`, `notepad.exe`, `calc.exe`.

---

## 🔍 Diario de decisiones

| Qué intentaba | Qué pasó | Qué aprendí |
| :--- | :--- | :--- |
| Paralelizar los pings | Tardaba el doble inicialmente | Tenía un `waitFor()` colocado antes del segundo `start()`. |
| Capturar códigos de salida | Uno devolvía error | Una dirección inválida arroja siempre un código distinto de `0`. |
| Abrir las aplicaciones | El Notepad no se abría | Había un pequeño error tipográfico en el nombre del ejecutable. |

---

## 🧠 Análisis técnico

1. **Secuencial vs. Paralelo:**  
   Los pings se ejecutan simultáneamente porque ambas llamadas a `.start()` ocurren de forma consecutiva antes de invocar cualquier `.waitFor()`. Si intercalaras un `waitFor()` antes del segundo `start()`, el segundo proceso se retrasaría y perderíamos el paralelismo.
2. **Código de salida:**  
   El método `waitFor()` devuelve un `int`. El valor `0` denota éxito total, mientras que cualquier valor diferente de cero representa una incidencia o fallo en la ejecución.
3. **Lógica condicional:**  
   ```java
   if (salida1 == 0 && salida2 == 0) {
       new ProcessBuilder("notepad.exe").start();
   } else {
       new ProcessBuilder("calc.exe").start();
   }
   ```
   Se emplea el operador `&&` porque el Bloc de Notas solo debe abrirse si ambos destinos responden correctamente.
4. **Gestión de excepciones:**  
   Se produce una `IOException` si el sistema operativo no localiza el ejecutable (por ejemplo, escribiendo mal `notepad.exe` o intentando ejecutarlo en un entorno Linux donde dicho comando no existe de forma nativa).

---

## 🛡️ Preparación para la defensa

* ✔️ Añadir un tercer ping para enriquecer el pipeline.
* ✔️ Mostrar el PID (Process ID) por consola.
* ✔️ Medir el tiempo de ejecución en milisegundos.
* ✔️ Modificar la lógica de negocio dinámicamente.
* ✔️ Provocar deliberadamente una `IOException`.
* ✔️ Explicar con claridad el concepto de paralelismo.

> **Lo que más me costó:** Recordar que el paralelismo real depende estrictamente del orden en el que se ejecutan los `.start()` frente alos `.waitFor()`.

---

## 🧭 Del Reto 1 al Reto 2

* **¿Qué hacía mi Reto 1 que ya no sirve?** Esperaba a que terminara cada proceso de manera secuencial antes de lanzar el siguiente.
* **¿Qué cambié?** Separé por completo las fases de lanzamiento (`start()`) y las de espera (`waitFor()`).
* **Ventaja del paralelo:** Menor tiempo total de ejecución del programa.
* **Problema nuevo:** Saber coordinar múltiples resultados concurrentes al mismo tiempo.
* **Si tuviera 50 procesos:** Utilizaría estructuras de datos como `List<Process>` o arrays para gestionarlos de forma dinámica mediante bucles.

---

## 🧠 Qué he aprendido

* `.start()` lanza procesos sin bloquear el hilo principal.
* `.waitFor()` bloquea la ejecución hasta que el subproceso finaliza.
* **Paralelismo** equivale a lanzar todas las tareas antes de empezar a recoger resultados.
* El código de salida `0` significa éxito absoluto.
* El operador `&&` asegura que se cumplan todas las condiciones de éxito simultáneamente.
* Una `IOException` difiere por completo de un código de error devuelto por un comando (como un ping fallido).
* Las decisiones lógicas son fiables porque dependen directamente de los estados devueltos por el sistema operativo.

---

## 🐞 Dificultades superadas

* **Dificultad 1:**  
  * *Síntoma:* La aplicación tardaba demasiado en completarse.  
  * *Causa:* Un `waitFor()` colocado demasiado pronto en el código.  
  * *Solución:* Reubicar las esperas al final tras todos los `.start()`.  
  * *Cómo evitarlo:* Recordar la regla de oro del paralelismo en procesos.

---

## 🪞 Autoevaluación

| Puedo explicar... | No | +/- | Sí |
| :--- | :---: | :---: | :---: |
| Qué hace `ProcessBuilder` | ◻ | ◻ |  |
| Por qué `start()` no espera | ◻ | ◻ |  |
| Qué línea hace que corran en paralelo | ◻ | ◻ |  |
| Qué pasa si muevo de sitio el `waitFor()` | ◻ | ◻ |  |
| Qué devuelve exactamente `waitFor()` | ◻ | ◻ |  |
| Por qué uso el operador `&&` | ◻ | ◻ |  |
| Cuándo salta una `IOException` | ◻ | ◻ |  |

* **Predicciones:** ¡Acerté todas!
* **Lo que haría diferente si repitiera:** Probar más combinaciones de direcciones IP y nombres de dominio para analizar un abanico más amplio de códigos de salida.
* **Lo que quiero preguntar en clase:** Cómo gestionar eficientemente un volumen elevado de procesos concurrentes sin perder legibilidad en el código.

---

## 🤝 Declaración de autoría

Confirmo que he realizado este reto aplicando mis propios razonamientos, código y conclusiones, y que me encuentro plenamente capacitado para defenderlo en clase.

---

## 📂 Estructura del repositorio

```text
src/main/java/org/example/PildoraParalelismo.java
README.md
```
