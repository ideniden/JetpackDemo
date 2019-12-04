package com.luoj.jetpackdemo;

import android.app.Application;
import android.os.Environment;

import androidx.room.Room;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.flattener.PatternFlattener;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;
import com.luoj.jetpackdemo.data.db.MyDataBase;
import com.luoj.jetpackdemo.data.model.User;

import java.io.File;

public class AppContext extends Application {

    MyDataBase dataBase;

    public MyDataBase getDataBase() {
        return dataBase;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initLog("JetpackDemo");

        dataBase = Room.databaseBuilder(this, MyDataBase.class, "my.db")
                .allowMainThreadQueries()
                .build();
        //添加测试数据
        User[] users = new User[100];
        for (int i = 0; i < users.length; i++) {
            int uid = i + 1;
            users[i] = new User(uid, uid + " Test User");
        }
        dataBase.getUserDao().insertAll(users);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }

    void initLog(String appName) {
        String logDirPath = Environment.getExternalStorageDirectory() + File.separator + appName + File.separator;
        File logDir = new File(logDirPath);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(LogLevel.ALL)
                .tag(appName)                                  // 指定 TAG，默认为 "X-LOG"
//                .t()                                                   // 允许打印线程信息，默认禁止
//                .st(3)                                                 // 允许打印深度为2的调用栈信息，默认禁止
//                .b()                                                   // 允许打印日志边框，默认禁止
                .build();
        com.elvishew.xlog.printer.Printer filePrinter = new FilePrinter                          // 打印日志到文件的打印器
                .Builder(logDirPath)                       // 指定保存日志文件的路径
                .fileNameGenerator(new DateFileNameGenerator())        // 指定日志文件名生成器，默认为 ChangelessFileNameGenerator("log")
//                .backupStrategy(new MyBackupStrategy())              // 指定日志文件备份策略，默认为 FileSizeBackupStrategy(1024 * 1024)
                .logFlattener(new PatternFlattener("{d yyyy-MM-dd HH:mm:ss} {L}/{t}:{m}"))                  // 指定日志平铺器，默认为 DefaultLogFlattener
                .build();
        Printer androidPrinter = new AndroidPrinter();
        XLog.init(config, filePrinter, androidPrinter);
    }

}
