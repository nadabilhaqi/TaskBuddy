import java.util.*;

public class TaskTree {
    private ArrayList<Task> rootTasks;
    private String owner;

    public TaskTree(String owner) {
        this.rootTasks = new ArrayList<>();
        this.owner = owner;
    }

    // Method untuk menambah root task (kategori utama)
    public boolean addRootTask(Task task) {
        if (task != null && task.isRootTask()) {
            rootTasks.add(task);
            System.out.println("✅ Task kategori '" + task.getTaskName() + "' berhasil ditambahkan!");
            return true;
        }
        System.out.println("❌ Gagal menambahkan task!");
        return false;
    }

    // Method untuk menambah subtask ke task tertentu
    public boolean addSubtask(String parentTaskId, Task subtask) {
        Task parentTask = findTaskById(parentTaskId);
        if (parentTask != null) {
            parentTask.addSubtask(subtask);
            System.out.println("✅ Subtask '" + subtask.getTaskName() +
                    "' berhasil ditambahkan ke '" + parentTask.getTaskName() + "'!");
            return true;
        }
        System.out.println("❌ Task parent dengan ID '" + parentTaskId + "' tidak ditemukan!");
        return false;
    }

    // Method untuk mencari task berdasarkan ID (recursive)
    public Task findTaskById(String taskId) {
        for (Task rootTask : rootTasks) {
            Task found = findTaskByIdRecursive(rootTask, taskId);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    private Task findTaskByIdRecursive(Task currentTask, String taskId) {
        if (currentTask.getTaskId().equals(taskId)) {
            return currentTask;
        }

        for (Task subtask : currentTask.getSubtasks()) {
            Task found = findTaskByIdRecursive(subtask, taskId);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    // Method untuk menghapus task berdasarkan ID
    public boolean removeTask(String taskId) {
        Task taskToRemove = findTaskById(taskId);
        if (taskToRemove == null) {
            System.out.println("❌ Task dengan ID '" + taskId + "' tidak ditemukan!");
            return false;
        }

        if (taskToRemove.isRootTask()) {
            rootTasks.remove(taskToRemove);
        } else {
            Task parent = taskToRemove.getParentTask();
            parent.removeSubtask(taskId);
        }

        System.out.println("✅ Task '" + taskToRemove.getTaskName() + "' berhasil dihapus!");
        return true;
    }

    // Method untuk menandai task sebagai selesai, termasuk semua subtasks
    public boolean completeTask(String taskId) {
        Task task = findTaskById(taskId);
        if (task != null) {
            completeTaskRecursive(task);
            System.out.println("✅ Task '" + task.getTaskName() + "' dan semua subtasks ditandai sebagai selesai!");
            return true;
        }
        System.out.println("❌ Task dengan ID '" + taskId + "' tidak ditemukan!");
        return false;
    }

    // Method rekursif untuk menandai task dan semua subtasks sebagai selesai
    private void completeTaskRecursive(Task task) {
        task.setCompleted(true);
        for (Task subtask : task.getSubtasks()) {
            completeTaskRecursive(subtask);
        }
    }

    // Method untuk mendapatkan semua task dalam bentuk list (flat)
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (Task rootTask : rootTasks) {
            addTaskToListRecursive(rootTask, allTasks);
        }
        return allTasks;
    }

    private void addTaskToListRecursive(Task task, ArrayList<Task> taskList) {
        taskList.add(task);
        for (Task subtask : task.getSubtasks()) {
            addTaskToListRecursive(subtask, taskList);
        }
    }

    // Method untuk mendapatkan task berdasarkan prioritas
    public ArrayList<Task> getTasksByPriority(int priority) {
        ArrayList<Task> result = new ArrayList<>();
        ArrayList<Task> allTasks = getAllTasks();

        for (Task task : allTasks) {
            if (task.getPriority() == priority) {
                result.add(task);
            }
        }
        return result;
    }

    // Method untuk mendapatkan task yang belum selesai
    public ArrayList<Task> getIncompleteTasks() {
        ArrayList<Task> result = new ArrayList<>();
        ArrayList<Task> allTasks = getAllTasks();

        for (Task task : allTasks) {
            if (!task.isCompleted()) {
                result.add(task);
            }
        }
        return result;
    }

    // Method untuk mendapatkan task yang terlambat
    public ArrayList<Task> getOverdueTasks() {
        ArrayList<Task> result = new ArrayList<>();
        ArrayList<Task> allTasks = getAllTasks();

        for (Task task : allTasks) {
            if (task.isOverdue()) {
                result.add(task);
            }
        }
        return result;
    }

    // Method untuk display tree structure
    public void displayTree() {
        if (rootTasks.isEmpty()) {
            System.out.println("📋 Tidak ada task untuk user: " + owner);
            return;
        }

        System.out.println("\n🌳 TASK TREE - " + owner.toUpperCase());
        System.out.println("================================");

        for (Task rootTask : rootTasks) {
            displayTaskRecursive(rootTask, 0);
        }

        System.out.println("\nTotal tasks: " + getAllTasks().size());
    }

    private void displayTaskRecursive(Task task, int level) {
        // Buat indentasi sesuai level
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < level; i++) {
            indent.append("  ");
        }
        if (level > 0) {
            indent.append("└─ ");
        }

        // Display task info
        System.out.println(indent + task.toString());

        // Display subtasks
        for (Task subtask : task.getSubtasks()) {
            displayTaskRecursive(subtask, level + 1);
        }
    }

    // Method untuk display summary
    public void displaySummary() {
        ArrayList<Task> allTasks = getAllTasks();
        if (allTasks.isEmpty()) {
            System.out.println("📊 Tidak ada task untuk dianalisis.");
            return;
        }

        int totalTasks = allTasks.size();
        int completedTasks = 0;
        int highPriority = 0;
        int mediumPriority = 0;
        int lowPriority = 0;
        int overdueTasks = 0;

        for (Task task : allTasks) {
            if (task.isCompleted()) completedTasks++;
            if (task.isOverdue()) overdueTasks++;

            switch (task.getPriority()) {
                case 1: highPriority++; break;
                case 2: mediumPriority++; break;
                case 3: lowPriority++; break;
            }
        }

        System.out.println("\n📊 RINGKASAN TASK - " + owner.toUpperCase());
        System.out.println("================================");
        System.out.println("Total Task: " + totalTasks);
        System.out.println("Selesai: " + completedTasks + " (" +
                (totalTasks > 0 ? (completedTasks * 100 / totalTasks) : 0) + "%)");
        System.out.println("Belum Selesai: " + (totalTasks - completedTasks));
        System.out.println("Terlambat: " + overdueTasks + " ⚠️");
        System.out.println("\nBerdasarkan Prioritas:");
        System.out.println("🔴 High: " + highPriority);
        System.out.println("🟡 Medium: " + mediumPriority);
        System.out.println("🟢 Low: " + lowPriority);
    }

    // Method untuk mencari task berdasarkan nama (case insensitive)
    public ArrayList<Task> searchTaskByName(String keyword) {
        ArrayList<Task> result = new ArrayList<>();
        ArrayList<Task> allTasks = getAllTasks();

        for (Task task : allTasks) {
            if (task.getTaskName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(task);
            }
        }
        return result;
    }

    // Getters
    public ArrayList<Task> getRootTasks() { return rootTasks; }
    public String getOwner() { return owner; }
    public boolean isEmpty() { return rootTasks.isEmpty(); }
    public int getTotalTasks() { return getAllTasks().size(); }
}