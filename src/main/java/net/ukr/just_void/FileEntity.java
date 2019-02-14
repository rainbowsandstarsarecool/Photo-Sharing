package net.ukr.just_void;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Entity
public class FileEntity {

    @Id
    @GeneratedValue
    private long id;

    private int hash;

    private Date date;

    @Column(columnDefinition = "LONGBLOB")
    private byte[] fileData;

    public FileEntity() {
    }

    public FileEntity(Date date, byte[] fileData) {
        this.date = date;
        this.fileData = fileData;
        refreshHash();
    }

    public int getHash() {
        return hash;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
        refreshHash();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        refreshHash();
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
        refreshHash();
    }

    public void refreshHash() {
        this.hash = hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileEntity)) return false;
        FileEntity that = (FileEntity) o;
        return id == that.id &&
                Objects.equals(date, that.date) &&
                Arrays.equals(fileData, that.fileData);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, date);
        result = 31 * result + Arrays.hashCode(fileData);
        return result;
    }
}
