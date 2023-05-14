import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main extends JFrame {
    private JPanel panel;
    private JLabel label;
    private JTextField textField;
    private JButton button;
    private JTable table;
    private JLabel resultLabel;
    private JButton resultButton;

    public Main() {
        // Set properties of the JFrame
        setTitle("Matrix Operations");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a new JPanel and set its layout
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a label and a text field for the input
        label = new JLabel("Enter the value of n (<=20):");
        textField = new JTextField(10);

        // Create a button for the input
        button = new JButton("Enter");
        button.addActionListener(e -> {
            processInput();
        });

        // Add the label, text field, and button to the panel
        JPanel inputPanel = new JPanel();
        inputPanel.add(label);
        inputPanel.add(textField);
        inputPanel.add(button);
        panel.add(inputPanel, BorderLayout.NORTH);

        // Create a JTable for displaying the matrix
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Create a label and a button for displaying the result
        resultLabel = new JLabel("");
        resultButton = new JButton("Process");
        resultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processResult();
            }
        });

        // Add the result label and button to the panel
        JPanel resultPanel = new JPanel();
        resultPanel.add(resultLabel);
        resultPanel.add(resultButton);
        panel.add(resultPanel, BorderLayout.SOUTH);

        // Add the panel to the JFrame
        add(panel);

    }

    private void processInput() {
        int n = Integer.parseInt(textField.getText());
        // Process input
    }

    private void processResult() {
        int n = Integer.parseInt(textField.getText());
        // Process input
        try {
            File inputFile = new File("input.txt");
            Scanner input = new Scanner(inputFile);
            int[][] A = new int[n][n];
            int minIndex = 0;
            int minElement = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    A[i][j] = input.nextInt();
                    if (A[i][j] < minElement) {
                        minElement = A[i][j];
                        minIndex = j;
                    }
                }
            }
            input.close();
            // Shift the columns of the matrix so that the column with the minimum element is first
            for (int i = 0; i < n; i++) {
                int[] temp = new int[n];
                for (int j = 0; j < n; j++) {
                    temp[j] = A[i][(j + minIndex) % n];
                }
                A[i] = temp;
            }
            // Populate the JTable with data from the file
            String[] columnNames = new String         [n];
            for (int i = 0; i < n; i++) {
                columnNames[i] = "Column " + (i + 1);
            }
            DefaultTableModel model = new DefaultTableModel(columnNames, n);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    model.setValueAt(A[i][j], i, j);
                }
            }
            table.setModel(model);
            // Calculate the determinant of the matrix
            int det = calculateDeterminant(A);
            // Set the result label to display the determinant
            resultLabel.setText("Determinant = " + det);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private int calculateDeterminant(int[][] A) {
        int n = A.length;
        if (n == 1) {
            return A[0][0];
        }
        if (n == 2) {
            return A[0][0] * A[1][1] - A[0][1] * A[1][0];
        }
        int det = 0;
        for (int i = 0; i < n; i++) {
            int[][] submatrix = new int[n - 1][n - 1];
            for (int j = 1; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (k < i) {
                        submatrix[j - 1][k] = A[j][k];
                    } else if (k > i) {
                        submatrix[j - 1][k - 1] = A[j][k];
                    }
                }
            }
            det += Math.pow(-1, i) * A[0][i] * calculateDeterminant(submatrix);
        }
        return det;
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setVisible(true);
    }
}