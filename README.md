# SmartTask

SmartTask es una aplicación de consola desarrollada en **Java** que permite gestionar tareas de manera simple e interactiva.

El sistema incluye funcionalidades completas de gestión de tareas junto con pruebas automatizadas y reporte de cobertura de código usando **JaCoCo**.

## Repositorio en Github: https://github.com/Elationg/EntregaM4_TD

## Reporte de Pruebas en https://elationg.github.io/EntregaM4_TD/
---

# 🚀 Funcionalidades

SmartTask permite:

- ✅ Agregar tareas
- 📋 Listar tareas
- ✔ Marcar tareas como completadas
- ✏ Editar nombre y/o prioridad
- 🗑 Eliminar tareas
- 🔥 Manejar prioridades (URGENTE, MEDIA, BAJA)
- 🧪 Ejecutar pruebas automatizadas con cobertura

---

# 📋 Menú del Sistema

Al ejecutar la aplicación se muestra el siguiente menú:

===== SMART TASK =====

1. Agregar tarea
2. Listar tareas
3. Marcar como completada
4. Editar tarea
5. Eliminar tarea 
0. Salir

## ✏ Opción 4 – Editar tarea

Permite:

- Cambiar solo el nombre
- Cambiar solo la prioridad
- Cambiar ambos campos
- Mantener uno sin modificar

Si la edición es exitosa:

## Tarea editada con éxito.

Si el ID no existe:

## ID no encontrado.


---

# 📁 Estructura del Proyecto

Proyecto estructurado como aplicación Maven:
smarttask/
│
├── src/
│ ├── main/java/com/smarttask/
│ │ ├── Main.java
│ │ ├── model/
│ │ └── service/
│ │
│ └── test/java/service
│ └── GestorTareasTest.java
│
├── target/
├── pom.xml
└── README.md


⚠ IMPORTANTE:  
Todos los comandos Maven deben ejecutarse dentro de la carpeta `smarttask`, al mismo nivel donde se encuentran:

- `src`
- `target`
- `pom.xml`

---

# ⚙ Tecnologías Utilizadas

- Java 21
- Maven
- JUnit 5
- JaCoCo (Code Coverage)
- GitHub Actions (para publicación automática del reporte)

---

# ▶ Cómo Ejecutar SmartTask en VSCode

Abrir el proyecto en una nueva ventana

Ir a src/main/java/com/smarttask

Hacer click en Main.java

Hacer click en Run Java (icono esquina superior derecha en VSCode)


---

# 🧪 Ejecutar las Pruebas

Para ejecutar todos los tests automatizados:

mvn clean test


⚠ IMPORTANTE:  
Debes estar dentro de la carpeta `smarttask`, al nivel donde se encuentra `pom.xml`.

Este comando:

- Limpia el proyecto
- Compila el código
- Ejecuta los tests
- Genera el reporte de cobertura

---

# 📊 Ver el Reporte de Cobertura (JaCoCo)

Después de ejecutar:

mvn clean test


El reporte se genera en:

target/site/index.html

## El reporte ha sido subido y publicado en GitHub Pages:
El archivo ci.yml determina que en cada push a la rama "Main" se llevan a cabo los siguientes pasos:

Steps:
Checkout repository
Set up Java 21
Build and run tests
Debug folder structure    //Agregado para corregir error
Upload JaCoCo report

Deploy:
Github Pages
Deploy

https://elationg.github.io/EntregaM4_TD/



