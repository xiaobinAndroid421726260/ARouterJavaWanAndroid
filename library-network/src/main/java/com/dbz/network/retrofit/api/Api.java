package com.dbz.network.retrofit.api;

import com.dbz.network.retrofit.bean.BannerBean;
import com.dbz.network.retrofit.bean.home.ArticleBean;
import com.dbz.network.retrofit.bean.home.TopBean;
import com.dbz.network.retrofit.bean.project.ProjectBean;
import com.dbz.network.retrofit.bean.project.ProjectTreeBean;
import com.dbz.network.retrofit.bean.square.SquareBean;
import com.dbz.network.retrofit.bean.system.NaviBean;
import com.dbz.network.retrofit.bean.system.SystemBean;
import com.dbz.network.retrofit.bean.system.SystemJsonBean;
import com.dbz.network.retrofit.bean.wechat.WechatBean;
import com.dbz.network.retrofit.bean.wechat.WechatFragmentBean;

import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Api {

    /**
     * 首页banner
     */
    @GET(HttpsApi.banner)
    Observable<BannerBean> getBannerJson();

    /**
     * 首页置顶文章
     */
    @GET(HttpsApi.article_top)
    Observable<TopBean> getTopJson();

    /**
     * 首页文章列表
     */
    @GET(HttpsApi.article_list_json + "{page}/json")
    Observable<ArticleBean> getArticleJson(@Path("page") int page);

    /**
     * 广场
     */
    @GET(HttpsApi.user_article_list_json + "{page}/json")
    Observable<SquareBean> getUserArticleJson(@Path("page") int page);

    /**
     * 获取公众号列表
     */
    @GET(HttpsApi.get_wechat_article_json)
    Observable<WechatBean> getWechatArticle();

    /**
     * 查看某个公众号历史数据
     */
    @GET(HttpsApi.get_wechat_list_json + "{user}/{page}/json")
    Observable<WechatFragmentBean> getUserArticleJson(@Path("user") int user, @Path("page") int page);

    /**
     * 在某个公众号中搜索历史文章
     */
    @GET(HttpsApi.get_wechat_list_json + "{user}/{page}/json/?k={k}")
    Observable<SquareBean> getUserArticleHistoryJson(@Path("user") int user, @Path("page") int page, @Query("k") String k);

    /**
     * 体系
     */
    @GET(HttpsApi.get_tree_json)
    Observable<SystemBean> getTreeJson();

    /**
     * 知识体系下的文章
     */
    @GET(HttpsApi.get_tree_json_cid + "/{page}/json")
    Observable<SystemJsonBean> getTreeJsonCid(@Path("page") int page, @Query("cid") int cid);

    /**
     * 导航数据
     */
    @GET(HttpsApi.get_navi_json)
    Observable<NaviBean> getNaviJson();

    /**
     * 项目分类
     */
    @GET(HttpsApi.get_project_tree_json)
    Observable<ProjectTreeBean> getProjectTreeJson();

    /**
     * 项目列表数据
     */
    @GET(HttpsApi.get_project_cid_json + "/{page}/json")
    Observable<ProjectBean> getProjectCidJson(@Path("page") int page, @Query("cid") int cid);
}