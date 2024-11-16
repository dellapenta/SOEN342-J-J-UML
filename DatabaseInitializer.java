import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String URL = "jdbc:sqlite:my.db";

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            if (conn != null) {
                System.out.println("Database initialized with driver: " + conn.getMetaData().getDriverName());

                // Create tables
                dropTableSql(stmt);
                stmt.executeUpdate(createAdminTableSql());
                stmt.executeUpdate(createClientTableSql());
                stmt.executeUpdate(createInstructorTableSql());
                stmt.executeUpdate(createOfferingTableSql());
                stmt.executeUpdate(createLessonTableSql());
                stmt.executeUpdate(createBookingTableSql());
                stmt.executeUpdate(createScheduleTableSql());
                stmt.executeUpdate(createLocationTableSql());

                // Insert data
                stmt.executeUpdate(insertAdminData());
                insertClientData(stmt);
                insertInstructorData(stmt);
                insertScheduleData(stmt);
                insertLocationData(stmt);
                insertLessonData(stmt);
                insertOfferingData(stmt);

                System.out.println("Database setup completed.");
            }
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    private static void dropTableSql(Statement stmt) throws SQLException {
        String[] dropTable = {
                "DROP TABLE IF EXISTS Administrators",
                "DROP TABLE IF EXISTS Bookings",
                "DROP TABLE IF EXISTS Offerings",
                "DROP TABLE IF EXISTS Lessons",
                "DROP TABLE IF EXISTS Clients",
                "DROP TABLE IF EXISTS Instructors",
                "DROP TABLE IF EXISTS Locations",
                "DROP TABLE IF EXISTS Schedules"
        };
        for (String sql : dropTable) {
            stmt.executeUpdate(sql);
        }

    }

    // SQL creation and insertion logic encapsulated in methods
    private static String createAdminTableSql() {
        return "CREATE TABLE IF NOT EXISTS Administrators ("
                + " id INTEGER PRIMARY KEY,"
                + " name text NOT NULL,"
                + " email text NOT NULL,"
                + " password text NOT NULL"
                + ");";
    }

    private static String insertAdminData() {
        return "INSERT INTO Administrators (name, email, password) VALUES ('Admin', 'admin@example.com', 'password123');";
    }

    private static String createClientTableSql() {
        return "CREATE TABLE IF NOT EXISTS Clients ("
                + " id INTEGER PRIMARY KEY,"
                + " name text NOT NULL,"
                + " phone_number text NOT NULL,"
                + " email text NOT NULL,"
                + " age INTEGER NOT NULL,"
                + " guardian_id INTEGER,"
                + " FOREIGN KEY(guardian_id) REFERENCES Clients(id)"
                + ");";
    }


    private static void insertClientData(Statement stmt) throws SQLException {
        String[] clientData = {
                "INSERT INTO Clients (name, phone_number, email, age) VALUES ('John Doe', '123-456-7890', 'Y9a0x@example.com', 25)",
                "INSERT INTO Clients (name, phone_number, email, age) VALUES ('Jane Doe', '987-654-3210', '4o5G1@example.com', 30)",
                "INSERT INTO Clients (name, phone_number, email, age) VALUES ('Bob Smith', '555-123-4567', 'q2o0W@example.com', 35)",
                "INSERT INTO Clients (name, phone_number, email, age) VALUES ('Alice Johnson', '111-222-3333', '8GnJ1@example.com', 40)",
                "INSERT INTO Clients (name, phone_number, email, age) VALUES ('Charlie Brown', '444-555-6666', '2m8iK@example.com', 45)"
        };
        for (String sql : clientData) {
            stmt.executeUpdate(sql);
        }
    }

    private static String createInstructorTableSql() {
        return "CREATE TABLE IF NOT EXISTS Instructors ("
                + " id INTEGER PRIMARY KEY,"
                + " name text NOT NULL,"
                + " phone_number text NOT NULL,"
                + " specialization text NOT NULL,"
                + " available_cities text NOT NULL"
                + ");";
    }

    private static void insertInstructorData(Statement stmt) throws SQLException {
        String[] instructorData = {
                "INSERT INTO Instructors (name, phone_number, specialization, available_cities) VALUES ('James', '123-456-7890', 'Yoga', 'Montreal, Ottawa')",
                "INSERT INTO Instructors (name, phone_number, specialization, available_cities) VALUES ('John', '987-654-3210', 'Pilates', 'Montreal, Quebec City')",
                "INSERT INTO Instructors (name, phone_number, specialization, available_cities) VALUES ('Jane', '555-123-4567', 'Pilates', 'Laval, Montreal')",
                "INSERT INTO Instructors (name, phone_number, specialization, available_cities) VALUES ('Bob', '111-222-3333', 'Fitness', 'Quebec City, Ottawa')",
                "INSERT INTO Instructors (name, phone_number, specialization, available_cities) VALUES ('Alice', '444-555-6666', 'Boxing', 'Toronto, Quebec City')"
        };
        for (String sql : instructorData) {
            stmt.executeUpdate(sql);
        }
    }

    private static String createLocationTableSql() {
        return "CREATE TABLE IF NOT EXISTS Locations ("
                + " id INTEGER PRIMARY KEY,"
                + " name text NOT NULL,"
                + " city text NOT NULL"
                + ");";
    }

    private static void insertLocationData(Statement stmt) throws SQLException {
        String[] locationData = {
                "INSERT INTO Locations (name, city) VALUES ('EV Building Room 7', 'Montreal')",
                "INSERT INTO Locations (name, city) VALUES ('Downtown Fitness Center', 'Montreal')",
                "INSERT INTO Locations (name, city) VALUES ('Community Gym', 'Laval')",
                "INSERT INTO Locations (name, city) VALUES ('Sports Complex', 'Quebec City')",
                "INSERT INTO Locations (name, city) VALUES ('Health Hub', 'Toronto')",
                "INSERT INTO Locations (name, city) VALUES ('Boxing Studio', 'Ottawa')",
                "INSERT INTO Locations (name, city) VALUES ('Yoga Studio', 'Vancouver')"
        };
        for (String sql : locationData) {
            stmt.executeUpdate(sql);
        }
    }

    private static String createScheduleTableSql() {
        return "CREATE TABLE IF NOT EXISTS Schedules ("
        + "	id INTEGER PRIMARY KEY,"
        + "	start_date Date NOT NULL,"
        + "	end_date Date NOT NULL,"
        + "	timeSlot text NOT NULL,"
        + "	day_of_week text NOT NULL"
        + ");";
    }

    private static void insertScheduleData(Statement stmt) throws SQLException {
        String[] scheduleData = {
                "INSERT INTO Schedules (start_date, end_date, timeSlot, day_of_week) VALUES ('2024-09-01', '2024-11-30', '12:00 - 1:00', 'Sunday')",
                "INSERT INTO Schedules (start_date, end_date, timeSlot, day_of_week) VALUES ('2024-10-01', '2024-12-15', '10:00 - 12:00', 'Saturday')",
                "INSERT INTO Schedules (start_date, end_date, timeSlot, day_of_week) VALUES ('2024-11-05', '2025-01-20', '18:00 - 20:00', 'Wednesday')",
                "INSERT INTO Schedules (start_date, end_date, timeSlot, day_of_week) VALUES ('2024-08-20', '2024-10-20', '09:00 - 11:00', 'Monday')",
                "INSERT INTO Schedules (start_date, end_date, timeSlot, day_of_week) VALUES ('2024-07-15', '2024-09-15', '14:00 - 16:00', 'Thursday')"
        };
        for (String sql : scheduleData) {
            stmt.executeUpdate(sql);
        }
    }

    private static String createLessonTableSql() {
        return "CREATE TABLE IF NOT EXISTS Lessons ("
                + " id INTEGER PRIMARY KEY,"
                + " name text NOT NULL,"
                + " type text NOT NULL,"
                + " capacity INTEGER NOT NULL,"
                + " location_id INTEGER NOT NULL,"
                + " schedule_id INTEGER NOT NULL,"
                + " available_instructors BOOLEAN DEFAULT true,"
                + " FOREIGN KEY(location_id) REFERENCES Locations(id),"
                + " FOREIGN KEY(schedule_id) REFERENCES Schedules(id)"
                + ");";
    }

    private static void insertLessonData(Statement stmt) throws SQLException {
        String[] lessonData = {
                "INSERT INTO Lessons (name, type, capacity, location_id, schedule_id, available_instructors) VALUES ('Joudo Class', 'Group', 5, 1, 1, false)",
                "INSERT INTO Lessons (name, type, capacity, location_id, schedule_id, available_instructors) VALUES ('Yoga Session', 'Private', 1, 7, 2, false)",
                "INSERT INTO Lessons (name, type, capacity, location_id, schedule_id, available_instructors) VALUES ('Boxing Training', 'Group', 3, 6, 3, false)",
                "INSERT INTO Lessons (name, type, capacity, location_id, schedule_id, available_instructors) VALUES ('Pilates Class', 'Group', 2, 1, 4, false)",
                "INSERT INTO Lessons (name, type, capacity, location_id, schedule_id, available_instructors) VALUES ('CrossFit', 'Private', 1, 3, 5, false)",
                "INSERT INTO Lessons (name, type, capacity, location_id, schedule_id) VALUES ('Swimming Lessons', 'Group', 3, 4, 1)",
                "INSERT INTO Lessons (name, type, capacity, location_id, schedule_id) VALUES ('Karate for Beginners', 'Group', 2, 5, 2)",
                "INSERT INTO Lessons (name, type, capacity, location_id, schedule_id) VALUES ('Advanced Pilates', 'Private', 1, 2, 3)",
                "INSERT INTO Lessons (name, type, capacity, location_id, schedule_id) VALUES ('Zumba Class', 'Group', 3, 1, 4)",
                "INSERT INTO Lessons (name, type, capacity, location_id, schedule_id) VALUES ('Kickboxing Basics', 'Private', 1, 6, 5)",
                "INSERT INTO Lessons (name, type, capacity, location_id, schedule_id) VALUES ('Personal Training', 'Private', 1, 3, 1)",
                "INSERT INTO Lessons (name, type, capacity, location_id, schedule_id) VALUES ('Yoga Retreat', 'Group', 3, 7, 2)",
                "INSERT INTO Lessons (name, type, capacity, location_id, schedule_id) VALUES ('Judo Fundamentals', 'Group', 2, 1, 3)",
                "INSERT INTO Lessons (name, type, capacity, location_id, schedule_id) VALUES ('Pilates for Beginners', 'Private', 1, 4, 4)"
        };
        for (String sql : lessonData) {
            stmt.executeUpdate(sql);
        }
    }

    private static String createOfferingTableSql() {
        return "CREATE TABLE IF NOT EXISTS Offerings ("
                + " id INTEGER PRIMARY KEY,"
                + " lesson_id INTEGER NOT NULL,"
                + " instructor_id INTEGER NOT NULL,"
                + " FOREIGN KEY(lesson_id) REFERENCES Lessons(id),"
                + " FOREIGN KEY(instructor_id) REFERENCES Instructors(id)"
                + ");";
    }

    private static void insertOfferingData(Statement stmt) throws SQLException {
        String[] offeringData = {
                "INSERT INTO Offerings (lesson_id, instructor_id) VALUES (1, 1)",
                "INSERT INTO Offerings (lesson_id, instructor_id) VALUES (2, 2)",
                "INSERT INTO Offerings (lesson_id, instructor_id) VALUES (3, 3)",
                "INSERT INTO Offerings (lesson_id, instructor_id) VALUES (4, 4)",
                "INSERT INTO Offerings (lesson_id, instructor_id) VALUES (5, 5)",
        };
        for (String sql : offeringData) {
            stmt.executeUpdate(sql);
        }
    }

    private static String createBookingTableSql() {
        return "CREATE TABLE IF NOT EXISTS Bookings ("
                + " id INTEGER PRIMARY KEY,"
                + " client_id INTEGER NOT NULL,"
                + " offering_id INTEGER NOT NULL,"
                + " FOREIGN KEY(client_id) REFERENCES Clients(id),"
                + " FOREIGN KEY(offering_id) REFERENCES Offerings(id)"
                + ");";
    }

}





