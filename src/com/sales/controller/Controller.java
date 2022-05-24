package com.sales.controller;

import com.sales.model.Inv;
import com.sales.model.InvsTabModel;
import com.sales.model.Item;
import com.sales.model.ItemsTabModel;
import com.sales.view.InvDialog;
import com.sales.view.InvFrame;
import com.sales.view.ItemDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Controller implements ActionListener, ListSelectionListener {

    private InvFrame frame;
    private InvDialog invDialog;
    private ItemDialog itemDialog;

    public Controller(InvFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actCom = e.getActionCommand();
        System.out.println("Action: " + actCom);
        switch (actCom) {
            case "Load File":
                load();
                break;
            case "Save File":
                save();
                break;
            case "Create New Invoice":
                createNewInv();
                break;
            case "Delete Invoice":
                deleteInv();
                break;
            case "Create New Item":
                createNewItem();
                break;
            case "Delete Item":
                deleteItem();
                break;
            case "createInvoiceCancel":
                createInvCancel();
                break;
            case "createInvoiceOK":
                createInvOK();
                break;
            case "createLineOK":
                createItemOK();
                break;
            case "createLineCancel":
                createItemCancel();
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selIndex = frame.getInvoiceTable().getSelectedRow();
        if (selIndex != -1) {
            System.out.println("You have selected row: " + selIndex);
            Inv currInv = frame.getInvs().get(selIndex);
            frame.getInvoiceNumLabel().setText("" + currInv.getNum());
            frame.getInvoiceDateLabel().setText(currInv.getDate());
            frame.getCustomerNameLabel().setText(currInv.getCus());
            frame.getInvoiceTotalLabel().setText("" + currInv.getInvTotal());
            ItemsTabModel linesTableModel = new ItemsTabModel(currInv.getItems());
            frame.getLineTable().setModel(linesTableModel);
            linesTableModel.fireTableDataChanged();
        }
    }

    private void load() {
        JFileChooser fco = new JFileChooser();
        try {
            int open = fco.showOpenDialog(frame);
            if (open == JFileChooser.APPROVE_OPTION) {
                File cusList = fco.getSelectedFile();
                Path cusPath = Paths.get(cusList.getAbsolutePath());
                List<String> cusItems = Files.readAllLines(cusPath);
                System.out.println("Invoices have been read");
                
                ArrayList<Inv> invsArray = new ArrayList<>();
                for (String cusItem : cusItems) {
                    String[] cusVs = cusItem.split(",");
                    int invNm = Integer.parseInt(cusVs[0]);
                    String invDate = cusVs[1];
                    String customerName = cusVs[2];

                    Inv inv = new Inv(invNm, invDate, customerName);
                    invsArray.add(inv);
                }
                System.out.println("Check point");
                open = fco.showOpenDialog(frame);
                if (open == JFileChooser.APPROVE_OPTION) {
                    File itemList = fco.getSelectedFile();
                    Path itemPath = Paths.get(itemList.getAbsolutePath());
                    List<String> itemItems = Files.readAllLines(itemPath);
                    System.out.println("Lines have been read");
                    for (String itemItem : itemItems) {
                        String lineParts[] = itemItem.split(",");
                        int invNum = Integer.parseInt(lineParts[0]);
                        String itemNm = lineParts[1];
                        double itemCost = Double.parseDouble(lineParts[2]);
                        int amount = Integer.parseInt(lineParts[3]);
                        Inv id = null;
                        for (Inv inv : invsArray) {
                            if (inv.getNum() == invNum) {
                                id = inv;
                                break;
                            }
                        }

                        Item item = new Item(itemNm, itemCost, amount, id);
                        id.getItems().add(item);
                    }
                }
                frame.setInvs(invsArray);
                InvsTabModel invoicesTableModel = new InvsTabModel(invsArray);
                frame.setInvoicesTableModel(invoicesTableModel);
                frame.getInvoiceTable().setModel(invoicesTableModel);
                frame.getInvoicesTableModel().fireTableDataChanged();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void save() {
        ArrayList<Inv> invs = frame.getInvs();
        String cus = "";
        String items = "";
        for (Inv inv : invs) {
            String invCSV = inv.getCSV();
            cus += invCSV;
            cus += "\n";

            for (Item item : inv.getItems()) {
                String lineCSV = item.getCSV();
                items += lineCSV;
                items += "\n";
            }
        }
        System.out.println("Check point");
        try {
            JFileChooser fco = new JFileChooser();
            int save = fco.showSaveDialog(frame);
            if (save == JFileChooser.APPROVE_OPTION) {
                File cusList = fco.getSelectedFile();
                FileWriter cusFileWrite = new FileWriter(cusList);
                cusFileWrite.write(cus);
                cusFileWrite.flush();
                cusFileWrite.close();
                save = fco.showSaveDialog(frame);
                if (save == JFileChooser.APPROVE_OPTION) {
                    File itemList = fco.getSelectedFile();
                    FileWriter itemFileWrite = new FileWriter(itemList);
                    itemFileWrite.write(items);
                    itemFileWrite.flush();
                    itemFileWrite.close();
                }
            }
        } catch (Exception ex) {

        }
    }

    private void createNewInv() {           // create new invoice
        invDialog = new InvDialog(frame);
        invDialog.setVisible(true);
    }

    private void deleteInv() {
        int selectedRow = frame.getInvoiceTable().getSelectedRow();
        if (selectedRow != -1) {
            frame.getInvs().remove(selectedRow);
            frame.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createNewItem() {          // create new item
        itemDialog = new ItemDialog(frame);
        itemDialog.setVisible(true);
    }

    private void deleteItem() {             //Delete selected item
        int selectedRow = frame.getLineTable().getSelectedRow();

        if (selectedRow != -1) {
            ItemsTabModel linesTableModel = (ItemsTabModel) frame.getLineTable().getModel();
            linesTableModel.getItems().remove(selectedRow);
            linesTableModel.fireTableDataChanged();
            frame.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createInvCancel() {                // cancle creating new invoice
        invDialog.setVisible(false);
        invDialog.dispose();
        invDialog = null;
    }

    private void createInvOK() {                    // cerate new invoice ok button
        String date = invDialog.getInvDateField().getText();
        String cus = invDialog.getCustNameField().getText();
        int num = frame.getNextInvoiceNum();

        Inv invoice = new Inv(num, date, cus);
        frame.getInvs().add(invoice);
        frame.getInvoicesTableModel().fireTableDataChanged();
        invDialog.setVisible(false);
        invDialog.dispose();
        invDialog = null;
    }

    private void createItemOK() {           // create new item ok button
        String item = itemDialog.getItemNameField().getText();
        String countStr = itemDialog.getItemCountField().getText();
        String priceStr = itemDialog.getItemPriceField().getText();
        int amount = Integer.parseInt(countStr);
        double cost = Double.parseDouble(priceStr);
        int selInv = frame.getInvoiceTable().getSelectedRow();
        if (selInv != -1) {
            Inv inv = frame.getInvs().get(selInv);
            Item line = new Item(item, cost, amount, inv);
            inv.getItems().add(line);
            ItemsTabModel linesTableModel = (ItemsTabModel) frame.getLineTable().getModel();
            linesTableModel.fireTableDataChanged();
            frame.getInvoicesTableModel().fireTableDataChanged();
        }
        itemDialog.setVisible(false);
        itemDialog.dispose();
        itemDialog = null;
    }

    private void createItemCancel() {           // cancel create new item button
        itemDialog.setVisible(false);
        itemDialog.dispose();
        itemDialog = null;
    }

}
