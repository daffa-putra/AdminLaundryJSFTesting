package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import pojo.Layanan;
import DAO.DAOLayanan;

@FacesConverter("layananConverter")
public class LayananConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            DAOLayanan layananDAO = new DAOLayanan();
            return layananDAO.getById(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        if (value instanceof Layanan) {
            return String.valueOf(((Layanan) value).getIdLayanan());
        }
        return "";
    }
}
