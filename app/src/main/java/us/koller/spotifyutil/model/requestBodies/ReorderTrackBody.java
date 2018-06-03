package us.koller.spotifyutil.model.requestBodies;

/**
 * POJO for the PUT-Body of a ReorderTrackBody Operation
 * **/
@SuppressWarnings("unused")
public class ReorderTrackBody {

    private int range_start;
    private int range_length;
    private int insert_before;

    public int getRange_start() {
        return range_start;
    }

    public void setRange_start(int range_start) {
        this.range_start = range_start;
    }

    public int getRange_length() {
        return range_length;
    }

    public void setRange_length(int range_length) {
        this.range_length = range_length;
    }

    public int getInsert_before() {
        return insert_before;
    }

    public void setInsert_before(int insert_before) {
        this.insert_before = insert_before;
    }

    @Override
    public String toString() {
        return "ReorderTrackBody{" +
                "range_start=" + range_start +
                ", range_length=" + range_length +
                ", insert_before=" + insert_before +
                '}';
    }
}
