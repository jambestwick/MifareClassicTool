package de.syss.MifareClassicTool.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import kotlin.BuilderInference;

/**
 * <p>文件描述：商品类<p>
 * <p>作者：jambestwick<p>
 * <p>创建时间：2024/2/2<p>
 * <p>更新时间：2024/2/2<p>
 * <p>版本号：<p>
 * <p>邮箱：jambestwick@126.com<p>
 */
@Entity(tableName = "product")
public class Product {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;
    @NonNull
    private String scanCode;//二维码或条码13位
    private String countryCode;//国家编码(第1-3位，中国690~699)
    private String manufacturer;//厂商编号(第4-7位，4位)
    private String category;//商品类目(第8-12位，厂商定义，类目+商品)
    private String productName;//商品名称
    @NonNull
    private Integer count;//商品数量
    @NonNull
    private Integer price;//单价（分）
    private String remark;//备注

    public Product(@NonNull String scanCode, String countryCode, String manufacturer, String category, String productName, @NonNull Integer count, @NonNull Integer price, String remark) {
        this.scanCode = scanCode;
        this.countryCode = countryCode;
        this.manufacturer = manufacturer;
        this.category = category;
        this.productName = productName;
        this.count = count;
        this.price = price;
        this.remark = remark;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    @NonNull
    public String getScanCode() {
        return scanCode;
    }

    public void setScanCode(@NonNull String scanCode) {
        this.scanCode = scanCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @NonNull
    public Integer getCount() {
        return count;
    }

    public void setCount(@NonNull Integer count) {
        this.count = count;
    }

    @NonNull
    public Integer getPrice() {
        return price;
    }

    public void setPrice(@NonNull Integer price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", scanCode='" + scanCode + '\'' +
            ", countryCode='" + countryCode + '\'' +
            ", manufacturer='" + manufacturer + '\'' +
            ", category='" + category + '\'' +
            ", productName='" + productName + '\'' +
            ", count=" + count +
            ", price=" + price +
            ", remark='" + remark + '\'' +
            '}';
    }
}
