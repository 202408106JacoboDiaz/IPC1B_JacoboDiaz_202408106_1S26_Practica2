# Laboratorio de Introducción a la Programación y Computación 1
**Universidad San Carlos de Guatemala**  
**Facultad de Ingeniería**  
**Escuela de Ingeniería en Ciencias y Sistemas**
---
&nbsp;
&nbsp;
# Práctica 2: Manual Técnico
&nbsp;
&nbsp;
---
 **Estudiante** | Juan Jacobo Díaz Naola 
 **Carnet** | 202408106
 **Documento** | Manual Técnico |
 
---
 
## 1. Descripción General
 
Esta práctica consiste en una aplicación de escritorio desarrollada en Java que simula una carrera de escobas mágicas inspirada en el universo ficticio de Harry Potter. El jugador (usuario) compite contra la computadora en una pista donde aparecen premios y obstáculos de forma aleatoria. La carrera se visualiza en tiempo real mediante una interfaz gráfica construida con la librería Swing, y el movimiento de cada corredor es manejado por hilos independientes.
 
---
 
## 2. Estructura del Proyecto
 
El proyecto sigue el patrón de diseño **MVC (Model - View - Controller)**, organizado en los siguientes paquetes:
```
carrerasescobas/
├── Main.java
├── model/
│   ├── Personaje.java
│   ├── ElementoP.java
│   └── ResultadoMatch.java
├── controller/
│   ├── GPersonaje.java
│   ├── GMatch.java
│   └── MotorC.java
├── view/
│   ├── MainMenu.java
│   ├── CreacionPer.java
│   ├── EleccionPer.java
│   ├── PanelP.java
│   ├── VentanaC.java
│   └── PuntajesTOP.java
└── util/
    └── Estilos.java
└── Main.java
```
---
 
## 3. Librerías Implementadas
 
**JFreeChart 1.5.4**  
Empleada para generar la gráfica de barras en el módulo de Top de Puntajes. Se encarga de mostrar el mejor puntaje obtenido por cada jugador, con el eje X representando los nombres y el eje Y los puntos.
 
**iTextPDF 5.5.13.3**  
Empleada para exportar el TOP de Puntajes a un archivo PDF. Genera una tabla con los datos de cada jugador incluyendo nombre, escoba utilizada, puntos obtenidos y ganador de la partida.
--
Ambas librerías se gestionan automáticamente mediante **Maven**, declaradas como dependencias en el archivo `pom.xml`.
---
## 4. Descripción de los Módulos
 
### 4.1 Model (Modelo)
 
**Personaje.java**  
Representa a un corredor del juego. Almacena su código, nombre, casa y modelo de escoba. Contiene el método `getVelocidadMs()` que retorna el tiempo de espera en milisegundos según la escoba asignada: Nimbus 2000 (duerme 3000ms), Nimbus 2001 (duerme 2000ms) y Saeta de Fuego (duerme 1000ms); todas estas inspiradas en el universo de Harry Potter.
 
**ElementoP.java**  
Representa un elemento que aparece en la pista. Maneja tres tipos mediante un enumerado: SNITCH, BLUDGER y QUAFFLE. Cada tipo tiene una letra representativa, un puntaje y una penalización asociada. La Snitch otorga 150 puntos y termina la carrera, la Quaffle otorga 10 puntos y el Bludger agrega 2000ms de penalización al corredor que lo toca.
 
**ResultadoMatch.java**  
Almacena el resultado de una partida finalizada. Registra el nombre del jugador, nombre de la computadora, escoba utilizada, puntos de ambos, ganador y la fecha y hora exacta de finalización generada automáticamente con `LocalDateTime`.
 
### 4.2 Controller (Controlador)
 
**GPersonaje.java**  
Gestiona la lista de personajes disponibles. Implementa el patrón Singleton para garantizar una única instancia durante toda la ejecución. Precarga cuatro personajes al iniciar y permite agregar nuevos, validando que no existan nombres duplicados. También selecciona un oponente aleatorio para la computadora excluyendo al personaje elegido por el jugador.
 
**GMatch.java**  
Gestiona el historial de partidas jugadas. También implementa el patrón Singleton. Permite registrar nuevas partidas y calcular el Top de Puntajes, filtrando el mejor resultado de cada jugador y ordenándolos de mayor a menor.
 
**MotorC.java**  
Es el núcleo de la carrera. Crea dos hilos independientes, uno para el jugador y otro para la computadora, que se mueven a lo largo de la pista según la velocidad de su escoba. Utiliza `synchronized` para evitar condiciones de carrera al acceder a posiciones y elementos compartidos. Genera los elementos de la pista aleatoriamente al iniciar y notifica a la vista mediante la interfaz `CarreraListener` cada vez que hay una actualización o la carrera termina.
 
### 4.3 View (Vista)
 
**MainMenu.java**  
Ventana inicial del sistema. Contiene los botones para acceder a los módulos de Jugar, Crear Personaje, Top de Puntajes y Salir.
 
**CreacionPer.java**  
Permite registrar un nuevo personaje ingresando nombre, casa y modelo de escoba. Valida que el nombre no esté vacío ni repetido.
 
**EleccionPer.java**  
Muestra la lista de personajes disponibles para que el jugador elija con cuál competir. La computadora elige su oponente automáticamente de forma aleatoria al iniciar la carrera.
 
**PanelP.java**  
Componente gráfico personalizado que extiende `JPanel` y sobreescribe `paintComponent()` para dibujar la pista, los corredores y los elementos en tiempo real usando `Graphics2D`.
 
**VentanaC.java**  
Ventana principal de la carrera. Muestra las tarjetas de ambos personajes, el panel de la pista, el marcador de puntos y posición en tiempo real, y el resultado final al terminar.
 
**PuntajesTOP.java**  
Muestra la gráfica de barras con los mejores puntajes usando JFreeChart y permite exportar el resultado a PDF con iTextPDF.
 
### 4.4 Util (Utilidades)
 
**EstilosUI.java**  
Centraliza la paleta de colores, fuentes y la creación de componentes reutilizables como botones y etiquetas, manteniendo una apariencia consistente en toda la aplicación.
 
---
 
## 5. Lógica General del Sistema
 
Al ejecutar la aplicación, se muestra el menú principal. El jugador (usuario) puede crear personajes nuevos o elegir uno existente para competir. Al iniciar la carrera, el motor genera aleatoriamente los elementos en la pista y lanza dos hilos: uno por cada corredor. Cada hilo avanza una posición por ciclo, esperando el tiempo definido por la escoba más cualquier penalización acumulada. Al llegar a la misma posición que un elemento activo, este se desactiva y aplica su efecto. La carrera termina cuando algún corredor toma la Snitch o llega a la meta, momento en que el resultado se registra en el gestor de partidas y se notifica a la vista.
 
---
 
## 6. Métodos Importantes
 
| Método | Clase | Descripción |
|--------|-------|-------------|
| `iniciar()` | MotorC | Lanza los dos hilos de la carrera |
| `correr(boolean)` | MotorC | Lógica de movimiento e interacción de cada corredor |
| `terminarCarrera()` | MotorC | Finaliza la carrera y registra el resultado |
| `generarElementos()` | MotorC | Genera aleatoriamente los elementos en la pista |
| `getVelocidadMs()` | Personaje | Retorna el tiempo de sleep según la escoba |
| `getTopPuntajes()` | GMatch | Calcula y ordena el top de puntajes |
| `paintComponent()` | PanelP | Dibuja la pista y corredores en tiempo real |
| `agregarPersonaje()` | GPersonaje | Registra un nuevo personaje con validación |
| `getInstance()` | GPersonaje / GMatch | Retorna la instancia única (Singleton) |
 
---
 
## 7. Patrones de Diseño Utilizados
 
**Singleton** — Aplicado en `GPersonaje` y `GMatch`. Garantiza que exista una única instancia de cada gestor durante toda la ejecución del programa.
 
**MVC (Model - View - Controller)** — Se separa la lógica de negocio de la interfaz gráfica. Los controladores actúan como intermediarios entre los modelos y la vista gráfica.
 
**Observer (mediante interfaz)** — La interfaz `CarreraListener` en `MotorC` permite que la vista sea notificada de los cambios sin que el controlador dependa directamente de ella.

---

**Juan Jacobo Díaz Naola, 2026.**