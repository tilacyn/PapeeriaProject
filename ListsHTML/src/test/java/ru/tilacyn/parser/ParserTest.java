package ru.tilacyn.parser;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class ParserTest {

    private Character sep = File.separatorChar;
    private String pathToTest = "src" + sep + "test" + sep + "resources" + sep;

    @Test
    public void evaluate1() throws IOException {
        FileReader reader = new FileReader(pathToTest + "plain_1");
        FileWriter writer = new FileWriter(pathToTest + "html_1");
        Parser parser = new Parser(reader, writer);

        parser.evaluate();

        reader.close();
        writer.close();

        FileReader resReader = new FileReader(pathToTest + "html_1");

        int n;
        String res = "";

        while ((n = resReader.read()) != -1) {
            res += (char) n;
            System.out.println((char) n);
        }

        resReader.close();

        String actualResult = "<ul>\n" +
                "<li>First\n" +
                "<ol>\n" +
                "<li>Second\n" +
                "<ul>\n" +
                "<li>Third\n" +
                "<li>Forth\n" +
                "</ul>\n" +
                "<li>Fifth\n" +
                "</ol>\n" +
                "</ul>\n";

        assertEquals(res.length(), actualResult.length());

        assertEquals(res, actualResult);


    }


    @Test
    public void evaluate2() throws IOException {
        FileReader reader = new FileReader(pathToTest + "plain_2");
        FileWriter writer = new FileWriter(pathToTest + "html_2");
        Parser parser = new Parser(reader, writer);

        parser.evaluate();

        reader.close();
        writer.close();

        FileReader resReader = new FileReader(pathToTest + "html_2");

        int n;
        StringBuilder res = new StringBuilder();

        while ((n = resReader.read()) != -1) {
            res.append((char) n);
        }

        resReader.close();

        String kek = "";
        kek += '2';

        assertEquals(kek, "2");

        String actualResult;
        actualResult = "<ul>\n" +
                "<li>1\n" +
                "<ul>\n" +
                "<li>2\n" +
                "<ol>\n" +
                "<li>3\n" +
                "</ol>\n" +
                "<ul>\n" +
                "<li>4\n" +
                "</ul>\n" +
                "</ul>\n" +
                "<ol>\n" +
                "<li>5\n" +
                "</ol>\n" +
                "</ul>\n" +
                "<ul>\n" +
                "<li>6\n" +
                "<ul>\n" +
                "<li>7\n" +
                "<ol>\n" +
                "<li>8\n" +
                "<ol>\n" +
                "<li>9\n" +
                "<ol>\n" +
                "<li>10\n" +
                "</ol>\n" +
                "</ol>\n" +
                "<li>11\n" +
                "</ol>\n" +
                "</ul>\n" +
                "<li>12\n" +
                "</ul>\n";

        assertEquals(res.toString(), actualResult);


    }

    @Test
    public void evaluate3() throws IOException {
        FileReader reader = new FileReader(pathToTest + "plain_3");
        FileWriter writer = new FileWriter(pathToTest + "html_3");
        Parser parser = new Parser(reader, writer);

        parser.evaluate();

        reader.close();
        writer.close();

        FileReader resReader = new FileReader(pathToTest + "html_3");

        int n;
        String res = "";

        while ((n = resReader.read()) != -1) {
            res += (char) n;
            System.out.println((char) n);
        }

        resReader.close();

        String actualResult = "<ul>\n<li>1\n</ul>\n";

        assertEquals(res.length(), actualResult.length());

        for (int i = 0; i < res.length(); i++) {
            System.out.println(i + " " + res.charAt(i) + " " + actualResult.charAt(i));
            assertEquals(res.charAt(i), actualResult.charAt(i));
        }

        /*

        assertEquals(res, );
        */

    }
}