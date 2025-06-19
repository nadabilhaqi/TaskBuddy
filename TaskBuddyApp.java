import java.util.*;

/**
 * TaskBuddy Application - Main Class
 * Aplikasi manajemen task dengan sistem antrian user dan struktur tree task
 */
public class TaskBuddyApp {
    // Constants
    private static final int MAX_QUEUE_SIZE = 5;
    private static final String APP_TITLE = "🚀 Selamat datang di TaskBuddy!";
    private static final String SEPARATOR = "================================";

    // Core components
    private UserQueue userQueue;
    private Scanner scanner;
    private LinkedList<User> registeredUsers;
    private HashMap<String, TaskTree> userTaskTrees;
    private ActivityLogList activityLogList; // Added for activity logging

    /**
     * Constructor - Inisialisasi aplikasi dengan data default
     */
    public TaskBuddyApp() {
        initializeComponents();
        loadDefaultUsers();
        initializeUserTaskTrees();
    }

    /**
     * Method utama untuk menjalankan aplikasi
     */
    public void run() {
        displayWelcomeMessage();
        runMainLoop();
        displayGoodbyeMessage();
    }

    // ==================== INITIALIZATION METHODS ====================

    private void initializeComponents() {
        userQueue = new UserQueue(MAX_QUEUE_SIZE);
        scanner = new Scanner(System.in);
        registeredUsers = new LinkedList<>();
        userTaskTrees = new HashMap<>();
        activityLogList = new ActivityLogList(); // Initialize activity log list
    }

    private void loadDefaultUsers() {
        registeredUsers.add(new User("admin", "admin123", "admin@taskbuddy.com"));
        registeredUsers.add(new User("user1", "pass123", "user1@email.com"));
        registeredUsers.add(new User("guest", "guest", ""));
    }

    private void initializeUserTaskTrees() {
        for (User user : registeredUsers) {
            userTaskTrees.put(user.getUsername(), new TaskTree(user.getUsername()));
        }
    }

    // ==================== MAIN FLOW METHODS ====================

    private void displayWelcomeMessage() {
        System.out.println(APP_TITLE);
        System.out.println(SEPARATOR);
    }

    private void runMainLoop() {
        while (true) {
            showMainMenu();
            int choice = getIntInput();

            if (!processMainMenuChoice(choice)) {
                break; // Exit application
            }
        }
    }

    private void displayGoodbyeMessage() {
        System.out.println("👋 Terima kasih telah menggunakan TaskBuddy!");
    }

    // ==================== MENU DISPLAY METHODS ====================

    private void showMainMenu() {
        System.out.println("\n📋 MENU UTAMA:");
        System.out.println("1. Login");
        System.out.println("2. Logout User Aktif");
        System.out.println("3. Lihat User Aktif");
        System.out.println("4. Lihat Antrian User");
        System.out.println("5. Menu Task");
        System.out.println("6. Lihat Semua User Terdaftar");
        System.out.println("7. Register User Baru");
        System.out.println("8. Lihat Log Aktivitas"); // New menu option
        System.out.println("0. Keluar");
        System.out.print("Pilih menu: ");
    }

    private void showTaskMenu() {
        User currentUser = userQueue.getCurrentActiveUser();
        System.out.println("\n📝 MENU TASK - " + currentUser.getUsername().toUpperCase());
        System.out.println(SEPARATOR);
        System.out.println("1. Buat Task Kategori (Root)");
        System.out.println("2. Buat Subtask");
        System.out.println("3. Lihat Task Tree");
        System.out.println("4. Tandai Task Selesai");
        System.out.println("5. Hapus Task");
        System.out.println("6. Ringkasan Task");
        System.out.println("7. Urutkan Task");
        System.out.println("8. Cari Task");
        System.out.println("0. Kembali ke Menu Utama");
        System.out.print("Pilih menu: ");
    }

    // ==================== MENU PROCESSING METHODS ====================

    private boolean processMainMenuChoice(int choice) {
        switch (choice) {
            case 1 -> handleLogin();
            case 2 -> handleLogout();
            case 3 -> showCurrentUser();
            case 4 -> userQueue.displayQueue();
            case 5 -> handleTaskMenu();
            case 6 -> showAllRegisteredUsers();
            case 7 -> registerNewUser();
            case 8 -> displayActivityLog(); // Handle new menu option
            case 0 -> {
                return false; // Exit application
            }
            default -> System.out.println("❌ Pilihan tidak valid!");
        }
        return true; // Continue application
    }

    private void processTaskMenuChoice(int choice) {
        switch (choice) {
            case 1 -> createRootTask();
            case 2 -> createSubtask();
            case 3 -> displayTaskTree();
            case 4 -> completeTask();
            case 5 -> removeTask();
            case 6 -> displayTaskSummary();
            case 7 -> handleSortMenu();
            case 8 -> handleSearchMenu();
            // case 0 handled in calling method
            default -> System.out.println("❌ Pilihan tidak valid!");
        }
    }

    // ==================== USER MANAGEMENT METHODS ====================

    private void handleLogin() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        if (username.isEmpty()) {
            System.out.println("❌ Username tidak boleh kosong!");
            return;
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = findUserByCredentials(username, password);
        if (user != null) {
            userQueue.loginUser(user);
            activityLogList.addLog(username, "Logged in"); // Log login action
        } else {
            System.out.println("❌ Username atau password salah!");
        }
    }

    private void handleLogout() {
        User currentUser = userQueue.getCurrentActiveUser();
        if (userQueue.logoutCurrentUser() && currentUser != null) {
            activityLogList.addLog(currentUser.getUsername(), "Logged out"); // Log logout action
        }
    }

    private void showCurrentUser() {
        User currentUser = userQueue.getCurrentActiveUser();
        if (currentUser != null) {
            System.out.println("👤 User aktif: " + currentUser.getUsername());
            System.out.println("📧 Email: " + (currentUser.getEmail().isEmpty() ? "Tidak ada" : currentUser.getEmail()));
        } else {
            System.out.println("❌ Tidak ada user yang aktif saat ini.");
        }
    }

    private void showAllRegisteredUsers() {
        System.out.println("\n👥 DAFTAR USER TERDAFTAR:");
        System.out.println(SEPARATOR);

        if (registeredUsers.isEmpty()) {
            System.out.println("Belum ada user terdaftar.");
            return;
        }

        for (int i = 0; i < registeredUsers.size(); i++) {
            User user = registeredUsers.get(i);
            System.out.println((i + 1) + ". " + user.getUsername() +
                    " (" + (user.getEmail().isEmpty() ? "No email" : user.getEmail()) + ")");
        }
    }

    private void registerNewUser() {
        System.out.println("\n📝 REGISTRASI USER BARU:");
        System.out.println(SEPARATOR);

        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        if (username.isEmpty()) {
            System.out.println("❌ Username tidak boleh kosong!");
            return;
        }

        if (isUsernameExists(username)) {
            System.out.println("❌ Username sudah digunakan!");
            return;
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (password.isEmpty()) {
            System.out.println("❌ Password tidak boleh kosong!");
            return;
        }

        System.out.print("Email (opsional): ");
        String email = scanner.nextLine().trim();

        User newUser = new User(username, password, email);
        registeredUsers.add(newUser);
        userTaskTrees.put(username, new TaskTree(username));
        activityLogList.addLog(username, "Registered new user"); // Log registration action

        System.out.println("✅ User '" + username + "' berhasil didaftarkan!");
    }

    // ==================== TASK MENU HANDLER ====================

    private void handleTaskMenu() {
        if (!validateActiveUser()) return;

        while (true) {
            showTaskMenu();
            int choice = getIntInput();

            if (choice == 0) {
                return; // Kembali ke menu utama
            }

            processTaskMenuChoice(choice);
        }
    }

    // ==================== TASK CREATION METHODS ====================

    private void createRootTask() {
        User currentUser = userQueue.getCurrentActiveUser();
        TaskTree taskTree = userTaskTrees.get(currentUser.getUsername());

        TaskCreationData data = getTaskCreationData("kategori/task");
        if (data == null) return;

        Task newTask = new Task(data.name, data.description, data.priority,
                data.deadline, currentUser.getUsername());
        if (taskTree.addRootTask(newTask)) {
            activityLogList.addLog(currentUser.getUsername(),
                    "Created root task '" + data.name + "'"); // Log root task creation
        }
    }

    private void createSubtask() {
        User currentUser = userQueue.getCurrentActiveUser();
        TaskTree taskTree = userTaskTrees.get(currentUser.getUsername());

        if (taskTree.isEmpty()) {
            System.out.println("❌ Belum ada task kategori! Buat task kategori terlebih dahulu.");
            return;
        }

        displayAllTasksWithNumbers(taskTree);

        System.out.print("\nMasukkan ID task parent: ");
        String parentId = scanner.nextLine().trim();

        if (parentId.isEmpty()) {
            System.out.println("❌ ID task parent tidak boleh kosong!");
            return;
        }

        TaskCreationData data = getTaskCreationData("subtask");
        if (data == null) return;

        Task subtask = new Task(data.name, data.description, data.priority,
                data.deadline, currentUser.getUsername());
        if (taskTree.addSubtask(parentId, subtask)) {
            activityLogList.addLog(currentUser.getUsername(),
                    "Created subtask '" + data.name + "' under task ID " + parentId); // Log subtask creation
        }
    }

    // ==================== TASK MANAGEMENT METHODS ====================

    private void displayTaskTree() {
        User currentUser = userQueue.getCurrentActiveUser();
        TaskTree taskTree = userTaskTrees.get(currentUser.getUsername());
        taskTree.displayTree();
    }

    private void completeTask() {
        User currentUser = userQueue.getCurrentActiveUser();
        TaskTree taskTree = userTaskTrees.get(currentUser.getUsername());

        if (taskTree.isEmpty()) {
            System.out.println("❌ Belum ada task!");
            return;
        }

        ArrayList<Task> incompleteTasks = taskTree.getIncompleteTasks();
        if (incompleteTasks.isEmpty()) {
            System.out.println("🎉 Semua task sudah selesai!");
            return;
        }

        displayTasksWithNumbers(incompleteTasks, "TASK YANG BELUM SELESAI");

        System.out.print("\nMasukkan ID task yang akan ditandai selesai: ");
        String taskId = scanner.nextLine().trim();

        if (!taskId.isEmpty()) {
            Task task = taskTree.findTaskById(taskId);
            if (taskTree.completeTask(taskId) && task != null) {
                activityLogList.addLog(currentUser.getUsername(),
                        "Completed task '" + task.getTaskName() + "' (ID: " + taskId + ")"); // Log task completion
            }
        }
    }

    private void removeTask() {
        User currentUser = userQueue.getCurrentActiveUser();
        TaskTree taskTree = userTaskTrees.get(currentUser.getUsername());

        if (taskTree.isEmpty()) {
            System.out.println("❌ Belum ada task!");
            return;
        }

        displayAllTasksWithNumbers(taskTree);

        System.out.print("\nMasukkan ID task yang akan dihapus: ");
        String taskId = scanner.nextLine().trim();

        if (taskId.isEmpty()) {
            System.out.println("❌ ID task tidak boleh kosong!");
            return;
        }

        Task task = taskTree.findTaskById(taskId);
        if (confirmAction("Yakin ingin menghapus task ini?") && task != null) {
            if (taskTree.removeTask(taskId)) {
                activityLogList.addLog(currentUser.getUsername(),
                        "Removed task '" + task.getTaskName() + "' (ID: " + taskId + ")"); // Log task removal
            }
        } else {
            System.out.println("❌ Penghapusan dibatalkan.");
        }
    }

    private void displayTaskSummary() {
        User currentUser = userQueue.getCurrentActiveUser();
        TaskTree taskTree = userTaskTrees.get(currentUser.getUsername());

        taskTree.displaySummary();

        // Tampilkan task yang terlambat jika ada
        ArrayList<Task> overdueTasks = taskTree.getOverdueTasks();
        if (!overdueTasks.isEmpty()) {
            SearchSort.displayTasks(overdueTasks, "⚠️ TASK TERLAMBAT");
        }
    }

    // ==================== ACTIVITY LOG METHOD ====================

    private void displayActivityLog() {
        System.out.println("\n📜 MENU LOG AKTIVITAS:");
        System.out.println("1. Lihat semua log aktivitas");
        System.out.println("2. Lihat log aktivitas user aktif");
        System.out.println("0. Kembali");
        System.out.print("Pilih opsi: ");

        int choice = getIntInput();

        switch (choice) {
            case 1 -> {
                ArrayList<String> allLogs = activityLogList.getAllLogs();
                displayLogs(allLogs, "SEMUA LOG AKTIVITAS");
            }
            case 2 -> {
                User currentUser = userQueue.getCurrentActiveUser();
                if (currentUser == null) {
                    System.out.println("❌ Tidak ada user aktif!");
                    return;
                }
                ArrayList<String> userLogs = activityLogList.getLogsByUser(currentUser.getUsername());
                displayLogs(userLogs, "LOG AKTIVITAS - " + currentUser.getUsername().toUpperCase());
            }
            case 0 -> {
                return;
            }
            default -> System.out.println("❌ Pilihan tidak valid!");
        }
    }

    private void displayLogs(ArrayList<String> logs, String title) {
        System.out.println("\n📜 " + title);
        System.out.println(SEPARATOR);

        if (logs.isEmpty()) {
            System.out.println("Tidak ada log aktivitas.");
            return;
        }

        for (int i = 0; i < logs.size(); i++) {
            System.out.println((i + 1) + ". " + logs.get(i));
        }
        System.out.println("\nTotal: " + logs.size() + " log(s)");
    }

    // ==================== SEARCH & SORT HANDLERS ====================

    private void handleSortMenu() {
        User currentUser = userQueue.getCurrentActiveUser();
        TaskTree taskTree = userTaskTrees.get(currentUser.getUsername());

        if (taskTree.isEmpty()) {
            System.out.println("❌ Belum ada task untuk diurutkan!");
            return;
        }

        SearchSort.displaySortMenu();
        int choice = getIntInput();

        processSortChoice(taskTree, choice);
    }

    private void handleSearchMenu() {
        User currentUser = userQueue.getCurrentActiveUser();
        TaskTree taskTree = userTaskTrees.get(currentUser.getUsername());

        if (taskTree.isEmpty()) {
            System.out.println("❌ Belum ada task untuk dicari!");
            return;
        }

        SearchSort.displaySearchMenu();
        int choice = getIntInput();

        processSearchChoice(taskTree, choice);
    }

    private void processSortChoice(TaskTree taskTree, int choice) {
        if (choice == 0) return;

        ArrayList<Task> allTasks = taskTree.getAllTasks();
        ArrayList<Task> sortedTasks = null;
        String title = "";

        switch (choice) {
            case 1 -> {
                sortedTasks = SearchSort.sortByPriority(allTasks);
                title = "Task diurutkan berdasarkan Prioritas";
            }
            case 2 -> {
                sortedTasks = SearchSort.sortByDeadline(allTasks);
                title = "Task diurutkan berdasarkan Deadline";
            }
            case 3 -> {
                sortedTasks = SearchSort.sortByName(allTasks);
                title = "Task diurutkan berdasarkan Nama";
            }
            case 4 -> {
                sortedTasks = SearchSort.sortByStatus(allTasks);
                title = "Task diurutkan berdasarkan Status";
            }
            default -> {
                System.out.println("❌ Pilihan tidak valid!");
                return;
            }
        }

        if (sortedTasks != null) {
            SearchSort.displayTasks(sortedTasks, title);
        }
    }

    private void processSearchChoice(TaskTree taskTree, int choice) {
        if (choice == 0) return;

        ArrayList<Task> allTasks = taskTree.getAllTasks();
        ArrayList<Task> searchResults = null;
        String title = "";

        switch (choice) {
            case 1 -> {
                System.out.print("Masukkan kata kunci: ");
                String keyword = scanner.nextLine().trim();
                if (!keyword.isEmpty()) {
                    searchResults = SearchSort.searchByName(allTasks, keyword);
                    title = "Hasil pencarian: '" + keyword + "'";
                }
            }
            case 2 -> {
                int priority = getPriorityInput();
                if (priority != -1) {
                    searchResults = SearchSort.searchByPriority(allTasks, priority);
                    String priorityName = getPriorityName(priority);
                    title = "Task dengan prioritas " + priorityName;
                }
            }
            case 3 -> {
                int statusChoice = getStatusInput();
                if (statusChoice != -1) {
                    boolean isCompleted = (statusChoice == 2);
                    searchResults = SearchSort.searchByStatus(allTasks, isCompleted);
                    title = "Task dengan status " + (isCompleted ? "SELESAI" : "BELUM SELESAI");
                }
            }
            case 4 -> {
                searchResults = SearchSort.searchOverdueTasks(allTasks);
                title = "⚠️ Task yang Terlambat";
            }
            default -> {
                System.out.println("❌ Pilihan tidak valid!");
                return;
            }
        }

        if (searchResults != null) {
            SearchSort.displayTasks(searchResults, title);
        }
    }

    // ==================== UTILITY METHODS ====================

    private boolean validateActiveUser() {
        if (userQueue.getCurrentActiveUser() == null) {
            System.out.println("❌ Silakan login terlebih dahulu!");
            return false;
        }
        return true;
    }

    private User findUserByCredentials(String username, String password) {
        return registeredUsers.stream()
                .filter(user -> user.getUsername().equals(username) &&
                        user.validatePassword(password))
                .findFirst()
                .orElse(null);
    }

    private boolean isUsernameExists(String username) {
        return registeredUsers.stream()
                .anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }

    private boolean confirmAction(String message) {
        System.out.print(message + " (y/n): ");
        String confirm = scanner.nextLine().trim();
        return confirm.toLowerCase().startsWith("y");
    }

    private int getIntInput() {
        try {
            String input = scanner.nextLine().trim();
            return input.isEmpty() ? -1 : Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private int getPriorityInput() {
        System.out.println("Pilih prioritas:");
        System.out.println("1. 🔴 HIGH");
        System.out.println("2. 🟡 MEDIUM");
        System.out.println("3. 🟢 LOW");
        System.out.print("Pilih (1-3): ");

        int priority = getIntInput();
        return (priority >= 1 && priority <= 3) ? priority : -1;
    }

    private int getStatusInput() {
        System.out.println("Pilih status:");
        System.out.println("1. Belum Selesai");
        System.out.println("2. Sudah Selesai");
        System.out.print("Pilih (1-2): ");

        int status = getIntInput();
        return (status >= 1 && status <= 2) ? status : -1;
    }

    private String getPriorityName(int priority) {
        return switch (priority) {
            case 1 -> "HIGH";
            case 2 -> "MEDIUM";
            case 3 -> "LOW";
            default -> "UNKNOWN";
        };
    }

    private void displayTasksWithNumbers(ArrayList<Task> tasks, String title) {
        System.out.println("\n📋 " + title + ":");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i).toString());
        }
    }

    private void displayAllTasksWithNumbers(TaskTree taskTree) {
        ArrayList<Task> allTasks = taskTree.getAllTasks();
        displayTasksWithNumbers(allTasks, "DAFTAR TASK YANG ADA");
    }

    // ==================== TASK CREATION HELPER ====================

    private static class TaskCreationData {
        String name;
        String description;
        int priority;
        String deadline;

        TaskCreationData(String name, String description, int priority, String deadline) {
            this.name = name;
            this.description = description;
            this.priority = priority;
            this.deadline = deadline;
        }
    }

    private TaskCreationData getTaskCreationData(String taskType) {
        System.out.print("Nama " + taskType + ": ");
        String taskName = scanner.nextLine().trim();

        if (taskName.isEmpty()) {
            System.out.println("❌ Nama " + taskType + " tidak boleh kosong!");
            return null;
        }

        System.out.print("Deskripsi (opsional): ");
        String description = scanner.nextLine().trim();

        int priority = getPriorityInput();
        if (priority == -1) {
            priority = 2; // Default medium
            System.out.println("⚠️ Prioritas tidak valid, menggunakan MEDIUM sebagai default.");
        }

        System.out.print("Deadline (dd-MM-yyyy, kosongkan jika tidak ada): ");
        String deadlineStr = scanner.nextLine().trim();

        return new TaskCreationData(taskName, description, priority, deadlineStr);
    }

    // ==================== MAIN METHOD ====================

    public static void main(String[] args) {
        TaskBuddyApp app = new TaskBuddyApp();
        app.run();
    }
}