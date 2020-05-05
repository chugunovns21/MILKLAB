package com.example.milk.service;

import com.example.milk.domain.Product;
import com.example.milk.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private ProductRepo productRepo;

    public Iterable<Product> findAll() {
        return productRepo.findAll();
    }

    public void deleteProduct(Product product) {
        productRepo.delete(product);
    }

    public void newProduct(String prodName, Long prodCoast,String prodInfo, MultipartFile file) throws IOException {
        Product product = new Product(prodName, prodInfo, prodCoast);
        if (checkImg(file)) {
            product.setProdImg(file.getOriginalFilename());
        }
        productRepo.save(product);
    }

    public void saveProduct(Product product, String prodName, String prodInfo, Long prodCoast, MultipartFile file) throws IOException {
        product.setProdName(prodName);
        product.setProdInfo(prodInfo);
        product.setProdCoast(prodCoast);
        if (checkImg(file)) {
            product.setProdImg(file.getOriginalFilename());
        }
        productRepo.save(product);
    }

    public boolean checkImg(MultipartFile file) throws IOException {
        if (!file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            file.transferTo(new File(uploadPath + "/" + file.getOriginalFilename()));
            return true;
        } else return false;
    }

    //Find
    public List<Product> findAllById(Long id) {
        return productRepo.findAllById(id);
    }

    public List<Product> findAllByName(String prodName) {
        return productRepo.findAllByProdName(prodName);
    }

    public List<Product> findAllByGroup(String prodGroup) {
        return productRepo.findAllByProdGroup(prodGroup);
    }

    public List<Product> findAllByCoast(Long prodCoast) {
        return productRepo.findAllByProdCoast(prodCoast);
    }

    public Product findById (Product product) {
        return product = productRepo.findById(product.getId()).get();
    }
}
