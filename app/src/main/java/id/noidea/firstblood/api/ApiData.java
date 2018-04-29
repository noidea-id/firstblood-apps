package id.noidea.firstblood.api;

/**
 * Class Generic untuk API.
 * @param <T> untuk objek class
 *
 * class ini dibuat karena struktur dari json yang di dapat dari api
 * terdiri dari status dan data
 */
public class ApiData<T> {
    // T stands for "Type"
    private String status;
    private T data;

    public ApiData() {
    }

    public ApiData(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
