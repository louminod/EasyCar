package view.frames;

import entities.Administrator;
import entities.Car;
import entities.references.AccountType;
import model.controllers.*;
import view.CustomFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainFrame extends JFrame implements CustomFrame {

    private JTable table;
    private Map<Integer, Car> mapTableRowValues;

    private JPanel centerPanel;
    private JPanel pageEndPanel;

    private MainFrameController controller;

    public MainFrame(MainFrameController controller) {
        this.controller = controller;
    }

    @Override
    public void buildFrame(String title, int width, int height) {
        this.setTitle(title);
        if (this.controller.getAccountType() != AccountType.ADMINISTRATOR) {
            this.setSize(width, height);
        } else {
            this.setSize(200, 100);
        }
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.buildComponents();
    }

    @Override
    public void buildComponents() {
        this.mapTableRowValues = new HashMap<>();
        this.loadCenterPanel();
        this.loadPageEndPanel();
    }

    /**
     * Load the JFrame center Panel.
     */
    private void loadCenterPanel() {
        this.centerPanel = new JPanel();
        this.centerPanel.setLayout(new BorderLayout());

        if (this.controller.getAccountType() != AccountType.ADMINISTRATOR) {
            this.createTable();
            this.loadTableData();
            this.centerPanel.add(this.table.getTableHeader(), BorderLayout.NORTH);
            this.centerPanel.add(this.table, BorderLayout.CENTER);
        } else {
            this.centerPanel.add(new JLabel(String.format("Welcome %s !", this.controller.getUser().getName())));
        }

        this.add(this.centerPanel, BorderLayout.CENTER);
    }

    /**
     * Create the cars table.
     */
    private void createTable() {
        String colNames[] = {"BRAND", "TYPE", "MODEL", "COLOR", "SEATS", "POWER"};
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
        JMenuItem menuItemSee = new JMenuItem(this.controller.getAccountType() == AccountType.EMPLOYEE ? "See/Update car details" : "See car details");
        menuItemSee.addActionListener(event -> {
            int row = this.table.getSelectedRow();
            Car car = this.mapTableRowValues.get(row);
            CarFrameController carFrameController = new CarFrameController(car, controller.getUser());
            CarFrame carFrame = new CarFrame(carFrameController);
            String title = String.format("%s %s", car.getBrand(), car.getModel());
            carFrame.buildFrame(title, 600, 400);
            carFrame.showFrame();
        });
        popupMenu.add(menuItemSee);
        if (this.controller.getAccountType() == AccountType.EMPLOYEE) {
            JMenuItem menuItemRemove = new JMenuItem("Remove car");
            menuItemRemove.addActionListener(event -> {
                int row = this.table.getSelectedRow();
                Car car = this.mapTableRowValues.get(row);
                this.controller.deleteCar(car);
                this.loadTableData();
                JOptionPane.showMessageDialog(null, "Car has been deleted !");
            });
            popupMenu.add(menuItemRemove);
        }
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
        for (Car car : this.controller.getCars()) {
            tableModel.addRow(car.convertToObjectTable());
            this.mapTableRowValues.put(row, car);
            row++;
        }
        this.table.setModel(tableModel);
        tableModel.fireTableDataChanged();
    }

    /**
     * Load the JFrame bottom Panel.
     */
    private void loadPageEndPanel() {
        this.pageEndPanel = new JPanel();
        this.pageEndPanel.setLayout(new FlowLayout());

        if (this.controller.getAccountType() == AccountType.CUSTOMER) {
            JButton btnRefresh = new JButton();
            btnRefresh.setText("Refresh table");
            btnRefresh.addActionListener(event -> {
                this.loadTableData();
            });
            this.pageEndPanel.add(btnRefresh);

            JButton btnReservations = new JButton();
            btnReservations.setText("My reservations");
            btnReservations.addActionListener(event -> {
                ReservationsFrameController reservationsFrameController = new ReservationsFrameController(this.controller.getUser());
                ReservationsFrame reservationsFrame = new ReservationsFrame(reservationsFrameController);
                reservationsFrame.buildFrame("My reservations", 800, 500);
                reservationsFrame.showFrame();
            });
            this.pageEndPanel.add(btnReservations);

            JButton btnUserData = new JButton();
            btnUserData.setText("My data");
            btnUserData.addActionListener(event -> {
                UserDataFrameController userDataFrameController = new UserDataFrameController(this.controller.getUser());
                UserDataFrame userDataFrame = new UserDataFrame(userDataFrameController);
                userDataFrame.buildFrame("My data", 400, 500);
                userDataFrame.showFrame();
            });
            this.pageEndPanel.add(btnUserData);

            JButton btnHelp = new JButton("Help");
            btnHelp.addActionListener(e -> {
                StringBuilder sb = new StringBuilder();
                sb.append("- Right click on a car to interact");
                JOptionPane.showMessageDialog(null, sb.toString());
            });
            this.pageEndPanel.add(btnHelp);
        }

        if (this.controller.getAccountType() == AccountType.EMPLOYEE) {
            JButton btnRefresh = new JButton();
            btnRefresh.setText("Refresh table");
            btnRefresh.addActionListener(event -> {
                this.loadTableData();
            });
            this.pageEndPanel.add(btnRefresh);

            JButton btnReservations = new JButton();
            btnReservations.setText("Manage reservations");
            btnReservations.addActionListener(event -> {
                ReservationsFrameController reservationsFrameController = new ReservationsFrameController(this.controller.getUser());
                ReservationsFrame reservationsFrame = new ReservationsFrame(reservationsFrameController);
                reservationsFrame.buildFrame("Manage reservations", 800, 500);
                reservationsFrame.showFrame();
            });
            this.pageEndPanel.add(btnReservations);

            JButton btnCarReturn = new JButton();
            btnCarReturn.setText("Car return");
            btnCarReturn.addActionListener(event -> {
                ReturnCarFrameController returnCarFrameController = new ReturnCarFrameController();
                ReturnCarFrame returnCarFrame = new ReturnCarFrame(returnCarFrameController);
                returnCarFrame.buildFrame("Return a car", 800, 150);
                returnCarFrame.showFrame();
            });
            this.pageEndPanel.add(btnCarReturn);

            JButton btnCarAdd = new JButton();
            btnCarAdd.setText("Add a car");
            btnCarAdd.addActionListener(event -> {
                AddCarFrameController addCarFrameController = new AddCarFrameController();
                AddCarFrame addCarFrame = new AddCarFrame(addCarFrameController);
                addCarFrame.buildFrame("Add a car", 800, 400);
                addCarFrame.showFrame();
            });
            this.pageEndPanel.add(btnCarAdd);

            JButton btnHelp = new JButton("Help");
            btnHelp.addActionListener(e -> {
                StringBuilder sb = new StringBuilder();
                sb.append("- Right click on a car to interact");
                JOptionPane.showMessageDialog(null, sb.toString());
            });
            this.pageEndPanel.add(btnHelp);
        }

        if (this.controller.getAccountType() == AccountType.ADMINISTRATOR) {
            JButton btnUsers = new JButton();
            btnUsers.setText("Manage users");
            btnUsers.addActionListener(event -> {
                ManageUsersFrameController manageUsersFrameController = new ManageUsersFrameController((Administrator) this.controller.getUser());
                ManageUsersFrame manageUsersFrame = new ManageUsersFrame(manageUsersFrameController);
                manageUsersFrame.buildFrame("Manage users", 800, 400);
                manageUsersFrame.showFrame();
            });
            this.pageEndPanel.add(btnUsers);
        }

        this.add(this.pageEndPanel, BorderLayout.PAGE_END);
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
        MainFrame mainFrame = (MainFrame) o;
        return Objects.equals(table, mainFrame.table) &&
                Objects.equals(mapTableRowValues, mainFrame.mapTableRowValues) &&
                Objects.equals(centerPanel, mainFrame.centerPanel) &&
                Objects.equals(pageEndPanel, mainFrame.pageEndPanel) &&
                Objects.equals(controller, mainFrame.controller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(table, mapTableRowValues, centerPanel, pageEndPanel, controller);
    }

    @Override
    public String toString() {
        return "MainFrame{" +
                "table=" + table +
                ", mapTableRowValues=" + mapTableRowValues +
                ", centerPanel=" + centerPanel +
                ", pageEndPanel=" + pageEndPanel +
                ", controller=" + controller +
                '}';
    }
}
