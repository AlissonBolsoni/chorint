package br.com.alissonbolsoni.chorint.services.impl;

import br.com.alissonbolsoni.chorint.services.FileManager;
import br.com.alissonbolsoni.chorint.utils.Log;

public class FileManagerFactory {
  public FileManager create() {
    return new FileManagerImpl(new Log());
  }
}
