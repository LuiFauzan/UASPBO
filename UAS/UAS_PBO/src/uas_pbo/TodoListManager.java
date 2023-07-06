package uas_pbo;
import java.sql.*;

public class TodoListManager {
    private Connection conn;

    public TodoListManager() {
 
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/todo_list", "username", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        try {
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS todo_list (" +
                    "ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "Todo VARCHAR(25)," +
                    "Kategori VARCHAR(25)," +
                    "Tanggal DATE," +
                    "Status VARCHAR(25))";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTask(String todo, String kategori, String tanggal, String status) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
              "INSERT INTO tasks (Todo, Kategori, Tanggal, Status) VALUES (?, ?, ?, ?)");
            stmt.setString(1, todo);
            stmt.setString(2, kategori);
            stmt.setString(3, tanggal);
            stmt.setString(4, status);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTask(int taskId, String todo, String kategori, String tanggal_selesai, String status) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE tasks SET Todo = ?, Kategori = ?, Tanggal = ?, Status = ? WHERE ID = ?");
            stmt.setString(1, todo);
            stmt.setString(2, kategori);
            stmt.setString(3, tanggal_selesai);
            stmt.setString(4, status);
            stmt.setInt(5, taskId);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllTasks() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tasks");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String todo = rs.getString("Todo");
                String kategori = rs.getString("Kategori");
                String tanggal_selesai = rs.getString("Tanggal");
                String status = rs.getString("Status");
                System.out.println("ID: " + id);
                System.out.println("To-Do: " + todo);
                System.out.println("Kategori: " + kategori);
                System.out.println("Tanggal Selesai: " + tanggal_selesai);
                System.out.println("Status: " + status);
                System.out.println("------------------------");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public static void main(String[] args) {
        TodoListManager todoList = new TodoListManager();
        todoList.addTask("Avanza", "Bengkel", "2023-07-10", "Belum Selesai");
        todoList.addTask("Xenia", "Bengkel", "2023-07-08", "Selesai");
        todoList.getAllTasks();
        todoList.updateTask(1, "BMW", "Pekerjaan", "2023-07-15", "Belum Selesai");
        todoList.getAllTasks();
    }
}
