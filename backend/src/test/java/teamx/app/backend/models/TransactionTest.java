package teamx.app.backend.models;

import org.junit.jupiter.api.Test;
import teamx.app.backend.utils.DTO.TransactionDTO;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void testIsPositiveTransaction() {
        Transaction transaction = new Transaction();

        // Test for a positive transaction scenario
        transaction.setQuantity(10);
        transaction.setTransactionType(Transaction.Type.INVENTORY_ORDER);
        assertTrue(transaction.isPositiveTransaction(), "INVENTORY_ORDER with positive quantity should be positive transaction");

        // Test for a negative transaction scenario
        transaction.setTransactionType(Transaction.Type.PROJECT_MATERIAL);
        assertFalse(transaction.isPositiveTransaction(), "PROJECT_MATERIAL with positive quantity should not be positive transaction");

        // Test for zero quantity
        transaction.setQuantity(0);
        assertFalse(transaction.isPositiveTransaction(), "Zero quantity should not be considered a positive transaction");
    }

    @Test
    void testToDTO() {
        Transaction transaction = Transaction.builder()
                .id(1L)
                .quantity(5)
                .transactionType(Transaction.Type.PROJECT_MATERIAL)
                .transactionDate(new Date(System.currentTimeMillis()))
                .build();

        TransactionDTO dto = transaction.toDTO();

        assertNotNull(dto, "DTO should not be null");
        assertEquals(transaction.getId(), dto.getId(), "ID should match");
        assertEquals(transaction.getQuantity(), dto.getQuantity(), "Quantity should match");
        assertEquals(transaction.getTransactionType().toString(), dto.getTransactionType(), "Transaction type should match");
        assertEquals(transaction.getTransactionDate(), dto.getTransactionDate(), "Transaction date should match");
    }
}
