package br.com.alissonbolsoni.chorint.domains.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileConverted {
  private List<UserOrder> userOrders;
  private String fileName;
}
