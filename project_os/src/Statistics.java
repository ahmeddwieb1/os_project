class Statistics {
    private int pageFaults;
    private int pageHits;

    public Statistics() {
        this.pageFaults = 0;
        this.pageHits = 0;
    }

    public void incrementPageFaults() {
        pageFaults++;
    }

    public void incrementPageHits() {
        pageHits++;
    }

    public int getPageFaults() {
        return pageFaults;
    }

    public int getPageHits() {
        return pageHits;
    }

    public void display() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("         PERFORMANCE STATISTICS");
        System.out.println("=".repeat(50));
        System.out.println("Total Page Faults:  " + pageFaults);
        System.out.println("Total Page Hits:    " + pageHits);
        int total = pageFaults + pageHits;
        double hitRatio = total > 0 ? (double) pageHits / total * 100 : 0;
        double faultRatio = total > 0 ? (double) pageFaults / total * 100 : 0;
        System.out.printf("Hit Ratio:          %.2f%%\n", hitRatio);
        System.out.printf("Fault Ratio:        %.2f%%\n", faultRatio);
        System.out.println("=".repeat(50));
    }
}