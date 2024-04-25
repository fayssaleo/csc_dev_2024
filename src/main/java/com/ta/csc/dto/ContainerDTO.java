package com.ta.csc.dto;

import com.ta.csc.domain.Container;

import java.util.Date;

public class ContainerDTO {

    public String shippingLine;
    public String containerNumber;
    public String type;
    public String length;
    public String invoiceCategory;
    public String fullOrEmpty;
    public boolean reef;
    public boolean imdg;
    public boolean oog;
    public boolean dmg;
    public Date incDate;
    public Date outDate;
    public int invoiceStorageDuration;

    public Container toContainer() {
        return new Container(null,shippingLine,containerNumber,type,length,invoiceCategory,fullOrEmpty,reef,imdg,oog,dmg,incDate,outDate,invoiceStorageDuration);
    }
}
