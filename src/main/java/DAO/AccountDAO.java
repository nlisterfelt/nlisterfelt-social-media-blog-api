package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    public List<Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                accounts.add(account);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }
    public Account addAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.username);
            preparedStatement.setString(2, account.password);

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            
            if(pkeyResultSet.next()){
                int generatedAccountId = pkeyResultSet.getInt(1);
                return new Account(generatedAccountId, account.username, account.password);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Account checkAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE account.username=?, account.password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, account.username);
            preparedStatement.setString(2, account.password);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account accountWithID = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return accountWithID;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
