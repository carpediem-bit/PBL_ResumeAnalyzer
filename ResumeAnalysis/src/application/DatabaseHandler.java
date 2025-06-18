package application;

import java.sql.Timestamp;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static final String DB_URL = "jdbc:sqlite:resume_analysis.db";

    static {
        initializeDatabase();
    }

    private static void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS analysis_history (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "file_name TEXT NOT NULL," +
                "resume_text TEXT NOT NULL," +
                "suggested_roles TEXT NOT NULL," +
                "matched_skills TEXT," +
                "missing_skills TEXT" +
                ");";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }

    public static void saveAnalysisSession(String fileName, String resumeText,
                                           List<String> suggestedRoles,
                                           List<String> matchedSkills,
                                           List<String> missingSkills) {
        String insertSQL = "INSERT INTO analysis_history " +
                "(file_name, resume_text, suggested_roles, matched_skills, missing_skills) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, fileName);
            pstmt.setString(2, resumeText);
            pstmt.setString(3, String.join(", ", suggestedRoles));
            pstmt.setString(4, String.join(", ", matchedSkills));
            pstmt.setString(5, String.join(", ", missingSkills));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to save analysis session: " + e.getMessage());
        }
    }

    public static List<AnalysisRecord> getAnalysisHistory() {
        List<AnalysisRecord> history = new ArrayList<>();
        String querySQL = "SELECT * FROM analysis_history ORDER BY timestamp DESC";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(querySQL)) {

            while (rs.next()) {
                AnalysisRecord record = new AnalysisRecord(
                        rs.getString("file_name"),
                        rs.getString("resume_text"),
                        rs.getString("suggested_roles"),
                        rs.getString("matched_skills"),
                        rs.getString("missing_skills"),
                        rs.getTimestamp("timestamp")
                );
                history.add(record);
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve history: " + e.getMessage());
        }
        return history;
    }

    public static class AnalysisRecord {
        private final String fileName;
        private final String resumeText;
        private final String suggestedRoles;
        private final String matchedSkills;
        private final String missingSkills;
        private final Timestamp timestamp;

        public AnalysisRecord(String fileName, String resumeText, String suggestedRoles,
                              String matchedSkills, String missingSkills, Timestamp timestamp) {
            this.fileName = fileName;
            this.resumeText = resumeText;
            this.suggestedRoles = suggestedRoles;
            this.matchedSkills = matchedSkills;
            this.missingSkills = missingSkills;
            this.timestamp = timestamp;
        }

        // Getters
        public String getFileName() { return fileName; }
        public String getResumeText() { return resumeText; }
        public String getSuggestedRoles() { return suggestedRoles; }
        public String getMatchedSkills() { return matchedSkills; }
        public String getMissingSkills() { return missingSkills; }
        public Timestamp getTimestamp() { return timestamp; }
    }
}

