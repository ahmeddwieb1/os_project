package main;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==================================================================");
        System.out.println("|   ####   ####      #    #      ####  #### #    # #     #####  |");
        System.out.println("|  #    # #         # #   #     #        #  ##  ## #     #    # |");
        System.out.println("|  #    #  ####    #####  #      ####    #  # ## # #     #####  |");
        System.out.println("|  #    #      #  #     # #          #   #  #    # #     #   #  |");
        System.out.println("|   ####   ####  #       # #####  ####  #### #    # ##### #    # |");
        System.out.println("==================================================================");
        System.out.println("| 1 | FIFO Page Replacement       |");
        System.out.println("====================================");
        System.out.println("| 2 | Clock Page Replacement      |");
        System.out.println("====================================");
        System.out.println("| 3 | Double Hashing              |");
        System.out.println("====================================");
        System.out.println("| 4 | Quadratic Hashing           |");
        System.out.println("====================================");
        System.out.println("| 5 | Linear Hashing              |");
        System.out.println("====================================");
        System.out.println("| 6 | Open Hashing (Chaining)     |");
        System.out.println("====================================");

        System.out.print("Choose algorithm (1-6): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                runFIFO(scanner);
                break;
            case 2:
                runClock(scanner);
                break;
            case 3:
                runDoubleHashing(scanner);
                break;
            case 4:
                runQuadraticHashing(scanner);
                break;
            case 5:
                runLinearHashing(scanner);
                break;
            case 6:
                runOpenHashing(scanner);
                break;
            default:
                System.out.println("Invalid choice!");
        }

        scanner.close();
    }

    // ==================== FIFO PAGE REPLACEMENT ====================
    public static void runFIFO(Scanner sc) {
        try {
            int frameCount = 0;
            while (frameCount <= 0) {
                System.out.print("Enter number of frames (positive integer): ");
                if (sc.hasNextInt()) {
                    frameCount = sc.nextInt();
                    if (frameCount <= 0) System.out.println("Frame count must be positive.");
                } else {
                    System.out.println("Invalid input. Please enter a positive integer.");
                    sc.next();
                }
            }

            int n = 0;
            while (n <= 0) {
                System.out.print("Enter number of page requests (positive integer): ");
                if (sc.hasNextInt()) {
                    n = sc.nextInt();
                    if (n <= 0) System.out.println("Number of page requests must be positive.");
                } else {
                    System.out.println("Invalid input. Please enter a positive integer.");
                    sc.next();
                }
            }

            int[] pageRequests = new int[n];
            System.out.println("Enter page numbers (non-negative integers):");
            for (int i = 0; i < n; i++) {
                while (true) {
                    if (sc.hasNextInt()) {
                        int page = sc.nextInt();
                        if (page < 0) {
                            System.out.println("Page number cannot be negative. Enter again:");
                        } else {
                            pageRequests[i] = page;
                            break;
                        }
                    } else {
                        System.out.println("Invalid input. Enter a non-negative integer:");
                        sc.next();
                    }
                }
            }

            int[] frames = new int[frameCount];
            Arrays.fill(frames, -1);
            Map<Integer, PageTableEntry> pageTable = new HashMap<>();
            Queue<Integer> fifoQueue = new LinkedList<>();

            int pageFaults = 0;
            int pageHits = 0;

            System.out.println("\n========== FIFO SIMULATION ==========\n");

            for (int page : pageRequests) {
                PageTableEntry entry = pageTable.getOrDefault(page, new PageTableEntry());

                if (entry.valid) {
                    System.out.println("Page " + page + " HIT");
                    pageHits++;
                } else {
                    System.out.println("Page " + page + " FAULT");
                    pageFaults++;

                    if (fifoQueue.size() < frameCount) {
                        for (int i = 0; i < frames.length; i++) {
                            if (frames[i] == -1) {
                                frames[i] = page;
                                entry.frameNumber = i;
                                entry.valid = true;
                                fifoQueue.add(page);
                                break;
                            }
                        }
                    } else {
                        int victimPage = fifoQueue.poll();
                        int victimFrame = pageTable.get(victimPage).frameNumber;
                        System.out.println("  → Evicting page " + victimPage + " from frame " + victimFrame);

                        frames[victimFrame] = page;
                        entry.frameNumber = victimFrame;
                        entry.valid = true;
                        fifoQueue.add(page);

                        PageTableEntry victimEntry = pageTable.get(victimPage);
                        victimEntry.valid = false;
                        victimEntry.frameNumber = -1;
                        pageTable.put(victimPage, victimEntry);
                    }
                    pageTable.put(page, entry);
                }

                System.out.print("Frames: ");
                for (int f : frames) {
                    if (f == -1) System.out.print("- ");
                    else System.out.print(f + " ");
                }
                System.out.println("\n");
            }

            System.out.println("========== SUMMARY ==========");
            System.out.println("Total Requests: " + n);
            System.out.println("Page Faults: " + pageFaults);
            System.out.println("Page Hits: " + pageHits);
            System.out.printf("Hit Rate: %.2f%%\n", (pageHits * 100.0 / n));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ==================== CLOCK PAGE REPLACEMENT ====================
    public static void runClock(Scanner scanner) {
        int frameCount = 0;
        while (true) {
            try {
                System.out.print("Enter number of frames (positive integer): ");
                frameCount = scanner.nextInt();
                if (frameCount <= 0) {
                    System.out.println("Error: Number of frames must be greater than 0.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid integer.");
                scanner.next();
            }
        }

        ClockManager manager = new ClockManager(frameCount);

        int n = 0;
        while (true) {
            try {
                System.out.print("Enter the number of pages in the reference string: ");
                n = scanner.nextInt();
                if (n <= 0) {
                    System.out.println("Error: Reference string length must be positive.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid integer.");
                scanner.next();
            }
        }

        int[] referenceString = new int[n];
        scanner.nextLine();

        while (true) {
            System.out.println("\nEnter " + n + " page IDs (space or comma separated):");
            String inputLine = scanner.nextLine().trim();
            String[] parts = inputLine.split("[,\\s]+");

            if (parts.length != n) {
                System.out.println("Error: Expected " + n + " values, got " + parts.length);
                continue;
            }

            try {
                boolean allValid = true;
                for (int i = 0; i < n; i++) {
                    int p = Integer.parseInt(parts[i].trim());
                    if (p < 0) {
                        System.out.println("Error: Page ID cannot be negative.");
                        allValid = false;
                        break;
                    }
                    referenceString[i] = p;
                }
                if (allValid) break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Use only numbers.");
            }
        }

        System.out.println("\n========== CLOCK SIMULATION ==========\n");
        for (int page : referenceString) {
            manager.accessPage(page);
        }

        manager.printSummary();
    }

    // ==================== DOUBLE HASHING ====================
    public static void runDoubleHashing(Scanner scanner) {
        System.out.print("Enter table size: ");
        int tableSize = scanner.nextInt();
        if (tableSize <= 1) {
            System.out.println("Wrong table size.");
            return;
        }

        DoubleHashing doubleHash = new DoubleHashing(tableSize);
        System.out.println("Enter words to hash (type 'done' to finish):");
        scanner.nextLine();
        while (true) {
            String word = scanner.nextLine().trim();
            if (word.equalsIgnoreCase("done")) break;
            if (!word.isEmpty()) doubleHash.insert(word);
        }

        doubleHash.printTable();
        System.out.println("\nTotal Collisions: " + doubleHash.getCollisionCount());
    }

    // ==================== QUADRATIC HASHING ====================
    public static void runQuadraticHashing(Scanner scanner) {
        System.out.print("Enter table size: ");
        int tableSize = scanner.nextInt();
        if (tableSize <= 1) {
            System.out.println("Wrong table size.");
            return;
        }

        QuadraticHashing quadHash = new QuadraticHashing(tableSize);
        System.out.println("Enter words to hash (type 'done' to finish):");
        scanner.nextLine();
        while (true) {
            String word = scanner.nextLine().trim();
            if (word.equalsIgnoreCase("done")) break;
            if (!word.isEmpty()) quadHash.insert(word);
        }

        quadHash.printTable();
        System.out.println("\nTotal Collisions: " + quadHash.getCollisionCount());
    }

    // ==================== LINEAR HASHING ====================
    public static void runLinearHashing(Scanner scanner) {
        System.out.print("Enter table size: ");
        int tableSize = scanner.nextInt();
        if (tableSize <= 1) {
            System.out.println("Wrong table size.");
            return;
        }

        LinearHashing linHash = new LinearHashing(tableSize);
        System.out.println("Enter words to hash (type 'done' to finish):");
        scanner.nextLine();
        while (true) {
            String word = scanner.nextLine().trim();
            if (word.equalsIgnoreCase("done")) break;
            if (!word.isEmpty()) linHash.insert(word);
        }

        linHash.printTable();
        System.out.println("\nTotal Collisions: " + linHash.getCollisionCount());
    }

    // ==================== OPEN HASHING (CHAINING) ====================
    public static void runOpenHashing(Scanner scanner) {
        System.out.print("Enter table size: ");
        int tableSize = scanner.nextInt();
        if (tableSize <= 1) {
            System.out.println("Wrong table size.");
            return;
        }

        OpenHashing openHash = new OpenHashing(tableSize);
        System.out.println("Enter words to hash (type 'done' to finish):");
        scanner.nextLine();
        while (true) {
            String word = scanner.nextLine().trim();
            if (word.equalsIgnoreCase("done")) break;
            if (!word.isEmpty()) openHash.insert(word);
        }

        openHash.printTable();
    }
}

// ==================== PAGE TABLE ENTRY ====================
class PageTableEntry {
    boolean valid;
    int frameNumber;

    public PageTableEntry() {
        valid = false;
        frameNumber = -1;
    }
}

// ==================== CLOCK MANAGER ====================
class ClockManager {
    private int[] frames;
    private boolean[] referenceBits;
    private int clockHand;
    private int pageFaults;
    private int pageHits;
    private Map<Integer, Integer> pageToFrame;

    public ClockManager(int frameCount) {
        this.frames = new int[frameCount];
        this.referenceBits = new boolean[frameCount];
        Arrays.fill(frames, -1);
        this.clockHand = 0;
        this.pageFaults = 0;
        this.pageHits = 0;
        this.pageToFrame = new HashMap<>();
    }

    public void accessPage(int page) {
        if (pageToFrame.containsKey(page)) {
            System.out.println("Page " + page + " HIT");
            pageHits++;
            int frameIndex = pageToFrame.get(page);
            referenceBits[frameIndex] = true;
        } else {
            System.out.println("Page " + page + " FAULT");
            pageFaults++;

            int emptyFrame = findEmptyFrame();
            if (emptyFrame != -1) {
                frames[emptyFrame] = page;
                referenceBits[emptyFrame] = true;
                pageToFrame.put(page, emptyFrame);
            } else {
                while (referenceBits[clockHand]) {
                    referenceBits[clockHand] = false;
                    clockHand = (clockHand + 1) % frames.length;
                }

                int victimPage = frames[clockHand];
                System.out.println("  → Evicting page " + victimPage + " from frame " + clockHand);
                pageToFrame.remove(victimPage);

                frames[clockHand] = page;
                referenceBits[clockHand] = true;
                pageToFrame.put(page, clockHand);

                clockHand = (clockHand + 1) % frames.length;
            }
        }

        System.out.print("Frames: ");
        for (int f : frames) {
            if (f == -1) System.out.print("- ");
            else System.out.print(f + " ");
        }
        System.out.println();
    }

    private int findEmptyFrame() {
        for (int i = 0; i < frames.length; i++) {
            if (frames[i] == -1) return i;
        }
        return -1;
    }

    public void printSummary() {
        int total = pageFaults + pageHits;
        System.out.println("\n========== SUMMARY ==========");
        System.out.println("Total Requests: " + total);
        System.out.println("Page Faults: " + pageFaults);
        System.out.println("Page Hits: " + pageHits);
        System.out.printf("Hit Rate: %.2f%%\n", (pageHits * 100.0 / total));
    }
}

// ==================== DOUBLE HASHING ====================
class DoubleHashing {
    private String[] table;
    private int collisionCount;

    public DoubleHashing(int size) {
        table = new String[size];
        collisionCount = 0;
    }

    private int hash1(String key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    private int hash2(String key) {
        int h = Math.abs(key.hashCode()) % (table.length - 1);
        return h == 0 ? 1 : h;
    }

    public void insert(String key) {
        int index = hash1(key);
        int step = hash2(key);
        int i = 0;

        while (table[index] != null) {
            collisionCount++;
            i++;
            index = (hash1(key) + i * step) % table.length;
            if (i >= table.length) {
                System.out.println("Table is full!");
                return;
            }
        }
        table[index] = key;
    }

    public void printTable() {
        System.out.println("\n========== DOUBLE HASHING TABLE ==========");
        for (int i = 0; i < table.length; i++) {
            System.out.println("Index " + i + ": " + (table[i] == null ? "empty" : table[i]));
        }
    }

    public int getCollisionCount() {
        return collisionCount;
    }
}

// ==================== QUADRATIC HASHING ====================
class QuadraticHashing {
    private String[] table;
    private int collisionCount;

    public QuadraticHashing(int size) {
        table = new String[size];
        collisionCount = 0;
    }

    private int hash(String key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    public void insert(String key) {
        int index = hash(key);
        int i = 0;

        while (table[index] != null) {
            collisionCount++;
            i++;
            index = (hash(key) + i * i) % table.length;
            if (i >= table.length) {
                System.out.println("Table is full!");
                return;
            }
        }
        table[index] = key;
    }

    public void printTable() {
        System.out.println("\n========== QUADRATIC HASHING TABLE ==========");
        for (int i = 0; i < table.length; i++) {
            System.out.println("Index " + i + ": " + (table[i] == null ? "empty" : table[i]));
        }
    }

    public int getCollisionCount() {
        return collisionCount;
    }
}

// ==================== LINEAR HASHING ====================
class LinearHashing {
    private String[] table;
    private int collisionCount;

    public LinearHashing(int size) {
        table = new String[size];
        collisionCount = 0;
    }

    private int hash(String key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    public void insert(String key) {
        int index = hash(key);

        while (table[index] != null) {
            collisionCount++;
            index = (index + 1) % table.length;
        }
        table[index] = key;
    }

    public void printTable() {
        System.out.println("\n========== LINEAR HASHING TABLE ==========");
        for (int i = 0; i < table.length; i++) {
            System.out.println("Index " + i + ": " + (table[i] == null ? "empty" : table[i]));
        }
    }

    public int getCollisionCount() {
        return collisionCount;
    }
}

// ==================== OPEN HASHING (CHAINING) ====================
class OpenHashing {
    private LinkedList<String>[] table;

    @SuppressWarnings("unchecked")
    public OpenHashing(int size) {
        table = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            table[i] = new LinkedList<>();
        }
    }

    private int hash(String key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    public void insert(String key) {
        int index = hash(key);
        table[index].add(key);
    }

    public void printTable() {
        System.out.println("\n========== OPEN HASHING TABLE (CHAINING) ==========");
        for (int i = 0; i < table.length; i++) {
            System.out.print("Index " + i + ": ");
            if (table[i].isEmpty()) {
                System.out.println("empty");
            } else {
                System.out.println(table[i]);
            }
        }
    }
}