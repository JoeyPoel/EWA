package teamx.app.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import teamx.app.backend.models.Product;
import teamx.app.backend.models.ProductCategory;
import teamx.app.backend.models.dto.ProductDTO;
import teamx.app.backend.repositories.ProductRepository;
import teamx.app.backend.services.ProductService;

import java.util.List;

/**
 * Product Controller
 * This class is a REST controller for the product model.
 *
 * @author Joey van der Poel
 * @author Junior Javier Brito Perez
 * @see Product
 * @see ProductRepository
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        try {
            List<ProductDTO> products = productService.getAllProductsDTO();
            if (products == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Products not found");
            }
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving products");
        }
    }

    @GetMapping("/getProductById/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
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

    @GetMapping("/getAllProductCategories")
    public ResponseEntity<List<ProductCategory>> getAllProductCategories() {
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

    // TODO: Add validation and authentication
    @PostMapping("/addProduct")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO product) {
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

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO newProductData) {
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

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long id) {
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
}
