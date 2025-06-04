import java.util.*;

public class TaskBuddyApp {
    private UserQueue userQueue;
    private Scanner scanner;
    private ArrayList<User> registeredUsers;

    public TaskBuddyApp() {
        userQueue = new UserQueue(5);
        scanner = new Scanner(System.in);
        registeredUsers = new ArrayList<>();

        // Tambah beberapa user default untuk testing
        registeredUsers.add(new User("admin", "admin123", "admin@taskbuddy.com"));
        registeredUsers.add(new User("user1", "pass123", "user1@email.com"));
        registeredUsers.add(new User("guest", "guest", ""));
    }

    public void run() {
        System.out.println("🚀 Selamat datang di TaskBuddy!");
        System.out.println("================================");

        while (true) {
            showMainMenu();
            int choice = getIntInput();

            switch (choice) {
                case 1 -> handleLogin();
                case 2 -> handleLogout();
                case 3 -> showCurrentUser();
                case 4 -> userQueue.displayQueue();
                case 5 -> createTask();
                case 6 -> showAllRegisteredUsers();
                case 7 -> registerNewUser();
                case 0 -> {
                    System.out.println("👋 Terima kasih telah menggunakan TaskBuddy!");
                    return;
                }
                default -> System.out.println("❌ Pilihan tidak valid!");
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\n📋 MENU UTAMA:");
        System.out.println("1. Login");
        System.out.println("2. Logout User Aktif");
        System.out.println("3. Lihat User Aktif");
        System.out.println("4. Lihat Antrian User");
        System.out.println("5. Buat Task");
        System.out.println("6. Lihat Semua User Terdaftar");
        System.out.println("7. Register User Baru");
        System.out.println("0. Keluar");
        System.out.print("Pilih menu: ");
    }

    private void handleLogin() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        // Cari user yang terdaftar
        User user = findRegisteredUser(username);
        if (user == null) {
            System.out.println("❌ Username tidak ditemukan!");
            return;
        }

        if (!user.validatePassword(password)) {
            System.out.println("❌ Password salah!");
            return;
        }

        // Login berhasil, tambah ke antrian
        if (userQueue.loginUser(user)) {
            System.out.println("✅ Login berhasil!");
        }
    }

    private void handleLogout() {
        userQueue.logoutCurrentUser();
    }

    private void showCurrentUser() {
        User current = userQueue.getCurrentActiveUser();
        if (current != null) {
            System.out.println("👑 User aktif: " + current.getUsername() +
                    " (" + current.getEmail() + ")");
        } else {
            System.out.println("❌ Tidak ada user aktif saat ini.");
        }
    }

    private void createTask() {
        User currentUser = userQueue.getCurrentActiveUser();
        if (currentUser == null) {
            System.out.println("❌ Silakan login terlebih dahulu!");
            return;
        }

        System.out.print("Nama task: ");
        String taskName = scanner.nextLine();

        if (taskName.trim().isEmpty()) {
            System.out.println("❌ Nama task tidak boleh kosong!");
            return;
        }

        System.out.println("✅ " + currentUser.getUsername() + " berhasil membuat task: '" + taskName + "'");
        // TODO: Integrate dengan TaskTree.java atau Task.java nanti
    }

    private void showAllRegisteredUsers() {
        System.out.println("\n👥 DAFTAR USER TERDAFTAR:");
        System.out.println("=========================");
        for (int i = 0; i < registeredUsers.size(); i++) {
            User user = registeredUsers.get(i);
            String status = user.isActive() ? "🟢" : "🔴";
            System.out.println((i + 1) + ". " + status + " " + user.getUsername() +
                    " (" + user.getEmail() + ")");
        }
    }

    private void registerNewUser() {
        System.out.print("Username baru: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Email (opsional): ");
        String email = scanner.nextLine();

        // Cek apakah username sudah ada
        if (findRegisteredUser(username) != null) {
            System.out.println("❌ Username sudah terdaftar!");
            return;
        }

        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            System.out.println("❌ Username dan password tidak boleh kosong!");
            return;
        }

        User newUser = new User(username, password, email);
        registeredUsers.add(newUser);
        System.out.println("✅ User '" + username + "' berhasil didaftarkan!");
    }

    private User findRegisteredUser(String username) {
        for (User user : registeredUsers) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    private int getIntInput() {
        try {
            String input = scanner.nextLine();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static void main(String[] args) {
        new TaskBuddyApp().run();
    }
}