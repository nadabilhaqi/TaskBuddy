// SearchSort.java
import java.util.*;

public class SearchSort {

    // ================ SORTING METHODS ================

    // Bubble Sort berdasarkan prioritas (1=High, 2=Medium, 3=Low)
    public static ArrayList<Task> sortByPriority(ArrayList<Task> tasks) {
        if (tasks == null || tasks.size() <= 1) {
            return tasks;
        }

        ArrayList<Task> sortedTasks = new ArrayList<>(tasks);
        int n = sortedTasks.size();

        System.out.println("🔄 Mengurutkan " + n + " task berdasarkan prioritas...");

        // Bubble Sort implementation
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                // Bandingkan prioritas (ascending: 1, 2, 3)
                if (sortedTasks.get(j).getPriority() > sortedTasks.get(j + 1).getPriority()) {
                    // Swap elements
                    Task temp = sortedTasks.get(j);
                    sortedTasks.set(j, sortedTasks.get(j + 1));
                    sortedTasks.set(j + 1, temp);
                    swapped = true;
                }
            }

            // Jika tidak ada swap, array sudah terurut
            if (!swapped) {
                break;
            }
        }

        System.out.println("✅ Pengurutan selesai!");
        return sortedTasks;
    }

    // Bubble Sort berdasarkan deadline (task dengan deadline terdekat di atas)
    public static ArrayList<Task> sortByDeadline(ArrayList<Task> tasks) {
        if (tasks == null || tasks.size() <= 1) {
            return tasks;
        }

        ArrayList<Task> sortedTasks = new ArrayList<>(tasks);
        int n = sortedTasks.size();

        System.out.println("🔄 Mengurutkan " + n + " task berdasarkan deadline...");

        // Bubble Sort implementation
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                Task task1 = sortedTasks.get(j);
                Task task2 = sortedTasks.get(j + 1);

                // Task tanpa deadline ditempatkan di akhir
                if (task1.getDeadline() == null && task2.getDeadline() != null) {
                    // Swap: task dengan deadline naik ke atas
                    Task temp = sortedTasks.get(j);
                    sortedTasks.set(j, sortedTasks.get(j + 1));
                    sortedTasks.set(j + 1, temp);
                    swapped = true;
                } else if (task1.getDeadline() != null && task2.getDeadline() != null) {
                    // Bandingkan deadline (ascending: deadline terdekat di atas)
                    if (task1.getDeadline().isAfter(task2.getDeadline())) {
                        Task temp = sortedTasks.get(j);
                        sortedTasks.set(j, sortedTasks.get(j + 1));
                        sortedTasks.set(j + 1, temp);
                        swapped = true;
                    }
                }
            }

            if (!swapped) {
                break;
            }
        }

        System.out.println("✅ Pengurutan selesai!");
        return sortedTasks;
    }

    // Bubble Sort berdasarkan nama task (alphabetical)
    public static ArrayList<Task> sortByName(ArrayList<Task> tasks) {
        if (tasks == null || tasks.size() <= 1) {
            return tasks;
        }

        ArrayList<Task> sortedTasks = new ArrayList<>(tasks);
        int n = sortedTasks.size();

        System.out.println("🔄 Mengurutkan " + n + " task berdasarkan nama...");

        // Bubble Sort implementation
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                // Bandingkan nama (case insensitive, ascending)
                if (sortedTasks.get(j).getTaskName().toLowerCase()
                        .compareTo(sortedTasks.get(j + 1).getTaskName().toLowerCase()) > 0) {

                    Task temp = sortedTasks.get(j);
                    sortedTasks.set(j, sortedTasks.get(j + 1));
                    sortedTasks.set(j + 1, temp);
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }

        System.out.println("✅ Pengurutan selesai!");
        return sortedTasks;
    }

    // Sorting berdasarkan status (belum selesai di atas)
    public static ArrayList<Task> sortByStatus(ArrayList<Task> tasks) {
        if (tasks == null || tasks.size() <= 1) {
            return tasks;
        }

        ArrayList<Task> sortedTasks = new ArrayList<>(tasks);
        int n = sortedTasks.size();

        System.out.println("🔄 Mengurutkan " + n + " task berdasarkan status...");

        // Bubble Sort implementation
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                // Task belum selesai (false) naik ke atas
                if (sortedTasks.get(j).isCompleted() && !sortedTasks.get(j + 1).isCompleted()) {
                    Task temp = sortedTasks.get(j);
                    sortedTasks.set(j, sortedTasks.get(j + 1));
                    sortedTasks.set(j + 1, temp);
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }

        System.out.println("✅ Pengurutan selesai!");
        return sortedTasks;
    }

    // ================ SEARCH METHODS ================

    // Linear Search berdasarkan nama task
    public static ArrayList<Task> searchByName(ArrayList<Task> tasks, String keyword) {
        ArrayList<Task> result = new ArrayList<>();

        if (tasks == null || keyword == null || keyword.trim().isEmpty()) {
            return result;
        }

        String searchKeyword = keyword.toLowerCase().trim();
        System.out.println("🔍 Mencari task dengan kata kunci: '" + keyword + "'");

        for (Task task : tasks) {
            if (task.getTaskName().toLowerCase().contains(searchKeyword) ||
                    task.getDescription().toLowerCase().contains(searchKeyword)) {
                result.add(task);
            }
        }

        System.out.println("✅ Ditemukan " + result.size() + " task yang cocok.");
        return result;
    }

    // Search berdasarkan prioritas
    public static ArrayList<Task> searchByPriority(ArrayList<Task> tasks, int priority) {
        ArrayList<Task> result = new ArrayList<>();

        if (tasks == null || priority < 1 || priority > 3) {
            return result;
        }

        String priorityName = (priority == 1) ? "HIGH" : (priority == 2) ? "MEDIUM" : "LOW";
        System.out.println("🔍 Mencari task dengan prioritas: " + priorityName);

        for (Task task : tasks) {
            if (task.getPriority() == priority) {
                result.add(task);
            }
        }

        System.out.println("✅ Ditemukan " + result.size() + " task dengan prioritas " + priorityName + ".");
        return result;
    }

    // Search berdasarkan status
    public static ArrayList<Task> searchByStatus(ArrayList<Task> tasks, boolean isCompleted) {
        ArrayList<Task> result = new ArrayList<>();

        if (tasks == null) {
            return result;
        }

        String status = isCompleted ? "SELESAI" : "BELUM SELESAI";
        System.out.println("🔍 Mencari task dengan status: " + status);

        for (Task task : tasks) {
            if (task.isCompleted() == isCompleted) {
                result.add(task);
            }
        }

        System.out.println("✅ Ditemukan " + result.size() + " task dengan status " + status + ".");
        return result;
    }

    // Search task yang terlambat
    public static ArrayList<Task> searchOverdueTasks(ArrayList<Task> tasks) {
        ArrayList<Task> result = new ArrayList<>();

        if (tasks == null) {
            return result;
        }

        System.out.println("🔍 Mencari task yang terlambat...");

        for (Task task : tasks) {
            if (task.isOverdue()) {
                result.add(task);
            }
        }

        System.out.println("⚠️ Ditemukan " + result.size() + " task yang terlambat!");
        return result;
    }

    // ================ DISPLAY METHODS ================

    // Display hasil sorting/searching dengan format yang rapi
    public static void displayTasks(ArrayList<Task> tasks, String title) {
        if (tasks == null || tasks.isEmpty()) {
            System.out.println("\n📋 " + title);
            System.out.println("================================");
            System.out.println("Tidak ada task ditemukan.");
            return;
        }

        System.out.println("\n📋 " + title.toUpperCase());
        System.out.println("================================");

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.println((i + 1) + ". " + task.toString());
        }

        System.out.println("\nTotal: " + tasks.size() + " task(s)");
    }

    // Display menu sorting
    public static void displaySortMenu() {
        System.out.println("\n🔄 MENU PENGURUTAN:");
        System.out.println("1. Urutkan berdasarkan Prioritas (High → Low)");
        System.out.println("2. Urutkan berdasarkan Deadline (Terdekat → Terjauh)");
        System.out.println("3. Urutkan berdasarkan Nama (A → Z)");
        System.out.println("4. Urutkan berdasarkan Status (Belum Selesai → Selesai)");
        System.out.println("0. Kembali");
        System.out.print("Pilih opsi: ");
    }

    // Display menu searching
    public static void displaySearchMenu() {
        System.out.println("\n🔍 MENU PENCARIAN:");
        System.out.println("1. Cari berdasarkan Nama/Deskripsi");
        System.out.println("2. Cari berdasarkan Prioritas");
        System.out.println("3. Cari berdasarkan Status");
        System.out.println("4. Cari Task yang Terlambat");
        System.out.println("0. Kembali");
        System.out.print("Pilih opsi: ");
    }
}