package com.twinkles.Norbs_Shopping_Mall.service.product;

import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.ProductDto;
import com.twinkles.Norbs_Shopping_Mall.data.model.Product;
import com.twinkles.Norbs_Shopping_Mall.data.repository.ProductRepository;
import com.twinkles.Norbs_Shopping_Mall.service.cloud.CloudService;
import com.twinkles.Norbs_Shopping_Mall.web.exception.NorbsShoppingMallException;
import com.twinkles.Norbs_Shopping_Mall.web.exception.ProductAlreadyExistException;
import com.twinkles.Norbs_Shopping_Mall.web.exception.ProductDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CloudService cloudService;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(ProductDto productDto) throws NorbsShoppingMallException, IOException {
        Optional<Product> foundProduct = productRepository.findByName(productDto.getName());
        if(foundProduct.isPresent()){
            throw new ProductAlreadyExistException("Product already exist!");
        }
        Product product = new Product();

        if(productDto.getImage() != null){
            Map<?,?> uploadResult = cloudService.upload(productDto.getImage().getBytes(),
                    ObjectUtils.asMap("public_id", "inventory/" + productDto.getImage().getOriginalFilename(),
                            "overwrite", true
                    ));
            product.setImageUrl(uploadResult.get("url").toString());
        }
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setDescription(productDto.getDescription());
        return productRepository.save(product);
    }

    @Override
    public Product saveOrUpdateProduct(Product product) throws NorbsShoppingMallException {
        if(product == null) throw new NorbsShoppingMallException("Product cannot be null");
        return productRepository.save(product);
    }

    @Override
    public Product updateProductDetails(Long productId, JsonPatch productPatch) throws NorbsShoppingMallException, JsonPatchException, JsonProcessingException {
        if(productId == null) throw new NorbsShoppingMallException("Product cannot be null");
        Optional<Product> foundProduct = productRepository.findById(productId);
        if(foundProduct.isEmpty()){
            throw new ProductDoesNotExistException("Product not found!");
        }
        Product targetProduct = foundProduct.get();

        targetProduct = applyPatchToProduct(productPatch,targetProduct);
        return saveOrUpdateProduct(targetProduct);
    }

    private Product applyPatchToProduct(JsonPatch productPatch, Product targetProduct) throws JsonPatchException, JsonProcessingException  {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = productPatch.apply(objectMapper.convertValue(targetProduct, JsonNode.class));
        return objectMapper.treeToValue(patched,Product.class);
    }
}
