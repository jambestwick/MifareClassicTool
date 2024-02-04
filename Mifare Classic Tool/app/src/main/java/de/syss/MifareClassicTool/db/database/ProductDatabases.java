package de.syss.MifareClassicTool.db.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import de.syss.MifareClassicTool.Common;
import de.syss.MifareClassicTool.db.Product;
import de.syss.MifareClassicTool.db.dao.ProductDao;

/**
 * <p>文件描述：<p>
 * <p>作者：jambestwick<p>
 * <p>创建时间：2024/2/2<p>
 * <p>更新时间：2024/2/2<p>
 * <p>版本号：<p>
 * <p>邮箱：jambestwick@126.com<p>
 */
@Database(entities = {Product.class},version = 1,exportSchema = false)
public abstract class ProductDatabases extends RoomDatabase {
    abstract ProductDao productDao();
    private static ProductDatabases db;

    private static ProductDatabases getInstance() {
        synchronized (ProductDatabases.class) {
            if (db == null) {
                synchronized (Product.class) {
                    db = Room.databaseBuilder(Common.getAppContext(), ProductDatabases.class, "productDB").allowMainThreadQueries().build();
                }
            }
        }
        return db;
    }

    public static ProductDao getUserDao() {
        return getInstance().productDao();
    }
}
