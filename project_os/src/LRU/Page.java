package LRU;

class Page {
    private int pageNumber;
    private int lastAccessTime;

    public Page(int pageNumber, int accessTime) {
        this.pageNumber = pageNumber;
        this.lastAccessTime = accessTime;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(int time) {
        this.lastAccessTime = time;
    }

    @Override
    public String toString() {
        return String.valueOf(pageNumber);
    }
}