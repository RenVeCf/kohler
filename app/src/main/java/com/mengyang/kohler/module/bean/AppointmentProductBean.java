package com.mengyang.kohler.module.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MengYang on 2018/5/17.
 */
public class AppointmentProductBean {
    /**
     * 卫浴产品 : {"一体·超感座便器":["一体·超感座便器"],"座便器":["连体座便器","分体座便器"],"C³清舒宝智能座便盖":["C³清舒宝智能座便盖"],"座便器盖板":["普通盖板"],"浴室龙头":["台盆龙头","浴缸龙头","净身盆龙头","挂墙式花洒龙头","龙头配件"],"淋浴龙头":["淋浴柱及淋浴屏","头顶花洒","手持花洒","智能淋浴系统","入墙式阀芯及面板","淋浴配件"],"梳妆":["浴室家具","镜柜/镜子系列","台盆"],"按摩浴缸":["水·乐浴缸","组合型水力/水疗按摩","舒缓型按摩浴缸","泡泡浴缸","舒适型水力按摩浴缸"],"浴缸":["铸铁浴缸","压克力浴缸","绮美石浴缸","浴缸配件"],"淋浴房":["淋浴房","淋浴房配件"],"蒸汽设备":["蒸汽设备"],"公共商用产品":["商用龙头","商用座便器","商用小便器","商用电子","其他商用产品"],"浴室配件":["浴室配件","灯具系列"],"艺术系列":["艺术系列","自然原质"],"瓷砖石材":["瓷砖","石材"],"浴室净·暖机（浴霸）":["浴室净·暖机（浴霸）"]}
     * 厨房产品 : {"整体橱柜":["整体橱柜"],"厨盆":["厨盆","厨房配件"],"厨房龙头":["厨房龙头系列"],"净水":["中央净水产品","末端净水产品","配件"]}
     * 其他 : {}
     */

    private 卫浴产品Bean 卫浴产品;
    private 厨房产品Bean 厨房产品;
    private 其他Bean 其他;

    public 卫浴产品Bean get卫浴产品() {
        return 卫浴产品;
    }

    public void set卫浴产品(卫浴产品Bean 卫浴产品) {
        this.卫浴产品 = 卫浴产品;
    }

    public 厨房产品Bean get厨房产品() {
        return 厨房产品;
    }

    public void set厨房产品(厨房产品Bean 厨房产品) {
        this.厨房产品 = 厨房产品;
    }

    public 其他Bean get其他() {
        return 其他;
    }

    public void set其他(其他Bean 其他) {
        this.其他 = 其他;
    }

    public static class 卫浴产品Bean {
        @SerializedName("一体·超感座便器")
        private List<String> _$311; // FIXME check this code
        private List<String> 座便器;
        @SerializedName("C³清舒宝智能座便盖")
        private List<String> _$C23; // FIXME check this code
        private List<String> 座便器盖板;
        private List<String> 浴室龙头;
        private List<String> 淋浴龙头;
        private List<String> 梳妆;
        private List<String> 按摩浴缸;
        private List<String> 浴缸;
        private List<String> 淋浴房;
        private List<String> 蒸汽设备;
        private List<String> 公共商用产品;
        private List<String> 浴室配件;
        private List<String> 艺术系列;
        private List<String> 瓷砖石材;
        @SerializedName("浴室净·暖机（浴霸）")
        private List<String> _$189; // FIXME check this code

        public List<String> get_$311() {
            return _$311;
        }

        public void set_$311(List<String> _$311) {
            this._$311 = _$311;
        }

        public List<String> get座便器() {
            return 座便器;
        }

        public void set座便器(List<String> 座便器) {
            this.座便器 = 座便器;
        }

        public List<String> get_$C23() {
            return _$C23;
        }

        public void set_$C23(List<String> _$C23) {
            this._$C23 = _$C23;
        }

        public List<String> get座便器盖板() {
            return 座便器盖板;
        }

        public void set座便器盖板(List<String> 座便器盖板) {
            this.座便器盖板 = 座便器盖板;
        }

        public List<String> get浴室龙头() {
            return 浴室龙头;
        }

        public void set浴室龙头(List<String> 浴室龙头) {
            this.浴室龙头 = 浴室龙头;
        }

        public List<String> get淋浴龙头() {
            return 淋浴龙头;
        }

        public void set淋浴龙头(List<String> 淋浴龙头) {
            this.淋浴龙头 = 淋浴龙头;
        }

        public List<String> get梳妆() {
            return 梳妆;
        }

        public void set梳妆(List<String> 梳妆) {
            this.梳妆 = 梳妆;
        }

        public List<String> get按摩浴缸() {
            return 按摩浴缸;
        }

        public void set按摩浴缸(List<String> 按摩浴缸) {
            this.按摩浴缸 = 按摩浴缸;
        }

        public List<String> get浴缸() {
            return 浴缸;
        }

        public void set浴缸(List<String> 浴缸) {
            this.浴缸 = 浴缸;
        }

        public List<String> get淋浴房() {
            return 淋浴房;
        }

        public void set淋浴房(List<String> 淋浴房) {
            this.淋浴房 = 淋浴房;
        }

        public List<String> get蒸汽设备() {
            return 蒸汽设备;
        }

        public void set蒸汽设备(List<String> 蒸汽设备) {
            this.蒸汽设备 = 蒸汽设备;
        }

        public List<String> get公共商用产品() {
            return 公共商用产品;
        }

        public void set公共商用产品(List<String> 公共商用产品) {
            this.公共商用产品 = 公共商用产品;
        }

        public List<String> get浴室配件() {
            return 浴室配件;
        }

        public void set浴室配件(List<String> 浴室配件) {
            this.浴室配件 = 浴室配件;
        }

        public List<String> get艺术系列() {
            return 艺术系列;
        }

        public void set艺术系列(List<String> 艺术系列) {
            this.艺术系列 = 艺术系列;
        }

        public List<String> get瓷砖石材() {
            return 瓷砖石材;
        }

        public void set瓷砖石材(List<String> 瓷砖石材) {
            this.瓷砖石材 = 瓷砖石材;
        }

        public List<String> get_$189() {
            return _$189;
        }

        public void set_$189(List<String> _$189) {
            this._$189 = _$189;
        }
    }

    public static class 厨房产品Bean {
    }

    public static class 其他Bean {
    }
}
