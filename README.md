# Controles UI: Contenedores

Práctica de desarrollo Android que integra Supabase como backend y diversos contenedores para la visualización de datos de alumnos y materias.

## Captura de pantalla

![Captura](app/src/main/res/drawable/captura.png)

## Descripción

La actividad principal utiliza contenedores (`LinearLayout`) para mostrar una lista de alumnos obtenida desde una base de datos en Supabase, junto con dos spinners para filtrar materias por semestre.

## Características

- **Integración con Supabase:** Consultas a las tablas `Alumnos` y `Materias` mediante la REST API de Supabase.
- **Gestión de imágenes con Glide:** Las fotos de los alumnos se cargan dinámicamente aplicando transformación circular (`circleCrop`).
- **Diseño de ítems personalizado:** Layout `item_alumno.xml` que muestra nombre, correo y teléfono junto con iconos descriptivos.
- **ListView con Adapter personalizado:** `AlumnoAdapter` extiende `ArrayAdapter` para vincular el modelo `Alumno` con la interfaz.
- **Spinners de filtrado:** Spinner de semestre (Primero–Décimo) que filtra dinámicamente el spinner de materias por nivel.

## Tecnologías

| Tecnología | Uso |
|---|---|
| Java | Lenguaje principal |
| Volley | Cliente HTTP para las consultas a Supabase |
| Glide | Carga y transformación de imágenes |
| Supabase REST API | Backend como servicio |
| XML Layouts | Diseño de interfaces con `LinearLayout` y `ConstraintLayout` |

> **Nota:** La práctica se implementó en **Java con Volley** en lugar de Kotlin con Postgrest-kt/Ktor, manteniendo consistencia con las prácticas anteriores del curso que seguían el mismo enfoque.

## Estructura del proyecto

```
app/src/main/java/com/example/practicacontenedoresui/
├── MainActivity.java     # Actividad principal, carga datos y configura spinners
├── Alumno.java           # Modelo de datos del alumno
├── Materia.java          # Modelo de datos de la materia
└── AlumnoAdapter.java    # Adapter personalizado para el ListView

app/src/main/res/layout/
├── activity_main.xml     # Layout principal (logo + spinners + ListView)
└── item_alumno.xml       # Layout de cada ítem de la lista
```
