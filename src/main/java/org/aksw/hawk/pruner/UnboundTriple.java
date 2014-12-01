package org.aksw.hawk.pruner;

import java.util.Set;

import org.aksw.hawk.querybuilding.SPARQLQuery;

import com.google.common.collect.Sets;

public class UnboundTriple {
	public static Set<SPARQLQuery> prune(Set<SPARQLQuery> queryStrings, int maximalUnboundTriplePatterns) {
		Set<SPARQLQuery> returnSet = Sets.newHashSet();
		// discard queries with more than x unbound triples away
		for (SPARQLQuery sparqlQuery : queryStrings) {
			int numberOfUnboundTriplePattern = 0;
			String[] split = new String[3];
			for (String triple : sparqlQuery.constraintTriples) {
				split = triple.split(" ");
				if (split[0].startsWith("?") && split[1].startsWith("?") && split[2].startsWith("?")) {
					numberOfUnboundTriplePattern++;
				}
			}
			if (numberOfUnboundTriplePattern <= maximalUnboundTriplePatterns) {
				returnSet.add(sparqlQuery);
			}
		}
		return returnSet;
	}

	public static void main(String args[]) {
		Set<SPARQLQuery> queries = Sets.newHashSet();
		SPARQLQuery query = new SPARQLQuery("?proj a <http://dbpedia.org/ontology/Writer>.");
		query.addConstraint("?const a <http://dbpedia.org/ontology/Philosopher>. ");
		query.addConstraint("?const <http://dbpedia.org/ontology/influencedBy> ?proj. ");
		query.addConstraint("?const <http://dbpedia.org/ontology/abstract> ?abstractconst. ");
		query.addFilterOverAbstractsContraint("?const", "Nobel Prize");
		query.addFilterOverAbstractsContraint("?const", "refused");
		queries.add(query);
		query = new SPARQLQuery("?proj a <http://dbpedia.org/ontology/Writer>.");
		query.addConstraint("?const a <http://dbpedia.org/ontology/Philosopher>. ");
		query.addConstraint("?const ?p ?proj. ");
		query.addConstraint("?const <http://dbpedia.org/ontology/abstract> ?abstractconst. ");
		query.addFilterOverAbstractsContraint("?const", "Nobel Prize");
		query.addFilterOverAbstractsContraint("?const", "refused");
		queries.add(query);
		query = new SPARQLQuery("?proj a <http://dbpedia.org/ontology/Writer>.");
		query.addConstraint("?const a <http://dbpedia.org/ontology/Philosopher>. ");
		query.addConstraint("?const ?p ?proj. ");
		query.addConstraint("?const ?pp ?abstractconst. ");
		query.addFilterOverAbstractsContraint("?const", "Nobel Prize");
		query.addFilterOverAbstractsContraint("?const", "refused");
		queries.add(query);

		System.out.println("" + queries.size());
		queries = UnboundTriple.prune(queries, 1);
		System.out.println("" + queries.size());
	}

	public static Set<SPARQLQuery> pruneLooseEndsOfBGP(Set<SPARQLQuery> queryStrings) {
		Set<SPARQLQuery> returnSet = Sets.newHashSet();
		// discard queries with more than x unbound triples away
		for (SPARQLQuery sparqlQuery : queryStrings) {
			int numberOfUnboundTriplePattern = 0;
			String[] split = new String[3];
			for (String triple : sparqlQuery.constraintTriples) {
				split = triple.split(" ");
				if (split[0].startsWith("?") && split[1].startsWith("?") && split[2].startsWith("?")) {
					numberOfUnboundTriplePattern++;
				}
			}
		}
		return returnSet;
	}
}