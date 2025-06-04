import java.util.*;

public class UserQueue {
    private ArrayList<User> queue;
    private int maxUsers;

    public UserQueue() {
        queue = new ArrayList<>();
        maxUsers = 5;
    }

    public UserQueue(int maxUsers) {
        queue = new ArrayList<>();
        this.maxUsers = maxUsers;
    }

    // Core Queue Operations
    public boolean enqueue(User user) {
        if (isFull()) {
            System.out.println("❌ Antrian penuh! Maksimal " + maxUsers + " user.");
            return false;
        }
        queue.add(user);
        System.out.println("✅ " + user.getUsername() + " masuk antrian. Posisi: " + queue.size());
        return true;
    }

    public User dequeue() {
        if (isEmpty()) {
            System.out.println("❌ Antrian kosong!");
            return null;
        }
        User user = queue.remove(0);
        user.setActive(false);
        System.out.println("✅ " + user.getUsername() + " keluar dari antrian.");
        return user;
    }

    public User peek() {
        return isEmpty() ? null : queue.get(0);
    }

    // Queue Status
    public boolean isEmpty() { return queue.isEmpty(); }
    public boolean isFull() { return queue.size() >= maxUsers; }
    public int size() { return queue.size(); }

    // User Management
    public boolean loginUser(User user) {
        if (findUserPosition(user.getUsername()) != -1) {
            System.out.println("⚠️ " + user.getUsername() + " sudah ada dalam antrian!");
            return false;
        }

        if (enqueue(user)) {
            // Set user pertama sebagai aktif
            if (queue.size() == 1) {
                user.setActive(true);
                System.out.println("👑 " + user.getUsername() + " menjadi user aktif!");
            }
            return true;
        }
        return false;
    }

    public boolean logoutCurrentUser() {
        User activeUser = getCurrentActiveUser();
        if (activeUser != null) {
            System.out.println("👋 " + activeUser.getUsername() + " logout dari sesi aktif.");
            dequeue();

            // Aktifkan user selanjutnya
            User nextUser = getCurrentActiveUser();
            if (nextUser != null) {
                nextUser.setActive(true);
                System.out.println("👑 " + nextUser.getUsername() + " sekarang menjadi user aktif!");
            }
            return true;
        }

        System.out.println("❌ Tidak ada user aktif saat ini.");
        return false;
    }

    public User getCurrentActiveUser() {
        return peek();
    }

    // Utility Methods
    public int findUserPosition(String username) {
        for (int i = 0; i < queue.size(); i++) {
            if (queue.get(i).getUsername().equalsIgnoreCase(username)) {
                return i + 1; // 1-based position
            }
        }
        return -1;
    }

    public void displayQueue() {
        if (isEmpty()) {
            System.out.println("📋 Antrian kosong.");
            return;
        }

        System.out.println("\n📋 ANTRIAN USER SAAT INI:");
        System.out.println("==========================");
        for (int i = 0; i < queue.size(); i++) {
            String status = (i == 0) ? " 👑 (AKTIF)" : " ⏳ (MENUNGGU)";
            System.out.println((i + 1) + ". " + queue.get(i).getUsername() + status);
        }
        System.out.println("Total: " + queue.size() + "/" + maxUsers);
    }

    public void clearQueue() {
        for (User user : queue) {
            user.setActive(false);
        }
        queue.clear();
        System.out.println("🗑️ Semua user dihapus dari antrian.");
    }
}