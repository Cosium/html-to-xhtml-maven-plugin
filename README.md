[![Maven Central Latest](https://img.shields.io/maven-central/v/com.cosium.html/html-to-xhtml-maven-plugin.svg)](https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.cosium.html%22%20AND%20a%3A%22html-to-xhtml-maven-plugin%22)
[![CI](https://github.com/Cosium/html-to-xhtml-maven-plugin/workflows/CI/badge.svg)](https://github.com/Cosium/html-to-xhtml-maven-plugin/actions?query=workflow%3ACI)

# HTML To XHTML Maven Plugin

```xml

<build>
  <plugins>
    <plugin>
      <groupId>com.cosium.html</groupId>
      <artifactId>html-to-xhtml-maven-plugin</artifactId>
      <version>${html-to-xhtml-maven-plugin.version}</version>
      <executions>
        <!-- On Maven generate-resources phase, convert ${project.basedir}/example.html to  ${project.build.outputDirectory}/example.xhtml -->
        <execution>
          <id>convert-html-to-xhtml</id>
          <goals>
            <goal>html-to-xhtml</goal>
          </goals>
        </execution>
      </executions>
      <configuration>
        <conversions>
          <conversion>
            <htmlInputFilePath>${project.basedir}/example.html</htmlInputFilePath>
            <xhtmlOutputFilePath>${project.build.outputDirectory}/example.xhtml</xhtmlOutputFilePath>
            <!--Optional-->
            <encoding>UTF-8</encoding>
            <tagElementToWrapDocumentIn>div</tagElementToWrapDocumentIn>
          </conversion>
        </conversions>
      </configuration>
    </plugin>
  </plugins>
</build>
```
