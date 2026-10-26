package com.example.shop.file;

import com.example.shop.invoice.Invoice;
import com.example.shop.order.Order;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
@Slf4j
public class FileSavingService {
    private final Path invoicesDirectory;
    private final Path ordersLogFileDirectory;

    public FileSavingService(String baseDirectoryPath) {
        Path baseDir = Paths.get(baseDirectoryPath);
        this.invoicesDirectory = baseDir.resolve("invoices");
        this.ordersLogFileDirectory = baseDir.resolve("orders_log.csv");
        try {
            Files.createDirectories(invoicesDirectory);
        } catch (IOException e) {
            log.error("Error: couldn't create the invoices folder", e);
        }
    }

    public void saveInvoiceAsText(Invoice invoice) {
        String safeFileName = invoice.invoiceNumber().replace("/", "_") + ".txt";
        Path path = invoicesDirectory.resolve(safeFileName);
        log.info("Saving invoice file: {}", invoice.invoiceNumber());
        try {
            Files.writeString(path, invoice.toString());
        } catch (IOException e) {
            log.error("Error: couldn't save the invoice file", e);
            throw new RuntimeException("Error while saving the invoice: " + e.getMessage(), e);
        }
    }

    public synchronized void appendOrderToLog(Order order) {
        String logLine = String.format("%d;%s;%s;%s;%s\n",
                order.getId(),
                order.getCustomer().getEmail(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getCreatedAt());
        log.info("Saving order of id: {} for a client: {}", order.getId(), order.getCustomer().getLastName());
        try {
            if (!Files.exists(ordersLogFileDirectory)) {
                Files.writeString(ordersLogFileDirectory, "ID;CustomerEmail;TotalPLN;Status;Date\n", StandardOpenOption.CREATE);
                log.info("Order of id: {} for a client: {} created successfully", order.getId(), order.getCustomer().getLastName());
            }
            Files.writeString(ordersLogFileDirectory, logLine, StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error("Error: couldn't save the order log file", e);
            throw new RuntimeException("Error while updating the register: " + e.getMessage(), e);
        }
    }
}
