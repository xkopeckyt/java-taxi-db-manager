package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.DrivingLicence;
import cz.muni.fi.pv168.project.business.model.Ride;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.CategoryTableModel;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;
import cz.muni.fi.pv168.project.ui.model.TemplateListModel;
import cz.muni.fi.pv168.project.ui.model.TemplateTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.List;
import java.util.Optional;

import static cz.muni.fi.pv168.project.ui.resources.Icons.DELETE_ICON;
import static cz.muni.fi.pv168.project.ui.resources.Icons.EDIT_ICON;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;

public class TemplatesDialog extends EntityDialog<Template> {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    private final JTable templatesTable;
    private final JButton newTemplate;
    private final JMenuItem renameTemplate;
    private final JMenuItem deleteTemplate;
    private final JFrame frame;
    private final TemplateListModel templateListModel;
    private final TemplateTableModel templateTableModel;
    private final ListModel<Category> categoryListModel;
    private final DrivingLicence licence;


    public TemplatesDialog(TemplateListModel templeModel, ListModel<Category> categoryListModel, DrivingLicence licence) {
        this.templateListModel = templeModel;
        this.categoryListModel = categoryListModel;
        this.licence = licence;
        frame = createFrame();
        this.templateTableModel = new TemplateTableModel(templeModel);
        this.templatesTable = new JTable(templateTableModel, templateTableModel.getColumnModel());
        this.templatesTable.setRowSorter(templateTableModel.getRowSorter());
        this.templatesTable.addComponentListener(templateTableModel.getResizeListener());
        this.templatesTable.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);

        this.newTemplate = new JButton("New");
        this.renameTemplate = new JMenuItem("Rename", EDIT_ICON);
        this.deleteTemplate = new JMenuItem("Delete", DELETE_ICON);

        this.newTemplate.addActionListener(e -> createNewTemplate());
        this.renameTemplate.addActionListener(e -> renameSelectedTemplate());
        this.deleteTemplate.addActionListener(e -> deleteSelectedTemplate());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(newTemplate);
        templatesTable.setComponentPopupMenu(createPopUpMenu());

        frame.setLayout(new BorderLayout());
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(templatesTable), BorderLayout.CENTER);
        changeActionState(0);
    }

    private JFrame createFrame() {
        var frame = new JFrame("Templates");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        return frame;
    }

    private void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        changeActionState(selectionModel.getSelectedItemsCount());
    }

    private void changeActionState(int count) {
        renameTemplate.setEnabled(count == 1);
        deleteTemplate.setEnabled(count >= 1);
    }

    private void createNewTemplate(){
        boolean validInput = false;
        var template = getNewTemplate();
        if (template == null) {
            return;
        }

        while(!validInput) {

            var dialog = new TemplateNameDialog(template, templateListModel,  categoryListModel, licence);
            var result = dialog.show(templatesTable, "New template", OK_CANCEL_OPTION, null);
            if (result.isPresent()) {
                String templateName = result.get().getName();
                if(templateListModel.isNameUsed(templateName)){
                    JOptionPane.showMessageDialog(null,
                            "The Template name: \"" + templateName + "\" is already used.", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
                else{
                    templateListModel.addRow(result.get());
                    int idx = templateListModel.getIndex(result.get());
                    templateTableModel.fireTableRowsInserted(idx, idx);
                    validInput = true;
                }

            }
            else{
                validInput = true;
            }
        }
    }

    private void renameSelectedTemplate(){
        int row = templatesTable.getSelectedRow();
        Template template = templateListModel.getElementAt(row);
        var dialog = new TemplateNameDialog(template, templateListModel, categoryListModel, licence);
        var result = dialog.show(templatesTable, "Rename category", OK_OPTION, null);
        if(result.isPresent()){
            templateTableModel.updateRow(template);
        }
    }

    private void deleteSelectedTemplate() {
        int[] rows = templatesTable.getSelectedRows();
        for (int i = rows.length - 1; i >= 0; i--) {
            var row = rows[i];
            int modelIndex = templatesTable.convertRowIndexToModel(row);
            templateTableModel.deleteRow(modelIndex);
        }
        templateTableModel.fireTableDataChanged();
    }

    private JPopupMenu createPopUpMenu(){
        var menu = new JPopupMenu();
        menu.add(renameTemplate);
        menu.add(deleteTemplate);
        return menu;
    }

    @Override
    Template getEntity() {
        int selectedRow = templatesTable.getSelectedRow();
        if (selectedRow != -1) {
            return (Template) templatesTable.getModel().getValueAt(selectedRow, 0);
        } else {
            return null;
        }
    }

    private Template getNewTemplate() {
        var rideDialogResult = RideDialog.showDialog("New template", null, categoryListModel, licence,
                templateListModel, false);
        if (rideDialogResult.isPresent()) {
            var ride = rideDialogResult.get();
            return new Template(
                    "",
                    ride.getDistance(),
                    ride.getRideDateTime(),
                    ride.getPrice(),
                    ride.getOriginalCurrency(),
                    ride.getCategory(),
                    ride.getPassengersCount()
            );
        }
        return null;
    }

    @Override
    public Optional<Template> show(JComponent parentComponent, String title, int option, Object[] options) {
        frame.setTitle(title);
        frame.pack();
        frame.setLocationRelativeTo(parentComponent);
        frame.setVisible(true);
        return Optional.empty();
    }
}
