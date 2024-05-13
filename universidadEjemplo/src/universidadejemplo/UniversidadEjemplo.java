/*
1. Cargar el driver MariaDB.
2. Establecer la conexión a la base de datos previamente creada.
3. Insertar 3 alumnos
4. Insertar 4 materias
5. Inscribir a los 3 alumnos en 2 materias cada uno.
6. Listar los datos de los alumnos con calificaciones superiores a 8.
7. Desinscribir un alumno de una de la materias.
*/


package universidadejemplo;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author adam
 */
public class UniversidadEjemplo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            //1. Cargar el driver MariaDB.
            Class.forName("org.mariadb.jdbc.Driver");
        
            //2. Establecer la conexión a la base de datos previamente creada.
            Connection conexion = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/universidad",
                "root",
                "");
        
            //3. Insertar 3 alumnos
            String sqlInsertAlumnos = "INSERT INTO alumno "
                    + " (dni, apellido, nombre, fechaNacimiento, estado) "
                    + " VALUES "
                    + "(23423, 'Sanchez', 'Hernando', '1994-03-01', 1), "
                    + "(43352, 'Suarez', 'Fernando', '1974-03-01', 1), "
                    + "(23342, 'Gomez', 'Fernanda', '1998-03-01', 1)";
            
            PreparedStatement psInsertAlumnos = conexion.prepareStatement(
                    sqlInsertAlumnos);
            int filasAlumnos = psInsertAlumnos.executeUpdate();
            if(filasAlumnos > 0) {
                JOptionPane.showMessageDialog(null,
                        "Alumnos agregados exitosamente");
                System.out.println(filasAlumnos);
            }
        
            //4. Insertar 4 materias
            String sqlInsertMaterias = "INSERT INTO materia "
                    + "(nombre, año, estado)"
                    + " VALUES "
                    + "('Matematica', 1, 1), "
                    + "('Lab1', 1, 1), "
                    + "('Web1', 1, 1), "
                    + "('Estructuras de Datos y Algoritmos', 1, 1) ";
            
            PreparedStatement psInsertMaterias = conexion.prepareStatement(
                    sqlInsertMaterias);
            int filasMaterias = psInsertMaterias.executeUpdate();
            if(filasMaterias > 0) {
                JOptionPane.showMessageDialog(null,
                        "Materias agregadas exitosamente");
                System.out.println(filasMaterias);
            }

            //5. Inscribir a los 3 alumnos en 2 materias cada uno.
            String sqlInscripciones = "INSERT INTO inscripcion"
                    + " (idAlumno, idMateria) "
                    + " VALUES "
                    + " (1, 1), "
                    + " (1, 2), "
                    + " (2,2), "
                    + " (2,4), "
                    + " (3,3), "
                    + " (3,1) ";

            //6. Listar los datos de los alumnos con calificaciones superiores a 8.
            String sqlNotaMayorOcho = "SELECT "
                    + " alumno.dni AS DNI,"
                    + " alumno.apellido AS Apellido,"
                    + " alumno.nombre AS Nombre ,"
                    + " alumno.fechaNacimiento AS FechaNacimiento,"
                    + " alumo.estado AS AlumnoActivo,"
                    + " materia.nombre AS Materia, "
                    + " materia.año AS Año, "
                    + " nota AS Nota "
                    + " FROM inscripcion "
                    + " JOIN alumno ON (inscripcion.idAlumno = alumno.idAlumno) "
                    + " JOIN materia ON (inscipcion.idMateria = materia.idMateria) "
                    + " WHERE nota > 8 ";
            PreparedStatement psNotaMayorOcho = conexion.prepareStatement(
                    sqlNotaMayorOcho);
            ResultSet consMayorOcho = psNotaMayorOcho.executeQuery();
            
            while(consMayorOcho.next()){
                System.out.println("DNI: " + consMayorOcho.getInt("DNI"));
                System.out.println("Apellido: " + consMayorOcho.getString("Apellido"));
                System.out.println("Nombre: " + consMayorOcho.getString("Nombre"));
                System.out.println("FechaNacimiento: " + consMayorOcho.getDate("FechaNacimiento"));
                System.out.println("AlumnoActivo: " + consMayorOcho.getBoolean("AlumnoActivo"));
                System.out.println("Materia: " + consMayorOcho.getString("Materia"));
                System.out.println("Año: " + consMayorOcho.getInt("Año"));
                System.out.println("Nota: " + consMayorOcho.getInt("Nota"));
                System.out.println("");
            }

            //7. Desinscribir un alumno de una de la materias.
            String sqlDesinscribir = "DELETE FROM inscripcion "
                    + " WHERE idAlumno = 1 "
                    + " AND idMateria = 1 ";
            PreparedStatement psDesinscribir = conexion.prepareStatement(sqlDesinscribir);
            int filasBaja = psDesinscribir.executeUpdate();
            if(filasBaja == 1) {
                JOptionPane.showMessageDialog(null,
                        "Inscripción dada de baja correctamente"); 
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            
           JOptionPane.showMessageDialog(null, "Error de conexion");
           // Error: 1049-42000: Unknown database
            
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar driver");
        }
    }
    
}
