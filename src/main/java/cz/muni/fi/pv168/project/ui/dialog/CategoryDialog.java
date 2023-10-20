package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Currency;
import cz.muni.fi.pv168.project.model.Ride;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.CategoryTableModel;
import cz.muni.fi.pv168.project.ui.model.RidesTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.Optional;

public class CategoryDialog extends EntityDialog<Category> {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private final JTable categoryTable;
    private final JButton newCategory;
    private final JButton renameCategory;
    private final JButton deleteCategory;
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
        this.renameCategory = new JButton("Rename");
        this.deleteCategory = new JButton("Delete");

        this.newCategory.addActionListener(e -> createNewCategory());
        this.renameCategory.addActionListener(e -> renameSelectedCategory());
        this.deleteCategory.addActionListener(e -> deleteSelectedCategory());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(newCategory);
        buttonPanel.add(renameCategory);
        buttonPanel.add(deleteCategory);

        frame.setLayout(new BorderLayout());
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(categoryTable), BorderLayout.CENTER);
        changeActionState(0);
    }

    private void createNewCategory(){
        var dialog = new CategoryNameDialog(null);
        var result = dialog.show(categoryTable, "New category");
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
        var result = dialog.show(categoryTable, "Rename category");
        categoryTableModel.fireTableDataChanged();
        ((RidesTableModel)ridesTable.getModel()).fireTableDataChanged();
    }
    private void deleteSelectedCategory() {
        int[] rows = categoryTable.getSelectedRows();
        for(int i = rows.length - 1; i >= 0; i--){
            categoryListModel.remove(rows[i]);
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
    public Optional<Category> show(JComponent parentComponent, String title) {
        frame.setTitle(title);
        frame.pack();
        frame.setLocationRelativeTo(parentComponent);
        frame.setVisible(true);
        return Optional.empty();
    }
}
