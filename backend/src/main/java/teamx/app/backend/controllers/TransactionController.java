package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.dto.TransactionDTO;
import teamx.app.backend.services.TransactionService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByProductID(@PathVariable Long id) {
        try {
            List<TransactionDTO> transactions = transactionService.getAllByProduct(id);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/warehouse/{id}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByWarehouseID(@PathVariable Long id) {
        try {
            List<TransactionDTO> transactions = transactionService.getAllByWarehouse(id);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
