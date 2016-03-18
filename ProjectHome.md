# Incerto - A Probabilistic Reasoner for the Semantic Web based on Markov Logic #

Incerto is a Java probabilistic reasoner for the Semantic Web.
It uses the capabilities of [Markov logic](http://en.wikipedia.org/wiki/Markov_logic_network) to learn and reason about uncertainty in [OWL2](http://www.w3.org/TR/owl2-profiles/) ontologies.

## FEATURES ##
  * Automatic learning of axioms uncertainty (weights) through the analysis of ontology individuals
  * Exact and approximate inference in weight-annotated ontologies
  * Support for [Alchemy](http://alchemy.cs.washington.edu/) and [PyMLNs](http://www9-old.in.tum.de/people/jain/mlns/) Markov logic engines
  * [OWL2](http://www.w3.org/TR/owl2-profiles/) support (except datatypes, keys, and in/equality assertions)
  * [SWRL](http://www.w3.org/Submission/SWRL/) rules support (except datatypes and built-ins)
  * First-order logic rules support
  * Experimental support to typed variables in predicate declarations through the use of rdfs:range and rdfs:domain properties
  * Programmatic, graphical, and command line interfaces
  * Ontology population support (from documents and web search engines)

## MAILING LIST ##
  * http://groups.google.com/group/Incerto-discuss

## DEVELOPED & SUPPORTED ##
  * Pedro Oliveira (Center for Informatics and Systems of the University of Coimbra)
    * http://student.dei.uc.pt/~pcoliv/