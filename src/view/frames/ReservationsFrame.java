package view.frames;

import entities.Customer;
import entities.Employee;
import entities.Reservation;
import entities.references.ReservationStatus;
import model.controllers.ReservationsFrameController;
import view.CustomFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReservationsFrame extends JFrame implements CustomFrame {

    private JPanel centerPanel;
    private JPanel pageEndPanel;
    private JTable table;

    private Map<Integer, Reservation> mapTableRowValues;

    private ReservationsFrameController controller;

    public ReservationsFrame(ReservationsFrameController controller) {
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
        this.loadPageEndPanel();
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
     * Create the reservations table.
     */
    private void createTable() {
        String colNames[] = {"CREATION DATE", "PRICE", "CAR", "STATUS", "START AT", "END AT"};
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
        if (Customer.class.equals(this.controller.getUser().getClass())) {
            JMenuItem menuItemCancel = new JMenuItem("Cancel reservation");
            menuItemCancel.addActionListener(event -> {
                int row = this.table.getSelectedRow();
                Reservation reservation = this.mapTableRowValues.get(row);
                if (reservation.getStatus() == ReservationStatus.CREATED || reservation.getStatus() == ReservationStatus.ACCEPTED) {
                    reservation.setStatus(ReservationStatus.CANCELED);
                    controller.updateReservation(reservation);
                    loadTableData();
                }
            });
            popupMenu.add(menuItemCancel);
        }
        if (Employee.class.equals(this.controller.getUser().getClass())) {
            JMenuItem menuItemAccept = new JMenuItem("Accept reservation");
            menuItemAccept.addActionListener(event -> {
                int row = this.table.getSelectedRow();
                Reservation reservation = this.mapTableRowValues.get(row);
                if (reservation.getStatus() == ReservationStatus.CREATED) {
                    reservation.setStatus(ReservationStatus.ACCEPTED);
                    controller.updateReservation(reservation);
                    loadTableData();
                } else {
                    JOptionPane.showMessageDialog(null, "You can't to this action");
                }
            });
            popupMenu.add(menuItemAccept);
            JMenuItem menuItemRefuse = new JMenuItem("Refuse reservation");
            menuItemRefuse.addActionListener(event -> {
                int row = this.table.getSelectedRow();
                Reservation reservation = this.mapTableRowValues.get(row);
                if (reservation.getStatus() == ReservationStatus.CREATED) {
                    reservation.setStatus(ReservationStatus.REFUSED);
                    controller.updateReservation(reservation);
                    loadTableData();
                } else {
                    JOptionPane.showMessageDialog(null, "You can't to this action");
                }
            });
            popupMenu.add(menuItemRefuse);
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
        for (Reservation r : this.controller.getUserReservations()) {
            tableModel.addRow(r.convertToObjectTable());
            this.mapTableRowValues.put(row, r);
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
        this.add(this.pageEndPanel, BorderLayout.PAGE_END);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(event -> {
            hideFrame();
        });

        JButton btnHelp = new JButton("Help");
        btnHelp.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("- Right click on a reservation to interact");
            JOptionPane.showMessageDialog(null, sb.toString());
        });

        this.pageEndPanel.add(btnBack);
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
        ReservationsFrame that = (ReservationsFrame) o;
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
        return "ReservationsFrame{" +
                "centerPanel=" + centerPanel +
                ", pageEndPanel=" + pageEndPanel +
                ", table=" + table +
                ", mapTableRowValues=" + mapTableRowValues +
                ", controller=" + controller +
                '}';
    }
}
