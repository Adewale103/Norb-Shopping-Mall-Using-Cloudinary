package com.twinkles.Norbs_Shopping_Mall.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.ProductDto;
import com.twinkles.Norbs_Shopping_Mall.data.model.Product;
import com.twinkles.Norbs_Shopping_Mall.service.product.ProductService;
import com.twinkles.Norbs_Shopping_Mall.web.exception.NorbsShoppingMallException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllProduct(){
        List<Product> allProducts = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(allProducts);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> createProduct( @ModelAttribute ProductDto productDto) throws IOException, NorbsShoppingMallException {
           Product savedProduct = productService.createProduct(productDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PatchMapping(path = "/{id}",consumes = "application/json-patch+json")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateProduct (@PathVariable Long id, @RequestBody JsonPatch patch){
        try{
            Product updatedProduct =productService.updateProductDetails(id, patch);
            return ResponseEntity.ok().body(updatedProduct);
        }
        catch(NorbsShoppingMallException | JsonProcessingException | JsonPatchException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }


}
