package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamx.app.backend.models.ProductCategory;
import teamx.app.backend.services.ProductService;
import teamx.app.backend.utils.DTO.ProductDTO;

import java.util.List;

/**
 * The ProductController class is a REST controller that handles product-related operations.
 * It exposes endpoints for retrieving, adding, updating, and deleting products.
 *
 * @author Joey van der Poel
 * @author Junior Javier Brito Perez
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retrieves all products from the database.
     *
     * @return ResponseEntity object with a list of ProductDTO objects representing all products.
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    /**
     * Adds a new product to the database.
     *
     * @param product The ProductDTO object representing the product to be added.
     * @return ResponseEntity object with a ProductDTO object representing the newly added product.
     */
    @PostMapping
    public ResponseEntity<ProductDTO> add(@RequestBody ProductDTO product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.add(product));
    }


    /**
     * Retrieves a product from the database based on the provided ID.
     *
     * @param id The ID of the product to be retrieved.
     * @return ResponseEntity object with a ProductDTO object representing the retrieved product.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findDTOById(id));
    }

    /**
     * Updates a product in the database based on the provided ID and new product data.
     *
     * @param id             The ID of the product to be updated.
     * @param newProductData The new product data to update the existing product with.
     * @return ResponseEntity object with a ProductDTO object representing the updated product.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateById(@PathVariable Long id, @RequestBody ProductDTO newProductData) {
        return ResponseEntity.ok(productService.update(id, newProductData));
    }

    /**
     * Deletes a product from the database based on the provided ID.
     *
     * @param id The ID of the product to be deleted.
     * @return ResponseEntity object with a ProductDTO object representing the deleted product.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDTO> deleteById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.delete(id));
    }

    /**
     * Retrieves all product categories from the database.
     *
     * @return ResponseEntity object with a List of ProductCategory objects representing all the product categories.
     */
    @GetMapping("/categories")
    public ResponseEntity<List<ProductCategory>> getAllCategories() {
        return ResponseEntity.ok(productService.findAllProductCategories());
    }

    @GetMapping("/active")
    public ResponseEntity<List<ProductDTO>> getAllActive() {
        return ResponseEntity.ok(productService.findAllActive());
    }
}
