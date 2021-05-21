package com.huanhe_tech.cli.DAO;

import com.ib.client.ContractDetails;
import com.ib.controller.ApiController;

import java.util.List;

public class ContractDetailsHanderTest implements ApiController.IContractDetailsHandler {
    @Override
    public void contractDetails(List<ContractDetails> list) {
        list.forEach(System.out::println);
    }
}
