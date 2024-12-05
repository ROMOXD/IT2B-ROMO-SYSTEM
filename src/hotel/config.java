package hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class config {
    
//Connection Method to SQLITE
public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC"); // Load the SQLite JDBC driver
            con = DriverManager.getConnection("jdbc:sqlite:HotelDB.db"); // Establish connection
            System.out.println("Connection Successful");
        } catch (Exception e) {
            System.out.println("Connection Failed: " + e);
        }
        return con;
    }
    
public void addRecord(String sql, Object... values) {
    try (Connection conn = this.connectDB(); // Use the connectDB method
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        // Loop through the values and set them in the prepared statement dynamically
        for (int i = 0; i < values.length; i++) {
            if (values[i] instanceof Integer) {
                pstmt.setInt(i + 1, (Integer) values[i]); // If the value is Integer
            } else if (values[i] instanceof Double) {
                pstmt.setDouble(i + 1, (Double) values[i]); // If the value is Double
            } else if (values[i] instanceof Float) {
                pstmt.setFloat(i + 1, (Float) values[i]); // If the value is Float
            } else if (values[i] instanceof Long) {
                pstmt.setLong(i + 1, (Long) values[i]); // If the value is Long
            } else if (values[i] instanceof Boolean) {
                pstmt.setBoolean(i + 1, (Boolean) values[i]); // If the value is Boolean
            } else if (values[i] instanceof java.util.Date) {
                pstmt.setDate(i + 1, new java.sql.Date(((java.util.Date) values[i]).getTime())); // If the value is Date
            } else if (values[i] instanceof java.sql.Date) {
                pstmt.setDate(i + 1, (java.sql.Date) values[i]); // If it's already a SQL Date
            } else if (values[i] instanceof java.sql.Timestamp) {
                pstmt.setTimestamp(i + 1, (java.sql.Timestamp) values[i]); // If the value is Timestamp
            } else {
                pstmt.setString(i + 1, values[i].toString()); // Default to String for other types
            }
        }

        pstmt.executeUpdate();
        System.out.println("Record added successfully!");
    } catch (SQLException e) {
        System.out.println("Error adding record: " + e.getMessage());
    }
} 


    // Dynamic view method to display records from any table
    public void viewRecords(String sqlQuery, String[] columnHeaders, String[] columnNames) {
        // Check that columnHeaders and columnNames arrays are the same length
        if (columnHeaders.length != columnNames.length) {
            System.out.println("Error: Mismatch between column headers and column names.");
            return;
        }

        try (Connection conn = this.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
             ResultSet rs = pstmt.executeQuery()) {

            // Print the headers dynamically                                                                  
            StringBuilder headerLine = new StringBuilder();
            headerLine.append("==========================================================================================================================================================================================================================\n|| ");
            for (String header : columnHeaders) {
                headerLine.append(String.format("%-20s || ", header)); // Adjust formatting as needed
            }
            headerLine.append("\n==========================================================================================================================================================================================================================");

            System.out.println(headerLine.toString());

            // Print the rows dynamically based on the provided column names
            while (rs.next()) {
                StringBuilder row = new StringBuilder("|| ");
                for (String colName : columnNames) {
                    String value = rs.getString(colName);
                    row.append(String.format("%-20s || ", value != null ? value : "")); // Adjust formatting
                }
                System.out.println(row.toString());
            }
            System.out.println("==========================================================================================================================================================================================================================");

        } catch (SQLException e) {
            System.out.println("Error retrieving records: " + e.getMessage());
        }
    }
    
    public void viewIndivRecords(String sqlQuery, String[] columnHeaders, String[] columnNames, Object... params) {
    // Check that columnHeaders and columnNames arrays are the same length
    if (columnHeaders.length != columnNames.length) {
        System.out.println("Error: Mismatch between column headers and column names.");
        return;
    }

    try (Connection conn = this.connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sqlQuery)) {

        // Bind parameters dynamically
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }

        try (ResultSet rs = pstmt.executeQuery()) {
            // Print the headers dynamically
            StringBuilder headerLine = new StringBuilder();
            headerLine.append("==========================================================================================================================================================================================================================\n|| ");
            for (String header : columnHeaders) {
                headerLine.append(String.format("%-20s || ", header)); // Adjust formatting as needed
            }
            headerLine.append("\n==========================================================================================================================================================================================================================");

            System.out.println(headerLine.toString());

            // Print the rows dynamically based on the provided column names
            boolean hasRows = false;
            while (rs.next()) {
                hasRows = true;
                StringBuilder row = new StringBuilder("|| ");
                for (String colName : columnNames) {
                    String value = rs.getString(colName);
                    row.append(String.format("%-20s || ", value != null ? value : "")); // Adjust formatting
                }
                System.out.println(row.toString());
            }

            if (!hasRows) {
                System.out.println("No records found for the given criteria.");
            }

            System.out.println("==========================================================================================================================================================================================================================");
        }

    } catch (SQLException e) {
        System.out.println("Error retrieving records: " + e.getMessage());
    }
}
    
    public void updateRecord(String sql, Object... values) {
        try (Connection conn = this.connectDB(); // Use the connectDB method
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Loop through the values and set them in the prepared statement dynamically
            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) values[i]); // If the value is Integer
                } else if (values[i] instanceof Double) {
                    pstmt.setDouble(i + 1, (Double) values[i]); // If the value is Double
                } else if (values[i] instanceof Float) {
                    pstmt.setFloat(i + 1, (Float) values[i]); // If the value is Float
                } else if (values[i] instanceof Long) {
                    pstmt.setLong(i + 1, (Long) values[i]); // If the value is Long
                } else if (values[i] instanceof Boolean) {
                    pstmt.setBoolean(i + 1, (Boolean) values[i]); // If the value is Boolean
                } else if (values[i] instanceof java.util.Date) {
                    pstmt.setDate(i + 1, new java.sql.Date(((java.util.Date) values[i]).getTime())); // If the value is Date
                } else if (values[i] instanceof java.sql.Date) {
                    pstmt.setDate(i + 1, (java.sql.Date) values[i]); // If it's already a SQL Date
                } else if (values[i] instanceof java.sql.Timestamp) {
                    pstmt.setTimestamp(i + 1, (java.sql.Timestamp) values[i]); // If the value is Timestamp
                } else {
                    pstmt.setString(i + 1, values[i].toString()); // Default to String for other types
                }
            }

            pstmt.executeUpdate();
            System.out.println("Record updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating record: " + e.getMessage());
        }
    }
    
    // Add this method in the config class
    public void deleteRecord(String sql, Object... values) {
    try (Connection conn = this.connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        // Loop through the values and set them in the prepared statement dynamically
        for (int i = 0; i < values.length; i++) {
            if (values[i] instanceof Integer) {
                pstmt.setInt(i + 1, (Integer) values[i]); // If the value is Integer
            } else {
                pstmt.setString(i + 1, values[i].toString()); // Default to String for other types
            }
        }

        pstmt.executeUpdate();
        System.out.println("Record deleted successfully!");
    } catch (SQLException e) {
        System.out.println("Error deleting record: " + e.getMessage());
        }
    }
    
    private void setPreparedStatementValues(PreparedStatement pstmt, Object... values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            if (values[i] instanceof Integer) {
                pstmt.setInt(i + 1, (Integer) values[i]);
            } else if (values[i] instanceof Double) {
                pstmt.setDouble(i + 1, (Double) values[i]);
            } else if (values[i] instanceof Float) {
                pstmt.setFloat(i + 1, (Float) values[i]);
            } else if (values[i] instanceof Long) {
                pstmt.setLong(i + 1, (Long) values[i]);
            } else if (values[i] instanceof Boolean) {
                pstmt.setBoolean(i + 1, (Boolean) values[i]);
            } else if (values[i] instanceof java.util.Date) {
                pstmt.setDate(i + 1, new java.sql.Date(((java.util.Date) values[i]).getTime()));
            } else if (values[i] instanceof java.sql.Date) {
                pstmt.setDate(i + 1, (java.sql.Date) values[i]);
            } else if (values[i] instanceof java.sql.Timestamp) {
                pstmt.setTimestamp(i + 1, (java.sql.Timestamp) values[i]);
            } else {
                pstmt.setString(i + 1, values[i].toString());
            }
        }
    }
    
     public double getSingleValue(String sql, Object... params) {
        double result = 0.0;
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            setPreparedStatementValues(pstmt, params);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getDouble(1);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving single value: " + e.getMessage());
        }
        return result;
    }
     
     public String getSingleStringValue(String sql, Object... params) {
    String result = null;
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        setPreparedStatementValues(pstmt, params);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            result = rs.getString(1);
        }

    } catch (SQLException e) {
        System.out.println("Error retrieving String value: " + e.getMessage());
    }
    return result;
}
     
     public int getSingleIntValue(String sql, Object... params) {
    int result = 0;
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        setPreparedStatementValues(pstmt, params); // Set query parameters
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            result = rs.getInt(1); // Fetch the first column as an integer
        }

    } catch (SQLException e) {
        System.out.println("Error retrieving integer value: " + e.getMessage());
    }
    return result;
}
     
     public String getLatestBookingDate(String sql, Object... params) {
    String latestDate = null;
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        setPreparedStatementValues(pstmt, params);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            latestDate = rs.getString("latest_cout");
        }

    } catch (SQLException e) {
        System.out.println("Error retrieving the latest booking date: " + e.getMessage());
    }
    return latestDate;
}
     
     public Object[] getMultipleValues(String query, Object... params) {
    try (Connection conn = connectDB();  // Replace with your connection method
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        // Bind the parameters to the query
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }

        // Execute the query
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                int columnCount = rs.getMetaData().getColumnCount();
                Object[] results = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    results[i - 1] = rs.getObject(i);  // Fetch each column value
                }
                return results;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;  // Return null if no result is found or an error occurs
}
     
     public double getSglValue(String sql, Object... params) {
    double result = 0.0;
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        // Set prepared statement values, converting Date to String if needed
        setPreparedStatementValues(pstmt, params);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            result = rs.getDouble(1);
        }

    } catch (SQLException e) {
        System.out.println("Error retrieving single value: " + e.getMessage());
    }
    return result;
}
     
     public void setPreparedStatementValue(PreparedStatement pstmt, Object... params) throws SQLException {
    for (int i = 0; i < params.length; i++) {
        if (params[i] instanceof Date) {
            // Convert Date to String (assuming your SQL query expects the date in this format)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Adjust format as needed
            pstmt.setString(i + 1, sdf.format((Date) params[i]));  // Convert Date to String and set
        } else {
            pstmt.setObject(i + 1, params[i]);  // For non-Date types (e.g., Integer, String)
        }
    }
}
    
}
