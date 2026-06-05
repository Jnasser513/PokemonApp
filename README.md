# PokemonApp

PokemonApp es una aplicación Android nativa en Kotlin basada en la [PokeAPI](https://pokeapi.co/).
El proyecto está construido con una arquitectura modular, separación por features y un enfoque offline first.

## Objetivo

La app permite listar Pokémon, ver sus detalles y marcar favoritos para consultarlos después desde almacenamiento local.
La base técnica fue pensada para escalar por features sin mezclar responsabilidades ni acoplar la UI con la lógica de negocio.

## Stack técnico

- Kotlin
- Jetpack Compose
- Clean Architecture
- MVI
- Room
- Retrofit + OkHttp + kotlinx.serialization
- Koin
- Gradle con `build-logic` y convention plugins

## Arquitectura

### Modularización

El proyecto está dividido en módulos para aislar responsabilidades y hacer el crecimiento más ordenado:

- `app`: punto de entrada y navegación raíz
- `core`: código compartido entre features
- `pokemon`: feature principal separada por capas
- `build-logic`: convención de Gradle y configuración reutilizable

### Separación por capas

Cada feature sigue una organización por responsabilidades:

- `data`: implementación de acceso a datos
- `domain`: contratos, modelos y casos de uso
- `network`: integración con la API remota
- `presentation`: UI, estado y lógica MVI

### Clean Architecture

La dirección de dependencias se mantiene desde la capa de presentación hacia dominio y luego hacia data.
Esto evita que la UI conozca detalles de persistencia o red, y facilita pruebas unitarias más simples.

### Offline First

La app prioriza la fuente local como origen de verdad.
Los Pokémon se guardan en Room y desde ahí se consumen tanto el listado principal como los favoritos.
La red se usa para sincronizar y poblar la base local cuando falta información.

### MVI

La UI de cada feature se modela con el patrón MVI:

- `Action`: intención del usuario
- `Event`: efectos o mensajes puntuales
- `ViewState`: estado observable de la pantalla

Esto ayuda a mantener la UI predecible y fácil de testear.

## Paquetes principales

### `core`

Contiene componentes reutilizables por toda la app:

- contratos de dominio
- manejo de errores y resultados
- datasource local con Room
- persistencia compartida
- componentes visuales compartidos
- utilidades de UI

### `pokemon`

Feature principal de la app:

- listado de Pokémon
- detalles
- favoritos
- pantallas con Compose
- ViewModels con MVI

## Decisiones técnicas

### 1. Arquitectura modular

Se eligió modularizar para mantener la app escalable y con límites claros entre dominio, infraestructura y UI.
Esto permite agregar nuevas features sin tocar todo el proyecto.

### 2. Offline first

Se priorizó almacenamiento local para que la app siga siendo útil sin red.
Room actúa como la fuente principal de lectura para la experiencia del usuario.

### 3. Clean Architecture

La lógica de negocio vive en `domain`, mientras que `data` implementa los contratos.
Así se evita mezclar reglas de negocio con detalles de red o base de datos.

### 4. MVI en presentación

Cada pantalla administra su estado de forma explícita.
Eso mejora la trazabilidad de los cambios en la UI y simplifica las pruebas.

### 5. Separación de responsabilidades

Las pantallas no conocen cómo se obtiene o persiste la información.
Los ViewModels coordinan acciones, casos de uso y estado, mientras que data y network resuelven la infraestructura.

### 6. Retrofit en lugar de Ktor

La comunicación remota se implementó con Retrofit, OkHttp y `kotlinx.serialization`.
Esto deja una integración simple, conocida y bien soportada para el consumo de PokeAPI.

### 7. Convention plugins

Se creó `build-logic` para evitar duplicar configuración de Gradle entre módulos.
Así se centralizan reglas de compilación, Compose, Room, Android library/app y Kotlin JVM.

## Tests

Se incluyeron pruebas unitarias para:

- ViewModels
- Repository
- Datasource

El proyecto usa `mockk`, `junit`, `turbine` y `kotlinx-coroutines-test`.

Además, se creó un módulo compartido de test fixtures para reutilizar:

- `MainDispatcherRule`
- builders de datos de prueba para Pokémon

## Estructura general

```text
app/
core/
  data/
  database/
  domain/
  presentation/
  test-fixtures/
pokemon/
  data/
  domain/
  network/
  presentation/
build-logic/
```

## Cómo ejecutar

### Compilar la app

```bash
./gradlew :app:assembleDebug
```

### Ejecutar tests unitarios

```bash
./gradlew :pokemon:presentation:testDebugUnitTest :core:data:testDebugUnitTest :core:database:testDebugUnitTest
```

## Notas

- La app está pensada para crecer por features.
- Los favoritos se guardan localmente para que estén disponibles sin conexión.
- La estructura actual ya queda lista para sumar nuevas pantallas y flujos sin rehacer la base.
