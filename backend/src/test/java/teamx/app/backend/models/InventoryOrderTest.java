package teamx.app.backend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import teamx.app.backend.utils.DTO.OrderDTO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryOrderTest {

    private InventoryOrder inventoryOrder;
    private Warehouse warehouse;
    private User orderedBy;
    private List<Transaction> transactions;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
        orderedBy = new User();

        transactions = new ArrayList<>();
        Product product1 = new Product();
        Product product2 = new Product();
        Transaction transaction1 = new Transaction(1L, 5, Transaction.Type.INVENTORY_ORDER, product1, new Date(System.currentTimeMillis()), warehouse, null, null, null);
        Transaction transaction2 = new Transaction(2L, 3, Transaction.Type.INVENTORY_ORDER, product2, new Date(System.currentTimeMillis()), warehouse, null, null, null);

        transactions.add(transaction1);
        transactions.add(transaction2);

        inventoryOrder = new InventoryOrder(1L, "Sample Order", false, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), warehouse, orderedBy, transactions);
    }

    @Test
    void testInventoryOrderGettersAndSetters() {
        assertEquals(1L, inventoryOrder.getId());
        assertEquals("Sample Order", inventoryOrder.getDescription());
        assertFalse(inventoryOrder.isDelivered());
        assertNotNull(inventoryOrder.getOrderDate());
        assertNotNull(inventoryOrder.getDeliveryDate());
        assertEquals(warehouse, inventoryOrder.getWarehouse());
        assertEquals(orderedBy, inventoryOrder.getOrderedBy());
        assertEquals(transactions, inventoryOrder.getTransactions());
    }

    @Test
    void testToDTO() {
        OrderDTO dto = inventoryOrder.toDTO();

        assertNotNull(dto);
        assertEquals(inventoryOrder.getId(), dto.getId());
        assertEquals(inventoryOrder.getOrderDate(), dto.getOrderDate());
        assertEquals(inventoryOrder.getDeliveryDate(), dto.getDeliveryDate());
        assertEquals(inventoryOrder.getWarehouse().getId(), dto.getWarehouseId());
        assertEquals(inventoryOrder.getOrderedBy().getId(), dto.getUserId());
        assertNotNull(dto.getProducts());
        assertEquals(transactions.size(), dto.getProducts().size());
    }
}
