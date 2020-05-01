package com.loveline.ecommerce.dao;

import com.loveline.ecommerce.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:4200")
@RepositoryRestResource(collectionResourceRel = "productCategory", path = "product_category") //name of JSON entry
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
