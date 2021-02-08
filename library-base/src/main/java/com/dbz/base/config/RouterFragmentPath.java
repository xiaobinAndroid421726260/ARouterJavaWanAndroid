package com.dbz.base.config;

public class RouterFragmentPath {

    /**
     * 首页组件
     */
    public static class Home {
        private static final String HOME = "/home";
        /**
         * 首页
         */
        public static final String PAGER_HOME = HOME + "/Home";
    }

    /**
     * 广场
     */
    public static class Square {
        private static final String SQUARE = "/square";
        /**
         * 广场页
         */
        public static final String PAGER_SQUARE = SQUARE + "/square";
    }

    /**
     * 公众号
     */
    public static class Wechat {
        private static final String WECHER = "/wechat";
        /**
         * 公众号页面
         */
        public static final String PAGER_WECHER = WECHER + "/wechat";
        /**
         * 列表页面
         */
        public static final String PAGER_INSIDE = WECHER + "/inside";
    }

    /**
     * 体系
     */
    public static class System {
        private static final String SYSTEM = "/system";
        /**
         * 体系
         */
        public static final String PAGER_SYSTEM = SYSTEM + "/system";

        /**
         * 体系 知识点
         */
        public static final String PAGER_KNOW = SYSTEM + "/know";
    }

    /**
     * 项目
     */
    public static class Project {
        private static final String PROJECT = "/project";
        /**
         * 项目
         */
        public static final String PAGER_PROJECT = PROJECT + "/project";
        /**
         * 列表页面
         */
        public static final String PAGER_INSIDE = PROJECT + "/inside";
    }
}