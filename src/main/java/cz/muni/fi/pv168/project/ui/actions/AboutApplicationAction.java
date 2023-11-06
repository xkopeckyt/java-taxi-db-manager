package cz.muni.fi.pv168.project.ui.actions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Optional;

import static javax.swing.JOptionPane.*;

public class AboutApplicationAction extends AbstractAction {
    public AboutApplicationAction() {
        super("About application");
        putValue(SHORT_DESCRIPTION, "Show info About application");
        putValue(MNEMONIC_KEY, KeyEvent.VK_I);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,1));
        panel.add(new JLabel("Creators:"));
        panel.add(new JLabel("  Jan Sokolář, Marek Zanechal"));
        panel.add(new JLabel("  Roland Olejník, Tomáš Kopecký"));
        panel.add(new JLabel("Project URL:"));
        panel.add(new JLabel("  https://gitlab.fi.muni.cz/xsokolar/pv-168-project-share-car-rider"));
        JOptionPane.showOptionDialog(null, panel, "About Application",
                NO_OPTION, PLAIN_MESSAGE, null, new String[]{}, null);

    }
}
