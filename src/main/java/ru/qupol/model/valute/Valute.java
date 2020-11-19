package ru.qupol.model.valute;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Valute")
public class Valute {

    String id;
    int numCode;
    String CharCode;
    String nominal;
    String name;
    String val;

    public String getId() {
        return id;
    }

    @XmlAttribute(name="ID")
    public void setId(String id) {
        this.id = id;
    }

    public int getNumCode() {
        return numCode;
    }

    @XmlElement(name = "NumCode")
    public void setNumCode(int numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return CharCode;
    }

    @XmlElement(name = "CharCode")
    public void setCharCode(String charCode) {
        CharCode = charCode;
    }

    public String getNominal() {
        return nominal;
    }

    @XmlElement(name = "Nominal")
    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    @XmlElement(name = "Name")
    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return val;
    }

    @XmlElement(name = "Value")
    public void setValue(String val) {
        this.val = val;
    }
}
