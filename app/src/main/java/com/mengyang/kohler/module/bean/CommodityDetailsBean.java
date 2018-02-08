package com.mengyang.kohler.module.bean;

import java.util.List;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/8
 */

public class CommodityDetailsBean {

    /**
     * attrList : [{"attrValue":"86mm","categoryComAttrName":"出水嘴高度（毫米）","keyName":"attr_3"},{"attrValue":"高雅","categoryComAttrName":"产品定位","keyName":"attr_12"},{"attrValue":"采用高科技镀层技术，加入科勒独有的手工镀层处理技巧，颜色及质地富有变化，使浴室极富个性，彰显个人品位/r/n铸铜本体、陶瓷阀芯 ，优良材质，经久耐用/r/n抛光镀铬表面，强力耐腐蚀/r/n","categoryComAttrName":"亮点","keyName":"detail_highlight"}]
     * pdfList : [{"fileName":"http://files.uat.kohler.com.cn/pdfs/k_394t_4.pdf","productPdfName":"尺寸图"}]
     * proDetail : {"categoryMetaDataId":5,"categoryName":"连体座便器","collectionName":"KATHRYN® 凯瑟琳","detailImage1Url1":"http://s7d4.scene7.com/is/image/kohlerchina/232x200%2D1?$232x200$&$Gradient=kohlerchina%2Fgradient%20%2D%20232x200&$Shadow=kohlerchina%2FBlank%20%2D%202&defaultImage=defaultsquare4&$Product=is{kohlerchina%2FK-3940-0_01}&$Badge=kohlerchina%2FBlank%20%2D%202","detailImage1Url2":"","detailImage1Url3":"","detailImage1Url4":"","detailImage1Url5":"","isDefault":"1","listImageUrl":"http://s7d4.scene7.com/is/image/kohlerchina/232x200%2D1?$232x200$&$Gradient=kohlerchina%2Fgradient%20%2D%20232x200&$Shadow=kohlerchina%2FBlank%20%2D%202&defaultImage=defaultsquare4&$Product=is{kohlerchina%2FK-3940-0_01}&$Badge=kohlerchina%2FBlank%20%2D%202","productCode":"K-3940-0","productName":"KATHRYN®凯瑟琳","seoDescriptionMobile":"连体座便器,KATHRYN®凯瑟琳\"五级旋风\"连体座便器,K-3940-0","skuCode":"K-3940-0","tempDetailImage1Url1":"http://s7d4.scene7.com/is/image/kohlerchina/K-3940-0_01","tempDetailImage1Url2":"","tempDetailImage1Url3":"","tempDetailImage1Url4":"","tempDetailImage1Url5":"","tempListImageUrl":"http://s7d4.scene7.com/is/image/kohlerchina/K-3940-0_01"}
     * skuAttrList : [{"attrValue":"2BZ/典雅黑","categorySkuAttrName":"颜色/表面处理工艺","keyName":"sku_attr_1","skuImageName":"http://files.uat.kohler.com.cn/images/Color-2BZ.jpg"}]
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
         * categoryMetaDataId : 5
         * categoryName : 连体座便器
         * collectionName : KATHRYN® 凯瑟琳
         * detailImage1Url1 : http://s7d4.scene7.com/is/image/kohlerchina/232x200%2D1?$232x200$&$Gradient=kohlerchina%2Fgradient%20%2D%20232x200&$Shadow=kohlerchina%2FBlank%20%2D%202&defaultImage=defaultsquare4&$Product=is{kohlerchina%2FK-3940-0_01}&$Badge=kohlerchina%2FBlank%20%2D%202
         * detailImage1Url2 :
         * detailImage1Url3 :
         * detailImage1Url4 :
         * detailImage1Url5 :
         * isDefault : 1
         * listImageUrl : http://s7d4.scene7.com/is/image/kohlerchina/232x200%2D1?$232x200$&$Gradient=kohlerchina%2Fgradient%20%2D%20232x200&$Shadow=kohlerchina%2FBlank%20%2D%202&defaultImage=defaultsquare4&$Product=is{kohlerchina%2FK-3940-0_01}&$Badge=kohlerchina%2FBlank%20%2D%202
         * productCode : K-3940-0
         * productName : KATHRYN®凯瑟琳
         * seoDescriptionMobile : 连体座便器,KATHRYN®凯瑟琳"五级旋风"连体座便器,K-3940-0
         * skuCode : K-3940-0
         * tempDetailImage1Url1 : http://s7d4.scene7.com/is/image/kohlerchina/K-3940-0_01
         * tempDetailImage1Url2 :
         * tempDetailImage1Url3 :
         * tempDetailImage1Url4 :
         * tempDetailImage1Url5 :
         * tempListImageUrl : http://s7d4.scene7.com/is/image/kohlerchina/K-3940-0_01
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
        private String tempDetailImage1Url1;
        private String tempDetailImage1Url2;
        private String tempDetailImage1Url3;
        private String tempDetailImage1Url4;
        private String tempDetailImage1Url5;
        private String tempListImageUrl;

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

        public String getTempDetailImage1Url1() {
            return tempDetailImage1Url1;
        }

        public void setTempDetailImage1Url1(String tempDetailImage1Url1) {
            this.tempDetailImage1Url1 = tempDetailImage1Url1;
        }

        public String getTempDetailImage1Url2() {
            return tempDetailImage1Url2;
        }

        public void setTempDetailImage1Url2(String tempDetailImage1Url2) {
            this.tempDetailImage1Url2 = tempDetailImage1Url2;
        }

        public String getTempDetailImage1Url3() {
            return tempDetailImage1Url3;
        }

        public void setTempDetailImage1Url3(String tempDetailImage1Url3) {
            this.tempDetailImage1Url3 = tempDetailImage1Url3;
        }

        public String getTempDetailImage1Url4() {
            return tempDetailImage1Url4;
        }

        public void setTempDetailImage1Url4(String tempDetailImage1Url4) {
            this.tempDetailImage1Url4 = tempDetailImage1Url4;
        }

        public String getTempDetailImage1Url5() {
            return tempDetailImage1Url5;
        }

        public void setTempDetailImage1Url5(String tempDetailImage1Url5) {
            this.tempDetailImage1Url5 = tempDetailImage1Url5;
        }

        public String getTempListImageUrl() {
            return tempListImageUrl;
        }

        public void setTempListImageUrl(String tempListImageUrl) {
            this.tempListImageUrl = tempListImageUrl;
        }
    }

    public static class AttrListBean {
        /**
         * attrValue : 86mm
         * categoryComAttrName : 出水嘴高度（毫米）
         * keyName : attr_3
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
         * fileName : http://files.uat.kohler.com.cn/pdfs/k_394t_4.pdf
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
         * attrValue : 2BZ/典雅黑
         * categorySkuAttrName : 颜色/表面处理工艺
         * keyName : sku_attr_1
         * skuImageName : http://files.uat.kohler.com.cn/images/Color-2BZ.jpg
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
