# HELMNotationParser 

HELMParser is a java-program to parse HELM and HELM2 notation. During the parsing process the validation of HELM2 takes place. The program not only parses but also generates so called notations objects. It produces an user-defined output, either HELM2 or HELM2 in JSON-format. The created log-file can be found in ./helm2parser.log

The program is called by the following command:
> java –jar HELM2Parser.jar –inputHELM <HELM> -output <HELM2/JSON> 

The program is also able to convert HELM1 to HELM2. To use this additional function call the program with –translate. 

> java –jar HELM2Parser.jar –inputHELM <HELM> -output <HELM2/JSON> -translate
