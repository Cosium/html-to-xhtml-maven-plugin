package com.cosium.html;

import static java.util.Optional.ofNullable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.maven.plugins.annotations.Parameter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

public class HtmlToXhtmlConversion {

  @Parameter(required = true)
  private String htmlInputFilePath;

  @Parameter(required = true)
  private String xhtmlOutputFilePath;

  @Parameter private String encoding;

  public HtmlToXhtmlConversion() {}

  public HtmlToXhtmlConversion(String htmlInputFilePath, String xhtmlOutputFilePath) {
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

  public void execute(String defaultEncoding) {
    try {
      doExecute(defaultEncoding);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void doExecute(String defaultEncoding) throws IOException {
    String finalEncoding = ofNullable(encoding).orElse(defaultEncoding);

    Path htmlFile = Paths.get(htmlInputFilePath);
    Document document;
    try (InputStream inputStream = Files.newInputStream(htmlFile)) {
      document =
          Jsoup.parse(
              inputStream, finalEncoding, htmlFile.toAbsolutePath().toString(), Parser.xmlParser());
    }
    document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
    Files.write(Paths.get(xhtmlOutputFilePath), document.html().getBytes(finalEncoding));
  }
}
