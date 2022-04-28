package br.com.alissonbolsoni.chorint.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileManager {

  void createFileWithContent(final String folder, final String fileName, final String content)
      throws IOException;

  List<Path> getAllFilesFromPath(Path file);
}
