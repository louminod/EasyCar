package view.frames;

import entities.Customer;
import entities.Employee;
import entities.Reservation;
import entities.User;
import entities.references.AccountType;
import entities.references.ReservationStatus;
import model.controllers.AddUserFrameController;
import model.controllers.ManageUsersFrameController;
import view.CustomFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ManageUsersFrame extends JFrame implements CustomFrame {

    private JPanel centerPanel;
    private JPanel pageEndPanel;
    private JTable table;

    private Map<Integer, User> mapTableRowValues;

    private ManageUsersFrameController controller;

    public ManageUsersFrame(ManageUsersFrameController controller) {
        this.controller = controller;
    }

    @Override
    public void buildFrame(String title, int width, int height) {
        this.setTitle(title);
        this.setSize(width, height);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);

        this.buildComponents();
    }

    @Override
    public void buildComponents() {
        this.mapTableRowValues = new HashMap<>();

        this.loadCenterPanel();
        this.loadBottomPanel();
    }

    /**
     * Load the JFrame center Panel.
     */
    private void loadCenterPanel() {
        this.centerPanel = new JPanel();
        this.centerPanel.setLayout(new BorderLayout());
        this.add(this.centerPanel, BorderLayout.CENTER);

        this.createTable();
        this.loadTableData();
        this.centerPanel.add(this.table.getTableHeader(), BorderLayout.NORTH);
        this.centerPanel.add(this.table, BorderLayout.CENTER);

    }

    /**
     * Create the users table.
     */
    private void createTable() {
        String colNames[] = {"TYPE", "NAME", "LASTNAME", "EMAIL"};
        if (this.table == null) {
            this.table = new JTable() {

                public boolean isCellEditable(int nRow, int nCol) {
                    return false;
                }
            };
        }
        DefaultTableModel tableModel = new DefaultTableModel(colNames, 0);
        tableModel.setColumnIdentifiers(colNames);
        this.table.setModel(tableModel);
        this.table.setRowHeight(30);
        this.table.setShowGrid(true);
        this.table.setComponentPopupMenu(this.createPopupMenu());
        this.table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (SwingUtilities.isRightMouseButton(me) == true) {
                    try {
                        int row = table.rowAtPoint(me.getPoint());
                        table.clearSelection();
                        table.addRowSelectionInterval(row, row);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Create the table popups.
     *
     * @return The JPopupMenu.
     */
    private JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItemChangePassword = new JMenuItem("Change Password");
        menuItemChangePassword.addActionListener(event -> {
            int row = this.table.getSelectedRow();
            User user = this.mapTableRowValues.get(row);
            String password = JOptionPane.showInputDialog(new JFrame(String.format("Change %s %s password", user.getLastName(), user.getName())), "New Password:");
            user.setPassword(password);
            this.controller.updateUser(user);
        });
        popupMenu.add(menuItemChangePassword);
        JMenuItem menuItemDeleteUser = new JMenuItem("Delete this user");
        menuItemDeleteUser.addActionListener(event -> {
            int row = this.table.getSelectedRow();
            User user = this.mapTableRowValues.get(row);
            this.controller.deleteUser(user);
            this.loadTableData();
        });
        popupMenu.add(menuItemDeleteUser);
        return popupMenu;
    }

    /**
     * Load the table data.
     */
    private void loadTableData() {
        DefaultTableModel tableModel = (DefaultTableModel) this.table.getModel();
        if (tableModel.getRowCount() > 0) {
            for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
                tableModel.removeRow(i);
            }
        }
        int row = 0;
        for (User u : this.controller.getUsers()) {
            tableModel.addRow(u.convertToObjectTable());
            this.mapTableRowValues.put(row, u);
            row++;
        }
        this.table.setModel(tableModel);
        tableModel.fireTableDataChanged();
    }

    /**
     * Load the JFrame bottom Panel.
     */
    private void loadBottomPanel() {
        this.pageEndPanel = new JPanel();
        this.pageEndPanel.setLayout(new FlowLayout());
        this.add(this.pageEndPanel, BorderLayout.PAGE_END);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(event -> {
            hideFrame();
        });
        JButton btnRefresh = new JButton("Refresh Users");
        btnRefresh.addActionListener(event -> {
            this.loadTableData();
        });
        JButton btnAddEmployee = new JButton("Add employee");
        btnAddEmployee.addActionListener(event -> {
            AddUserFrameController addUserFrameController = new AddUserFrameController(AccountType.EMPLOYEE);
            AddUserFrame addUserFrame = new AddUserFrame(addUserFrameController);
            addUserFrame.buildFrame("Add employee", 600, 400);
            addUserFrame.showFrame();
        });
        JButton btnAddAdministrator = new JButton("Add administrator");
        btnAddAdministrator.addActionListener(event -> {
            AddUserFrameController addUserFrameController = new AddUserFrameController(AccountType.ADMINISTRATOR);
            AddUserFrame addUserFrame = new AddUserFrame(addUserFrameController);
            addUserFrame.buildFrame("Add administrator", 600, 400);
            addUserFrame.showFrame();
        });

        JButton btnHelp = new JButton("Help");
        btnHelp.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("- Right click on a user to interact");
            JOptionPane.showMessageDialog(null, sb.toString());
        });

        this.pageEndPanel.add(btnBack);
        this.pageEndPanel.add(btnRefresh);
        this.pageEndPanel.add(btnAddEmployee);
        this.pageEndPanel.add(btnAddAdministrator);
        this.pageEndPanel.add(btnHelp);
    }

    @Override
    public void showFrame() {
        this.setVisible(true);
    }

    @Override
    public void hideFrame() {
        this.setVisible(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManageUsersFrame that = (ManageUsersFrame) o;
        return Objects.equals(centerPanel, that.centerPanel) &&
                Objects.equals(pageEndPanel, that.pageEndPanel) &&
                Objects.equals(table, that.table) &&
                Objects.equals(mapTableRowValues, that.mapTableRowValues) &&
                Objects.equals(controller, that.controller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(centerPanel, pageEndPanel, table, mapTableRowValues, controller);
    }

    @Override
    public String toString() {
        return "ManageUsersFrame{" +
                "centerPanel=" + centerPanel +
                ", pageEndPanel=" + pageEndPanel +
                ", table=" + table +
                ", mapTableRowValues=" + mapTableRowValues +
                ", controller=" + controller +
                '}';
    }
}
