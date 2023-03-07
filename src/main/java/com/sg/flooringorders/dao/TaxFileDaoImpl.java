package com.sg.flooringorders.dao;

import com.sg.flooringorders.dto.Tax;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

public class TaxFileDaoImpl implements TaxFileDao {
    private String stateAbbreviation;
    private String stateName;
    private BigDecimal taxRate;

    public final String TAX_FILE;

    public static final String DELIMITER = ",";

    private Map<String, Tax> taxes = new HashMap<>();

    public TaxFileDaoImpl(){
        this.TAX_FILE = "src\\main\\Data\\Taxes\\taxes.txt";
    }

    public TaxFileDaoImpl(String taxTextFile){
        this.TAX_FILE = taxTextFile;
    }

    public String getStateAbbreviation() {
        return this.stateAbbreviation;
    }

    public String getStateName() {
        return stateName;
    }

    public BigDecimal getTaxRate(){
        return this.taxRate;
    }

    public Tax getTax(String state){
        loadTaxes();
        Tax tax = taxes.get(state);
        return tax;
    }

    public List<Tax> getAllTaxes(){
        loadTaxes();
        List<Tax> currTaxes = new ArrayList<Tax>(taxes.values());
        return currTaxes;
    }

    private Tax unmarshallTax(String currentLine) {
        String[] taxTokens = currentLine.split(DELIMITER);

        Tax taxFromFile = new Tax(taxTokens[0], taxTokens[1], new BigDecimal(taxTokens[2]));

        taxFromFile.setStateAbbreviation(taxTokens[0]);
        taxFromFile.setStateName(taxTokens[1]);
        taxFromFile.setTaxRate(new BigDecimal(taxTokens[2]));

        return taxFromFile;
    }

    private void loadTaxes()  {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String currLine;
        Tax currTax;
        while (scanner.hasNextLine()) {
            currLine = scanner.nextLine();
            //ignore the first line, the heading
            if (currLine.startsWith("State")) {
                continue;
            }
            currTax = unmarshallTax(currLine);
            taxes.put(currTax.getStateAbbreviation(), currTax);
        }
        scanner.close();
    }
}
