package com.loveline.ecommerce.config;

import com.loveline.ecommerce.entity.Product;
import com.loveline.ecommerce.entity.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Configuration
public class MyDataRestConfig  implements RepositoryRestConfigurer {

    private EntityManager entityManager;

    @Autowired
    public  MyDataRestConfig(EntityManager theEntityManager){
        entityManager= theEntityManager;
    }
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        HttpMethod[] theUnsupportedAction= {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};

        //disable HTTP methods for products : PUT, POST, DELETE
        config.getExposureConfiguration().
                forDomainType(Product.class).
                withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedAction))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedAction));

        //disable HTTP methods for productCategory : PUT, POST, DELETE
        config.getExposureConfiguration().
                forDomainType(ProductCategory.class).
                withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedAction))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedAction));
    // call an internal helper method
        exposeIds(config);
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        // expose entity ids
        //-get a list of all entity classes on the entity manager
        Set<EntityType<?>> entities= entityManager.getMetamodel().getEntities();
        List<Class> entityClasses= new ArrayList<>();

        // get the entity types of entity
        for (EntityType temEntityType: entities){
            entityClasses.add(temEntityType.getJavaType());
        }

        //expose the entity ids for the array of entity/domain types

        Class[] domainTypes= entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
