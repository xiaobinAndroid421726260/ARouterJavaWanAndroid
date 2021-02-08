package com.dbz.base.config;

public class RouterActivityPath {


    /**
     * MainActivity
     */
    public static class Main {
        private static final String MAIN = "/main";
        /**
         * MainActivity
         */
        public static final String PAGER_MAIN = MAIN + "/main";
    }

    /**
     * WebViewActivity
     */
    public static class WebView {
        private static final String WEB = "/web";
        /**
         * WebViewActivity
         */
        public static final String PAGER_WEB = WEB + "/webview";
    }

    /**
     * KnowledgeActivity
     */
    public static class Know {
        private static final String KNOW = "/know";
        /**
         * KnowledgeActivity
         */
        public static final String PAGER_KNOW = KNOW + "/know";
    }
}