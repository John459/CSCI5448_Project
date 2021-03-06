package com.csci5448.pages.user_pages;

import com.csci5448.accounts.UserAccount;
import com.csci5448.content.Sport;
import com.csci5448.content.SportFactory;
import com.csci5448.control.Controller;
import com.csci5448.pages.PageDisplay;
import com.csci5448.pages.common_pages.LobbyPage;

public class UserLobbyPage extends LobbyPage {

    public UserLobbyPage() {
        super(UserAccount.class);
        for (Sport sport : Sport.values()) {
            super.addPageActionCommandConsumer(sport.toString(), selectedSportStr -> {
                Sport selectedSport = SportFactory.chooseSport(selectedSportStr);
                if (selectedSport != null) {
                    Controller.setCurrentPage(new SportOptionPage(selectedSport));
                }
            });
        }
    }

    public void displayPage() {
        PageDisplay.getPageDisplay().showPageWelcomeText("ESPNGen Lobby");
        PageDisplay.getPageDisplay().showNavCommands();
        System.out.println("\tPlease select a sport from the menu below:");
        for (Sport sport : Sport.values()) {
            System.out.println("\t\t" + sport);
        }
        super.displayPage();
        PageDisplay.getPageDisplay().showInputPrompt();
    }

}
