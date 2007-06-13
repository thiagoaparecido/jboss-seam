package org.jboss.seam.ui;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.jboss.seam.core.Expressions;

public class ModelValidator implements Validator
{

   public void validate(FacesContext context, UIComponent component, Object value)
         throws ValidatorException
   {
      ValueExpression valueExpression = component.getValueExpression("value");
      if (valueExpression!=null)
      {
         Expressions.instance().validate( valueExpression.getExpressionString(), value );
      }
   }

}
