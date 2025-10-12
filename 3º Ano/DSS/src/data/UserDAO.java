package data;

import business.Facades.ServicesFacade;
import business.Station.Administrator;
import business.Station.Customer;
import business.Station.Service;
import business.Station.User;
import business.vehicle.*;

import java.sql.*;
import java.util.*;

public class UserDAO implements Map<Integer, User> {
    private static UserDAO singleton = null;

    private UserDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "Id INT NOT NULL PRIMARY KEY," +
                    "Name VARCHAR(75)," +
                    "Email VARCHAR(75)," +
                    "VATNumber VARCHAR(255)," +
                    "Address VARCHAR(255)," +
                    "Telephone VARCHAR(255))";
            stm.executeUpdate(sql);

            String createVehiclesTableSQL = "CREATE TABLE IF NOT EXISTS vehicles (" +
                    "VehicleId INT NOT NULL PRIMARY KEY," +
                    "UserId INT NOT NULL," +
                    "VehicleFile VARCHAR(255)," +
                    "Services VARCHAR(255)," +
                    "TypeOfVehicle INT," +
                    "FOREIGN KEY (UserId) REFERENCES users(Id))";
            stm.executeUpdate(createVehiclesTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static UserDAO getInstance() {
        if (UserDAO.singleton == null) {
            UserDAO.singleton = new UserDAO();
        }
        return UserDAO.singleton;
    }

    @Override
    public int size() {
        int count = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT count(*) FROM users")) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        boolean exists;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT Id FROM users WHERE Id=" + key)) {
            exists = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return exists;
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public User get(Object key) {
        User u = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM users WHERE Id='" + key + "'")) {
            if (rs.next()) {
                u = new Customer(
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("VATNumber"),
                        rs.getString("Address"),
                        rs.getString("Telephone"),
                        extractVehiclesFromResultSet(key));
            } else {
                u = new Administrator(rs.getString("Name"), rs.getString("Email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return u;
    }

    public User putnew(Integer key, User user) {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)) {
            String insertOrUpdateUserQuery = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE name = VALUES(name), email = VALUES(email), VATNumber = VALUES(VATNumber), Address = VALUES(Address), Telephone = VALUES(Telephone)";
            try (PreparedStatement userStm = conn.prepareStatement(insertOrUpdateUserQuery)) {
                userStm.setInt(1, key);
                userStm.setString(2, user.getName());
                userStm.setString(3, user.getEmail());
                userStm.setString(4, user.getVATNumber());
                userStm.setString(5, user.getAddress());
                userStm.setString(6, user.getTelephone());
                userStm.executeUpdate();
            }

            String insertOrUpdateVehicleQuery = "INSERT INTO vehicles VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE VehicleFile = VALUES(VehicleFile), Services = VALUES(Services), TypeOfVehicle = VALUES(TypeOfVehicle)";
            try (PreparedStatement vehicleStm = conn.prepareStatement(insertOrUpdateVehicleQuery)) {
                int totalVehicles = this.size();
                for (Vehicle v : user.getVehicles()) {
                    vehicleStm.setInt(1, totalVehicles + i);
                    vehicleStm.setInt(2, key);
                    vehicleStm.setString(3, v.getVehicleFile());
                    vehicleStm.setString(4, v.getServicesNeeded().toString());
                    vehicleStm.setInt(5, v.getVehicleType());
                    vehicleStm.executeUpdate();
                    i++;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        return user;
    }

    @Override
    public User put(Integer key, User user) {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)) {
            String insertOrUpdateUserQuery = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE name = VALUES(name), email = VALUES(email), VATNumber = VALUES(VATNumber), Address = VALUES(Address), Telephone = VALUES(Telephone)";
            try (PreparedStatement userStm = conn.prepareStatement(insertOrUpdateUserQuery)) {
                userStm.setInt(1, key);
                userStm.setString(2, user.getName());
                userStm.setString(3, user.getEmail());
                userStm.setString(4, user.getVATNumber());
                userStm.setString(5, user.getAddress());
                userStm.setString(6, user.getTelephone());
                userStm.executeUpdate();
            }

            String insertOrUpdateVehicleQuery = "INSERT INTO vehicles VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE VehicleFile = VALUES(VehicleFile), Services = VALUES(Services), TypeOfVehicle = VALUES(TypeOfVehicle)";
            try (PreparedStatement vehicleStm = conn.prepareStatement(insertOrUpdateVehicleQuery)) {
                for (Vehicle v : user.getVehicles()) {
                    vehicleStm.setInt(1, v.getId());
                    vehicleStm.setInt(2, key);
                    vehicleStm.setString(3, v.getVehicleFile());
                    vehicleStm.setString(4, v.getServicesNeeded().toString());
                    vehicleStm.setInt(5, v.getVehicleType());
                    vehicleStm.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        return user;
    }

    @Override
    public User remove(Object key) {
        User u = this.get(key);

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM users WHERE Id=" + key);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM vehicles WHERE UserId=" + key);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        return u;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends User> users) {
        for (Map.Entry<? extends Integer, ? extends User> entry : users.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM users");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM vehicles");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    @Override
    public Set<Integer> keySet() {
        Set<Integer> keys = new HashSet<>();

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT Id FROM users")) {
            while (rs.next()) {
                keys.add(rs.getInt("Id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

        return keys;
    }

    @Override
    public Collection<User> values() {
        Collection<User> users = new HashSet<>();

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM users")) {
            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return users;
    }

    @Override
    public Set<Entry<Integer, User>> entrySet() {
        Set<Entry<Integer, User>> entries = new HashSet<>();

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM users")) {
            while (rs.next()) {
                int id = rs.getInt("Id");
                User user = extractUserFromResultSet(rs);
                entries.add(Map.entry(id, user));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return entries;
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("Id");
        String email = rs.getString("Email");
        String name = rs.getString("Name");
        String VATNumber = rs.getString("VATNumber");
        String Address = rs.getString("Address");
        String Telephone = rs.getString("Telephone");
        List<Vehicle> Vehicles = new ArrayList<>();

        if (id == 0) {
            User u = new Administrator(name, email);
            u.setId(id);
            return u;
        } else {
            User u = new Customer(name, email, VATNumber, Address, Telephone, Vehicles);
            u.setId(id);
            return u;
        }
    }

    private List<Vehicle> extractVehiclesFromResultSet(Object key) throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM vehicles WHERE UserId=" + key)) {

            while (rs.next()) {
                String vehicleFile = rs.getString("VehicleFile");
                int id = rs.getInt("VehicleId");
                List<Service> servicesNeeded = new ArrayList<>();
                servicesNeeded.addAll(ServicesFacade.parseServices(rs.getString("Services")));
                int vehicleType = rs.getInt("TypeOfVehicle");
                switch (vehicleType) {
                    case 1:
                        Vehicle dv = new DieselVehicle(id, vehicleFile, servicesNeeded);
                        vehicles.add(dv);
                        break;
                    case 2:
                        Vehicle ev = new ElectricVehicle(id, vehicleFile, servicesNeeded);
                        vehicles.add(ev);
                        break;
                    case 3:
                        Vehicle gv = new GasolineVehicle(id, vehicleFile, servicesNeeded);
                        vehicles.add(gv);
                        break;
                    case 4:
                        Vehicle hv = new HybridVehicle(id, vehicleFile, servicesNeeded);
                        vehicles.add(hv);
                        break;
                }
            }
        }
        return vehicles;
    }

}
