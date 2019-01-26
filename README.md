# result-type
> A Result type for the Java programming language.

[![](https://jitpack.io/v/MrKloan/result-type.svg)](https://jitpack.io/#MrKloan/result-type)

## Usage

Wrap a value of any type:
```java
Result.ok(1)
      .map(value -> value + 2)
      .map(String::valueOf)
      .flatMap(value -> Result.ok(value + "3"))
      .ifOk(value -> { /* Do something with the value */ });
```

Propagate errors safely:
```java
Result.error(new IllegalArgumentException("Error message"))
      .mapError(IllegalStateException::new)
      .ifError(error -> { /* Do something with the error */ });
```

Unwrap the value:
```java
final Result<String> result = Result.ok("Value");

if(result.isOk()) {
    final String value = result.get();
    /* Do something with the value */
}
```

Unwrap the error:
```java
final Result<String> result = Result.error(new IllegalStateException("Error message"));

if(result.isError()) {
    final Throwable error = result.getError();
    /* Do something with the error */
}
```

Wrap a value safely by supplying a fallback error:  
```java
Result.ofNullable(someVariable, NullPointerException::new);
```

Unwrap safely by supplying a fallback value:
```java
final Result<Integer> result = Result.error(new IllegalStateException("Error message"));
final Integer value = result.getOrElse(() -> 7);
```

Wrap a legacy method that may throw an exception to gracefully integrate it to your railway flow:
```java
public Result<User> findUser(final Id id) {
    return Result
        .of(() -> legacyService.findUser(id))
        .map(user -> legacyService.transform(user));
}
```

Try to recover from an error using a fallback method:
```java
public Result<User> findUser(final Id id) {
    return Result
        .of(() -> legacyService.findUser(id))
        .switchIfError(error -> fallbackSearch(id))
        .map(user -> legacyService.transform(user));
}

private Result<User> fallbackSearch(final Id id) {
    return Result.of(() -> cacheService.findUser(id));
}
```

## Installation

Gradle:
```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

dependencies {
	implementation 'com.github.MrKloan:result-type:1.0.0'
}
```

Maven:
```xml
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>

<dependencies>
	<dependency>
		<groupId>com.github.MrKloan</groupId>
		<artifactId>result-type</artifactId>
		<version>1.0.0</version>
	</dependency>
</dependencies>
```
