package com.huanhe_tech.cli;

import com.ib.client.Contract;
import com.ib.client.ContractDescription;
import com.ib.controller.ApiController;

public class SymbolSearchlHandler implements ApiController.ISymbolSamplesHandler {


    @Override
    public void symbolSamples(ContractDescription[] contractDescriptions) {
//        System.out.println(contractDescriptions.);
        for (ContractDescription cd : contractDescriptions) {
            Contract c = cd.contract();
            StringBuilder derivativeSecTypesSB = new StringBuilder();
            for (String str: cd.derivativeSecTypes()) {
                derivativeSecTypesSB.append(str);
                derivativeSecTypesSB.append(",");
            }

            System.out.println("Symbol: " + c.symbol());

        }
    }
}
