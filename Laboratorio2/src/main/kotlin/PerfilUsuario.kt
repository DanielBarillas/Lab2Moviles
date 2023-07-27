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
            nombres = "Brandon",
            apellidos = "Palacios",
            urlPhoto = null,
            edad = 30,
            correo = "brandon.palacios@gmail.com",
            biografia = "Adicción por la programación y los videojuegos (Hollow Knight)",
            estado = Estado.ACTIVO
        )
    )
    perfilesUsuarios.add(
        PerfilUsuario(
            id = 2,
            nombres = "Alice",
            apellidos = "Shiled",
            urlPhoto = "https://example.com/alice.jpg",
            edad = 25,
            correo = "Alice.shield@gmail.com",
            biografia = "Amo cuidar de las demás personas",
            estado = Estado.INACTIVO
        )
    )

    while (true) {
        println(",------.                ,---.,--.,--.       ,--.                                                 ,--.")
        println("|  .--. ' ,---. ,--.--./  .-'`--'|  |     ,-|  | ,---.     ,--.,--. ,---. ,--.,--. ,--,--.,--.--.`--' ,---.  ,---.  ")
        println("|  '--' || .-. :|  .--'|  `-,,--.|  |    ' .-. || .-. :    |  ||  |(  .-' |  ||  |' ,-.  ||  .--',--.| .-. |(  .-'")
        println("|  | --' \\   --.|  |   |  .-'|  ||  |    \\ `-' |\\   --.    '  ''  '.-'  `)'  ''  '\\ '-'  ||  |   |  |' '-' '.-'  `)")
        println(" `--'      `----'`--'   `--'  `--'`--'     `---'  `----'     `----' `----'  `----'  `--`--'`--'   `--' `---' `----'  ")
        println("===================================================== Menu ==========================================================\n")
        println("1. Crear perfil")
        println("2. Buscar perfil de usuario(s)")
        println("3. Eliminar perfil")
        println("4. Agregar Hobby")
        println("5. Salir\n")

        try {
            val opcion = readLine()?.toInt() ?: continue

            when (opcion) {
                1 -> {
                    println()
                    // Opción para crear perfil
                    val perfil = crearPerfil()
                    perfilesUsuarios.add(perfil)
                    println("\nPerfil creado exitosamente.")
                }
                2 -> {
                    println()
                    // Opción para buscar perfil de usuario(s)
                    buscarPerfil(perfilesUsuarios)
                }
                3 -> {
                    println()
                    // Opción para eliminar perfil
                    eliminarPerfil(perfilesUsuarios)
                }
                4 -> {
                    println()
                    // Opción para agregar Hobby
                    agregarHobby(perfilesUsuarios)
                }
                5 -> {
                    // Opción para salir del programa
                    println()
                    println("\nSaliendo del programa...")
                    return
                }
                else -> {
                    println()
                    println("\nOpción inválida, intenta de nuevo.\n")
                }
            }
        } catch (e: Exception) {
            println()
            println("Error en: ${e.message}")
        }
    }
}

fun crearPerfil(): PerfilUsuario {
    println("\n Ingresa los datos del nuevo perfil:")
    print("\n Por favor, ingrese su ID: ")
    val id = readLine()?.toIntOrNull() ?: throw IllegalArgumentException("ID inválida, debe ingresar datos númericos.")
    print("\n Por favor, ingrese sus nombres: ")
    val nombres = readLine()?: throw IllegalArgumentException("Los nombres no pueden ser nulos.")
    print("\n Por favor, ingrese sus Apellidos: ")
    val apellidos = readLine()?: throw IllegalArgumentException("los apellidos no pueden ser nulos.")
    print("\n Por favor, ingrese la URL de Foto de Perfil (opcional): ")
    val urlPhoto = readLine()
    print("\n Por favor, ingrese su edad: ")
    val edad = readLine()?.toIntOrNull() ?: throw IllegalArgumentException("Edad inválida, debe ingresar datos númericos.")
    print("\n Por favor, ingrese su correo: ")
    val correo = readLine() ?: throw IllegalArgumentException("El correo no puede ser nulo.")
    print("\n Escriba su biografía (opcional): ")
    val biografia = readLine()
    print("\n Escriba su estado (Activo, Pendiente o Inactivo): ")
    val estadoStr = readLine() ?: throw IllegalArgumentException("Error: estado no puede ser nulo, por favor ingrese una de las opciones que se le dan.")
    val estado = Estado.values().find { it.name.equals(estadoStr, ignoreCase = true) }
        ?: throw IllegalArgumentException("Estado inválido, por favor ingrese una de las opciones que se le dan.")

    return PerfilUsuario(id, nombres, apellidos, urlPhoto, edad, correo, biografia, estado)
}

fun buscarPerfil(perfilesUsuarios: List<PerfilUsuario>) {
    println("Buscar perfil:")
    print("\n Ingresa el ID o nombres/apellidos: ")
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

    if(result.isEmpty()){
        println("\n Lo siento, el perfil ingresado no ha encontrado.")
        return
    }

    for (perfil in result) {
        println("\n ID: ${perfil.id}")
        println("\n Nombres: ${perfil.nombres}")
        println("\n Apellidos: ${perfil.apellidos}")
        println("\n URL de Foto de Perfil: ${perfil.urlPhoto ?: "No disponible"}")
        println("\n Edad: ${perfil.edad}")
        println("\n Correo: ${perfil.correo}")
        println("\n Biografía: ${perfil.biografia ?: "No disponible"}")
        println("\n Estado: ${perfil.estado}")
        if (perfil.hobbies.isNotEmpty()) {
            println("\n Hobbies:")
            for (hobby in perfil.hobbies) {
                println("- Título: ${hobby.titulo}")
                println("  Descripción: ${hobby.descripcion}")
                println("  URL de Foto: ${hobby.urlPhoto ?: "No disponible"}")
            }
        } else {
            println("\n Hobbies: El usuario no tiene hobbies registrados.")
        }
        println("-----------------------------------------")
    }
}

fun eliminarPerfil(perfilesUsuarios: MutableList<PerfilUsuario>) {
    println("\n Eliminar perfil:")
    print("\n Ingresa el ID del perfil a eliminar: ")
    val idInput = readLine()?.toIntOrNull() ?: return

    val perfil = perfilesUsuarios.find { it.id == idInput }

    if (perfil != null) {
        perfilesUsuarios.remove(perfil)
        println("\n El perfil ingresado/seleccionado ha sido eliminado exitosamente.")
    } else {
        println("\n Lo siento, el perfil ingresado no ha encontrado.")
    }
}

fun agregarHobby(perfilesUsuarios: MutableList<PerfilUsuario>) {
    println("\n Agregar Hobby:")
    print("\n Ingresa el ID o nombres/apellidos del perfil: ")
    val input = readLine()?: return

    val perfil = perfilesUsuarios.find {
        it.id == input.toIntOrNull() || it.nombres.contains(input, ignoreCase = true) ||
                it.apellidos.contains(input, ignoreCase = true)
    }

    if (perfil != null) {
        val hobby = crearHobby()
        perfil.agregarHobby(hobby)
        println("\n Hobby ha sido agregado exitosamente al perfil ingresado/seleccionado con ID ${perfil.id}.")
    } else {
        println("\n Lo siento el perfil ingresado no ha encontrado.")
    }
}

fun crearHobby(): Hobby {
    println("\n Ingresa los datos del hobby:")
    print("\n Título: ")
    val titulo = readLine() ?: throw IllegalArgumentException("\n Título no puede ser nulo.")
    print("\n Descripción: ")
    val descripcion = readLine() ?: throw IllegalArgumentException("\n Descripción no puede ser nula.")
    print("\n URL de Foto de Hobby (opcional): ")
    val urlPhoto = readLine()
    return Hobby(titulo, descripcion, urlPhoto)
}