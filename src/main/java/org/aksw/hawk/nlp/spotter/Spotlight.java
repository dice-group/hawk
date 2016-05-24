package org.aksw.hawk.nlp.spotter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aksw.hawk.datastructures.HAWKQuestion;
import org.aksw.qa.commons.datastructure.Entity;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.impl.ResourceImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;

public class Spotlight extends ASpotter {
	static Logger log = LoggerFactory.getLogger(Spotlight.class);

	private String requestURL = "http://spotlight.sztaki.hu:2222/rest/annotate";
	private String confidence = "0.2";
	private String support = "20";

	public Spotlight() {
	}

	private String doTASK(final String inputText) throws MalformedURLException, IOException, ProtocolException {

		String urlParameters = "text=" + URLEncoder.encode(inputText, "UTF-8");
		urlParameters += "&confidence=" + confidence;
		urlParameters += "&support=" + support;

		return requestPOST(urlParameters, requestURL);
	}

	@Override
	public Map<String, List<Entity>> getEntities(final String question) {
		HashMap<String, List<Entity>> tmp = new HashMap<>();
		try {
			String foxJSONOutput = doTASK(question);

			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(foxJSONOutput);

			JSONArray resources = (JSONArray) jsonObject.get("Resources");
			if (resources != null) {
				ArrayList<Entity> tmpList = new ArrayList<>();
				for (Object res : resources.toArray()) {
					JSONObject next = (JSONObject) res;
					Entity ent = new Entity();
					ent.label = (String) next.get("@surfaceForm");
					String uri = ((String) next.get("@URI")).replaceAll(",", "%2C");
					ent.uris.add(new ResourceImpl(uri));
					for (String type : ((String) next.get("@types")).split(",")) {
						ent.posTypesAndCategories.add(new ResourceImpl(type));
					}
					tmpList.add(ent);
				}
				tmp.put("en", tmpList);
			}
		} catch (IOException | ParseException e) {
			log.error("Could not call Spotlight for NER/NED", e);
		}
		if (!tmp.isEmpty()) {
			log.debug("\t" + Joiner.on("\n").join(tmp.get("en")));
		}
		return tmp;
	}

	// TODO Christian: Unit Test
	public static void main(final String args[]) {
		HAWKQuestion q = new HAWKQuestion();
		q.getLanguageToQuestion().put("en", "Which buildings in art deco style did Shreve, Lamb and Harmon design?");
		ASpotter fox = new Spotlight();
		q.setLanguageToNamedEntites(fox.getEntities(q.getLanguageToQuestion().get("en")));
		for (String key : q.getLanguageToNamedEntites().keySet()) {
			System.out.println(key);
			for (Entity entity : q.getLanguageToNamedEntites().get(key)) {
				System.out.println("\t" + entity.label + " ->" + entity.type);
				for (Resource r : entity.posTypesAndCategories) {
					System.out.println("\t\tpos: " + r);
				}
				for (Resource r : entity.uris) {
					System.out.println("\t\turi: " + r);
				}
			}
		}
	}
}
