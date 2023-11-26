package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.PageSettings;
import teamx.app.backend.models.Transaction;
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

    @PostMapping("/getAllTransactionsByProductIdPaginated/{id}")
    public ResponseEntity<Page<TransactionDTO>> getTransactionsByProductID(
            @PathVariable Long id,
            @RequestBody PageSettings pageSettings){
        try {
            Page<TransactionDTO> transactions = transactionService.getAllTransactionsByProductPaginated(
                    id, pageSettings);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
