package com.cloud.model.kudosCibil;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContextData extends CibilOrderEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7577685592627098918L;
	@JsonProperty("Field")
    private List<Field> field;
    public void setField(List<Field> field) {
         this.field = field;
     }
     public List<Field> getField() {
         return field;
     }

}