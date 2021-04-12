package com.huanhe_tech.siever.utils;

public class LoadReSrc {
    private final String uriHeader;
    private final String uriAnchor;

    /**
     *
     * @param uriHeader resources 或者 assets
     * @param uriAnchor 锚定的子文件夹/文件名
     */
    public LoadReSrc(String uriHeader, String uriAnchor) {
        String projectDir = System.getProperty("user.dir");
        this.uriHeader = projectDir + "/" + uriHeader + "/";
        this.uriAnchor = uriAnchor;
    }

    public String getUri() {
        return uriHeader + uriAnchor;
    }
}
