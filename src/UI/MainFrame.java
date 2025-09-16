package UI;

import model.User;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public static final String CARD_REG = "reg";
    public static final String CARD_PROFILE = "profile";
    public static final String CARD_SEARCH = "search";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);

    private final RegistrationPanel registrationPanel;
    private final ProfilePanel profilePanel;
    private final SearchPanel searchPanel;

    // We keep a transient User object while navigating
    private final User workingUser = new User();

    public MainFrame() {
        super("Matchy");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        registrationPanel = new RegistrationPanel(this, workingUser);
        profilePanel = new ProfilePanel(this, workingUser);
        searchPanel = new SearchPanel(this);

        cardPanel.add(registrationPanel, CARD_REG);
        cardPanel.add(profilePanel, CARD_PROFILE);
        cardPanel.add(searchPanel, CARD_SEARCH);

        setLayout(new BorderLayout());
        add(cardPanel, BorderLayout.CENTER);

        showCard(CARD_REG);
    }

    public void showCard(String name) {
        cardLayout.show(cardPanel, name);
    }
}
