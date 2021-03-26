package com.huanhe_tech.handler;

public enum MGlobalSettings {
    INSTANCE(false, 0, false);
    private boolean reqHistoricalComplete; //
    private int symbolListIndex;
    private boolean isNN; // 是否属于 NASDAQ 或者 NYSE 的补票

    MGlobalSettings(boolean reqHistoricalComplete, int symbolListIndex, boolean isNN) {
        this.reqHistoricalComplete = reqHistoricalComplete;
        this.symbolListIndex = symbolListIndex;
        this.isNN = isNN;
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

    public boolean isNN() {
        return isNN;
    }

    public void setNN(boolean NN) {
        this.isNN = NN;
    }
}
