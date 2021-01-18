package com.cosium.html;

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

  @Parameter(defaultValue = "UTF-8")
  private String encoding;

  @Parameter(required = true)
  private String htmlInputFilePath;

  @Parameter(required = true)
  private String xhtmlOutputFilePath;

  public HtmlToXhtmlConversion() {}

  public HtmlToXhtmlConversion(
      String encoding, String htmlInputFilePath, String xhtmlOutputFilePath) {
    this.encoding = encoding;
    this.htmlInputFilePath = htmlInputFilePath;
    this.xhtmlOutputFilePath = xhtmlOutputFilePath;
  }

  public String getEncoding() {
    return encoding;
  }

  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  public String getHtmlInputFilePath() {
    return htmlInputFilePath;
  }

  public void setHtmlInputFilePath(String htmlInputFilePath) {
    this.htmlInputFilePath = htmlInputFilePath;
  }

  public String getXhtmlOutputFilePath() {
    return xhtmlOutputFilePath;
  }

  public void setXhtmlOutputFilePath(String xhtmlOutputFilePath) {
    this.xhtmlOutputFilePath = xhtmlOutputFilePath;
  }

  public void execute() {
    try {
      doExecute();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void doExecute() throws IOException {
    Path htmlFile = Paths.get(htmlInputFilePath);
    Document document;
    try (InputStream inputStream = Files.newInputStream(htmlFile)) {
      document =
          Jsoup.parse(
              inputStream, encoding, htmlFile.toAbsolutePath().toString(), Parser.xmlParser());
    }
    document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
    Files.write(Paths.get(xhtmlOutputFilePath), document.html().getBytes(encoding));
  }
}
