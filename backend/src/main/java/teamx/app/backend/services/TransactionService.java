package teamx.app.backend.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamx.app.backend.models.Transaction;
import teamx.app.backend.models.Warehouse;
import teamx.app.backend.models.dto.TransactionDTO;
import teamx.app.backend.repositories.TransactionRepository;

import java.sql.Date;
import java.util.List;

@Service
public class TransactionService {
    TransactionRepository transactionRepository;
    ProductService productService;
    WarehouseService warehouseService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, ProductService productService, WarehouseService warehouseService) {
        this.transactionRepository = transactionRepository;
        this.productService = productService;
        this.warehouseService = warehouseService;
    }

    private static int getCurrentStock(List<Transaction> productTransactions) {
        int currentStock = 0;
        for (Transaction transaction : productTransactions) {

            if (transaction.getTransactionType() == Transaction.Type.ORDER ||
                    (transaction.getTransactionType() == Transaction.Type.ADJUSTMENT ||
                            transaction.getTransactionType() == Transaction.Type.RETURN ||
                            transaction.getTransactionType() == Transaction.Type.OTHER) &&
                            transaction.getQuantity() > 0) {
                currentStock += transaction.getQuantity();
            } else {
                currentStock -= transaction.getQuantity();
            }
        }
        return currentStock;
    }

    public List<TransactionDTO> getAllByProduct(@NonNull Long productId) {
        return transactionRepository.getAllByProduct(productService.getProductById(productId))
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<TransactionDTO> getAllByWarehouse(@NonNull Long warehouseId) {
        return transactionRepository.getAllByWarehouse(warehouseService.getWarehouseById(warehouseId))
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public int getProductCurrentStock(Long warehouseId, Long productId) {
        Warehouse warehouse = warehouseId != null ? warehouseService.getWarehouseById(warehouseId) : null;
        List<Transaction> productTransactions = warehouse == null ?
                transactionRepository.getAllByProductAndTransactionDateBefore(
                        productService.getProductById(productId),
                        new Date(System.currentTimeMillis())
                )
                :
                transactionRepository.getAllByWarehouseAndProductAndTransactionDateBefore(
                        warehouse,
                        productService.getProductById(productId),
                        new Date(System.currentTimeMillis())
                );
        return getCurrentStock(productTransactions);
    }

    private TransactionDTO convertToDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setProductId(transaction.getProduct().getId());
        transactionDTO.setQuantity(transaction.getQuantity());
        transactionDTO.setTransactionDate(transaction.getTransactionDate());
        transactionDTO.setTransactionType(transaction.getTransactionType().toString());

        Long warehouseId = transaction.getWarehouse() != null ? transaction.getWarehouse().getId() : null;
        transactionDTO.setWarehouseId(warehouseId);
        transactionDTO.setTransferFromWarehouseId(transaction.getTransferFrom() != null ?
                transaction.getTransferFrom().getId() : null);
        transactionDTO.setProjectId(transaction.getProject() != null ? transaction.getProject().getId() : null);
        return transactionDTO;
    }
}