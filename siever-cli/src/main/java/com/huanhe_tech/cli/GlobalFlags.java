package com.huanhe_tech.cli;

import java.util.concurrent.locks.ReentrantLock;

public class GlobalFlags extends ReentrantLock {
    private GlobalFlags() {
    }

    /**
     *  从 AllSymbolsQueue 队列中 take 数据
     *  到请求 symbol 类型的异步处理顺序 flag
     */
    public enum ReqTypeFlag {
        STATE(true);

        private Boolean b;
        ReqTypeFlag(boolean b) {
            this.b = b;
        }

        public void setState(Boolean b) {
            this.b = b;
        }

        public Boolean getState() {
            return b;
        }
    }

    /**
     *  从 FiltrateBySymbolTypeQueue 队列中 take 数据
     *  到请求历史数据的异步处理顺序 flag
     */
    public enum ReqHistoricalFlag {
        STATE(true);

        private Boolean b;
        ReqHistoricalFlag(boolean b) {
            this.b = b;
        }

        public void setState(Boolean b) {
            this.b = b;
        }

        public Boolean getState() {
            return b;
        }
    }

    /**
     * 向 conid 表更新历史数据完成与否的标志位
     */
    public enum UpdateHistDone {
        STATE(false);
        private Boolean b;

        UpdateHistDone(Boolean b) {
            this.b = b;
        }

        public Boolean getB() {
            return b;
        }

        public void setB(Boolean b) {
            this.b = b;
        }
    }

    public enum UpdateHistCompleteness {
        STATE(false);
        private Boolean b;

        UpdateHistCompleteness(Boolean b) {
            this.b = b;
        }

        public Boolean getB() {
            return b;
        }

        public void setB(Boolean b) {
            this.b = b;
        }
    }

}
