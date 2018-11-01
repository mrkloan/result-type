# result-type
> A Result type for the Java programming language.

## Usage

Wrap a value of any type:
```java
Result.ok(1)
    .map(value -> value + 2)
    .map(String::valueOf)
    .flatMap(value -> Result.ok(value + "3"))
    .ifOk(value -> { /* Do something with the value */});
```

Propagate errors safely:
```java
Result.error("Error message")
    .mapError(IllegalArgumentException::new)
    .ifError(error -> { /* Do something with the error */ });
```

Unwrap the value:
```java
final Result<String, ?> result = Result.ok("Value");

if(result.isOk()) {
    final String value = result.get();
    /* Do something with the value */
}
```

Unwrap the error:
```java
final Result<?, String> result = Result.error("Error message");

if(result.isError()) {
    final String error = result.getError();
    /* Do something with the error */
}
```

Wrap safely by supplying a fallback error:  
```java
Result.ofNullable(someVariable, NullPointerException::new);
```

Unwrap safely by supplying a fallback value:
```java
final Result<Integer, String> result = Result.error("Error message");
final Integer value = result.getOrElse(() -> 7);
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
	implementation 'com.github.MrKloan:result-type:1.0'
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
		<version>1.0</version>
	</dependency>
</dependencies>
```