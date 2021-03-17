package com.huanhe_tech.handler;

public enum MGlobalSettings {
    INSTANCE(false, 0, false);
    private boolean reqHistoricalComplete; //
    private int symbolListIndex;
    private boolean hasNN; // 是否属于 NASDAQ 或者 NYSE 的补票

    MGlobalSettings(boolean reqHistoricalComplete, int symbolListIndex, boolean hasNN) {
        this.reqHistoricalComplete = reqHistoricalComplete;
        this.symbolListIndex = symbolListIndex;
        this.hasNN = hasNN;
    }

    public boolean isReqHistoricalComplete() {
        return reqHistoricalComplete;
    }

    public void setReqHistoricalComplete(boolean reqHistoricalComplete) {
        this.reqHistoricalComplete = reqHistoricalComplete;
    }

    public int getSymbolListIndex() {
        return symbolListIndex;
    }

    public void setSymbolListIndex(int symbolListIndex) {
        this.symbolListIndex += symbolListIndex;
    }

    public boolean hasNN() {
        return hasNN;
    }

    public void setHasNN(boolean hasNN) {
        this.hasNN = hasNN;
    }
}
