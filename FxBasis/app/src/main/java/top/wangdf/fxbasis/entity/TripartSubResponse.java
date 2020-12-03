package top.wangdf.fxbasis.entity;

/**
 * {
 * 	"code": "200",
 * 	"data": {
 * 		"token1": "anN4aG9leHpheWlndWJuY3RocG9udHFoa2tjMg==",
 * 		"token2": "NTkxMjRkM2ZiYmFhMmMwNjYyZjg0MDI0NjNjYzMyMWU=",
 * 		"url": "https://bb.skr.today/zh_cn/?sign=11"
 *        }
 * }
 */
public class TripartSubResponse {
    private String token1;
    private String token2;
    private String url;

    public TripartSubResponse(String token1, String token2, String url) {
        this.token1 = token1;
        this.token2 = token2;
        this.url = url;
    }

    public TripartSubResponse() {
    }

    public String getToken1() {
        return token1;
    }

    public void setToken1(String token1) {
        this.token1 = token1;
    }

    public String getToken2() {
        return token2;
    }

    public void setToken2(String token2) {
        this.token2 = token2;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
