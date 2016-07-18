package view;

/**
 * Слушатель исключений (для контроллеров view)
 */
public interface ExceptionListener {
    /**
     * Обрабатывает произошедшие в ui исключения и отображает диалоговое окно
     * @param throwable произошедшее исключение
     */
    void handleExceptionAndShowDialog(Throwable throwable);

    /**
     * Обрабатывает исключения и отображает в консоль
     * @param exception произошедшее исключение
     */
    void handleExceptionAndDisplayItInCodeArea(Exception exception);
}
