package paquete;

import java.sql.*;  // Importa las clases necesarias para trabajar con bases de datos
import java.util.Scanner;  // Importa la clase Scanner para leer entradas del usuario

public class ConsultaDatos {

    // Método que muestra el menú para que el usuario elija qué opción ejecutar
    public static void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);  // Crea un objeto Scanner para leer la opción del usuario
        while (true) {  // Bucle para mostrar el menú hasta que el usuario decida salir
            System.out.println("Seleccione una opción:");
            System.out.println("1 – Ver películas");
            System.out.println("2 – Salir");

            int opcion = scanner.nextInt();  // Lee la opción seleccionada por el usuario

            // Dependiendo de la opción, se ejecuta una acción
            switch (opcion) {
                case 1:
                    mostrarPeliculas();  // Si se selecciona la opción 1, se llaman las películas
                    break;
                case 2:
                    System.out.println("Saliendo del menu..");  // Si se selecciona la opción 2, se sale del programa
                    return;  // Sale del bucle y termina el programa
                default:	
                    System.out.println("Opción no válida.");  // Muestra un mensaje si la opción es incorrecta
            }
        }
    }

    // Método que muestra las películas disponibles en la base de datos
    public static void mostrarPeliculas() {
        String url = "jdbc:mysql://localhost:3306/cine_cristianespinar";  // URL de la base de datos
        String usuario = "root";  // Usuario de la base de datos
        String contraseña = "1234";  // Contraseña de la base de datos

        try {
            // Establece la conexión con la base de datos
            Connection conexion = DriverManager.getConnection(url, usuario, contraseña);

            // Consulta SQL para obtener las películas y sus tipos
            String query = "SELECT p.id_pelicula, p.titulo, p.director, p.duracion, p.clasificacion, t.nombre_tipo " +
                           "FROM peliculas p " +
                           "JOIN tipo t ON p.id_tipo = t.id_tipo";

            Statement stmt = conexion.createStatement();  // Crea un Statement para ejecutar la consulta
            ResultSet rs = stmt.executeQuery(query);  // Ejecuta la consulta y obtiene los resultados

            // Imprime el encabezado de la tabla con los nombres de las columnas
            System.out.printf("%-10s %-30s %-20s %-10s %-10s %-15s\n", 
                              "ID_Pelicula", "Titulo", "Director", "Duracion", "Clasificacion", "Tipo");
            System.out.println("---------------------------------------------------------------------------------");

            // Recorre los resultados de la consulta e imprime cada película
            while (rs.next()) {
                System.out.printf("%-10s %-30s %-20s %-10d %-10s %-15s\n", 
                                  rs.getString("id_pelicula"),
                                  rs.getString("titulo"),
                                  rs.getString("director"),
                                  rs.getInt("duracion"),
                                  rs.getString("clasificacion"),
                                  rs.getString("nombre_tipo"));
            }
            rs.close();  // Cierra el ResultSet
            stmt.close();  // Cierra el Statement
            conexion.close();  // Cierra la conexión a la base de datos
        } catch (SQLException e) {
            // Si ocurre un error en la consulta o conexión, se imprime un mensaje de error
            System.out.println("Error no se pueden consultar las peliculas: " + e.getMessage());
        }
    }
}


