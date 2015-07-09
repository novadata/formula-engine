package com.novacloud.data.formula;

/**
 * Created by zhaoxy on 5/11/15.
 */
public class Function {
    Category category;
    String name;
    String signature;
    String remark;
    String example;
    public Function(Category category,String name,String signature){
        this.category = category;
        this.name = name;
        this.signature = signature;
    }



    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
