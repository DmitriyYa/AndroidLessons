package com.example.p69parcelable;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by dima on 11.07.17.
 */

public class MyObject implements Parcelable {

    final static String LOG_TAG = "myLogs";

    public String s;
    public int i;

    // обычный конструктор
    public MyObject(String _s, int _i) {
        Log.d(LOG_TAG, "MyObject(String _s, int _i)");
        s = _s;
        i = _i;
    }

    // конструктор, считывающий данные из Parcel
    //Конструктор MyObject(Parcel parcel) принимает на вход Parcel и заполняет объект данными из него.
    //Этот метод использовался нами чуть ранее в CREATOR.createFromParcel.
    private MyObject(Parcel in) {
        Log.d(LOG_TAG, "MyObject(Parcel parcel)");
        s = in.readString();
        i = in.readInt();
    }

    //CREATOR типа Parcelable.Creator<MyObject> используется для создания экземпляра нашего MyObject
    //и заполнения его данными из Parcel.
    public static final Creator<MyObject> CREATOR = new Creator<MyObject>() {
        // распаковываем объект из Parcel
        //Для этого используется его метод createFromParcel, который мы должны реализовать.
        // На вход нам дается Parcel, а вернуть мы должны готовый MyObject.
        // В нашем примере мы используем здесь конструктор MyObject(Parcel parcel), который реализован чуть выше.
        @Override
        public MyObject createFromParcel(Parcel in) {
            Log.d(LOG_TAG, "createFromParcel");
            return new MyObject(in);
        }

        @Override
        public MyObject[] newArray(int size) {
            return new MyObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    // упаковываем объект в Parcel
    //В методе writeToParcel мы получаем на вход Parcel и упаковываем в него наш объект.
    //Т.е., в нашем случае, помещаем туда переменные s и i.  flags не используем.
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.d(LOG_TAG, "writeToParcel");
        dest.writeString(s);
        dest.writeInt(i);
    }
}
