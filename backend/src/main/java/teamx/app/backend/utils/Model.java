package teamx.app.backend.utils;

public interface Model<D extends DTO> {
    D toDTO();
}