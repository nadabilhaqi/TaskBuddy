import java.util.*;

public class UserQueue {
    private LinkedList<User> queue;
    private int maxUsers;

    public UserQueue() {
        queue = new LinkedList<>();
        maxUsers = 5;
    }

    public UserQueue(int maxUsers) {
        queue = new LinkedList<>();
        this.maxUsers = maxUsers;
    }

    public boolean enqueue(User user) {
        if (isFull()) {
            System.out.println("❌ Antrian penuh! Maksimal " + maxUsers + " user.");
            return false;
        }
        queue.addLast(user);
        System.out.println("✅ " + user.getUsername() + " masuk antrian. Posisi: " + queue.size());
        return true;
    }

    public User dequeue() {
        if (isEmpty()) {
            System.out.println("❌ Antrian kosong!");
            return null;
        }
        User user = queue.removeFirst();
        user.setActive(false);
        System.out.println("✅ " + user.getUsername() + " keluar dari antrian.");
        return user;
    }

    public User peek() {
        return isEmpty() ? null : queue.getFirst();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public boolean isFull() {
        return queue.size() >= maxUsers;
    }

    public int size() {
        return queue.size();
    }

    public boolean loginUser(User user) {
        if (findUserPosition(user.getUsername()) != -1) {
            System.out.println("⚠️ " + user.getUsername() + " sudah ada dalam antrian!");
            return false;
        }
        if (enqueue(user)) {
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

    public int findUserPosition(String username) {
        int position = 1;
        for (User user : queue) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return position;
            }
            position++;
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
        int index = 1;
        for (User user : queue) {
            String status = (index == 1) ? " 👑 (AKTIF)" : " ⏳ (MENUNGGU)";
            System.out.println(index + ". " + user.getUsername() + status);
            index++;
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