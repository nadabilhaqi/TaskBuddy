// User.java
public class User {
    private String username;
    private String password;
    private String email;
    private boolean isActive;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.email = "";
        this.isActive = false;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isActive = false;
    }

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public boolean isActive() { return isActive; }

    // Setters
    public void setActive(boolean active) { this.isActive = active; }
    public void setEmail(String email) { this.email = email; }

    // Validasi password
    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    @Override
    public String toString() {
        return username + (isActive ? " (Aktif)" : " (Tidak Aktif)");
    }
}