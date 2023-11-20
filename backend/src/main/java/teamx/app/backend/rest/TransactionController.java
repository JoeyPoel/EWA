package teamx.app.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.Transaction;
import teamx.app.backend.repositories.TransactionRepository;

import java.sql.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/transactions")
public class TransactionController {
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping(path = "/getAllTransactions")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        try {
            List<Transaction> transactions = transactionRepository.findAll();
            if (transactions.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No transactions found");
            }
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving transactions");
        }
    }

    @GetMapping(path = "/getTransactionByProjectId/{projectId}")
    public ResponseEntity<List<Transaction>> getTransactionsByProjectId(@PathVariable Long projectId) {
        try {
            List<Transaction> transactions = transactionRepository.getTransactionsByProjectId(projectId);
            if (transactions.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No transactions found");
            }
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving transactions");
        }
    }

    @GetMapping(path = "/getTransactionByWarehouseId/{warehouseId}")
    public ResponseEntity<List<Transaction>> getTransactionsByWarehouseId(@PathVariable Long warehouseId) {
        try {
            List<Transaction> transactions = transactionRepository.getTransactionsByWarehouseId(warehouseId);
            if (transactions.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No transactions found");
            }
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving transactions");
        }
    }

    @GetMapping(path = "/getTransactionsByProductId/{productId}")
    public ResponseEntity<List<Transaction>> getTransactionsByProductId(@PathVariable Long productId) {
        try {
            List<Transaction> transactions = transactionRepository.getTransactionsByProductId(productId);
            if (transactions.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No transactions found");
            }
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving transactions");
        }
    }

    @GetMapping(path = "/getCurrentStock/{warehouseId}/{productId}")
    public ResponseEntity<Integer> getCurrentStock(@PathVariable Long productId, @PathVariable Long warehouseId) {
        try {
            List<Transaction> transactions = transactionRepository.getTransactionsByProductIdAndWarehouseIdAndTransactionDateBefore(productId, warehouseId, new Date(System.currentTimeMillis()));
            if (transactions.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No transactions found");
            }
            int stock = 0;
            for (Transaction transaction : transactions) {
                if (transaction.getOrder() != null) {
                    stock += transaction.getQuantity();
                } else if (transaction.getTransferFrom() != null) {
                    stock += transaction.getQuantity();
                } else {
                    stock -= transaction.getQuantity();
                }
            }
            return new ResponseEntity<>(stock, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving transactions");
        }
    }
}
