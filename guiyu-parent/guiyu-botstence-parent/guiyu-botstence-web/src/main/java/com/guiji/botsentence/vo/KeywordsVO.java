package com.guiji.botsentence.vo;

import lombok.Data;

import java.util.List;

@Data
public class KeywordsVO {
    private int intentId;
    private String intentName;
    private String keyWords;





//    private Currency currency;
//    private Trade trade;
//
//    @Data
//    public class Currency{//通用层
//        private List<Intent> intents;
//    }
//
//    @Data
//    public class Trade{//行业层
//        private List<Industry> intents;
//    }
//
//    @Data
//    public class Industry {//行业第一层
//        private String industryId;
//        private String industryName;
//        private List<PIndustry> PIndustry;
//    }
//
//    @Data
//    public class PIndustry {//行业第二层
//        private String industryId;
//        private String industryName;
//        private List<ChildIndustry> ChildIndustry;
//    }
//
//    @Data
//    public class ChildIndustry {//行业第三层
//        private String industryId;
//        private String industryName;
//        private List<Intent> intents;
//    }
//    @Data
//    public class Intent {//意图层
//        private int intentId;
//        private String intentName;
//        private String keyWords;
//
//    }
}
