package com.github.davidmoten.rx.parallel;

import japa.parser.ast.TypeParameter;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.visitor.VoidVisitorAdapter;

public class MethodVisitor extends VoidVisitorAdapter {

	@Override
	public void visit(MethodDeclaration m, Object arg) {
		// here you can access the attributes of the method.
		// this method will be called for all methods in this
		// CompilationUnit, including inner class methods
		System.out.println(m.getName());
		StringBuilder s = new StringBuilder();
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
		System.out.println(s);
	}

}
