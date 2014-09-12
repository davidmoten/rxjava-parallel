package com.github.davidmoten.rx.parallel;

import japa.parser.ast.Node;
import japa.parser.ast.TypeParameter;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

public class MethodVisitor extends VoidVisitorAdapter<Object> {

	private List<String> lines;

	public MethodVisitor(List<String> lines) {
		this.lines = lines;
	}

	@Override
	public void visit(MethodDeclaration m, Object arg) {
		// here you can access the attributes of the method.
		// this method will be called for all methods in this
		// CompilationUnit, including inner class methods
		StringBuilder s = new StringBuilder();
		
		//type parameter section
		if (m.getAnnotations()!=null && !m.getAnnotations().isEmpty()) {
			AnnotationExpr a = m.getAnnotations().get(0);
		}
		if (m.getTypeParameters() != null && !m.getTypeParameters().isEmpty()) {
			s.append("<");
			boolean first = true;
			for (TypeParameter t : m.getTypeParameters()) {
				if (!first)
					s.append(", ");
				appendNode(s,t);
//				s.append(t.getName());
//				if (t.getTypeBound() != null) {
//					boolean firstBound = true;
//					for (ClassOrInterfaceType bound : t.getTypeBound()) {
//						if (firstBound)
//							s.append(" extends ");
//						else
//							s.append(" & ");
//						s.append(bound.getName());
//						firstBound = false;
//					}
//				}
				first = false;
			}
			s.append(">");
		}
		
		//method return
		
		System.out.println(s);
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
