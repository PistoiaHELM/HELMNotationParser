<<<<<<< HEAD
/**
 * *****************************************************************************
 * Copyright C 2015, The Pistoia Alliance
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *****************************************************************************
 */
package org.helm.notation2.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.helm.notation2.parser.exceptionparser.ExceptionState;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * code StartHELM2Parser main class to run a single HELM2Parser from the command
 * line
 * 
 * @author hecht
 */
public class StartHELM2Parser {

	/** The Logger for this class */
	private static final Logger LOG = LoggerFactory
			.getLogger(StartHELM2Parser.class);

	/**
	 * main method to run a single HELM2Parser from the command line
	 * 
	 * @param args
	 * @throws ParseException
	 * @throws ExceptionState
	 * @throws IOException
	 * @throws JDOMException
	 */
	public static void main(String[] args) throws ParseException,
			ExceptionState, IOException, JDOMException {
		/* options for the program */
		Options options = new Options();
		/* add helm string */
		options.addOption("inputHELM", true, "HELM1 or HELM2 string in a file");

		/* add output option */
		options.addOption("output", true,
				"output can be in JSON- or HELM2-format");

		/* add translate option */
		options.addOption("translate", false, "translate HELM1 to HELM2");

		CommandLineParser parameter = new DefaultParser();
		try {

			CommandLine cmd = parameter.parse(options, args);

			String filename = cmd.getOptionValue("inputHELM");

			ParserHELM2 parser = new ParserHELM2();

			FileReader in = new FileReader(filename);

			BufferedReader br = new BufferedReader(in);

			String line;
			String helm;
			try {
				while ((line = br.readLine()) != null) {
					helm = line;
					/* HELM1 notation has to be translated into HELM2 */
					if (cmd.hasOption("translate")) {

						ConverterHELM1ToHELM2 converter = new ConverterHELM1ToHELM2();
						helm = converter.doConvert(helm);
						LOG.info("HELM1 is translated to HELM2");
					}

					parser.parse(helm);

					/* There are two different output options */
					String output = "";
					if (cmd.getOptionValue("output").equals("HELM2")) {
						output = parser.getHELM2Notation().toHELM2();
					} else if (cmd.getOptionValue("output").equals("JSON")) {

						output = parser.getJSON();
					}
					System.out.println(output);
				}
			} finally {
				br.close();
			}
		}

		catch (NullPointerException e) {
			System.out
					.println("Please call the program with the following arguments: "
							+ "\n"
							+ "-inputHELM  <"
							+ options.getOption("inputHELM").getDescription()
							+ ">\n"
							+ "-output <"
							+ options.getOption("output").getDescription()
							+ ">\n-translate(optional) <"
							+ options.getOption("translate").getDescription()
							+ ">");
		} catch (ParseException exp) {
			System.out.println("Unexpected exception: " + exp.getMessage());
		}

	}

}
=======
/**
 * *****************************************************************************
 * Copyright C 2015, The Pistoia Alliance
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *****************************************************************************
 */
package org.helm.notation2.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.helm.notation2.parser.ExceptionParser.ExceptionState;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * code StartHELM2Parser main class to run a single HELM2Parser from the command line
 * 
 * @author hecht
 */
public class StartHELM2Parser {

  /** The Logger for this class */
  private static final Logger LOG = LoggerFactory.getLogger(StartHELM2Parser.class);



  /**
   * main method to run a single HELM2Parser from the command line
   * 
   * @param args
   * @throws ParseException
   * @throws ExceptionState
   * @throws IOException
   * @throws JDOMException
   */
  public static void main(String[] args) throws ParseException, ExceptionState, IOException, JDOMException {
    /* options for the program */
    Options options = new Options();
    /* add helm string */
    options.addOption("inputHELM", true, "HELM1 or HELM2 string in a file");

    /* add output option */
    options.addOption("output", true, "output can be in JSON- or HELM2-format");
    
    /* add translate option*/
    options.addOption("translate", false, "translate HELM1 to HELM2");

    try {
      CommandLineParser parameter = new DefaultParser();
      CommandLine cmd = parameter.parse(options, args);

      String filename = cmd.getOptionValue("inputHELM");

      ParserHELM2 parser = new ParserHELM2();

      FileReader in = new FileReader(filename);

      BufferedReader br = new BufferedReader(in);

      String line;
      String helm;
      while ((line = br.readLine()) != null) {
        helm = line;
        System.out.println(helm);
        /* HELM1 notation has to be translated into HELM2 */
        if (cmd.hasOption("translate")) {

          ConverterHELM1ToHELM2 converter = new ConverterHELM1ToHELM2();
          helm = converter.doConvert(helm);
          LOG.info("HELM1 is translated to HELM2");
        }


        parser.parse(helm);
    
        /* There are two different output options */
        String output = "";
        if (cmd.getOptionValue("output").equals("HELM2")) {
          output = parser.getHELM2Notation().toHELM2();
        } else if (cmd.getOptionValue("output").equals("JSON")) {

          output = parser.getJSON();
        }
        System.out.println(output);
      }
    }
 catch (ParseException exp) {
      System.out.println("Unexpected exception: " + exp.getMessage());
    }
    
  }

}
>>>>>>> 028ee9b5dc0c7557d6e1cfa25a041b95a69e9c58
