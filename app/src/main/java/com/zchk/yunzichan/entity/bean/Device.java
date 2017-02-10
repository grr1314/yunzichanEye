package com.zchk.yunzichan.entity.bean;

/**
 * Created by SenseTech on 2016/9/18.
 */
public class Device {

    private int id;
    private String nameCn;
    private int typeId;
    private String typeName;
    private String lableCode;
    private int companyId;
    private String company;

    public Device() {
        super();
    }
    public Device(int id, String nameCn, int typeId, String typeName,
                  String lableCode, int companyId, String company) {
        super();
        this.id = id;
        this.nameCn = nameCn;
        this.typeId = typeId;
        this.typeName = typeName;
        this.lableCode = lableCode;
        this.companyId = companyId;
        this.company = company;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNameCn() {
        return nameCn;
    }
    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }
    public int getTypeId() {
        return typeId;
    }
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public String getLableCode() {
        return lableCode;
    }
    public void setLableCode(String lableCode) {
        this.lableCode = lableCode;
    }
    public int getCompanyId() {
        return companyId;
    }
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }

}

