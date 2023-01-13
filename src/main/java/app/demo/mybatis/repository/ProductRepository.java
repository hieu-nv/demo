package app.demo.mybatis.repository;

import java.util.Optional;
import java.util.UUID;
import app.demo.mybatis.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * ProductRepository.
 *
 * @author Hieu Nguyen
 */
@Mapper
public interface ProductRepository {

  /**
   * findById.
   *
   * @param id UUID
   * @return Optional&lt;Product&gt;
   */
  Optional<Product> findById(@Param("id") UUID id);

  void create(Product product);
}
