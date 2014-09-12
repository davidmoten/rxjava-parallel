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

		List<String> lines = new ArrayList<String>();
		{
			InputStream is = Generator.class
					.getResourceAsStream("/signatures.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();
		}

		// creates an input stream for the file to be parsed
		InputStream is = Generator.class
				.getResourceAsStream("/Observable.java");

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
		new MethodVisitor(lines).visit(cu, null);

		System.exit(0);

	}
	
	public static void main(String[] args) throws IOException, ParseException {
		new Generator().run();
	}

}
