// ActivityLogNode.java
public class ActivityLog {
    private String timestamp;
    private String username;
    private String activity;
    private ActivityLog prev;
    private ActivityLog next;

    public ActivityLog(String username, String activity) {
        this.timestamp = java.time.LocalDateTime.now().toString();
        this.username = username;
        this.activity = activity;
        this.prev = null;
        this.next = null;
    }

    // Getters
    public String getTimestamp() { return timestamp; }
    public String getUsername() { return username; }
    public String getActivity() { return activity; }
    public ActivityLog getPrev() { return prev; }
    public ActivityLog getNext() { return next; }

    // Setters
    public void setPrev(ActivityLog prev) { this.prev = prev; }
    public void setNext(ActivityLog next) { this.next = next; }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + username + " - " + activity;
    }
}