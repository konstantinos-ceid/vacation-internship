package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Product;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.ProductRepository;
import gr.knowledge.internship.vacation.service.dto.ProductDTO;
import gr.knowledge.internship.vacation.service.mapper.ProductMapper;
import gr.knowledge.internship.vacation.utility.Constants;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Log4j2
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;


    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional(readOnly = true)
    public ProductDTO getById(Long id) {
        ProductDTO result = new ProductDTO();
        log.debug("Request to get Product by id : {}", id);
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            result = productMapper.toDto(product.get());
        } else throw new NotFoundException(Constants.NotFoundExceptionMessage);
        return result;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAll() {
        log.debug("Request to get All Companies");
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Product by id : {}", id);
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else throw new NotFoundException(Constants.NotFoundExceptionMessage);
    }

    @Transactional(readOnly = true)
    public Boolean isExistingProduct(Long id){
        return productRepository.findById(id).isPresent();
    }
}