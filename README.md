# translation-app

## About
Данный код является реализацией REST-сервиса, отвечающим за перевод текстовой строки с одного языка на другой.

В качестве входных данных выступает json-объект. имеющий в себе одно поле текстового формата. Значения исходного и результирующего языков передаются параметрами запроса.

## Endpoint

### POST/translate

***
   RequestParams
   * _from_ - язык, с которого требуется выпонить перевод
   * _to_ - язык, на которогый требуется выпонить перевод

Входные данные - json следующего вида:

```java
{
    "content" : "Today is perfect weather! Yeah"
}
```
Выходные данные:

```java
{
    "translatedContent": "Сегодня идеальная погода! Да "
}
```
## Test Program
Чтобы протестировать работу сервиса достаточно в `Postman` послать запрос по следующему Request URL с желаемым телом запроса:
```
   http://localhost:8090/translate?from=en&to=ru
```

## Database
База данных имеет следующую реализацию:

<img width="692" alt="Screenshot 2023-03-27 at 23 48 29" src="https://user-images.githubusercontent.com/76592052/228064316-b0c1c233-0fee-494d-a7da-04204158bccc.png">

В ходе разработки использовались следующие технологии:

    1) Java 17
    2) Spring Framework
    3) Spting JDBC (JDBCTemplate)
    4) H2 inmemory database
    5) Внешний API для перевода - YANDEX TRANSLATE API
    6) SQL 
    7) Lombok
