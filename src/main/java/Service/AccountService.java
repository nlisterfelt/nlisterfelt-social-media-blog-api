package Service;

import java.util.List;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }
    public List<Account> getAllAccounts(){
        return accountDAO.getAllAccounts();
    }
    public Account addAccount(Account account){
        if(account.username!=null && account.password.length()>=4){
            return accountDAO.addAccount(account);
        }
        return null;
    }
    public Account checkAccount(Account account){
        return accountDAO.checkAccount(account);
    }
}
