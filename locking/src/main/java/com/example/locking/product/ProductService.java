package com.example.locking.product;

import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void updateProductPrice(Long productId, Double newPrice){
        try{
            Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

            product.setPrice(newPrice);

        } catch(ObjectOptimisticLockingFailureException e){
            System.out.println("낙관적 락 충돌 발생!!!");
        }
    }

}
