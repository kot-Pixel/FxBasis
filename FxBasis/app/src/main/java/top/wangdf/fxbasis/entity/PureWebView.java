package top.wangdf.fxbasis.entity;

/**
 *          *             {"title":"", 标题
 *          *             "url":"", 加载的地址
 *          *             "hasTitleBar":false, 是否显示标题栏
 *          *             "rewriteTitle":true, 是否通过加载的Web重写标题
 *          *             "stateBarTextColor":"black", 状态栏字体颜色 black|white
 *          *             "titleTextColor":"#FFFFFF", 标题字体颜色
 *          *             "titleColor":"#FFFFFF", 状态栏和标题背景色
 *          *             "postData":"", webView post方法时会用到
 *          *             "html":"", 加载htmlCode（例如：<body></body>）,
 *          *             "webBack":true, true:web回退(点击返回键webview可以回退就回退，无法回退的时候关闭该页面)|false(点击返回键关闭该页面) 直接关闭页面
 *          *             }
 */
public class PureWebView {
    private String title;
    private String url;
    private boolean hasTitleBar;
    private boolean rewriteTitle;
    private String stateBarTextColor;
    private String titleTextColor;

    public String getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    public PureWebView(String title, String url, boolean hasTitleBar, boolean rewriteTitle, String stateBarTextColor, String titleTextColor, String titleColor, String postData, String html, boolean webBack) {
        this.title = title;
        this.url = url;
        this.hasTitleBar = hasTitleBar;
        this.rewriteTitle = rewriteTitle;
        this.stateBarTextColor = stateBarTextColor;
        this.titleTextColor = titleTextColor;
        this.titleColor = titleColor;
        this.postData = postData;
        this.html = html;
        this.webBack = webBack;
    }

    private String titleColor;
    private String postData;
    private String html;
    private boolean webBack;

    public PureWebView() {
    }

    @Override
    public String toString() {
        return "PureWebView{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", hasTitleBar='" + hasTitleBar + '\'' +
                ", rewriteTitle='" + rewriteTitle + '\'' +
                ", stateBarTextColor='" + stateBarTextColor + '\'' +
                ", titleTextColor='" + titleTextColor + '\'' +
                ", postData='" + postData + '\'' +
                ", html='" + html + '\'' +
                ", webBack='" + webBack + '\'' +
                '}';
    }

    public PureWebView(String title, String url, boolean hasTitleBar, boolean rewriteTitle, String stateBarTextColor, String titleTextColor, String postData, String html, boolean webBack) {
        this.title = title;
        this.url = url;
        this.hasTitleBar = hasTitleBar;
        this.rewriteTitle = rewriteTitle;
        this.stateBarTextColor = stateBarTextColor;
        this.titleTextColor = titleTextColor;
        this.postData = postData;
        this.html = html;
        this.webBack = webBack;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isHasTitleBar() {
        return hasTitleBar;
    }

    public void setHasTitleBar(boolean hasTitleBar) {
        this.hasTitleBar = hasTitleBar;
    }

    public boolean isRewriteTitle() {
        return rewriteTitle;
    }

    public void setRewriteTitle(boolean rewriteTitle) {
        this.rewriteTitle = rewriteTitle;
    }

    public boolean isWebBack() {
        return webBack;
    }

    public void setWebBack(boolean webBack) {
        this.webBack = webBack;
    }

    public String getStateBarTextColor() {
        return stateBarTextColor;
    }

    public void setStateBarTextColor(String stateBarTextColor) {
        this.stateBarTextColor = stateBarTextColor;
    }

    public String getTitleTextColor() {
        return titleTextColor;
    }

    public void setTitleTextColor(String titleTextColor) {
        this.titleTextColor = titleTextColor;
    }

    public String getPostData() {
        return postData;
    }

    public void setPostData(String postData) {
        this.postData = postData;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

}
