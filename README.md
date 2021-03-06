﻿# Домашние задания для курса "Разработчик Java" в OTUS

Группа 2018-08

### Преподаватели
Vitaly Chibrikov (Виталий Чибриков)

chibrikov@otus.ru

Vladimir Sonkin (Владимир Сонькин)

vladson@ya.ru

###Студент
Alexey Kuzmichev (Алексей Кузьмичёв)

avk_job@mail.ru

## Задания

### ДЗ 01. Сборка и запуск проекта
<details><summary>Задание</summary>
<p>

* Собрать проект под управлением maven в Intellij IDEA.
* Добавить зависимость на Google Guava/Apache Commons/библиотеку на ваш выбор.
* Использовать библиотечные классы для обработки входных данных.
* Задать имя проекта (project_name) в pom.xml.
* Собрать project_name.jar содержащий все зависимости.
* Проверить, что приложение можно запустить из командной строки.
* Выложить проект на github.
* Создать ветку "obfuscation", изменить в ней pom.xml, так чтобы сборка содержала стадию обфускации байткода.
<p>
</details>

### ДЗ 02. Измерение памяти
<details><summary>Задание</summary>
<p>

* Написать стенд для определения размера объекта.
* Определить размер пустой строки и пустых контейнеров.
* Определить рост размера контейнера от количества элементов в нем.
<p>
</details>

Запуск: `mvn clean package exec:exec -e`

### ДЗ 03. MyArrayList
<details><summary>Задание</summary>
<p>

* Написать свою реализацию ArrayList на основе массива. 
* Проверить, что на ней работают методы java.util.Collections
<p>
</details>

### ДЗ 04. Тестовый фреймворк на аннотациях
<details><summary>Задание</summary>
<p>

* Написать свой тестовый фреймворк. Поддержать аннотации @Test, @Before, @After. 
* Запускать вызовом статического метода с 
1. именем класса с тестами, 
2. именем package в котором надо найти и запустить тесты
<p>
</details>

### ДЗ 05. Измерение активности GC
<details><summary>Задание</summary>
<p>

* Написать приложение, которое следит за сборками мусора и пишет в лог количество сборок каждого типа (young, old) и время которое ушло на сборки в минуту.
* Добиться OutOfMemory в этом приложении через медленное подтекание по памяти (например добавлять элементы в List и удалять только половину).
* Настроить приложение (можно добавлять Thread.sleep(...)) так чтобы оно падало с OOM примерно через 5 минут после начала работы.
* Собрать статистику (количество сборок, время на сборки) по разным типам GC.
<p>
</details>

### ДЗ 06. Cache Engine
<details><summary>Задание</summary>
<p>

* Напишите свой cache engine с soft references.
<p>
</details>


### ДЗ 07. Написать эмулятор АТМ
<details><summary>Задание</summary>
<p>

* Написать эмулятор АТМ (банкомата).

* Объект класса АТМ должен уметь
- принимать банкноты разных номиналов (на каждый номинал должна быть своя ячейка)
- выдавать запрошенную сумму минимальным количеством банкнот или ошибку если сумму нельзя выдать
- выдавать сумму остатка денежных средств
<p>
</details>

### ДЗ 08. ATM Department
<details><summary>Задание</summary>
<p>

* Написать приложение ATM Department:
- Приложение может содержать несколько ATM
- Department может собирать сумму остатков со всех ATM
- Department может инициировать событие – восстановить состояние всех ATM до начального.
  (начальные состояния у разных ATM могут быть разными)
<p>
</details>

### ДЗ 09. JSON Object Writer
<details><summary>Задание</summary>
<p>

* Напишите свой json object writer (object to JSON string) аналогичный gson на основе javax.json или simple-json и Reflection.
* Поддержите массивы объектов и примитивных типов, и коллекции из стандартной библиотеки.
<p>
</details>

### ДЗ 10. myORM
<details><summary>Задание</summary>
<p>

* Создайте в базе таблицу с полями: 
`id bigint(20) NOT NULL auto_increment 
name varchar(255)
age int(3)`

* Создайте абстрактный класс `DataSet`. Поместите `long id` в `DataSet`. 
* Добавьте класс `UserDataSet` (с полями, которые соответствуют таблице) унаследуйте его от `DataSet`. 

* Напишите `Executor`, который сохраняет наследников `DataSet` в базу и читает их из базы по `id` и классу. 

`<T extends DataSet> void save(T user){…}`
`<T extends DataSet> T load(long id, Class<T> clazz){…}`
<p>
</details>

### ДЗ 11. Hibernate ORM
<details><summary>Задание</summary>
<p>

На основе предыдущего ДЗ (myORM):
* Оформить решение в виде DBService (interface DBService, class DBServiceImpl, UsersDAO, UsersDataSet, Executor)
* Не меняя интерфейс DBSerivice сделать DBServiceHibernateImpl на Hibernate.
* Добавить в UsersDataSet поля:

адрес (OneToOne) 

`class AddressDataSet {
private String street;
}`

и телефон* (OneToMany)

`class PhoneDataSet{
private String number;
}`
* Добавить соответствущие датасеты и DAO. 
<p>
</details>

### ДЗ 12. Веб сервер
<details><summary>Задание</summary>
<p>

* Встроить веб сервер в приложение из ДЗ-11. 

* Сделать админскую страницу, на которой можно:
- добавить пользователя, 
- получить имя пользователя по id,
- получить количество пользователей в базе.
<p>
</details>

### ДЗ 13. Многопоточная сортировка
<details><summary>Задание</summary>
<p>

Написать приложение, которое сортирует массив чисел в 4 потоках с использованием библиотеки или без нее.

<p>
</details>

### ДЗ 14. WAR
<details><summary>Задание</summary>
<p>

* Собрать WAR для приложения из ДЗ-12.
* Запустить кэш и DBService как Spring Beans, передавать (inject) их в сервлеты.
* Запустить веб-приложение во внешнем веб сервере.

<p>
</details>


### ДЗ 15. Message System
<details><summary>Задание</summary>
<p>

* Добавить систему обмена сообщениями в веб сервер из ДЗ-12.
* Пересылать сообщения из вебсокета в DBService и обратно.
* Организовать структуру пакетов без циклических зависимостей.

<p>
</details>


### ДЗ 16. Message Server
<details><summary>Задание</summary>
<p>

* Сервер из ДЗ 15 разделить на три приложения: MessageServer, Frontend, DBServer.
* Запускать Frontend и DBServer из MessageServer.
* Сделать MessageServer сокет-сервером, Frontend и DBServer клиентами.
* Пересылать сообщения с Frontend на DBService через MessageServer.

<p>
</details>
