package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamx.app.backend.utils.DTO.TransactionDTO;
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
     * Retrieves all transactions associated with the given product id.
     *
     * @param id The id of the product.
     * @return A ResponseEntity object containing a list of TransactionDTO objects.
     */
    @GetMapping("/product/{id}")
    public ResponseEntity<List<TransactionDTO>> getAllByProductId(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.findAllByProduct(id));
    }

    /**
     * Retrieves all transactions associated with the given warehouse id.
     *
     * @param id The id of the warehouse.
     * @return A ResponseEntity object containing a list of TransactionDTO objects.
     */
    @GetMapping("/warehouse/{id}")
    public ResponseEntity<List<TransactionDTO>> getAllByWarehouseId(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.findAllByWarehouse(id));
    }
}
