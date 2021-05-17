package com.huanhe_tech.cli;

import com.ib.client.Contract;

/**
 *  该类的作用：
 *  仅指定 secType 为 STK，用于从官方下载的 symbol 列表文件中提取 NASDAQ 和 NYSE 标的时的参数
 */
public class ContractWithSTK {
    public static Contract NNContractForReqDetails(String symbol) {
        Contract contract = new Contract();
        contract.symbol(symbol);
        contract.secType("STK");
        return contract;
    }

}
