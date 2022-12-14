# ProfiTSoft_HomeWork3
# Task1
Модифікувати завдання з попереднього блоку (у папці є перелік текстових файлів, кожен із яких є "зліпок" БД порушень правил дорожнього руху протягом певного року...) таким чином, 
щоб різні файли з папки завантажувалися асинхронно за допомогою пулу потоків, але загальна статистика однаково формувалася.
Використовувати CompletableFuture і ExecutorService. Порівняти швидкодію програми, коли не використовується паралелізація, коли використовується 2 потоки, 4 і 8.
Файлів в папці повинно бути 10+, їх розмір повинен бути достатнім, щоб заміри були цікавими. Результати порівняння прикласти коментарем до виконаного завдання.

# Task2
Розробити клас-утиліту зі статичним методом, який приймає параметр типу Class і шлях до properties-файлу,
створює об'єкт цього класу, наповнює його атрибути значеннями з properties-файлу (використовуючи сетери) і повертає цей об'єкт.

Приклад сигнатури метода:
public static <T>T loadFromProperties(Class<T> cls, Path propertiesPath)

Properties-файл має формат:
stringProperty=value1
numberProperty=10
timeProperty=29.11.2022 18:30

Метод має вміти парсити строки, цілі числа (int та Integer) і дати з часом (Instant).
Створити анотацію @Property, за допомогою якої можна було б опціонально змінити назву property (якщо назва атрибуту класу відрізняється від ключа property в файлі), 
а для полів типу дата/час задати - очікуваний формат дати.
Наприклад, клас, який ми читаємо з файлу вище, міг би мати такі атрибути:

private String stringProperty;

@Property(name="numberProperty")
private int myNumber

@Property(format="dd.MM.yyyy tt:mm")
private Instant timeProperty;

Складені ключі (prefix.propertyKey) в цьому завданні можуть використовуватися тільки якщо ми задаємо їх в анотації @Property(name="prefix.propertyKey").
Якщо щось розпарсити/заповнити не вдалося (не підтримується тип, неправильний формат, тощо), метод повинен кидати відповідний Exception.
Створити unit-тести для цього метода.

