package ru.tilacyn.main;

import ru.tilacyn.parser.Parser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Bad Input\nTry again!\n<input filename> <output filename>\n");
            return;
        }
        String inputFileName = args[0];
        String outputFileName = args[1];

        FileReader reader = new FileReader(inputFileName);

        FileWriter writer = new FileWriter(outputFileName);

        Parser parser = new Parser(reader, writer);
        parser.evaluate();

        reader.close();
        writer.close();


    }
}
