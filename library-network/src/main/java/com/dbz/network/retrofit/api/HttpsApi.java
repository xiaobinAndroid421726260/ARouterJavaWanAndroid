package com.dbz.network.retrofit.api;

public class HttpsApi {


    public static final String HTTPS = "https://";

    public static final String A_HOST = HTTPS + "www.wanandroid.com/";


    // 首页文章列表 banner
    public static final String banner = A_HOST + "banner/json";

    // 置顶文章
    public static final String article_top = A_HOST + "article/top/json";

    // 首页文章列表
    public static final String article_list_json = A_HOST + "article/list/";

    // 广场
    public static final String user_article_list_json = A_HOST + "user_article/list/";

    // 获取公众号列表
    public static final String get_wechat_article_json = A_HOST + "wxarticle/chapters/json";

    // 查看某个公众号历史数据 、 在某个公众号中搜索历史文章
    public static final String get_wechat_list_json = A_HOST + "wxarticle/list/";

    // 体系
    public static final String get_tree_json = A_HOST + "tree/json";

    // 知识体系下的文章
    public static final String get_tree_json_cid = A_HOST + "article/list";

    // 导航
    public static final String get_navi_json = A_HOST + "navi/json";

    // 项目分类
    public static final String get_project_tree_json = A_HOST + "project/tree/json";

    // 项目列表数据
    public static final String get_project_cid_json = A_HOST + "project/list";
}