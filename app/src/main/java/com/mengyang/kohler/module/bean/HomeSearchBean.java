package com.mengyang.kohler.module.bean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/2
 */

public class HomeSearchBean {
    /**
     * attrList : [{"attrValue":"挂墙式","categoryComAttrName":"装置类型","keyName":"attr_22"},{"attrValue":"409x95x98mmmm","categoryComAttrName":"尺寸（毫米）","keyName":"attr_28"},{"attrValue":"皂筒容量750ML，AA（1.5V）碱性电池4节","categoryComAttrName":"说明","keyName":"attr_19"},{"attrValue":"<ul class=\"product_intro list-paddingleft-2\"><li><p>荣获IF和红点两项设计大奖，彰显高端气质<\/p><\/li><li><p>国家技术专利防卡液滴漏设计，避免沾染台面，确保使用环境清洁<\/p><\/li><li><p>自动感应，精准给皂<\/p><\/li><li><p>LED灯光提醒出液状态与低电压，便于使用及维护<\/p><\/li><li><p>泡沫/液体两种出液方式可供选择，满足不同定位需求<\/p><\/li><\/ul>","categoryComAttrName":"亮点","keyName":"detail_highlight"}]
     * pdfList : [{"fileName":"1496152667317_K-20001T-S.pdf","productPdfName":"尺寸图"},{"fileName":"1496152667317_K-20001T-S.pdf","productPdfName":"安装说明书"}]
     * proDetail : {"categoryMetaDataId":161,"categoryName":"给皂器","collectionName":"STRAYT™ 斯磊","detailImage1Url1":"http://s7d4.scene7.com/is/image/kohlerchina/232x200%2D1?$232x200$&$Gradient=kohlerchina%2Fgradient%20%2D%20232x200&$Shadow=kohlerchina%2FBlank%20%2D%202&defaultImage=defaultsquare4&$Product=is{kohlerchina%2FK-20002T-S_01}&$Badge=kohlerchina%2FBlank%20%2D%202","detailImage1Url2":"","detailImage1Url3":"","detailImage1Url4":"","detailImage1Url5":"","isDefault":"1","listImageUrl":"http://s7d4.scene7.com/is/image/kohlerchina/232x200%2D1?$232x200$&$Gradient=kohlerchina%2Fgradient%20%2D%20232x200&$Shadow=kohlerchina%2FBlank%20%2D%202&defaultImage=defaultsquare4&$Product=is{kohlerchina%2FK-20002T-S_01}&$Badge=kohlerchina%2FBlank%20%2D%202","productCode":"K-20002T-S","productName":"STRAYT™ 斯磊自动感应液体给皂器","seoDescriptionMobile":"STRAYT™ 斯磊自动感应液体给皂器","skuCode":"K-20002T-S"}
     * skuAttrList : [{"attrValue":"S/不锈钢抛光","categorySkuAttrName":"颜色/表面处理工艺","keyName":"sku_attr_1","skuImageName":"Color-S.jpg"}]
     */

    private ProDetailBean proDetail;
    private List<AttrListBean> attrList;
    private List<PdfListBean> pdfList;
    private List<SkuAttrListBean> skuAttrList;

    public ProDetailBean getProDetail() {
        return proDetail;
    }

    public void setProDetail(ProDetailBean proDetail) {
        this.proDetail = proDetail;
    }

    public List<AttrListBean> getAttrList() {
        return attrList;
    }

    public void setAttrList(List<AttrListBean> attrList) {
        this.attrList = attrList;
    }

    public List<PdfListBean> getPdfList() {
        return pdfList;
    }

    public void setPdfList(List<PdfListBean> pdfList) {
        this.pdfList = pdfList;
    }

    public List<SkuAttrListBean> getSkuAttrList() {
        return skuAttrList;
    }

    public void setSkuAttrList(List<SkuAttrListBean> skuAttrList) {
        this.skuAttrList = skuAttrList;
    }

    public static class ProDetailBean {
        /**
         * categoryMetaDataId : 161
         * categoryName : 给皂器
         * collectionName : STRAYT™ 斯磊
         * detailImage1Url1 : http://s7d4.scene7.com/is/image/kohlerchina/232x200%2D1?$232x200$&$Gradient=kohlerchina%2Fgradient%20%2D%20232x200&$Shadow=kohlerchina%2FBlank%20%2D%202&defaultImage=defaultsquare4&$Product=is{kohlerchina%2FK-20002T-S_01}&$Badge=kohlerchina%2FBlank%20%2D%202
         * detailImage1Url2 :
         * detailImage1Url3 :
         * detailImage1Url4 :
         * detailImage1Url5 :
         * isDefault : 1
         * listImageUrl : http://s7d4.scene7.com/is/image/kohlerchina/232x200%2D1?$232x200$&$Gradient=kohlerchina%2Fgradient%20%2D%20232x200&$Shadow=kohlerchina%2FBlank%20%2D%202&defaultImage=defaultsquare4&$Product=is{kohlerchina%2FK-20002T-S_01}&$Badge=kohlerchina%2FBlank%20%2D%202
         * productCode : K-20002T-S
         * productName : STRAYT™ 斯磊自动感应液体给皂器
         * seoDescriptionMobile : STRAYT™ 斯磊自动感应液体给皂器
         * skuCode : K-20002T-S
         */

        private int categoryMetaDataId;
        private String categoryName;
        private String collectionName;
        private String detailImage1Url1;
        private String detailImage1Url2;
        private String detailImage1Url3;
        private String detailImage1Url4;
        private String detailImage1Url5;
        private String isDefault;
        private String listImageUrl;
        private String productCode;
        private String productName;
        private String seoDescriptionMobile;
        private String skuCode;

        public int getCategoryMetaDataId() {
            return categoryMetaDataId;
        }

        public void setCategoryMetaDataId(int categoryMetaDataId) {
            this.categoryMetaDataId = categoryMetaDataId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCollectionName() {
            return collectionName;
        }

        public void setCollectionName(String collectionName) {
            this.collectionName = collectionName;
        }

        public String getDetailImage1Url1() {
            return detailImage1Url1;
        }

        public void setDetailImage1Url1(String detailImage1Url1) {
            this.detailImage1Url1 = detailImage1Url1;
        }

        public String getDetailImage1Url2() {
            return detailImage1Url2;
        }

        public void setDetailImage1Url2(String detailImage1Url2) {
            this.detailImage1Url2 = detailImage1Url2;
        }

        public String getDetailImage1Url3() {
            return detailImage1Url3;
        }

        public void setDetailImage1Url3(String detailImage1Url3) {
            this.detailImage1Url3 = detailImage1Url3;
        }

        public String getDetailImage1Url4() {
            return detailImage1Url4;
        }

        public void setDetailImage1Url4(String detailImage1Url4) {
            this.detailImage1Url4 = detailImage1Url4;
        }

        public String getDetailImage1Url5() {
            return detailImage1Url5;
        }

        public void setDetailImage1Url5(String detailImage1Url5) {
            this.detailImage1Url5 = detailImage1Url5;
        }

        public String getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(String isDefault) {
            this.isDefault = isDefault;
        }

        public String getListImageUrl() {
            return listImageUrl;
        }

        public void setListImageUrl(String listImageUrl) {
            this.listImageUrl = listImageUrl;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getSeoDescriptionMobile() {
            return seoDescriptionMobile;
        }

        public void setSeoDescriptionMobile(String seoDescriptionMobile) {
            this.seoDescriptionMobile = seoDescriptionMobile;
        }

        public String getSkuCode() {
            return skuCode;
        }

        public void setSkuCode(String skuCode) {
            this.skuCode = skuCode;
        }
    }

    public static class AttrListBean {
        /**
         * attrValue : 挂墙式
         * categoryComAttrName : 装置类型
         * keyName : attr_22
         */

        private String attrValue;
        private String categoryComAttrName;
        private String keyName;

        public String getAttrValue() {
            return attrValue;
        }

        public void setAttrValue(String attrValue) {
            this.attrValue = attrValue;
        }

        public String getCategoryComAttrName() {
            return categoryComAttrName;
        }

        public void setCategoryComAttrName(String categoryComAttrName) {
            this.categoryComAttrName = categoryComAttrName;
        }

        public String getKeyName() {
            return keyName;
        }

        public void setKeyName(String keyName) {
            this.keyName = keyName;
        }
    }

    public static class PdfListBean {
        /**
         * fileName : 1496152667317_K-20001T-S.pdf
         * productPdfName : 尺寸图
         */

        private String fileName;
        private String productPdfName;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getProductPdfName() {
            return productPdfName;
        }

        public void setProductPdfName(String productPdfName) {
            this.productPdfName = productPdfName;
        }
    }

    public static class SkuAttrListBean {
        /**
         * attrValue : S/不锈钢抛光
         * categorySkuAttrName : 颜色/表面处理工艺
         * keyName : sku_attr_1
         * skuImageName : Color-S.jpg
         */

        private String attrValue;
        private String categorySkuAttrName;
        private String keyName;
        private String skuImageName;

        public String getAttrValue() {
            return attrValue;
        }

        public void setAttrValue(String attrValue) {
            this.attrValue = attrValue;
        }

        public String getCategorySkuAttrName() {
            return categorySkuAttrName;
        }

        public void setCategorySkuAttrName(String categorySkuAttrName) {
            this.categorySkuAttrName = categorySkuAttrName;
        }

        public String getKeyName() {
            return keyName;
        }

        public void setKeyName(String keyName) {
            this.keyName = keyName;
        }

        public String getSkuImageName() {
            return skuImageName;
        }

        public void setSkuImageName(String skuImageName) {
            this.skuImageName = skuImageName;
        }
    }
}
