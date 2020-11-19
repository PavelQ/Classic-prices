package ru.qupol.model.valute;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "ValCurs")
public class ValCurs {
    List<Valute> valutes;

    public List<Valute> getValutes() {
        return valutes;
    }

    @XmlElement(name = "Valute")
    public void setValutes(List<Valute> valutes) {
        this.valutes = valutes;
    }
}
