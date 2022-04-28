package br.com.alissonbolsoni.chorint.services;

import br.com.alissonbolsoni.chorint.domains.entity.UserOrder;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public interface FileConverter {

  List<UserOrder> convert(List<String> lines);

  default boolean canConvert(final String line) {
    final var size = line.length();
    final var first = line.substring(0, 1);
    final var last = line.substring(line.length() - 1);

    return size == 95 && StringUtils.isNumeric(first) && StringUtils.isNumeric(last);
  }
}
