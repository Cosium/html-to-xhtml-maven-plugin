package com.cosium.html;

import static java.util.Optional.ofNullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.maven.plugins.annotations.Parameter;

public class MavenHtmlToXhtmlConversion {

  @Parameter(required = true)
  private String htmlInputFilePath;

  @Parameter(required = true)
  private String xhtmlOutputFilePath;

  @Parameter private String encoding;

  @Parameter private String tagElementToWrapDocumentIn;

  public MavenHtmlToXhtmlConversion() {}

  public MavenHtmlToXhtmlConversion(String htmlInputFilePath, String xhtmlOutputFilePath) {
    this.htmlInputFilePath = htmlInputFilePath;
    this.xhtmlOutputFilePath = xhtmlOutputFilePath;
  }

  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  public void setHtmlInputFilePath(String htmlInputFilePath) {
    this.htmlInputFilePath = htmlInputFilePath;
  }

  public void setXhtmlOutputFilePath(String xhtmlOutputFilePath) {
    this.xhtmlOutputFilePath = xhtmlOutputFilePath;
  }

  public void setTagElementToWrapDocumentIn(String tagElementToWrapDocumentIn) {
    this.tagElementToWrapDocumentIn = tagElementToWrapDocumentIn;
  }

  public MavenHtmlToXhtmlConversion withTagElementToWrapDocumentIn(
      String tagElementToWrapDocumentIn) {
    setTagElementToWrapDocumentIn(tagElementToWrapDocumentIn);
    return this;
  }

  public void execute(String defaultEncoding) {
    try {
      doExecute(defaultEncoding);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void doExecute(String defaultEncoding) throws IOException {
    Path htmlInputFile = Paths.get(htmlInputFilePath);

    Path copiedHtmlInputFile = Files.createTempFile(null, null);
    try {
      Files.copy(htmlInputFile, copiedHtmlInputFile, StandardCopyOption.REPLACE_EXISTING);
      String baseUri = copiedHtmlInputFile.toAbsolutePath().toString();

      try (InputStream inputStream = Files.newInputStream(copiedHtmlInputFile);
          OutputStream outputStream = Files.newOutputStream(Paths.get(xhtmlOutputFilePath))) {
        new HtmlToXhtmlConversion(
                ofNullable(encoding).orElse(defaultEncoding), tagElementToWrapDocumentIn)
            .execute(baseUri, inputStream, outputStream);
      }
    } finally {
      Files.deleteIfExists(copiedHtmlInputFile);
    }
  }
}
