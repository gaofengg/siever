package com.huanhe_tech.handler;

import com.ib.client.Contract;
import com.ib.client.ContractDescription;
import com.ib.controller.ApiController;

public class MSymbolSearchHandler implements ApiController.ISymbolSamplesHandler {


    @Override
    public void symbolSamples(ContractDescription[] contractDescriptions) {
        for (ContractDescription cd : contractDescriptions) {
            Contract c = cd.contract();

            StringBuilder derivativeSecTypesSB = new StringBuilder();
            for (String str: cd.derivativeSecTypes()) {
                derivativeSecTypesSB.append(str);
                derivativeSecTypesSB.append(",");
            }

            if (c.hashCode() > 0) {
                System.out.println(c.symbol());
            }


        }
    }
}
