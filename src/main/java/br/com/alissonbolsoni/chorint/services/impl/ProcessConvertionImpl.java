package br.com.alissonbolsoni.chorint.services.impl;

import br.com.alissonbolsoni.chorint.domains.entity.FileConverted;
import br.com.alissonbolsoni.chorint.services.FileConverter;
import br.com.alissonbolsoni.chorint.services.ProcessConvertion;
import br.com.alissonbolsoni.chorint.utils.Log;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProcessConvertionImpl implements ProcessConvertion {

  private final FileConverter converter;
  private final Log log;

  @Override
  public List<FileConverted> processFiles(final List<Path> files) {

    final List<FileConverted> fileConvertedList = new ArrayList<>();

    files.forEach(
        path -> {
          try (Stream<String> stream = Files.lines(path)) {
            final var lines = stream.collect(Collectors.toList());

            final var fileName = path.getFileName().toString();

            final var converted = converter.convert(lines);

            fileConvertedList.add(new FileConverted(converted, fileName));

          } catch (IOException e) {
            log.error("Can not open the file");
          }
        });

    return fileConvertedList;
  }
}
