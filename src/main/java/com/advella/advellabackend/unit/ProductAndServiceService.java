package com.advella.advellabackend.unit;

import com.advella.advellabackend.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductAndServiceService {
    private final ServiceService serviceService;
    private final ProductService productService;


    public List<Object> getLatestProductsAndServices(int amount) {
        List<com.advella.advellabackend.model.Service> services = serviceService.getLatestServices(amount);
        List<Product> products = productService.getLatestProducts(amount);

        List<Object> objects = new ArrayList<>();

        Stream.of(services, products).flatMap(List::stream)
                .sorted(Comparator.comparing(o -> {
                    if (o instanceof Product) return ((Product) o).getPostedDateTime();
                    if (o instanceof com.advella.advellabackend.model.Service) return ((com.advella.advellabackend.model.Service) o).getPostedDateTime();
                    throw new RuntimeException("illegal type");
                }))
                .forEach(objects::add);
        return objects.subList(0, amount);
    }

    public List<Object> getProductsAndServicesWithSearchedQuery(String searchedQuery) {
        List<Object> objects = new ArrayList<>();
        objects.addAll(serviceService.getSearchedService(searchedQuery));
        objects.addAll(productService.getSearchedProducts(searchedQuery));

        return objects;
    }
}
