package com.novacloud.data.formula.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressionEngineFactory implements ScriptEngineFactory {
	final static List<String> NAMES = Arrays.asList("JSEL","jsel","JSON");
	final static List<String> EXTS = Arrays.asList(".JSEL",".jsel",".json");
	final static List<String> MIME_TYPES = Arrays.asList("text/jsel","application/jsel");
	static final Map<String, String> ps = new HashMap<>();
	static{
	    ps.put(ScriptEngine.ENGINE, "JSEL");
	    ps.put(ScriptEngine.ENGINE_VERSION, "2.0");
	    ps.put(ScriptEngine.NAME, "JSEL");
	    ps.put(ScriptEngine.LANGUAGE, "JSEL");
	    ps.put(ScriptEngine.LANGUAGE_VERSION, "1.0");
	}

	public String getName() {
      return (String)this.getParameter(ScriptEngine.NAME);
  }

  public String getEngineName() {
      return (String)this.getParameter(ScriptEngine.ENGINE);
  }

  public String getEngineVersion() {
      return (String)this.getParameter(ScriptEngine.ENGINE_VERSION);
  }

  public String getLanguageName() {
      return (String)this.getParameter(ScriptEngine.LANGUAGE);
  }

  public String getLanguageVersion() {
      return (String)this.getParameter(ScriptEngine.LANGUAGE_VERSION);
  }
	public List<String> getExtensions() {
		return EXTS;
	}

	public String getMethodCallSyntax(String obj, String m, String... args) {
		return obj+"."+m+'('+join(args,",")+')';
	}

	private String join(String[] args, String sep) {
		StringBuilder buf = new StringBuilder();
		for(String arg:args){
			if(buf.length()>0){
				buf.append(sep);
			}
			buf.append(arg);
		}
		return buf.toString();
	}

	public List<String> getMimeTypes() {
		return MIME_TYPES;
	}

	public List<String> getNames() {
		return NAMES;
	}

	public String getOutputStatement(String toDisplay) {
		return toDisplay;
	}

	public Object getParameter(String key) {
		return ps.get(key);
	}

	public String getProgram(String... statements) {
		return join(statements,";");
	}
	public ScriptEngine getScriptEngine() {
		return new ExpressionEngine(this);
	}
}