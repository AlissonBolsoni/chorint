package br.com.alissonbolsoni.chorint.services.impl;

import br.com.alissonbolsoni.chorint.services.FileManager;
import br.com.alissonbolsoni.chorint.utils.Log;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileManagerImpl implements FileManager {

  private final Log log;

  @Override
  public void createFileWithContent(
      final String folder, final String fileName, final String content) throws IOException {

    final var folderPath = Paths.get(folder).toAbsolutePath();
    if (!Files.exists(folderPath)) {
      Files.createDirectory(folderPath);
    }

    final var path = Paths.get(folder, fileName).toAbsolutePath();
    Files.deleteIfExists(path);
    Files.writeString(path, content, StandardOpenOption.CREATE_NEW);

    log.info("File {} create with success in path {}", fileName, path);
  }

  @Override
  public List<Path> getAllFilesFromPath(final Path file) {
    final List<Path> files = new ArrayList<>();

    if (Files.exists(file)) {
      if (Files.isRegularFile(file)) {
        files.add(file);
      } else {
        try {
          files.addAll(Files.list(file).collect(Collectors.toList()));
        } catch (IOException e) {
          log.error("Con not get files");
        }
      }
    } else {
      log.error("The path {} entered is not valid", file.toString());
    }

    return files;
  }
}
