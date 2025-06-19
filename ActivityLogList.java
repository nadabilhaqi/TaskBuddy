import java.util.ArrayList;

// ActivityLogList.java
public class ActivityLogList {
    private ActivityLog head;
    private ActivityLog tail;
    private int size;

    public ActivityLogList() {
        head = null;
        tail = null;
        size = 0;
    }

    // Add log to the end of list
    public void addLog(String username, String activity) {
        ActivityLog newNode = new ActivityLog(username, activity);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        size++;
    }

    // Get all logs as list (from newest to oldest)
    public ArrayList<String> getAllLogs() {
        ArrayList<String> logs = new ArrayList<>();
        ActivityLog current = tail;

        while (current != null) {
            logs.add(current.toString());
            current = current.getPrev();
        }

        return logs;
    }

    // Get logs by username
    public ArrayList<String> getLogsByUser(String username) {
        ArrayList<String> userLogs = new ArrayList<>();
        ActivityLog current = tail;

        while (current != null) {
            if (current.getUsername().equals(username)) {
                userLogs.add(current.toString());
            }
            current = current.getPrev();
        }

        return userLogs;
    }

    // Get recent N logs
    public ArrayList<String> getRecentLogs(int count) {
        ArrayList<String> recentLogs = new ArrayList<>();
        ActivityLog current = tail;
        int collected = 0;

        while (current != null && collected < count) {
            recentLogs.add(current.toString());
            current = current.getPrev();
            collected++;
        }

        return recentLogs;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}