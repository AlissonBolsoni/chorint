package br.com.alissonbolsoni.chorint.services;

import br.com.alissonbolsoni.chorint.domains.entity.FileConverted;
import java.nio.file.Path;
import java.util.List;

public interface ProcessConvertion {

  List<FileConverted> processFiles(List<Path> files);
}
