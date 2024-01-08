package teamx.app.backend.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Date;
import java.util.List;

public class DTO {


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChartsDataDTO extends DTO {
        private List<String> labels;
        private List<DataSetDTO> datasets;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataSetDTO extends DTO {
        private String label;
        private List<Integer> data;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CapacityDTO extends DTO {
        private Long id;
        private Long warehouseId;
        private String categoryName;
        private Long categoryId;
        private int capacity;
        private int minimumStockLevel;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InventoryProductDTO extends DTO {
        private Long productId;
        private Long warehouseId;
        private String name;
        private String description;
        private Double price;
        private int quantity;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InventoryProjectDTO extends DTO {
        private Long id;
        private int quantity;
        private String ProductName;
        private String warehouseName;
        private String transactionType;
        private Date transactionDate;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderDTO extends DTO {
        private Long id;
        private List<OrderLineDTO> products;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        Date orderDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        Date deliveryDate;
        private Long warehouseId;
        private Long ProjectId;
        private Long userId;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderLineDTO extends DTO {
        private Long productId;
        private int quantity;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductDTO extends DTO {
        private Long id;
        private String name;
        private String description;
        private Double price;
        private Long categoryId;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectDTO extends DTO {
        private long id;
        private String name;
        private String description;
        private String location;
        private String clientName;
        private String clientEmail;
        private String clientPhone;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private Date startDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private Date endDate;
        private String status;
        private Long teamId;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TaskDTO extends DTO {
        private Long id;
        private int order;
        private String name;
        private String description;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private String deadline;
        private String status;
        private Long projectId;
        private Long personalTodoListOwnerId;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamDTO extends DTO {
        private Long id;
        private Long warehouseId;
        private String name;
        private Long leaderId;
        private List<Long> membersIds;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionDTO extends DTO {
        private Long id;
        private int quantity;
        private String transactionType;
        private Date transactionDate;
        private Long productId;
        private Long warehouseId;
        private Long transferFromWarehouseId;
        private Long projectId;
        private Long orderId;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDTO extends DTO {
        private Long id;
        private String name;
        private String email;
        private String password;
        private String role;
        private Long teamId;
        private String jwtToken;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MailRequest {
        private String name;
        private String to;
        private String from;
        private String subject;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MailResponse {
        private String message;
        private boolean status;
    }
}