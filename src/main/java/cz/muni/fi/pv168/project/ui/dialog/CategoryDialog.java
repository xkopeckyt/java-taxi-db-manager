package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.CategoryTableModel;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.Optional;

import static cz.muni.fi.pv168.project.ui.resources.Icons.DELETE_ICON;
import static cz.muni.fi.pv168.project.ui.resources.Icons.EDIT_ICON;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;

public class CategoryDialog extends EntityDialog<Category> {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private final JTable categoryTable;
    private final JButton newCategory;
    private final JMenuItem renameCategory;
    private final JMenuItem deleteCategory;
    private final JFrame frame;
    private final CategoryListModel categoryListModel;
    private final CategoryTableModel categoryTableModel;
    private final JTable ridesTable;
    public CategoryDialog(CategoryListModel categoryModel, JTable ridesTable){
        this.categoryListModel = categoryModel;
        this.ridesTable = ridesTable;
        frame = createFrame();
        this.categoryTableModel = new CategoryTableModel(categoryModel);
        this.categoryTable = new JTable(categoryTableModel, categoryTableModel.getColumnModel());
        this.categoryTable.setRowSorter(categoryTableModel.getRowSorter());
        this.categoryTable.addComponentListener(categoryTableModel.getResizeListener());
        this.categoryTable.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);

        this.newCategory = new JButton("New");
        this.renameCategory = new JMenuItem("Rename", EDIT_ICON);
        this.deleteCategory = new JMenuItem("Delete", DELETE_ICON);

        this.newCategory.addActionListener(e -> createNewCategory());
        this.renameCategory.addActionListener(e -> renameSelectedCategory());
        this.deleteCategory.addActionListener(e -> deleteSelectedCategory());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(newCategory);
        categoryTable.setComponentPopupMenu(createPopUpMenu());

        frame.setLayout(new BorderLayout());
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(categoryTable), BorderLayout.CENTER);
        changeActionState(0);
    }

    private void createNewCategory(){
        var dialog = new CategoryNameDialog(null);
        var result = dialog.show(categoryTable, "New category", OK_CANCEL_OPTION, null);
        if(result.isPresent()){
            categoryListModel.add(result.get());
            int idx = categoryListModel.getIndex(result.get());
            categoryTableModel.fireTableRowsInserted(idx, idx);
        }

    }
    private void renameSelectedCategory(){
        int row = categoryTable.getSelectedRow();
        Category category = categoryListModel.getElementAt(row);
        var dialog = new CategoryNameDialog(category);
        var result = dialog.show(categoryTable, "Rename category", OK_OPTION, null);
        categoryTableModel.fireTableDataChanged();
        ((RidesTableModel)ridesTable.getModel()).fireTableDataChanged();
    }
    private void deleteSelectedCategory() {
        RidesTableModel ridesTableModel = (RidesTableModel) ridesTable.getModel();
        int[] rows = categoryTable.getSelectedRows();
        for(int i = rows.length - 1; i >= 0; i--){
            int modelIndex = categoryTable.convertRowIndexToModel(rows[i]);
            Category category = categoryTableModel.getEntity(modelIndex);
            if(category.getName() == "Cash" || category.getName() == "Card"){
                JOptionPane.showMessageDialog(null,
                        "The category: \"" + category.getName() + "\" cannot be deleted.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                continue;
            }
            boolean used = false;
            for(int j = 0; j < ridesTableModel.getRowCount(); j++){
                if(ridesTableModel.getEntity(j).getCategory() == category){
                    used = true;
                    break;
                }
            }
            if(used){
                JOptionPane.showMessageDialog(null,
                        "The category: \"" + category.getName() + "\" is being used.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            } else{
                categoryTableModel.deleteRow(modelIndex);
            }
        }
        categoryTableModel.fireTableDataChanged();
    }


    private JFrame createFrame() {
        var frame = new JFrame("Categories");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        return frame;
    }

    private void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        changeActionState(selectionModel.getSelectedItemsCount());
    }

    private void changeActionState(int count) {
        renameCategory.setEnabled(count == 1);
        deleteCategory.setEnabled(count >= 1);
    }

    private JPopupMenu createPopUpMenu(){
        var menu = new JPopupMenu();
        menu.add(renameCategory);
        menu.add(deleteCategory);
        return menu;
    }

    @Override
    Category getEntity() {
        int selectedRow = categoryTable.getSelectedRow();
        if (selectedRow != -1) {
            return (Category) categoryTable.getModel().getValueAt(selectedRow, 0);
        } else {
            return null;
        }
    }

    @Override
    public Optional<Category> show(JComponent parentComponent, String title, int option, Object[] options) {
        frame.setTitle(title);
        frame.pack();
        frame.setLocationRelativeTo(parentComponent);
        frame.setVisible(true);
        return Optional.empty();
    }
}
