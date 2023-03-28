# translation-app

## About
Данный код является реализацией REST-сервиса, отвечающим за перевод текстовой строки с одного языка на другой.

В качестве входных данных выступает json-объект. имеющий в себе одно поле текстового формата. Значения исходного и результирующего языков передаются параметрами запроса.

## Endpoint

#### POST/translate

    URI parameters
        *from
        *to 




## Database
В базу данных записывается следующая информация:

<img width="692" alt="Screenshot 2023-03-27 at 23 48 29" src="https://user-images.githubusercontent.com/76592052/228064316-b0c1c233-0fee-494d-a7da-04204158bccc.png">

В ходе разработки использовались следующие технологии:

    1) Java 17
    2) Spring Framework
    3) Spting JDBC (JDBCTemplate)
    4) H2 inmemory database
    5) Внешний API для перевода - YANDEX TRANSLATE API
    6) SQL 
    7) Lombok
