package com.example.p79xmlpullparser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

//В этом уроке:
//- парсим XML с помощью XmlPullParser

//Напишем приложение, которое возьмет xml-файл и разберет его на тэги и аттрибуты.
public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String tmp = "";

        try {

            //мы получаем XmlPullParser с помощью метода prepareXpp и начинаем его разбирать
            XmlPullParser xpp = prepareXpp();

            //в цикле while мы запускаем прогон документа, пока не достигнем конца - END_DOCUMENT.
            // Прогон обеспечивается методом next в конце цикла while.
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {

                // В switch мы проверяем на каком элементе остановился парсер.
                switch (xpp.getEventType()) {
                    // начало документа
                    case XmlPullParser.START_DOCUMENT:
                        Log.d(LOG_TAG, "START_DOCUMENT");
                        break;
                    // начало тэга
                    case XmlPullParser.START_TAG:
                        Log.d(LOG_TAG, "START_TAG: name = " + xpp.getName()
                                + ", depth = " + xpp.getDepth() + ", attrCount = "
                                + xpp.getAttributeCount());
                        tmp = "";
                        for (int i = 0; i < xpp.getAttributeCount(); i++) {
                            tmp = tmp + xpp.getAttributeName(i) + " = "
                                    + xpp.getAttributeValue(i) + ", ";
                        }
                        if (!TextUtils.isEmpty(tmp))
                            Log.d(LOG_TAG, "Attributes: " + tmp);
                        break;
                    // конец тэга
                    case XmlPullParser.END_TAG:
                        Log.d(LOG_TAG, "END_TAG: name = " + xpp.getName());
                        break;
                    // содержимое тэга
                    case XmlPullParser.TEXT:
                        Log.d(LOG_TAG, "text = " + xpp.getText());
                        break;

                    default:
                        break;
                }
                // следующий элемент
                xpp.next();
            }
            Log.d(LOG_TAG, "END_DOCUMENT");

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //В методе prepareXpp мы подготавливаем XmlPullParser.
    // Для этого вытаскиваем данные из папки res/xml.
    // Это аналогично вытаскиванию строк или картинок – сначала получаем доступ к ресурсам (getResources),
    // затем вызываем метод, соответствующий ресурсу.
    // В нашем случае это - метод getXml. Но возвращает он не xml-строку , а готовый XmlPullParser.
    XmlPullParser prepareXpp() {
        return getResources().getXml(R.xml.data);
    }
}
