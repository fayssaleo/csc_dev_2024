package com.ta.csc.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Container {
    @Override
    public String toString() {
        return "Container{" +
                "id=" + id +
                ", shippingLine='" + shippingLine + '\'' +
                ", containerNumber='" + containerNumber + '\'' +
                ", type='" + type + '\'' +
                ", length='" + length + '\'' +
                ", invoiceCategory='" + invoiceCategory + '\'' +
                ", fullOrEmpty='" + fullOrEmpty + '\'' +
                ", reef=" + reef +
                ", imdg=" + imdg +
                ", oog=" + oog +
                ", dmg=" + dmg +
                ", incDate=" + incDate +
                ", outDate=" + outDate +
                ", invoiceStorageDuration=" + invoiceStorageDuration +
                ", stillInYard=" + stillInYard +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todoSeqGen")
    @SequenceGenerator(name = "todoSeqGen", sequenceName = "todoSeq", initialValue = 1, allocationSize = 100)
    Long id;
    String shippingLine;
    String containerNumber;
    String type;
    String length;
    String invoiceCategory;
    String fullOrEmpty;
    boolean reef;
    boolean imdg;
    boolean oog;
    boolean dmg;
    Date incDate;
    Date outDate;
    int invoiceStorageDuration;
    boolean stillInYard;

    public boolean isStillInYard() {
        return stillInYard;
    }

    public void setStillInYard(boolean stillInYard) {
        this.stillInYard = stillInYard;
    }

    public Container() {
    }

    public Container(Long id, String shippingLine, String containerNumber, String type, String length, String invoiceCategory, String fullOrEmpty, boolean reef, boolean imdg, boolean oog, boolean dmg, Date incDate, Date outDate, int invoiceStorageDuration) {
        this.id = id;
        this.shippingLine = shippingLine;
        this.containerNumber = containerNumber;
        this.type = type;
        this.length = length;
        this.invoiceCategory = invoiceCategory;
        this.fullOrEmpty = fullOrEmpty;
        this.reef = reef;
        this.imdg = imdg;
        this.oog = oog;
        this.dmg = dmg;
        this.incDate = incDate;
        this.outDate = outDate;
        this.invoiceStorageDuration = invoiceStorageDuration;
    }
    public Container(Long id, String shippingLine, String containerNumber, String type, String length, String invoiceCategory, String fullOrEmpty, boolean reef, boolean imdg, boolean oog, boolean dmg, Date incDate, Date outDate, int invoiceStorageDuration,boolean stillInYard) {
        this.id = id;
        this.shippingLine = shippingLine;
        this.containerNumber = containerNumber;
        this.type = type;
        this.length = length;
        this.invoiceCategory = invoiceCategory;
        this.fullOrEmpty = fullOrEmpty;
        this.reef = reef;
        this.imdg = imdg;
        this.oog = oog;
        this.dmg = dmg;
        this.incDate = incDate;
        this.outDate = outDate;
        this.invoiceStorageDuration = invoiceStorageDuration;
        this.stillInYard=stillInYard;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShippingLine() {
        return shippingLine;
    }

    public void setShippingLine(String shippingLine) {
        this.shippingLine = shippingLine;
    }

    public String getContainerNumber() {
        return containerNumber;
    }

    public void setContainerNumber(String containerNumber) {
        this.containerNumber = containerNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getInvoiceCategory() {
        return invoiceCategory;
    }

    public void setInvoiceCategory(String invoiceCategory) {
        this.invoiceCategory = invoiceCategory;
    }

    public String getFullOrEmpty() {
        return fullOrEmpty;
    }

    public void setFullOrEmpty(String fullOrEmpty) {
        this.fullOrEmpty = fullOrEmpty;
    }

    public boolean isReef() {
        return reef;
    }

    public void setReef(boolean reef) {
        this.reef = reef;
    }

    public boolean isImdg() {
        return imdg;
    }

    public void setImdg(boolean imdg) {
        this.imdg = imdg;
    }

    public boolean isOog() {
        return oog;
    }

    public void setOog(boolean oog) {
        this.oog = oog;
    }

    public boolean isDmg() {
        return dmg;
    }

    public void setDmg(boolean dmg) {
        this.dmg = dmg;
    }

    public Date getIncDate() {
        return incDate;
    }

    public void setIncDate(Date incDate) {
        this.incDate = incDate;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public int getInvoiceStorageDuration() {
        return invoiceStorageDuration;
    }

    public void setInvoiceStorageDuration(int invoiceStorageDuration) {
        this.invoiceStorageDuration = invoiceStorageDuration;
    }
}
