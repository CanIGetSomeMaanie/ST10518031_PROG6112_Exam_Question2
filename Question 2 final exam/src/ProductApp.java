import java.awt.GridLayout;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ProductApp extends JFrame {

    private final String[] products = {"Cozy Nights Monarch", "Restonic Barbados", "Sealy Phuket"};

    private final JTextField txtCustomer = new JTextField(20);
    private final JComboBox<String> cmbProduct = new JComboBox<>(products);
    private final JTextField txtPrice = new JTextField(20);
    private final JButton btnProcess = new JButton("PROCESS");

    public ProductApp() {
        super("Product App");
        buildMenu();
        buildForm();

        // The button and the Process menu item use the same logic
        btnProcess.addActionListener(e -> processSale());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    // Creates the File and Tools menus
    private void buildMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        JMenu toolsMenu = new JMenu("Tools");
        JMenuItem processItem = new JMenuItem("Process");
        JMenuItem clearItem = new JMenuItem("Clear");
        processItem.addActionListener(e -> processSale());
        clearItem.addActionListener(e -> clearForm());
        toolsMenu.add(processItem);
        toolsMenu.add(clearItem);

        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        setJMenuBar(menuBar);
    }

    // Lays out the labels, text boxes, combo box and button
    private void buildForm() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel("Customer Name:"));
        panel.add(txtCustomer);
        panel.add(new JLabel("Select Product:"));
        panel.add(cmbProduct);
        panel.add(new JLabel("Enter Price:"));
        panel.add(txtPrice);
        panel.add(new JLabel(""));
        panel.add(btnProcess);
        add(panel);
    }

    // Captures the input, calculates, displays the message box and saves the file
    private void processSale() {
        String customer = txtCustomer.getText().trim();
        String product = (String) cmbProduct.getSelectedItem();
        double price;

        // Make sure the customer name was entered
        if (customer.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the customer name.");
            return;
        }

        // Make sure the price is a number
        try {
            price = Double.parseDouble(txtPrice.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid price.");
            return;
        }

        ProductCalculations calc = new ProductCalculations(price) {
            @Override
            public double GetVst() {
                return 0;
            }
        };

        // Build the output once and use it for the message box and the file
        String details = "Customer Name: " + customer + "\n"
                + "Product: " + product + "\n"
                + "VAT: R " + calc.GetVat() + "\n"
                + "COMMISSION: R " + calc.GetCommission() + "\n"
                + "TOTAL: R " + calc.GetTotal();

        JOptionPane.showMessageDialog(this, details);
        saveToFile(details);
    }

    // Saves the sale details to data.txt
    private void saveToFile(String details) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("data.txt"))) {
            writer.println("DATA LOG");
            writer.println("**********************************");
            writer.println(details);
            writer.println("**********************************");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
        }
    }

    // Clears the text boxes and sets the combo box selected index to 0
    private void clearForm() {
        txtCustomer.setText("");
        txtPrice.setText("");
        cmbProduct.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductApp().setVisible(true));
    }
}