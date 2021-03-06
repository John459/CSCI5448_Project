package com.csci5448.pages.common_pages;

import com.csci5448.accounts.Account;
import com.csci5448.control.Controller;
import com.csci5448.control.EmailControl;
import com.csci5448.pages.Page;
import com.csci5448.pages.PageDisplay;

import javax.mail.MessagingException;

public class SupportRequestPage extends Page {

    private static final String SUBMIT_REQUEST_ID = "submit_request";

    public SupportRequestPage() {
        super.addPageAction(SUBMIT_REQUEST_ID, this::requestSupportAction);
    }

    private void requestSupportAction(String supportRequest) {
        if (supportRequest == null || supportRequest.length() == 0) {
            return;
        }

        Account currentAccount = Controller.getCurrentAccount();
        if (currentAccount == null) {
            return;
        }

        final String messageBody = "User: " + currentAccount.getUsername() + "\n\n" +
                supportRequest + "\n\n" +
                "Instructions: Forward this email to " + currentAccount.getUsername() + " together " +
                "with your reply.";

        System.out.println("\tSending support request...");

        try {
            EmailControl.getEmailControl().sendSelfEmail("Support Request From " + currentAccount.getUsername(),
                    messageBody);
        } catch (MessagingException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("\tYour support request has been sent to an administrator and will be reviewed shortly.\n" +
                "\tPlease periodically check your inbox at " + currentAccount.getUsername() + " for a reply.");
        Controller.goToLobbyPage();
    }

    @Override
    public void displayPage() {
        PageDisplay.getPageDisplay().showPageWelcomeText("Support Request");
        PageDisplay.getPageDisplay().showNavCommands();
        System.out.println("\tPlease type \'" + SUBMIT_REQUEST_ID + "\' followed" +
                " by a description of the problem you are experiencing.");
        PageDisplay.getPageDisplay().showInputPrompt();
    }
}
