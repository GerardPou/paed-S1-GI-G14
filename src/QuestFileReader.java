import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class QuestFileReader {

    public static ArrayList<Quest> readQuestsFromFile(String filePath) {
        ArrayList<Quest> questsList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            line = reader.readLine();
            if (line == null) {
                System.err.println("El archivo está vacío.");
                return questsList;
            }

            lineNumber++;
            int expectedCount = 0;
            try {
                expectedCount = Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.err.println("Línea 1: Número de quests inválido (" + line + ")");
            }

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String[] parts = line.split(";");
                if (parts.length != 8) {
                    System.err.println("Línea " + lineNumber + ": Número de campos inválido (" + parts.length + ")");
                    continue;
                }

                try {
                    String nom = parts[0];
                    String assignatura = parts[1];
                    String dataLliuramentStr = parts[2];
                    int tempsEstimat = Integer.parseInt(parts[3]);
                    int dificultat = Integer.parseInt(parts[4]);
                    int progres = Integer.parseInt(parts[5]);
                    String pes = parts[6];
                    String ubicacio = parts[7];

                    if (dificultat < 1 || dificultat > 10) {
                        System.err.println("Línea " + lineNumber + ": Dificultat fuera de rango (" + dificultat + ")");
                        continue;
                    }
                    if (progres < 0 || progres > 100) {
                        System.err.println("Línea " + lineNumber + ": Progrés fuera de rango (" + progres + ")");
                        continue;
                    }
                    if (!pes.matches("^#[0-9A-Fa-f]{6}$")) {
                        System.err.println("Línea " + lineNumber + ": Formato de color inválido (" + pes + ")");
                        continue;
                    }
                    if (!ubicacio.matches("^\\d+-\\d+$")) {
                        System.err.println("Línea " + lineNumber + ": Formato de ubicación inválido (" + ubicacio + ")");
                        continue;
                    }

                    LocalDate dataLliurament;
                    try {
                        dataLliurament = LocalDate.parse(dataLliuramentStr, formatter);
                    } catch (DateTimeParseException e) {
                        System.err.println("Línea " + lineNumber + ": Formato de fecha inválido (" + dataLliuramentStr + ")");
                        continue;
                    }

                    Quest quest = new Quest(nom, assignatura, dataLliurament,
                            tempsEstimat, dificultat, progres, pes, ubicacio);

                    questsList.add(quest);

                } catch (NumberFormatException e) {
                    System.err.println("Línea " + lineNumber + ": Error al parsear números (" + e.getMessage() + ")");
                } catch (Exception e) {
                    System.err.println("Línea " + lineNumber + ": Error al procesar (" + e.getMessage() + ")");
                }
            }

            if (expectedCount != 0 && questsList.size() != expectedCount) {
                System.err.println("Advertencia: se esperaban " + expectedCount +
                        " quests, pero se leyeron " + questsList.size());
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        return questsList;
    }
}
