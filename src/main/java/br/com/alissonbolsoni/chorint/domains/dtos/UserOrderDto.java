package br.com.alissonbolsoni.chorint.domains.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserOrderDto {

  @JsonProperty("user_id")
  private int userId;

  private String name;
  private List<OrderDto> orders;
}
