package de.syss.MifareClassicTool.db.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import de.syss.MifareClassicTool.db.Product;

/**
 * <p>文件描述：数据库SQL层<p>
 * <p>作者：jambestwick<p>
 * <p>创建时间：2024/2/2<p>
 * <p>更新时间：2024/2/2<p>
 * <p>版本号：<p>
 * <p>邮箱：jambestwick@126.com<p>
 */
@Dao
public interface ProductDao {
    @Insert
    void addProduct(Product product);

    @Delete
    void removeProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Query("select * from product where scanCode=:scanCode")
    Product queryAUser(String scanCode);

    @Query("select * from product  ")
    List<Product> queryProduct();
}
