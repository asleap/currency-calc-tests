#currency-calc-tests
A set of tests for the currency calculator located here: http://www.sberbank.ru/ru/quotes/converter.
Test cases are described in [test-cases-ru.md](https://github.com/asleap/currency-calc-tests/blob/master/test-cases-ru.md) file.
Written using JUnit 4, Selenium and Yandex.Allure.
Requirements: maven and Chrome browser.

Manual:
- Clone project and cd to the project derictory.
- Run `mvn clean test` to start testing. An instance of Chrome browser will be launched.
- Run `mvn site` to generate Allure report
- Run `mvn jetty:run` to start Jetty and go to http://localhost:8080 to check the report.
