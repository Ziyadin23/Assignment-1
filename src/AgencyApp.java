import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AgencyApp extends JFrame {
    private final RealEstateAgencyDAO dao = new RealEstateAgencyDAO();

    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Name", "Address"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable table = new JTable(model);

    private final JTextField nameField = new JTextField(16);
    private final JTextField addressField = new JTextField(16);

    private final JButton addBtn = new JButton("Add");
    private final JButton updateBtn = new JButton("Update Selected");
    private final JButton deleteBtn = new JButton("Delete Selected");
    private final JButton refreshBtn = new JButton("Refresh");

    public AgencyApp() {
        super("Real Estate Agency - Simple UI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(640, 420);
        setLocationRelativeTo(null);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                nameField.setText(String.valueOf(model.getValueAt(r, 1)));
                addressField.setText(String.valueOf(model.getValueAt(r, 2)));
            }
        });

        JPanel input = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4,4,4,4);
        gc.gridx = 0; gc.gridy = 0; gc.anchor = GridBagConstraints.LINE_END;
        input.add(new JLabel("Name:"), gc);
        gc.gridx = 1; gc.anchor = GridBagConstraints.LINE_START;
        input.add(nameField, gc);

        gc.gridx = 0; gc.gridy = 1; gc.anchor = GridBagConstraints.LINE_END;
        input.add(new JLabel("Address:"), gc);
        gc.gridx = 1; gc.anchor = GridBagConstraints.LINE_START;
        input.add(addressField, gc);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        buttons.add(addBtn);
        buttons.add(updateBtn);
        buttons.add(deleteBtn);
        buttons.add(refreshBtn);

        JPanel top = new JPanel(new BorderLayout(8,8));
        top.add(input, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.SOUTH);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        wireActions();
        reloadTable();
    }

    private void wireActions() {
        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String addr = addressField.getText().trim();
            if (name.isEmpty() || addr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and address are required.");
                return;
            }
            try {
                dao.insertAgency(name, addr);
                clearInputs();
                reloadTable();
            } catch (Exception ex) {
                showError(ex);
            }
        });

        updateBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r < 0) {
                JOptionPane.showMessageDialog(this, "Select a row first.");
                return;
            }
            int id = Integer.parseInt(String.valueOf(model.getValueAt(r, 0)));
            String name = nameField.getText().trim();
            String addr = addressField.getText().trim();
            if (name.isEmpty() || addr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and address are required.");
                return;
            }
            try {
                dao.updateAgency(id, name, addr);
                reloadTable();
            } catch (Exception ex) {
                showError(ex);
            }
        });

        deleteBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r < 0) {
                JOptionPane.showMessageDialog(this, "Select a row first.");
                return;
            }
            int id = Integer.parseInt(String.valueOf(model.getValueAt(r, 0)));
            int confirm = JOptionPane.showConfirmDialog(this, "Delete agency id " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            try {
                dao.deleteAgency(id);
                clearInputs();
                reloadTable();
            } catch (Exception ex) {
                showError(ex);
            }
        });

        refreshBtn.addActionListener(e -> reloadTable());
    }

    private void reloadTable() {
        try {
            List<Agency> agencies = dao.listAgencies();
            model.setRowCount(0);
            for (Agency a : agencies) {
                model.addRow(new Object[]{a.getId(), a.getName(), a.getAddress()});
            }
        } catch (Exception ex) {
            showError(ex);
        }
    }
    // afaf
    private void clearInputs() {
        nameField.setText("");
        addressField.setText("");
    }

    private void showError(Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AgencyApp().setVisible(true));
    }
}