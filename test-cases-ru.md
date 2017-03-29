В данном документе содержится список тестов для калькулятора иностранных валют, расположенного по адресу http://www.sberbank.ru/ru/quotes/converter.

Список тестов (отмеченные будут выполнены):
- [x] В обоих выподащих списках возможно выбрать следующие валюты: RUR, CHF, EUR, GBP, JPY, USD
- [x] При выборе валюты, уже выбранной в другом выпадающем списке, валюта в другом списке изменяется на валюту в данном списке до применения выбора
- [x] Умножение валют с учетом текущего курса купли/продажи происходит правильно, в пределах заданной погрешности. Проверить для множителей 5, 7, 10, 13, 23, 50, 100, 2351 с погрешностью e = 0.01
- Комбинация выбранных радио-кнопок исключает невозможные варианты (напр. при выборе "Получение" - "Выдать наличные" нельзя выбрать "Интернет-банк" в качестве "Способа обмена")
- Невозможно выбрать время и дату старше текущей