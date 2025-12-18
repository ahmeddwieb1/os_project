import java.util.Scanner;

class InputHandler {
    private Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public int getFrameCount() {
        System.out.print("Enter number of frames (1-10): ");
        int frames = scanner.nextInt();
        while (frames < 1 || frames > 10) {
            System.out.print("Invalid! Enter frames (1-10): ");
            frames = scanner.nextInt();
        }
        return frames;
    }

    public int[] getPageReferenceString() {
        System.out.print("Enter number of page references: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        int[] pages = new int[n];
        System.out.println("Enter page reference string (space or comma-separated):");
        String input = scanner.nextLine();

        // Remove commas and extra spaces
        input = input.replaceAll(",", " ").trim();
        String[] tokens = input.split("\\s+");

        if (tokens.length != n) {
            System.out.println("Warning: Expected " + n + " pages, got " + tokens.length);
            n = Math.min(n, tokens.length);
            pages = new int[n];
        }

        for (int i = 0; i < n; i++) {
            pages[i] = Integer.parseInt(tokens[i]);
        }
        return pages;
    }

    public void close() {
        scanner.close();
    }
}