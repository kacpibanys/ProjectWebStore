package com.example.shop.file;

import com.example.shop.invoice.Invoice;
import com.example.shop.order.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileSavingService {
    private static final String INVOICES_DIR = "invoices";
    private static final String ORDERS_FILE = "orders_log.csv";

    public FileSavingService() {
        try {
            Files.createDirectories(Paths.get(INVOICES_DIR));
        } catch (IOException e) {
            System.err.println("Error: couldnt create the invoices folder: " + e.getMessage());
        }
    }

    public void saveInvoiceAsText(Invoice invoice) {
        String safeFileName = invoice.invoiceNumber().replace("/", "_") + ".txt";
        Path path = Paths.get(INVOICES_DIR, safeFileName);

        try {
            Files.writeString(path, invoice.toString());
        } catch (IOException e) {
            throw new RuntimeException("Error while saving the invoice: " + e.getMessage(), e);
        }
    }

    public void appendOrderToLog(Order order) {
        Path path = Paths.get(ORDERS_FILE);
        String logLine = String.format("%d;%s;%s;%s;%s\n",
                order.getId(),
                order.getCustomer().getEmail(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getCreatedAt());

        try {
            if (!Files.exists(path)) {
                Files.writeString(path, "ID;CustomerEmail;TotalPLN;Status;Date\n", StandardOpenOption.CREATE);
            }
            Files.writeString(path, logLine, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Error while updating the register: " + e.getMessage(), e);
        }
    }
}
