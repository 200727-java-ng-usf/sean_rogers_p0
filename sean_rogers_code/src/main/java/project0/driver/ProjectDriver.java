package project0.driver;

import project0.dao.AccountDAOImpl;
import project0.models.Account;
import project0.utilities.AppState;
import project0.utilities.DAOUtilities;

import java.util.ArrayList;
import java.util.List;

public class ProjectDriver {

    public static AppState app = new AppState();

    public static void main(String[] args) {

        DAOUtilities daou = new DAOUtilities();
        AccountDAOImpl adi = new AccountDAOImpl();
        List<Account> accountList = adi.getAccounts();

        System.out.println(accountList.size());

        for(int i = 0; i < accountList.size(); i++) {
            System.out.println(accountList.get(i));
        }

    }
}
