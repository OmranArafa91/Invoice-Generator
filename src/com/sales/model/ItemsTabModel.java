
package com.sales.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ItemsTabModel extends AbstractTableModel {

    private ArrayList<Item> items;
    private String[] cols = {"No.", "Item Name", "Item Price", "Count", "Item Total"};

    public ItemsTabModel(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
    
    
    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int col) {
        return cols[col];
    }
    
    @Override
    public Object getValueAt(int rowInd, int colInd) {
        Item item = items.get(rowInd);
        
        switch(colInd) {
            case 0: return item.getInv().getNum();
            case 1: return item.getItem();
            case 2: return item.getPrice();
            case 3: return item.getAmount();
            case 4: return item.getItemTotal();
            default : return "";
        }
    }
    
}
