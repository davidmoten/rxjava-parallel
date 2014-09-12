package com.github.davidmoten.rx.parallel;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Generator {

	public void run() throws IOException, ParseException {

		// creates an input stream for the file to be parsed
		InputStream  is = Generator.class.getResourceAsStream("/Observable.java");

		CompilationUnit cu;
		try {
			// parse the file
			cu = JavaParser.parse(is);
		} finally {
			is.close();
		}

		// prints the resulting compilation unit to default system output
		System.out.println(cu.toString());
		
		 // visit and print the methods names
        new MethodVisitor().visit(cu, null);

		System.exit(0);

//		InputStream is = Generator.class.getResourceAsStream("/signatures.txt");
//		BufferedReader br = new BufferedReader(new InputStreamReader(is));
//		String line;
//		while ((line = br.readLine()) != null) {
//			// System.out.println(line);
//			processSig(line);
//		}
//		br.close();
	}

	private static String toString(Sig sig) {
		StringBuilder s = new StringBuilder();
		s.append("public final ");
		s.append(sig.typeParameters);
		if (sig.typeParameters.length() > 0)
			s.append(" ");
		s.append("Parallel");
		s.append(sig.methodReturnType);
		s.append(" ");
		s.append(sig.methodName);
		s.append("(");
		boolean first = true;
		for (Parameter p : sig.parameters) {
			if (!first)
				s.append(", ");
			s.append("final ");
			s.append(p.type);
			s.append(" ");
			s.append(p.name);
			first = false;

		}
		s.append(") {\n");
		s.append("    return create(new Func1<Observable<T>, "
				+ sig.methodReturnType + ">() {\n");
		s.append("                        @Override\n");
		s.append("                        public " + sig.methodReturnType
				+ " call(Observable<T> o) {\n");
		s.append("                            return o." + sig.methodName + "(");
		first = true;
		for (Parameter p : sig.parameters) {
			if (!first)
				s.append(", ");
			s.append(p.name);
			first = false;
		}
		s.append(");\n");
		s.append("                        }\n");
		s.append("                    });\n");
		s.append("}");

		// @Override
		// public Observable<R> call(Observable<T> o) {
		// return o.compose(transformer);
		// }
		// });
		return s.toString();
	}

	private void processSig(String line) {
		Sig sig = from(line);

		System.out.println(toString(sig));
	}

	public static void main(String[] args) throws Exception {
		new Generator().run();
	}

	private static Sig from(String line) {

		AtomicInteger i = new AtomicInteger(0);
		AtomicBoolean inParameters = new AtomicBoolean(false);
		String typeParameter = "";
		String methodReturnType = "";
		String methodName = "";
		String token = "";
		String paramType = "";
		List<Parameter> parameters = new ArrayList<Parameter>();

		while (i.get() < line.length()) {
			char ch = line.charAt(i.get());
			if (ch != ' ') {
				token = readToken(i, inParameters, line);
				if (token.startsWith("<"))
					typeParameter = token;
				else if (methodReturnType.length() == 0
						&& !token.startsWith("<"))
					methodReturnType = token;
				else if (methodReturnType.length() > 0
						&& methodName.length() == 0)
					methodName = token;
				else if (inParameters.get()) {
					if (paramType.length() == 0)
						paramType = token;
					else {
						parameters.add(new Parameter(paramType, token));
						paramType = "";
					}
				}
			} else
				i.incrementAndGet();
		}

		return new Sig(typeParameter, methodReturnType, methodName, parameters);
	}

	private static String readToken(AtomicInteger i,
			AtomicBoolean inParameters, String line) {
		int level = 0;
		char ch = line.charAt(i.get());
		StringBuilder s = new StringBuilder();
		while (i.get() < line.length()
				&& (level > 0 || (ch != ' ' && ch != ',' && ch != '(' && ch != ')'))) {
			if (ch == '<')
				level++;
			if (ch == '>')
				level--;
			s.append(ch);
			i.incrementAndGet();
			if (i.get() < line.length())
				ch = line.charAt(i.get());
		}
		if (ch == '(') {
			inParameters.set(true);
			i.incrementAndGet();
		} else if (ch == ')')
			i.incrementAndGet();
		else if (ch == ',')
			i.incrementAndGet();
		return s.toString();
	}

	private static class Sig {
		String typeParameters;
		String methodReturnType;
		String methodName;
		List<Parameter> parameters;

		Sig(String typeParameters, String methodReturnType, String methodName,
				List<Parameter> parameters) {
			this.typeParameters = typeParameters;
			this.methodReturnType = methodReturnType;
			this.methodName = methodName;
			this.parameters = parameters;
		}

	}

	private static class Parameter {
		String type;
		String name;

		public Parameter(String type, String name) {
			this.type = type;
			this.name = name;
		}

	}

}
