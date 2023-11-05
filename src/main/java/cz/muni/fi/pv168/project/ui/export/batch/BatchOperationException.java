package cz.muni.fi.pv168.project.ui.export.batch;

public class BatchOperationException extends RuntimeException {
    public BatchOperationException(String message) {
        super(message);
    }

    public BatchOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}