package Users;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class User {
    private String name;
    private String userId;
    private String email;
    private String morada;
    private String nif;

    public User() {
        this.name = "";
        this.userId = "";
        this.email = "";
        this.morada = "";
        this.nif = "";
    }

    public User(String name, String userId, String email, String morada, String nif) {
        this.name = name;
        this.userId = userId;
        this.email = email;
        this.morada = morada;
        this.nif = nif;
    }


    public static ArrayList<User> readUsers(String USERS_FILE) {
        ArrayList<User> usersList = new ArrayList<>();
        try {
            File file = new File(USERS_FILE);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] userData = data.split(",");
                String name = userData[0];
                String userId = userData[1];
                String email = userData[2];
                String morada = userData[3];
                String nif = userData[4];
                User user = new User(name, userId, email, morada, nif);
                usersList.add(user);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return usersList;
    }

    public static void writeUser(String USERS_FILE, User user) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE, true))) {
            writer.println(user.toString());
            System.out.println("User saved to file: " + user.toString());
        } catch (IOException e) {
            System.out.println("Failed to save user to file.");
        }
    }

    public static String createUser(String USERS_FILE) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name:");
        String name = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter NIF:");
        String morada = scanner.nextLine();
        System.out.println("Enter Address:");
        String nif = scanner.nextLine();
        String userId = User.generateUserId(name);
        User user = new User(name, userId, email, morada, nif);
        writeUser(USERS_FILE, user);
        scanner.close();
        return userId;
    }


    public static boolean loginUser(String userEmail, List<User> userList) {

        User user = findUserByEmail(userList, userEmail);

        if (user == null) {
            return false;
        } else {
            return true;
        }
    }

    private static User findUserByEmail(List<? extends User> userList, String userEmail) {
        for (User user : userList) {
            if (user.getEmail().equals(userEmail)) {
                return user;
            }
        }
        return null;
    }

    public static String generateUserId(String name) {
        return "U" + name + (int) (Math.random() * 1000);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }


    @Override
    public User clone() throws CloneNotSupportedException {
        User clone = (User) super.clone();
        return new User(this.name, this.userId, this.email, this.morada, this.nif);
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof User)) return false;
        User u = (User) o;
        return Objects.equals(name, u.name) &&
                Objects.equals(userId, u.userId) &&
                Objects.equals(email, u.email) &&
                Objects.equals(morada, u.morada) &&
                Objects.equals(nif, u.nif);
    }

    @Override
    public String toString() {
        return name + "," + userId + "," + email + "," + morada + "," + nif;}
    }



