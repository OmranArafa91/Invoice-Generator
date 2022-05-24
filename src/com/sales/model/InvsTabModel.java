
package com.sales.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class InvsTabModel extends AbstractTableModel {
    private ArrayList<Inv> invs;
    private String[] cols = {"No.", "Date", "Customer", "Total"};
    
    public InvsTabModel(ArrayList<Inv> invs) {
        this.invs = invs;
    }
    
    @Override
    public int getRowCount() {
        return invs.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int column) {
        return cols[column];
    }
    
    @Override
    public Object getValueAt(int rowInd, int columnInd) {
        Inv inv = invs.get(rowInd);
        
        switch (columnInd) {
            case 0: return inv.getNum();
            case 1: return inv.getDate();
            case 2: return inv.getCus();
            case 3: return inv.getInvTotal();
            default : return "";
        }
    }
}
