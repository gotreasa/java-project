# Contributing

:+1::tada: First off, thanks for taking the time to contribute! :tada::+1:

The following is a set of guidelines for contributing to Java Project.
These are mostly guidelines, not rules. Use your best judgment, and feel free to propose changes to this document in a pull request.

## How to contribute to Java Project

To start to develop within this project open up the NOTES.md and note down what you're going to work on during the pompodoro.
Follow the instructions below to run the tests.
Follow the TDD rules of write a failing test, then write just enough code to get it to pass, and commit.
If you can clarify the code without changing the functionality then refactor, keeping the tests passing.

### Test Driven Development

To get 100% test coverage we do test driven development. To run the tests run:

```bash
./gradlew test
```

If you want to run the tests in continuous mode (aka watch mode),
add one of the `-t` or `--continuous` flags:

```bash
./gradlew test -t
```

You can exit the continuous mode with `CTRL` + `D`.

### Integration Tests

You should place your integration tests into to the `src/it/java` folder,
following the same package structure you do with the production and unit test code.

To run the integration tests, execute the following gradle task:

```bash
./gradlew integrationTest
```

Like for unit tests, continuous mode is available for integration tests, too:

```bash
./gradlew integrationTest -t
```

### Test Coverage

The coverage report is available at `build/reports/jacoco/test/html/index.html`.

It is automatically generated after running the tests and it combines unit
test and integration test coverage.
