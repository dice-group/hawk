/**
 * 
 */
package org.aksw.hawk;

import java.util.Map;
import java.util.Set;

import org.aksw.autosparql.commons.qald.Question;
import org.aksw.hawk.cache.AbstractIndexCache;
import org.aksw.hawk.cache.CachedParseTree;
import org.aksw.hawk.controller.Pipeline;
import org.aksw.hawk.nlp.SentenceToSequence;
import org.aksw.hawk.nlp.spotter.Fox;
import org.aksw.hawk.pruner.Pruner;
import org.aksw.hawk.querybuilding.Annotater;
import org.aksw.hawk.querybuilding.SPARQLQueryBuilder;

import com.hp.hpl.jena.rdf.model.RDFNode;

/**
 * @author Lorenz Buehmann
 *
 */
public class Hawk {
	
	public static void main(String[] args) throws Exception {
		Pipeline controller = new Pipeline();

		controller.nerdModule = new Fox();
		controller.cParseTree = new CachedParseTree();

		AbstractIndexCache cache = new AbstractIndexCache();
		controller.sentenceToSequence = new SentenceToSequence();
		controller.queryBuilder = new SPARQLQueryBuilder();
		controller.annotater = new Annotater();

		controller.pruner = new Pruner();
		
		Question q = new Question();
		q.languageToQuestion.put("en", "Which recipients of the Victoria Cross died in the Battle of Arnhem?");
		
		Map<String, Set<RDFNode>> sparql = controller.calculateSPARQLRepresentation(q);
		
		System.out.println(sparql);
	}

}