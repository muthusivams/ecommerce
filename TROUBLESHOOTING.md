# Java Compiler Error Troubleshooting

## Symptom

Maven build fails with:

```text
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.8.1:compile ...
Fatal error compiling: java.lang.NoSuchFieldError:
Class com.sun.tools.javac.tree.JCTree$JCImport does not have member field
'com.sun.tools.javac.tree.JCTree qualid'
```

## Root cause

This is typically a **JDK + annotation processor incompatibility**, most commonly caused by an older Lombok version running on newer JDKs (especially JDK 21+).

## Recommended fixes

1. Upgrade Lombok to a version that supports your JDK:
   - `1.18.30+` for JDK 21 compatibility (or latest available).
2. Upgrade Maven Compiler Plugin to a recent version (for example `3.11.0`).
3. Ensure Maven and Java versions align with your project target.

## Example `pom.xml` updates

```xml
<properties>
  <maven.compiler.release>21</maven.compiler.release>
  <lombok.version>1.18.32</lombok.version>
</properties>

<dependencies>
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>${lombok.version}</version>
    <scope>provided</scope>
  </dependency>
</dependencies>

<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-compiler-plugin</artifactId>
      <version>3.11.0</version>
      <configuration>
        <release>${maven.compiler.release}</release>
        <annotationProcessorPaths>
          <path>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.version}</version>
          </path>
        </annotationProcessorPaths>
      </configuration>
    </plugin>
  </plugins>
</build>
```

## Quick verification commands

```bash
mvn -v
java -version
mvn -DskipTests clean compile
```

If the error persists, check for transitive/duplicate Lombok versions:

```bash
mvn dependency:tree -Dincludes=org.projectlombok:lombok
```
