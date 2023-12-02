package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.ProductCategory;
import teamx.app.backend.models.dto.ProductDTO;
import teamx.app.backend.services.ProductService;

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
     * Retrieves all products.
     *
     * @return a ResponseEntity containing a list of ProductDTOs
     * @throws ResponseStatusException if there is an error retrieving products
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        try {
            List<ProductDTO> products = productService.getAllProductsDTO();

            if (products.isEmpty()) {
                return new ResponseEntity<>(products, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving products");
        }
    }

    /**
     * Adds a new product.
     *
     * @param product the ProductDTO object to be added
     * @return a ResponseEntity containing the added ProductDTO
     * @throws ResponseStatusException if there is an error adding the product
     */
    @PostMapping
    public ResponseEntity<ProductDTO> add(@RequestBody ProductDTO product) {
        try {
            ProductDTO newProduct = productService.addProductDTO(product);
            if (newProduct == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
            }
            return new ResponseEntity<>(newProduct, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding product");
        }
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return a ResponseEntity containing the retrieved ProductDTO object
     * @throws ResponseStatusException if the product is not found or if there is an error retrieving it
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        try {
            ProductDTO product = productService.getProductDTOById(id);
            if (product == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
            }
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving product");
        }
    }

    /**
     * Updates a product with new data.
     *
     * @param id                 the ID of the product to update
     * @param newProductData     the new ProductDTO object containing updated data
     * @return a ResponseEntity containing the updated ProductDTO object
     * @throws ResponseStatusException if the product is not found or if there is an error updating it
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateById(@PathVariable Long id, @RequestBody ProductDTO newProductData) {
        try {
            ProductDTO updatedProduct = productService.updateProductDTO(newProductData, id);
            if (updatedProduct == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
            }
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating product");
        }
    }

    /**
     * Deletes a product with the specified ID.
     *
     * @param id the ID of the product to delete
     * @return a ResponseEntity containing the deleted ProductDTO object
     * @throws ResponseStatusException if the product is not found or if there is an error deleting it
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDTO> deleteById(@PathVariable Long id) {
        try {
            ProductDTO deletedProduct = productService.deleteProductDTO(id);
            if (deletedProduct == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
            }
            return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting product");
        }
    }

    /**
     * Retrieves all product categories.
     *
     * @return a ResponseEntity containing a list of ProductCategory objects
     * @throws ResponseStatusException if the product categories are not found or if there is an error retrieving them
     */
    @GetMapping("/categories")
    public ResponseEntity<List<ProductCategory>> getAllCategories() {
        try {
            List<ProductCategory> productCategories = productService.getAllProductCategories();
            if (productCategories == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product categories not found");
            }
            return new ResponseEntity<>(productCategories, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving product categories");
        }
    }
}
