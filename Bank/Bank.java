
//Banking Application CipherByte-Technologies

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Bank {

    // JDBC connection variables
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/userdb"; //url of db
    private static final String JDBC_USER = "root"; //username 
    private static final String JDBC_PASSWORD = "1234"; //password of mysql

    // Scanner object for user input
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Display welcome message
        System.out.println("****WELCOME TO BANKY*****");
        System.out.println("A Tradition Of Trust");
        System.out.println("Choose an option: 1. Signup  2. Login");

        // Read user's choice (signup or login)
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 1) {
            // Signup process
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            // Call signup method and proceed to login if signup is successful
            if (signup(email, password)) {
                System.out.println("Enter Details to login: ");
                System.out.print("Enter your email: ");
                String email1 = scanner.nextLine();
                System.out.print("Enter your password: ");
                String password1 = scanner.nextLine();
                if (login(email1, password1)) {
                    userfunc(email1);
                }
            }
        } else if (choice == 2) {
            // Login process
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            boolean res = login(email, password);
            if (res) userfunc(email);
        } else {
            // Invalid choice
            System.out.println("Invalid choice");
        }

        scanner.close(); // Close the scanner
    }

    // Signup method to register a new user
    public static boolean signup(String email, String password) {
        boolean signup = false;
        String sql = "INSERT INTO users (email, password) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Signup successful!");
                signup = true;
            }

        } catch (SQLException e) {
            System.out.println("Error during signup: " + e.getMessage());
        }
        return signup;
    }

    // Login method to authenticate an existing user
    public static boolean login(String email, String password) {
        boolean login = false;
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful!");
                login = true;
            } else {
                System.out.println("Invalid email or password.");
            }

        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
        return login;
    }

    // Method for user functionalities after login
    static void userfunc(String email) {
        int c = 1;
        while (c == 1) {
            System.out.println();
            System.out.println();
            System.out.println(".....A Tradition of Trust.....");
            System.out.println("1. Create an Account\t2. Check Balance"); //options for user
            System.out.println("3. Withdraw\t\t 4. Deposit");
            System.out.println("5. Transfer funds\t 6. Exit");
            System.out.println("Enter an option: ");
            int option = scanner.nextInt(); //option choosen by user
            switch (option) {
                case 1:
                    //checking whether user has an account or not
                    if (!hasAccount(email)) {
                        createaccount(email);
                    } else {
                        System.out.println("You have already an account.");
                    }
                    break;
                case 2:
                    checkbalance(email);
                    break;
                case 3:
                    Withdraw(email);
                    break;
                case 4:
                    Deposit(email);
                    break;
                case 5:
                    TransferFunds(email);
                    break;
                case 6:
                    c = 2;
                    break;
                default:
                    System.out.println("Enter Correct option...");
                    break;
            }
        }
    }

    // Method to check if the user already has an account
    static boolean hasAccount(String email) {
        boolean hasAccount = false;
        String sql = "SELECT 1 FROM accounts WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                hasAccount = true;
            }
        } catch (SQLException e) {
            System.out.println("Error during account check: " + e.getMessage());
        }
        return hasAccount;
    }

    // Method to create a new account for the user
    static void createaccount(String email) {
        System.out.print("Enter initial deposit amount: ");
        double amount = scanner.nextDouble();
        String sql = "INSERT INTO accounts (email, balance) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setDouble(2, amount);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Account created successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error during account creation: " + e.getMessage());
        }
    }

    // Method to check the account balance
    static void checkbalance(String email) {
        String sql = "SELECT balance FROM accounts WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double balance = rs.getDouble("balance");
                System.out.println("Your current balance is: " + balance);
            } else {
                System.out.println("Account not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error during balance check: " + e.getMessage());
        }
    }

    // Method to withdraw money from the account
    static void Withdraw(String email) {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        String sqlSelect = "SELECT balance FROM accounts WHERE email = ?";
        String sqlUpdate = "UPDATE accounts SET balance = ? WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
             PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {

            pstmtSelect.setString(1, email);

            ResultSet rs = pstmtSelect.executeQuery();
            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");
                if (currentBalance >= amount) {
                    double newBalance = currentBalance - amount;
                    pstmtUpdate.setDouble(1, newBalance);
                    pstmtUpdate.setString(2, email);
                    pstmtUpdate.executeUpdate();
                    System.out.println("Withdrawal successful. New balance: " + newBalance);
                } else {
                    System.out.println("Insufficient balance.");
                }
            } else {
                System.out.println("Account not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error during withdrawal: " + e.getMessage());
        }
    }

    // Method to deposit money into the account
    static void Deposit(String email) {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        String sqlSelect = "SELECT balance FROM accounts WHERE email = ?";
        String sqlUpdate = "UPDATE accounts SET balance = ? WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect);
             PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {

            pstmtSelect.setString(1, email);

            ResultSet rs = pstmtSelect.executeQuery();
            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");
                double newBalance = currentBalance + amount;
                pstmtUpdate.setDouble(1, newBalance);
                pstmtUpdate.setString(2, email);
                pstmtUpdate.executeUpdate();
                System.out.println("Deposit successful. New balance: " + newBalance);
            } else {
                System.out.println("Account not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error during deposit: " + e.getMessage());
        }
    }

    // Method to transfer funds to another account
    static void TransferFunds(String email) {
        System.out.print("Enter recipient email: ");
        String recipientEmail = scanner.next();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();

        String sqlSelectSender = "SELECT balance FROM accounts WHERE email = ?";
        String sqlSelectRecipient = "SELECT balance FROM accounts WHERE email = ?";
        String sqlUpdateSender = "UPDATE accounts SET balance = ? WHERE email = ?";
        String sqlUpdateRecipient = "UPDATE accounts SET balance = ? WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement pstmtSelectSender = conn.prepareStatement(sqlSelectSender);
             PreparedStatement pstmtSelectRecipient = conn.prepareStatement(sqlSelectRecipient);
             PreparedStatement pstmtUpdateSender = conn.prepareStatement(sqlUpdateSender);
             PreparedStatement pstmtUpdateRecipient = conn.prepareStatement(sqlUpdateRecipient)) {

            pstmtSelectSender.setString(1, email);
            pstmtSelectRecipient.setString(1, recipientEmail);

            ResultSet rsSender = pstmtSelectSender.executeQuery();
            ResultSet rsRecipient = pstmtSelectRecipient.executeQuery();

            if (rsSender.next() && rsRecipient.next()) {
                double senderBalance = rsSender.getDouble("balance");
                double recipientBalance = rsRecipient.getDouble("balance");

                if (senderBalance >= amount) {
                    double newSenderBalance = senderBalance - amount;
                    double newRecipientBalance = recipientBalance + amount;

                    pstmtUpdateSender.setDouble(1, newSenderBalance);
                    pstmtUpdateSender.setString(2, email);
                    pstmtUpdateSender.executeUpdate();

                    pstmtUpdateRecipient.setDouble(1, newRecipientBalance);
                    pstmtUpdateRecipient.setString(2, recipientEmail);
                    pstmtUpdateRecipient.executeUpdate();

                    System.out.println("Transfer successful. Your new balance: " + newSenderBalance);
                } else {
                    System.out.println("Insufficient balance.");
                }
            } else {
                System.out.println("Account not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error during fund transfer: " + e.getMessage());
        }
    }
}
