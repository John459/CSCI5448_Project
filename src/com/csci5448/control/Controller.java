package com.csci5448.control;

import com.csci5448.accounts.Account;
import com.csci5448.content.Sport;
import com.csci5448.data.JournalistAccountDAO;
import com.csci5448.data.SportDAO;
import com.csci5448.data.UserAccountDAO;
import com.csci5448.pages.Page;
import com.csci5448.pages.common_pages.LoginPage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.LinkedList;
import java.util.Queue;

public class Controller {

    public static final JournalistAccountDAO journalistAccountDAO = new JournalistAccountDAO();
    public static final UserAccountDAO userAccountDAO = new UserAccountDAO();
    public static final SportDAO sportDAO = new SportDAO();

    public static SessionFactory sessionFactory;

    private static Queue<Page> previousPages = new LinkedList<>();
    private static Page currentPage;
    private static Account currentAccount;

    public static void initSessionFactory() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public static void setCurrentAccount(Account account) {
        currentAccount = account;
    }

    public static void setCurrentPage(Page page) {
        if (currentPage != null) {
            previousPages.add(currentPage);
        }
        currentPage = page;
        currentPage.displayPage();
    }

    public static void returnToPreviousPage() {
        if (previousPages.size() == 0) {
            return;
        }
        Page previousPage = previousPages.poll();
        if (previousPage != null) {
            currentPage = previousPage;
            currentPage.displayPage();
        }
    }

    public static void logout() {
        if (currentAccount == null) {
            return;
        }

        System.out.println("Logging out...");
        currentAccount = null;
        previousPages.clear();
        setCurrentPage(new LoginPage());
    }

    public static void sendCommandToPage(String command, Object arg) {
        currentPage.performAction(command, arg);
    }

}
