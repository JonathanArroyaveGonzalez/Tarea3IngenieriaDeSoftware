package Estudiantes;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Descripcion de la Clase
 *lee la información de varios estudiantes de un
 * archivo de texto, donde los datos están separados por comas (formato CSV), y los escribe de
 * nuevo en otro archivo, pero con el formato JSON
 * @author Jonathan A.
 * @version 1.0
*/
public class EstudiantesCSVtoJSON {
    public static void main(String[] args) {
        // Paso 1: ingresar el nombre del archivo CSV
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Ingrese el nombre del archivo CSV: ");
        String csvFile;
        try {
            csvFile = br.readLine();
        } catch (IOException e) {
            System.out.println("Error al leer la entrada del usuario: " + e.getMessage());
            return;
        }

        // Paso 2: Leer el archivo CSV y convertir los datos a objetos Estudiante
        List<Estudiante> estudiantes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    int id = Integer.parseInt(data[0].trim());
                    String nombre = data[1].trim();
                    String apellido = data[2].trim();
                    Estudiante estudiante = new Estudiante(id, nombre, apellido);
                    estudiantes.add(estudiante);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo CSV: " + e.getMessage());
            return;
        }

        // Paso 3: Convertir la lista de estudiantes a formato JSON manualmente
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");
        for (int i = 0; i < estudiantes.size(); i++) {
            Estudiante estudiante = estudiantes.get(i);
            jsonBuilder.append("{");
            jsonBuilder.append("\"id\":").append(estudiante.getId()).append(",");
            jsonBuilder.append("\"nombre\":\"").append(estudiante.getNombre()).append("\",");
            jsonBuilder.append("\"apellido\":\"").append(estudiante.getApellido()).append("\"");
            jsonBuilder.append("}");
            if (i < estudiantes.size() - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]");
        String json = jsonBuilder.toString();

        // Paso 4: Escribir el JSON en un nuevo archivo
        String jsonFile = csvFile.replace(".csv", ".json");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFile))) {
            writer.write(json);
            System.out.println("Archivo JSON creado exitosamente: " + jsonFile);
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo JSON: " + e.getMessage());
        }
    }
}



