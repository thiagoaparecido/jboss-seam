package org.jboss.seam.excel;

/**
 * Encapsulate errors occuring in excel workbook generation
 */
public class ExcelWorkbookException extends RuntimeException
{

   private static final long serialVersionUID = -2591516870660824325L;

   public ExcelWorkbookException()
   {

      super();
   }

   public ExcelWorkbookException(String message)
   {
      super(message);

   }

   public ExcelWorkbookException(String message, Throwable t)
   {
      super(message, t);

   }

   public ExcelWorkbookException(Throwable t)
   {
      super(t);

   }

}
