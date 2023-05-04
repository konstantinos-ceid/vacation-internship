package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.service.ProductService;
import gr.knowledge.internship.vacation.service.dto.ProductDTO;
import gr.knowledge.internship.vacation.utility.Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDTO> save(@RequestBody ProductDTO productDTO) {
        log.debug("Rest request to save Product : {}", productDTO);
        ProductDTO result = productService.save(productDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable("id") Long id, @RequestBody ProductDTO productDTO) {
        log.debug("Rest request to save Product : {}", productDTO);
        Boolean isExistingProduct = productService.isExistingProduct(id);
        Util.validateUpdateInput(productDTO.getId(), id, isExistingProduct);
        ProductDTO result = productService.save(productDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable("id") Long id) {
        log.debug("Rest request to get Product by id : {}", id);
        ProductDTO product = productService.getById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductDTO>> getAll() {
        log.debug("Rest request to get all Product ");
        List<ProductDTO> productDTOList = productService.getAll();
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        log.debug("Rest Request to delete Product by id : {}", id);
        Boolean isExistingProduct = productService.isExistingProduct(id);
        Util.validateDeleteInput(isExistingProduct);
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}