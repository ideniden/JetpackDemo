package com.luoj.jetpackdemo.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class User implements Serializable {

    @PrimaryKey
    public Integer uid;

    @ColumnInfo(name = "name")
    @NonNull
    public String name;

    public User() {
    }

    public User(int uid, @NonNull String name) {
        this.uid = uid;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return uid == user.uid &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, name);
    }

}
