package teamx.app.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.Transaction;
import teamx.app.backend.repositories.TransactionRepository;

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
}
