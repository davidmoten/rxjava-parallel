package com.github.davidmoten.rx.parallel;

import japa.parser.ast.Node;
import japa.parser.ast.TypeParameter;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class MethodVisitor extends VoidVisitorAdapter<Object> {

	private List<String> lines;

	public MethodVisitor(List<String> lines) {
		this.lines = lines;
	}

	@Override
	public void visit(MethodDeclaration m, Object arg) {
		if (!m.getType().toString().startsWith("Observable")
				|| Modifier.isStatic(m.getModifiers())
				|| !Modifier.isPublic(m.getModifiers()))
			return;

		if (m.getAnnotations() != null)
			for (AnnotationExpr a : m.getAnnotations()) {
				if ("Deprecated".equals(a.getName().getName()))
					return;
			}
		// here you can access the attributes of the method.
		// this method will be called for all methods in this
		// CompilationUnit, including inner class methods
		StringBuilder s = new StringBuilder();

		// type parameter section
		if (m.getTypeParameters() != null && !m.getTypeParameters().isEmpty()) {
			s.append("<");
			boolean first = true;
			for (TypeParameter t : m.getTypeParameters()) {
				if (!first)
					s.append(", ");
				s.append(t.getName());
				if (t.getTypeBound() != null) {
					boolean firstBound = true;
					for (ClassOrInterfaceType bound : t.getTypeBound()) {
						if (firstBound)
							s.append(" extends ");
						else
							s.append(" & ");
						s.append(bound.getName());
						firstBound = false;
					}
				}
				first = false;
			}
			s.append(">");
		}
		String typeParameters = s.toString();

		// method return
		String methodReturnType = m.getType().toString();

		// parameters
		List<MyParameter> parameters = new ArrayList<MyParameter>();
		if (m.getParameters() != null && !m.getParameters().isEmpty()) {
			for (Parameter p : m.getParameters()) {
				parameters.add(new MyParameter(p.getType().toString(), p
						.getId().getName()));
			}
		}

		StringBuilder r = new StringBuilder();

		r.append("public final " + typeParameters + " Parallel"
				+ methodReturnType + " " + m.getName() + "(");
		boolean first = true;
		for (MyParameter p : parameters) {
			if (!first)
				r.append(", ");
			r.append("final " + p.type + " " + p.name);
			first = false;
		}
		r.append(") {\n");
		r.append("    return create(new Func1<Observable<T>,"
				+ methodReturnType + ">() {\n");
		r.append("        @Override\n");
		r.append("        public " + methodReturnType
				+ "call(Observable<T> o) {\n");
		r.append("            return o." + m.getName() + "(");
		first = true;
		for (MyParameter p : parameters) {
			if (!first)
				r.append(", ");
			r.append(p.name);
			first = false;
		}
		r.append(");\n");
		r.append("        }\n");
		r.append("    });\n");
		r.append("}\n");
		System.out.println(r);
	}

	private static class MyParameter {
		String type;
		String name;

		public MyParameter(String type, String name) {
			this.type = type;
			this.name = name;
		}

	}

	private void appendNode(StringBuilder s, Node node) {
		for (int lineNum = node.getBeginLine(); lineNum <= node.getEndLine(); lineNum++) {
			String line = lines.get(lineNum);
			int start;
			if (lineNum == node.getBeginLine())
				start = node.getBeginColumn();
			else
				start = 0;
			int end;
			if (lineNum == node.getEndLine())
				end = node.getEndColumn();
			else
				end = line.length();
			s.append(line.substring(start, end));
		}
	}

}
