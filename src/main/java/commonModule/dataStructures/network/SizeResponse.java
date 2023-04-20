package commonModule.dataStructures.network;

public class SizeResponse implements Response {

    private String size;

    public SizeResponse(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
