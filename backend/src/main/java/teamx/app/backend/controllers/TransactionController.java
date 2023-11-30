package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.dto.TransactionDTO;
import teamx.app.backend.services.TransactionService;

import java.util.List;

/**
 * The TransactionController class is a REST controller that handles transaction-related operations.
 *
 * @author Junior Javier Brito Perez
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Retrieves all transactions for a given product ID.
     *
     * @param id the ID of the product
     * @return object containing a list of TransactionDTO objects
     * @throws ResponseStatusException if an error occurs while getting the transactions
     */
    @GetMapping("/product/{id}")
    public ResponseEntity<List<TransactionDTO>> getAllByProductId(@PathVariable Long id) {
        try {
            List<TransactionDTO> transactions = transactionService.getAllByProduct(id);

            if (transactions.isEmpty()) {
                return new ResponseEntity<>(transactions, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Retrieves all transactions for a given warehouse ID.
     *
     * @param id the ID of the warehouse
     * @return object containing a list of TransactionDTO objects
     * @throws ResponseStatusException if an error occurs while getting the transactions
     */
    @GetMapping("/warehouse/{id}")
    public ResponseEntity<List<TransactionDTO>> getAllByWarehouseId(@PathVariable Long id) {
        try {
            List<TransactionDTO> transactions = transactionService.getAllByWarehouse(id);

            if (transactions.isEmpty()) {
                return new ResponseEntity<>(transactions, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
