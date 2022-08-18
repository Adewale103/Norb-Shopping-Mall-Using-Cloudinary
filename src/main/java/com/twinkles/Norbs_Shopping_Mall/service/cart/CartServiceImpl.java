package com.twinkles.Norbs_Shopping_Mall.service.cart;

import com.twinkles.Norbs_Shopping_Mall.data.dtos.QuantityOperation;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.CartRequestDto;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.requests.CartUpdateDto;
import com.twinkles.Norbs_Shopping_Mall.data.dtos.responses.CartResponseDto;
import com.twinkles.Norbs_Shopping_Mall.data.model.AppUser;
import com.twinkles.Norbs_Shopping_Mall.data.model.Cart;
import com.twinkles.Norbs_Shopping_Mall.data.model.Item;
import com.twinkles.Norbs_Shopping_Mall.data.model.Product;
import com.twinkles.Norbs_Shopping_Mall.data.repository.AppUserRepository;
import com.twinkles.Norbs_Shopping_Mall.data.repository.CartRepository;
import com.twinkles.Norbs_Shopping_Mall.data.repository.ProductRepository;
import com.twinkles.Norbs_Shopping_Mall.web.exception.NorbsShoppingMallException;
import com.twinkles.Norbs_Shopping_Mall.web.exception.ProductDoesNotExistException;
import com.twinkles.Norbs_Shopping_Mall.web.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public CartResponseDto addItemToCart(CartRequestDto requestDto) throws NorbsShoppingMallException {
        AppUser foundUser = appUserRepository.findById(requestDto.getAppUserId()).
                orElseThrow(()-> new UserNotFoundException("User with ID "+requestDto.getAppUserId()+" not found!"));

        Product foundProduct = productRepository.findById(requestDto.getProductId()).
                orElseThrow(()-> new ProductDoesNotExistException("Product with ID "+requestDto.getProductId()+" not found!" ));

        Item item = new Item(foundProduct, requestDto.getQuantity());

        if(!quantityIsValid(foundProduct,requestDto.getQuantity())){
            throw new NorbsShoppingMallException("Quantity too large!");
        }
        Cart userCart = foundUser.getMyCart();

        userCart.addItem(item);
        userCart.setTotalPrice(userCart.getTotalPrice() + calculatePrice(item));

        cartRepository.save(userCart);

        return CartResponseDto.builder()
                .itemList(userCart.getItems())
                .totalPrice(userCart.getTotalPrice())
                .build();

    }
    private double calculatePrice(Item item) {
        return item.getProduct().getPrice() * item.getQuantityAddedToCart();
    }

    private boolean quantityIsValid(Product foundProduct, Integer quantity) {
        return foundProduct.getQuantity() >= quantity;
    }

    @Override
    public CartResponseDto updateCartItem(CartUpdateDto cartUpdateDto) throws NorbsShoppingMallException {
        AppUser foundUser = appUserRepository.findById(cartUpdateDto.getUserId()).
                orElseThrow(()-> new UserNotFoundException("User with ID "+cartUpdateDto.getUserId()+" not found!"));

        Cart foundUserCart = foundUser.getMyCart();

        Item foundItem = findItemInCart(cartUpdateDto.getItemId(),foundUserCart);

        if(foundItem.getQuantityAddedToCart() == 0){
            throw new NorbsShoppingMallException("Quantity of item is now zero!");
        }
        if(cartUpdateDto.getQuantityOp() == QuantityOperation.INCREASE){
            foundItem.setQuantityAddedToCart(foundItem.getQuantityAddedToCart() + 1);
            foundUserCart.setTotalPrice(foundUserCart.getTotalPrice() + foundItem.getProduct().getPrice());
        }
        else if(cartUpdateDto.getQuantityOp() == QuantityOperation.DECREASE){
            foundItem.setQuantityAddedToCart(foundItem.getQuantityAddedToCart() - 1);
            foundUserCart.setTotalPrice(foundUserCart.getTotalPrice() - foundItem.getProduct().getPrice());
        }
        cartRepository.save(foundUserCart);
        return CartResponseDto.builder()
                .itemList(foundUserCart.getItems())
                .totalPrice(foundUserCart.getTotalPrice())
                .build();
    }

    private Item findItemInCart(Long itemId, Cart foundUserCart) throws NorbsShoppingMallException {
        return foundUserCart.getItems().stream().filter(x->x.getId().equals(itemId)).findFirst().
                orElseThrow(()-> new NorbsShoppingMallException("Item not found!"));
    }
}
