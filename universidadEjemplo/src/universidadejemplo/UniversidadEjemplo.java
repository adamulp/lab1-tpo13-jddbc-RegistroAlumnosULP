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
 * @author Adam, Alexis, Enzo, Nico
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
            PreparedStatement psInscripciones = conexion.prepareStatement(sqlInscripciones);
            int filasInscripciones = psInscripciones.executeUpdate();
            if(filasInscripciones > 0){
                JOptionPane.showMessageDialog(null,
                        "Inscripciones agregadas exitosamente");
            }

            //6. Listar los datos de los alumnos con calificaciones superiores a 8.
            
            // Registrar unas notas
            String sqlRendir1 = " UPDATE inscripcion "
                    + " SET nota = 9 "
                    + " WHERE idAlumno = 1 "
                    + " AND idMateria = 1 ";
            PreparedStatement psRendir1 = conexion.prepareStatement(sqlRendir1);
            int filasRendir1 = psRendir1.executeUpdate();
            if(filasRendir1 == 1) {
                JOptionPane.showMessageDialog(null, "Nota registrada");
            }
            
            String sqlRendir2 = " UPDATE inscripcion "
                    + " SET nota = 8 "
                    + " WHERE idAlumno = 2 "
                    + " AND idMateria = 2 ";
            PreparedStatement psRendir2 = conexion.prepareStatement(sqlRendir2);
            int filasRendir2 = psRendir2.executeUpdate();
            if(filasRendir2 == 1) {
                JOptionPane.showMessageDialog(null, "Nota registrada");
            }
            
            String sqlRendir3 = " UPDATE inscripcion "
                    + " SET nota = 10 "
                    + " WHERE idAlumno = 3 "
                    + " AND idMateria = 3 ";
            PreparedStatement psRendir3 = conexion.prepareStatement(sqlRendir3);
            int filasRendir3 = psRendir3.executeUpdate();
            if(filasRendir3 == 1) {
                JOptionPane.showMessageDialog(null, "Nota registrada");
            }
            
            // Armar el SELECT y mostrar los resultados
            String sqlNotaMayorOcho = "SELECT "
                    + " alumno.dni AS DNI,"
                    + " alumno.apellido AS Apellido,"
                    + " alumno.nombre AS Nombre ,"
                    + " alumno.fechaNacimiento AS FechaNacimiento,"
                    + " alumno.estado AS AlumnoActivo,"
                    + " materia.nombre AS Materia, "
                    + " materia.año AS Año, "
                    + " nota AS Nota "
                    + " FROM inscripcion "
                    + " JOIN alumno ON (inscripcion.idAlumno = alumno.idAlumno) "
                    + " JOIN materia ON (inscripcion.idMateria = materia.idMateria) "
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
