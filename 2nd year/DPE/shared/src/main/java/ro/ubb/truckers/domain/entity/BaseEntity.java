package ro.ubb.truckers.domain.entity;

public abstract class BaseEntity<I> {
    private I id;

    BaseEntity(I id) {
        this.id = id;
    }

    public I getId() {
        return id;
    }

    public void setId(I id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }

    public abstract String toJSON();
}
