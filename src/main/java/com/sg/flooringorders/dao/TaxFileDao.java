package com.sg.flooringorders.dao;

import com.sg.flooringorders.dto.Tax;

import java.util.List;

public interface TaxFileDao {
    public Tax getTax(String stateName);

    public List<Tax> getAllTaxes();


}
