package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.Product;
import com.advella.advellabackend.model.User;
import com.advella.advellabackend.services.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @ApiOperation(value = "Get products", notes = "Gets all products")
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @ApiOperation(value = "Get products in category", notes = "Gets all products in category by categoryId")
    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsInCategory(@PathVariable("categoryId") Integer categoryId) {
        return productService.getAllProductsWithCategoryId(categoryId);
    }

    @ApiOperation(value = "Get products posted by user", notes = "Gets requested number of products posted by user")
    @GetMapping("/products/user/{userId}")
    public ResponseEntity<List<Product>> getProductsInPostedByUser(@PathVariable("userId") int userId, @RequestParam int amount) {
        return productService.getProductsInPostedByUser(userId, amount);
    }

    @ApiOperation(value = "Add new product", notes = "Adds new product")
    @PostMapping("/products/new")
    public ResponseEntity<Product> addNewProduct(@RequestPart Product newProduct, @RequestHeader("Authorization") String token, @RequestPart(name = "image", required = false) MultipartFile image) {
        return productService.addNewProduct(newProduct, token, image);
    }

    @ApiOperation(value = "Open product status", notes = "Sets status of this product as open")
    @PostMapping("/products/open/{productId}")
    public ResponseEntity<Product> openProductStatus(@PathVariable int productId) {
        return productService.openProduct(productId);
    }

    @ApiOperation(value = "Close product status", notes = "Sets status of this product as closed")
    @PostMapping("/products/closed/{productId}")
    public ResponseEntity<Product> closeProductStatus(@PathVariable int productId) {
        return productService.closeProduct(productId);
    }

    @ApiOperation(value = "Get products by location", notes = "Gets all products by location")
    @GetMapping("/products/dash-board/{location}")
    public ResponseEntity<List<Product>> getProductsByLocation(@PathVariable String location) {
        return productService.getProductsByLocation(location);
    }

    @ApiOperation(value = "Get latest products", notes = "Gets requested number of latest products")
    @GetMapping("/products/latest")
    public ResponseEntity<List<Product>> getLatestProducts(@RequestParam(required = false, defaultValue = "5") int amount) {
        return ResponseEntity.ok(productService.getLatestProducts(amount));
    }

    @ApiOperation(value = "Get product count", notes = "Gets number of all products")
    @GetMapping("/products/dash-board/count")
    public ResponseEntity<Integer> getProductCount() {
        return ResponseEntity.ok(productService.getProductCount());
    }

    @ApiOperation(value = "Get product by Id", notes = "Gets a product by its productId")
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId) {
        return productService.getProductByIdResponse(productId);
    }

    @ApiOperation(value = "Delete product", notes = "Deletes product by its productId")
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId, @RequestHeader("Authorization") String token) {
        return productService.deleteProductById(productId, token);
    }

    @ApiOperation(value = "Get products between dates", notes = "Gets number of products posted between dates")
    @GetMapping("/products/dash-board/{startDate}/{endDate}")
    public ResponseEntity<Integer> getProductsBetweenDates(@PathVariable long startDate, @PathVariable long endDate) {
        return ResponseEntity.ok(productService.getProductCount(new Date(startDate), new Date(endDate)));
    }

    @ApiOperation(value = "Get all bidders of product", notes = "Gets all bidders of products")
    @GetMapping("/products/bidders/{productId}")
    public ResponseEntity<Collection<User>> getProductBidders(@PathVariable int productId) {
        return productService.getProductBidders(productId);
    }

    @ApiOperation(value = "Get highest bidder of product", notes = "Gets highest bidder of product")
    @GetMapping("/products/bidders/highest/{productId}")
    public ResponseEntity<User> getHighestBidder(@PathVariable int productId) {
        return productService.getHighestBidder(productId);
    }
}
