package com.csci5448.pages.common_pages;

import com.csci5448.accounts.Account;
import com.csci5448.control.Controller;
import com.csci5448.data.SessionManager;
import com.csci5448.pages.Page;
import com.csci5448.pages.PageDisplay;
import org.hibernate.Session;

public class DeleteAccountPage<T extends Account> extends Page {

    private static final String DELETE_ACCOUNT_ID = "delete";
    private static final String CONFIRM_DELETE_ID = "confirm";

    private final T account;

    public DeleteAccountPage(T account) {
        this.account = account;
        super.addPageActionStringArr(DELETE_ACCOUNT_ID, this::deleteAccountAction);
    }

    private void deleteAccountAction(String[] credentials) {
        if (credentials == null || credentials.length != 2) {
            return;
        }
        if (!credentials[0].equals(account.getUsername()) || !credentials[1].equals(account.getPassword())) {
            System.out.println("\tInvalid account credentials.");
            return;
        }

        super.addPageAction(CONFIRM_DELETE_ID, this::confirmAccountDeletionAction);
        System.out.println("\tAre you sure you want to delete your account? Type \'" + CONFIRM_DELETE_ID +
                "\' to confirm the deletion.");
        PageDisplay.getPageDisplay().showInputPrompt();

    }

    private void confirmAccountDeletionAction(String arg) {
        try (Session session = Controller.sessionFactory.openSession()) {
            if (!SessionManager.getSessionManager().performOp(session, session::delete, account)) {
                return;
            }
        }
        System.out.println("\tAccount deletion successful.");
        Controller.logout();
    }

    @Override
    public boolean freezeInput(String identifier, String arg) {
        if (super.containsPageAction(CONFIRM_DELETE_ID) && !identifier.equalsIgnoreCase(CONFIRM_DELETE_ID)) {
            super.removePageAction(CONFIRM_DELETE_ID);
            System.out.println("\tAccount will not be deleted.");
            Controller.returnToPreviousPage();
            return true;
        }
        return false;
    }

    @Override
    public void performDefaultAction(String identifier, String arg) {
        System.out.println("\tAccount will not be deleted.");
        Controller.returnToPreviousPage();
    }

    @Override
    public void displayPage() {
        System.out.println("\tTo delete your account please type \'" + DELETE_ACCOUNT_ID + " <username> <password>\'.");
        PageDisplay.getPageDisplay().showInputPrompt();
    }

}
