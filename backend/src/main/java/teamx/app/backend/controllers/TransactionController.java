package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamx.app.backend.services.TransactionService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
//
//    @GetMapping(path = "/getAllTransactions")
//    public ResponseEntity<List<Transaction>> getAllTransactions() {
//        try {
//            List<Transaction> transactions = transactionService.getAllTransactions();
//            if (transactions.isEmpty()) {
//                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No transactions found");
//            }
//            return new ResponseEntity<>(transactions, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving transactions");
//        }
//    }

//    @GetMapping(path = "/getTransactionByProjectId/{projectId}")
//    public ResponseEntity<List<Transaction>> getTransactionsByProjectId(@PathVariable Long projectId) {
//        try {
//            List<Transaction> transactions = transactionService.getTransactionsByProjectId(projectId);
//            if (transactions.isEmpty()) {
//                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No transactions found");
//            }
//            return new ResponseEntity<>(transactions, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving transactions");
//        }
//    }
//
//    @GetMapping(path = "/getTransactionByWarehouseId/{warehouseId}")
//    public ResponseEntity<List<Transaction>> getTransactionsByWarehouseId(@PathVariable Long warehouseId) {
//        try {
//            List<Transaction> transactions = transactionService.getTransactionsByWarehouseId(warehouseId);
//            if (transactions.isEmpty()) {
//                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No transactions found");
//            }
//            return new ResponseEntity<>(transactions, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving transactions");
//        }
//    }
//
//    @GetMapping(path = "/getTransactionsByProductId/{productId}")
//    public ResponseEntity<List<Transaction>> getTransactionsByProductId(@PathVariable Long productId) {
//        try {
//            List<Transaction> transactions = transactionService.getTransactionsByProductId(productId);
//            if (transactions.isEmpty()) {
//                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No transactions found");
//            }
//            return new ResponseEntity<>(transactions, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving transactions");
//        }
//    }

//    @GetMapping(path = "/getCurrentStock/{warehouseId}/{productId}")
//    public ResponseEntity<Integer> getCurrentStock(@PathVariable Long productId, @PathVariable Long warehouseId) {
//        try {
//
//            Integer stock = transactionService.getCurrentStock(warehouseId, productId);
//            return new ResponseEntity<>(stock, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving transactions");
//        }
//    }
}
