# Laboratorio de Introducción a la Programación y Computación 1
**Universidad San Carlos de Guatemala**  
**Facultad de Ingeniería**  
**Escuela de Ingeniería en Ciencias y Sistemas**
---
&nbsp;
&nbsp;
# Práctica 2: Manual de Usuario
&nbsp;
&nbsp;
---
**Estudiante** | Juan Jacobo Díaz Naola 
**Carnet** | 202408106 
**Documento** | Manual de Usuario 
 
---
 
## 1. Introducción
Carrera de Escobas es un juego de escritorio desarrollado en Java donde el jugador (usuario) compite contra la computadora en una carrera de escobas mágicas, inspirado en el universo ficticio de Harry Potter. A lo largo de la pista aparecen elementos que pueden sumar puntos o ralentizar a los corredores. El objetivo es llegar primero a la meta o atrapar la 'Snitch' antes que el oponente.
 
---
 ## 2. Requisitos para ejecutar el programa
 
- Tener instalado **Java 17 o superior**
- El archivo **`CarreraEscobas.jar`** proporcionado junto a este manual
 
> No es necesario tener IntelliJ IDEA instalado para ejecutar el archivo JAR.
 
---
## 3. Cómo ejecutar el programa
### Opción A — Desde el archivo .JAR
 
1. Localizar el archivo **`CarreraEscobas.jar`**
2. Hacer **doble clic** sobre el mismo
3. Si el sistema está correctamente configurado, el programa abrirá directamente
 
### Opción B — Desde la terminal
 
1. Abrir la terminal o símbolo del sistema (CMD) en la carpeta donde se encuentra el JAR
2. Ejecutar el siguiente comando:
 
```bash
java -jar CarreraEscobas.jar
```
---
 
## 3. Menú Principal
 
Al iniciar el programa aparece la ventana principal con cuatro opciones:
 
**Jugar** — Inicia el proceso para seleccionar un personaje y comenzar una carrera.
 
**Crear Personaje** — Permite registrar un nuevo personaje en el sistema con nombre, casa y modelo de escoba.
 
**Ver Top de Puntajes** — Muestra una gráfica de barras con los mejores puntajes obtenidos por cada jugador.
 
**Salir** — Cierra la aplicación.
 
---
 
## 4. Crear un Personaje
 
1. En el Menú Principal, hacer clic en **Crear Personaje**
2. Ingresar los siguientes datos:
   - **Nombre:** nombre único del personaje
   - **Casa:** elegir entre Gryffindor, Slytherin, Ravenclaw o Hufflepuff
   - **Escoba:** elegir el modelo de escoba
3. Hacer clic en **Guardar**
 
### Modelos de escoba disponibles
 
| Escoba | Velocidad |
|--------|-----------|
| Nimbus 2000 | Lenta — avanza cada 3 segundos |
| Nimbus 2001 | Rápida — avanza cada 2 segundos |
| Saeta de Fuego | Muy rápida — avanza cada 1 segundo |
 
### Consideraciones
- El nombre no puede estar vacío
- No se pueden registrar dos personajes con el mismo nombre
- El sistema ya incluye 4 personajes precargados: Harry Potter, Draco Malfoy, Luna Lovegood y Cedric Diggory
 
---
 
## 5. Iniciar una Carrera
 
1. En el Menú Principal, hacer clic en **Jugar**
2. Se mostrará la lista de personajes disponibles
3. Seleccionar el personaje con el que se desea competir haciendo clic sobre él
4. Hacer clic en **Iniciar Carrera**
5. La computadora elegirá automáticamente un oponente aleatorio de la lista
 
---
 
## 6. Pantalla de Carrera
 
Una vez iniciada la carrera se mostrará la siguiente información:
 
**Tarjetas de personajes** — En la parte superior aparecen los datos de ambos corredores: nombre, casa y escoba utilizada. El jugador aparece a la izquierda y la computadora a la derecha.
 
**Pista gráfica** — En el centro se visualizan las dos pistas con los corredores moviéndose en tiempo real. Cada corredor se representa con un círculo de color con su inicial.
 
**Marcador** — En la parte inferior se muestra la posición actual y los puntos acumulados de cada corredor, actualizándose en tiempo real.
 
**Estado de la carrera** — Debajo del marcador aparece un mensaje indicando si la carrera está en progreso o quién ganó al finalizar.
 
### Elementos en la pista
 
Durante la carrera aparecen tres tipos de elementos representados por letras:
 
| Letra | Elemento | Efecto |
|-------|----------|--------|
| S | Snitch | Otorga 150 puntos y termina la carrera inmediatamente |
| B | Bludger | Penaliza al corredor que lo toca con 2 segundos adicionales de espera |
| Q | Quaffle | Otorga 10 puntos al corredor que pasa por él |
 
### ¿Cómo se gana?
 
Un jugador gana cuando ocurre una de estas dos condiciones:
- Es el primero en llegar a la **meta** al final de la pista
- Es el primero en tocar la **Snitch (S)** en cualquier punto del recorrido
 
### Al terminar la carrera
 
- Aparece un mensaje indicando el ganador y los puntos finales de ambos
- Se habilita el botón **Volver al Menú** para regresar a la pantalla principal
- El resultado queda guardado automáticamente en el historial de partidas
 
---
 
## 7. Ver Top de Puntajes
 
1. En el Menú Principal, hacer clic en **Ver Top de Puntajes**
2. Se mostrará una gráfica de barras con el mejor puntaje de cada jugador
3. Si aún no se ha jugado ninguna partida, aparecerá un mensaje indicándolo
 
### Exportar a PDF
 
1. Dentro de la pantalla de Top de Puntajes, hacer clic en **Exportar PDF**
2. Se abrirá una ventana para elegir dónde guardar el archivo
3. Seleccionar la ubicación y hacer clic en **Guardar**
4. El archivo PDF se generará con una tabla que incluye nombre del jugador, escoba, puntos obtenidos y ganador de cada partida
 
---
 
## 8. Navegación entre pantallas
 
| Acción | Resultado |
|--------|-----------|
| Clic en Jugar | Abre la pantalla de elección de personajes |
| Clic en Volver | Regresa a la pantalla anterior |
| Clic en Iniciar Carrera | Abre la ventana de carrera |
| Cerrar ventana de carrera | Regresa a la elección de personajes |
| Clic en Salir | Cierra la aplicación completamente |
 
---
 
## 9. Errores comunes y soluciones
 
**El programa no abre al hacer doble clic en el JAR:**  
Verificar que Java 17 o superior esté instalado. Abrir la terminal y ejecutar `java -version` para confirmarlo. Si no está instalado, descargarlo desde [https://www.java.com](https://www.java.com).
 
**Error "Unable to access jarfile":**  
Verificar que el comando se está ejecutando desde la misma carpeta donde está el archivo `CarreraEscobas.jar`. Usar `cd` para navegar a la carpeta correcta antes de ejecutar el comando.
 
**El programa abre pero no muestra la interfaz gráfica:**  
Asegurarse de que el sistema operativo permite aplicaciones gráficas Java. En Windows, intentar ejecutar como administrador.
 
**No puedo crear un personaje:**  
Verificar que el nombre no esté vacío y que no exista otro personaje con el mismo nombre en la lista.
 
**La gráfica de Top de Puntajes está vacía:**  
Es necesario jugar al menos una partida antes de consultar el top.
 
**El botón Exportar PDF está deshabilitado:**  
El botón se habilita únicamente cuando existe al menos una partida registrada.
---
**Juan Jacobo Díaz Naola, 2026**
 