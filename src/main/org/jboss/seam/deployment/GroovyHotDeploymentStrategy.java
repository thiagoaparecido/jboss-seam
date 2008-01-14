package org.jboss.seam.deployment;

import groovy.lang.GroovyClassLoader;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.groovy.control.CompilerConfiguration;


/**
 * @author Pete Muir
 *
 */
public class GroovyHotDeploymentStrategy extends HotDeploymentStrategy
{
   
   private static final String DEFAULT_SCRIPT_EXTENSION = new CompilerConfiguration().getDefaultScriptExtension();
   
   private ClassLoader classLoader;
   
   private GroovyDeploymentHandler groovyDeploymentHandler;
   
   public GroovyHotDeploymentStrategy(ClassLoader classLoader, File hotDeployDirectory)
   {
      super(classLoader, hotDeployDirectory);
      if (super.getClassLoader() != null)
      {
         this.classLoader = new GroovyClassLoader(super.getClassLoader());
      }
      groovyDeploymentHandler = new GroovyDeploymentHandler(DEFAULT_SCRIPT_EXTENSION);
      getDeploymentHandlers().put(GroovyDeploymentHandler.NAME, groovyDeploymentHandler);
   }
   
   @Override
   public ClassLoader getClassLoader()
   {
      return classLoader;
   }

   @Override
   public boolean isFromHotDeployClassLoader(Class componentClass)
   {
      //loaded by groovy or java
      if (getClassLoader() == null)
      {
         return false;
      }
      else
      {
         ClassLoader classClassLoader = componentClass.getClassLoader().getParent(); //Groovy use an Inner Delegate CL
         return classClassLoader == getClassLoader() || classClassLoader == getClassLoader().getParent();
      }
   }
   
   @Override
   public ClassLoader getScannableClassLoader()
   {
      if (classLoader != null)
      {
         return classLoader.getParent();
      }
      else
      {
         return null;
      }
   }
   
   @Override
   public Set<Class<Object>> getScannedComponentClasses()
   {
      Set<Class<Object>> set = new HashSet<Class<Object>>();
      set.addAll(super.getScannedComponentClasses());
      set.addAll(groovyDeploymentHandler.getClasses());
      return Collections.unmodifiableSet(set);
   }
   
}
