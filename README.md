# HELMNotationParser 

HELMParser is a java-program to parse HELM and HELM2 notation. During the parsing process the validation of HELM2 takes place. The program not only parses but also generates so called notations objects. A file containing in each line a HELM notation is given as the input. It produces an user-defined output, either HELM2 or HELM2 in JSON-format. The created log-file can be found in ./helm2parser.log

The program is called by the following command:
> java –jar helm2parser.jar –inputHELM [file] -output [HELM2/JSON] 

The program is also able to convert HELM1 to HELM2. To use this additional function call the program with –translate. 

> java –jar helm2parser.jar –inputHELM [file] -output [HELM2/JSON] -translate

##Example
> java -jar helm2parser.jar -inputHELM ./filename.txt -output JSON

##Example for Inputfile
PEPTIDE1{A.G.D.A'55'}$$$$V2.0


    {
    "annotation" : {
    "annotation" : ""
    },
    "listOfPolymers" : [ {
    "polymerID" : {
      "id" : "PEPTIDE1"
    },
    "annotation" : null,
    "polymerElements" : {
      "listOfElements" : [ {
        "unit" : "A",
        "annotation" : null,
        "count" : "1"
      }, {
        "unit" : "G",
        "annotation" : null,
        "count" : "1"
      }, {
        "unit" : "D",
        "annotation" : null,
        "count" : "1"
      }, {
        "unit" : "A",
        "annotation" : null,
        "count" : "55"
      } ]
    }
    } ],
    "listOfConnections" : [ ],
    "listOfGroupings" : [ ]
    }
