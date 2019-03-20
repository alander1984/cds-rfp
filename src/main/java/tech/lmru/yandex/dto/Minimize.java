package tech.lmru.yandex.dto;

public enum Minimize {

    cost("cost"),
    time("time"),
    distance("distance"),
    combined("combined");

    private String value;

    Minimize(final String value) {
        this.value = value;
    }

    /**
     * Получение значение текущего значения перечисления
     * @return
     */
    public String value() {
        return value;
    }

    /**
     * Преобразование к строке текущего значения перечисления
     * @return
     */
    @Override
    public String toString() {
        return this.value();
    }
}
