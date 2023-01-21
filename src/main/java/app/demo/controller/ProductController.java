package app.demo.controller;

import app.demo.request.ProductCreateRequest;
import app.demo.response.ProductCreateResponse;
import app.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProductController.
 *
 * @author Hieu Nguyen
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
  private final ProductService productService;

  @PostMapping
  public ResponseEntity<ProductCreateResponse> create(@RequestBody ProductCreateRequest request) {
    return ResponseEntity.ok().body(ProductCreateResponse.of(productService.create(request)));
  }
}
