package com.seiryuu.common.constant;

/**
 * @author Seiryuu
 * @since 2022-10-09 11:46
 * 字典数据常量类
 */
public interface DictDataConstants {

    /**
     * 公用数据
     */
    class CommonData implements DictDataConstants {
        /**
         * 字符串空
         */
        public static final String STR_NULL = "";

        /**
         * 父id
         */
        public static final String PARENT_ID = "0";

        /**
         * 默认用户id
         */
        public static final String DEFAULT_USER_ID = "0";
    }

    /**
     * 删除状态
     */
    class DelStats implements DictDataConstants {
        /**
         * 删除状态-正常
         */
        public static final String NORMAL = "0";

        /**
         * 删除状态-删除
         */
        public static final String REMOVE = "1";
    }

    /**
     * 业务是否
     */
    class YesOrNo implements DictDataConstants {
        /**
         * 业务是
         */
        public static final String YES = "1";

        /**
         * 业务否
         */
        public static final String NO = "0";
    }
}
