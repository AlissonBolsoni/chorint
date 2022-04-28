package br.com.alissonbolsoni.chorint.usecases.impl;

import static java.util.Objects.isNull;

import br.com.alissonbolsoni.chorint.domains.entity.FileConverted;
import br.com.alissonbolsoni.chorint.domains.entity.UserOrder;
import br.com.alissonbolsoni.chorint.exceptions.ArgumentNullOrEmptyException;
import br.com.alissonbolsoni.chorint.services.FileManager;
import br.com.alissonbolsoni.chorint.services.ProcessConvertion;
import br.com.alissonbolsoni.chorint.usecases.NormalizeFile;
import br.com.alissonbolsoni.chorint.utils.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class NormalizeFileImpl implements NormalizeFile {

  private static final String RETURN_PATH = "target/files";

  private final ProcessConvertion processConvertion;
  private final ObjectMapper mapper;
  private final FileManager fileManager;
  private final Log log;

  @Override
  public void execute(final String[] args) {

    if (isNull(args) || args.length == 0) {
      throw new ArgumentNullOrEmptyException("Arguments must be not null or not empty");
    }

    final List<Path> files = new ArrayList<>();

    Arrays.stream(args)
        .forEach(
            arg -> {
              final var file = new File(arg).toPath();
              files.addAll(fileManager.getAllFilesFromPath(file));
            });

    if (files.isEmpty()) {
      log.info("Finish process because has nothing to process");
      return;
    }

    final var filesConverted = processConvertion.processFiles(files);

    for (FileConverted fileConverted : filesConverted) {
      final var fileName = "json-".concat(fileConverted.getFileName());
      final var userOrdersDto =
          fileConverted.getUserOrders().stream().map(UserOrder::toDto).collect(Collectors.toList());

      try {
        final var json = mapper.writeValueAsString(userOrdersDto);
        fileManager.createFileWithContent(RETURN_PATH, fileName, json);
      } catch (IOException e) {
        if (e instanceof JsonProcessingException) {
          log.error(
              "Fail to convert file {} to json. Error message: {}",
              fileConverted.getFileName(),
              e.getMessage());
        } else {
          log.error("Fail to create file {}. Error message: {}", fileName, e);
        }
      }
    }
  }
}
