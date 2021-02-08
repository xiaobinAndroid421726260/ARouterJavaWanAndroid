package com.dbz.network.retrofit.bean.home;

import java.util.List;

public class TopBean {

    /**
     * data : [{"apkLink":"","audit":1,"author":"扔物线","canEdit":false,"chapterId":249,"chapterName":"干货资源","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":true,"host":"","id":12554,"link":"http://i0k.cn/59VQB","niceDate":"刚刚","niceShareDate":"2020-03-23 16:36","origin":"","prefix":"","projectLink":"","publishTime":1612022400000,"realSuperChapterId":248,"selfVisible":0,"shareDate":1584952597000,"shareUser":"","superChapterId":249,"superChapterName":"干货资源","tags":[],"title":"Android 面试黑洞&mdash;&mdash;当我按下 Home 键再切回来，会发生什么？","type":1,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":377,"chapterName":"优质内推","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":16100,"link":"https://mp.weixin.qq.com/s/1u7W3gIAwY6-LNBw5eYuTw","niceDate":"2021-01-20 15:19","niceShareDate":"2020-11-17 01:11","origin":"","prefix":"","projectLink":"","publishTime":1611127172000,"realSuperChapterId":376,"selfVisible":0,"shareDate":1605546687000,"shareUser":"","superChapterId":377,"superChapterName":"内推","tags":[],"title":"我来招人啦！","type":1,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<blockquote>\r\n<p>本题目摘自一篇 Blog，大家先尝试思考下，过两天注明博客链接。<\/p>\r\n<\/blockquote>\r\n<p>众所周知，对于 RxJava我们可以使用：<\/p>\r\n<ol>\r\n<li>observeOn<\/li>\r\n<li>subscribeOn<\/li>\r\n<\/ol>\r\n<p>去做线程调度，我们看个例子：<\/p>\r\n<pre><code>fun fetchItem(): Single&lt;Item&gt; {\r\n    return download(itemId.getAndIncrement())\r\n        .flatMap(::unZip)\r\n        .flatMap(::checkMd5)\r\n}\r\n\r\nprivate fun download(id: Int): Single&lt;Item&gt; {\r\n    return Single.just(id)\r\n        //Simulate a long time operation\r\n        .delay(300, TimeUnit.MILLISECONDS, Schedulers.io())\r\n        .map { Item(it) }\r\n}\r\n\r\nprivate fun unZip(item: Item): Single&lt;Item&gt; {\r\n    return Single.just(item)\r\n        //Simulate a long time operation\r\n        .delay(300, TimeUnit.MILLISECONDS, Schedulers.io())\r\n}\r\n\r\nprivate fun checkMd5(item: Item): Single&lt;Item&gt; {\r\n    return Single.just(item)\r\n        //Simulate a long time operation\r\n        .delay(300, TimeUnit.MILLISECONDS, Schedulers.io())\r\n}\r\n<\/code><\/pre><p>上面模拟的操作中，download unZip checkMd5 都各自指定了调度器，导致 fetchItem 实际上发生了三次线程切换。 <\/p>\r\n<p>对于这种一系列的耗时操作来说，完全可以运行在同一条后台线程上。<\/p>\r\n<p>还有这个更刻意的例子：<\/p>\r\n<pre><code>Observable\r\n    .create(emitter -&gt; {\r\n        System.out.println(&quot;create on &quot; + Thread.currentThread().getName());\r\n        emitter.onNext(&quot;Test&quot;);\r\n        emitter.onComplete();\r\n    })\r\n    .subscribeOn(Schedulers.io())\r\n    .observeOn(Schedulers.io())\r\n    .map(s -&gt; {\r\n        System.out.println(&quot;map on &quot; + Thread.currentThread().getName());\r\n        return s;\r\n    })\r\n    .observeOn(Schedulers.io())\r\n    .flatMapCompletable(s -&gt; {\r\n        System.out.println(&quot;flatMap on &quot; + Thread.currentThread().getName());\r\n        return Completable.complete();\r\n    })\r\n    .subscribe();\r\n<\/code><\/pre><p>其实几次操作都交给了 io 线程调度，大概率运行在不同的线程上，实际上完全可以避免没有必要的 io 调度。<\/p>\r\n<p>问题来了：<\/p>\r\n<ol>\r\n<li>我们如何无感知的避免这类多余的线程调度，例如当前方法已经运行在 io 线程，那么就没有必要再去做 io 线程调度了；<\/li>\r\n<li>无感知指的是，不需要刻意去修改业务逻辑代码，毕竟上面第一个例子那个几个方法内部指定Schedulers.io()也是合理的。<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"host":"","id":16929,"link":"https://www.wanandroid.com/wenda/show/16929","niceDate":"2021-01-17 22:29","niceShareDate":"2021-01-16 21:29","origin":"","prefix":"","projectLink":"","publishTime":1610893780000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1610803767000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 如何检测和避免 RxJava 的重复线程切换？","type":1,"userId":2,"visible":1,"zan":1},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<ul>\r\n<li>Thread.setPrioriy()<\/li>\r\n<li>Process.setThreadPriority()<\/li>\r\n<\/ul>\r\n<p>请问：<\/p>\r\n<ol>\r\n<li>两种方式有何区别？更推荐用哪种方式？<\/li>\r\n<li>Android 中的线程与操作系统中的线程是如何对应的？通过优先级的设置如何影响到 CPU 的调度？<\/li>\r\n<li>方式 1 有什么问题吗或者说使用注意事项吗?（这道题有点偏，可以选答）<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"host":"","id":16848,"link":"https://www.wanandroid.com/wenda/show/16848","niceDate":"2021-01-11 00:00","niceShareDate":"2021-01-10 23:59","origin":"","prefix":"","projectLink":"","publishTime":1610294406000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1610294379000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | Android 中两种设置线程优先级的方式，有何区别？","type":1,"userId":2,"visible":1,"zan":7},{"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>在上一问，我们了解了 Java 中 lambda 表达式的原理：<\/p>\r\n<p><a href=\"https://www.wanandroid.com/wenda/show/16717\">每日一问 | Java中匿名内部类写成 lambda，真的只是语法糖吗？<\/a><\/p>\r\n<p>新的问题来了，看下面一段简单的 lambda 代码：<\/p>\r\n<pre><code>Runnable r = ()-&gt;{\r\n      Log.d(&quot;test&quot;,&quot;hello, lambda&quot;);\r\n};\r\nr.run();\r\n<\/code><\/pre><ol>\r\n<li>上述代码在 Android 中与 Java 中，运行时原理有何不同？<\/li>\r\n<li>与 Java 的 lambda 相比，哪个更像是语法糖？<\/li>\r\n<li>transformClassesWithDesugarForDebug 这个任务是做什么的？<\/li>\r\n<\/ol>","descMd":"","envelopePic":"","fresh":false,"host":"","id":16771,"link":"https://www.wanandroid.com/wenda/show/16771","niceDate":"2021-01-07 00:40","niceShareDate":"2021-01-06 01:46","origin":"","prefix":"","projectLink":"","publishTime":1609951257000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1609868785000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | Java 中的 lambda 与 Android 中的 lambda 有什么不同？","type":1,"userId":2,"visible":1,"zan":8},{"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":360,"chapterName":"小编发布","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":16446,"link":"https://www.wanandroid.com/blog/show/2030","niceDate":"2020-12-13 20:21","niceShareDate":"2020-12-13 15:43","origin":"","prefix":"","projectLink":"","publishTime":1607862102000,"realSuperChapterId":297,"selfVisible":0,"shareDate":1607845415000,"shareUser":"","superChapterId":298,"superChapterName":"原创文章","tags":[{"name":"本站发布","url":"/article/list/0?cid=360"}],"title":"年底收租 大家自由支持 感谢 &amp; 通知","type":1,"userId":-1,"visible":1,"zan":0}]
     * errorCode : 0
     * errorMsg :
     */

    private int errorCode;
    private String errorMsg;
    private List<DataBean> data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * apkLink :
         * audit : 1
         * author : 扔物线
         * canEdit : false
         * chapterId : 249
         * chapterName : 干货资源
         * collect : false
         * courseId : 13
         * desc :
         * descMd :
         * envelopePic :
         * fresh : true
         * host :
         * id : 12554
         * link : http://i0k.cn/59VQB
         * niceDate : 刚刚
         * niceShareDate : 2020-03-23 16:36
         * origin :
         * prefix :
         * projectLink :
         * publishTime : 1612022400000
         * realSuperChapterId : 248
         * selfVisible : 0
         * shareDate : 1584952597000
         * shareUser :
         * superChapterId : 249
         * superChapterName : 干货资源
         * tags : []
         * title : Android 面试黑洞&mdash;&mdash;当我按下 Home 键再切回来，会发生什么？
         * type : 1
         * userId : -1
         * visible : 1
         * zan : 0
         */

        private String apkLink;
        private int audit;
        private String author;
        private boolean canEdit;
        private int chapterId;
        private String chapterName;
        private boolean collect;
        private int courseId;
        private String desc;
        private String descMd;
        private String envelopePic;
        private boolean fresh;
        private String host;
        private int id;
        private String link;
        private String niceDate;
        private String niceShareDate;
        private String origin;
        private String prefix;
        private String projectLink;
        private long publishTime;
        private int realSuperChapterId;
        private int selfVisible;
        private long shareDate;
        private String shareUser;
        private int superChapterId;
        private String superChapterName;
        private String title;
        private int type;
        private int userId;
        private int visible;
        private int zan;
        private int top;
        private List<?> tags;

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public String getApkLink() {
            return apkLink;
        }

        public void setApkLink(String apkLink) {
            this.apkLink = apkLink;
        }

        public int getAudit() {
            return audit;
        }

        public void setAudit(int audit) {
            this.audit = audit;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public boolean isCanEdit() {
            return canEdit;
        }

        public void setCanEdit(boolean canEdit) {
            this.canEdit = canEdit;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public boolean isCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDescMd() {
            return descMd;
        }

        public void setDescMd(String descMd) {
            this.descMd = descMd;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public boolean isFresh() {
            return fresh;
        }

        public void setFresh(boolean fresh) {
            this.fresh = fresh;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getNiceShareDate() {
            return niceShareDate;
        }

        public void setNiceShareDate(String niceShareDate) {
            this.niceShareDate = niceShareDate;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getProjectLink() {
            return projectLink;
        }

        public void setProjectLink(String projectLink) {
            this.projectLink = projectLink;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public int getRealSuperChapterId() {
            return realSuperChapterId;
        }

        public void setRealSuperChapterId(int realSuperChapterId) {
            this.realSuperChapterId = realSuperChapterId;
        }

        public int getSelfVisible() {
            return selfVisible;
        }

        public void setSelfVisible(int selfVisible) {
            this.selfVisible = selfVisible;
        }

        public long getShareDate() {
            return shareDate;
        }

        public void setShareDate(long shareDate) {
            this.shareDate = shareDate;
        }

        public String getShareUser() {
            return shareUser;
        }

        public void setShareUser(String shareUser) {
            this.shareUser = shareUser;
        }

        public int getSuperChapterId() {
            return superChapterId;
        }

        public void setSuperChapterId(int superChapterId) {
            this.superChapterId = superChapterId;
        }

        public String getSuperChapterName() {
            return superChapterName;
        }

        public void setSuperChapterName(String superChapterName) {
            this.superChapterName = superChapterName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }

        public List<?> getTags() {
            return tags;
        }

        public void setTags(List<?> tags) {
            this.tags = tags;
        }
    }
}