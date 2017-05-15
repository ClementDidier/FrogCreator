package plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

// TODO: Javadoc et ajout d'exceptions
public class PluginLoader<T>
{
	private final static String CLASS_EXTENSION = ".class";
	
	private Class<? extends Annotation> annotationClass;
	private String pluginFolder;
	
	
	public PluginLoader(String pluginFolder, Class<? extends Annotation> annotationClass)
	{
		this.annotationClass = annotationClass;
		this.pluginFolder = pluginFolder;
	}
	
	private File[] getJarFiles()
	{
		File pluginsDir = new File(this.pluginFolder);
		File[] jarFiles = pluginsDir.listFiles(new FilenameFilter() 
		{
			@Override
			public boolean accept(File dir, String name)
			{
				return name.toLowerCase().endsWith(".jar");
			}
		});
		
		if(jarFiles == null)
			return new File[0];
		return jarFiles;
	}
	
	private List<String> getClassesNameFromJar(String jarfile) throws IOException
	{
		List<String> classes = new ArrayList<String>();
		
		try (JarInputStream jarFile = new JarInputStream(new FileInputStream(jarfile)))
		{
			JarEntry jarEntry;

			while((jarEntry = jarFile.getNextJarEntry()) != null) 
			{
				if(jarEntry.getName().endsWith(CLASS_EXTENSION)) 
				{
					classes.add(jarEntry.getName().replaceAll("/", "\\.").replace(CLASS_EXTENSION, ""));
				}
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		
		return classes;
	}
	
	@SuppressWarnings("unchecked")
	private List<String> getAnnotedClassFromJar(String jarfile, Class<?> annotationClass) throws ClassNotFoundException, IOException
	{
		List<String> classes = new ArrayList<String>();
		List<String> classesInJar = getClassesNameFromJar(jarfile);
		File jar = new File(jarfile);
		
		URLClassLoader loader = new URLClassLoader(new URL[] {jar.toURI().toURL()});
		
		for(String classe : classesInJar)
		{
			Class<?> clazz = loader.loadClass(classe);
			
			if(clazz.isAnnotationPresent((Class<? extends Annotation>) annotationClass))
				classes.add(classe);
		}
		
		loader.close();
		
		return classes;
	}
	
	/**
	 * Obtient la liste des plugins valides centenus dans le dossier de plugins
	 * @return La liste des plugins valides
	 */
	public List<T> getPlugins()
	{
		List<T> plugins = new ArrayList<T>();
		
	    for (File jarFile : this.getJarFiles()) 
	    {
			try (URLClassLoader loader = new URLClassLoader(new URL[] {jarFile.toURI().toURL()}))
			{	
				List<String> classNamesWithAnnot = getAnnotedClassFromJar(jarFile.getPath(), annotationClass);
				//System.out.println(Arrays.toString(classNamesWithAnnot.toArray()));
				
				for(String classe : classNamesWithAnnot)
				{
					Class<?> clazz = loader.loadClass(classe);
					
					try
					{
						@SuppressWarnings("unchecked")
						T plugin = (T)clazz.newInstance();
						plugins.add(plugin);
					}
					catch(ClassCastException e)
					{
						// Cas de cast d'une classe avec l'annotation @Plugin mais qui n'implémente pas Plugin
						System.err.println("Une classe annoté ne correspond pas au type de plugin attendu. (" + classe + ")");
					}
				}
			}
			catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IOException e)
			{
				e.printStackTrace();
			}
	    }
	    
	    return plugins;
	}
}
