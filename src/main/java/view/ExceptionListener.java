package view;

public interface ExceptionListener {
    void handleExceptionAndShowDialog(Throwable throwable);
    void handleExceptionAndDisplayItInCodeArea(Exception exception);
}
