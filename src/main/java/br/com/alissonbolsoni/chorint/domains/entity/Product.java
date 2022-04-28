package br.com.alissonbolsoni.chorint.domains.entity;

import br.com.alissonbolsoni.chorint.domains.dtos.ProductDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Product {

  private int productId;
  private Double value;

  public ProductDto toDto() {
    return new ProductDto(productId, value);
  }
}
