# result-type
> A Result type for the Java programming language.

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
	implementation 'com.github.MrKloan:reflection:1.0'
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
		<artifactId>reflection</artifactId>
		<version>1.0</version>
	</dependency>
</dependencies>
```