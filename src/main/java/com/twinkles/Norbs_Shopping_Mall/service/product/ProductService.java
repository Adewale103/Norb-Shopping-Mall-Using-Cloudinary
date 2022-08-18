package com.twinkles.Norbs_Shopping_Mall.service.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.ProductDto;
import com.twinkles.Norbs_Shopping_Mall.data.model.Product;
import com.twinkles.Norbs_Shopping_Mall.web.exception.NorbsShoppingMallException;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product createProduct(ProductDto productDto) throws NorbsShoppingMallException, IOException;
    Product saveOrUpdateProduct(Product product) throws NorbsShoppingMallException;
    Product updateProductDetails(Long productId, JsonPatch patch) throws NorbsShoppingMallException, JsonPatchException, JsonProcessingException;
}
