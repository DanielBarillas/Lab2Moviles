// * <h1> Laboratorio 2 </h1>
// * <h2> Main (Clase principal) </h2>
// *
// * <p>
// * Programación de plataformas móviles - Universidad del Valle de Guatemala
// * </p>
// *
// * Creado por:
// *
// * @author Pablo Daniel Barillas Moreno || Carné No. 22193
// * @version 1.0
// * @since 26/07/2023
// *

data class Hobby(
    val titulo: String,
    val descripcion: String,
    val urlPhoto: String?
)

enum class Estado {
    ACTIVO, PENDIENTE, INACTIVO
}

class PerfilUsuario(
    val id: Int,
    val nombres: String,
    val apellidos: String,
    val urlPhoto: String?,
    val edad: Int,
    val correo: String,
    val biografia: String?,
    val estado: Estado,
    val hobbies: MutableList<Hobby> = mutableListOf()
) {
    fun agregarHobby(hobby: Hobby) {
        hobbies.add(hobby)
    }
}

fun main() {
    val perfilesUsuarios = mutableListOf<PerfilUsuario>()

    // Perfiles precargados para pruebas
    perfilesUsuarios.add(
        PerfilUsuario(
            id = 1,
            nombres = "Juan",
            apellidos = "Perez",
            urlPhoto = null,
            edad = 30,
            correo = "juan.perez@example.com",
            biografia = "Soy un apasionado de la música",
            estado = Estado.ACTIVO
        )
    )
    perfilesUsuarios.add(
        PerfilUsuario(
            id = 2,
            nombres = "Maria",
            apellidos = "Gomez",
            urlPhoto = "https://example.com/maria.jpg",
            edad = 25,
            correo = "maria.gomez@example.com",
            biografia = "Amo viajar y descubrir nuevas culturas",
            estado = Estado.INACTIVO
        )
    )

    while (true) {
        println("Menu:")
        println("1. Crear perfil")
        println("2. Buscar perfil de usuario(s)")
        println("3. Eliminar perfil")
        println("4. Agregar Hobby")
        println("5. Salir")

        try {
            val opcion = readLine()?.toInt() ?: continue

            when (opcion) {
                1 -> {
                    // Crear perfil
                    val perfil = crearPerfil()
                    perfilesUsuarios.add(perfil)
                    println("Perfil creado exitosamente.")
                }
                2 -> {
                    // Buscar perfil de usuario(s)
                    buscarPerfil(perfilesUsuarios)
                }
                3 -> {
                    // Eliminar perfil
                    eliminarPerfil(perfilesUsuarios)
                }
                4 -> {
                    // Agregar Hobby
                    agregarHobby(perfilesUsuarios)
                }
                5 -> {
                    println("Saliendo del programa...")
                    return
                }
                else -> {
                    println("Opción inválida, intenta de nuevo.")
                }
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}

fun crearPerfil(): PerfilUsuario {
    println("Ingresa los datos del nuevo perfil:")
    print("ID: ")
    val id = readLine()?.toIntOrNull() ?: throw IllegalArgumentException("ID inválido.")
    print("Nombres: ")
    val nombres = readLine() ?: throw IllegalArgumentException("Nombres no pueden ser nulos.")
    print("Apellidos: ")
    val apellidos = readLine() ?: throw IllegalArgumentException("Apellidos no pueden ser nulos.")
    print("URL de Foto de Perfil (opcional): ")
    val urlPhoto = readLine()
    print("Edad: ")
    val edad = readLine()?.toIntOrNull() ?: throw IllegalArgumentException("Edad inválida.")
    print("Correo: ")
    val correo = readLine() ?: throw IllegalArgumentException("Correo no puede ser nulo.")
    print("Biografía (opcional): ")
    val biografia = readLine()
    print("Estado (Activo, Pendiente o Inactivo): ")
    val estadoStr = readLine() ?: throw IllegalArgumentException("Estado no puede ser nulo.")
    val estado = Estado.values().find { it.name.equals(estadoStr, ignoreCase = true) }
        ?: throw IllegalArgumentException("Estado inválido.")

    return PerfilUsuario(id, nombres, apellidos, urlPhoto, edad, correo, biografia, estado)
}

fun buscarPerfil(perfilesUsuarios: List<PerfilUsuario>) {
    println("Buscar perfil:")
    print("Ingresa el ID o nombres/apellidos: ")
    val input = readLine() ?: return

    val result = mutableListOf<PerfilUsuario>()
    val idInput = input.toIntOrNull()

    for (perfil in perfilesUsuarios) {
        if (perfil.id == idInput || perfil.nombres.contains(input, ignoreCase = true) ||
            perfil.apellidos.contains(input, ignoreCase = true)
        ) {
            result.add(perfil)
        }
    }

    if (result.isNotEmpty()) {
        for (perfil in result) {
            println("ID: ${perfil.id}")
            println("Nombres: ${perfil.nombres}")
            println("Apellidos: ${perfil.apellidos}")
            println("URL de Foto de Perfil: ${perfil.urlPhoto ?: "No disponible"}")
            println("Edad: ${perfil.edad}")
            println("Correo: ${perfil.correo}")
            println("Biografía: ${perfil.biografia ?: "No disponible"}")
            println("Estado: ${perfil.estado}")
            if (perfil.hobbies.isNotEmpty()) {
                println("Hobbies:")
                for (hobby in perfil.hobbies) {
                    println("- Título: ${hobby.titulo}")
                    println("  Descripción: ${hobby.descripcion}")
                    println("  URL de Foto: ${hobby.urlPhoto ?: "No disponible"}")
                }
            } else {
                println("Hobbies: No tiene hobbies registrados.")
            }
            println("----------------------")
        }
    } else {
        println("Perfil no encontrado.")
    }
}

fun eliminarPerfil(perfilesUsuarios: MutableList<PerfilUsuario>) {
    println("Eliminar perfil:")
    print("Ingresa el ID del perfil a eliminar: ")
    val idInput = readLine()?.toIntOrNull() ?: return

    val perfil = perfilesUsuarios.find { it.id == idInput }

    if (perfil != null) {
        perfilesUsuarios.remove(perfil)
        println("Perfil eliminado exitosamente.")
    } else {
        println("Perfil no encontrado.")
    }
}

fun agregarHobby(perfilesUsuarios: MutableList<PerfilUsuario>) {
    println("Agregar Hobby:")
    print("Ingresa el ID o nombres/apellidos del perfil: ")
    val input = readLine() ?: return

    val perfil = perfilesUsuarios.find {
        it.id == input.toIntOrNull() || it.nombres.contains(input, ignoreCase = true) ||
                it.apellidos.contains(input, ignoreCase = true)
    }

    if (perfil != null) {
        val hobby = crearHobby()
        perfil.agregarHobby(hobby)
        println("Hobby agregado exitosamente al perfil con ID ${perfil.id}.")
    } else {
        println("Perfil no encontrado.")
    }
}

fun crearHobby(): Hobby {
    println("Ingresa los datos del hobby:")
    print("Título: ")
    val titulo = readLine() ?: throw IllegalArgumentException("Título no puede ser nulo.")
    print("Descripción: ")
    val descripcion = readLine() ?: throw IllegalArgumentException("Descripción no puede ser nula.")
    print("URL de Foto de Hobby (opcional): ")
    val urlPhoto = readLine()
    return Hobby(titulo, descripcion, urlPhoto)
}

