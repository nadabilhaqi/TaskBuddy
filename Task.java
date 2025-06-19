// Task.java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Task {
    private String taskId;
    private String taskName;
    private String description;
    private int priority; // 1 = High, 2 = Medium, 3 = Low
    private LocalDate deadline;
    private boolean isCompleted;
    private String createdBy;
    private ArrayList<Task> subtasks;
    private Task parentTask;

    // Constructor utama
    public Task(String taskName, String description, int priority, LocalDate deadline, String createdBy) {
        this.taskId = generateTaskId();
        this.taskName = taskName;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.createdBy = createdBy;
        this.isCompleted = false;
        this.subtasks = new ArrayList<>();
        this.parentTask = null;
    }

    // Constructor sederhana
    public Task(String taskName, int priority, String createdBy) {
        this(taskName, "", priority, (LocalDate) null, createdBy);
    }

    // Constructor dengan deadline string
    public Task(String taskName, String description, int priority, String deadlineStr, String createdBy) {
        this.taskId = generateTaskId();
        this.taskName = taskName;
        this.description = description;
        this.priority = priority;
        this.createdBy = createdBy;
        this.isCompleted = false;
        this.subtasks = new ArrayList<>();
        this.parentTask = null;

        // Parse deadline string (format: dd-MM-yyyy)
        try {
            if (deadlineStr != null && !deadlineStr.trim().isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                this.deadline = LocalDate.parse(deadlineStr, formatter);
            } else {
                this.deadline = null;
            }
        } catch (Exception e) {
            this.deadline = null;
            System.out.println("⚠️ Format tanggal salah, gunakan format: dd-MM-yyyy");
        }
    }

    private String generateTaskId() {
        return "TSK" + System.currentTimeMillis() % 10000;
    }

    // Getters
    public String getTaskId() { return taskId; }
    public String getTaskName() { return taskName; }
    public String getDescription() { return description; }
    public int getPriority() { return priority; }
    public LocalDate getDeadline() { return deadline; }
    public boolean isCompleted() { return isCompleted; }
    public String getCreatedBy() { return createdBy; }
    public ArrayList<Task> getSubtasks() { return subtasks; }
    public Task getParentTask() { return parentTask; }

    // Setters
    public void setTaskName(String taskName) { this.taskName = taskName; }
    public void setDescription(String description) { this.description = description; }
    public void setPriority(int priority) { this.priority = priority; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public void setCompleted(boolean completed) { this.isCompleted = completed; }
    public void setParentTask(Task parentTask) { this.parentTask = parentTask; }

    // Method untuk menambah subtask
    public void addSubtask(Task subtask) {
        subtask.setParentTask(this);
        this.subtasks.add(subtask);
    }

    // Method untuk menghapus subtask
    public boolean removeSubtask(String taskId) {
        return subtasks.removeIf(task -> task.getTaskId().equals(taskId));
    }

    // Method untuk cek apakah task ini adalah root (tidak punya parent)
    public boolean isRootTask() {
        return parentTask == null;
    }

    // Method untuk mendapatkan level/depth task dalam tree
    public int getTaskLevel() {
        int level = 0;
        Task current = this.parentTask;
        while (current != null) {
            level++;
            current = current.getParentTask();
        }
        return level;
    }

    // Method untuk mendapatkan string prioritas
    public String getPriorityString() {
        switch (priority) {
            case 1: return "🔴 HIGH";
            case 2: return "🟡 MEDIUM";
            case 3: return "🟢 LOW";
            default: return "⚪ UNKNOWN";
        }
    }

    // Method untuk mendapatkan status string
    public String getStatusString() {
        return isCompleted ? "✅ SELESAI" : "⏳ BELUM SELESAI";
    }

    // Method untuk mendapatkan deadline string
    public String getDeadlineString() {
        if (deadline == null) {
            return "Tidak ada deadline";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return deadline.format(formatter);
    }

    // Method untuk cek apakah deadline sudah lewat
    public boolean isOverdue() {
        if (deadline == null) return false;
        return LocalDate.now().isAfter(deadline) && !isCompleted;
    }

    // Method untuk mendapatkan jumlah hari tersisa
    public long getDaysUntilDeadline() {
        if (deadline == null) return Long.MAX_VALUE;
        return LocalDate.now().until(deadline).getDays();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(taskId).append("] ");
        sb.append(taskName);
        sb.append(" | ").append(getPriorityString());
        sb.append(" | ").append(getStatusString());
        if (deadline != null) {
            sb.append(" | 📅 ").append(getDeadlineString());
            if (isOverdue()) {
                sb.append(" ⚠️ TERLAMBAT");
            }
        }
        sb.append(" | 👤 ").append(createdBy);
        if (!subtasks.isEmpty()) {
            sb.append(" | 📂 ").append(subtasks.size()).append(" subtask(s)");
        }
        return sb.toString();
    }

    // Method untuk display task dengan indentasi sesuai level
    public String toStringWithIndent() {
        StringBuilder indent = new StringBuilder();
        int level = getTaskLevel();
        for (int i = 0; i < level; i++) {
            indent.append("  ");
        }
        if (level > 0) {
            indent.append("└─ ");
        }
        return indent + toString();
    }
}