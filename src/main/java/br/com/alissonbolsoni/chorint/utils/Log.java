package br.com.alissonbolsoni.chorint.utils;

public class Log {

  public void info(final String message, final Object... args) {
    final var strMessage = String.format(message.replace("{}", "%s"), args);

    System.out.println("INFO - ".concat(strMessage));
  }

  public void error(final String message, final Object... args) {
    final var strMessage = String.format(message.replace("{}", "%s"), args);

    System.out.println("ERROR - ".concat(strMessage));
  }
}
