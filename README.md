**Проблема**: Выгрузить из базы Oracle данные, хранящиеся в виде BLOB, и перенести в Рostgresql, в которой отсутствует тип данных BLOB.

Есть готовая программа "Data Base Extractor", которая решает проблему. Конфиг программы, в котором указывается что брать, откуда, какими SQL запросами, лежит в .xml.

**Задача**: Детально разобраться с кодом программы "Data Base Extractor". Создать приложение на Java Servlet, которое все данные для конфига берёт не из .xml, а через Web. Поднять базу и перенести тестовые данные.

Формы Web страничек, которые должны быть созданы, представлены в файлах Task1.jpg Task2.jpg Task3.jpg и т.д.