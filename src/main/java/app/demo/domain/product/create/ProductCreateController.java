package app.demo.domain.product.create;

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
public class ProductCreateController {
  private final ProductCreateService productCreateService;

  @PostMapping
  public ResponseEntity<ProductCreateResponse> create(@RequestBody ProductCreateRequest request) {
    return ResponseEntity.ok().body(ProductCreateResponse.of(productCreateService.create(request)));
  }
}
