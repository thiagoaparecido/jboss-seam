<#include "../util/TypeInfo.ftl">
<#assign entityName = pojo.shortName>
<#assign componentName = util.lower(entityName)>
<#assign listName = componentName + "List">
package ${actionPackage};

<#if pojo.packageName != "">
import ${pojo.packageName}.*;
</#if>
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.List;
import java.util.Arrays;

@Name("${listName}")
public class ${entityName}List extends EntityQuery<${entityName}>
{

    private static final String[] RESTRICTIONS = {
<#foreach property in pojo.allPropertiesIterator>
<#if !c2h.isCollection(property) && !util.isToOne(property)>
<#if c2j.isComponent(property)>
<#foreach componentProperty in property.value.propertyIterator>
<#if isString(componentProperty)>
        "lower(${componentName}.${property.name}.${componentProperty.name}) like concat(lower(${'#'}{${listName}.${componentName}.${property.name}.${componentProperty.name}}),'%')",
</#if>
</#foreach>
<#else>
<#if isString(property)>
        "lower(${componentName}.${property.name}) like concat(lower(${'#'}{${listName}.${componentName}.${property.name}}),'%')",
</#if>
</#if>
</#if>
</#foreach>
    };

<#if pojo.isComponent(pojo.identifierProperty)>
    private ${entityName} ${componentName};

    public ${entityName}List()
    {
        ${componentName} = new ${entityName}();
        ${componentName}.setId( new ${entityName}Id() );
    }
<#else>
    private ${entityName} ${componentName} = new ${entityName}();
</#if>

    @Override
    public String getEjbql() 
    { 
        return "select ${componentName} from ${entityName} ${componentName}";
    }
    
    @Override
    public Integer getMaxResults()
    {
    	return 25;
    }
    
    public ${entityName} get${entityName}()
    {
        return ${componentName};
    }
    
    @Override
    public List<String> getRestrictions()
    {
        return Arrays.asList(RESTRICTIONS);
    }

}
