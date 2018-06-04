package us.koller.spotifyutil.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO for wrapper for a PagingObject from the Spotify API
 * */
@SuppressWarnings({"unused", "WeakerAccess"})
public class PagingObject<T> implements Parcelable {

    private String href;
    private List<T> items;
    private int limit;
    private String next;
    private int offset;
    private String previous;
    private int total;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public List<T> getItems() {
        return items;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(href);
        //store generics List
        if (items != null) {
            parcel.writeInt(items.size());
            Class<?> objectsType = items.get(0).getClass();
            parcel.writeSerializable(objectsType);
            parcel.writeList(items);
        }
        parcel.writeInt(limit);
        parcel.writeString(next);
        parcel.writeInt(offset);
        parcel.writeString(previous);
        parcel.writeInt(total);
    }

    public final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public PagingObject createFromParcel(Parcel parcel) {
            PagingObject pagingObject = new PagingObject();
            parcel.writeString(href);
            parcel.writeList(items);
            parcel.writeInt(limit);
            parcel.writeString(next);
            parcel.writeInt(offset);
            parcel.writeString(previous);
            parcel.writeInt(total);

            pagingObject.setHref(parcel.readString());
            // read generics List
            Class<?> type = (Class<?>) parcel.readSerializable();
            //noinspection unchecked
            pagingObject.setItems(new ArrayList<>(parcel.readInt()));
            parcel.readList(pagingObject.getItems(), type.getClassLoader());

            pagingObject.setLimit(parcel.readInt());
            pagingObject.setNext(parcel.readString());
            pagingObject.setOffset(parcel.readInt());
            pagingObject.setPrevious(parcel.readString());
            pagingObject.setTotal(parcel.readInt());
            return pagingObject;
        }

        @Override
        public PagingObject[] newArray(int i) {
            return new PagingObject[i];
        }
    };
}
