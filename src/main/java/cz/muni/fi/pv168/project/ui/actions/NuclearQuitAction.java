package cz.muni.fi.pv168.project.ui.actions;

import cz.muni.fi.pv168.project.storage.sql.db.DatabaseManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Action that terminates the application and deletes a database.
 */
public class NuclearQuitAction extends AbstractAction {

    private final DatabaseManager databaseManager;

    public NuclearQuitAction() {
        this(DatabaseManager.createProductionInstance());
    }

    public NuclearQuitAction(DatabaseManager databaseManager) {
        super("Nuclear Quit");
        this.databaseManager = databaseManager;

        putValue(SHORT_DESCRIPTION, "Terminates the application and deletes a database");
        putValue(MNEMONIC_KEY, KeyEvent.VK_Q);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        databaseManager.destroySchema();

        System.exit(0);
    }
}
