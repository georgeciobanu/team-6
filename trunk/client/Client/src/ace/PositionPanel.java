package ace;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.table.AbstractTableModel;



public class PositionPanel extends JPanel {
    
    /** Creates a new instance of TransactionPanel */
    public PositionPanel() {
        setLayout( new BoxLayout ( this, BoxLayout.PAGE_AXIS ) );        
        
        add( new JLabel("Position Management") );
        JTable positionTable = new JTable(new positionTableModel());
        JScrollPane scrollPane1 = new JScrollPane(positionTable);
        add(scrollPane1);
        
         add( new JLabel("Orders") );
        JTable ordersTable = new JTable(new ordersTableModel());
        JScrollPane scrollPane2 = new JScrollPane(ordersTable);
        add(scrollPane2);
        
        

               
    }
    
    class positionTableModel extends AbstractTableModel {
        private String[] columnNames = {"Currency",
                                        "Net",
                                        "Average Rate",
                                        "Reval Rate",
                                        "P/L ($)",
                                        "Limit",
                                        "Stop"};
        private Object[][] data = {
            {"AUD/CAD", 45,
             0.02456, 0.86055, 2456, 0.0456, 0.450},
            {"AUD/JPY", 27,
             0.012340, 0.96350, 24.00, 12.0000, 0.89900},
            {"AUD/NZD", 6,
             0.000000, 0.70999, 0.00, 0.00250, 0.08555},
            {"AUD/USD", 6,
             0.0547, 1.11786, 3.00, 0.34670, 0.12340},
            {"CAD/JPY", 5,
             0.04340, 155555.86055, 0.00, 0.8700, 0.23800},
             {"CAD/USA", 12,
             10.300, 1086055, 456.00, 0.0400, 4357.00},
             {"CAD/NZD", 3,
             0.666000, 0.086055, 5.20, 0.04600, 56.54000},
             {"CAD/AUD", 5,
             2.00000, 0.76088, 0.20, 0.01234, 21.00},
             {"USD/JPY", 3,
             0.000000, 2.55, 5.60, 0.04440, 0.00012},
             {"USD/NZD", 5,
             8.000000, 1.860, 1, 200, 120000},
             {"USD/AUD", 0,
             6.200000, 0.5055, 1.0, 0.00999, 0.57400},
             {"USD/CAD", 2,
             0.40002, 0.000001, 50, 10000, 0.098760},
             {"JPY/CAD", 87,
             0.000001, 0.00055, 0.666, 0.54000, 0.06680},
             {"JPY/USD", 0,
             0.0023, 0.85600, 0.00, 0.657, 0.2400},
             {"JPY/NZD", 8612,
             33.0000, 0.95011, 0.33, 0.5500, 0.4000},
             {"NZD/USD", 123,
             13400, 1.60505, 4.0, 0.07874,11.000},
        };

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        public boolean isCellEditable(int row, int col) {
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        }

        public void setValueAt(Object value, int row, int col) {
           {
                System.out.println("Setting value at " + row + "," + col
                                   + " to " + value
                                   + " (an instance of "
                                   + value.getClass() + ")");
            }

            data[row][col] = value;

            {
                System.out.println("New value of data:");
                printDebugData();
            }
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j=0; j < numCols; j++) {
                    System.out.print("  " + data[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }
    
    class ordersTableModel extends AbstractTableModel {
        private String[] columnNames = {"Ref",
                                        "Buy/Sell",
                                        "Ccy Pair",
                                        "Deal Amount",
                                        "Basis",
                                        "Order Rate",
                                        "Live Price",
                                        "Points Diff",
                                        "Conf",
                                        "Expir."};
        private Object[][] data = {
            {7, "Buy",
             "USD/CAD", 0.86055, "Limit", 0.00002, 0.000001, 2, 12, 42},
            {5, "Buy",
             "AUD/JPY", 0.0556, "Limit", 1.00000, 0.96350, 123, 1, 4},
            {12, "Sell",
             "AUD/NZD", 0.865, "Limit", 0.80000, 0.70999, 4, 43, 2},
            {1, "Sell",
             "AUD/USD", 0.8995, "Limit", 1.09000, 1.11786, 51, 47, 1},
            {2, "Sell",
             "CAD/JPY", 0.675, "Stop Loss", 99999.00000, 155555.86055, 1, 6, 133},
             {3, "Sell",
             "USD/AUD", 0.165, "Limit", 0.600000, 0.5055, 15, 4, 600},
             {9, "Sell",
             "JPY/NZD", 0.98445, "Stop Loss", 0.940000, 0.95011, 6, 687, 15},
             {76, "Buy",
             "CAD/USA", 0.85355, "Limit", 1.00000, 1086055, 2, 21, 9},
             {4, "Buy",
             "CAD/JPY", 1.86055, "Stop Loss", 160000.00000, 155555.86055, 0, 654, 7},
             {65, "Buy",
             "CAD/AUD", 2.055, "Limit", 0.770000, 0.76088, 7, 96, 2},
             {21, "Buy",
             "CAD/AUD", 0.95055, "Limit", 0.80000, 0.76088, 2, 879, 13},
             {22, "Sell",
             "JPY/NZD", 0.86425, "Stop Loss", 37.95011, 0.95011, 34, 156, 90},
             {77, "Sell",
             "CAD/USA", 1.06335, "Stop Loss", 1086055, 1086055, 0, 458, 6},
             {96, "Buy",
             "USD/JPY", 0.76055, "Limit", 2.49, 2.55, 23, 478, 1},
             {99, "Sell",
             "USD/CAD", 0.81475, "Limit", 0.0000011, 0.000001, 0.000001, 236, 10},
             {100, "Sell",
             "USD/JPY", 0.8609, "Limit", 2.555, 2.55, 2, 0.005, 24},
        };

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        public boolean isCellEditable(int row, int col) {
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        }

        public void setValueAt(Object value, int row, int col) {
            {
                System.out.println("Setting value at " + row + "," + col
                                   + " to " + value
                                   + " (an instance of "
                                   + value.getClass() + ")");
            }

            data[row][col] = value;
            fireTableCellUpdated(row, col);

            {
                System.out.println("New value of data:");
                printDebugData();
            }
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j=0; j < numCols; j++) {
                    System.out.print("  " + data[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }
    
}
