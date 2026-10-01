# 🎬 UDITflix Monitor

Programa desarrollado en **Java** para comprobar la disponibilidad de los vídeos del catálogo de **UDITflix** mediante procesos externos.

El programa no descarga ni reproduce vídeos reales. Para simular la comprobación de disponibilidad utiliza el comando `ping` mediante `ProcessBuilder`.

---

## 📋 Descripción

El programa comprueba individualmente los siguientes vídeos:

* 🎨 Animación 3D
* 🎮 Videojuegos
* 💻 Kotlin
* 📱 Android
* 🦋 Flutter

Cada vídeo tiene asociado un nombre y una dirección que se utiliza para realizar la comprobación.

Los datos se almacenan utilizando una **matriz de dos dimensiones**, tal como requiere el ejercicio.

---

## ⚙️ Funcionamiento

El programa realiza los siguientes pasos:

1. Guarda los vídeos y sus direcciones en una matriz.
2. Recorre la matriz utilizando un bucle `for`.
3. Obtiene el nombre y la dirección de cada vídeo.
4. Crea un proceso externo mediante `ProcessBuilder`.
5. Ejecuta el proceso mediante `start()`.
6. Obtiene y muestra el PID del proceso.
7. Lee la información proporcionada por el proceso.
8. Espera a que el proceso termine utilizando `waitFor()`.
9. Determina si el vídeo está activo o caído.
10. Muestra el resultado en la consola.

Estos son precisamente los elementos principales solicitados en el reto.

---

## 🧩 Tecnologías utilizadas

* **Java**
* `ProcessBuilder`
* `Process`
* `BufferedReader`
* `InputStreamReader`
* `getInputStream()`
* `waitFor()`
* Matrices bidimensionales
* Bucle `for`

El ejercicio establece el uso obligatorio de estas herramientas.

---

## 🔎 Comprobación mediante Ping

Para simular la disponibilidad de los vídeos se utiliza el comando:

```text
ping
```

Las direcciones que no existen representan vídeos que están caídos.

Para representar un vídeo activo se utiliza:

```text
127.0.0.1
```

Esta dirección corresponde al propio ordenador y es la utilizada en la práctica para simular contenido disponible.

---

## 🧮 Matriz de vídeos

Los vídeos se almacenan en una matriz de dos dimensiones:

```java
String[][] videos = {
    {"Animación 3D", "192.168.0.555"},
    {"Videojuegos", "10.0.0.123"},
    {"Kotlin", "55.55.55.55"},
    {"Android", "127.0.0.1"},
    {"Flutter", "127.0.0.1"}
};
```

Cada fila representa un vídeo.

Por ejemplo:

```text
videos[i][0] → Nombre del vídeo
videos[i][1] → Dirección que se comprueba
```

---

## 🔄 Bucle `for`

El programa utiliza un bucle `for` para recorrer todas las filas de la matriz:

```java
for (int i = 0; i < videos.length; i++) {
```

En cada vuelta del bucle se obtiene un vídeo diferente y se realiza su comprobación.

---

## 🚀 ProcessBuilder

`ProcessBuilder` permite crear y ejecutar procesos externos desde Java.

En este proyecto se utiliza para ejecutar `ping`:

```java
ProcessBuilder pb =
    new ProcessBuilder("ping", "-n", "1", direccion);
```

Después se inicia el proceso mediante:

```java
Process proceso = pb.start();
```

---

## 🆔 PID

Después de iniciar el proceso, el programa obtiene su identificador:

```java
long pid = proceso.pid();
```

El **PID** es el identificador que tiene ese proceso mientras se está ejecutando en el sistema operativo.

El PID puede ser diferente en cada ordenador y en cada ejecución.

---

## 📥 getInputStream()

El resultado generado por el proceso se obtiene mediante:

```java
proceso.getInputStream()
```

Después se utiliza `BufferedReader` para poder leer la información línea por línea:

```java
BufferedReader br = new BufferedReader(
    new InputStreamReader(proceso.getInputStream())
);
```

De esta forma el programa puede analizar la respuesta proporcionada por `ping`.

---

## ⏳ waitFor()

El método:

```java
proceso.waitFor();
```

hace que el programa espere hasta que el proceso externo haya terminado.

Esto permite que la comprobación finalice antes de continuar con el siguiente vídeo.

---

## ✅ Detección del estado

El programa analiza la información obtenida del `ping`.

Si encuentra `TTL`, considera que la dirección ha respondido:

```java
if (linea.contains("TTL")) {
    activo = true;
}
```

Después se muestra el resultado:

```text
ESTADO: ACTIVO
```

o:

```text
ESTADO: CAÍDO
```

---

## 🖥️ Ejemplo de salida

La salida esperada tiene una estructura similar a:

```text
========================================
 UDITFLIX - CATÁLOGO
========================================

[VÍDEO] Animación 3D
PID: 15240
ESTADO: CAÍDO

[VÍDEO] Videojuegos
PID: 16324
ESTADO: CAÍDO

[VÍDEO] Kotlin
PID: 17480
ESTADO: CAÍDO

[VÍDEO] Android
PID: 18592
ESTADO: ACTIVO

[VÍDEO] Flutter
PID: 19640
ESTADO: ACTIVO

========================================
 COMPROBACIÓN FINALIZADA
========================================
```

Los números de PID serán diferentes dependiendo del ordenador y de la ejecución.

---

## 📁 Estructura del proyecto

```text
UDITflixMonitor/
│
├── src/
│   └── org/
│       └── example/
│           └── UDITflixMonitor.java
│
├── README.md
└── .gitignore
```

---

## ▶️ Ejecución

### 1. Abrir el proyecto

Abrir el proyecto utilizando **IntelliJ IDEA**.

### 2. Ejecutar el programa

Ejecutar la clase:

```text
UDITflixMonitor
```

También se puede ejecutar desde una terminal si el proyecto está correctamente configurado.

---

## 📚 Conceptos aprendidos

Este proyecto permite practicar:

* Matrices bidimensionales.
* Bucles `for`.
* Procesos externos.
* `ProcessBuilder`.
* `Process`.
* Lectura de la salida de un proceso.
* `BufferedReader`.
* PID de procesos.
* `waitFor()`.
* Comprobación de disponibilidad mediante `ping`.

---

## 👨‍💻 Autor

**UDITflix Monitor**

Proyecto realizado como práctica de programación Java.

