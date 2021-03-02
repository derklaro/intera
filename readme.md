# intera

![GitHub Workflow Status (branch)](https://img.shields.io/github/workflow/status/derklaro/intera/build/master)
[![JitPack](https://jitpack.io/v/derklaro/intera.svg)](https://jitpack.io/#derklaro/intera)
[![MIT License](https://img.shields.io/badge/license-MIT-blue)](license.txt)

A user-interface library for converting rome numerals to arabic and back written in java.

### Example usage

For basic usage you can just use the default implementation with all common roman counting rules
applied. To obtain the default instance you can simply use:

```java
final Intera intera = Intera.defaults();
```

The default instance is shared, and you don't have to use a local variable to save the instance. All
intera instances are thread save.

An instance of intera only has to methods: `parse` and `write`. `parse` simply converts a roman
number as a string to an arabic number using the known associations defined in the instance (We will
come to that later). `write` converts the arabic letter back to a roman number string. For example:

```java
final int arabic = Intera.defaults().parse("MDCCLXXX"); // 1780
final String roman = Intera.defaults().write(1780); // MDCCLXXX
```

The default number mapping is:

| Rome   | I | V | X  | L  | C   | D   | M    |
|--------|---|---|----|----|-----|-----|------|
| Arabic | 1 | 5 | 10 | 50 | 100 | 500 | 1000 |

You can change this mapping by using the intera-builder:

```java
final Intera intera = Intera.builder()
  .registerAssociation('X', 100)
  .registerAssociation('I', 1000)
  .registerAssociation('M', 10000)
  .build();
```

You can also change the mathematical behaviour of the instance. By default, a maximum of three same
chars can follow each other:
`XXX` is ok (30), `XXXX` (40) has to be `XL` (50 - 10)

This behaviour can be modified in the builder. In this example we set the maximum same chars in a
row to 5. If this value is smaller or equal to 1 the check is disabled:

```java
final Intera intera = Intera.builder()
  .defaultAssociations()
  .maxSameCharsInRow(5)
  .build();
```

There is still one other rule in the roman math system. Subtractions are only allowed in some cases.
You can only subtract by 1 from a 5 or 10, by 10 from a 50 and 100 and by 100 from a 500 and 1000.
These rules can get disabled by using the builder as well as modified. To disable you can simply
use:

```java
final Intera intera = Intera.builder()
  .defaultAssociations()
  .subtractionValidator(SubtractionValidator.disabled())
  .build();
```

To modify the behaviour you can simply use:

```java
final Intera intera = Intera.builder()
  .defaultAssociations()
  .subtractionValidator((number, subtraction) -> number > subtraction)
  .build();
```

The result is a boolean which when `true` indicates that the operation can't be done, `false`
otherwise.

This was a quick go-trough all features of the library, for more information check the
documentation.

### Get intera

The compiled jar file is always included in
the [latest release](https://github.com/derklaro/intera/releases/latest).

For gradle you may use:

```groovy
maven {
  name 'jitpack'
  url 'https://jitpack.io'
}

dependencies {
  implementation 'com.github.derklaro:intera:1.0.1'
}
```

For maven:

```xml
<repository>
  <id>jitpack.io</id>
  <url>https://jitpack.io</url>
</repository>

<dependency>
  <groupId>com.github.derklaro</groupId>
  <artifactId>intera</artifactId>
  <version>1.0.1</version>
</dependency>
```

### Contributing

I appreciate contributions of any type. For any new features or fixes/style changes, please open an
issue

The project is built with Gradle, require at least JDK 8, and use the google checkstyle
configuration. Please make sure all tests pass, license headers are updated, and checkstyle passes
to help us review your contribution.

### License

`intera` is released under the terms of the [MIT License](license.txt).
