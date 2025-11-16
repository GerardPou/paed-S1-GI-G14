import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public class Main {
    public static void main(String args[]) {
        String filePath = "src/randomSmall.paed";
        ArrayList<Quest> quests = QuestFileReader.readQuestsFromFile(filePath);

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("===== MENÚ PRINCIPAL =====");
            System.out.println("1. Insertion Sort");
            System.out.println("2. Selection Sort");
            System.out.println("3. Quick Sort");
            System.out.println("4. Merge Sort");
            System.out.println("5. Mostrar Questes ordenades");
            System.out.println("\n0. Sortir del programa");
            System.out.print("Escull una opció (0-5): ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();

                System.out.print("");

                switch (opcion) {
                    case 0:
                        System.out.println("\nSortint del programa...");
                        break;
                    case 1:
                        System.out.println("\n→ INSERTION SORT");
                        insertionSort(quests);
                        break;
                    case 2:
                        System.out.println("\n→ SELECTION SORT");
                        selectionSort(quests);
                        break;
                    case 3:
                        System.out.println("\n→ QUICK SORT");
                        quickSort(quests);
                        break;
                    case 4:
                        System.out.println("\n→ MERGE SORT");
                        mergeSort(quests);
                        break;
                    case 5:
                        System.out.println("\n→ Mostrar Questes ordenades");
                        mostrarQuestsOrdenades();
                        break;
                    default:
                        System.out.println("\n⚠ Opció inválida. Has d'escollir entre un número entre 0 i 4.");
                }
            } else {
                System.out.println("\n⚠ Entrada invàlida. Sisplau introdueixi un número entre (0-4)");
                scanner.next();
            }

            System.out.println();

        } while (opcion != 0);

        scanner.close();
    }

    static void mostrarQuestsOrdenades() {
        int opcio;

        do {
            System.out.println("===== ESCULL MÈTODE DE ORDRE =====");
            System.out.println("1. Ascendent");
            System.out.println("2. Ascendent Small");
            System.out.println("\n0. Enradera");
            System.out.print("Escull una opció (0-2): ");

            Scanner scanner = new Scanner(System.in);
            opcio = scanner.nextInt();

            switch (opcio){
                case 1:
                    mostrarDataset("src/ascendent.paed");
                    opcio = 0;
                    break;
                case 2:
                    mostrarDataset("src/ascendentSmall.paed");
                    opcio = 0;
                    break;
                case 0:
                    break;
                default:
                    System.out.println("\n⚠ Opció inválida. Has d'escollir entre un número entre 0 i 2.");
                    break;

            }
        } while (opcio != 0);

    }

    static void mostrarDataset(String path) {
        ArrayList<Quest> quests = QuestFileReader.readQuestsFromFile(path);
        printQuests(quests);
    }
    static void printQuests(ArrayList<Quest> quests) {
        int i = 0;

        System.out.println("===== LLISTA DE QUESTS =====");

        for (Quest q : quests) {
            i++;

            System.out.printf("%d: %s [%s]\n", i, q.getNom(), q.getDataLliurament().toString());
        }

        System.out.println("===================================");
    }

    static void insertionSort(ArrayList<Quest> quests) {

        double startTime = System.nanoTime();

        for (int i = 1; i < quests.size(); i++) {
            Quest key = quests.get(i);
            int j = i - 1;

            while (j >= 0 && quests.get(j).getDataLliurament().compareTo(key.getDataLliurament()) > 0) {
                quests.set(j + 1, quests.get(j));
                j--;
            }
            quests.set(j + 1, key);
        }

        double endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000.0;

        printQuests(quests);

        System.out.printf("Temps d'execució de Insertion Sort: %.2f ms\n", duration);
    }

    static void selectionSort(ArrayList<Quest> quests) {

        double startTime = System.nanoTime();

        int n = quests.size();

        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;

            for (int j = i + 1; j < n; j++) {
                if (quests.get(j).getDataLliurament().compareTo(quests.get(minIdx).getDataLliurament()) < 0) {
                    minIdx = j;
                }
            }

            if (minIdx != i) {
                Quest tmp = quests.get(i);
                quests.set(i, quests.get(minIdx));
                quests.set(minIdx, tmp);
            }
        }

        double endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000.0;

        printQuests(quests);

        System.out.printf("Temps d'execució de Selection Sort: %.2f ms\n", duration);
    }

    static void quickSort(ArrayList<Quest> quests) {
        int i = 0;

        double startTime = System.nanoTime();

        quickSort(quests, 0, quests.size() - 1);

        double endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000.0;

        System.out.println("\nQuests ordenades de menor a major nivell de dificultat:\n");

        for (Quest q : quests) {
            i++;

            System.out.printf("%d: %s [dificultat %.2f]\n", i, q.getNom(), q.calcularNivellDificultat());
        }

        System.out.println("===================================");
        System.out.printf("Temps d'execució de Quick Sort: %.2f ms\n", duration);

    }

    private static void quickSort(ArrayList<Quest> arr, int i, int j) {
        if (i < j) {
            int p = particio(arr, i, j);
            quickSort(arr, i, p);
            quickSort(arr, p + 1, j);
        }
    }

    private static int particio(ArrayList<Quest> arr, int i, int j) {
        int left = i;
        int right = j;
        int meitat = (i + j) / 2;
        double pivot = arr.get(meitat).calcularNivellDificultat();

        while (true) {
            while (arr.get(left).calcularNivellDificultat() < pivot) left++;
            while (arr.get(right).calcularNivellDificultat() > pivot) right--;

            if (left >= right) return right;

            Quest temp = arr.get(left);
            arr.set(left, arr.get(right));
            arr.set(right, temp);

            left++;
            right--;
        }
    }

    static void mergeSort(ArrayList<Quest> quests) {
        int i = 0;

        double startTime = System.nanoTime();

        mergeSort(quests, 0, quests.size() - 1);

        double endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000.0;

        System.out.println("\nQuests ordenades de menor a major nivell de dificultat:\n");

        for (Quest q : quests) {
            i++;

            System.out.printf("%d: %s [dicifultat: %.2f]\n", i, q.getNom(), q.calcularNivellDificultat());
        }

        System.out.println("===================================");
        System.out.printf("Temps d'execució de Merge Sort: %.2f ms\n", duration);
    }

    private static void mergeSort(ArrayList<Quest> arr, int i, int j) {
        if (i < j) {
            int meitat = (i + j) / 2;
            mergeSort(arr, i, meitat);
            mergeSort(arr, meitat + 1, j);
            merge(arr, i, meitat, j);
        }
    }

    private static void merge(ArrayList<Quest> arr, int i, int meitat, int j) {
        ArrayList<Quest> aux = new ArrayList<>(arr);
        int left = i;
        int right = meitat + 1;
        int cursor = i;

        while (left <= meitat && right <= j) {
            double difLeft = arr.get(left).calcularNivellDificultat();
            double difRight = arr.get(right).calcularNivellDificultat();

            if (difLeft <= difRight) {
                aux.set(cursor, arr.get(left));
                left++;
            } else {
                aux.set(cursor, arr.get(right));
                right++;
            }
            cursor++;
        }

        while (left <= meitat) {
            aux.set(cursor, arr.get(left));
            left++;
            cursor++;
        }

        while (right <= j) {
            aux.set(cursor, arr.get(right));
            right++;
            cursor++;
        }

        cursor = i;
        while (cursor <= j) {
            arr.set(cursor, aux.get(cursor));
            cursor++;
        }
    }
}