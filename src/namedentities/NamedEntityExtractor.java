package namedentities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import opennlp.model.MaxentModel;
import opennlp.maxent.io.BinaryGISModelReader;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.Span;

public class NamedEntityExtractor {

	  public static String[] NAME_TYPES = { "person", "organization"};/*, "location",
      , "date", "time", "percentage", "money" };*/
      public static NamedEntityType[] ENTITY_TYPES = { NamedEntityType.PERSON,
                      NamedEntityType.ORGANIZATION};/*, NamedEntityType.LOCATION,
      NamedEntityType.DATE, NamedEntityType.TIME, NamedEntityType.PERCENTAGE,
        NamedEntityType.MONEY
       };*/

      NameFinderME[] finders = null;
      SentenceDetectorME englishSentenceDetector;
      /**
       * Constructor for a NamedEntity Extractor
       * @throws IOException
       */
      public NamedEntityExtractor() 
      {
              try {
				englishSentenceDetector = new SentenceDetectorME((SentenceModel) loadTrainnedModel("./data/en-sent.bin"));

              finders = new NameFinderME[NAME_TYPES.length];
              
              //Set up entity finders... need one for each 'type' of entity.
              //person type
              finders[0] = new NameFinderME(new TokenNameFinderModel(new FileInputStream("./data/en-ner-person.bin")));
              //organization type
              finders[1] = new NameFinderME(new TokenNameFinderModel(new FileInputStream("./data/en-ner-organization.bin")));
              //location type
             // finders[2] = new NameFinderME(new TokenNameFinderModel(new FileInputStream("./data/en-ner-location.bin")));
              //date type
             // finders[3] = new NameFinderME(new TokenNameFinderModel(new FileInputStream("./data/en-ner-date.bin")));
              //time type
             // finders[4] = new NameFinderME(new TokenNameFinderModel(new FileInputStream("./data/en-ner-time.bin")));
              //percentage type
             // finders[5] = new NameFinderME(new TokenNameFinderModel(new FileInputStream("./data/en-ner-percentage.bin")));
              //money type
             // finders[6] = new NameFinderME(new TokenNameFinderModel(new FileInputStream("./data/en-ner-money.bin")));
              
              
              } catch (IOException e) {
  				e.printStackTrace();
  			}
      }
      /**
       * Find entities using the passed in finder
       * @param entities - the list of entities(found entities will be added to this list)
       * @param tokens - the tokens within the sentence
       * @param finder - the current Named Entity finder that we're using
       * @param type - the type that we're searching for
       */
      protected void findNamesInSentence(List<NamedEntity> entities,
                      String[] tokens, NameFinderME finder, NamedEntityType type) {

              Span[] nameSpans = finder.find(tokens);
              if (nameSpans == null || nameSpans.length == 0)
                      return;
              for (Span span : nameSpans) {
                      StringBuilder buf = new StringBuilder();
                      for (int i = span.getStart(); i < span.getEnd(); i++) {
                              buf.append(tokens[i]);
                              if (i < span.getEnd() - 1)
                                      buf.append(" ");
                      }
                      NamedEntity ne = new NamedEntity();
                      ne.setType(type);
                      ne.setEntityValue(buf.toString());
                      entities.add(ne);
              }

      }
      /**
       * Parse Sentences with an english Sentence Detector.
       * @param text - the text with sentences to parse
       * @return - an arraylist of sentences
       */
      public ArrayList<String> findSentences(String text)
      {
              
              ArrayList<String> toReturn = new ArrayList<String>();
              //Use sentence detector to parse sentences
              String[] sentences = englishSentenceDetector.sentDetect(text);
              for(String sent: sentences)
              {
                      //Get rid of special characters - Do we want to do this?
                      sent = sent.replaceAll("\\n", " ");
                      sent = sent.replaceAll("\\.", "");
                      sent = sent.replaceAll("\\,", "");
                      toReturn.add(sent);
              }
              return toReturn;
      }
      /**
       * Find NamedEntities within the text
       * @param text - the text to find named entities in.
       * @return - the located entities.
       */
      public ArrayList<NamedEntity> findNamedEntities(String text) {
              ArrayList<NamedEntity> entities = new ArrayList<NamedEntity>();
              String[] sentences = englishSentenceDetector.sentDetect(text);
              opennlp.tools.tokenize.Tokenizer tokenizer = new SimpleTokenizer();
              
              //For each sentence use the list of 'finders' to find entities.
              for (String sentence : sentences) 
              {
                      String[] tokens = tokenizer.tokenize(sentence);
                      for (int i = 0; i < finders.length; i++) 
                      {
                              findNamesInSentence(entities, tokens, finders[i], ENTITY_TYPES[i]);
                      }
              }
 
              
              return entities;
      }
      /**
       * Not really sure... copied from online class
       * @param name
       * @return
       * @throws IOException
       */
      protected SentenceModel loadTrainnedModel(String name) throws IOException {
              SentenceModel model = new SentenceModel(new FileInputStream(name));
              return model;
      }

}