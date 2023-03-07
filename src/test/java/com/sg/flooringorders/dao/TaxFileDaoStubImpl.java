package com.sg.flooringorders.dao;

import com.sg.flooringorders.dto.Tax;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaxFileDaoStubImpl implements TaxFileDao{

    Map<String, Tax> currTaxes = new HashMap<>();
    public TaxFileDaoStubImpl(){
        Tax tax1 = new Tax("CA", "Callifornia", new BigDecimal(2));
        Tax tax2 = new Tax("NY", "New York", new BigDecimal(5));
        Tax tax3 = new Tax("KA", "Kansas", new BigDecimal(1));
        Tax tax4 = new Tax("NJ", "New Jersey", new BigDecimal(1.50));

        this.currTaxes.put(tax1.getStateName(), tax1);
        this.currTaxes.put(tax2.getStateName(), tax2);
        this.currTaxes.put(tax3.getStateName(), tax3);
        this.currTaxes.put(tax4.getStateName(), tax4);

    }

    @Override
    public Tax getTax(String stateName) {
        return this.currTaxes.get(stateName);
    }

    @Override
    public List<Tax> getAllTaxes() {
        List<Tax> allTaxes = new ArrayList<>();
        for(Tax tax : currTaxes.values()){
            allTaxes.add(tax);
        }
        return allTaxes;
    }
}
